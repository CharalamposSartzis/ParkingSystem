
public class Driver 
{
	// Private fields.
    private String firstName;
    private String lastName;
    private String mobilePhoneNumber;



    // Constructor.
    public Driver(String firstName, String lastName, String mobilePhoneNumber) 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhoneNumber = mobilePhoneNumber;
    }



    // Getters.
	
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }



    @Override
    public String toString() 
    {
        return "\n(" +
                "\nfirstName= " + firstName +
                ", \nlastName= " + lastName +
                ", \nmobilePhoneNumber= " + mobilePhoneNumber +
                ")\n";
    }
}
