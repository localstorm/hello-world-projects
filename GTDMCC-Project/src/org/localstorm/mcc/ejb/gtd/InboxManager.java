package org.localstorm.mcc.ejb.gtd;

import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.gtd.entity.InboxEntry;
import org.localstorm.mcc.ejb.users.User;

import java.util.List;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface InboxManager extends BaseManager<InboxEntry>
{
    public static final String BEAN_NAME = "InboxManagerBean";

    public void submitNote(InboxEntry ie);
    public void removeNote(InboxEntry ie);

    public List<InboxEntry> getInboxEntries(User owner);
}
