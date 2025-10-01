import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;



/**
 *
 * @author Charalampos Sartzis
 */
public class Main
{
	
	// Private static fields.
    private static List<Driver> drivers = new ArrayList<>(20);    
    private static List<Vehicle> vehicles = new ArrayList<>(30);     
    private static List<Parking> parkings = new ArrayList<>(30);                // TODO CHECK size during the other functionalities.
    private static List<Receipt> receiptsArchive = new ArrayList<>(parkings.size());
    private static ParkingArea parkingArea = new ParkingArea();       



    public static void main(String[] args) 
    {
        // Driver driver = findOrCreateDriver("6909090909", "Charalampos", "Sartzis");
        // Vehicle vehicle = findOrCreateVehicle("ΥΥΥ1234", 1, false, driver);

        // Αρχικοποίηση λίστας οδηγών με βασικά δεδομένα.
        drivers.add(new Driver("Giannis", "Papadopoulos", "6900000001"));
        drivers.add(new Driver("Maria", "Lefa", "6900000002"));
        drivers.add(new Driver("Nikos", "Chatzis", "6900000003"));
        drivers.add(new Driver("Eleni", "Papanastasi", "6900000004"));
        drivers.add(new Driver("Petros", "Nikolaou", "6900000005"));

        

        // Αρχικοποίηση λίστας οχημάτων με βασικά δεδομένα.
        // 1 οδηγός μπορεί να οδηγεί και να παρκάρει 2 vehicles (ξεχωριστά).
        // Αλλά 1 όχημα οδηγείται κάθε φορά από 1 οδηγό.
        vehicles.add( new Vehicle("AAA1111", 1, false, drivers.get(0), Util.VEHICLE_TYPES[0]) );
        vehicles.add( new Vehicle("LLL4444", 1, true, drivers.get(1), Util.VEHICLE_TYPES[1]) );
        vehicles.add( new Vehicle("SSS7777", 2, true, drivers.get(3), Util.VEHICLE_TYPES[0]) );
        vehicles.add( new Vehicle("TTT8888", 1, false, drivers.get(3), Util.VEHICLE_TYPES[1]) );

        vehicles.add( new Van("GGG3333", 2, false, drivers.get(0), 6.0, Util.VAN_TYPES_OF_USE[0]) );
        vehicles.add( new Van("HHH6666", 1, true, drivers.get(2), 7.5, Util.VAN_TYPES_OF_USE[1]) );
        vehicles.add( new Van("JJJ9999", 2, false, drivers.get(4), 8.0, Util.VAN_TYPES_OF_USE[2]) );



        parkingArea.parkVehicle(vehicles.get(0), 3, parkings);
        parkingArea.parkVehicle(vehicles.get(2), 8, parkings);
        parkingArea.parkVehicle(vehicles.get(3), 15, parkings);
        parkingArea.parkVehicle(vehicles.get(5), 1, parkings);
        parkingArea.parkVehicle(vehicles.get(6), 24, parkings);
        


        Scanner input = new Scanner(System.in);
        String choice;
        boolean repeatFlag = false;
        do{
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Insert new driver");
            System.out.println("2. Insert new vehicle.");
            System.out.println("3. Park a vehicle.");
            System.out.println("4. Unpark a vehicle.");
            System.out.println("5. Show archived parkings of a driver.");
            System.out.println("6. Show archived parkings of a vehicle.");
            System.out.println("7. Show the number of free/occupied spaces, based on their type (electric or regular).");
            System.out.println("8. Exit.");
            System.out.print("Your choice: ");
            choice = input.nextLine();
            // input.nextLine();         // Clear buffer.

            switch(choice) 
            {
                case "1":
                    add_new_driver(input);
                    break;
                case "2":
                    add_new_vehicle(input);
                    break;
                case "3":
                    park_vehicle(input);
                    break;
                case "4":
                    unpark_vehicle(input);
                    break;
                case "5":
                    show_driver_receipts(input);
                    break;
                case "6":
                    show_vehicle_receipts(input);
                    break;
                case "7":
                    show_num_parking_spaces();
                    break;
                case "8":
                    System.out.println("Exiting...");
                    // exit();
                    break;
                default:
                    repeatFlag = true;
                    System.out.println("Invalid choice! Please try again.\n");
            }
        }while(repeatFlag);

        input.close();
    } //end-of-main.



