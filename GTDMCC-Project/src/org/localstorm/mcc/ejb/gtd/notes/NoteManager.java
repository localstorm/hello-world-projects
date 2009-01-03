package org.localstorm.mcc.ejb.gtd.notes;

import java.util.Collection;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface NoteManager 
{
    public static final String BEAN_NAME = "NoteManagerBean";

    public Note findById(int noteId);
    
    public void updateNote(Note note);
    
    public void createAttachedNote(Note note, ReferencedObject obj);
    
    public void detachNote(Note note, ReferencedObject obj);
    
    public Collection<Note> findByObject(ReferencedObject obj);
    
}
