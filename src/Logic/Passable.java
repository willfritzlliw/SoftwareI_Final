package Logic;

import Model.Part;
import Model.Product;

/**
 * static methods used to pass a part or a product between scenes, stores a part and a product and has getters and setters
 * A compatible feature I could add to this code is the ability to pass an associated parts list so one product could send it to another
 * @author William
 */
public class Passable {
    
    private static Part partId = null;
    
    private static Product productId = null;

    /**
    *returns saved Part because it is static it will allow a part to be passed with ease
    *@return partId 
    */
    public static Part getPartId() {
        return partId;
    }

    /**
    *returns saved Product, because it is static it will allow a product to be passed with ease
    *@return productId 
    */
    public static Product getProductId() {
        return productId;
    }

    /**
    *sets saved Part, because it is static it will allow a part to be set with ease
    *@param partId 
    */
    public static void setPartId(Part partId) {
        Passable.partId = partId;
    }

    /**
    *sets saved Product, because it is static it will allow a product to be set with ease
    *@param productId 
    */
    public static void setProductId(Product productId) {
        Passable.productId = productId;
    }
    
    
    
}
