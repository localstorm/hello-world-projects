package org.localstorm.mcc.web;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.localstorm.mcc.ejb.gtd.files.FileAttachment;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.notes.Note;
import org.localstorm.mcc.ejb.gtd.tasks.Task;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Clipboard 
{
    private Map<Integer, GTDList> lists;
    private Map<Integer, Task>    tasks;
    private Map<Integer, Note>           notes;
    private Map<Integer, FileAttachment> files;
    
    public Clipboard() {
        lists = new TreeMap<Integer, GTDList>();
        tasks = new TreeMap<Integer, Task>();
        notes = new TreeMap<Integer, Note>();
        files = new TreeMap<Integer, FileAttachment>();
    }

    public Collection<FileAttachment> getFiles()
    {
        return this.files.values();
    }
    
    public Collection<GTDList> getLists()
    {
        return this.lists.values();
    }

    public Collection<Task> getTasks() {
        return this.tasks.values();
    }

    public Collection<Note> getNotes() {
        return this.notes.values();
    }
    
    public GTDList pickList(Integer id)
    {
        return this.lists.remove(id);
    }
    
    public Task pickTask(Integer id)
    {
        return this.tasks.remove(id);
    }

    public Note pickNote(Integer id)
    {
        return this.notes.remove(id);
    }
    
    public FileAttachment pickFile(Integer id)
    {
        return this.files.remove(id);
    }

    public void copyTask(Task t)
    {
        this.tasks.put(t.getId(), t);
    }
    
    public void copyList(GTDList l)
    {
        this.lists.put(l.getId(), l);
    }

    public void copyNote(Note n)
    {
        this.notes.put(n.getId(), n);
    }

    public void copyFile(FileAttachment n)
    {
        this.files.put(n.getId(), n);
    }

    public void clearLists()
    {
        this.lists.clear();
    }

    public void clearTasks()
    {
        this.tasks.clear();
    }

    public void clearNotes()
    {
        this.notes.clear();
    }

    public void clearFiles()
    {
        this.files.clear();
    }
}
