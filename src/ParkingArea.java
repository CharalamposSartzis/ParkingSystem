import java.util.List;
import java.util.ArrayList;



public class ParkingArea 
{
    // The following characteristics are unique for each ParkingArea object. So, we're not using the "static" keyword.
    // In current application we are using only one instance of ParkingArea (there's only one parking area).

    // The parking area's specifications.
    private final int numParkingPlaces = 100;
    private final int numElectricParkingPlaces = (numParkingPlaces * 20)/100;       // Electric parking spaces = 20% of total parking spaces.
    private final int numRegularParkingPlaces = numParkingPlaces - numElectricParkingPlaces;

    private final int firstSpacePosition = 0;
    private final int lastSpacePosition = numParkingPlaces - 1;

    // Parking area's attributes that will be initialized at the time of area's creation/construction.
    private List<ParkingSpace> parkingSpaces;
    private int indexCurrentElectricSpace;    
    private int indexCurrentRegularSpace;  
    // private List<ParkingSpace> currentVehicleOccupiedSpaces;     // The spaces that are being occupied by each parked vehicle at a time.
    

    public ParkingArea() 
    {
        parkingSpaces = new ArrayList<>(numParkingPlaces);
        initParkingSpaces(); 

        // The index of the current electric space begins from the first parking space (ascending) and
        //   the index of the current regular space begins from the last parking space (descending).
        //   This approach allows the parking of an electric vehicle in the regular spaces, if there aren't any free electric ones.
        //   On the other side: the regular vehicles are not allowed to park beyond the regular spaces.
        indexCurrentElectricSpace = firstSpacePosition;                      
        indexCurrentRegularSpace = lastSpacePosition;    

        // No occupied spaces at the beginning. They will be filled (at a time) 
        //   only if the current vehicle has successfully been parked.
        // currentVehicleOccupiedSpaces = new ArrayList<>(Util.VEHICLE_MAX_SIZE_IN_SPACES);        
    }



    public List<ParkingSpace> getParkingSpaces(){
        return parkingSpaces;
    }
    


    public int getFirstSpacePosition(){
        return firstSpacePosition;
    }

    public int getNumElectricParkingPlaces(){
        return numElectricParkingPlaces;
    }

    public int getLastSpacePosition(){
        return lastSpacePosition;
    }

    public int getIndexCurrentElectricSpace()
    {
        return indexCurrentElectricSpace;
    }

    public int getIndexCurrentRegularSpace()
    {
        return indexCurrentRegularSpace;
    }

    

    @Override
    public String toString() {
        return "\nParking Area{" +
                    parkingSpaces +     
                "}\n";
    }


    // The total operation of trying to park a vehicle in the parking area.
    public boolean parkVehicle(Vehicle vehicle, int durationInHours, List<Parking> parkings)          
    {
        // At the beginning of the vehicle's parking operation, there are not any occupied spaces.
        // currentVehicleOccupiedSpaces.clear();      

        if(parkingSpaces==null || parkingSpaces.isEmpty())      // If parkingSpaces list is null OR empty.
        {
            return false;               // No spaces for parking!
        }

        int vehicleSize = vehicle.getSizeInParkingSpaces();
        if(!Util.is_valid_parking_size(vehicleSize))        // If given vehicle has a parking size of 1 or 2 spaces.
        {
            return false;
        }

        // At this point: the vehicle has a size of 1 or 2 spaces.

        int indexCurrentSpace;          // by-default: =0.

        boolean isElectricVehicle = vehicle.isElectric();
        if(isElectricVehicle)
        {
            if(!existElectricSpacesFree())       
            {
                return false;       // There are not any free electric spaces.
            }

            indexCurrentSpace = indexCurrentElectricSpace;   // Begin the vehicle parking from the current free electric space.
        }
        else
        {
            if(!existRegularSpacesFree())      
            {
                return false;       // There are not any free regular spaces.
            }

            indexCurrentSpace = indexCurrentRegularSpace;   // Begin the vehicle parking from the current regular space.
        }

        ParkingSpace currentSpace = parkingSpaces.get(indexCurrentSpace);   
        if(currentSpace==null || currentSpace.isOccupied())     // If the current regular space isn't free.
        {
            return false;
        }

        // At this point: the current parking space is free. So the vehicle can park here.
        Parking newParking = new Parking(vehicle, this, durationInHours);
        newParking.occupyVehicleParkingSpace(currentSpace);

        if(vehicleSize==2)     // If the vehicle needs 2 spaces to park: 1 more space has to be occupied, if it's free.
        {
            ParkingSpace nextSpace = parkingSpaces.get(indexCurrentSpace+1);
            if(nextSpace==null || nextSpace.isOccupied())
            {
                return false;
            }
            
            // At this point: the next parking space is free. So the vehicle (of size 2) occupies the 2nd needed space.
            newParking.occupyVehicleParkingSpace(nextSpace);
        }

        // Parking newParking = new Parking(vehicle, parkingArea, durationInHours);
        // parkings.add(newParking);

        // At this point: the vehicle has been parked successfully.
        parkings.add(newParking);       
        moveIndexCurrentSpace(isElectricVehicle, vehicleSize, true);    // So the current space index is moved by as many positions as the vehicle's size.

        return true;
    }

