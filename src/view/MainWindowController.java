/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CommonControllers;
import controller.CustomersController;
import java.beans.EventHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.util.Callback;
import view.customers.FrmCustomerController;
import view.items.FrmItemController;
import view.suppliers.FrmSupplierController;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class MainWindowController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnRemove;
    @FXML
    private ChoiceBox<String> cmbCusSearchItem;
    @FXML
    private TextField txtCusSearch;
    @FXML
    private TableView<PersonTableRow> tblCustomers;
    @FXML
    private ChoiceBox<?> cmbSupSearchItem;
    @FXML
    private TextField txtSupSearch;
    @FXML
    private TableView<?> tblSuppliers;
    @FXML
    private ChoiceBox<?> cmbItmSearchItem;
    @FXML
    private TextField txtItmSearch;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableColumn<PersonTableRow, String> cusIdColumn;
    @FXML
    private TableColumn<PersonTableRow, String> cusfNameColumn;
    @FXML
    private TableColumn<PersonTableRow, String> cuslNameColumn;
    @FXML
    private TableColumn<PersonTableRow, String> cusTpColumn;
    @FXML
    private TableColumn<PersonTableRow, String> cusAddColumn;

    private int cmbCusSelectedIndex;
    private ObservableList<PersonTableRow> cusData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCusSearchItem.setItems(FXCollections.observableList(new ArrayList<String>() {
            {
                add("Customer ID");
                add("Name");
                add("TP number");
                add("Address");
            }
        }));
        cmbCusSearchItem.getSelectionModel().select(0);
        cmbCusSearchItem.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                cmbCusSelectedIndex = newValue.intValue();
            }
        }
        );
        initCustomerTable();

    }

    @FXML
    private void btnAdd_onClick(ActionEvent event) {
        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case 0: //customers
                CustomersController.openAddNewCustomerWindow();
                //refresh table
                break;

            case 1://suppliers
                ((FrmSupplierController) UICommonController.getInstance().
                        openFXMLWindow("suppliers/frmSupplier.fxml",
                                Modality.APPLICATION_MODAL, false, "")).initData(false);
                break;

            case 2:
                ((FrmItemController) UICommonController.getInstance().
                        openFXMLWindow("items/frmItem.fxml",
                                Modality.APPLICATION_MODAL, false, "")).initData(false);
                break;
            default:
                throw new AssertionError();
        }

    }

    @FXML
    private void btnEdit_onClick(ActionEvent event) {
        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case 0: //customers
                if (tblCustomers.getSelectionModel().getSelectedIndex() > 0) {
                    String selectedID = tblCustomers.getSelectionModel().getSelectedItem().getId();
                    CustomersController.openEditCustomerWindow(selectedID);
                }
                break;

            case 1://suppliers
                ((FrmSupplierController) UICommonController.getInstance().
                        openFXMLWindow("suppliers/frmSupplier.fxml",
                                Modality.APPLICATION_MODAL, false, "")).initData(true);

                break;
            case 2:
                ((FrmItemController) UICommonController.getInstance().
                        openFXMLWindow("items/frmItem.fxml",
                                Modality.APPLICATION_MODAL, false, "")).initData(true);
                break;
            default:
                throw new AssertionError();
        }
    }

    @FXML
    private void btnRemove_onClick(ActionEvent event) {
    }

    @FXML
    private void txtCusSearch_onAction(Event event) {
        refreshCustomer();
    }

    @FXML
    private void txtSupSearch_onAction(ActionEvent event) {
    }

    @FXML
    private void txtItmSearch_Action(ActionEvent event) {
    }

    private void refreshCustomer() {
        String txt = txtCusSearch.getText();
        cusData.clear();
        switch (cmbCusSelectedIndex) {
            case 0:
                CustomersController.refreshTable(this, CustomersController.PersonColumns.CustomerID, txt);
                break;
            case 1:
                CustomersController.refreshTable(this, CustomersController.PersonColumns.Name, txt);
                break;
            case 2:
                CustomersController.refreshTable(this, CustomersController.PersonColumns.Telephone, txt);
                break;
            case 3:
                CustomersController.refreshTable(this, CustomersController.PersonColumns.Address, txt);
                break;
        }
    }

    public void tblCustomerAddItem(String id, String fname, String lname, String address, String tp) {
        cusData.add(new PersonTableRow(id, fname, lname, address, tp));
    }

    public void tblCustomerSetItems() {
        tblCustomers.getItems().setAll(cusData);
    }

    private void initCustomerTable() {
        tblCustomers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cusAddColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cusIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusTpColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        cusfNameColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        cuslNameColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
    }

    @FXML
    private void btnCusReset_onAction(ActionEvent event) {
        txtCusSearch.setText("");
        refreshCustomer();
    }

    //table details classes;
    public class PersonTableRow {

        private final StringProperty id = new SimpleStringProperty("");
        private final StringProperty fname = new SimpleStringProperty("");
        private final StringProperty lname = new SimpleStringProperty("");
        private final StringProperty address = new SimpleStringProperty("");
        private final StringProperty telephone = new SimpleStringProperty("");

        public PersonTableRow() {
        }

        public PersonTableRow(String id, String fname, String lname, String address, String telephone) {
            this.id.set(id);
            this.fname.set(fname);
            this.lname.set(lname);
            this.address.set(address);
            this.telephone.set(telephone);
        }

        /**
         * @return the id
         */
        public String getId() {
            return id.get();
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id.set(id);
        }

        /**
         * @return the fname
         */
        public String getFname() {
            return fname.get();
        }

        /**
         * @param fname the fname to set
         */
        public void setFname(String fname) {
            this.fname.set(fname);
        }

        /**
         * @return the lname
         */
        public String getLname() {
            return lname.get();
        }

        /**
         * @param lname the lname to set
         */
        public void setLname(String lname) {
            this.lname.set(lname);
        }

        /**
         * @return the address
         */
        public String getAddress() {
            return address.get();
        }

        /**
         * @param address the address to set
         */
        public void setAddress(String address) {
            this.address.set(address);
        }

        /**
         * @return the telephone
         */
        public String getTelephone() {
            return telephone.get();
        }

        /**
         * @param telephone the telephone to set
         */
        public void setTelephone(String telephone) {
            this.address.set(telephone);
        }

    }

}
