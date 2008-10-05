package org.localstorm.mcc.ejb.notes.impl;

import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.notes.Note;
import org.localstorm.mcc.ejb.notes.NoteManagerLocal;
import org.localstorm.mcc.ejb.notes.NoteManagerRemote;
import org.localstorm.mcc.ejb.notes.NoteToObject;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;
import org.localstorm.mcc.ejb.tasks.Task;

/**
 *
 * @author localstorm
 */
@Stateless
public class NoteManagerBean implements NoteManagerLocal, NoteManagerRemote 
{

    @Override
    public void updateNote(Note note) 
    {
        em.merge( note );
    }

    @Override
    public void createAttachedNote(Note note, GTDList list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createAttachedNote(Note note, Task task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createAttachedNote(Note note, ReferencedObject obj) {
        em.persist(note);
        em.persist(new NoteToObject(note, obj));
    }
   
    @Override
    public Collection<Note> findByObject(ReferencedObject obj) {
        Query lq = em.createNamedQuery(NoteToObject.Queries.FIND_NOTES_BY_OBJECT);
        lq.setParameter(NoteToObject.Properties.OBJECT, obj);
        
        List<Note> list = lq.getResultList();
        System.out.println("RETURNED: "+list.size());
        return list;
    }
    
    @Override
    public Collection<Note> findByList(GTDList list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Note> findByTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Note findById(int noteId) {
        return (Note) em.find(Note.class, noteId);
    }
    
    @Override
    public void detachNote(Note note, ReferencedObject obj)
    {
        Query lq = em.createNamedQuery(NoteToObject.Queries.FIND_LINKS_BY_OBJECT_AND_NOTE);
        lq.setParameter(NoteToObject.Properties.OBJECT, obj);
        lq.setParameter(NoteToObject.Properties.NOTE,   note);
        
        List<NoteToObject> list = lq.getResultList();
        
        for (NoteToObject n: list)
        {
            em.remove(n);
        }
        
        note = this.findById(note.getId());
        em.remove(note);
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    private EntityManager em;
}
