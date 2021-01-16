package Model;

/**
 * Used to create In House parts with a full and empty constructor and allow for data manipulation via setters and getter methods.
 * A compatible feature for this code would be the ability to add a branch location/name that makes the part, assuming the company would have multiple locations when it grows.
 * @author William Fritz
 */
public class InHouse extends Part {
    
    private int machineId;
    
     /**
     * Full In House constructor, creates an InHouse object with all values filled with appropriate data
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id,name,price,stock,min,max);
        this.machineId = machineId; 
    }
    
     /**
     * Empty In House constructor, creates an InHouse object with no values filled in when a full constructor is not needed 
     */
    public InHouse(){
        super();
        machineId = 0;
    }
    /**
     * Returns Machine Id value from object and returns it to the calling method
     * @return 
     */
    public int getMachineId() {
        return machineId;
    }
     /**
     * Sets machine Id using passed value
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    
    
}
