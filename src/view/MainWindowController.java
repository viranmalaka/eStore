/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    }

    @FXML
    private void btnEdit_onClick(ActionEvent event) {
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