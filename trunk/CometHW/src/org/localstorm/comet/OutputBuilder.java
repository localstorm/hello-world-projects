package org.localstorm.comet;

public abstract class OutputBuilder
{
  public abstract String build(Event e);
  
  public abstract String startChunk();
  
  public abstract String getSeparator();
  
  public abstract String endChunk();
}
