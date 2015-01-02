/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.promotion;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.openbravo.basic.BasicException;
import com.openbravo.beans.JCalendarDialog;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.*;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.panels.JProductFinder;
import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.util.PropertyUtils;
import java.awt.Component;
import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Escartin Aurélien aurelien.escartin@gmail.com
 */
public class PromoEditor extends JPanel implements EditorRecord {

    private SentenceList m_sentPromo;
    private ComboBoxValModel m_jTypeModel;
    private ComboBoxValModel m_jCatModel;
    private DirtyManager m_Dirty;
    DataLogicSales dls;
    String _DescBonusArticle = null;
    private Object m_sID;
    private DataLogicSales m_dlSales;
    private Session s;

    /** Creates new form PlacesEditor
     * @param app
     * @param dlSales
     * @param dirty */
    public PromoEditor(AppView app, DataLogicSales dlSales, DirtyManager dirty) {
        m_dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        initComponents();
//        jLabel13.setText("%");
        jLabel1.setText("Name");
        jLabel2.setText("Date");
        dls = dlSales;
        m_jArticle.setVisible(false);
        m_jBonusArticle.setVisible(false);
        m_jCategory.setVisible(false);
        //btnValidTo.setVisible(false);

        m_sentPromo = dlSales.getPromoTypeList();
        m_jTypeModel = new ComboBoxValModel();

        m_jName.getDocument().addDocumentListener(dirty);
        m_jStartDate.getDocument().addDocumentListener(dirty);
        m_jEndDate.getDocument().addDocumentListener(dirty);
        m_jStartHour.getDocument().addDocumentListener(dirty);
        m_jEndHour.getDocument().addDocumentListener(dirty);
        m_jArticle.getDocument().addDocumentListener(dirty);
        m_jCategory.getDocument().addDocumentListener(dirty);
        m_jAmount.getDocument().addDocumentListener(dirty);
        m_jMin.getDocument().addDocumentListener(dirty);
        m_jMax.getDocument().addDocumentListener(dirty);
        m_jStepAmount.getDocument().addDocumentListener(dirty);
        m_jStepQty.getDocument().addDocumentListener(dirty);
        m_jBonusArticle.getDocument().addDocumentListener(dirty);

        m_jType.addActionListener(dirty);
        m_jType.addActionListener(new java.awt.event.ActionListener() {
 
            @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSelectPromoType(evt);
            }
        });
        
        /*btnValidTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidToActionPerformed(evt);
            }
        });
        
        btnValidFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidToActionPerformed(evt);
            }
        });*/

