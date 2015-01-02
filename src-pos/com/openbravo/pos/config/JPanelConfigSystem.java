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
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.DirectoryEvent;
import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author JG uniCenta
 */
public class JPanelConfigSystem extends javax.swing.JPanel implements PanelConfig {
    
    private DirtyManager dirty = new DirtyManager();
    
    /** Creates new form JPanelConfigDatabase */
    public JPanelConfigSystem() {
        
        initComponents();
                
        
        jTextAutoLogoffTime.getDocument().addDocumentListener(dirty);
        jMarineOpt.addActionListener(dirty);
        jchkTextOverlay.addActionListener(dirty);
        jchkAutoLogoff.addActionListener(dirty);
        jchkAutoLogoffToTables.addActionListener(dirty);
        jchkShowCustomerDetails.addActionListener(dirty);
        jchkShowWaiterDetails.addActionListener(dirty);
        jCustomerColour.addActionListener(dirty);
        jWaiterColour.addActionListener(dirty);
        jTableNameColour.addActionListener(dirty);
        jTaxIncluded.addActionListener(dirty);
        jCheckPrice00.addActionListener(dirty);          
        jMoveAMountBoxToTop.addActionListener(dirty);
        jCloseCashbtn.addActionListener(dirty); 

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

       
//lets test for our settings       
        String timerCheck =(config.getProperty("till.autotimer"));
        if (timerCheck == null){
            config.setProperty("till.autotimer","100");
        }                
        jTextAutoLogoffTime.setText(config.getProperty("till.autotimer").toString());

        jMarineOpt.setSelected(Boolean.valueOf(config.getProperty("till.marineoption")).booleanValue()); 
        jchkShowCustomerDetails.setSelected(Boolean.valueOf(config.getProperty("table.showcustomerdetails")).booleanValue());
        jchkShowWaiterDetails.setSelected(Boolean.valueOf(config.getProperty("table.showwaiterdetails")).booleanValue());
        jchkTextOverlay.setSelected(Boolean.valueOf(config.getProperty("payments.textoverlay")).booleanValue());        
        jchkAutoLogoff.setSelected(Boolean.valueOf(config.getProperty("till.autoLogoff")).booleanValue());    
        jchkAutoLogoffToTables.setSelected(Boolean.valueOf(config.getProperty("till.autoLogoffrestaurant")).booleanValue());           
        jTaxIncluded.setSelected(Boolean.valueOf(config.getProperty("till.taxincluded")).booleanValue());
        jCheckPrice00.setSelected(Boolean.valueOf(config.getProperty("till.pricewith00")).booleanValue());        
        jMoveAMountBoxToTop.setSelected(Boolean.valueOf(config.getProperty("till.amountattop")).booleanValue());  
        jCloseCashbtn.setSelected(Boolean.valueOf(config.getProperty("screen.600800")).booleanValue());
        
        
        if (config.getProperty("table.customercolour")==null){
            jCustomerColour.setSelectedItem("blue");
        }else{
            jCustomerColour.setSelectedItem(config.getProperty("table.customercolour"));
        }
        if (config.getProperty("table.waitercolour")==null){
            jWaiterColour.setSelectedItem("red");
        }else{
            jWaiterColour.setSelectedItem(config.getProperty("table.waitercolour"));
        }
        if (config.getProperty("table.tablecolour")==null){                
            jTableNameColour.setSelectedItem("black");      
        }else{
            jTableNameColour.setSelectedItem((config.getProperty("table.tablecolour")));  
        }
    
        
        if (jchkAutoLogoff.isSelected()){
                jchkAutoLogoffToTables.setVisible(true);
                jLabelInactiveTime.setVisible(true);
                jLabelTimedMessage.setVisible(true);
                jTextAutoLogoffTime.setVisible(true);
        }else{    
                jchkAutoLogoffToTables.setVisible(false);
                jLabelInactiveTime.setVisible(false);
                jLabelTimedMessage.setVisible(false);
                jTextAutoLogoffTime.setVisible(false);
        }
        
        
        dirty.setDirty(false);
    }
   
