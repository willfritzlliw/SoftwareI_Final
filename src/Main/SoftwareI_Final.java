/**
 * An potential error I came across with this class was a failed FXMLloader, I fixed this by prompting the user with instructions on steps to take to fix the issue
 * 
 * A compatible feature that could be added to this class is a prompt allowing the user to choose weather they want a test set created or not
 */

package Main;

import Model.*;
import Logic.GenerateId;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;  

/**
 * Launches Inventory Management Program and builds a test inventory and sets the main view as the scene
 * @author William Fritz
 */
public class SoftwareI_Final extends Application {

    
    /**
     * Main method sets the base ID to 100 and calls the create test set method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GenerateId.setBaseID(100);
        createTestSet();
        launch(args);
    }
    
    /**
    *Creates the primary scene and sets the stage and checks for an error loading the FXML document which would prompt the user to contact the software company as that would be a fatal error for this program
    *@param primaryStage 
    */
    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/main_view.fxml"));
            Scene scene = new Scene(root, 900, 500);
        
            primaryStage.setScene(scene);
            primaryStage.setTitle("Inventory Managment System");
            primaryStage.show();
        }
        catch(Exception e){
                Alert error = new Alert(Alert.AlertType.ERROR);

                error.setTitle("Fatal Error");
                error.setHeaderText("Contact your product vendor at (123)-456-7891, with error code 1234");

                Optional<ButtonType> result = error.showAndWait();

                if(result.get() == ButtonType.OK){
                    System.exit(0);
                }
        }    
    }
    
    /**
     * Builds test set of parts and products and adds to inventory, This allow the software designer to build a test set, this code would be removed on the production build
     */
    private static void createTestSet() {
        //creating parts and products
        InHouse part1 = new InHouse(GenerateId.GenterateNewId(),"Cat 5 Cable",50,10,1,100,225);
        InHouse part2 = new InHouse(GenerateId.GenterateNewId(),"Ethernet Cable",20,25,2,200,555);
        InHouse part3 = new InHouse(GenerateId.GenterateNewId(),"USB Cable",10,70,5,250,255);
        Outsourced part4 = new Outsourced(GenerateId.GenterateNewId(),"Raspberry Pi",40,50,1,100,"Pi Foundation");
        Outsourced part5 = new Outsourced(GenerateId.GenterateNewId(),"Arduino",35,40,1,100,"Adafruit");
        
        Product prod1 = new Product(GenerateId.GenterateNewId(),"Pi Bundle",99,30,1,200);
        prod1.addAssociatedPart(part4);
        prod1.addAssociatedPart(part2);
        prod1.addAssociatedPart(part3);
        
        Product prod2 = new Product(GenerateId.GenterateNewId(),"Arduino Bundle",89,20,1,200);
        prod2.addAssociatedPart(part5);
        prod2.addAssociatedPart(part2);
        prod2.addAssociatedPart(part3);
        
        //adding parts and products to Inventory
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        
        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        
    }
    
}
