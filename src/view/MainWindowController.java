/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private ChoiceBox<?> cmbCusSearchItem;
    @FXML
    private TextField txtCusSearch;
    @FXML
    private TableView<?> tblCustomers;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btnAdd_onClick(ActionEvent event) {
        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case 0: //customers
                UIControllCommon.openFXMLWindow(this, "customers/frmAddCustomer.fxml", 
                        Modality.APPLICATION_MODAL, false);

                //refresh table
                break;
                
                
            case 1://suppliers
                UIControllCommon.openFXMLWindow(this, "suppliers/frmAddSupplier.fxml", 
                        Modality.APPLICATION_MODAL, false);

                break;
            case 2:

                break;
            default:
                throw new AssertionError();
        }

    }

    @FXML
    private void btnEdit_onClick(ActionEvent event) {
        switch (tabPane.getSelectionModel().getSelectedIndex()) {
            case 0: //customers
                UIControllCommon.openFXMLWindow(this, "customers/frmEditCustomer.fxml", 
                        Modality.APPLICATION_MODAL, false);

                //refresh table
                break;
                
                
            case 1://suppliers
                UIControllCommon.openFXMLWindow(this, "suppliers/frmEditSupplier.fxml", 
                        Modality.APPLICATION_MODAL, false);

                break;
            case 2:

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
    }

    @FXML
    private void txtSupSearch_onAction(ActionEvent event) {
    }

    @FXML
    private void txtItmSearch_Action(ActionEvent event) {
    }

}