    /**
     *
     * @param config
     */
    @Override
    public void saveProperties(AppConfig config) {
        
        config.setProperty("till.autotimer",jTextAutoLogoffTime.getText());
        config.setProperty("till.marineoption", Boolean.toString(jMarineOpt.isSelected()));
        config.setProperty("table.showcustomerdetails", Boolean.toString(jchkShowCustomerDetails.isSelected()));
        config.setProperty("table.showwaiterdetails", Boolean.toString(jchkShowWaiterDetails.isSelected()));        
        config.setProperty("payments.textoverlay", Boolean.toString(jchkTextOverlay.isSelected()));         
        config.setProperty("till.autoLogoff", Boolean.toString(jchkAutoLogoff.isSelected()));                 
        config.setProperty("till.autoLogoffrestaurant", Boolean.toString(jchkAutoLogoffToTables.isSelected()));                        
        config.setProperty("table.customercolour",jCustomerColour.getSelectedItem().toString());
        config.setProperty("table.waitercolour",jWaiterColour.getSelectedItem().toString());
        config.setProperty("table.tablecolour",jTableNameColour.getSelectedItem().toString());         
        config.setProperty("till.taxincluded",Boolean.toString(jTaxIncluded.isSelected()));                     
        config.setProperty("till.pricewith00",Boolean.toString(jCheckPrice00.isSelected()));                         
        config.setProperty("till.amountattop",Boolean.toString(jMoveAMountBoxToTop.isSelected()));         
        config.setProperty("screen.600800",Boolean.toString(jCloseCashbtn.isSelected())); 
        
         
        dirty.setDirty(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jchkAutoLogoff = new javax.swing.JCheckBox();
        jchkAutoLogoffToTables = new javax.swing.JCheckBox();
        jTextAutoLogoffTime = new javax.swing.JTextField();
        jLabelInactiveTime = new javax.swing.JLabel();
        jLabelTimedMessage = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jchkShowCustomerDetails = new javax.swing.JCheckBox();
        jchkShowWaiterDetails = new javax.swing.JCheckBox();
        jLabelCustomerTextColour = new javax.swing.JLabel();
        jCustomerColour = new javax.swing.JComboBox();
        jLabelServerTextColour = new javax.swing.JLabel();
        jWaiterColour = new javax.swing.JComboBox();
        jLabelTableNameTextColour = new javax.swing.JLabel();
        jTableNameColour = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jMarineOpt = new javax.swing.JCheckBox();
        jCloseCashbtn = new javax.swing.JCheckBox();
        jCheckPrice00 = new javax.swing.JCheckBox();
        jchkTextOverlay = new javax.swing.JCheckBox();
        jTaxIncluded = new javax.swing.JCheckBox();
        jMoveAMountBoxToTop = new javax.swing.JCheckBox();

        setPreferredSize(new java.awt.Dimension(650, 450));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("label.autologoffpanel"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel2.setLayout(null);

        jchkAutoLogoff.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jchkAutoLogoff.setText(bundle.getString("label.autologonoff")); // NOI18N
        jchkAutoLogoff.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkAutoLogoff.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkAutoLogoff.setPreferredSize(new java.awt.Dimension(0, 25));
        jchkAutoLogoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkAutoLogoffActionPerformed(evt);
            }
        });
        jPanel2.add(jchkAutoLogoff);
        jchkAutoLogoff.setBounds(10, 20, 190, 25);

        jchkAutoLogoffToTables.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jchkAutoLogoffToTables.setText(bundle.getString("label.autoloffrestaurant")); // NOI18N
        jchkAutoLogoffToTables.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkAutoLogoffToTables.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkAutoLogoffToTables.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel2.add(jchkAutoLogoffToTables);
        jchkAutoLogoffToTables.setBounds(200, 20, 260, 25);

        jTextAutoLogoffTime.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextAutoLogoffTime.setText("0");
        jTextAutoLogoffTime.setMaximumSize(new java.awt.Dimension(0, 25));
        jTextAutoLogoffTime.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextAutoLogoffTime.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel2.add(jTextAutoLogoffTime);
        jTextAutoLogoffTime.setBounds(200, 50, 50, 25);

        jLabelInactiveTime.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelInactiveTime.setText(bundle.getString("label.autolofftime")); // NOI18N
        jLabelInactiveTime.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelInactiveTime.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelInactiveTime.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel2.add(jLabelInactiveTime);
        jLabelInactiveTime.setBounds(30, 50, 170, 25);

