package org.localstorm.comet;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

public class ConcurrentNavigableCyclicBuffer<E>
{
  private final LinkedBlockingDeque<E> lbd;

  public ConcurrentNavigableCyclicBuffer(int capacity)
  {
    lbd = new LinkedBlockingDeque<E>(capacity);
  }

  public synchronized void add(E data)
  {
    if (!lbd.offer(data))
    {
      lbd.removeFirst();
      lbd.add(data);
    }
  }

  // Don't make this method synchronized!
  public Iterator<E> iterator()
  {
    return lbd.iterator();
  }

  public E getLast()
  {
    return lbd.peekLast();
  }

}
