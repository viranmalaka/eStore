/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.orders;

import controller.CommonControllers;
import controller.CustomersController;
import controller.ItemController;
import controller.SalesOrderController;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import logger.LogController;
import model.Customer;
import model.Item;
import model.Supplier;
import org.apache.logging.log4j.Level;
import view.FrmControllerCommon;
import view.SelectController;
import view.UICommonController;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class FrmSaleOrderController implements Initializable, FrmControllerCommon {

//<editor-fold defaultstate="collapsed" desc="Elements">
    @FXML
    private DatePicker dtpOrderDate;
    @FXML
    private TextField txtCustomerID;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private Button btnSearchCustomer;
    @FXML
    private Button btnAddNewCustomer;
    @FXML
    private TextField txtItemID;
    @FXML
    private TextField txtItemName;
    @FXML
    private Button btnItemSearch;
    @FXML
    private TextField txtQty;
    @FXML
    private Label lblUnit;
    @FXML
    private TextField txtDiscount;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnCancle;
    @FXML
    private Label lblAvailableQty;
    @FXML
    private Label lblUnitPrice;
    @FXML
    private Label lblItemTotal;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnCreateOrder;
    @FXML
    private TableView<TableRaw> tblItems;
    @FXML
    private Button btnClose;
    @FXML
    private TextField txtSOrderID;
    @FXML
    private TableColumn<TableRaw, String> colItemID;
    @FXML
    private TableColumn<TableRaw, String> colItemName;
    @FXML
    private TableColumn<TableRaw, Double> colUnitePrice;
    @FXML
    private TableColumn<TableRaw, Double> colQty;
    @FXML
    private TableColumn<TableRaw, Double> colDiscount;
    @FXML
    private TableColumn<TableRaw, Double> colTotal;
    @FXML
    private Label lblAddressTele;
    @FXML
    private Label lblExpDate;
//</editor-fold>

    /**
     * Initializes the controller class.
     */
    private boolean orderEditing = false;
    private int editingID = -1;
    private double total;
    @FXML
    private CheckBox chkPaid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtDiscount.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        txtQty.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));

        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colItemID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tp"));
        colUnitePrice.setCellValueFactory(new PropertyValueFactory<>("uPrice"));

        tblItems.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TableRaw> obs, TableRaw oldSelection, TableRaw newSelection) -> {
            try {
                if (newSelection != null) {
                    editingID = tblItems.getSelectionModel().getSelectedIndex();
                    ItemController.setItemInSalesOrder(this, ItemController.ItemColumns.ItemID, newSelection.getId());
                    txtQty.setText(String.valueOf(newSelection.getQty()));
                    txtDiscount.setText(String.valueOf(newSelection.getDiscount()));
                    lblTotal.setText("Total : " + newSelection.getTp());

                    btnAdd.setText("Edit");
                }
            } catch (Exception e) {
            }
        });
        txtQty.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    txtQtuanty_change(null);
                }
            }
        });
        txtDiscount.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                txtDiscount_Change(null);
            }
        });
        txtDiscount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (txtDiscount.getText().length() > 2) {
                    txtDiscount.setText(oldValue);
                }
            }
        });
    }

    public void initData(String newId) { // create
        txtSOrderID.setText(newId);
        dtpOrderDate.setValue(LocalDate.now());
        orderEditing = false;
    }
