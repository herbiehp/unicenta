//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (C) 2008-2009 Openbravo, S.L.
//    Copyright (c) 2009-2004 uniCenta
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
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.ConfigPaymentPanelBluePay20POST;
import com.openbravo.pos.payment.ConfigPaymentPanelBluePayAUTHNETEMU;
import com.openbravo.pos.payment.ConfigPaymentPanelCaixa;
import com.openbravo.pos.payment.ConfigPaymentPanelEmpty;
import com.openbravo.pos.payment.ConfigPaymentPanelGeneric;
import com.openbravo.pos.payment.ConfigPaymentPanelLinkPoint;
import com.openbravo.pos.payment.PaymentConfiguration;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adrianromero
 * @author Mikel Irurita
 */
public class JPanelConfigPayment extends javax.swing.JPanel implements PanelConfig {

    private final DirtyManager dirty = new DirtyManager();
//    private Map<String, PaymentConfiguration> paymentsName = new HashMap<String, PaymentConfiguration>();
    private final Map<String, PaymentConfiguration> paymentsName = new HashMap<>();
    private PaymentConfiguration pc;
    
    /** Creates new form JPanelConfigPayment */
    public JPanelConfigPayment() {
        
        initComponents();
                
        // dirty manager
        jcboCardReader.addActionListener(dirty);
        jcboPaymentGateway.addActionListener(dirty);
        jchkPaymentTest.addActionListener(dirty);
        
        // Payment Provider                
        initPayments("Not defined", new ConfigPaymentPanelEmpty());
        initPayments("external", new ConfigPaymentPanelEmpty());
        initPayments("PayPoint / SecPay", new ConfigPaymentPanelGeneric());
        initPayments("AuthorizeNet", new ConfigPaymentPanelGeneric());
        initPayments("BluePay AUTH.NET EMU", new ConfigPaymentPanelBluePay20POST()); // JG Added BluePay re: Walter Wojick
        initPayments("BluePay 2.0 POST", new ConfigPaymentPanelBluePay20POST()); // JG Added BluePay re: Walter Wojick
        initPayments("Planetauthorize", new ConfigPaymentPanelGeneric());
        initPayments("First Data / LinkPoint / YourPay", new ConfigPaymentPanelLinkPoint());
        initPayments("PaymentsGateway.net", new ConfigPaymentPanelGeneric());
        initPayments("La Caixa (Spain)", new ConfigPaymentPanelCaixa());
        
        // Lector de tarjetas.
        jcboCardReader.addItem("Not defined");
        jcboCardReader.addItem("Generic");
        jcboCardReader.addItem("Intelligent");
        jcboCardReader.addItem("Keyboard");
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

        jcboCardReader.setSelectedItem(config.getProperty("payment.magcardreader"));
        jcboPaymentGateway.setSelectedItem(config.getProperty("payment.gateway"));
        jchkPaymentTest.setSelected(Boolean.parseBoolean(config.getProperty("payment.testmode")));       
        pc.loadProperties(config);
        dirty.setDirty(false);
    }
   
    /**
     *
     * @param config
     */
    @Override
    public void saveProperties(AppConfig config) {
        
        config.setProperty("payment.magcardreader", comboValue(jcboCardReader.getSelectedItem()));
        config.setProperty("payment.gateway", comboValue(jcboPaymentGateway.getSelectedItem()));
        config.setProperty("payment.testmode", Boolean.toString(jchkPaymentTest.isSelected()));
        pc.saveProperties(config);
        dirty.setDirty(false);
    }
    
    private void initPayments(String name, PaymentConfiguration pc) {
        jcboPaymentGateway.addItem(name);
        paymentsName.put(name, pc);
    }
     
    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jcboPaymentGateway = new javax.swing.JComboBox();
        jchkPaymentTest = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jcboCardReader = new javax.swing.JComboBox();

        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(600, 450));

        jPanel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 200));
        jPanel2.setLayout(new java.awt.GridLayout(1, 1));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText(AppLocal.getIntString("label.paymentgateway")); // NOI18N
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel13.setPreferredSize(new java.awt.Dimension(100, 30));

        jcboPaymentGateway.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboPaymentGateway.setPreferredSize(new java.awt.Dimension(200, 30));
        jcboPaymentGateway.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboPaymentGatewayActionPerformed(evt);
            }
        });

        jchkPaymentTest.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jchkPaymentTest.setText(AppLocal.getIntString("label.paymenttestmode")); // NOI18N
        jchkPaymentTest.setPreferredSize(new java.awt.Dimension(80, 30));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText(AppLocal.getIntString("label.magcardreader")); // NOI18N
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel11.setPreferredSize(new java.awt.Dimension(100, 30));

        jcboCardReader.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboCardReader.setPreferredSize(new java.awt.Dimension(200, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jcboCardReader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jcboPaymentGateway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jchkPaymentTest, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboCardReader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboPaymentGateway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jchkPaymentTest, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void jcboPaymentGatewayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboPaymentGatewayActionPerformed
    pc = paymentsName.get(comboValue(jcboPaymentGateway.getSelectedItem()));

    if (pc != null) {
        jPanel2.removeAll();
        jPanel2.add(pc.getComponent());
        jPanel2.revalidate();
        jPanel2.repaint(); 
    }
}//GEN-LAST:event_jcboPaymentGatewayActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox jcboCardReader;
    private javax.swing.JComboBox jcboPaymentGateway;
    private javax.swing.JCheckBox jchkPaymentTest;
    // End of variables declaration//GEN-END:variables
    
}
