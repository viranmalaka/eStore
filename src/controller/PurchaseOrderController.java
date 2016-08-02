/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.SessionManager;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PurchaseOrder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import view.UICommonController;
import view.orders.FrmPurchaseOrderController;

/**
 *
 * @author Malaka
 */
public class PurchaseOrderController {
    public static void openCreatePurchaseOrderWindow() {
        String newPOId = CommonControllers.convertIndex(getNextIndex(), 'P');
        FXMLLoader createFXML = UICommonController.getInstance().createFXML("orders/frmPurchaseOrder.fxml");
        Stage stage = UICommonController.getInstance().getStage(createFXML);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Add New Purchase Order");
        ((FrmPurchaseOrderController)createFXML.getController()).initData(newPOId);
        
        stage.showAndWait();
    }

    private static long getNextIndex() {
        SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(PurchaseOrder.class);
        Object maxID = criteria.setProjection(Projections.max("id")).uniqueResult();
        session.close();
        if (maxID == null) {
            return 1;
        }
        return Long.parseLong(maxID + "") + 1;
    }

  }
