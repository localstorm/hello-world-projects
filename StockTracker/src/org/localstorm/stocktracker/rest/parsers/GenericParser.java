package org.localstorm.stocktracker.rest.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class GenericParser<T> implements XmlStreamParser<T>
{

    public abstract T parse(XMLStreamReader reader) throws XMLStreamException;


    protected Date parseDate(DateFormat df, String lexicalForm) throws ParseException
    {
        if (lexicalForm==null || lexicalForm.length()==0){
            return null;
        }

        return df.parse(lexicalForm);
    }
}
