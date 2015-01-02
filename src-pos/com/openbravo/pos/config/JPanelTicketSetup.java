//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2014 uniCenta
//    http://www.unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.AppConfig;
import java.awt.Component;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author JG uniCenta
 */
public class JPanelTicketSetup extends javax.swing.JPanel implements PanelConfig {
    
    private DirtyManager dirty = new DirtyManager();
    private String receipt="1";
    private Integer x = 0;
    private String receiptSize;
    private String pickupSize;
    private Integer ps = 0;

    /**
     *
     */
    public JPanelTicketSetup() {
        
        initComponents();
                          
        jReceiptSize.addChangeListener(dirty);
        jPickupSize.addChangeListener(dirty);
        jTextReceiptPrefix.getDocument().addDocumentListener(dirty);
        m_jReceiptPrintOff.addActionListener(dirty);
        
/** Added: JG 23 July 13 */        
        jchkSCOnOff.addActionListener(dirty);
        jchkSCRestaurant.addActionListener(dirty);        
        jTextSCRate.getDocument().addDocumentListener(dirty);        
/** - */  
        
    }

    /**
     *
     * @return
     */
    @Override
    public boolean hasChanged() {
        return dirty.isDirty();
    }
    
    /**
     *
     * @return
     */
    @Override
    public Component getConfigComponent() {
        return this;
    }
   
    /**
     *
     * @param config
     */
    @Override
    public void loadProperties(AppConfig config) {

        receiptSize =(config.getProperty("till.receiptsize"));
        if (receiptSize == null || "".equals(receiptSize)){
            jReceiptSize.setModel(new SpinnerNumberModel(1,1,20,1));
        } else {            
            jReceiptSize.setModel(new SpinnerNumberModel(Integer.parseInt(receiptSize),1,20,1));
        }                

        pickupSize =(config.getProperty("till.pickupsize"));
        if (pickupSize == null || "".equals(pickupSize)){
            jPickupSize.setModel(new SpinnerNumberModel(1,1,20,1));
        } else {            
            jPickupSize.setModel(new SpinnerNumberModel(Integer.parseInt(pickupSize),1,20,1));
        }        
        
        jTextReceiptPrefix.setText(config.getProperty("till.receiptprefix"));        
// build the example receipt using the loaded details        
        receipt="";
        x=1;
        while (x < (Integer)jReceiptSize.getValue()){
            receipt += "0";
        x++; 
    }
         
        receipt += "1";
         jTicketExample.setText(jTextReceiptPrefix.getText()+receipt);  
         m_jReceiptPrintOff.setSelected(Boolean.valueOf(config.getProperty("till.receiptprintoff")).booleanValue()); 
        
        dirty.setDirty(false);

/** Added: JG 23 July 13 */      
        String SCCheck =(config.getProperty("till.SCRate"));
        if (SCCheck == null){
            config.setProperty("till.SCRate","0");
        }                
        jTextSCRate.setText(config.getProperty("till.SCRate").toString());
        jchkSCOnOff.setSelected(Boolean.valueOf(config.getProperty("till.SCOnOff")).booleanValue());    
        jchkSCRestaurant.setSelected(Boolean.valueOf(config.getProperty("till.SCRestaurant")).booleanValue());
        
        if (jchkSCOnOff.isSelected()){
                jchkSCRestaurant.setVisible(true);
                jLabelSCRate.setVisible(true);
                jTextSCRate.setVisible(true);
                jLabelSCRatePerCent.setVisible(true);
        }else{    
                jchkSCRestaurant.setVisible(false);
                jLabelSCRate.setVisible(false);
                jTextSCRate.setVisible(false);
                jLabelSCRatePerCent.setVisible(false);
        }            
/** - */        
                
        
    }
   
