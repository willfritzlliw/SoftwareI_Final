
package View_Controller;

import Logic.GenerateId;
import Logic.Passable;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
 * FXML Controller class for the add product view, has bounds and format checking for input and makes user confirm cancel
 * A runtime error I encountered in this controller class was in the changeScreen method. The FXMLLoader failed due to a misspelled filename,
 * I corrected this by catching an exception caused by a failed FXMLLoader and presenting a error message to the user with instructions.
 * 
 * A compatible feature that could be added to this view is the ability to mulit-select parts in the all parts table and add them all to the associated parts list
 * @author William
 */
public class AddProduct_ViewController implements Initializable {

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
     * Initializes the controller class, and sets the new product ID
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsView = FXCollections.observableList(Inventory.getAllParts());
        workingProd = new Product();
        addedPartsView = FXCollections.observableList(workingProd.getAllAssociatedParts());
        
        idField.setText(Integer.toString((GenerateId.getCurrentID() + 1)));
         
        partIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
  
        addpartIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        addpartNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        addpartInventoryCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        addpartPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        
        fillTable();
    }    
    
    /**
     * fills the Table Views with inventory data, allow for ease of table fills for future updates
     */
    public void fillTable(){
        allPartTable.getItems().addAll(partsView);
    }
    
    /**
     * used to re-fill the Table Views in order to reflect any changes made by the user, allows for ease of use for future updates
     */
    public void reFillTables(){
        allPartTable.getItems().setAll(partsView);
        addedPartTable.getItems().setAll(addedPartsView);
    }
    
    /**
     * Asks user if they are sure they want to cancel the product creation and if they choose yes this method will exit the current screen and not add any product to the inventory
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
     * used to add a part to the list of the products associated parts, also checks if a part is selected and presents and error message if it is not
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
     * used to remove a part to the list of associated parts, also checks if a part is selected and presents and error message if it is not
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
     * adds created product to inventory, catches multiple Number Formate Exceptions and displays error message to user
     * @param event 
     */
    @FXML
    public void addProduct(ActionEvent event){
        
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
                workingProd.setId(GenerateId.GenterateNewId());
                workingProd.setName(nameField.getText());
                
                Inventory.addProduct(workingProd);
                
                changeScreen(event);
            }
    }
    
    /**
     * Sorts the All Parts Table View, alerts user if there is no part that matches search also catches Number Format Exception
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
     * changes scene back to the main screen, an error that was corrected in this method is catching an exception caused by a failed FXMLLoader. Once caught the method will 
     * prompt the user to contact the software company if there is an error loading the scene
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
