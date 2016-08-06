/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.orders;

import controller.CommonControllers;
import controller.ItemController;
import controller.PurchaseOrderController;
import controller.SupplierController;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import logger.LogController;
import model.Item;
import model.Supplier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import view.FrmControllerCommon;
import view.SelectController;
import view.UICommonController;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class FrmPurchaseOrderController implements Initializable, FrmControllerCommon {

    //<editor-fold defaultstate="collapsed" desc="FXML Elements">
    @FXML
    private DatePicker dtpOrderDate;
    @FXML
    private TextField txtSupplierID;
    @FXML
    private TextField txtSupplierName;
    @FXML
    private Button btnSupplierSearch;
    @FXML
    private Button btn_AddNewSupplier;
    @FXML
    private TextField txtItemID;
    @FXML
    private TextField txtItemName;
    @FXML
    private Button btnItemSearch;
    @FXML
    private TextField txtQty;
    @FXML
    private DatePicker dtpExpireDate;
    @FXML
    private TextField txtLabelPrice;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnCancle;
    @FXML
    private Label lblTotal;
    @FXML
    private Button btnSaveOrder;
    @FXML
    private Button btnClose;
    @FXML
    private TableView<ItemTableRaw> tblItems;
    @FXML
    private TextField txtOrderID;
    @FXML
    private CheckBox chkPaid;
    @FXML
    private Label lblSupDetails;
    @FXML
    private Label lblUnit;
    @FXML
    private TextField txtPP;
    @FXML
    private TextField txtTP;
    @FXML
    private TableColumn<ItemTableRaw, String> colItemID;
    @FXML
    private TableColumn<ItemTableRaw, String> colName;
    @FXML
    private TableColumn<ItemTableRaw, Double> colUnitePrice;
    @FXML
    private TableColumn<ItemTableRaw, Double> colLablePrice;
    @FXML
    private TableColumn<ItemTableRaw, Double> colQty;
    @FXML
    private TableColumn<ItemTableRaw, Double> colTotal;
    @FXML
    private TableColumn<ItemTableRaw, String> colExpDate;

//</editor-fold>
    /**
     * Initializes the controller class.
     */
    private boolean orderEditing = false;
    private int editingID = -1;
    private double total;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtQty.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        txtPP.setTextFormatter(new TextFormatter<>(new NumberStringConverter("#.00")));
        txtLabelPrice.setTextFormatter(new TextFormatter<>(new NumberStringConverter("#.00")));
        txtTP.setTextFormatter(new TextFormatter<>(new NumberStringConverter("#.00")));

        colExpDate.setCellValueFactory(new PropertyValueFactory<>("expDate"));
        colItemID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitePrice.setCellValueFactory(new PropertyValueFactory<>("up"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tp"));
        colLablePrice.setCellValueFactory(new PropertyValueFactory<>("labelPrice"));

        tblItems.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends ItemTableRaw> obs, ItemTableRaw oldSelection, ItemTableRaw newSelection) -> {
            try {
                if (newSelection != null) {
                    editingID = tblItems.getSelectionModel().getSelectedIndex();
                    ItemController.setItemInPurchaseOrder(this, ItemController.ItemColumns.ItemID, newSelection.getId());
                    txtQty.setText(String.valueOf(newSelection.getQty()));
                    txtPP.setText(String.valueOf(newSelection.getUp()));
                    txtTP.setText(String.valueOf(newSelection.getTp()));
                    txtLabelPrice.setText(String.valueOf(newSelection.getLabelPrice()));
                    if (newSelection.getExpDate().equals("")) {
                        dtpExpireDate.setValue(null);
                    } else {
                        dtpExpireDate.setValue(LocalDate.parse(newSelection.getExpDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    }
                    btnAdd.setText("Edit");
                }
            } catch (Exception e) {
            }
        });

        txtTP.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                txtTP_type(null);
            }
        });
        txtPP.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                txtUP_type(null);
            }
        });
        txtQty.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                txtQty_type(null);
            }
        });

    }

