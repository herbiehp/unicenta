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

package com.openbravo.pos.payment;

import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSystem;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author adrianromero
 */
public abstract class JPaymentSelect extends javax.swing.JDialog 
                            implements JPaymentNotifier {
    
    private PaymentInfoList m_aPaymentInfo;
    private boolean printselected;
    
    private boolean accepted;
    
    private AppView app;
    private double m_dTotal; 
    private CustomerInfoExt customerext;
    private DataLogicSystem dlSystem;
    
// JG 16 May 12 use diamond inference
    private final Map<String, JPaymentInterface> payments = new HashMap<>();
    private String m_sTransactionID;
    
    
    /** Creates new form JPaymentSelect
     * @param parent
     * @param modal
     * @param o */
    protected JPaymentSelect(java.awt.Frame parent, boolean modal, ComponentOrientation o) {
        super(parent, modal);
        initComponents();    
        
        this.applyComponentOrientation(o);
        
        getRootPane().setDefaultButton(m_jButtonOK); 
   
    }
    /** Creates new form JPaymentSelect
     * @param parent
     * @param modal
     * @param o */
    protected JPaymentSelect(java.awt.Dialog parent, boolean modal, ComponentOrientation o) {
        super(parent, modal);
        initComponents();    

        this.applyComponentOrientation(o);        
    }

    /**
     *
     * @param app
     */
    public void init(AppView app) {
        this.app = app;
        dlSystem = (DataLogicSystem) app.getBean("com.openbravo.pos.forms.DataLogicSystem");
        printselected = true;
    }
    
    /**
     *
     * @param value
     */
    public void setPrintSelected(boolean value) {
        printselected = value;
    }
    
    /**
     *
     * @return
     */
    public boolean isPrintSelected() {
        return printselected;
    }

    /**
     *
     * @return
     */
    public List<PaymentInfo> getSelectedPayments() {
        return m_aPaymentInfo.getPayments();
    }
            
    /**
     *
     * @param total
     * @param customerext
     * @return
     */
    public boolean showDialog(double total, CustomerInfoExt customerext) {
        
        m_aPaymentInfo = new PaymentInfoList();
        accepted = false;
        
        m_dTotal = total;
        
        this.customerext = customerext;        
        
        setPrintSelected(!Boolean.parseBoolean(app.getProperties().getProperty("till.receiptprintoff")));
        m_jButtonPrint.setSelected(printselected);

        m_jTotalEuros.setText(Formats.CURRENCY.formatValue(m_dTotal));
        
        addTabs();

        if (m_jTabPayment.getTabCount() == 0) {
            // No payment panels available            
            m_aPaymentInfo.add(getDefaultPayment(total));
            accepted = true;            
        } else {
            getRootPane().setDefaultButton(m_jButtonOK);
            printState();
            setVisible(true);
        }
        
        // gets the print button state
        printselected = m_jButtonPrint.isSelected();
        
        // remove all tabs        
        m_jTabPayment.removeAll();
        
        return accepted;
    }

    /**
     *
     */
    protected abstract void addTabs();

    /**
     *
     * @param isPositive
     * @param isComplete
     */
    protected abstract void setStatusPanel(boolean isPositive, boolean isComplete);

    /**
     *
     * @param total
     * @return
     */
    protected abstract PaymentInfo getDefaultPayment(double total);
    
    /**
     *
     * @param value
     */
    protected void setOKEnabled(boolean value) {
        m_jButtonOK.setEnabled(value);        
    }
    
    /**
     *
     * @param value
     */
    protected void setAddEnabled(boolean value) {
        m_jButtonAdd.setEnabled(value);
    }
        
    /**
     *
     * @param jpay
     */
    protected void addTabPayment(JPaymentCreator jpay) {
        if (app.getAppUserView().getUser().hasPermission(jpay.getKey())) {
            
            JPaymentInterface jpayinterface = payments.get(jpay.getKey());
            if (jpayinterface == null) {
                jpayinterface = jpay.createJPayment();
                payments.put(jpay.getKey(), jpayinterface);
            }
            
            jpayinterface.getComponent().applyComponentOrientation(getComponentOrientation());
            m_jTabPayment.addTab(
                    AppLocal.getIntString(jpay.getLabelKey()),
                    new javax.swing.ImageIcon(getClass().getResource(jpay.getIconKey())),
                    jpayinterface.getComponent());
        }
    }

    /**
     *
     */
    public interface JPaymentCreator {

        /**
         *
         * @return
         */
        public JPaymentInterface createJPayment();

        /**
         *
         * @return
         */
        public String getKey();

        /**
         *
         * @return
         */
        public String getLabelKey();

        /**
         *
         * @return
         */
        public String getIconKey();
    }

    /**
     *
     */
    public class JPaymentCashCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentCashPos(JPaymentSelect.this, dlSystem);
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "payment.cash"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.cash"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/cash.png"; }
    }
        
    /**
     *
     */
    public class JPaymentChequeCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentCheque(JPaymentSelect.this);
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "payment.cheque"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.cheque"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/cheque.png"; }
    }

    /**
     *
     */
    public class JPaymentPaperCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentPaper(JPaymentSelect.this, "paperin");
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "payment.paper"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.paper"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/voucher.png"; }
    }
   
    /**
     *
     */
    public class JPaymentMagcardCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentMagcard(app, JPaymentSelect.this);
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "payment.magcard"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.magcard"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/ccard.png"; }
    }
        
    /**
     *
     */
    public class JPaymentFreeCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentFree(JPaymentSelect.this);
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "payment.free"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.free"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/wallet.png"; }
    }
        
    /**
     *
     */
    public class JPaymentDebtCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentDebt(JPaymentSelect.this);
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "payment.debt"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.debt"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/customer.png"; }
    }

    /**
     *
     */
    public class JPaymentCashRefundCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentRefund(JPaymentSelect.this, "cashrefund");
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "refund.cash"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.cashrefund"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/cash.png"; }
    }
        
    /**
     *
     */
    public class JPaymentChequeRefundCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentRefund(JPaymentSelect.this, "chequerefund");
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "refund.cheque"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.chequerefund"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/cheque.png"; }
    }
       
    /**
     *
     */
    public class JPaymentPaperRefundCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentRefund(JPaymentSelect.this, "paperout");
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "refund.paper"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.paper"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/voucher.png"; }
    }

    /**
     *
     */
    public class JPaymentMagcardRefundCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
       public JPaymentInterface createJPayment() {     
            return new JPaymentMagcard(app, JPaymentSelect.this);
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "refund.magcard"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.magcard"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/ccard.png"; }
    }

