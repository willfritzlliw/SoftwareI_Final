package View_Controller;

import Logic.Passable;
import Model.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML for the main view scene, contains delete functionality and can change scenes for add and modify. one problem I encountered in this code was differentiating 
 * between an Id and a name search as they are different data types. I solved this problem by using a try and catch statement to see if the input is able to be parsed to an Integer.
 * 
 * A compatible feature that I could add to this view is the ability to add parts to a product by selecting parts and a product and using a button add to the main view. This would 
 * speed up the modification process. 
 * @author William
 */
public class Main_viewController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML
    private TextField partSearch;
    @FXML
    private Button addPart;
    @FXML
    private Button modifyPart;
    @FXML
    private Button deletePart;
    @FXML
    private TextField productSearch;
    @FXML
    private Button addProduct;
    @FXML
    private Button modifyProduct;
    @FXML
    private Button deleteProduct;
    @FXML
    private Button exitButton;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableView<Product> productTable;
    
    private ObservableList<Part> partsView;
    private ObservableList<Product> productsView;
    
    /**
     * Initializes the controller class, and sets the table views value factory to the appropriate naming convention and call the fillTable method
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsView = FXCollections.observableList(Inventory.getAllParts());
        productsView = FXCollections.observableList(Inventory.getAllProducts());
         
        partIdCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        productIdCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productNameCol.setCellValueFactory( new PropertyValueFactory<Product, String>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
         
         fillTable();
    }    
    
    /**
     * Asks user if they are sure they want to exit and if yes will exit the program
     */
    public void exit(){
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        
        confirm.setTitle("Confirm Exit");
        confirm.setHeaderText("Are you sure you want to exit?");
        
        Optional<ButtonType> result = confirm.showAndWait();
        
        if(result.get() == ButtonType.OK){
            System.exit(0);
        }
        else{
            confirm.close();
        }  
    }
    
    /**
     * fills the Table Views with inventory data used to initially set up the tables, this method is intended to be called by the initialize method
     */
    public void fillTable(){
        partTable.getItems().addAll(partsView);
        productTable.getItems().addAll(productsView);
    }
    
    /**
     * used to re-fill the Table Views, using the set all method that is part of and Observable List, in order to reflect any changes made by the user
     */
    public void reFillTables(){
        partTable.getItems().setAll(partsView);
        productTable.getItems().setAll(productsView);
    }
    
    /**
     * sets the part table via passed part, if part passed is null an alert message will be displayed
     * @param partLookup 
     */
    public void partTableLookupID(Part partLookup){
         if(partLookup == null)
        {
            Alert error = new Alert(Alert.AlertType.ERROR);

            error.setTitle("Error");
            error.setHeaderText("Part not found");
            Optional<ButtonType> result = error.showAndWait();

            if(result.get() == ButtonType.OK){
                    error.close();
             }
            reFillTables();
        }
        else{
            partTable.getItems().setAll(partLookup);
         }
    }
    
    /**
     * sets parts via part list and displays error message if there is no part that matches search
     * @param partLookup 
     */
    public void partTableLookupName(ObservableList<Part> partLookup){
        if(partLookup.isEmpty())
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
            partTable.getItems().setAll(partLookup);
        }
    }
    
    /**
     * sets product table to a single product based off of an ID via passed product, if product passed is null an alert message will be displayed
     * @param productLookup
     */
    public void productTableLookupID(Product productLookup){
        if(productLookup == null)
        {
            Alert error = new Alert(Alert.AlertType.ERROR);

            error.setTitle("Error");
            error.setHeaderText("Product not found");
            Optional<ButtonType> result = error.showAndWait();

            if(result.get() == ButtonType.OK){
                    error.close();
             }
            reFillTables();
        }
        else{
             productTable.getItems().setAll(productLookup);
        }
    }
    
    /**
     * sets products via product list and alerts the user if there is no product that matches the search
     * @param productLookup
     */
    public void productTableLookupName(ObservableList<Product> productLookup){
        
        if(productLookup.isEmpty())
        {
            Alert error = new Alert(Alert.AlertType.ERROR);

            error.setTitle("Error");
            error.setHeaderText("product not found");
            Optional<ButtonType> result = error.showAndWait();

            if(result.get() == ButtonType.OK){
                    error.close();
             }
            reFillTables();
        }
        else{
            productTable.getItems().setAll(productLookup);
        }
    }
    
    /**
     * Asks user if they are sure they want to delete part, tests if part is selected and deletes the part
     */
    @FXML
    public void deletePart(){
        if(partTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must have a selected part");
            alert.setContentText("in order to delete and item you must select it in the table first");

            alert.showAndWait();
        }
        else{
            Alert confirm = new Alert(AlertType.CONFIRMATION);

            confirm.setTitle("Confirm Deletion");
            confirm.setHeaderText("Are you sure you want to delete part?");

            Optional<ButtonType> result = confirm.showAndWait();

            if(result.get() == ButtonType.OK){
                Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
                reFillTables();
            }
            else{
                confirm.close();
            } 
        }
    }
    
    /**
     * Asks user if they are sure they want to delete product, tests if part is selected and deletes the product
     */
    @FXML
    public void deleteProduct(){
        if(productTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must have a selected product");
            alert.setContentText("in order to delete and item you must select it in the table first");

            alert.showAndWait();
        }else if(!productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selected product has a part or parts associated with it ");
            alert.setContentText("in order to delete item there must be no parts associated with it");

            alert.showAndWait();
        }
        else{
            Alert confirm = new Alert(AlertType.CONFIRMATION);

            confirm.setTitle("Confirm Deletion");
            confirm.setHeaderText("Are you sure you want to delete product?");

            Optional<ButtonType> result = confirm.showAndWait();

            if(result.get() == ButtonType.OK){
                Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
                reFillTables();
            }
            else{
                confirm.close();
            } 
        }
    }
    
    
    /**
     * Searches for a part based off of ID or name, checks if input is able to be parsed and catches Number Formate Exception if not. Then proceeds with word search
     * I used a try and catch statement as a way to test if the number was an ID or a name search
     */
    @FXML
    public void searchPart(){
        int id = -1;
        
        //test if the number was an ID or a name search
        try{
            id = Integer.parseInt(partSearch.getText());
        }
        catch (NumberFormatException e){
            id = -1;
        }
        
        if(partSearch.getText().equals("")){
            reFillTables();
        }
        else if(id != -1){
            partTableLookupID(Inventory.lookupPart(id));
        }
        else
        {
            partTableLookupName(FXCollections.observableArrayList(Inventory.lookupPart(partSearch.getText())));
        }
        
    }
    
    /**
     * Searches for a product based off of ID or name, checks if input is able to be parsed and catches Number Formate Exception if not. Then proceeds with word search
     * I used a try and catch statement as a way to test if the number was an ID or a name search
     */
    @FXML
    public void searchProduct(){
        int id = -1;
        
        //test if the number was an ID or a name search
        try{
            id = Integer.parseInt(productSearch.getText());
        }
        catch (NumberFormatException e){
            id = -1;
        }
        
        if(productSearch.getText().equals("")){
            reFillTables();
        }
        else if(id != -1){
            productTableLookupID(Inventory.lookupProduct(id));
        }
        else
        {
            productTableLookupName(FXCollections.observableArrayList(Inventory.lookupProduct(productSearch.getText())));
        }
        
    }
    
    /**
     * changes scene to add Part View, uses the ActionEvent passed into it to identify the stage
     * @param event 
     */
    @FXML
    public void addPartView(ActionEvent event){
        
        changeScene("/View_Controller/AddPart_View.fxml", event);
    } 
    
    /**
     * tests if part is selected and changes scene to Modify Part Screen, uses the ActionEvent passed into it to identify the stage
     * @param event 
     */
    @FXML
    public void modifyPartView(ActionEvent event){
        
        if(partTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must have a selected part");
            alert.setContentText("in order to modify and item you must select it in the table first");

            alert.showAndWait();
        }
        else{
        
            Passable.setPartId(partTable.getSelectionModel().getSelectedItem());
            
            changeScene("/View_Controller/modifyPart_View.fxml", event);
        }
    }
    
    /**
     * changes screen to Add Product View, uses the ActionEvent passed into it to identify the stage
     * @param event 
     */
    @FXML
    public void addProductView(ActionEvent event){
            changeScene("/View_Controller/addProduct_View.fxml", event);
        }
    
    /**
     * tests if product is selected and changes scene to Modify Product View, uses the ActionEvent passed into it to identify the stage
     * @param event 
     */
    @FXML
    public void modProductView(ActionEvent event){
        if(productTable.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must have a selected product");
            alert.setContentText("in order to modify and item you must select it in the table first");

            alert.showAndWait();
        }
        else{
        
            Passable.setProductId(productTable.getSelectionModel().getSelectedItem());
            
            changeScene("/View_Controller/modifyProduct_View.fxml", event);
        }
    }
    
    
    /**
     * Used to change a scene based off of passed path and event, an error that was corrected in this method is catching an exception caused by a failed FXMLLoader 
     * @param path
     * @param event 
     */
    private void changeScene(String path, ActionEvent event){
        try{
               Parent modView = FXMLLoader.load(getClass().getResource(path));

               Scene addViewScene = new Scene(modView);

               Stage addWindow = (Stage)(((Node)event.getSource()).getScene().getWindow());

               addWindow.setScene(addViewScene);
               addWindow.show();
            }
            catch(Exception e)
            {
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