//<editor-fold defaultstate="collapsed" desc="Select Customer">

    @FXML
    private void btnSearchCustomer_onAction(ActionEvent event) {
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("Select.fxml");
        Stage s = UICommonController.getInstance().getStage(createFXML);

        ((SelectController) createFXML.getController()).initData(this, Customer.class);
        s.setResizable(false);
        s.showAndWait();
        if (((SelectController) createFXML.getController()).isSelected()) {
            CustomersController.setCusInSalesOrder(this, CustomersController.PersonColumns.CustomerID, txtCustomerID.getText());
        }
    }

    public void setCustomerValue(String customerID, String string, String address, String telephone) {
        txtCustomerID.setText(customerID);
        txtCustomerName.setText(string);
        lblAddressTele.setText("Address : " + address.replaceAll("\n", "") + "; Telephone : " + telephone);
    }

    @FXML
    private void btnAddNewCustomer_onAction(ActionEvent event) {
        if (CustomersController.openAddNewCustomerWindow()) {
            String id = CommonControllers.convertIndex(CustomersController.getNextIndex() - 1, 'C');
            txtCustomerID.setText(id);
            CustomersController.setCusInSalesOrder(this, CustomersController.PersonColumns.CustomerID, txtCustomerID.getText());
        }
    }

    @FXML
    private void txtCusID_onAction(ActionEvent event) {
        CustomersController.setCusInSalesOrder(this, CustomersController.PersonColumns.CustomerID, txtCustomerID.getText());
    }

    @FXML
    private void txtCusName_onAction(ActionEvent event) {
        CustomersController.setCusInSalesOrder(this, CustomersController.PersonColumns.Name, txtCustomerName.getText());
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Select Item">
    @FXML
    private void btnSearchItem_onAction(ActionEvent event) {
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("Select.fxml");
        Stage s = UICommonController.getInstance().getStage(createFXML);
        s.setResizable(false);
        ((SelectController) createFXML.getController()).initData(this, Item.class);

        s.showAndWait();
        if (((SelectController) createFXML.getController()).isSelected()) {
            ItemController.setItemInSalesOrder(this, ItemController.ItemColumns.ItemID, txtItemID.getText());
        }
    }

    private void btnAddNewItem_onAction(ActionEvent event) {
        if (ItemController.openAddNewItemWindow()) {
            String id = CommonControllers.convertIndex(ItemController.getNextIndex() - 1, 'I');
            txtItemID.setText(id);
            ItemController.setItemInSalesOrder(this, ItemController.ItemColumns.ItemID, txtItemID.getText());
        }
    }

    @FXML
    private void txtItemID_onAction(ActionEvent event) {
        ItemController.setItemInSalesOrder(this, ItemController.ItemColumns.ItemID, txtItemID.getText());
    }

    @FXML
    private void txtItemName_onAction(ActionEvent event) {
        ItemController.setItemInSalesOrder(this, ItemController.ItemColumns.Name, txtItemName.getText());
    }

    public void setItemValue(String id, String name, String scale, double avaiQty, Date expDate, double sellingPrice) {
        txtItemID.setText(id);
        txtItemName.setText(name);
        lblUnit.setText(scale);
        lblAvailableQty.setText("Available Qty : " + avaiQty);
        lblExpDate.setText("Exp Date : " + (expDate == null ? "N/A" : expDate.toString()));
        lblUnitPrice.setText("Selling Price : " + sellingPrice);
    }

    @Override
    public void setItemValue(String id, String name, String scale) {
        txtItemID.setText(id);
        txtItemName.setText(name);
        lblUnit.setText(scale);
    }
//</editor-fold>

    @FXML
    private void btnRemove_onAction(ActionEvent event) {
        if (editingID > -1) {
            updateTotal(-1 * tblItems.getItems().get(editingID).getTp());
            tblItems.getItems().remove(editingID);
            tblItems.refresh();
            editingID = -1;
            clearFields();
        }
    }

    @FXML
    private void btnAdd_onAction(ActionEvent event) {
        do {
            //<editor-fold defaultstate="collapsed" desc="Item Validating">

            String id = txtItemID.getText();
            String name = txtItemName.getText();

            String emptyFields = "";
            if (id.equals("")) {
                emptyFields += "Item ID \n";
            }
            if (name.equals("")) {
                emptyFields += "Item Name \n";
            }
            if (txtQty.getText().equals("")) {
                emptyFields += "Quantity \n";
            }
            if (!emptyFields.equals("")) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR,
                        UICommonController.CommonHeadding.EMPTY_FIELDS,
                        UICommonController.CommonTitles.FORMATTING_ERROR,
                        emptyFields);
                break;
            }

            double qty;
            double dis = 0;
            double itemTotal = 0;
            double unitPrice = 0;
            double availableQty = 0;
            try {
                qty = Double.parseDouble(txtQty.getText());
                if (!txtDiscount.getText().equals("")) {
                    dis = Double.parseDouble(txtDiscount.getText());
                }
                itemTotal = Double.parseDouble(lblItemTotal.getText().substring(8));
                unitPrice = Double.parseDouble(lblUnitPrice.getText().substring(16));
                availableQty = Double.parseDouble(lblAvailableQty.getText().substring(16));
            } catch (NumberFormatException numberFormatException) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR, "Number Converting Error", "");
                break;
            }
            if (!ItemController.matchItemValues(id, name)) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR, "Item ID and Name are not match", "");
                break;
            }
            if (itemTotal != (qty * unitPrice * (100 - dis) / 100)) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR, "Product of quantity and unit price with discount is not equal to total", "Value Error");
                break;
            }

            if (availableQty < qty) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR, "Entered Quantity should be less than the available quantity.", "Value Error");
                txtQty.requestFocus();
                break;
            }
            String negFields = "";
            if (qty <= 0) {
                negFields += "Quantity \n";
            }
            if (dis < 0) {
                negFields += "Discount \n";
            }
            if (!negFields.equals("")) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR,
                        UICommonController.CommonHeadding.NEGETIVE_NUMBER,
                        UICommonController.CommonTitles.VALIDATING_ERROR,
                        negFields);
                break;
            }


