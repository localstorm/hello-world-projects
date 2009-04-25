package org.localstorm.tools.aop;

import org.localstorm.tools.runtime.DeadlineCallLogger;
import org.localstorm.tools.runtime.CallLogger;
import org.localstorm.tools.runtime.Logged;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.compiler.Javac.CtFieldWithInit;
import org.localstorm.tools.aop.random.RandomUtil;

/**
 * @author localstorm
 */
public class LogInstrumentor {

    private static String INSTRUMENTED_FLAG = "__$localstorm";

    public static void tryInstrument(ClassPool pool, File f) throws Exception {

        FileInputStream fis = null;

        try {

            fis = new FileInputStream(f);
            CtClass cl = pool.makeClass(fis);
            tryInstrumentCtClass(cl, f);

        } finally {
            if (fis!=null) {
                fis.close();
            }
        }
    }

    private static String getRandomThreadLocalFieldDeclaration(String field) {
        return "private final static ThreadLocal "+field+" = new ThreadLocal();";
    }

    private static String getParametersString(CtClass[] params) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');

        for (int i=0; i<params.length; i++) {
            sb.append(params[i].getName());

            if (i<params.length-1) {
                sb.append(',');
            }
        }

        sb.append(')');
        return sb.toString();
    }

    private static boolean instrumented(CtClass cl) {
        try {
            return cl.getField(INSTRUMENTED_FLAG)!=null;
        } catch(NotFoundException e) {
            return false;
        }
    }

    private static void markClassAsInstrumented(CtClass cl) throws Exception {
        CtField ctf = CtFieldWithInit.make("private static final boolean "+INSTRUMENTED_FLAG+"=true;", cl);
        cl.addField(ctf);
    }

    private static String getTimeLoggingBlock(String fieldId) {
        return "{"+fieldId+".set(Long.valueOf(System.currentTimeMillis()));}";
    }

    private static String getTimingCheckBlock(String fieldId,
                                              CtClass cl,
                                              CtMethod meth,
                                              long timeLimit) throws Exception {
        CtClass[]    params = meth.getParameterTypes();
        String paramsString = getParametersString(params);

        if (timeLimit<=0)
        {
            return "{ long time = (System.currentTimeMillis()-((Long)"+fieldId+".get()).longValue());" +
                     CallLogger.class.getName()+".getInstance().logTime(\""+cl.getName()+"\"," +
                     "\""+meth.getName()+paramsString+"\"," +
                     "time);" +
                     fieldId+".remove();}";
        } else {
            return "{ long time = (System.currentTimeMillis()-((Long)"+fieldId+".get()).longValue());" +
                     "if (time>"+timeLimit+") {"+
                        DeadlineCallLogger.class.getName()+".getInstance().logTime(\""+
                            cl.getName()+"\",\""+
                            meth.getName()+paramsString+"\","+
                            timeLimit+"L, time);" +
                     "}"+
                     fieldId+".remove();}";
        }
    }

    private static Logged getLogAnnotation(CtMethod method) {

        Object[] annot = method.getAvailableAnnotations();
        for (Object ann: annot) {
            if (ann instanceof Logged) {
                return (Logged) ann;
            }
        }

        return null;
    }

    private static void tryInstrumentCtClass(CtClass cl, File f) throws Exception
    {
        if (cl.isFrozen())
        {
            System.err.println("Class [" + cl.getName() + "] is frozen. Skipping...");
            return;
        }

        if (instrumented(cl))
        {
            System.err.println("Class [" + cl.getName() + "] already instrumented!");
            return;
        }
        
        boolean changes = false;
        CtMethod[] mets = cl.getDeclaredMethods();
        for (CtMethod method:mets)
        {
            Logged logAnn = getLogAnnotation(method);
            if (logAnn != null)
            {
                changes = true;
                String fieldId = RandomUtil.generateRandomVariableName(12);
                CtField tlf = CtFieldWithInit.make(getRandomThreadLocalFieldDeclaration(fieldId), cl);
                cl.addField(tlf);
                method.insertBefore(getTimeLoggingBlock(fieldId));
                method.insertAfter(getTimingCheckBlock(fieldId, cl, method, logAnn.value()));
            }
        }

        if (changes)
        {
            markClassAsInstrumented(cl);
            tryWriteClass(cl, f);
            System.out.println("Class [" + cl.getName() + "] was instrumented!");
        } else
        {
            System.out.println("Class [" + cl.getName() + "] was skipped!");
        }
    }

    private static void tryWriteClass(CtClass cl, File f)
            throws IOException,
                   CannotCompileException
    {
        DataOutputStream dos = null;
        String temp = f.getAbsolutePath()+".tmp";
        File tempFile = new File(temp);

        try
        {
            dos = new DataOutputStream(new FileOutputStream(temp));
            cl.toBytecode(dos);
            tempFile.renameTo(f);
        } catch(CannotCompileException e)
        {
            System.err.println("Can't compile class ["+cl.getName()+"]. Skipping...");
        } finally {
            tempFile.delete();
            
            if (dos!=null)
            {
                dos.close();
            }
        }
    }

}