    // Επιστρέφει τον οδηγό αν υπάρχει, ψάχνοντας με βάση το κινητό.
    //   Αλλιώς δημιουργείται νέος οδηγός, προστίθεται στη λίστα και επιστρέφεται.
    private static Driver add_new_driver(Scanner input) 
    {
        Driver driver;
        String firstName;
        String lastName;
        String phoneNumber;

        do {
            System.out.print("Enter mobile phone (10 digits): ");
            phoneNumber = input.nextLine();
        } while (!Util.is_valid_mobile_number(phoneNumber));

        driver = find_driver(phoneNumber);
        if(driver!=null)
        {
            System.out.println("Driver already exists!");
            return driver;        // TODO.
        }
        
        do {
            System.out.print("Enter first name (at least 2 letters): ");
            firstName = input.nextLine();
        } while(!Util.is_valid_name(firstName));

        do {
            System.out.print("Enter last name (at least 2 letters): ");
            lastName = input.nextLine();
        } while(!Util.is_valid_name(lastName));


        driver = new Driver(firstName, lastName, phoneNumber);
        drivers.add(driver);
        System.out.println("New driver added: " + driver);

        return driver;
    }

    // Επιστρέφει το όχημα, αν υπάρχει, ψάχνοντας με βάση την πινακίδα του.
    //   Αλλιώς δημιουργείται νέο όχημα, προστίθεται στη λίστα και επιστρέφεται.
    private static Vehicle add_new_vehicle(Scanner input) 
    {
        Vehicle vehicle;
        String licensePlate;
        int sizeInParkingSpaces = 0;
        boolean isElectric = false;
        String typeOfVehicle;
        Driver driver;

        do 
        {
            System.out.print("Enter license plate, e.g. [LLL1111]: ");
            licensePlate = input.nextLine();
        } while(!Util.is_valid_license_plate(licensePlate));

        vehicle = find_vehicle(licensePlate);
        if(vehicle!=null)
        {
            System.out.println("Vehicle already exists!");
            return vehicle;
        }

        String choice;
        boolean repeatFlag = false;
        do 
        {
            System.out.print("Enter size in parking spaces (1 or 2): ");
            choice = input.nextLine();
            // input.nextLine();         // Clear buffer.

            switch(choice)
            {
                case "1":
                    sizeInParkingSpaces = 1;
                    break;
                case "2":
                    sizeInParkingSpaces = 2;
                    break;
                default:
                    repeatFlag = true;
                    System.out.println("Invalid choice! Please try again.\n");
            }
        } while(repeatFlag);


        repeatFlag = false;
        do 
        {
            System.out.print("Is the vehicle electric? (0=No, 1=Yes): ");
            choice = input.nextLine();
            // input.nextLine();         // Clear buffer.

            switch(choice)
            {
                case "0":
                    isElectric = false;
                    break;
                case "1":
                    isElectric = true;
                    break;
                default:
                    repeatFlag = true;
                    System.out.println("Invalid choice! Please try again.\n");
            }
        } while(repeatFlag);


        do 
        {
            System.out.print("Enter the type of vehicle [CAR, MOTORCYCLE, VAN]: ");     // TODO menu-prompt for the VAN type.
            typeOfVehicle = input.nextLine();
        } while(!Util.is_valid_vehicle_type(typeOfVehicle));


        driver = add_new_driver(input);

        vehicle = new Vehicle(licensePlate, sizeInParkingSpaces, isElectric, driver, typeOfVehicle);
        vehicles.add(vehicle);
        System.out.println("Vehicle added successfully: " + vehicle);

        return vehicle;
    }

    private static void park_vehicle(Scanner input)
    {
        Vehicle vehicle = add_new_vehicle(input);
        int durationInHours=0;

        String durationStr;
        boolean repeatFlag = false;
        do 
        {
            System.out.print("Enter duration of parking in hours, between 1 and 24: ");
            durationStr = input.nextLine();

            if(Util.is_valid_number(durationStr))
            {
                durationInHours = Integer.parseInt(durationStr);
                if(!Util.is_valid_parking_duration(durationInHours))        // 
                {
                    repeatFlag = true;      // Re-prompt, because the given int is not between 1 and 24.
                }
            }
            else
            {
                repeatFlag = true;      // Re-prompt, because the given string is not a number.
            }

        } while(repeatFlag);


        // Attempt to park the vehicle in the parking area.
        boolean vehicleParked = parkingArea.parkVehicle(vehicle, durationInHours, parkings);
        if(!vehicleParked)
        {
            System.out.println("Vehicle could not be parked!");
            return;
        }

        // Parking newParking = new Parking(vehicle, parkingArea, durationInHours);
        // parkings.add(newParking);
        System.out.println("Vehicle has successfully been parked!");        // TODO menu-prompt for the parking details.
    }

