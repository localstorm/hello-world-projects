package org.localstorm.mcc.web.gtd.filter.security;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.gtd.files.FileAttachment;
import org.localstorm.mcc.ejb.gtd.files.FileManager;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.lists.ListManager;
import org.localstorm.mcc.ejb.gtd.notes.Note;
import org.localstorm.mcc.ejb.gtd.notes.NoteManager;
import org.localstorm.mcc.ejb.gtd.referenced.RefObjectManager;
import org.localstorm.mcc.ejb.gtd.referenced.ReferencedObject;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SecurityRuntimeException;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
class SecurityUtil 
{
    public static final String ACCESS_DENIED = "Access denied!";
    
    public static void checkContextSecurity(HttpSession sess, Integer contextId, User user, Logger log)
    {
        if (contextId!=null)
        {
            log.info("Checking access to context=" + contextId + " for user=" + user.getLogin());
            Map<Integer, Boolean> acm = (Map<Integer, Boolean>) SessionUtil.getValue(sess,
                                                                                     SessionKeys.ACCESSIBLE_CONTEXTS_MAP);
            if (contextId==-1 || acm.containsKey(contextId)) {
                return;
            }

            throw new SecurityRuntimeException(ACCESS_DENIED);
        }
    }

    public static void checkListSecurity(HttpSession sess, Integer listId, User user, Logger log)
            throws SecurityRuntimeException
    {
        if (listId!=null)
        {
            log.info("Checking access to list=" + listId + " for user=" + user.getLogin());
            Integer contextId;

            try {

                ListManager lm = ContextLookup.lookup(ListManager.class, ListManager.BEAN_NAME);
                GTDList list = lm.findById(listId);
                contextId = list.getContext().getId();
                
            } catch(Exception e) {
                log.error(e);
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }

            checkContextSecurity(sess, contextId, user, log);
        }
    }

    public static void checkObjectSecurity(HttpSession sess, Integer objectId, User user, Logger log)
    {
        if (objectId!=null)
        {
            log.info("Checking access to object=" + objectId + " for user=" + user.getLogin());
            Integer contextId;

            try {

                RefObjectManager rm = ContextLookup.lookup(RefObjectManager.class, RefObjectManager.BEAN_NAME);
                ReferencedObject ro = rm.findById(objectId);
                contextId = ro.getContext().getId();

            } catch(Exception e) {
                log.error(e);
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }

            checkContextSecurity(sess, contextId, user, log);
        }
    }

    public static void checkTaskSecurity(HttpSession sess, Integer taskId, User user, Logger log)
    {
        if (taskId!=null)
        {
            log.info("Checking access to taskt=" + taskId + " for user=" + user.getLogin());
            Integer listId;

            try {

                TaskManager tm = ContextLookup.lookup(TaskManager.class, TaskManager.BEAN_NAME);
                Task      task = tm.findById(taskId);
                listId = task.getList().getId();

            } catch(Exception e) {
                log.error(e);
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }

            checkListSecurity(sess, listId, user, log);
        }
    }

    public static void checkFileSecurity(HttpSession sess, Integer fileId, User user, Logger log)
    {
        if (fileId!=null)
        {
            log.info("Checking access to file=" + fileId + " for user=" + user.getLogin());
            Integer objectId;

            try {

                FileManager    fm = ContextLookup.lookup(FileManager.class, FileManager.BEAN_NAME);
                FileAttachment fa = fm.findById(fileId);
                ReferencedObject o = fm.findByFileAttachment(fa);
                objectId           = o.getId();

            } catch(Exception e) {
                log.error(e);
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }

            checkObjectSecurity(sess, objectId, user, log);
        }
    }

    public static void checkNoteSecurity(HttpSession sess, Integer noteId, User user, Logger log)
    {
        if (noteId!=null)
        {
            log.info("Checking access to note=" + noteId + " for user=" + user.getLogin());
            Integer objectId;

            try {

                NoteManager    nm = ContextLookup.lookup(NoteManager.class, NoteManager.BEAN_NAME);
                Note         note = nm.findById(noteId);
                ReferencedObject o = nm.findByNote(note);

                objectId           = o.getId();

            } catch(Exception e) {
                log.error(e);
                throw new SecurityRuntimeException(ACCESS_DENIED);
            }

            checkObjectSecurity(sess, objectId, user, log);
        }
    }

}
