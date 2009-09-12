package sojohw;

/**
 * @author localstorm
 */
public class Employee
{
    private String firstName;
    private String secondName;
    private Address address;

    public Employee() {

    }

    public Employee(String fName, String sName, Address addr)
    {
        this.firstName = fName;
        this.secondName = sName;
        this.address = addr;
    }

    public Address getAddress() {
        return address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFullName()
    {
        return firstName+" "+secondName;
    }

    @Override
    public String toString() {
        return getFullName()+" at "+address.toString();
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

}
