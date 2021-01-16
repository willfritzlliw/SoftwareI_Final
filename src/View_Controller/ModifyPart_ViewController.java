
package View_Controller;

import Logic.GenerateId;
import Logic.Passable;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
 * FXML Controller class for the modify part view, auto fills the text fields with the selected parts data and performs format and bounds checks
 * A logical error that I encountered while coding this controller class was in the Initialize method.
 * and InHouse and Outsourced part type is not known yet. I corrected this by casting the passed part to an InHouse and if an error was 
 * thrown then I knew it was an outsourced part.
 * 
 * A compatible feature that could be added for this view is the ability to add a bar code to the part or a serial number that is associated with the part
 * @author William
 */
public class ModifyPart_ViewController implements Initializable {

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
     * Initializes the controller class, and sets all the fields to the values of the part selected for modification. A logical error that I encountered while coding this method was
     * InHouse and Outsourced part type being unknown. I corrected this by casting the passed part to an InHouse and if an error was thrown then I knew it was an outsourced part 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lastId = Passable.getPartId().getId();
        Part part = Passable.getPartId();
        inOut = new ToggleGroup();
        idField.setText(Integer.toString(lastId));
        nameField.setText(part.getName());
        priceField.setText(Double.toString(part.getPrice()));
        maxField.setText(Integer.toString(part.getMax()));
        minFeild.setText(Integer.toString(part.getMin()));
        invField.setText(Integer.toString(part.getStock()));
        
        InHouse isInHouse;
        Outsourced isOutSourced;
        try{
            isInHouse = (InHouse)part;
            InHouseRadio.setSelected(true);
            typeText.setText("Machine ID");
            machineIdField.setText(Integer.toString(isInHouse.getMachineId()));
        }
        catch(Exception e){
            isOutSourced = (Outsourced)part;
            outsourceRadio.setSelected(true);
            typeText.setText("Company Name");
            machineIdField.setText(isOutSourced.getCompanyName());
        }
    }    
    
    /**
     * Asks user if they are sure they want to exit and if the user selects yes cancels part modification without saving changes
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
     * adds newly modified part to the inventory, catches multiple Number Formate Exceptions and displays error message to user. Replaces old part with newly created part information
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
                newPart.setId(lastId);
                newPart.setName(nameField.getText());
                
                Inventory.updatePart(Inventory.lookupPartIndex(lastId), newPart);
                
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
                newPart.setId(lastId);
                newPart.setName(nameField.getText());
                newPart.setCompanyName(machineIdField.getText());
                
                Inventory.updatePart(Inventory.lookupPartIndex(lastId), newPart);
 
                
                changeScreen(E);
            }
        }
    }
    
    /**
     * detects if the part is in-house or outsourced and changes the labels text display between machine ID and company name
     */
    public void setType(){
        if(outsourceRadio.isSelected()){
            typeText.setText("Company Name");
            machineIdField.setText("");
            machineIdField.setPromptText("Type Company Name");
        }
        else{
            typeText.setText("Machine ID");
            machineIdField.setText("");
            machineIdField.setPromptText("Type Machine ID");
        }
    }
    
    /**
     * changes sene back the main screen, an error that was corrected in this method is catching an exception caused by a failed FXMLLoader 
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