//<editor-fold defaultstate="collapsed" desc="Selecting Supplier Codes">
    @FXML
    private void btnSupplierSearch_onAction(ActionEvent event) {
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("Select.fxml");
        Stage s = UICommonController.getInstance().getStage(createFXML);

        ((SelectController) createFXML.getController()).initData(this, Supplier.class);
        s.setResizable(false);
        s.showAndWait();
        if (((SelectController) createFXML.getController()).isSelected()) {
            SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.SupplierID, txtSupplierID.getText());
        }
    }

    @FXML
    private void btn_AddNewSupplier_onAction(ActionEvent event) {
        if (SupplierController.openAddNewSupplierWindow()) {

            String id = CommonControllers.convertIndex(SupplierController.getNextIndex() - 1, 'S');
            txtSupplierID.setText(id);
            SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.SupplierID, txtSupplierID.getText());
        }
    }

    @FXML
    private void txtSubName_onAction(ActionEvent event) {
        SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.Name, txtSupplierName.getText());
    }

    @FXML
    private void txtSupID_onAction(ActionEvent event) {
        SupplierController.setSupInPurchaseOrder(this, SupplierController.PersonColumns.SupplierID, txtSupplierID.getText());
    }

    public void setSupplierValues(String id, String name, String add, String telephone) {
        txtSupplierID.setText(id);
        txtSupplierName.setText(name);
        lblSupDetails.setText("Address : " + add.replaceAll("\n", " ") + "; Telephone : " + telephone);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Selecting Item Codes">
    @FXML
    private void btnItemSearch_onAction(ActionEvent event) {
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("Select.fxml");
        Stage s = UICommonController.getInstance().getStage(createFXML);
        s.setResizable(false);
        ((SelectController) createFXML.getController()).initData(this, Item.class);

        s.showAndWait();
        if (((SelectController) createFXML.getController()).isSelected()) {
            ItemController.setItemInPurchaseOrder(this, ItemController.ItemColumns.ItemID, txtItemID.getText());
        }
    }

    @FXML
    private void btnAddNewItem_onAction(ActionEvent event) {
        if (ItemController.openAddNewItemWindow()) {
            String id = CommonControllers.convertIndex(ItemController.getNextIndex() - 1, 'I');
            txtItemID.setText(id);
            ItemController.setItemInPurchaseOrder(this, ItemController.ItemColumns.ItemID, txtItemID.getText());
        }
    }

    @FXML
    private void txtItemID_onAction(ActionEvent event) {
        ItemController.setItemInPurchaseOrder(this, ItemController.ItemColumns.ItemID, txtItemID.getText());
    }

    @FXML
    private void txtItemName_onAction(ActionEvent event) {
        ItemController.setItemInPurchaseOrder(this, ItemController.ItemColumns.Name, txtItemName.getText());
    }

    public void setItemValue(String id, String name, String scale) {
        txtItemID.setText(id);
        txtItemName.setText(name);
        lblUnit.setText(scale);
    }
//</editor-fold>

    @FXML
    private void dtpExpireDate_onChange(InputMethodEvent event) {
    }

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
        //item validating
        switch (1) {
            case 1:
                //<editor-fold defaultstate="collapsed" desc="Validating">
                String id = txtItemID.getText();
                String name = txtItemName.getText();

                String emptyFields = "";
                if (id.equals("")) {
                    emptyFields += "Item ID \n";
                    txtItemID.requestFocus();
                }
                if (name.equals("")) {
                    emptyFields += "Item name \n";
                    if (emptyFields.equals("")) {
                        txtItemName.requestFocus();
                    }
                }
                if (txtQty.getText().equals("")) {
                    emptyFields += "Quantity \n";
                    if (emptyFields.equals("")) {
                        txtQty.requestFocus();
                    }
                }
                if (txtPP.getText().equals("")) {
                    emptyFields += "Unit Purchase Price \n";
                    if (emptyFields.equals("")) {
                        txtPP.requestFocus();
                    }
                }
                if (txtTP.getText().equals("")) {
                    emptyFields += "Total Purchase Price \n";
                    if (emptyFields.equals("")) {
                        txtTP.requestFocus();
                    }
                }
                if (txtLabelPrice.getText().equals("")) {
                    emptyFields += "Label Price \n";
                    if (emptyFields.equals("")) {
                        txtLabelPrice.requestFocus();
                    }
                }
                if (!emptyFields.equals("")) {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR,
                            UICommonController.CommonHeadding.EMPTY_FIELDS,
                            UICommonController.CommonTitles.FORMATTING_ERROR,
                            emptyFields);
                    break;
                }

                double qty;
                double up;
                double tp;
                double lp;
                try {
                    qty = Double.parseDouble(txtQty.getText());
                    up = Double.parseDouble(txtPP.getText());
                    tp = Double.parseDouble(txtTP.getText());
                    lp = Double.parseDouble(txtLabelPrice.getText());
                } catch (NumberFormatException numberFormatException) {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR, "Number Converting Error", "");
                    break;
                }

                if (!ItemController.matchItemValues(id, name)) {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR, "Item ID and Name are not match", "");
                    break;
                }
                if (tp != qty * up) {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR, "Product of Quantity and Unit Price is not equal to Total", "");
                    break;
                }

                String negetiveField = "";
                if (qty <= 0) {
                    negetiveField += "Quantity \n";
                }
                if (up <= 0) {
                    negetiveField += "Unit Purchase Price\n";
                }
                if (tp <= 0) {
                    negetiveField += "Total Purchase Price \n";
                }
                if (lp <= 0) {
                    negetiveField += "Labled Price \n";
                }
                if (!negetiveField.equals("")) {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR,
                            UICommonController.CommonHeadding.NEGETIVE_NUMBER,
                            UICommonController.CommonTitles.VALIDATING_ERROR,
                            negetiveField);
                    break;
                }

                LocalDate date = null;
                if (dtpExpireDate.getValue() != null) {
                    if (dtpExpireDate.getValue().isBefore(LocalDate.now())) {
                        UICommonController.showAlertBox(Alert.AlertType.ERROR, "Expire Date is Passed", "");
                        break;
                    }
                    date = dtpExpireDate.getValue();
                }
