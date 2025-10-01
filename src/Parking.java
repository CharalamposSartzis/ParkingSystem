import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;



public class Parking 
{
	
	// Private static fields.
    private Vehicle vehicle;
    private ParkingArea parkingArea;
    private List<ParkingSpace> vehicleParkingSpaces;    // Each parked vehicle has occupied 1 or 2 parking spaces.
    private LocalDateTime beginDate;
    private int durationInHours;



    // Constructor.
    public Parking(  
                    Vehicle vehicle, 
                    ParkingArea parkingArea,
                    int durationInHours
                ) 
    {
        this.vehicle = vehicle;
        this.parkingArea = parkingArea;
        this.vehicleParkingSpaces = new ArrayList<>(Util.VEHICLE_MAX_SIZE_IN_SPACES);    // parkingArea.getCurrentVehicleOccupiedSpaces();      // TODO...
        this.beginDate = LocalDateTime.now();       // Current system datetime.
        this.durationInHours = durationInHours;
    }



	// Getters.

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingArea getParkingArea() {
        return parkingArea;
    }

    public List<ParkingSpace> getVehicleParkingSpaces() {
        return vehicleParkingSpaces;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public int getDurationInHours() {
        return durationInHours;
    }



	// Methods for managing parking spaces.

    public void occupyVehicleParkingSpace(ParkingSpace spaceToOccupy)
    { 
        spaceToOccupy.setOccupied(true);
        this.vehicleParkingSpaces.add(spaceToOccupy);
    }

    public void freeVehicleParkingSpace(ParkingSpace spaceToRemove)
    {
        spaceToRemove.setOccupied(false);
        this.vehicleParkingSpaces.remove(spaceToRemove);
    }

    
	
	// toString().
	
    @Override
    public String toString() 
    {
        return "(parking's beginDate= " + beginDate + ").";
    }
    

    // @Override
    // public String toString() {
    //     return "\nParking{" +
    //             "\nvehicle=" + vehicle +
    //             ", \noccupiedParkingSpaces=" + vehicleParkingSpaces + 
    //             ", \nbeginDate=" + beginDate +
    //             ", \ndurationInHours=" + durationInHours +
    //             "}\n";
    // }

}