        writeValueEOF();
    }

    /**
     *
     * @throws BasicException
     */
    public void activate() throws BasicException {
        m_jCatModel = new ComboBoxValModel(m_dlSales.getCategoriesList().list());
        m_jCatName.setModel(m_jCatModel);
        m_jTypeModel = new ComboBoxValModel(m_sentPromo.list());
        m_jType.setModel(m_jTypeModel);
        onSelectPromoType(null);
    }

    /**
     *
     */
    @Override
    public void refresh() {
    }

    /**
     *
     */
    @Override
    public void writeValueEOF() {


        m_sID = null;
/*        m_jName.setText(null);
        m_jTypeModel.setSelectedKey(null);
        m_jStartDate.setText(null);
        m_jEndDate.setText(null);
        m_jStartHour.setText(null);
        m_jEndHour.setText(null);
        m_jArticle.setText(null);
        m_jCategory.setText(null);
        m_jAmount.setText(null);
        m_jMin.setText(null);
        m_jMax.setText(null);
        m_jStepAmount.setText(null);
        m_jStepQty.setText(null);
        m_jBonusArticle.setText(null);

        m_jName.setEnabled(false);
        m_jStartDate.setEnabled(false);
        m_jEndDate.setEnabled(false);
        //m_jStartHour.setEnabled(false);
        //m_jEndHour.setEnabled(false);
        //m_jArticle.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jAmount.setEnabled(false);
        m_jMin.setEnabled(false);
        m_jMax.setEnabled(false);
        m_jStepAmount.setEnabled(false);
        //m_jStepQty.setEnabled(false);
        m_jBonusArticle.setEnabled(false);*/
        
    }

    /**
     *
     */
    @Override
    public void writeValueInsert() {

        m_sID = UUID.randomUUID().toString();
        m_jName.setText(null);
        m_jTypeModel.setSelectedKey(null);
        m_jStartDate.setText(null);
        m_jEndDate.setText(null);
        m_jStartHour.setText(null);
        m_jEndHour.setText(null);
        m_jArticle.setText(null);
        m_jCategory.setText(null);
        m_jAmount.setText(null);
        m_jMin.setText(null);
        m_jMax.setText(null);
        m_jStepAmount.setText(null);
        m_jStepQty.setText(null);
        m_jBonusArticle.setText(null);
        m_jProdName.setText(null);
        m_jBonusProd.setText(null);
        
/*        m_jName.setEnabled(false);
        m_jStartDate.setEnabled(false);
        m_jEndDate.setEnabled(false);
        m_jStartHour.setEnabled(false);
        m_jEndHour.setEnabled(false);
        m_jArticle.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jAmount.setEnabled(false);
        m_jMin.setEnabled(false);
        m_jMax.setEnabled(false);
        m_jStepAmount.setEnabled(false);
        m_jStepQty.setEnabled(false);
        m_jBonusArticle.setEnabled(false);
        m_jType.setEnabled(true);
        * 
        */

    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueDelete(Object value) {

        Object[] promo = (Object[]) value;
        m_sID = Formats.STRING.formatValue(promo[0]);
        m_jName.setText(Formats.STRING.formatValue(promo[1]));
        m_jStartDate.setText(MessageFormat.format("{0,number,#}", promo[2]));
        m_jEndDate.setText(MessageFormat.format("{0,number,#}", promo[3]));
        //m_jStartDate.setText(Formats.STRING.formatValue(promo[2]));
        //m_jEndDate.setText(Formats.STRING.formatValue(promo[3]));
        m_jStartHour.setText(Formats.INT.formatValue(promo[4]));
        m_jEndHour.setText(Formats.INT.formatValue(promo[5]));
        m_jArticle.setText(Formats.STRING.formatValue(promo[6]));
        m_jCategory.setText(Formats.STRING.formatValue(promo[7]));
        m_jTypeModel.setSelectedKey(Formats.INT.formatValue(promo[8]));
        m_jAmount.setText(Formats.INT.formatValue(promo[9]));
        m_jMin.setText(Formats.INT.formatValue(promo[10]));
        m_jMax.setText(Formats.INT.formatValue(promo[11]));
        m_jStepAmount.setText(Formats.INT.formatValue(promo[12]));
        m_jStepQty.setText(Formats.INT.formatValue(promo[13]));
        m_jBonusArticle.setText(Formats.STRING.formatValue(promo[14]));
        try {
            getProdName(m_jArticle.getText());
        } catch (BasicException ex) {
            Logger.getLogger(PromoEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            getBonusName(m_jBonusArticle.getText());
        } catch (BasicException ex) {
            Logger.getLogger(PromoEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            getCatName(m_jCategory.getText());
        } catch (BasicException ex) {
            Logger.getLogger(PromoEditor.class.getName()).log(Level.SEVERE, null, ex);
        }


/*        m_jName.setEnabled(true);
        m_jStartDate.setEnabled(false);
        m_jEndDate.setEnabled(false);
        //m_jStartHour.setEnabled(false);
        //m_jEndHour.setEnabled(false);
        //m_jArticle.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jAmount.setEnabled(false);
        m_jMin.setEnabled(false);
        m_jMax.setEnabled(false);
        m_jStepAmount.setEnabled(false);
        //m_jStepQty.setEnabled(false);
        m_jBonusArticle.setEnabled(false);
        m_jType.setEnabled(true);*/
        
    }

    /**
     *
     * @param value
     */
    @Override
    public void writeValueEdit(Object value) {
        
        Object[] promo = (Object[]) value;
        m_sID = Formats.STRING.formatValue(promo[0]);
        m_jName.setText(Formats.STRING.formatValue(promo[1]));
        m_jStartDate.setText(MessageFormat.format("{0,number,#}", promo[2]));
        m_jEndDate.setText(MessageFormat.format("{0,number,#}", promo[3]));
        //m_jStartDate.setText(Formats.STRING.formatValue(promo[2]));
        //m_jEndDate.setText(Formats.STRING.formatValue(promo[3]));
        m_jStartHour.setText(Formats.INT.formatValue(promo[4]));
        m_jEndHour.setText(Formats.INT.formatValue(promo[5]));
        m_jArticle.setText(Formats.STRING.formatValue(promo[6]));
        m_jCategory.setText(Formats.STRING.formatValue(promo[7]));
        m_jTypeModel.setSelectedKey(Formats.INT.formatValue(promo[8]));
        m_jAmount.setText(Formats.INT.formatValue(promo[9]));
        m_jMin.setText(Formats.INT.formatValue(promo[10]));
        m_jMax.setText(Formats.INT.formatValue(promo[11]));
        m_jStepAmount.setText(Formats.INT.formatValue(promo[12]));
        m_jStepQty.setText(Formats.INT.formatValue(promo[13]));
        m_jBonusArticle.setText(Formats.STRING.formatValue(promo[14]));
        try {
            getProdName(m_jArticle.getText());
        } catch (BasicException ex) {
            Logger.getLogger(PromoEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            getBonusName(m_jBonusArticle.getText());
        } catch (BasicException ex) {
            Logger.getLogger(PromoEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            getCatName(m_jCategory.getText());
        } catch (BasicException ex) {
            Logger.getLogger(PromoEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
/*        m_jName.setEnabled(true);
        m_jStartDate.setEnabled(false);
        m_jEndDate.setEnabled(false);
        //m_jStartHour.setEnabled(false);
        //m_jEndHour.setEnabled(false);
        m_jMax.setEnabled(false);
        m_jMin.setEnabled(false);
        m_jName.setEnabled(false);
        m_jStartDate.setEnabled(false);
        //m_jStartHour.setEnabled(false);
        m_jStepAmount.setEnabled(false);
        //m_jStepQty.setEnabled(false);
        m_jType.setEnabled(true);*/

        Integer _type = new Integer(promo[8].toString());
        
        switch (_type) {

            // discount in %
            case 1:
//                jLabel13.setText("%");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                m_jCategory.setText(null);
                //m_jAmount.setText(null);
                m_jMin.setText(null);
                m_jMax.setText(null);
                m_jStepAmount.setText(null);
                m_jStepQty.setText(null);
                //m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;

            // discount in $
            case 2:
//                jLabel13.setText("$");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                m_jCategory.setText(null);
                //m_jAmount.setText(null);
                m_jMin.setText(null);
                m_jMax.setText(null);
                m_jStepAmount.setText(null);
                m_jStepQty.setText(null);
                m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;

            // Gift / Coupon
            case 3:
//                jLabel13.setText("");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                m_jCategory.setText(null);
                m_jAmount.setText(null);
                m_jMin.setText(null);
                m_jMax.setText(null);
                //m_jStepAmount.setText(null);
                //m_jStepQty.setText(null);
                //m_jBonusArticle.setText(null);
                //m_jBonusProd.setText(null);
                
                break;

            //Get X% of discount on the cheapest article of a category
            case 4:
//                jLabel13.setText("%");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                m_jArticle.setText(null);
                //m_jCategory.setText(null);
                //m_jAmount.setText(null);
                m_jMin.setText(null);
                m_jMax.setText(null);
                m_jStepAmount.setText(null);
                m_jStepQty.setText(null);
                m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;

            //Mix'n'Match (Buy 2 get 3)  
            case 5:
//                jLabel13.setText("");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                m_jCategory.setText(null);
                m_jAmount.setText(null);
                //m_jMin.setText(null);
                //m_jMax.setText(null);
                m_jStepAmount.setText(null);
                //m_jStepQty.setText(null);
                m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;
                
            // discount in % by category
            case 6:
//                jLabel13.setText("%");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                //m_jCategory.setText(null);
                //m_jAmount.setText(null);
                m_jMin.setText(null);
                m_jMax.setText(null);
                m_jStepAmount.setText(null);
                m_jStepQty.setText(null);
                //m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;

        }

    }

    /**
     *
     * @return
     * @throws BasicException
     */
    @Override
    public Object createValue() throws BasicException {


        // Here we will do the integrity check of the promotion, depending on the selected discount value

        if (m_jTypeModel.getSelectedKey() != null) {

            Integer _type = new Integer(m_jTypeModel.getSelectedKey().toString());

            if (m_jName.getText().equals("")) {
                JOptionPane.showConfirmDialog(this, "Discount Name is mandatory", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                return null;
            }
            if ((m_jStartDate.getText().equals("")) || (m_jEndDate.getText().equals(""))) {
                JOptionPane.showConfirmDialog(this, "A period is Mandatory", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                return null;
            }

            if (!(m_jStartDate.getText().equals("")) && !(m_jEndDate.getText().equals(""))) {
                Integer _start = new Integer(m_jStartDate.getText());
                Integer _end = new Integer(m_jEndDate.getText());

                if (_start > _end) {
                    JOptionPane.showConfirmDialog(this, "End Date must greater than Start date", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                    return null;
                }
            }

            if ((m_jArticle.getText().equals("")) && (m_jCategory.getText().equals(""))) {
                JOptionPane.showConfirmDialog(this, "Please fill in a Product or a Category ", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                return null;
            }
            if ((!m_jArticle.getText().equals("")) && (!m_jCategory.getText().equals(""))) {
                JOptionPane.showConfirmDialog(this, "You have to fill in a Product or a Category, but not both", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                return null;
            }

            switch (_type) {

                //1 --> Discount in %
                case 1:

                    if (m_jAmount.getText().equals("")) {
                        JOptionPane.showConfirmDialog(this, "Please add amount of Discount", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                        return null;
                    }
                    break;

                // Discount in money
                case 2:
                    if (m_jAmount.getText().equals("")) {
                        JOptionPane.showConfirmDialog(this, "Please add amount of Discount", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                        return null;
                    }

                    break;

                // Gift / Coupon
                case 3:
                    if (m_jBonusArticle.getText().equals("")) {
                        JOptionPane.showConfirmDialog(this, "Please add the Product for the Gift", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                        return null;
                    }
                    if (m_jStepQty.getText().equals("")) {
                        JOptionPane.showConfirmDialog(this, "Please add the Quantity step", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                        return null;
                    }

                    break;

                //Get X% of discount on the cheapest product of a category
                case 4:
                    if ((m_jCategory.getText().equals(""))) {
                        JOptionPane.showConfirmDialog(this, "Please add a category", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                        return null;
                    }

                    break;

                //Mix'n'Match (Buy 2 get 3)  
                case 5:
                    if (m_jMin.getText().equals("")) {
                        JOptionPane.showConfirmDialog(this, "Please complete Minimum Threshold", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                        return null;
                    }
                    if (m_jMax.getText().equals("")) {
                        JOptionPane.showConfirmDialog(this, "Please complete Minimum Threshold", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                        return null;
                    }
                    if (m_jStepQty.getText().equals("")) {
                        JOptionPane.showConfirmDialog(this, "Please add a Quantity step", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                        return null;
                    }
                    break;
                    
                //6 --> Discount in %
                case 6:

                    if (m_jAmount.getText().equals("")) {
                        JOptionPane.showConfirmDialog(this, "Please add the amount of Discount", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
                        return null;
                    }
                    break;


            }
        } else {
            JOptionPane.showConfirmDialog(this, "You Must select a Discount Type", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String _cle = m_jTypeModel.getSelectedKey().toString();

        Object[] promo = new Object[16];
        promo[0] = m_sID == null ? UUID.randomUUID().toString() : m_sID;
        promo[1] = Formats.STRING.parseValue(m_jName.getText());
        promo[2] = Formats.INT.parseValue(m_jStartDate.getText());
        promo[3] = Formats.INT.parseValue(m_jEndDate.getText());
        promo[4] = Formats.INT.parseValue(m_jStartHour.getText());
        promo[5] = Formats.INT.parseValue(m_jEndHour.getText());
        promo[6] = Formats.STRING.parseValue(m_jArticle.getText());
        promo[7] = Formats.STRING.parseValue(m_jCategory.getText());
        promo[8] = Formats.INT.parseValue(_cle);
        promo[9] = Formats.DOUBLE.parseValue(m_jAmount.getText());
        promo[10] = Formats.INT.parseValue(m_jMin.getText());
        promo[11] = Formats.INT.parseValue(m_jMax.getText());
        promo[12] = Formats.INT.parseValue(m_jStepAmount.getText());
        promo[13] = Formats.INT.parseValue(m_jStepQty.getText());
        promo[14] = Formats.STRING.parseValue(m_jBonusArticle.getText());
        promo[15] = Formats.STRING.parseValue(m_jBonusProd.getText());
        //promo[15] = Formats.STRING.parseValue(_DescBonusArticle);


        return promo;


    }

    /**
     *
     * @return
     */
    @Override
    public Component getComponent() {
        return this;
    }
    

private void onSelectPromoType(java.awt.event.ActionEvent evt) {                                   

    if (m_jTypeModel.getSelectedKey() != null) {

        Integer _type = new Integer(m_jTypeModel.getSelectedKey().toString());
        
        if(m_jCategory.getText().isEmpty()) {
            m_jCatName.setSelectedIndex(-1);
        }

        m_jAmount.setText("");
        m_jBonusArticle.setText("");
        //m_jCategory.setText("");
        m_jStepQty.setText("");
        m_jMin.setText("");
        m_jMax.setText("");
        m_jProdName.setText("");
        m_jBonusProd.setText("");
        m_jProdName.setText(null);
        m_jBonusProd.setText(null);

        switch (_type) {

            // discount in %
            case 1:
//                jLabel13.setText("%");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                m_jCategory.setText(null);
                //m_jAmount.setText(null);
                m_jMin.setText(null);
                m_jMax.setText(null);
                m_jStepAmount.setText(null);
                m_jStepQty.setText(null);
                m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;

            // discount value
            case 2:
//                jLabel13.setText("Value");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                m_jCategory.setText(null);
                //m_jAmount.setText(null);
                m_jMin.setText(null);
                m_jMax.setText(null);
                m_jStepAmount.setText(null);
                m_jStepQty.setText(null);
                m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;

            // Gift / Coupon
            case 3:
//                jLabel13.setText("");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                m_jCategory.setText(null);
                m_jAmount.setText(null);
                //m_jMin.setText(null);
                //m_jMax.setText(null);
                //m_jStepAmount.setText(null);
                //m_jStepQty.setText(null);
                //m_jBonusArticle.setText(null);
                //m_jBonusProd.setText(null);
                
                break;

            //Get X% of discount on the cheapest article of a category
            case 4:
//                jLabel13.setText("%");

                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                m_jArticle.setText(null);
                //m_jCategory.setText(null);
                //m_jAmount.setText(null);
                //m_jMin.setText(null);
                //m_jMax.setText(null);
                m_jStepAmount.setText(null);
                m_jStepQty.setText(null);
                m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;

            //Mix'n'Match (Buy 2 get 3)  
            case 5:
//                jLabel13.setText("");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                m_jCategory.setText(null);
                m_jAmount.setText(null);
                //m_jMin.setText(null);
                //m_jMax.setText(null);
                m_jStepAmount.setText(null);
                //m_jStepQty.setText(null);
                m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;
                
            // discount in % by category
            case 6:
//                jLabel13.setText("%");
                
                //m_jName.setText(null);
                //m_jStartDate.setText(null);
                //m_jEndDate.setText(null);
                //m_jStartHour.setText(null);
                //m_jEndHour.setText(null);
                //m_jTypeModel.setSelectedKey(null);
                //m_jArticle.setText(null);
                //m_jCategory.setText(null);
                //m_jAmount.setText(null);
                m_jMin.setText(null);
                m_jMax.setText(null);
                m_jStepAmount.setText(null);
                m_jStepQty.setText(null);
                //m_jBonusArticle.setText(null);
                m_jBonusProd.setText(null);
                
                break;

        }
    }


}                                  

private void OnCheckAmount(java.awt.event.FocusEvent evt) {                               

    if (!m_jAmount.getText().equals("")) {
        if (!isNumeric(m_jAmount.getText())) {
            JOptionPane.showConfirmDialog(this, "The Amount is not correct", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            m_jAmount.setText(null);
        }
    }
}                              

private void OnCheckMin(java.awt.event.FocusEvent evt) {                            

    if (!m_jMin.getText().equals("")) {
        if (!isNumeric(m_jMin.getText())) {
            JOptionPane.showConfirmDialog(this, "The minimum is not correct", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            m_jMin.setText(null);
            return;
        }
    }
    if ((!m_jMin.getText().equals("")) && (!m_jMax.getText().equals(""))) {
        Integer _min = new Integer(m_jMin.getText());
        Integer _max = new Integer(m_jMax.getText());

        if (_min > _max) {
            JOptionPane.showConfirmDialog(this, "The minimum can't be greater than the maximum", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            m_jMin.setText(null);
        }
    }

}                           

private void OnCheckMax(java.awt.event.FocusEvent evt) {                            


    if (!m_jMax.getText().equals("")) {
        if (!isNumeric(m_jMax.getText())) {
            JOptionPane.showConfirmDialog(this, "The maximum is not correct", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            m_jMax.setText(null);
            return;
        }
    }
    if ((!m_jMin.getText().equals("")) && (!m_jMax.getText().equals(""))) {
        Integer _min = new Integer(m_jMin.getText());
        Integer _max = new Integer(m_jMax.getText());

        if (_max < _min) {
            JOptionPane.showConfirmDialog(this, "The minimum can't be greater than the maximum", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            m_jMin.setText(null);
        }
    }


}                           

private void OnCheckQuantityStep(java.awt.event.FocusEvent evt) {                                     

    if (!m_jStepAmount.getText().equals("")) {
        if (!isNumeric(m_jStepAmount.getText())) {
            JOptionPane.showConfirmDialog(this, "The step is not correct", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            m_jStepAmount.setText(null);
        }
    }


}                                    

private void OnCheckDiscountStep(java.awt.event.FocusEvent evt) {                                     

    if (!m_jStepQty.getText().equals("")) {
        if (!isNumeric(m_jStepQty.getText())) {
            JOptionPane.showConfirmDialog(this, "The step is not correct", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            m_jStepQty.setText(null);
        }
    }

}                                    

private void OnCheckBonus(java.awt.event.FocusEvent evt) {
    if (!m_jBonusArticle.getText().equals("")) {

        try {
            try {
                ProductInfoExt _pie = dls.getProductInfoByReference(m_jBonusArticle.getText());
                m_jBonusArticle.setText(_pie.getID());
                _DescBonusArticle = _pie.getName();
            } catch (NullPointerException e) {
                JOptionPane.showConfirmDialog(this, "This Product doesn't exist", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            }

        } catch (BasicException e) {
            JOptionPane.showConfirmDialog(this, "This Product doesn't exist", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
        }

    }
}

private void OnCheckArticle(java.awt.event.FocusEvent evt) {

    if (!m_jArticle.getText().equals("")) {
        try {
            try {
                ProductInfoExt _pie = dls.getProductInfoByReference(m_jArticle.getText());
                m_jArticle.setText(_pie.getID());
            } catch (NullPointerException e) {
                JOptionPane.showConfirmDialog(this, "This Product doesn't exist", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            }
        } catch (BasicException e) {
            JOptionPane.showConfirmDialog(this, "This Product doesn't exist", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
        }

    }

}

private void OnCheckCategory(java.awt.event.FocusEvent evt) {
    if (!m_jCategory.getText().equals("")) {
        try {
            try {
                CategoryInfo _ci = dls.getCategoryInfo(m_jCategory.getText());
            } catch (NullPointerException e) {
                JOptionPane.showConfirmDialog(this, "This Category doesn't exist", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
            }
        } catch (BasicException e) {
            JOptionPane.showConfirmDialog(this, "This Category doesn't exist", AppLocal.getIntString("Invalid Entry"), JOptionPane.WARNING_MESSAGE);
        }

    }
}

    private boolean isNumeric(String test) {
        try {
            BigInteger bigInteger = new java.math.BigInteger(test);
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_jName = new javax.swing.JTextField();
        m_jArticle = new javax.swing.JTextField();
        m_jBonusArticle = new javax.swing.JTextField();
        m_jCategory = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        m_jStartDate = new javax.swing.JTextField();
        m_jStartHour = new javax.swing.JTextField();
        m_jType = new javax.swing.JComboBox();
        m_jProdName = new javax.swing.JTextField();
        m_jCatName = new javax.swing.JComboBox();
        m_jAmount = new javax.swing.JTextField();
        m_jMin = new javax.swing.JTextField();
        m_jStepAmount = new javax.swing.JTextField();
        m_jStepQty = new javax.swing.JTextField();
        m_jBonusProd = new javax.swing.JTextField();
        btnValidTo = new javax.swing.JButton();
        m_jEndDate = new javax.swing.JTextField();
        btnValidFrom = new javax.swing.JButton();
        m_jEndHour = new javax.swing.JTextField();
        m_jSearch = new javax.swing.JButton();
        m_jMax = new javax.swing.JTextField();
        m_jSearch1 = new javax.swing.JButton();

        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        m_jName.setMaximumSize(new java.awt.Dimension(110, 28));
        m_jName.setMinimumSize(new java.awt.Dimension(110, 28));
        m_jName.setPreferredSize(new java.awt.Dimension(110, 28));

        m_jArticle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jArticle.setPreferredSize(new java.awt.Dimension(110, 28));

        m_jBonusArticle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jBonusArticle.setPreferredSize(new java.awt.Dimension(110, 28));

        m_jCategory.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jCategory.setPreferredSize(new java.awt.Dimension(110, 28));
        m_jCategory.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                m_jCategoryPropertyChange(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("<html><b>Name</b>");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel1.setPreferredSize(new java.awt.Dimension(150, 28));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Date");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Time");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Discount");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Product");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Product Category");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Amount");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Min / Max");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Step Amount");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Quantity");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("Bonus Article");
        jLabel11.setMaximumSize(new java.awt.Dimension(62, 18));
        jLabel11.setMinimumSize(new java.awt.Dimension(62, 18));
        jLabel11.setPreferredSize(new java.awt.Dimension(62, 18));

        m_jStartDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jStartHour.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jStartHour.setPreferredSize(new java.awt.Dimension(110, 28));

        m_jType.setPreferredSize(new java.awt.Dimension(210, 25));

        m_jProdName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jCatName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        m_jCatName.setPreferredSize(new java.awt.Dimension(56, 25));
        m_jCatName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jCatNameActionPerformed(evt);
            }
        });

        m_jAmount.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jAmount.setPreferredSize(new java.awt.Dimension(110, 28));

        m_jMin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jMin.setPreferredSize(new java.awt.Dimension(68, 28));

        m_jStepAmount.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jStepAmount.setPreferredSize(new java.awt.Dimension(110, 28));

        m_jStepQty.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jStepQty.setPreferredSize(new java.awt.Dimension(110, 28));

        m_jBonusProd.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        btnValidTo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/date.png"))); // NOI18N
        btnValidTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidToActionPerformed(evt);
            }
        });

        m_jEndDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        btnValidFrom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/date.png"))); // NOI18N
        btnValidFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidFromActionPerformed(evt);
            }
        });

        m_jEndHour.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jEndHour.setPreferredSize(new java.awt.Dimension(110, 28));

        m_jSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/search24.png"))); // NOI18N
        m_jSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jSearchActionPerformed(evt);
            }
        });

        m_jMax.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        m_jSearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/search24.png"))); // NOI18N
        m_jSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jSearch1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(m_jArticle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m_jBonusArticle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m_jCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(m_jAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(m_jMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m_jMax, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(m_jStepAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(m_jStartHour, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(m_jEndHour, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(m_jStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(116, 116, 116)
                                        .addComponent(btnValidFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(m_jEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(m_jProdName, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(m_jCatName, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(m_jType, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(m_jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnValidTo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(m_jStepQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(m_jBonusProd, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m_jSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jName, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 19, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnValidFrom))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jStartHour, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jEndHour, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(m_jEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnValidTo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(m_jSearch)
                            .addComponent(m_jProdName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jCatName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(m_jAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jMin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jMax, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jStepAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(m_jStepQty, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(m_jBonusProd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(m_jSearch1))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jArticle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jBonusArticle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnValidFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidFromActionPerformed

    Date date;
    try {
        date = (Date) Formats.TIMESTAMP.parseValue(m_jStartDate.getText());
    } catch (BasicException e) {
        date = null;
    }
    date = JCalendarDialog.showCalendarTimeHours(this, date);
    if (date != null) {


        DateFormat formatter_date;
        formatter_date = new SimpleDateFormat("yyyyMMdd");

        DateFormat formatter_heure;
        formatter_heure = new SimpleDateFormat("HH");

        String _date = formatter_date.format(date);
        String _heure = formatter_heure.format(date);
        m_jStartDate.setText(Formats.STRING.formatValue(_date));
        m_jStartHour.setText(Formats.STRING.formatValue(_heure));
    }

    }//GEN-LAST:event_btnValidFromActionPerformed

    private void btnValidToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidToActionPerformed

    Date date;
    try {
        date = (Date) Formats.TIMESTAMP.parseValue(m_jEndDate.getText());
    } catch (BasicException e) {
        date = null;
    }
    date = JCalendarDialog.showCalendarTimeHours(this, date);
    if (date != null) {


        DateFormat formatter_date;
        formatter_date = new SimpleDateFormat("yyyyMMdd");

        DateFormat formatter_heure;
        formatter_heure = new SimpleDateFormat("HH");

        String _date = formatter_date.format(date);
        String _heure = formatter_heure.format(date);

        m_jEndDate.setText(Formats.STRING.formatValue(_date));
        m_jEndHour.setText(Formats.STRING.formatValue(_heure));
    }
    }//GEN-LAST:event_btnValidToActionPerformed

    private void m_jSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jSearchActionPerformed
        ProductInfoExt prod = JProductFinder.showMessage(PromoEditor.this, m_dlSales);
        if (prod != null) {
            //buttonTransition(prod);
            assignProduct(prod);
            try {
                getProdName(prod.getID());
            } catch (BasicException ex) {
                Logger.getLogger(PromoEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_m_jSearchActionPerformed

    private void m_jSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jSearch1ActionPerformed
        ProductInfoExt prod = JProductFinder.showMessage(PromoEditor.this, m_dlSales);
        if (prod != null) {
            //buttonTransition(prod);
            assignProduct1(prod);
            try {
                getBonusName(prod.getID());
            } catch (BasicException ex) {
                Logger.getLogger(PromoEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_m_jSearch1ActionPerformed

    private void m_jCatNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jCatNameActionPerformed
        try {
            getCatID(m_jCatModel.getSelectedText());
        } catch (BasicException ex) {
            Logger.getLogger(PromoEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_m_jCatNameActionPerformed

    private void m_jCategoryPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_m_jCategoryPropertyChange
        if(m_jCategory.getText().isEmpty()) {
            m_jCatName.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_m_jCategoryPropertyChange
   
    private void assignProduct(ProductInfoExt prod) {

            if (prod == null) {
                //m_jArticle.setText(null);
                //Jt_articleid.setText(null);
            } else {
                m_jArticle.setText(prod.getID());
            }

    }
    
    private void assignProduct1(ProductInfoExt prod) {

            if (prod == null) {
                //m_jBonusArticle.setText(null);
                //jt_bonusid.setText(null);
            } else {
                m_jBonusArticle.setText(prod.getID());
            }

    }
    
    private void getCatID(String name) throws BasicException {
        Connection connection;// = null;
        Statement statement;// = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;// = null;
        String prodname = null;

        PropertyUtils properties = new PropertyUtils();

        try {

            String driverName = properties.getDriverName();
            Class.forName(driverName);

            String url = properties.getUrl();
            String username = properties.getDBUser();
            String password = properties.getDBPassword();
            connection = (Connection) DriverManager.getConnection(url, username, password);

            statement = (Statement) connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM CATEGORIES WHERE NAME = '" + name + "'");
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                
                    m_jCategory.setText(id);
                
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            // Could not find the database driver
        } catch (SQLException e) {
            // Could not connect to the database
//            System.out.println(e);


        }
}
    
    private void getCatName(String Id) throws BasicException {
        Connection connection;// = null;
        Statement statement;// = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;// = null;
        String prodname = null;

        PropertyUtils properties = new PropertyUtils();

        try {

            String driverName = properties.getDriverName();
            Class.forName(driverName);

            String url = properties.getUrl();
            String username = properties.getDBUser();
            String password = properties.getDBPassword();
            connection = (Connection) DriverManager.getConnection(url, username, password);

            statement = (Statement) connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM CATEGORIES WHERE ID = '" + Id + "'");
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                
                int ii=m_jCatName.getItemCount();
                for(int i=1; i<ii; i++){
                    String a = m_jCatModel.getElementAt(i).toString();
                    String b = name;
                    if(a.equals(b)){
                        //m_jCatModel.setSelectedKey(i);
                        m_jCatModel.setSelectedItem(name);
                    }
                }
                
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            // Could not find the database driver
        } catch (SQLException e) {
            // Could not connect to the database
//            System.out.println(e);


        }
}
    
    private void getProdName(String Id) throws BasicException {
        Connection connection;// = null;
        Statement statement;// = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;// = null;
        String prodname = null;

        PropertyUtils properties = new PropertyUtils();

        try {

            String driverName = properties.getDriverName();
            Class.forName(driverName);

            String url = properties.getUrl();
            String username = properties.getDBUser();
            String password = properties.getDBPassword();
            connection = (Connection) DriverManager.getConnection(url, username, password);

            statement = (Statement) connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM PRODUCTS WHERE ID = '" + Id + "'");
            while (resultSet.next()) {
                String id = resultSet.getString("NAME");
                m_jProdName.setText(id);
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            // Could not find the database driver
        } catch (SQLException e) {
            // Could not connect to the database
//            System.out.println(e);


        }
}
    
    private void getBonusName(String Id) throws BasicException {
        Connection connection;// = null;
        Statement statement;// = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;// = null;
        String prodname = null;

        PropertyUtils properties = new PropertyUtils();

        try {

            String driverName = properties.getDriverName();
            Class.forName(driverName);

            String url = properties.getUrl();
            String username = properties.getDBUser();
            String password = properties.getDBPassword();
            connection = (Connection) DriverManager.getConnection(url, username, password);

            statement = (Statement) connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM PRODUCTS WHERE ID = '" + Id + "'");
            while (resultSet.next()) {
                String id = resultSet.getString("NAME");
                m_jBonusProd.setText(id);
            }

            connection.close();
        } catch (ClassNotFoundException e) {
            // Could not find the database driver
        } catch (SQLException e) {
            // Could not connect to the database
//            System.out.println(e);


        }
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnValidFrom;
    private javax.swing.JButton btnValidTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField m_jAmount;
    private javax.swing.JTextField m_jArticle;
    private javax.swing.JTextField m_jBonusArticle;
    private javax.swing.JTextField m_jBonusProd;
    private javax.swing.JComboBox m_jCatName;
    private javax.swing.JTextField m_jCategory;
    private javax.swing.JTextField m_jEndDate;
    private javax.swing.JTextField m_jEndHour;
    private javax.swing.JTextField m_jMax;
    private javax.swing.JTextField m_jMin;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextField m_jProdName;
    private javax.swing.JButton m_jSearch;
    private javax.swing.JButton m_jSearch1;
    private javax.swing.JTextField m_jStartDate;
    private javax.swing.JTextField m_jStartHour;
    private javax.swing.JTextField m_jStepAmount;
    private javax.swing.JTextField m_jStepQty;
    private javax.swing.JComboBox m_jType;
    // End of variables declaration//GEN-END:variables
}
