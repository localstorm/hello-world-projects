package org.localstorm.comet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Subscription
{
  private final long id;
  private final Set<SourceName> sourceNames;
  
  public Subscription(long incrementAndGet, Collection<SourceName> sourceNames)
  {
    this.id = incrementAndGet;
    this.sourceNames = new HashSet<SourceName>(sourceNames);
  }

  public Long getId()
  {
    return id;
  }

  public Collection<SourceName> getSourceNames()
  {
    return Collections.unmodifiableSet(sourceNames);
  }

  public boolean contains(SourceName name)
  {
    return sourceNames.contains(name);
  }

}
