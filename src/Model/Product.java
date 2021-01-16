package Model;

import java.util.ArrayList;

/**
 * Used to create a product with a full and empty constructor and allow for data manipulation via setters and getter methods.
 * A compatible feature that could be added to this code is the ability to add a full list of parts to the associated parts list.
 * @author William Fritz
 */
public class Product {
    
    private ArrayList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
   
     /**
     * Full Product constructor, creates a product with all values filled with appropriate data
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price,int stock,int min,int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        associatedParts = new ArrayList<>(); 
    }
    
     /**
     * Empty Product constructor, creates a product with no values filled in when a full constructor is not needed 
     */
    public Product(){
        id = 0;
        name = " ";
        price = 0;
        stock = 0;
        min = 0;
        max = 1;
        associatedParts = new ArrayList<>();   
    }
    
    //Setters
    
     /**
     * Sets Product Id using passed value
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
     /**
     * Sets Product Name using passed value
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets Product Price using passed value
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets Product Current Stock using passed value
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * Sets Product Min Stock using passed value
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * Sets Product Max Stock using passed value
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    
    //Getters
    
    /**
     * Gets Product Id value from object and returns it to the calling method
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Gets Product Name value from object and returns it to the calling method
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Gets Product Price value from object and returns it to the calling method
     * @return 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets Product Current Stock value from object and returns it to the calling method
     * @return 
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets Product Min Stock value from object and returns it to the calling method
     * @return 
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets Product Max Stock value from object and returns it to the calling method
     * @return 
     */
    public int getMax() {
        return max;
    }
    
    //assosiated parts processing
    
    /**
     * Adds Associated Part to Products List of Parts
     * @param part
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    /**
     * Adds All Parts From a List to the Associated Parts List
     * @param parts 
     */
    public void addAllAssociatedParts(ArrayList<Part> parts){
        associatedParts.addAll(parts);
    }
    
    /**
     * Deletes Associated Part from Products List of Parts
     * @param selectedAssociatedPart
     * @return Boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
       return associatedParts.remove(selectedAssociatedPart);
    }
    
    /**
     * Gets all Associated Part from Products List of Parts
     * @return ArrayList of Parts
     */
    public ArrayList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
