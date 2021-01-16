package Model;

/**
 * Used to create outsourced parts with a full and empty constructor and allow for data manipulation via setters and getter methods.
 * A compatible feature would be the addition of a company contact information field so the user can call the company for information.
 * @author William Fritz
 */
public class Outsourced extends Part{
    private String companyName;
    
     /**
     * Full Outsourced constructor, creates an Outsourced object with all values filled with appropriate data
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param companyName
     * @param max
     * @param min
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
    
        super(id,name,price,stock,min,max);
        this.companyName = companyName;
    }
     /**
     * Empty Outsourced constructor, creates an Outsourced object with no values filled in when a full constructor is not needed 
     */
    public Outsourced(){
        super();
        companyName = " ";
    }
    
     /**
     * Sets Company Name using passed value
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
     /**
     * Returns Company Name value from object and returns it to the calling method
     * @return 
     */
    public String getCompanyName() {
        return companyName;
    }
    
    
}
