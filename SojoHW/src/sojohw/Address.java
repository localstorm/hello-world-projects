package sojohw;

/**
 * @author localstorm
 */
public class Address {

    private String street;
    private int house;

    public Address() {
    }

    
    public Address(String street, int house) {
        this.street = street;
        this.house = house;
    }

    public int getHouse() {
        return house;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return street +" "+house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    

}
