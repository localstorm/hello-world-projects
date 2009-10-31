package org.localstorm.comet;

public class CometAdapter
{
  public static final String ADAPTER_NAME = "CometAdapter";

  private long m_timeout;
  private int m_queuesCapacity = 100;
  
  private NonBlockingDataSelector dataSelector;
  private ContinuationsManager contManager;

  public CometAdapter() {
    this.dataSelector = new NonBlockingDataSelector(m_queuesCapacity);
    this.contManager = new ContinuationsManager();
    
  }

  public void start() throws Exception
  {
	  JettyHttpServer server = new JettyHttpServer(8080);
	  
	  dataSelector.deallocateDataSources();
	  dataSelector.allocateDataSource(new SourceName("INPUT", "DEFAULT"));
	 
 	  OutputBuilder outputBuilder = new JsonOutputBuilder();
	  CometHandler ch = new CometHandler(dataSelector, contManager, m_timeout, outputBuilder);
	 
	  server.addCallback("/comet", ch);

	  server.addCallback("/test", new TestCallback("/comet.html"));
	  server.addCallback("/jquery-1.3.2.min.js", new TestCallback("/jquery-1.3.2.min.js"));
	  server.addCallback("/jquery.json-2.2.min.js", new TestCallback("/jquery.json-2.2.min.js"));
  }

  public InputHandler getInputHandler(String port)
  {
	  return new InputHandler(port);
  }
  
  // -------------- Inner Classes -----------------------------

  public class InputHandler
  {
    private String port;

    public InputHandler(String port)
    {
      this.port = port;
    }

    public void handle(Object o)
    {
      SourceName sourceName = new SourceName(port, "DEFAULT");
      dataSelector.pushData(sourceName, o);
      contManager.resumeForSourceName(sourceName);
    }

  } // end of handler!

}
