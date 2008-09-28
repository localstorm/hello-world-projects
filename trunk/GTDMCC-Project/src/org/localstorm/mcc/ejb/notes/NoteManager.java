package org.localstorm.mcc.ejb.notes;

import java.util.Collection;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;
import org.localstorm.mcc.ejb.tasks.Task;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface NoteManager 
{
    public static final String BEAN_NAME = "NoteManagerBean";

    public Note findById(int noteId);
    
    public void updateNote(Note note);
    
    public void createAttachedNote(Note note, GTDList list);
    
    public void createAttachedNote(Note note, Task task);
    
    public void createAttachedNote(Note note, ReferencedObject obj);
    
    public void detachNote(Note note, ReferencedObject obj);
    
    public Collection<Note> findByList(GTDList list);
    public Collection<Note> findByTask(Task task);
    public Collection<Note> findByObject(ReferencedObject obj);
    
}
