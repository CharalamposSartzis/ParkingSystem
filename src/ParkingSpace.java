
public class ParkingSpace 
{
    private int id;
    private boolean isElectric;
    private boolean isOccupied;



    public ParkingSpace(int id, boolean isElectric) 
    {
        this.id = id;
        this.isElectric = isElectric;
        this.isOccupied = false;
    }



    public int getId() {
        return id;
    }

    public boolean isElectric() {
        return isElectric;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }



    @Override
    public String toString() 
    {
        return "\n\tspace [id= " + id + ": isElectric= " + isElectric + ", isOccupied= "+ isOccupied + "]";
    }
}