//</editor-fold>

                //tring to adding or editing
                if (editingID < 0) { // add new
                    //<editor-fold defaultstate="collapsed" desc="Add New Entry">
                    int hasID = -1;
                    for (int i = 0; i < tblItems.getItems().size(); i++) {
                        if (tblItems.getItems().get(i).getId().equals(id)) { // existing
                            hasID = i;
                            break;
                        }
                    }
                    if (hasID < 0) { // add new Item
                        tblItems.getItems().add(new ItemTableRaw(id,
                                name,
                                date == null ? "" : date.toString(),
                                qty,
                                up,
                                tp,
                                lp));
                        updateTotal(tp);
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
                            ItemTableRaw itr = tblItems.getItems().get(hasID);
                            itr.setExpDate(date == null ? "" : date.toString());
                            itr.setLabelPrice(lp);
                            itr.setName(name);
                            updateTotal(-1 * itr.getTp());
                            itr.setQty(itr.getQty() + qty);
                            itr.setTp(itr.getQty() * up);
                            itr.setUp(up);
                            tblItems.getItems().remove(hasID);
                            tblItems.getItems().add(hasID, itr);
                            updateTotal(itr.getTp());
                            tblItems.refresh();

                        } else if (reques.get() == ButtonType.NO) {
                            ItemTableRaw itr = tblItems.getItems().get(hasID);
                            itr.setExpDate(date == null ? "" : date.toString());
                            itr.setLabelPrice(lp);
                            itr.setName(name);
                            itr.setQty(qty);
                            updateTotal(-1 * itr.getTp());
                            itr.setTp(tp);
                            updateTotal(tp);
                            itr.setUp(up);
                            tblItems.getItems().remove(hasID);
                            tblItems.getItems().add(hasID, itr);
                            tblItems.refresh();
                        } // cancle
                    }
