/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.items;

import controller.CommonControllers;
import controller.ItemController;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logger.LogController;
import model.Item;
import org.apache.logging.log4j.Level;
import view.UICommonController;

/**
 * FXML Controller class
 *
 * @author Malaka
 */
public class FrmItemController implements Initializable {

    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;
    @FXML
    private TextField txtItmID;
    @FXML
    private TextField txtItmName;
    @FXML
    private ChoiceBox<String> cmbItmUnit;
    @FXML
    
    
    private Label lblTitle;
    private boolean forEdit;
    private boolean added;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cmbItmUnit.getItems().setAll(ItemController.getAllUnits());
    }

    @FXML
    private void btnSave_onAction(ActionEvent event) {
        String id = txtItmID.getText();
        String name = txtItmName.getText();
        String scale = cmbItmUnit.getSelectionModel().getSelectedItem();
        int selIndex = cmbItmUnit.getSelectionModel().getSelectedIndex();
        switch (1) {
            case 1:
                String emptyItem = "";

                if (name.equals("")) {
                    emptyItem += "Name \n";
                }
                if (selIndex < 0) {
                    emptyItem += "Unit \n";
                }

                if (!emptyItem.equals("")) {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR,
                            UICommonController.CommonHeadding.EMPTY_FIELDS,
                            UICommonController.CommonTitles.VALIDATING_ERROR,
                            emptyItem);
                    break;
                }

                if (!CommonControllers.isName(name)) {
                    UICommonController.showAlertBox(Alert.AlertType.ERROR,
                            UICommonController.CommonHeadding.INVALID_FORMATTINGS,
                            UICommonController.CommonTitles.FORMATTING_ERROR,
                            String.format("'%s' is not a name \n", name)
                    );
                    break;
                }

                if (forEdit) {
                    boolean updateItem = ItemController.updateItem(id, name, Item.Units.valueOf(scale));
                    if (!updateItem) {
                        UICommonController.showAlertBox(Alert.AlertType.ERROR,  "Item is not Updated Successfully","Error");
                        LogController.log(Level.ERROR, "Item Updating is not done.");
                        added = false;
                    } else {
                        UICommonController.showAlertBox(Alert.AlertType.INFORMATION, "Item is Updated Successfully", "");
                        ((Stage) btnSave.getScene().getWindow()).close();
                        LogController.log(Level.INFO, "Item Updated - " + id);
                        added = true;
                    }
                }else{
                    boolean saveItem = ItemController.saveItem(id, name, Item.Units.valueOf(scale));
                    if (!saveItem) {
                        UICommonController.showAlertBox(Alert.AlertType.ERROR,  "Item is not Saved Successfully", "Error");
                        LogController.log(Level.ERROR, "Item Saving is not done.");
                        added = false;
                    } else {
                        UICommonController.showAlertBox(Alert.AlertType.INFORMATION, "Item is Saved Successfully", "");
                        ((Stage) btnSave.getScene().getWindow()).close();
                        LogController.log(Level.INFO, "New Item Created - " + id);
                        added = true;
                    }
                }
        }
    }

    @FXML
    private void btnClose_onClose(ActionEvent event) {
        Optional<ButtonType> showAlertBox = UICommonController.showAlertBox(Alert.AlertType.INFORMATION, 
                "Are you sure ? you want to close.", 
                "",null,
                ButtonType.YES,ButtonType.NO);
        if (showAlertBox.get() == ButtonType.YES) {
            ((Stage) btnClose.getScene().getWindow()).close();
            added = false;
        }
    }

    public void initData(String newItemId) { // for new Item
        lblTitle.setText("Add new Item");
        txtItmID.setText(newItemId);
        forEdit = false;
        LogController.log(Level.TRACE, "Open -> Add New Item Window");
    }

    public void initData(String itemID, String name, String scale) { // for edit
        forEdit = true;
        lblTitle.setText("Edit Item");
        txtItmName.setText(name);
        cmbItmUnit.getSelectionModel().select(scale);
    }

    public boolean isAdded() {
        return added;
    }

}
