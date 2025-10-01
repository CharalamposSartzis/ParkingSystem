
public class Receipt
{
    private Parking parking;
    private int cost;



    public Receipt(Parking parking)
    {
        this.parking = parking;
        this.cost = calculateCost();
    }



    public Parking getParking()
    {
        return parking;    
    }

    public int getCost()
    {
        return cost;    
    }



    @Override
    public String toString()
    {
        return "\nReceipt's info: (receiptCost=" + getCost() + "), ";
    }



    private int calculateCost() 
    {
        int durationInHours = parking.getDurationInHours();

        if(!Util.is_valid_parking_duration(durationInHours))      // At the parking area: the valid parking duration is between 1 and 24 hours.
        {
            return -1;
        }

        if(durationInHours>=Util.MIN_PARKING_HOURS && durationInHours<=3)
        {
            return 5;
        }
        else if(durationInHours>=4 && durationInHours<=8)       // Usage of "if" only, is OK, since we RETURN the corresponding value in each case. 
        {                                                       // We keep "else-if", to maintain the logic, in case of changes in the actions of each case.
            return 8;
        }
        else if(durationInHours>=9 && durationInHours<=23)
        {
            return 12;
        }
        else if(durationInHours==Util.MAX_PARKING_HOURS)    
        {
            return 15;
        }
        else
        {
            return 0;
        }
    }

} //END-of-class.