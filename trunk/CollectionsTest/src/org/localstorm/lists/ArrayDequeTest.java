package org.localstorm.lists;

import org.localstrorm.tests.Experiment;
import org.localstrorm.tests.ExperimentResult;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: localstorm
 * Date: Nov 29, 2009
 * Time: 12:13:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayDequeTest {

    @Experiment(base = "ArrayDeque", name = "100000 lists to the end")
    public void insertToTheEnd(Queue<ExperimentResult> results) {
        int sizes[] = {0, 10, 100, 1000, 10000, 100000, 120000};

        for (int size : sizes) {
            System.gc();
            ArrayDeque al = new ArrayDeque(size);
            long nt = System.nanoTime();
            for (int i = 0; i < 100000; i++) {
                al.addLast(1);
            }
            long time = (System.nanoTime() - nt);
            results.add(new ExperimentResult(time, 100000, size));
        }
    }

    @Experiment(base = "ArrayDeque", name = "100000 lists to the beginning")
    public void insertToTheBeg(Queue<ExperimentResult> results) {
        int sizes[] = {0, 10, 100, 1000, 10000, 100000, 120000};

        for (int size : sizes) {
            System.gc();
            ArrayDeque al = new ArrayDeque(size);
            long nt = System.nanoTime();
            for (int i = 0; i < 100000; i++) {
                al.addFirst(1);
            }
            long time = (System.nanoTime() - nt);
            results.add(new ExperimentResult(time, 100000, size));
        }
    }

    @Experiment(base = "ArrayDeque", name = "Iterating over 100000 elements forward and backward with iterator")
    public void scanWithIteratorTest(Queue<ExperimentResult> results) {
        ArrayDeque<Integer> al = new ArrayDeque<Integer>(100000);
        for (int i = 0; i < 100000; i++) {
            al.add(1);
        }
        System.gc();
        long nt = System.nanoTime();
        Iterator<Integer> it = al.iterator();
        int i=0;
        while(it.hasNext()){
            i = it.next();
        }

        it = al.descendingIterator();
        while(it.hasNext()){
            i = it.next();
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 100000, 100000));
    }
    
}
