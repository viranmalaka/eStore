/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import hibernate.SessionManager;
import javafx.stage.Modality;
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
        ((FrmPurchaseOrderController) UICommonController.getInstance().
                openFXMLWindow("items/frmItem.fxml",
                        Modality.APPLICATION_MODAL, false, "Add New Item")).initData(newPOId);
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