//</editor-fold>
            // trying to add or edit
            if (editingID < 0) { //creating new
                int hasID = -1;
                for (int i = 0; i < tblItems.getItems().size(); i++) {
                    if (tblItems.getItems().get(i).getId().equals(id)) { // existing
                        hasID = i;
                        break;
                    }
                }
                if (hasID < 0) { // add new Item
                    tblItems.getItems().add(new TableRaw(id, name, qty, unitPrice, dis, itemTotal));
                    updateTotal(itemTotal);
                } else { // update current Index (hasID)
                    Optional<ButtonType> reques = UICommonController.showAlertBox(Alert.AlertType.CONFIRMATION,
                            "This item is already in the list. \n"
                            + "Do you want to add this quantity into it ?",
                            "Repeat Items",
                            "Click 'Yes' to add this quantity to previous item.\n"
                            + "Click 'No' to Override it.\n"
                            + "Click 'Cancle' to cancle this adding",
                            ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    if (reques.get() == ButtonType.YES) {
                        TableRaw itr = tblItems.getItems().get(hasID);
                        double addingQty;
                        if (qty + itr.getQty() > availableQty) {
                            UICommonController.showAlertBox(Alert.AlertType.INFORMATION,
                                    "Your request quantity is more than available quantity",
                                    "", "Add the item with available quantity.");
                            addingQty = availableQty;
                        } else {
                            addingQty = qty + itr.getQty();
                        }
                        itr.setDiscount(dis);
                        itr.setName(name);
                        updateTotal(-1 * itr.getTp());
                        itr.setQty(addingQty);
                        itr.setTp(itr.getQty() * unitPrice * (100 - dis) / 100);
                        tblItems.getItems().remove(hasID);
                        tblItems.getItems().add(hasID, itr);
                        updateTotal(itr.getTp());
                        tblItems.refresh();

                    } else if (reques.get() == ButtonType.NO) {
                        TableRaw itr = tblItems.getItems().get(hasID);
                        itr.setName(name);
                        itr.setQty(qty);
                        itr.setDiscount(dis);
                        updateTotal(-1 * itr.getTp());
                        itr.setTp(itemTotal);
                        updateTotal(itemTotal);
                        itr.setuPrice(unitPrice);
                        tblItems.getItems().remove(hasID);
                        tblItems.getItems().add(hasID, itr);
                        tblItems.refresh();
                    } // cancle
                }
            } else {// editing existing
                tblItems.getItems().get(editingID).setDiscount(dis);
                tblItems.getItems().get(editingID).setId(id);
                tblItems.getItems().get(editingID).setName(name);
                tblItems.getItems().get(editingID).setQty(qty);
                updateTotal(-1 * tblItems.getItems().get(editingID).getTp());
                tblItems.getItems().get(editingID).setTp(itemTotal);
                tblItems.getItems().get(editingID).setuPrice(unitPrice);
                updateTotal(itemTotal);
                tblItems.refresh();
                editingID = -1;
            }
            clearFields();
        } while (false);

    }

    @FXML

    private void btnCancle_onAction(ActionEvent event) {
        if (editingID < 0) {
            if (UICommonController.showAlertBox(Alert.AlertType.CONFIRMATION, "All entered data will be loss", "").get() == ButtonType.OK) {
                clearFields();
            }
        } else if (UICommonController.showAlertBox(Alert.AlertType.CONFIRMATION, "Are You sure to quit editing the entry", "").get() == ButtonType.OK) {
            clearFields();
            editingID = -1;
        }
    }

    @FXML
    private void btnCreateOrder_onAction(ActionEvent event) {
        do {
            if (!CustomersController.matchCustomerValues(txtCustomerID.getText(), txtCustomerName.getText())) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR, "Customer ID and Name are not match", "");
                txtCustomerID.requestFocus();
                break;
            }
            if (tblItems.getItems().isEmpty()) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR, "There are no any items in the order", "", "You should add at least one item");
                break;
            }

            SalesOrderController soc = new SalesOrderController();
            if (!orderEditing) {
                boolean pre1 = soc.createNewPurchaseOrder(txtSOrderID.getText(),
                        Date.from(dtpOrderDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        txtCustomerID.getText(), chkPaid.isSelected(), total
                );

                boolean pre2 = true;
                for (TableRaw item : tblItems.getItems()) {
                    pre2 = soc.addItemsToPurchaseOrder(pre1, item.getId(), item.getQty(), item.getUPrice(), item.getDiscount()) & pre2;
                }

                boolean res = soc.saveOrder(pre1 & pre2);
                if (res) {
                    UICommonController.showAlertBox(Alert.AlertType.INFORMATION, "Saving Successfully", "");
                    LogController.log(Level.INFO, "Add new Purchase Order -> " + txtSOrderID.getText());
                    ((Stage) tblItems.getScene().getWindow()).close();
                } else {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR, "Saving is not Successfully", "");
                    LogController.log(Level.INFO, "Error Add new Purchase Order -> " + txtSOrderID.getText());
                }
            }

        } while (false);

    }

    @FXML
    private void btnClose_onAction(ActionEvent event) {
    }
