package sojohw;

import java.util.ArrayList;
import java.util.List;
import net.sf.sojo.common.ObjectUtil;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.csv.CsvSerializer;
import net.sf.sojo.interchange.json.JsonSerializer;

/**
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Employee emp = new Employee("Obama", "Barack", new Address("White House", 1));
        Employee empCopy = (Employee) new ObjectUtil().copy(emp);

        System.out.println("1: "+emp);
        System.out.println("2: "+empCopy);

        Serializer csvSerializer = new CsvSerializer();

        List<Address> l = new ArrayList<Address>();
        l.add(emp.getAddress());
        l.add(empCopy.getAddress());

        String csvStr2 = (String) csvSerializer.serialize(l);
        System.out.println(csvStr2);

        Serializer jss = new JsonSerializer();
        System.out.println(jss.serialize(emp));
    }

}
