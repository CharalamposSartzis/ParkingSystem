
public class Vehicle 
{
	
    private String licensePlate;           // The unique identifier for the vehicle.
    private int sizeInParkingSpaces;       // Size in parking spaces (1 or 2).
    private boolean isElectric;            // Indicates if the vehicle is electric or not.
    private Driver driver;                 // Associated driver.
    private String typeOfVehicle;

    
	
    public Vehicle( String licensePlate, 
                    int sizeInParkingSpaces, 
                    boolean isElectric, 
                    Driver driver, 
                    String typeOfVehicle) 
    {
        this.licensePlate = licensePlate;
        this.sizeInParkingSpaces = sizeInParkingSpaces;
        this.isElectric = isElectric;
        this.driver = driver;
        this.typeOfVehicle = typeOfVehicle;
    }



    public String getLicensePlate() {
        return licensePlate;
    }

    public int getSizeInParkingSpaces() {
        return sizeInParkingSpaces;
    }

    public boolean isElectric() {
        return isElectric;
    }

    public Driver getDriver() {
        return driver;
    }

    public String getTypeOfVehicle() {
        return typeOfVehicle;
    }



    @Override
    public String toString() {
        return "\n(vehicle's licensePlate= " + licensePlate + ").";
    }

    // @Override
    // public String toString() {
    //     return "\n[" + 
    //             "\ntypeOfVehicle=" + typeOfVehicle +
    //             ", \nlicensePlate=" + licensePlate + 
    //             ", \nsizeInParkingSpaces=" + sizeInParkingSpaces + 
    //             ", \nisElectric=" + isElectric +
    //             ", \ndriver=" + driver + 
    //             "]\n";
    // }



} //END-of-class.
