package org.localstorm.comet;

public class SourceName implements Comparable<SourceName>
{
  private final String portName;
  private final String streamName;
  private final long startId; 

  public SourceName(String portName, String streamName, long startId)
  {
    this.portName = portName;
    this.streamName = streamName;
    this.startId = startId;
  }
  
  public SourceName(String portName, String streamName)
  {
    this(portName, streamName, 0);
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((portName == null) ? 0 : portName.hashCode());
    result = prime * result + ((streamName == null) ? 0 : streamName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SourceName other = (SourceName) obj;
    if (portName == null)
    {
      if (other.portName != null)
        return false;
    }
    else if (!portName.equals(other.portName))
      return false;
    if (streamName == null)
    {
      if (other.streamName != null)
        return false;
    }
    else if (!streamName.equals(other.streamName))
      return false;
    return true;
  }

 
  @Override
  public String toString()
  {
    return "SourceName [portName=" + portName + ", streamName="
        + streamName + ", startId=" + startId + "]";
  }

  @Override
  public int compareTo(SourceName o)
  {
    int pc = this.portName.compareTo(o.portName);
    return (pc != 0) ? pc : this.streamName.compareTo(o.streamName);
  }

  public String getPortName()
  {
    return portName;
  }
  
  public String getStreamName()
  {
    return streamName;
  }

  public long getStartId()
  {
    return startId;
  }

}
