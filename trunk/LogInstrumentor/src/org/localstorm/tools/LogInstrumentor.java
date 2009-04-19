package org.localstorm.tools;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.compiler.Javac.CtFieldWithInit;
import org.localstorm.tools.random.RandomUtil;

/**
 * @author localstorm
 */
public class LogInstrumentor {

    private static String INSTRUMENTED_FLAG = "__$instrumented$";

    public static void main(String[] args) throws Exception {

        ClassPool cp = ClassPool.getDefault();
        CtClass cl = cp.getCtClass("org.localstorm.tools.Simple");

        if (instrumented(cl)) {
            System.err.println("Class ["+cl.getName()+"] already instrumented!");
            return;
        }

        markClassAsInstrumented(cl);
        
        CtMethod[] mets = cl.getDeclaredMethods();
        for (CtMethod method : mets) {
            Logged logAnn = getLogAnnotation(method);
            if (logAnn!=null) {
                String fieldId = RandomUtil.generateRandomVariableName(12);

                CtField tlf = CtFieldWithInit.make(getRandomThreadLocalFieldDeclaration(fieldId), cl);
                cl.addField(tlf);

                method.insertBefore(getTimeLoggingBlock(fieldId));
                method.insertAfter(getTimingCheckBlock(fieldId, cl, method, logAnn.value()));
            }
        }

        cl.toBytecode(new DataOutputStream(new FileOutputStream("build/classes/org/localstorm/tools/Simple.class")));

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
            cl.getField(INSTRUMENTED_FLAG);
            return true;
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

}
