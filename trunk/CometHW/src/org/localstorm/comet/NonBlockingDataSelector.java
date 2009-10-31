package org.localstorm.comet;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;


public class NonBlockingDataSelector implements DataSelector
{
  private ConcurrentSkipListMap<SourceName, ConcurrentNavigableCyclicBuffer<Event>> dataSources;
  private AtomicLong sequence;
  private final int queueCapacity;

  public NonBlockingDataSelector(int queueCapacity)
  {
    this.queueCapacity = queueCapacity;
    dataSources = new ConcurrentSkipListMap<SourceName, ConcurrentNavigableCyclicBuffer<Event>>();
    sequence = new AtomicLong(0);
  }

  public void allocateDataSource(SourceName name)
  {
    dataSources.put(name, new ConcurrentNavigableCyclicBuffer<Event>(queueCapacity));
  }

  public void deallocateDataSources()
  {
    dataSources.clear();
  }

  public void deallocateDataSource(String portName)
  {
    for (SourceName sn : dataSources.keySet())
    {
      if (sn.getPortName().equals(portName))
      {
        dataSources.remove(sn);
      }
    }
  }

  @Override
  public Collection<Event> getEvents(Subscription sub)
  {
    LinkedList<Event> result = new LinkedList<Event>();

    for (SourceName sn : sub.getSourceNames())
    {
      ConcurrentNavigableCyclicBuffer<Event> buffer = dataSources.get(sn);
      if (buffer == null)
      {
        continue;
      }
      scanForNewEvents(sn.getStartId(), buffer, result);
    }

    return (result.isEmpty()) ? null : result;
  }

  private void scanForNewEvents(long startFromId, ConcurrentNavigableCyclicBuffer<Event> buffer,
                                LinkedList<Event> result)
  {
    Event e = buffer.getLast();
    if (e == null || e.getId() <= startFromId)
    {
      return;
    }

    // Request to get last message
    if (startFromId < 0)
    {
      result.add(e);
      return;
    }

    // Start from the end for better performance?
    Iterator<Event> it = buffer.iterator();
    while (it.hasNext())
    {
      e = it.next();
      if (e.getId() > startFromId)
      {
        result.add(e);
      }
    }
  }

  public boolean pushData(SourceName name, Object data)
  {
    ConcurrentNavigableCyclicBuffer<Event> buffer = dataSources.get(name);
    if (buffer == null)
    {
      return false;
    }
    else
    {
      String portName = name.getPortName();
      String streamName = name.getStreamName();
      buffer.add(new Event(sequence.incrementAndGet(), data, portName, streamName));
      return true;
    }
  }

}
