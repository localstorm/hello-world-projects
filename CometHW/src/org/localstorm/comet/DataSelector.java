package org.localstorm.comet;

import java.util.Collection;

public interface DataSelector
{
  Collection<Event> getEvents(Subscription sub);
}