//<editor-fold defaultstate="collapsed" desc="Automatic value">

    @FXML
    private void txtQtuanty_change(KeyEvent event) {
        try {
            double up = Double.parseDouble(lblUnitPrice.getText().substring(16));
            double qty = Double.parseDouble(txtQty.getText());
            if (txtDiscount.getText().equals("")) {
                lblItemTotal.setText("Total : " + up * qty);
            } else {
                double dis = Double.parseDouble(txtDiscount.getText());
                lblItemTotal.setText("Total : " + up * qty * (100 - dis) / 100);
            }
        } catch (NumberFormatException numberFormatException) {
        }
    }

    @FXML
    private void txtDiscount_Change(KeyEvent event) {
        try {
            if (!txtQty.getText().equals("")) {
                double up = Double.parseDouble(lblUnitPrice.getText().substring(16));
                double qty = Double.parseDouble(txtQty.getText());
                double dis = Double.parseDouble(txtDiscount.getText());
                lblItemTotal.setText("Total : " + up * qty * (100 - dis) / 100);
            }
        } catch (NumberFormatException numberFormatException) {
        }
    }
//</editor-fold>

    private void updateTotal(double num) {
        this.total += num;
        lblTotal.setText("Total : " + String.valueOf(total));
    }

    private void clearFields() {
        txtItemID.setText("");
        txtItemName.setText("");
        txtQty.setText("");
        txtDiscount.setText("");
        lblAvailableQty.setText("Available Qty : #");
        lblUnitPrice.setText("Sell Price (per unit) : #");
        lblExpDate.setText("Exp Date : #");
        lblItemTotal.setText("Total : #");
    }

    public class TableRaw {

        private final StringProperty id = new SimpleStringProperty("");
        private final StringProperty name = new SimpleStringProperty("");
        private final DoubleProperty qty = new SimpleDoubleProperty(0);
        private final DoubleProperty uPrice = new SimpleDoubleProperty(0);
        private final DoubleProperty discount = new SimpleDoubleProperty(0);
        private final DoubleProperty tp = new SimpleDoubleProperty(0);

        public TableRaw() {
        }

        public TableRaw(String id, String name, double qty, double u, double dis, double tp) {
            this.id.set(id);
            this.name.set(name);
            this.qty.set(qty);
            this.uPrice.set(u);
            this.discount.set(dis);
            this.tp.set(tp);
        }

        public String getId() {
            return id.get();
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public Double getQty() {
            return qty.get();
        }

        public void setQty(Double qty) {
            this.qty.set(qty);
        }

        public Double getUPrice() {
            return uPrice.get();
        }

        public void setuPrice(Double uPrice) {
            this.uPrice.set(uPrice);
        }

        public Double getDiscount() {
            return discount.get();
        }

        public void setDiscount(Double discount) {
            this.discount.set(discount);
        }

        public Double getTp() {
            return tp.get();
        }

        public void setTp(Double tp) {
            this.tp.set(tp);
        }

    }
}
