package org.localstorm.comet;

import java.io.Serializable;


public class Event implements Serializable
{
  private static final long serialVersionUID = 2122658278004328236L;

  private final long uid;
  private final Object data;
  private final String portName;
  private final String streamName;
  
  public Event(long uid, Object data, String portName, String streamName)
  {
    this.uid = uid;
    this.data = data;
    this.portName = portName;
    this.streamName = streamName;
  }

  public Object getData()
  {
    return data;
  }
  
  public long getId()
  {
    return uid;
  }

  public String getPortName()
  {
    return portName;
  }
  
  public String getStreamName()
  {
    return streamName;
  }

  @Override
  public String toString()
  {
    return "Event [data=" + data + ", portName=" + portName + ", streamName=" + streamName
        + ", uid=" + uid + "]";
  }
  
  
  
  
}