        jLabelTimedMessage.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelTimedMessage.setText(bundle.getString("label.autologoffzero")); // NOI18N
        jLabelTimedMessage.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelTimedMessage.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelTimedMessage.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel2.add(jLabelTimedMessage);
        jLabelTimedMessage.setBounds(260, 50, 190, 25);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("label.tabledisplayoptions"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel3.setLayout(null);

        jchkShowCustomerDetails.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jchkShowCustomerDetails.setText(bundle.getString("label.tableshowcustomerdetails")); // NOI18N
        jchkShowCustomerDetails.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkShowCustomerDetails.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkShowCustomerDetails.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel3.add(jchkShowCustomerDetails);
        jchkShowCustomerDetails.setBounds(10, 20, 220, 25);

        jchkShowWaiterDetails.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jchkShowWaiterDetails.setText(bundle.getString("label.tableshowwaiterdetails")); // NOI18N
        jchkShowWaiterDetails.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkShowWaiterDetails.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkShowWaiterDetails.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel3.add(jchkShowWaiterDetails);
        jchkShowWaiterDetails.setBounds(10, 60, 220, 23);

        jLabelCustomerTextColour.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelCustomerTextColour.setText(bundle.getString("label.textcolourcustomer")); // NOI18N
        jLabelCustomerTextColour.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelCustomerTextColour.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelCustomerTextColour.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel3.add(jLabelCustomerTextColour);
        jLabelCustomerTextColour.setBounds(240, 20, 130, 25);

        jCustomerColour.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCustomerColour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "black", "blue", "grey", "green", "orange", "red", "white", "yellow" }));
        jCustomerColour.setMaximumSize(new java.awt.Dimension(0, 25));
        jCustomerColour.setMinimumSize(new java.awt.Dimension(0, 0));
        jCustomerColour.setPreferredSize(new java.awt.Dimension(0, 25));
        jCustomerColour.setSelectedItem("blue");
        jCustomerColour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCustomerColourActionPerformed(evt);
            }
        });
        jPanel3.add(jCustomerColour);
        jCustomerColour.setBounds(380, 20, 200, 30);

        jLabelServerTextColour.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelServerTextColour.setText(bundle.getString("label.textcolourwaiter")); // NOI18N
        jLabelServerTextColour.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelServerTextColour.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelServerTextColour.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel3.add(jLabelServerTextColour);
        jLabelServerTextColour.setBounds(240, 60, 130, 25);

        jWaiterColour.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jWaiterColour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "black", "blue", "grey", "green", "orange", "red", "white", "yellow" }));
        jWaiterColour.setMaximumSize(new java.awt.Dimension(0, 25));
        jWaiterColour.setMinimumSize(new java.awt.Dimension(0, 0));
        jWaiterColour.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel3.add(jWaiterColour);
        jWaiterColour.setBounds(380, 60, 200, 30);

        jLabelTableNameTextColour.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabelTableNameTextColour.setText(bundle.getString("label.textclourtablename")); // NOI18N
        jLabelTableNameTextColour.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabelTableNameTextColour.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabelTableNameTextColour.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel3.add(jLabelTableNameTextColour);
        jLabelTableNameTextColour.setBounds(240, 100, 130, 30);

        jTableNameColour.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableNameColour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "black", "blue", "grey", "green", "orange", "red", "white", "yellow" }));
        jTableNameColour.setMaximumSize(new java.awt.Dimension(0, 25));
        jTableNameColour.setMinimumSize(new java.awt.Dimension(0, 0));
        jTableNameColour.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel3.add(jTableNameColour);
        jTableNameColour.setBounds(380, 100, 200, 30);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("label.general"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel4.setLayout(null);

        jMarineOpt.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jMarineOpt.setText(bundle.getString("label.marine")); // NOI18N
        jMarineOpt.setMaximumSize(new java.awt.Dimension(0, 25));
        jMarineOpt.setMinimumSize(new java.awt.Dimension(0, 0));
        jMarineOpt.setPreferredSize(new java.awt.Dimension(180, 30));
        jPanel4.add(jMarineOpt);
        jMarineOpt.setBounds(10, 20, 180, 30);

        jCloseCashbtn.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jCloseCashbtn.setText(bundle.getString("message.systemclosecash")); // NOI18N
        jCloseCashbtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jCloseCashbtn.setMaximumSize(new java.awt.Dimension(0, 25));
        jCloseCashbtn.setMinimumSize(new java.awt.Dimension(0, 0));
        jCloseCashbtn.setPreferredSize(new java.awt.Dimension(180, 30));
        jPanel4.add(jCloseCashbtn);
        jCloseCashbtn.setBounds(210, 20, 180, 30);

        jCheckPrice00.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jCheckPrice00.setText(bundle.getString("label.pricewith00")); // NOI18N
        jCheckPrice00.setToolTipText("");
        jCheckPrice00.setMaximumSize(new java.awt.Dimension(0, 25));
        jCheckPrice00.setMinimumSize(new java.awt.Dimension(0, 0));
        jCheckPrice00.setPreferredSize(new java.awt.Dimension(180, 30));
        jCheckPrice00.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckPrice00ActionPerformed(evt);
            }
        });
        jPanel4.add(jCheckPrice00);
        jCheckPrice00.setBounds(410, 20, 180, 30);

        jchkTextOverlay.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jchkTextOverlay.setText(bundle.getString("label.currencybutton")); // NOI18N
        jchkTextOverlay.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jchkTextOverlay.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkTextOverlay.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkTextOverlay.setPreferredSize(new java.awt.Dimension(180, 30));
        jPanel4.add(jchkTextOverlay);
        jchkTextOverlay.setBounds(10, 50, 180, 30);

        jTaxIncluded.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTaxIncluded.setText(bundle.getString("label.taxincluded")); // NOI18N
        jTaxIncluded.setMaximumSize(new java.awt.Dimension(0, 25));
        jTaxIncluded.setMinimumSize(new java.awt.Dimension(0, 0));
        jTaxIncluded.setPreferredSize(new java.awt.Dimension(180, 30));
        jPanel4.add(jTaxIncluded);
        jTaxIncluded.setBounds(210, 50, 180, 30);

        jMoveAMountBoxToTop.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jMoveAMountBoxToTop.setText(bundle.getString("label.inputamount")); // NOI18N
        jMoveAMountBoxToTop.setMaximumSize(new java.awt.Dimension(0, 25));
        jMoveAMountBoxToTop.setMinimumSize(new java.awt.Dimension(0, 0));
        jMoveAMountBoxToTop.setPreferredSize(new java.awt.Dimension(180, 30));
        jPanel4.add(jMoveAMountBoxToTop);
        jMoveAMountBoxToTop.setBounds(410, 50, 180, 30);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jchkAutoLogoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkAutoLogoffActionPerformed
        if (jchkAutoLogoff.isSelected()){
                jchkAutoLogoffToTables.setVisible(true);
                jLabelInactiveTime.setVisible(true);
                jLabelTimedMessage.setVisible(true);
                jTextAutoLogoffTime.setVisible(true);
        }else{    
                jchkAutoLogoffToTables.setVisible(false);
                jLabelInactiveTime.setVisible(false);
                jLabelTimedMessage.setVisible(false);
                jTextAutoLogoffTime.setVisible(false);
        }
    }//GEN-LAST:event_jchkAutoLogoffActionPerformed

    private void jCheckPrice00ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckPrice00ActionPerformed

    }//GEN-LAST:event_jCheckPrice00ActionPerformed

    private void jCustomerColourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCustomerColourActionPerformed

    }//GEN-LAST:event_jCustomerColourActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckPrice00;
    private javax.swing.JCheckBox jCloseCashbtn;
    private javax.swing.JComboBox jCustomerColour;
    private javax.swing.JLabel jLabelCustomerTextColour;
    private javax.swing.JLabel jLabelInactiveTime;
    private javax.swing.JLabel jLabelServerTextColour;
    private javax.swing.JLabel jLabelTableNameTextColour;
    private javax.swing.JLabel jLabelTimedMessage;
    private javax.swing.JCheckBox jMarineOpt;
    private javax.swing.JCheckBox jMoveAMountBoxToTop;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JComboBox jTableNameColour;
    private javax.swing.JCheckBox jTaxIncluded;
    private javax.swing.JTextField jTextAutoLogoffTime;
    private javax.swing.JComboBox jWaiterColour;
    private javax.swing.JCheckBox jchkAutoLogoff;
    private javax.swing.JCheckBox jchkAutoLogoffToTables;
    private javax.swing.JCheckBox jchkShowCustomerDetails;
    private javax.swing.JCheckBox jchkShowWaiterDetails;
    private javax.swing.JCheckBox jchkTextOverlay;
    // End of variables declaration//GEN-END:variables
    
}
