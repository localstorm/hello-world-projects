package org.localstorm.mcc.ejb.notes;

import java.util.Collection;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.tasks.Task;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface NoteManager 
{
    public void updateNote(Note note);
    
    public Note findById(int id) throws ObjectNotFoundException;
    
    public void createAttachedNote(Note note, GTDList list);
    
    public void createAttachedNote(Note note, Task task);
    
    /*
     public void createAttachedNote(Note note, ReferencedObject obj);
    */
    
    public Collection<Note> findByList(GTDList list);
    public Collection<Note> findByTask(Task task);
    //public Collection<Note> findByList(ReferencedObject list);
    
    
}
