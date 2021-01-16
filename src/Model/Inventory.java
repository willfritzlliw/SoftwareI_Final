package Model;

import java.util.ArrayList;

/**
 * Used to create and modify the current part and product inventory, contains adding and removing methods and searching methods for both parts and products.
 * A compatible feature would be the addition of an inventory warning flag that would lead to an alert message telling the user they need to restock the inventory.
 * @author William
 */
public class Inventory {
    
    private static ArrayList<Part> allParts = new ArrayList<Part>();
    private static ArrayList<Product> allProducts = new ArrayList<Product>();
    
    
    /**
     * adds a new part to the inventory
     * @param newPart
    */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    /** 
     * adds a new product to the inventory
     * @param newProduct 
    */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    /**
     * returns a part from the inventory based on ID
     * @param partId
     * @return Part
    */
    public static Part lookupPart(int partId){

        for(int x = 0; x < allParts.size(); x++){
           if(partId == allParts.get(x).getId()){
                return allParts.get(x);
           }
        }
        return null;
    }
    
    /**
     * returns a part index from the inventory based on ID
     * @param partId
     * @return Part
    */
     public static int lookupPartIndex(int partId){

        for(int x = 0; x < allParts.size(); x++){
           if(partId == allParts.get(x).getId()){
                return x;
           }
        }
        return -1;
    }
    
    /**
     * returns a product from the inventory based on ID
     * @param productId
     * @return
     */

    public static Product lookupProduct(int productId){
        
        for(int x = 0; x < allProducts.size(); x++){
           if(productId == allProducts.get(x).getId()){
               return allProducts.get(x);
           }
        }

        return null;
    }
    
    /**
     * returns a product index from the inventory based on ID
     * @param productId
     * @return
     */
    public static int lookupProductIndex(int productId){
        
        for(int x = 0; x < allProducts.size(); x++){
           if(productId == allProducts.get(x).getId()){
               return x;
           }
        }

        return -1;
    }
    
    /**
     * returns a list of parts from the inventory based on name
     * @param partName
     * @return Parts
    */
    public static ArrayList<Part> lookupPart(String partName){
        
        ArrayList<Part> releventParts = new ArrayList<>();
        
         for(int x = 0; x < allParts.size(); x++){
           if(allParts.get(x).getName().toLowerCase().contains(partName.toLowerCase())){
                releventParts.add(allParts.get(x));
           }
        }
        return releventParts;
    }
    
    /**
     * returns a list product from the inventory based on name
     * @param productName
     * @return Products
     */

    public static ArrayList<Product> lookupProduct(String productName){
        
        ArrayList<Product> releventProducts = new ArrayList<>();
        
         for(int x = 0; x < allProducts.size(); x++){
           if(allProducts.get(x).getName().toLowerCase().contains(productName.toLowerCase())){
                releventProducts.add(allProducts.get(x));
           }
        }
        return releventProducts;
    }
    
    /**
     * updates a part in the inventory
     * @param Index
     * @param selectedPart
     */
    public static void updatePart(int Index, Part selectedPart){
    
        allParts.remove(Index);
        allParts.add(Index, selectedPart);
    }
    
     /**
     * updates a product in the inventory
     * @param Index
     * @param newProduct
     */
    public static void updateProduct(int Index, Product newProduct){
    
        allProducts.remove(Index);
        allProducts.add(Index, newProduct);
    }
    
    /**
     * deletes a part in the inventory
     * @param selectedPart
     * @return isSuccesful
     */
    public static boolean deletePart(Part selectedPart){
        
        return allParts.remove(selectedPart);
    }
    
    /**
     * deletes a product in the inventory
     * @param selectedProduct
     * @return isSuccesful
     */
    public static boolean deleteProduct(Product selectedProduct){
    
        return allProducts.remove(selectedProduct);
    }
    
    /**
     * returns a list of all parts
     * @return Parts
     */
    public static ArrayList<Part> getAllParts(){
    
        return allParts;
    }  
    
     /**
     * returns a list of all products
     * @return Parts
     */
    public static ArrayList<Product> getAllProducts(){
    
        return allProducts;
    } 
    
}