    // The departure of the vehicle: free the parking area's spaces and remove the corresponding parking.
    public boolean unparkVehicle(Parking parking, List<Parking> parkings)
    {
        if(parkings==null || parkings.isEmpty())
        {
            System.out.println("There are no parkings!");
            return false;
        }

        if(parking==null)
        {
            System.out.println("No parking has been given!");
            return false;
        }

        List<ParkingSpace> vehicleParkingSpaces = parking.getVehicleParkingSpaces();
        if(vehicleParkingSpaces==null || vehicleParkingSpaces.isEmpty())
        {
            System.out.println("Invalid parking: it hasn't any occupied parking spaces!");
            return false;
        }

        Vehicle parkedVehicle = parking.getVehicle();
        boolean isElectricVehicle = parkedVehicle.isElectric();
        int vehicleSize = parkedVehicle.getSizeInParkingSpaces();

        int numOfVehicleParkingSpaces = vehicleParkingSpaces.size();
        for(int i=(numOfVehicleParkingSpaces-1); i>=0; i--)             // We iterate the list backwards, in order to remove the last item, each time (less complexity).
        {
            ParkingSpace vehicleParkingSpace = vehicleParkingSpaces.get(i);
            if(!vehicleParkingSpace.isOccupied())
            {
                System.out.println("Invalid parking: it has a free vehicle's parking space!");
                return false;
            }

            // At this point, the vehicle is departing. So we free the parking spaces.
            parking.freeVehicleParkingSpace(vehicleParkingSpace);
        }

        // Final steps of vehicle's departure: remove the parking and move back the index of the current free parking space.
        parkings.remove(parking);
        moveIndexCurrentSpace(isElectricVehicle, vehicleSize, false);

        return true;
    }

    private void initParkingSpaces() 
    {
        for (int i = 0; i < numElectricParkingPlaces; i++)
        {
            parkingSpaces.add(new ParkingSpace(i+1, true));     // The first, e.g. 20 parking spaces, are electric ones.
        }
            
        for (int i = numElectricParkingPlaces; i < numParkingPlaces; i++)
        {
            parkingSpaces.add(new ParkingSpace(i+1, false));    // Just after the electric parking spaces, they begin the regular ones.
        }
    }


    public boolean indexesHaveValidPositions()
    {
        // Valid indexes positions: 
        //   when their values are between the first and last space position, correspondingly,
        //   AND 
        //   when they haven't cross each other.
        return  (indexCurrentElectricSpace >= firstSpacePosition) && 
                (indexCurrentRegularSpace <= lastSpacePosition) && 
                 indexesHaveNotCrossed();     
    }

    private boolean indexesHaveNotCrossed()
    {
        // True: if the current-electric and current-regular space indexes haven't cross each other.
        return (indexCurrentElectricSpace <= indexCurrentRegularSpace);     
    }

    private boolean existElectricSpacesFree()
    {
        // There's at least 1 electric space free: 
        //   if the current-electric and current-regular space indexes: haven't cross each other.
        return indexesHaveNotCrossed();     
    }

    private boolean existRegularSpacesFree()
    {
        // There's at least 1 regular space free: 
        //   if the current-electric and current-regular space indexes: haven't cross each other 
        //   AND also
        //   the current-regular space index: hasn't surpass the boundary line between the regular and electric spaces.
        return  indexesHaveNotCrossed() && 
                (indexCurrentRegularSpace >= numElectricParkingPlaces);                                           
    }

    private void moveIndexCurrentSpace(boolean isElectricVehicle, int vehicleSize, boolean occupySpace)
    {
        if(occupySpace)
        {
            if(isElectricVehicle)
            {
                // The current-electric space index is moving forward, as many positions as the parked vehicle's size.
                indexCurrentElectricSpace += vehicleSize;       
            }
            else
            {
                // The current-regular space index is moving backwards, as many positions as the parked vehicle's size.
                indexCurrentRegularSpace -= vehicleSize;        
            }
        }

        if(!occupySpace)    // If we want to free the parking space. 
        {
            if(isElectricVehicle)
            {
                // The current-electric space index is moving backwards, as many positions as the departured vehicle's size.
                indexCurrentElectricSpace -= vehicleSize;       
            }
            else
            {
                // The current-regular space index is moving forward, as many positions as the departured vehicle's size.
                indexCurrentRegularSpace += vehicleSize;        
            }
        }
    }

}
