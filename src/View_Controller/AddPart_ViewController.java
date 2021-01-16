package View_Controller;

import Logic.*;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class for the add part view, has format and bounds checking functionality also forces user to confirm when they click the cancel button
 * which gives the user the option to save work if the button is clicked by mistake. 
 * A runtime error that was encountered and corrected in this controller was in the addPart method. A part was being created even though there 
 * were input errors. I used the Boolean allGood to solve this problem, if anything was entered incorrectly I set allGood to false
 * 
 * A compatible feature that could be added to this view is the ability to assign this part to products and have those products be associated to the part automatically

 * @author William
 */
public class AddPart_ViewController implements Initializable {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private ToggleGroup inOut;
    @FXML
    private RadioButton outsourceRadio;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField machineIdField;
    @FXML
    private TextField minFeild;
    
    private int lastId;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField invField;
    @FXML
    private Label typeText;
    
    
    /**
     * Initializes the add part view by adding radio buttons to a Togglegroup and sets the part ID to what the next ID would be
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inOut = new ToggleGroup();
        lastId = GenerateId.getCurrentID();
        lastId++;
        idField.setText(Integer.toString(lastId));
        
        
    }    
    
    /**
     * Asks user if they are sure they want to exit and if the user chooses yes the method will cancel the part addition
     * @param E 
     */
   @FXML
    public void cancelPart(ActionEvent E){
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        
        confirm.setTitle("Confirm Cancel");
        confirm.setHeaderText("Are you sure you want to cancel?");
        
        Optional<ButtonType> result = confirm.showAndWait();
        
        if(result.get() == ButtonType.OK){
            changeScreen(E);
        }
        else{
            confirm.close();
        } 
    } 
    
    /**
     * Adds new part to the inventory based on weather InHouse or Outsourced radio is selected, catches multiple Number Formate Exceptions 
     * and displays error message to user if they entered invalid information. A runtime error that was encountered and corrected in this code
     * was a part being created even though there were input errors. I used the Boolean allGood to solve this problem.
     * @param E 
     */
    @FXML
    public void addPart(ActionEvent E){
        if(InHouseRadio.isSelected()){
            Boolean allGood = true;
            InHouse newPart = new InHouse();
            
            errorLabel.setText(" ");
           
            try{
                newPart.setPrice(Double.parseDouble(priceField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Price must be a double");
            }
            
            try{
                newPart.setStock(Integer.parseInt(invField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Inventory must be a Integer");
            }
            
            try{
                newPart.setMin(Integer.parseInt(minFeild.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Min inventory must be a Integer");
            }
            
            try{
                newPart.setMax(Integer.parseInt(maxField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Max inventory must be a Integer");
            }
            
            try{
                newPart.setMachineId(Integer.parseInt(machineIdField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("MachineID must be a Integer");
            }
            
            if(newPart.getMax()< newPart.getMin())
            {
                allGood = false;
                errorLabel.setText("Max must be more than or equal to min");
            }
            
            if(newPart.getStock() > newPart.getMax() || newPart.getStock() < newPart.getMin()){
                allGood = false;
                errorLabel.setText("Inventory must be inbetween min and max inventory");
            }
            
            if(allGood){
                newPart.setId(GenerateId.GenterateNewId());
                newPart.setName(nameField.getText());
                
                Inventory.addPart(newPart);
                
                changeScreen(E);
            }
   
        }
        
        if(outsourceRadio.isSelected()){
            Boolean allGood = true;
            Outsourced newPart = new Outsourced();
            
            errorLabel.setText(" ");
           
            try{
                newPart.setPrice(Double.parseDouble(priceField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Price must be a double");
            }
            
            try{
                newPart.setStock(Integer.parseInt(invField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Inventory must be a Integer");
            }
            
            try{
                newPart.setMin(Integer.parseInt(minFeild.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Min inventory must be a Integer");
            }
            
            try{
                newPart.setMax(Integer.parseInt(maxField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Max inventory must be a Integer");
            }
            
            if(newPart.getMax()< newPart.getMin())
            {
                allGood = false;
                errorLabel.setText("Max must be more than or equal to min");
            }
            
            if(newPart.getStock() > newPart.getMax() || newPart.getStock() < newPart.getMin()){
                allGood = false;
                errorLabel.setText("Inventory must be inbetween min and max inventory");
            }
            
            
            if(allGood){
                newPart.setId(GenerateId.GenterateNewId());
                newPart.setName(nameField.getText());
                newPart.setCompanyName(machineIdField.getText());
                
                Inventory.addPart(newPart);
                
                changeScreen(E);
            }
        }
    }
    
    /**
     * Detects if the part is in-house or outsourced based on which radio button is selected, then changes the label to match the type of part
     */
    public void setType(){
        if(outsourceRadio.isSelected()){
            typeText.setText("Company Name");
            machineIdField.setPromptText("Type Company Name");
        }
        else{
            typeText.setText("Machine ID");
            machineIdField.setPromptText("Type Machine ID");
        }
    }
    
    /**
     * Changes sene back the main screen and used the passed ActionEvent to get the correct stage object, an error that was corrected 
     * in this method is catching an exception caused by a failed FXMLLoader 
     * @param event 
     */
    private void changeScreen(ActionEvent event){
            
            try{
                Parent mainView = FXMLLoader.load(getClass().getResource("/View_Controller/main_view.fxml"));

                Scene mainViewScene = new Scene(mainView, 900,500);

                Stage mainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();

                mainWindow.setScene(mainViewScene);
                mainWindow.show();
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
}
