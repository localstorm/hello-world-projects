package org.localstorm.comet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.util.ajax.Continuation;
import org.mortbay.util.ajax.ContinuationSupport;

public class CometHandler implements GenericCallback
{
  private final long timeout;
  private final DataSelector ds;
  private final ContinuationsManager cm;
  private final AtomicLong sequence;
  private final OutputBuilder outputBuilder;

  public CometHandler(DataSelector ds, ContinuationsManager cm, long timeout, OutputBuilder builder)
  {
    this.timeout = timeout;
    this.ds = ds;
    this.cm = cm;
    this.sequence = new AtomicLong(0);
    this.outputBuilder = builder;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException
  {
    Continuation cnt = ContinuationSupport.getContinuation(request, request);
    Subscription sub = getSubscription(cnt, request);

    Long id = sub.getId();

    if (!cnt.isPending())
    {
      // First-time connection
      response.setContentType("text/plain");
    }

    Collection<Event> events = null;

    try
    {
      cm.forbidResuming();
      events = ds.getEvents(sub);
      if (events == null)
      {
        cm.register(id, cnt);

        // Suspend never blocks with SelectChannelConnector
        cnt.suspend(timeout);
      }
    }
    finally
    {
      cm.allowResuming();
    }

    // Resumed or data available
    cnt.setObject(null);
    cm.unregister(id);

    if (events != null)
    {
      writeEventsToOutput(response, events);
    }
    else
    {
      writeEventsToOutput(response, Collections.EMPTY_LIST);
    }
  }

  private void writeEventsToOutput(HttpServletResponse response, Collection<Event> events)
      throws IOException
  {
    ServletOutputStream sos = response.getOutputStream();
    sos.print(outputBuilder.startChunk());

    for (Iterator<Event> it = events.iterator(); it.hasNext();)
    {
      sos.print(outputBuilder.build(it.next()));
      if (it.hasNext())
      {
        sos.println(outputBuilder.getSeparator());
      }
    }

    sos.print(outputBuilder.endChunk());
  }

  private Subscription getSubscription(Continuation cnt, HttpServletRequest request)
  {
    Subscription sub = (Subscription) cnt.getObject();

    if (sub == null)
    {
      Collection<SourceName> sourceNames = getSourceNames(request);
      if (sourceNames != null)
      {
        sub = new Subscription(sequence.incrementAndGet(), sourceNames);
        cnt.setObject(sub);
      }
      else
      {
        return null;
      }
    }
    return sub;
  }

  private Collection<SourceName> getSourceNames(HttpServletRequest request)
  {
    String value = request.getParameter("lastId");
	return Collections.singletonList(new SourceName("INPUT", "DEFAULT", getIdFromString(value, -1)));
  }

  private long getIdFromString(String value, int defaultValue)
  {
    try
    {
      return Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      return defaultValue;
    }
  }

}
