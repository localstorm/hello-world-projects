/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.gtd.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;

/**
 *
 * @author localstorm
 */
public class TaskHitCollector extends HitCollector {

    private IndexSearcher searcher;
    private Collection<Task> tasks;
    private String idFieldName;
    private Map<Integer, Task> tMap;
    private List<Relevance> result;

    public TaskHitCollector(IndexSearcher is, String idFieldName, Collection<Task> ts) {
        this.searcher    = is;
        this.tasks       = ts;
        this.idFieldName = idFieldName;
        this.tMap        = new TreeMap<Integer, Task>();

        for (Task t: tasks) {
            tMap.put(t.getId(), t);
        }

        result = new LinkedList<Relevance>();
    }

    @Override
    public void collect(int docId, float relevance) {
        try {

            Document doc = searcher.doc(docId);
            Field idf = doc.getField(this.idFieldName);
            String taskId = idf.stringValue();
            int tid = Integer.parseInt(taskId);

            Task t = this.tMap.get(tid);
            this.result.add(new Relevance(t, relevance));
            
        } catch(Exception e) {
            e.printStackTrace();
            // LOG?
        }
    }

    public Collection<Task> getTasks() {
        Collections.sort(this.result);
        
        List<Task> ordered = new ArrayList<Task>();

        for (Relevance r: this.result) {
            ordered.add(r.getTask());
        }

        return ordered;
    }

    private final static class Relevance implements Comparable<Relevance>
    {
        private Task task;
        private float relevance;

        public Relevance(Task task, float relevance) {
            this.task = task;
            this.relevance = relevance;
        }

        public float getRelevance() {
            return relevance;
        }

        public Task getTask() {
            return task;
        }

        @Override
        public int compareTo(Relevance o) {
            if (this.relevance<o.getRelevance()) {
                return 1;
            }
            if (this.relevance>o.getRelevance()) {
                return -1;
            }
            return 0;
        }
    }
}
