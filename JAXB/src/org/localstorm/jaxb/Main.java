/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.jaxb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.localstorm.jaxb.bound.*;

/**
 *
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JAXBException, IOException {
        JAXBContext ctx = JAXBContext.newInstance(new Class[] {Shiporder.class, Map.class});
        Unmarshaller um = ctx.createUnmarshaller();
       
        Shiporder c = (Shiporder) um.unmarshal(new File("input.xml"));
        System.out.println(c.getShipto().getCountry());

        Marshaller mr = ctx.createMarshaller();
        FileOutputStream fos = new FileOutputStream("output.xml");
        mr.marshal(c, fos);
        fos.close();
        

        Map m = (Map) um.unmarshal(new File("mapInput.xml"));
        for (EntryType et: m.getEntries()) {
            System.out.println( et.getKey()+"="+et.getValue() );
        }

        //MapWrapper mt = new MapWrapper();
       // EntryListType el = new EntryListType();
        //mt.setEntryList(el);
        
        //mr.marshal( mt, System.out );
        
    }

}
