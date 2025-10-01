
public class Van extends Vehicle 
{
    private double length;          // Length in meters.
    private String typeOfUse;
    
	
    public Van( String licensePlate, 
                int sizeInParkingSpaces, 
                boolean isElectric, 
                Driver driver,
                // String typeOfVehicle,        // Not needed. By-default is a VAN type of vehicle.
                double length,
                String typeOfUse
                ) 
    {
        super(  licensePlate, 
                sizeInParkingSpaces, 
                isElectric, 
                driver, 
                Util.VEHICLE_TYPES[2]        // TODO CHECK Vehicle type = "VAN".
                );
        this.length = length;
        this.typeOfUse = typeOfUse;
    }



    public double getLength() {
        return length;
    }

    public String getTypeOfUse() {
        return typeOfUse;
    }



    @Override
    public String toString() {
        return "\n[" + 
                super.toString() +
                "\nlength in meters= " + length + 
                ", \ntype of use= " + typeOfUse + 
                "]\n";
    }
}