    /**
     *
     * @param config
     */
    @Override
    public void saveProperties(AppConfig config) {
        
        config.setProperty("till.receiptprefix", jTextReceiptPrefix.getText());
        config.setProperty("till.receiptsize", jReceiptSize.getValue().toString());
        config.setProperty("till.pickupsize", jPickupSize.getValue().toString());        
        config.setProperty("till.receiptprintoff",Boolean.toString(m_jReceiptPrintOff.isSelected()));
        
/** Added: JG 23 July 13 */
        config.setProperty("till.SCOnOff",Boolean.toString(jchkSCOnOff.isSelected()));
        config.setProperty("till.SCRate",jTextSCRate.getText());
        config.setProperty("till.SCRestaurant",Boolean.toString(jchkSCRestaurant.isSelected()));
/** - */             
        
        dirty.setDirty(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jReceiptSize = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jTextReceiptPrefix = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTicketExample = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPickupSize = new javax.swing.JSpinner();
        m_jReceiptPrintOff = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jchkSCOnOff = new javax.swing.JCheckBox();
        jchkSCRestaurant = new javax.swing.JCheckBox();
        jTextSCRate = new javax.swing.JTextField();
        jLabelSCRate = new javax.swing.JLabel();
        jLabelSCRatePerCent = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jTextField2.setText("jTextField2");

        setPreferredSize(new java.awt.Dimension(700, 500));
        setLayout(null);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("label.configreceipt"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel1.setLayout(null);

        jReceiptSize.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jReceiptSize.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        jReceiptSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jReceiptSizeStateChanged(evt);
            }
        });
        jPanel1.add(jReceiptSize);
        jReceiptSize.setBounds(190, 20, 50, 40);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText(bundle.getString("Label.ticketsetupnumber")); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 20, 160, 40);

        jTextReceiptPrefix.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextReceiptPrefix.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextReceiptPrefix.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextReceiptPrefixKeyReleased(evt);
            }
        });
        jPanel1.add(jTextReceiptPrefix);
        jTextReceiptPrefix.setBounds(380, 20, 80, 40);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(bundle.getString("Label.ticketsetupprefix")); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(250, 20, 130, 40);

        jTicketExample.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTicketExample.setText("1");
        jTicketExample.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTicketExample.setEnabled(false);
        jPanel1.add(jTicketExample);
        jTicketExample.setBounds(510, 20, 170, 40);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(bundle.getString("label.pickupcodesize")); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 70, 160, 40);

        jPickupSize.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jPickupSize.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        jPickupSize.setToolTipText("");
        jPickupSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jPickupSizeStateChanged(evt);
            }
        });
        jPanel1.add(jPickupSize);
        jPickupSize.setBounds(190, 70, 50, 40);

        m_jReceiptPrintOff.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jReceiptPrintOff.setText(bundle.getString("label.receiptprint")); // NOI18N
        m_jReceiptPrintOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jReceiptPrintOffActionPerformed(evt);
            }
        });
        jPanel1.add(m_jReceiptPrintOff);
        m_jReceiptPrintOff.setBounds(10, 120, 180, 30);

        add(jPanel1);
        jPanel1.setBounds(10, 10, 730, 160);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("label.SChargepanel"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel5.setLayout(null);

        jchkSCOnOff.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jchkSCOnOff.setText(bundle.getString("label.SCOnOff")); // NOI18N
        jchkSCOnOff.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkSCOnOff.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkSCOnOff.setPreferredSize(new java.awt.Dimension(0, 25));
        jchkSCOnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkSCOnOffActionPerformed(evt);
            }
        });
        jPanel5.add(jchkSCOnOff);
        jchkSCOnOff.setBounds(10, 20, 190, 25);

        jchkSCRestaurant.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jchkSCRestaurant.setText(bundle.getString("label.SCRestaurant")); // NOI18N
        jchkSCRestaurant.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkSCRestaurant.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkSCRestaurant.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel5.add(jchkSCRestaurant);
        jchkSCRestaurant.setBounds(200, 20, 160, 25);

        jTextSCRate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextSCRate.setText("0");
        jTextSCRate.setMaximumSize(new java.awt.Dimension(0, 25));
        jTextSCRate.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextSCRate.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel5.add(jTextSCRate);
        jTextSCRate.setBounds(170, 50, 50, 25);

        jLabelSCRate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelSCRate.setText(bundle.getString("label.SCRate")); // NOI18N
        jLabelSCRate.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelSCRate.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelSCRate.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel5.add(jLabelSCRate);
        jLabelSCRate.setBounds(10, 50, 150, 25);

        jLabelSCRatePerCent.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelSCRatePerCent.setText(bundle.getString("label.SCZero")); // NOI18N
        jLabelSCRatePerCent.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelSCRatePerCent.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelSCRatePerCent.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel5.add(jLabelSCRatePerCent);
        jLabelSCRatePerCent.setBounds(230, 50, 50, 25);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Service Charge function in development");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(380, 10, 290, 70);

        add(jPanel5);
        jPanel5.setBounds(10, 180, 730, 90);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextReceiptPrefixKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextReceiptPrefixKeyReleased

        jTicketExample.setText(jTextReceiptPrefix.getText()+ receipt);
    }//GEN-LAST:event_jTextReceiptPrefixKeyReleased

    private void jReceiptSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jReceiptSizeStateChanged

        receipt="";
        x=1;
        while (x < (Integer)jReceiptSize.getValue()){
            receipt += "0";
        x++; 
    }
        receipt += "1";
         jTicketExample.setText(jTextReceiptPrefix.getText()+receipt);
         
    }//GEN-LAST:event_jReceiptSizeStateChanged

    private void jPickupSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jPickupSizeStateChanged

    }//GEN-LAST:event_jPickupSizeStateChanged

    private void jchkSCOnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkSCOnOffActionPerformed
        if (jchkSCOnOff.isSelected()){
            jchkSCRestaurant.setVisible(true);
            jLabelSCRate.setVisible(true);
            jTextSCRate.setVisible(true);
            jLabelSCRatePerCent.setVisible(true);
        }else{
            jchkSCRestaurant.setVisible(false);
            jLabelSCRate.setVisible(false);
            jTextSCRate.setVisible(false);
            jLabelSCRatePerCent.setVisible(false);
        }
    }//GEN-LAST:event_jchkSCOnOffActionPerformed

    private void m_jReceiptPrintOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jReceiptPrintOffActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_m_jReceiptPrintOffActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelSCRate;
    private javax.swing.JLabel jLabelSCRatePerCent;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSpinner jPickupSize;
    private javax.swing.JSpinner jReceiptSize;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextReceiptPrefix;
    private javax.swing.JTextField jTextSCRate;
    private javax.swing.JTextField jTicketExample;
    private javax.swing.JCheckBox jchkSCOnOff;
    private javax.swing.JCheckBox jchkSCRestaurant;
    private javax.swing.JCheckBox m_jReceiptPrintOff;
    // End of variables declaration//GEN-END:variables
    
}