    private static void unpark_vehicle(Scanner input)
    {
        // boolean noParkingsExist = (parkings==null || parkings.isEmpty());
        // if(noParkingsExist)
        // {
        //     System.out.println("No parkings exist!");
        //     return;
        // }

        Parking parking;
        String licensePlate;

        do 
        {
            System.out.print("Enter the license plate of the parked vehicle, e.g. [LLL1111]: ");
            licensePlate = input.nextLine();
        } while(!Util.is_valid_license_plate(licensePlate));

        parking = find_parking(licensePlate);
        if(parking==null)
        {
            System.out.println("There isn't any parked vehicle with the license plate ["+ licensePlate +"] !");
            return;
        }

        // Vehicle vehicle = parking.getVehicle();

        // Attempt to unpark the vehicle from the parking area and remove the corresponding parking.
        boolean vehicleUnparked = parkingArea.unparkVehicle(parking, parkings);
        if(!vehicleUnparked)
        {
            System.out.println("Vehicle could not be unparked!");
            return;
        }

        // At this point: vehicle has been unparked/departed successfully.
        Receipt parkingReceipt = new Receipt(parking);
        receiptsArchive.add(parkingReceipt);
        System.out.println("The vehicle with license plate [" + parking.getVehicle().getLicensePlate() + "] has been departed successfully. " + 
                            "\nHere's the receipt: [Parking cost= " + parkingReceipt.getCost() + "€].");
    }

    private static void show_driver_receipts(Scanner input)
    {
        if(receiptsArchive==null || receiptsArchive.isEmpty())
        {
            System.out.println("There are no archived parking receipts!");
            return;
        }

        String phoneNumber;

        do {
            System.out.print("Enter driver's mobile phone (10 digits): ");
            phoneNumber = input.nextLine();
        } while (!Util.is_valid_mobile_number(phoneNumber));

        // Driver driver = find_driver(phoneNumber);
        // if(driver==null)
        // {
        //     System.out.println("Driver doesn't exist!");
        //     return;        
        // }

        List<Receipt> driverReceipts = new ArrayList<>();

        int numOfReceipts = receiptsArchive.size();
        for(int i=0; i<numOfReceipts; i++)
        {
            Receipt currentReceipt = receiptsArchive.get(i);
            Vehicle currentVehicle = currentReceipt.getParking().getVehicle();
            Driver currentDriver = currentVehicle.getDriver();
            
            if(currentDriver.getMobilePhoneNumber()==phoneNumber)
            {
                driverReceipts.add(currentReceipt);
                System.out.println("" + currentReceipt + currentVehicle);  // Print the receipt's cost and the vehicle's license plate, using each object's toString().
            }
        }

        if(driverReceipts.isEmpty())
        {
            System.out.println("There are no archived receipts for the given driver!");
        }
    }

    private static void show_vehicle_receipts(Scanner input)
    {
        if(receiptsArchive==null || receiptsArchive.isEmpty())
        {
            System.out.println("There are no archived parking receipts!");
            return;
        }

        String licensePlate;

        do 
        {
            System.out.print("Enter license plate, e.g. [LLL1111]: ");
            licensePlate = input.nextLine();
        } while(!Util.is_valid_license_plate(licensePlate));

        // Vehicle vehicle = find_vehicle(licensePlate);
        // if(vehicle==null)
        // {
        //     System.out.println("Vehicle doesn't exist!");
        //     return;
        // }

        List<Receipt> vehicleReceipts = new ArrayList<>();

        int numOfReceipts = receiptsArchive.size();
        for(int i=0; i<numOfReceipts; i++)
        {
            Receipt currentReceipt = receiptsArchive.get(i);
            Parking currentParking = currentReceipt.getParking();
            Vehicle currentVehicle = currentParking.getVehicle();
            
            if(currentVehicle.getLicensePlate()==licensePlate)
            {
                vehicleReceipts.add(currentReceipt);
                System.out.println("" + currentReceipt + currentParking);   // Print the receipt's cost and the parking's begin date, using each object's toString().
            }
        }

        if(vehicleReceipts.isEmpty())
        {
            System.out.println("There are no archived receipts for the given vehicle!");
        }
    }

