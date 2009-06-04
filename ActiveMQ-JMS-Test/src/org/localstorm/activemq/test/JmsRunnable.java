package org.localstorm.activemq.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class JmsRunnable implements Runnable
{
    private String  destName;
    private ConnectionFactory cf;
    private InitialContext ic;

    public JmsRunnable(InitialContext ic, String destinationName) throws Exception 
    {
        this.destName = destinationName;
        this.cf = (ConnectionFactory)ic.lookup("ConnectionFactory");;
        this.ic = ic;
    }
  
    public void run()
    {
        try
        {
            Connection conn  = cf.createConnection();
            Session session  = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = (Destination) ic.lookup(destName); 
            MessageConsumer consumer = session.createConsumer(dest);
            
            conn.start();
            
            while (true) {
                Message msg = (Message) consumer.receive(1000);
                
                if (msg==null) {
                    System.out.println("Nothing received! Trying again");
                    continue;
                } else {
                    System.out.println("Something received.");
                    handle(msg);
                }
            }
            
        } catch(Exception e) {
            if (!interruptionCause(e)) {
                e.printStackTrace();
            } else {
              System.out.println("Listener thread interrupted.");
            }
        }
    }

    private boolean interruptionCause(Throwable e)
    {
      while (e !=null)  {
        if (e instanceof InterruptedException) {
          return true;
        }
        e = e.getCause();
      }
      return false;
    }

    private void handle(Message msg) throws JMSException
    {
       TextMessage txt = (TextMessage) msg;
       
       System.out.println("Text: "+txt.getText());
       
    }
}