//</editor-fold>
                } else { // editing
                    tblItems.getItems().get(editingID).setId(id);
                    tblItems.getItems().get(editingID).setExpDate(date == null ? "" : date.toString());
                    tblItems.getItems().get(editingID).setLabelPrice(lp);
                    tblItems.getItems().get(editingID).setName(name);
                    updateTotal(-1 * tblItems.getItems().get(editingID).getTp());
                    tblItems.getItems().get(editingID).setTp(tp);
                    tblItems.getItems().get(editingID).setQty(qty);
                    tblItems.getItems().get(editingID).setUp(up);
                    tblItems.refresh();
                    updateTotal(tp);
                    editingID = -1;
                }
                clearFields();
        }

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

    private void clearFields() {
        txtItemID.setText("");
        txtItemName.setText("");
        txtQty.setText("");
        txtPP.setText("");
        txtTP.setText("");
        lblUnit.setText("");
        txtLabelPrice.setText("");
        dtpExpireDate.setValue(null);
        btnAdd.setText("Add");
        tblItems.getSelectionModel().clearSelection();
    }

    @FXML
    private void btnSaveOrder_onAction(ActionEvent event) {
        //add validationg for supplier
        do {
            if (!SupplierController.matchSupplierValues(txtSupplierID.getText(), txtSupplierName.getText())) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR, "Supplier ID and Name are not match", "");
                txtSupplierID.requestFocus();
                break;
            }
            if (tblItems.getItems().isEmpty()) {
                UICommonController.showAlertBox(Alert.AlertType.ERROR, "There are no any items in the order", "", "You should add at least one item");
                break;
            }

            // saving
            PurchaseOrderController poc = new PurchaseOrderController();
            if (!orderEditing) {
                boolean pre1 = poc.createNewPurchaseOrder(txtOrderID.getText(),
                        Date.from(dtpOrderDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        txtSupplierID.getText(), chkPaid.isSelected(), total
                );

                boolean pre2 = true;
                for (ItemTableRaw item : tblItems.getItems()) {
                    pre2 = poc.addItemsToPurchaseOrder(pre1, item.getId(), item.getQty(), item.getUp(), item.getLabelPrice(), item.getExpDate()) & pre2;
                }

                boolean res = poc.saveOrder(pre1 & pre2);
                if (res) {
                    UICommonController.showAlertBox(Alert.AlertType.INFORMATION, "Saving Successfully", "");
                    LogController.log(Level.INFO, "Add new Purchase Order -> " + txtOrderID.getText());
                    ((Stage) tblItems.getScene().getWindow()).close();
                } else {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR, "Saving is not Successfully", "");
                    LogController.log(Level.INFO, "Error Add new Purchase Order -> " + txtOrderID.getText());
                }
            } else {
                boolean pre1 = poc.editPurchaseOrder(txtOrderID.getText(),
                        Date.from(dtpOrderDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        txtSupplierID.getText(), chkPaid.isSelected(), total
                );

                boolean pre2 = true;

                for (ItemTableRaw item : tblItems.getItems()) {
                    pre2 = poc.addItemsToPurchaseOrder(pre1, item.getId(), item.getQty(), item.getUp(), item.getLabelPrice(), item.getExpDate()) & pre2;
                }

                boolean res = poc.saveOrder(pre1 & pre2);
                if (res) {
                    UICommonController.showAlertBox(Alert.AlertType.INFORMATION, "Updating Successfully", "");
                    LogController.log(Level.INFO, "Update Purchase Order -> " + txtOrderID.getText());;
                    ((Stage) tblItems.getScene().getWindow()).close();
                } else {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR, "Saving is not Successfully", "");
                    LogController.log(Level.INFO, "Error eding Purchase Order -> " + txtOrderID.getText());
                }
            }
        } while (false);
    }

    @FXML
    private void btnClose_onAction(ActionEvent event) {
        if (UICommonController.showAlertBox(Alert.AlertType.CONFIRMATION, "Do you want to close ? \nAll entered data will be loss", "", "", ButtonType.YES, ButtonType.NO).get().equals(ButtonType.YES)) {
            ((Stage) btnClose.getScene().getWindow()).close();
        }
    }

    public void initData(String purchaseOrderID, Date date, String supplierID, String name, boolean paid, double total) {
        //editing
        txtOrderID.setText(purchaseOrderID);
        dtpOrderDate.setValue(LocalDate.parse(date.toString()));
        txtSupplierID.setText(supplierID);
        txtSupplierName.setText(name);
        chkPaid.setSelected(paid);
        updateTotal(total);
        orderEditing = true;
    }

    public void fillTableItems(String id, String name, Date expDate, double qty, double up, double lp) {
        tblItems.getItems().add(new ItemTableRaw(id, name,
                expDate == null ? "" : expDate.toString(),
                qty, up, qty * up, lp));
    }

    public void initData(String newPOId) { // create
        txtOrderID.setText(newPOId);
        dtpOrderDate.setValue(LocalDate.now());
        orderEditing = false;
    }

