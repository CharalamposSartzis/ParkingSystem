
/**
 *
 * Utility class for the global scope constants and simple validation functions.
 *
 */
public class Util 
{
    public static final String[] VEHICLE_TYPES      = {"CAR", "MOTORCYCLE", "VAN"};
    public static final String[] VAN_TYPES_OF_USE   = {"FOOD_TRANSPORT", "OBJECTS_TRANSPORT", "ORDERS_DELIVERY"};

    public static final int VEHICLE_MIN_SIZE_IN_SPACES = 1;
    public static final int VEHICLE_MAX_SIZE_IN_SPACES = 2;

    public static final int MIN_PARKING_HOURS = 1;
    public static final int MAX_PARKING_HOURS = 24;

    public static final int MIN_NAME_LENGTH = 2;



    public static boolean is_valid_vehicle_type(String vehicleType) 
    {
        int numOfVehicleTypes = VEHICLE_TYPES.length;

        for(int i=0; i<numOfVehicleTypes; i++)
        {
            if( VEHICLE_TYPES[i].equals(vehicleType) ) 
            {
                return true;
            }
        }

        return false;   // At this point: the vehicleType hasn't been found.
    }



    public static boolean is_valid_van_type_of_use(String vanTypeOfUse) 
    {
        int numOfVanTypesOfUse = VAN_TYPES_OF_USE.length;

        for(int i=0; i<numOfVanTypesOfUse; i++)
        {
            if( VAN_TYPES_OF_USE[i].equals(vanTypeOfUse) ) 
            {
                return true;
            }
        }

        return false;   // At this point: the vanTypeOfUse hasn't been found.
    }



    public static boolean is_valid_parking_size(int sizeInParkingSpaces) 
    {
        return (sizeInParkingSpaces>=VEHICLE_MIN_SIZE_IN_SPACES && 
                sizeInParkingSpaces<=VEHICLE_MAX_SIZE_IN_SPACES);
    }

    // public static boolean is_valid_parking_size(int[] parkingSpaces) 
    // {
    //     int sizeInParkingSpaces = parkingSpaces.length;
    //     return ( is_valid_parking_size(sizeInParkingSpaces) );
    // }



    public static boolean is_valid_parking_duration(int durationInHours)
    {
        return (durationInHours>=MIN_PARKING_HOURS && 
                durationInHours<=MAX_PARKING_HOURS);
    }
    


    public static boolean is_valid_license_plate(String licensePlate) 
    {
        // Έλεγχος μήκους: πρέπει να είναι ίσο με 7.
        if( licensePlate == null || 
            licensePlate.length() != 7) 
        {
            return false;
        }

        // Έλεγχος των 3 πρώτων χαρακτήρων: πρέπει να είναι κεφαλαία γράμματα.
        for(int i = 0; i < 3; i++) 
        {
            char currentCharacter = licensePlate.charAt(i);
            boolean isUppercaseLetter = (   currentCharacter>='A' &&
                                            currentCharacter<='Z');

            if(!isUppercaseLetter) 
            {
                return false;
            }
        }

        // Έλεγχος των 4 τελευταίων χαρακτήρων: πρέπει να είναι ψηφία.
        for (int i = 3; i < 7; i++) 
        {
            char currentCharacter = licensePlate.charAt(i);
            boolean isDigit = (  currentCharacter>='0' &&
                                        currentCharacter<='9');

            if(!isDigit) 
            {
                return false;
            }
        }

        // Σ' αυτό το σημείο: περάσαμε όλους τους ελέγχους, άρα ο αριθμός πινακίδας είναι έγκυρος.
        return true;
    }



    public static boolean is_valid_mobile_number(String mobileNumber) 
    {
        // Έλεγχος μήκους: πρέπει να είναι ίσο με 10.
        if( mobileNumber == null || 
            mobileNumber.length() != 10) 
        {
            return false;
        }

        // Έλεγχος αν ξεκινά με "69".
        if(!mobileNumber.startsWith("69"))
        {
            return false;
        }

        int numberLength = mobileNumber.length();

        // Έλεγχος αν όλοι οι χαρακτήρες είναι ψηφία.
        for(int i=0; i < numberLength; i++) 
        {
            char currentCharacter = mobileNumber.charAt(i);

            boolean isDigit = ( currentCharacter>='0' &&
                                currentCharacter<='9');

            if(!isDigit) 
            {
                return false;
            }
        }

        // Σ' αυτό το σημείο: περάσαμε όλους τους ελέγχους, άρα ο αριθμός του κινητού είναι έγκυρος.
        return true;
    }



    public static boolean is_valid_name(String name) 
    {
        if(name == null) 
        {
            return false;
        }

        String trimmedName = name.trim();
        if(trimmedName.length() < 2) 
        {
            return false;
        }
        
        int trimmedNameLength = trimmedName.length();
        for(int i=0; i<trimmedNameLength; i++)
        {
            char currentCharacter = trimmedName.charAt(i);

            boolean isUppercaseLetter = (currentCharacter >= 'A' && currentCharacter<= 'Z');
            boolean isLowercaseLetter = (currentCharacter >= 'a' && currentCharacter<= 'z');
            boolean isLetter = (isUppercaseLetter || isLowercaseLetter);

            // The character must be between the limits of the English alphabet.
            if(!isLetter) 
            {
                return false;
            }
        }

        return true;
    }



    public static boolean is_valid_number(String numString)
    {
        if(numString == null) 
        {
            return false;
        }

        String trimmedStr = numString.trim();
        if(trimmedStr.length() != 1) 
        {
            return false;
        }

        char firstChar = trimmedStr.charAt(0);
        boolean isNumber = (firstChar>='0' && firstChar<='9');
        if(!isNumber)
        {
            return false;
        }

        return true;    // At this point: all tests were successfull. So it's a number.
    }
	
} //END-of-class.
