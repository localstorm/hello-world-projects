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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        ClassPool cp = ClassPool.getDefault();
        CtClass cl = cp.getCtClass("org.localstorm.tools.Simple");

        if (instrumented(cl))
        {
            System.err.println("Class ["+cl.getName()+"] already instrumented!");
            return;
        }

        markClassAsInstrumented(cl);
        
        CtMethod[] mets = cl.getDeclaredMethods();
        for (CtMethod method : mets)
        {
            if (hasLogAnnotation(method))
            {
                
                String fieldId = RandomUtil.generateRandomVariableName(12);

                CtField tlf = CtFieldWithInit.make(getRandomThreadLocalFieldDeclaration(fieldId), cl);
                cl.addField(tlf);

                method.insertBefore(getTimeLoggingBlock(fieldId));
                method.insertAfter(getTimingCheckBlock(fieldId, cl, method));
            }
        }

        cl.toBytecode(new DataOutputStream(new FileOutputStream("build/classes/org/localstorm/tools/Simple.class")));

    }

    private static String getRandomThreadLocalFieldDeclaration(String field)
    {
        return "private final ThreadLocal "+field+" = new ThreadLocal();";
    }

    private static boolean instrumented(CtClass cl)
    {
        try
        {
            cl.getField(INSTRUMENTED_FLAG);
            return true;
        } catch(NotFoundException e){
            return false;
        }
    }

    private static void markClassAsInstrumented(CtClass cl) throws Exception
    {
        CtField ctf = CtFieldWithInit.make("private static final boolean "+INSTRUMENTED_FLAG+"=true;", cl);
        cl.addField(ctf);
    }

    private static String getTimeLoggingBlock(String fieldId)
    {
        return "{"+fieldId+".set(Long.valueOf(System.currentTimeMillis()));}";
    }

    private static String getTimingCheckBlock(String fieldId, CtClass cl, CtMethod meth)
    {
        return "{ long time = (System.currentTimeMillis()-((Long)"+fieldId+".get()).longValue());" +
                 "org.localstorm.tools.CallTimingLogger.getInstance().logTime(\""+cl.getName()+"\",\""+meth.getName()+"\",time);" +
                 fieldId+".remove();}";
    }

    private static boolean hasLogAnnotation(CtMethod method)
    {
        Object[] annot = method.getAvailableAnnotations();
        for (Object ann: annot)
        {
            if (ann instanceof Log)
            {
                return true;
            }
        }

        return false;
    }

}
