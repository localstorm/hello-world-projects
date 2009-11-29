package org.localstorm.lists;

import org.localstrorm.tests.Experiment;
import org.localstrorm.tests.ExperimentResult;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: localstorm
 * Date: Nov 29, 2009
 * Time: 12:18:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class LinkedListTest {


    @Experiment(base = "LinkedList", name = "Inserting 100000 elements to the end of the list")
    public void insertToTheEnd(Queue<ExperimentResult> results) {

        System.gc();
        LinkedList al = new LinkedList();
        long nt = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            al.add(1);
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 100000, 0));

    }

    @Experiment(base = "LinkedList", name = "Inserting 100000 elements to the beginning of the list")
    public void insertToTheBeg(Queue<ExperimentResult> results) {
        System.gc();
        LinkedList al = new LinkedList();
        long nt = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            al.add(0, 1);
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 100000, 0));
    }

    @Experiment(base = "LinkedList", name = "Inserting 100000 elements to the random place of the list")
    public void insertToTheRnd(Queue<ExperimentResult> results) {

        Random rnd = new Random();
        int[] pregenerated = new int[10000];
        for (int i = 0; i < 10000; i++) {
            pregenerated[i] = rnd.nextInt(i + 1);
        }

        System.gc();
        LinkedList al = new LinkedList();
        long nt = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            al.add(pregenerated[i], 1);
        }

        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 10000, 0));
    }

    @Experiment(base = "LinkedList", name = "Iterating over 100000 elements forward and backward with iterator")
    public void scanWithIteratorTest(Queue<ExperimentResult> results) {
        LinkedList<Integer> al = new LinkedList<Integer>();
        for (int i = 0; i < 100000; i++) {
            al.add(1);
        }
        System.gc();
        long nt = System.nanoTime();
        ListIterator<Integer> it = al.listIterator();
        int i=0;
        while(it.hasNext()){
            i = it.next();
        }

        Iterator<Integer> it2 = al.descendingIterator();
        while(it2.hasNext()){
            i = it2.next();
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 100000, 100000));
    }

    @Experiment(base = "LinkedList", name = "Iterating over 10000 elements forward and backward with indices")
    public void scanWithIndicesTest(Queue<ExperimentResult> results) {
        LinkedList<Integer> al = new LinkedList<Integer>();
        for (int i = 0; i < 10000; i++) {
            al.add(1);
        }
        System.gc();
        long nt = System.nanoTime();
        int j = 0;
        for (int i=0; i<10000; i++){
            j = al.get(i);
        }

        for (int i=10000-1; i>=0; i--){
            j = al.get(i);
        }

        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 10000, 10000));
    }
}
