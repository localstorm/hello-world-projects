package org.localstorm.comet;

import java.util.Random;

import org.localstorm.comet.CometAdapter.InputHandler;

class MyData
{
	private String name;
	private String value;
	
	public MyData(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}

/**
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        CometAdapter ca = new CometAdapter();
        
        ca.start();
        InputHandler ih = ca.getInputHandler("INPUT");
        
        Random rnd = new Random();
        for (int i=0; i<Integer.MAX_VALUE; i++)
        {
        	ih.handle(new MyData("name"+i, "value"+i));
        	System.out.println("Data been sent ("+i+")");
        	Thread.sleep(rnd.nextInt(5000));
        }
    }

}
