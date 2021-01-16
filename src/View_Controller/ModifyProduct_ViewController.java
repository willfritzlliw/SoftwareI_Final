
package View_Controller;

import Logic.GenerateId;
import Logic.Passable;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class for the Modify Product View, loads the products associated parts and fills in all the text fields with the products details
 * A runtime error that was corrected in this controller class was in the changeScreen method. This fix was catching an exception caused by a failed FXMLLoader and displaying 
 * an error message with instructions that can lead to the program being fixed
 * 
 * A compatible feature for this view would be the ability to save custom part adds for a special offer such as adding extra parts to a product during the holidays
 * @author William
 */
public class ModifyProduct_ViewController implements Initializable {

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invFeild;
    @FXML
    private TextField priceField;
    @FXML
    private TextField minField;
    @FXML
    private TableView<Part> allPartTable;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Part> addedPartTable;
    @FXML
    private TableColumn<Part, Integer> addpartIdCol;
    @FXML
    private TableColumn<Part, String> addpartNameCol;
    @FXML
    private TableColumn<Part, Integer> addpartInventoryCol;
    @FXML
    private TableColumn<Part, Double> addpartPriceCol;
    @FXML
    private Button addPart;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private ObservableList<Part> partsView;
    private ObservableList<Part> addedPartsView;
    private Product workingProd;
    
    @FXML
    private Button removePartBTN;
    @FXML
    private TextField maxField;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField prodSearch;
    
    /**
     * Initializes the controller class, sets up the Table Views value factory to match variable names, fills in the text fields with the products values
     * and gets the products associated parts
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsView = FXCollections.observableList(Inventory.getAllParts());
        workingProd = new Product(Passable.getProductId().getId(),Passable.getProductId().getName(), Passable.getProductId().getPrice(), Passable.getProductId().getStock(),Passable.getProductId().getMin(),Passable.getProductId().getMax());
        workingProd.addAllAssociatedParts(Passable.getProductId().getAllAssociatedParts());
        addedPartsView = FXCollections.observableList(workingProd.getAllAssociatedParts());
        
        idField.setText(Integer.toString(Passable.getProductId().getId()));
         
        partIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
  
        addpartIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        addpartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        addpartInventoryCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        addpartPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        
        //filling in fields
        
        idField.setText(Integer.toString(workingProd.getId()));
        nameField.setText(workingProd.getName());
        priceField.setText(Double.toString(workingProd.getPrice()));
        invFeild.setText(Integer.toString(workingProd.getStock()));
        minField.setText(Integer.toString(workingProd.getMin()));
        maxField.setText(Integer.toString(workingProd.getMax()));
        
        fillTable();
    }    
    
    /**
     * Fills the Table Views with inventory data, allows for ease of use for any future updates
     */
    public void fillTable(){
        allPartTable.getItems().addAll(partsView);
        addedPartTable.getItems().addAll(addedPartsView);
    }
    
    /**
     * Used to re-fill the Table Views in order to reflect any changes made by the user, allow for ease of use for any future updates
     */
    public void reFillTables(){
        allPartTable.getItems().setAll(partsView);
        addedPartTable.getItems().setAll(addedPartsView);
    }
    
    /**
     * Asks user if they are sure they want to cancel and if they choose yes they will exit the screen without saving any modifications
     * @param E 
     */
    @FXML
    public void cancelProduct(ActionEvent E){
        
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
     * Used to add a part to the list of associated parts, checks if there is in fact a part selected and displays and error message if one is not
     */
    @FXML 
    public void addPartToList()
    {
        if(allPartTable.getSelectionModel().getSelectedItem() ==  null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must have a selected part");
            alert.setContentText("in order to add an item you must select it in the table first");

            alert.showAndWait();
        }
        else{
            workingProd.addAssociatedPart(allPartTable.getSelectionModel().getSelectedItem());
            reFillTables();
        }
    }
    
     /**
     * Used to remove a part from the list of associated parts, checks if there is in fact a part selected and displays and error message if one is not
     */
    @FXML 
    public void removePartFromList()
    {
        if(addedPartTable.getSelectionModel().getSelectedItem() ==  null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must have a selected part");
            alert.setContentText("in order to remove an item you must select it in the table first");

            alert.showAndWait();
        }
        else{
            workingProd.deleteAssociatedPart(addedPartTable.getSelectionModel().getSelectedItem());
            reFillTables();
        }
    }
    
    /**
     * Modifies product in the inventory, catches multiple Number Formate Exceptions and displays error message to user if formate is wrong
     * @param event 
     */
    @FXML
    public void modProduct(ActionEvent event){
        
             Boolean allGood = true;
            errorLabel.setText(" ");
           
            try{
                workingProd.setPrice(Double.parseDouble(priceField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Price must be a double");
            }
            
            try{
                workingProd.setStock(Integer.parseInt(invFeild.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Inventory must be a Integer");
            }
            
            try{
                workingProd.setMin(Integer.parseInt(minField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Min inventory must be a Integer");
            }
            
            try{
                workingProd.setMax(Integer.parseInt(maxField.getText()));
            }
            catch(NumberFormatException e){
                 allGood = false;
                 errorLabel.setText("Max inventory must be a Integer");
            }
            
            if(workingProd.getMax()< workingProd.getMin())
            {
                allGood = false;
                errorLabel.setText("Max must be more than or equal to min");
            }
            
            if(workingProd.getStock() > workingProd.getMax() || workingProd.getStock() < workingProd.getMin()){
                allGood = false;
                errorLabel.setText("Inventory must be inbetween min and max inventory");
            }
            
            
            if(allGood){
                workingProd.setName(nameField.getText());
                
                Inventory.updateProduct(Inventory.lookupProductIndex(workingProd.getId()), workingProd);
                
                changeScreen(event);
            }
    }
    
    /**
     * Sorts the All Parts Table View, alerts user if there is no part that matches the text, also catches Number Format Exception
     */
    @FXML
    public void searchPart(){
        int id = -1;
        try{
            id = Integer.parseInt(prodSearch.getText());
        }
        catch (NumberFormatException e){
            id = -1;
        }
        
        if(prodSearch.getText().equals("")){
            reFillTables();
        }
        else if(id != -1){
            allPartTable.getItems().setAll(Inventory.lookupPart(id));
        }
        else if(Inventory.lookupPart(prodSearch.getText()).isEmpty())
        {
            Alert error = new Alert(Alert.AlertType.ERROR);

            error.setTitle("Error");
            error.setHeaderText("part not found");
            Optional<ButtonType> result = error.showAndWait();

            if(result.get() == ButtonType.OK){
                    error.close();
             }
            reFillTables();
        }
        else{
            allPartTable.getItems().setAll(FXCollections.observableArrayList(Inventory.lookupPart(prodSearch.getText())));
        }
        
    }
    
    /**
     * Changes scene back to the main screen, an error that was corrected in this method is catching an exception caused by a failed FXMLLoader. This 
     * error was solved by adding an alert that instructs the user to contact the customer support of the software company
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
