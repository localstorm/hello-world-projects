package org.localstorm.mcc.ejb.notes.impl;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.Constants;
import org.localstorm.mcc.ejb.except.ObjectNotFoundException;
import org.localstorm.mcc.ejb.lists.GTDList;
import org.localstorm.mcc.ejb.notes.Note;
import org.localstorm.mcc.ejb.notes.NoteManagerLocal;
import org.localstorm.mcc.ejb.notes.NoteManagerRemote;
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
    public Note findById(int id) throws ObjectNotFoundException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Note> findByList(GTDList list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Note> findByTask(Task task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    private EntityManager em;
}
