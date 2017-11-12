package org.localstorm.mcc.ejb.gtd;

import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.gtd.entity.Agent;
import org.localstorm.mcc.ejb.users.User;

import java.util.Collection;

/**
 *
 * @author localstorm
 */

public interface AgentManager extends BaseManager<Agent>
{
    public static final String BEAN_NAME="AgentManagerBean";
    
    public Collection<Agent> getAgents(User u);

    public Collection<Agent> getAgents();
}
