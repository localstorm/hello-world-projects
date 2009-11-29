package org.localstrorm.tests;

import org.localstorm.lists.ArrayDequeTest;
import org.localstorm.lists.ArrayListTest;
import org.localstorm.lists.LinkedListTest;
import org.localstorm.lists.PriorityQueueTest;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Queue;

public class PerformanceTest {

    public static void main(String[] args) throws Exception {
        run(ArrayListTest.class);
        run(ArrayDequeTest.class);
        run(LinkedListTest.class);
        run(PriorityQueueTest.class);
    }

    private static void run(Class<?> test) throws Exception {
        Object o = test.newInstance();
        for (Method m : test.getMethods()) {
            Experiment exp = m.getAnnotation(Experiment.class);
            if (exp != null) {
                Queue<ExperimentResult> queue = new ArrayDeque<ExperimentResult>();
                m.invoke(o, queue);

                for (ExperimentResult er: queue) {
                    System.out.println("["+er.getTime()/er.getFactor()+"] "+exp.base()+" \\ "+exp.name()+", initial size of collection:\t"+er.getSize());
                }
            }
        }
    }

}
