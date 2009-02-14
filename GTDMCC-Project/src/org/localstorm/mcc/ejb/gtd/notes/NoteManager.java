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

    public ReferencedObject findByNote(Note note);

    public void reattach(Note note, ReferencedObject ro);
    
    public void attachToObject(Note note, ReferencedObject obj);
    
    public void detach(Note note, ReferencedObject obj);
    
    public Collection<Note> findAllByObject(ReferencedObject obj);
    
}
