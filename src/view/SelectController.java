/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ItemController;
import controller.SupplierController;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Customer;
import model.Item;
import model.Supplier;
import view.orders.FrmPurchaseOrderController;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class SelectController implements Initializable {

    @FXML
    private ChoiceBox<String> chbProperty;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<TableRaw> list;

    
    @FXML
    private TableColumn<TableRaw, String> clmID;
    @FXML
    private TableColumn<TableRaw, String> clmName;
    
    
    Class findingClass;
    FrmPurchaseOrderController controller;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    

    
    public void initData(FrmPurchaseOrderController controll, Class findingClass){
        this.findingClass = findingClass;
        this.controller = controll;
        chbProperty.getItems().setAll(FXCollections.observableList(new ArrayList<String>() {{
            add("ID");
            add("Name");
        }}));
        clmID.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        chbProperty.getSelectionModel().select(0);
        btnGO_onAction(new ActionEvent(this, list));
    }
    
    @FXML
    private void btnGO_onAction(ActionEvent event) {
        if (findingClass == Supplier.class) {
            List<Supplier> filterdSupplier = SupplierController.getFilterdSupplier(
                    chbProperty.getSelectionModel().getSelectedIndex() == 0 ? 
                            SupplierController.PersonColumns.SupplierID :
                            SupplierController.PersonColumns.Name, 
                    txtSearch.getText());
            ObservableList<TableRaw> data = FXCollections.observableArrayList();
            for (Supplier supplier : filterdSupplier) {
                data.add(new TableRaw(supplier.getSupplierID(), supplier.getFirstName() + " " + supplier.getLastName()));
            }
            list.getItems().setAll(data);
        }else if(findingClass == Item.class){
            List<Item> fiterdItem = ItemController.getFilterdItem(chbProperty.getSelectionModel().getSelectedIndex() == 0 ?
                    ItemController.ItemColumns.ItemID :
                    ItemController.ItemColumns.Name,
                    txtSearch.getText());
            ObservableList<TableRaw> data = FXCollections.observableArrayList();
            for (Item item : fiterdItem) {
                data.add(new TableRaw(item.getItemID(), item.getName()));
            }
            list.getItems().setAll(data);
        }else if(findingClass == Customer.class){
            
        }
    }

    @FXML
    private void txtSearch_onAction(ActionEvent event) {
        btnGO_onAction(event);
    }


    @FXML
    private void btnCancle(ActionEvent event) {
        ((Stage)txtSearch.getScene().getWindow()).close();
        selected = false;
    }

    @FXML
    private void btnSelect(ActionEvent event) {
        int sel = list.getSelectionModel().getSelectedIndex();
        if (sel > 0) {
            setValue(list.getSelectionModel().getSelectedItem().getId());
            btnCancle(event);
            selected = true;
        }else{
            if (list.getItems().size() > 0) {
                setValue(list.getItems().get(0).getId());
                btnCancle(event);
                selected = true;
            }
        }
    }

    @FXML
    private void listClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            btnSelect(new ActionEvent(this, list));
        }
    }
    
    private void setValue(String id){
        if (findingClass == Supplier.class) {
            controller.setSupplierValues(id, "", "", "");
        }else if(findingClass == Customer.class){
            controller.setItemValue(id, "", "");
        }else if(findingClass == Item.class){
            
        }
    }
    
    public class TableRaw{
        private final StringProperty id = new SimpleStringProperty("");
        private final StringProperty name = new SimpleStringProperty("");

        public TableRaw() {
        }

        public TableRaw(String id, String name) {
            setID(id);
            setName(name);
        }
        
        public String getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }
        
        public void setID(String i){
            this.id.set(i);
        }
        public void setName(String n){
            this.name.set(n);
        }
    }
    
}
