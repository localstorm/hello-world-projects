package org.localstorm.lists;

import org.localstrorm.tests.Experiment;
import org.localstrorm.tests.ExperimentResult;

import java.util.*;

public class ArrayListTest {


    @Experiment(base = "ArrayList", name = "100000 lists to the end")
    public void insertToTheEnd(Queue<ExperimentResult> results) {
        int sizes[] = {0, 10, 100, 1000, 10000, 100000, 120000};

        for (int size : sizes) {
            System.gc();
            ArrayList al = new ArrayList(size);
            long nt = System.nanoTime();
            for (int i = 0; i < 100000; i++) {
                al.add(1);
            }
            long time = (System.nanoTime() - nt);
            results.add(new ExperimentResult(time, 100000, size));
        }
    }

    @Experiment(base = "ArrayList", name = "10000 lists to the beginning")
    public void insertToTheBeg(Queue<ExperimentResult> results) {
        int sizes[] = {0, 10, 100, 1000, 10000, 20000, 40000};

        for (int size : sizes) {
            System.gc();
            ArrayList al = new ArrayList(size);
            long nt = System.nanoTime();
            for (int i = 0; i < 10000; i++) {
                al.add(0, 1);
            }
            long time = (System.nanoTime() - nt);
            results.add(new ExperimentResult(time, 10000, size));
        }
    }

    @Experiment(base = "ArrayList", name = "10000 lists to the random place")
    public void insertToTheRnd(Queue<ExperimentResult> results) {
        int sizes[] = {0, 10, 100, 1000, 10000, 20000};

        Random rnd = new Random();
        int[] pregenerated = new int[10000];
        for (int i = 0; i < 10000; i++) {
            pregenerated[i] = rnd.nextInt(i + 1);
        }

        for (int size : sizes) {
            System.gc();
            ArrayList al = new ArrayList(size);
            long nt = System.nanoTime();
            for (int i = 0; i < 10000; i++) {
                al.add(pregenerated[i], 1);
            }
            long time = (System.nanoTime() - nt);
            results.add(new ExperimentResult(time, 10000, size));
        }
    }

    @Experiment(base = "ArrayList", name = "Iterating over 100000 elements forward and backward with iterator")
    public void scanWithIteratorTest(Queue<ExperimentResult> results) {
        ArrayList<Integer> al = new ArrayList<Integer>(100000);
        for (int i = 0; i < 100000; i++) {
            al.add(1);
        }
        System.gc();
        long nt = System.nanoTime();
        ListIterator<Integer> it = al.listIterator();
        int i = 0;
        while (it.hasNext()) {
            i = it.next();
        }

        it = al.listIterator(al.size() - 1);
        while (it.hasPrevious()) {
            i = it.previous();
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 100000, 100000));
    }

    @Experiment(base = "ArrayList", name = "Iterating over 100000 elements forward and backward with indices")
    public void scanWithIndicesTest(Queue<ExperimentResult> results) {
        ArrayList<Integer> al = new ArrayList<Integer>(100000);
        for (int i = 0; i < 100000; i++) {
            al.add(1);
        }
        System.gc();
        long nt = System.nanoTime();
        int j = 0;
        for (int i=0; i<100000; i++){
            j = al.get(i);
        }

        for (int i=100000-1; i>=0; i--){
            j = al.get(i);
        }

        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 100000, 100000));
    }

}
