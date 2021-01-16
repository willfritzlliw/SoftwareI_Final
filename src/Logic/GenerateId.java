package Logic;

/**
 * Generates Unique Id, returns current ID, and allows the base ID to be set to a specific number
 * A logic error I came across was trying to display the ID of a newly created part or product without incrementing the current ID of this class, I solved this issue by 
 * adding the getCurentID method which does not increment the ID
 * 
 * A compatible feature that could be added to this class would be a different ID being created for either a product or a part
 * @author William
 */
public class GenerateId {
    
    private static int LastId;
    
    /**
    *returns new unique Id allow for unique Id and because it is static it will allow project scaling without confusing code
    *@return lastId 
    */
    public static int GenterateNewId(){
        return ++LastId;
    }
    
    /**
    *returns current unique Id, because it is static it will allow project scaling without confusing code. Solves the error of a new part or product 
    * being started and would have incremented the ID, if not for this method, but never saving the item in the inventory 
    *@return lastId 
    */
    public static int getCurrentID(){
        return LastId;
    }
    
    /**
    *Sets base number for all new unique Ids, allows for the software designer to set a different base for each company he sells this program to
    *@param base 
    */
    public static void setBaseID(int base){
        LastId = base;
    }
}