    private static void show_num_parking_spaces()
    {
        if(parkingArea==null)
        {
            System.out.println("There is no parking area!");
            return;
        }

        List<ParkingSpace> parkingAreaSpaces = parkingArea.getParkingSpaces();
        if( parkingAreaSpaces==null ||
            parkingAreaSpaces.isEmpty() )
        {
            System.out.println("There are no parking area spaces!");
            return;
        }

        if(!parkingArea.indexesHaveValidPositions())
        {
            System.out.println("Invalid indexes' positions!");
            return;
        }

        // int indexCurrentElecticSpace = parkingArea.getIndexCurrentElecticSpace();
        // int indexCurrentRegularSpace = parkingArea.getIndexCurrentRegularSpace();

        // Counters of the electrical and regular parking spaces, also considering if they are free or not (occupied).
        int numOccupiedElectricSpaces = 0;
        int numFreeElecticSpaces = 0;
        int numOccupiedRegularSpaces = 0;
        int numFreeRegularSpaces = 0;

        // Firstly we run through the first 20 spaces (the electrical ones).
        int lastElectricSpacePosition = parkingArea.getNumElectricParkingPlaces() - 1;
        for(int i=0; i<=lastElectricSpacePosition; i++)     
        {
            ParkingSpace currentSpace = parkingAreaSpaces.get(i);
            if(currentSpace.isOccupied())
            {
                numOccupiedElectricSpaces++;
            }
            else
            {
                numFreeElecticSpaces++;
            }
        }

        // Secondly we run through the last 80 spaces (the regular ones).
        int lastSpacePosition = parkingArea.getLastSpacePosition();
        for(int i=lastElectricSpacePosition+1; i<=lastSpacePosition; i++)
        {
            ParkingSpace currentSpace = parkingAreaSpaces.get(i);
            if(currentSpace.isOccupied())
            {
                numOccupiedRegularSpaces++;
            }
            else
            {
                numFreeRegularSpaces++;
            }
        }

        System.out.println("Number of electrical spaces: \t[free="+ numFreeElecticSpaces + ", occupied= " + numOccupiedElectricSpaces + "]");
        System.out.println("Number of regular spaces: \t[free="+ numFreeRegularSpaces + ", occupied= " + numOccupiedRegularSpaces + "]");
    }



    // Επιστρέφει τον οδηγό αν υπάρχει, ψάχνοντας με βάση το κινητό.
    private static Driver find_driver( String mobilePhoneNumber ) 
    {
        boolean noDriversExist = (drivers==null || drivers.isEmpty());
        if(noDriversExist)
        {
            // System.out.println("No drivers exist!");
            return null;
        }

        int numOfDrivers = drivers.size();

        for(int i=0; i<numOfDrivers; i++) 
        {
            Driver currentDriver = drivers.get(i);
            if( currentDriver.getMobilePhoneNumber().equals(mobilePhoneNumber) )
            {
                return currentDriver;
            }
        }

        // At this point: the driver doesn't exist.
        return null;
    }

    // Επιστρέφει το όχημα, αν υπάρχει, ψάχνοντας με βάση την πινακίδα του.
    private static Vehicle find_vehicle( String licensePlate) 
    {
        boolean noVehiclesExist = (vehicles==null || vehicles.isEmpty());
        if(noVehiclesExist)
        {
            // System.out.println("No vehicles exist!");
            return null;
        }

        int numOfVehicles = vehicles.size();

        for(int i=0; i<numOfVehicles; i++) 
        {
            Vehicle currentVehicle = vehicles.get(i);
            if( currentVehicle.getLicensePlate().equals(licensePlate) ) 
            {
                return currentVehicle;
            }
        }

        // At this point: the vehicle doesn't exist.
        return null;
    }

    // Επιστρέφει τη στάθμευση, αν υπάρχει, ψάχνοντας με βάση την πινακίδα του οχήματος.
    private static Parking find_parking(String licensePlate) 
    {
        boolean noParkingsExist = (parkings==null || parkings.isEmpty());
        if(noParkingsExist)
        {
            System.out.println("No parkings exist!");
            return null;
        }

        int numOfParkings= parkings.size();

        for(int i=0; i<numOfParkings; i++) 
        {
            Parking currentParking = parkings.get(i);
            Vehicle parkedVehicle = currentParking.getVehicle();

            if( parkedVehicle.getLicensePlate().equals(licensePlate) ) 
            {
                return currentParking;
            }
        }

        // At this point: the parking doesn't exist.
        return null;
    }

} //END-of-Main-class.