// * Bank Payment receipt - Thanks Steve Clough! August 2011

    /**
     *
     */
            public class JPaymentBankCreator implements JPaymentCreator {

        /**
         *
         * @return
         */
        @Override
        public JPaymentInterface createJPayment() {
            return new JPaymentBank(JPaymentSelect.this);
        }

        /**
         *
         * @return
         */
        @Override
        public String getKey() { return "payment.bank"; }

        /**
         *
         * @return
         */
        @Override
        public String getLabelKey() { return "tab.bank"; }

        /**
         *
         * @return
         */
        @Override
        public String getIconKey() { return "/com/openbravo/images/bank.png"; }
    }
    
    /**
     *
     * @param value
     */
    protected void setHeaderVisible(boolean value) {
        jPanel6.setVisible(value);
    }
    
    private void printState() {
        
        m_jRemaininglEuros.setText(Formats.CURRENCY.formatValue(m_dTotal - m_aPaymentInfo.getTotal()));
        m_jButtonRemove.setEnabled(!m_aPaymentInfo.isEmpty());
        m_jTabPayment.setSelectedIndex(0); // selecciono el primero
        ((JPaymentInterface) m_jTabPayment.getSelectedComponent()).activate(customerext, m_dTotal - m_aPaymentInfo.getTotal(), m_sTransactionID);
    }
    
    /**
     *
     * @param parent
     * @return
     */
    protected static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        } else if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        } else {
            return getWindow(parent.getParent());
        }
    }

    /**
     *
     * @param isPositive
     * @param isComplete
     */
    @Override
    public void setStatus(boolean isPositive, boolean isComplete) {
        
        setStatusPanel(isPositive, isComplete);
    }
    
    /**
     *
     * @param tID
     */
    public void setTransactionID(String tID){
        this.m_sTransactionID = tID;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        m_jLblTotalEuros1 = new javax.swing.JLabel();
        m_jTotalEuros = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        m_jLblRemainingEuros = new javax.swing.JLabel();
        m_jRemaininglEuros = new javax.swing.JLabel();
        m_jButtonAdd = new javax.swing.JButton();
        m_jButtonRemove = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        m_jTabPayment = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        m_jButtonCancel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        m_jButtonOK = new javax.swing.JButton();
        m_jButtonPrint = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(AppLocal.getIntString("payment.title")); // NOI18N
        setResizable(false);

        m_jLblTotalEuros1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        m_jLblTotalEuros1.setText(AppLocal.getIntString("label.totalcash")); // NOI18N
        jPanel4.add(m_jLblTotalEuros1);

        m_jTotalEuros.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        m_jTotalEuros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jTotalEuros.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTotalEuros.setOpaque(true);
        m_jTotalEuros.setPreferredSize(new java.awt.Dimension(125, 25));
        m_jTotalEuros.setRequestFocusEnabled(false);
        jPanel4.add(m_jTotalEuros);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        m_jLblRemainingEuros.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        m_jLblRemainingEuros.setText(AppLocal.getIntString("label.remainingcash")); // NOI18N
        jPanel6.add(m_jLblRemainingEuros);

        m_jRemaininglEuros.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        m_jRemaininglEuros.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jRemaininglEuros.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jRemaininglEuros.setOpaque(true);
        m_jRemaininglEuros.setPreferredSize(new java.awt.Dimension(125, 25));
        m_jRemaininglEuros.setRequestFocusEnabled(false);
        jPanel6.add(m_jRemaininglEuros);

        m_jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/btnplus.png"))); // NOI18N
        m_jButtonAdd.setToolTipText("Add Part Payment");
        m_jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonAddActionPerformed(evt);
            }
        });
        jPanel6.add(m_jButtonAdd);

        m_jButtonRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/btnminus.png"))); // NOI18N
        m_jButtonRemove.setToolTipText("Delete Part Payment");
        m_jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonRemoveActionPerformed(evt);
            }
        });
        jPanel6.add(m_jButtonRemove);

        jPanel4.add(jPanel6);

        getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        m_jTabPayment.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        m_jTabPayment.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        m_jTabPayment.setFocusable(false);
        m_jTabPayment.setRequestFocusEnabled(false);
        m_jTabPayment.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m_jTabPaymentStateChanged(evt);
            }
        });
        jPanel3.add(m_jTabPayment, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.BorderLayout());

        m_jButtonCancel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/cancel.png"))); // NOI18N
        m_jButtonCancel.setText(AppLocal.getIntString("Button.Cancel")); // NOI18N
        m_jButtonCancel.setFocusPainted(false);
        m_jButtonCancel.setFocusable(false);
        m_jButtonCancel.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonCancelActionPerformed(evt);
            }
        });
        jPanel2.add(m_jButtonCancel);
        jPanel2.add(jPanel1);

        m_jButtonOK.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        m_jButtonOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/ok.png"))); // NOI18N
        m_jButtonOK.setText(AppLocal.getIntString("Button.OK")); // NOI18N
        m_jButtonOK.setFocusPainted(false);
        m_jButtonOK.setFocusable(false);
        m_jButtonOK.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonOK.setMaximumSize(new java.awt.Dimension(100, 44));
        m_jButtonOK.setPreferredSize(new java.awt.Dimension(150, 55));
        m_jButtonOK.setRequestFocusEnabled(false);
        m_jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonOKActionPerformed(evt);
            }
        });
        jPanel2.add(m_jButtonOK);

        jPanel5.add(jPanel2, java.awt.BorderLayout.LINE_END);

        m_jButtonPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/printer24_off.png"))); // NOI18N
        m_jButtonPrint.setSelected(true);
        m_jButtonPrint.setToolTipText("Print Receipt");
        m_jButtonPrint.setFocusPainted(false);
        m_jButtonPrint.setFocusable(false);
        m_jButtonPrint.setMargin(new java.awt.Insets(8, 16, 8, 16));
        m_jButtonPrint.setRequestFocusEnabled(false);
        m_jButtonPrint.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/printer24.png"))); // NOI18N
        m_jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jButtonPrintActionPerformed(evt);
            }
        });
        jPanel5.add(m_jButtonPrint, java.awt.BorderLayout.LINE_START);

        getContentPane().add(jPanel5, java.awt.BorderLayout.SOUTH);

        setSize(new java.awt.Dimension(672, 497));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonRemoveActionPerformed

        m_aPaymentInfo.removeLast();
        printState();        
        
    }//GEN-LAST:event_m_jButtonRemoveActionPerformed

    private void m_jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonAddActionPerformed

        PaymentInfo returnPayment = ((JPaymentInterface) m_jTabPayment.getSelectedComponent()).executePayment();
        if (returnPayment != null) {
            m_aPaymentInfo.add(returnPayment);
            printState();
        }        
        
    }//GEN-LAST:event_m_jButtonAddActionPerformed

    private void m_jTabPaymentStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m_jTabPaymentStateChanged

        if (m_jTabPayment.getSelectedComponent() != null) {
//            ((JPaymentInterface) m_jTabPayment.getSelectedComponent()).activate(customerext, m_dTotal - m_aPaymentInfo.getTotal(), m_sTransactionID);
            ((JPaymentInterface) m_jTabPayment.getSelectedComponent()).activate(customerext, 
                    m_dTotal - m_aPaymentInfo.getTotal(),
                    m_sTransactionID);            
        }
        
    }//GEN-LAST:event_m_jTabPaymentStateChanged

    private void m_jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonOKActionPerformed
        
        PaymentInfo returnPayment = ((JPaymentInterface) m_jTabPayment.getSelectedComponent()).executePayment();

        if (returnPayment != null) {
                        
            m_aPaymentInfo.add(returnPayment);
            accepted = true;
            
            dispose();
        }           
        
    }//GEN-LAST:event_m_jButtonOKActionPerformed

    private void m_jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonCancelActionPerformed

        dispose();
        
    }//GEN-LAST:event_m_jButtonCancelActionPerformed

    private void m_jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jButtonPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_m_jButtonPrintActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton m_jButtonAdd;
    private javax.swing.JButton m_jButtonCancel;
    private javax.swing.JButton m_jButtonOK;
    private javax.swing.JToggleButton m_jButtonPrint;
    private javax.swing.JButton m_jButtonRemove;
    private javax.swing.JLabel m_jLblRemainingEuros;
    private javax.swing.JLabel m_jLblTotalEuros1;
    private javax.swing.JLabel m_jRemaininglEuros;
    private javax.swing.JTabbedPane m_jTabPayment;
    private javax.swing.JLabel m_jTotalEuros;
    // End of variables declaration//GEN-END:variables
    
}