//<editor-fold defaultstate="collapsed" desc="Automatic calculation">
    @FXML
    private void txtQty_type(KeyEvent event) {
        try {
            String up = txtPP.getText();
            String tp = txtTP.getText();
            double q = Double.parseDouble(txtQty.getText());

            if (!up.equals("") && !tp.equals("")) {
            } else if (!up.equals("") && tp.equals("")) {
                txtTP.setText(String.valueOf(Double.parseDouble(txtPP.getText()) * q));
                txtTP.setText(String.valueOf(Double.parseDouble(txtPP.getText()) * q));
            } else if (up.equals("") && !tp.equals("")) {
                txtPP.setText(String.valueOf(Double.parseDouble(txtTP.getText()) / q));
            }

        } catch (NumberFormatException numberFormatException) {
        }
    }

    @FXML
    private void txtUP_type(KeyEvent event) {
        try {
            String q = txtQty.getText();
            String tp = txtTP.getText();
            double up = Double.parseDouble(txtPP.getText());

            if (!q.equals("") && !tp.equals("")) {
                txtTP.setText(String.valueOf(Double.parseDouble(txtQty.getText()) * up));
            } else if (!q.equals("") && tp.equals("")) {
                txtTP.setText(String.valueOf(Double.parseDouble(txtQty.getText()) * up));
            } else if (q.equals("") && !tp.equals("")) {
                txtQty.setText(String.valueOf(Double.parseDouble(txtTP.getText()) / up));
            }
        } catch (NumberFormatException numberFormatException) {
        }
    }

    @FXML
    private void txtTP_type(KeyEvent event) {
        try {
            String up = txtPP.getText();
            String q = txtQty.getText();
            double t = Double.parseDouble(txtTP.getText());

            if (!up.equals("") && !q.equals("")) {
                txtPP.setText(String.valueOf(t / Double.parseDouble(txtQty.getText())));
            } else if (!up.equals("") && q.equals("")) {
                txtQty.setText(String.valueOf(t / Double.parseDouble(txtPP.getText())));
            } else if (up.equals("") && !q.equals("")) {
                txtPP.setText(String.valueOf(t / Double.parseDouble(txtQty.getText())));
            }
        } catch (NumberFormatException numberFormatException) {
        }

    }
//</editor-fold>

    private void updateTotal(double num) {
        this.total += num;
        lblTotal.setText("Total : " + String.valueOf(total));
    }

    public class ItemTableRaw {

        private final StringProperty id = new SimpleStringProperty("");
        private final StringProperty name = new SimpleStringProperty("");
        private final StringProperty expDate = new SimpleStringProperty("");
        private final DoubleProperty qty = new SimpleDoubleProperty(0);
        private final DoubleProperty up = new SimpleDoubleProperty(0);
        private final DoubleProperty tp = new SimpleDoubleProperty(0);
        private final DoubleProperty labelPrice = new SimpleDoubleProperty(0);

        public ItemTableRaw() {
        }

        public ItemTableRaw(String id, String name, String expData, double qty, double up, double tp, double lp) {
            this.setExpDate(expData);
            this.setId(id);
            this.setLabelPrice(lp);
            this.setName(name);
            this.setQty(qty);
            this.setTp(tp);
            this.setUp(up);
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

        public String getExpDate() {
            return expDate.get();
        }

        public void setExpDate(String expDate) {
            this.expDate.set(expDate);
        }

        public Double getQty() {
            return qty.get();
        }

        public void setQty(Double qty) {
            this.qty.set(qty);
        }

        public Double getUp() {
            return up.get();
        }

        public void setUp(Double up) {
            this.up.set(up);
        }

        public Double getTp() {
            return tp.get();
        }

        public void setTp(Double tp) {
            this.tp.set(tp);
        }

        public Double getLabelPrice() {
            return labelPrice.get();
        }

        public void setLabelPrice(Double labelPrice) {
            this.labelPrice.set(labelPrice);
        }

    }
}
