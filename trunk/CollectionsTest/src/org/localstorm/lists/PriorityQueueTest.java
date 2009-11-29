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
public class PriorityQueueTest {


    @Experiment(base = "PriorityQueue", name = "Inserting 1000000 ascending elements")
    public void insertAsc(Queue<ExperimentResult> results) {

        System.gc();
        PriorityQueue<Integer> al = new PriorityQueue<Integer>();
        long nt = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            al.add(i);
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 1000000, 0));

    }

    @Experiment(base = "PriorityQueue", name = "Inserting 1000000 descending elements")
    public void insertDesc(Queue<ExperimentResult> results) {
        System.gc();
        PriorityQueue<Integer> al = new PriorityQueue<Integer>();
        long nt = System.nanoTime();
        for (int i = 1000000; i >= 1; i--) {
            al.add(i);
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 1000000, 0));
    }

    @Experiment(base = "PriorityQueue", name = "Inserting 100000 random elements")
    public void insertRnd(Queue<ExperimentResult> results) {
        System.gc();
        PriorityQueue<Integer> al = new PriorityQueue<Integer>();
        Random rnd = new Random();
        int[] pregenerated = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            pregenerated[i] = rnd.nextInt(1000000);
        }

        long nt = System.nanoTime();
        for (int i = 1000000-1; i >= 0; i--) {
            al.add(pregenerated[i]);
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 1000000, 0));
    }


    @Experiment(base = "PriorityQueue", name = "Popping 1000000 ascending elements")
    public void popAsc(Queue<ExperimentResult> results) {

        System.gc();
        PriorityQueue<Integer> al = new PriorityQueue<Integer>();
        for (int i = 0; i < 100000; i++) {
            al.add(i);
        }
        long nt = System.nanoTime();
        while (!al.isEmpty()){
            al.poll();
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 1000000, 0));

    }

    @Experiment(base = "PriorityQueue", name = "Popping 1000000 descending elements")
    public void popDesc(Queue<ExperimentResult> results) {
        System.gc();
        PriorityQueue<Integer> al = new PriorityQueue<Integer>();
        for (int i = 1000000; i >= 1; i--) {
            al.add(i);
        }
        long nt = System.nanoTime();
        while (!al.isEmpty()){
            al.poll();
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 1000000, 0));
    }

    @Experiment(base = "PriorityQueue", name = "Popping 1000000 random elements")
    public void popRnd(Queue<ExperimentResult> results) {
        System.gc();
        PriorityQueue<Integer> al = new PriorityQueue<Integer>();
        Random rnd = new Random();
        int[] pregenerated = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            pregenerated[i] = rnd.nextInt(1000000);
        }

        for (int i = 1000000-1; i >= 0; i--) {
            al.add(pregenerated[i]);
        }
        long nt = System.nanoTime();
        while (!al.isEmpty()){
            al.poll();
        }
        long time = (System.nanoTime() - nt);
        results.add(new ExperimentResult(time, 1000000, 0));
    }


}