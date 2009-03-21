package sax2hw;

import java.io.FileReader;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;


public class Main
{

    public static void main (String args[]) throws Exception
    {
        XMLReader xr = XMLReaderFactory.createXMLReader();
        MyHandler handler = new MyHandler(new MedlineCitationCollector());
        xr.setContentHandler(handler);
        xr.setErrorHandler(handler);

        FileReader r = new FileReader("test.xml");
        xr.parse(new InputSource(r));
        r.close();
    }


}
