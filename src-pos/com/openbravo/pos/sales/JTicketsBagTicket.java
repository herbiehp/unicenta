//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2014 uniCenta & previous Openbravo POS works
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

package com.openbravo.pos.sales;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.ListKeyed;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.panels.JTicketsFinder;
import com.openbravo.pos.printer.*;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.ticket.FindTicketsInfo;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.ticket.TicketTaxInfo;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author JG uniCenta
 */
public class JTicketsBagTicket extends JTicketsBag {
    
    private DataLogicSystem m_dlSystem = null;

    /**
     *
     */
    protected DataLogicCustomers dlCustomers = null;

    private final DataLogicSales m_dlSales; 
    private TaxesLogic taxeslogic;
    private ListKeyed taxcollection;



    private final DeviceTicket m_TP;    
    private final TicketParser m_TTP;    
    private final TicketParser m_TTP2; 
    
    private TicketInfo m_ticket;
    private TicketInfo m_ticketCopy;
    
    private final JTicketsBagTicketBag m_TicketsBagTicketBag;
    
    private final JPanelTicketEdits m_panelticketedit;

    /** Creates new form JTicketsBagTicket
     * @param app
     * @param panelticket */
    public JTicketsBagTicket(AppView app, JPanelTicketEdits panelticket) {
        
        super(app, panelticket);
        m_panelticketedit = panelticket; 
        m_dlSystem = (DataLogicSystem) m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
        m_dlSales = (DataLogicSales) m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        dlCustomers = (DataLogicCustomers) m_App.getBean("com.openbravo.pos.customers.DataLogicCustomers");
        
        // Inicializo la impresora...
//JG July 2014 - Thank you Ron Isaacson        m_TP = new DeviceTicket();
        m_TP = new DeviceTicket(app.getProperties());        
   
        // Inicializo el parser de documentos de ticket
        m_TTP = new TicketParser(m_TP, m_dlSystem); // para visualizar el ticket
        m_TTP2 = new TicketParser(m_App.getDeviceTicket(), m_dlSystem); // para imprimir el ticket
        
        initComponents();
        
        m_TicketsBagTicketBag = new JTicketsBagTicketBag(this);
        
        m_jTicketEditor.addEditorKeys(m_jKeys);
        
        // Este deviceticket solo tiene una impresora, la de pantalla
        m_jPanelTicket.add(m_TP.getDevicePrinter("1").getPrinterComponent(), BorderLayout.CENTER);
        
        try {
            taxeslogic = new TaxesLogic(m_dlSales.getTaxList().list());
        } catch (BasicException ex) {
    }
    }
    
    /**
     *
     */
    @Override
    public void activate() {
        
        // precondicion es que no tenemos ticket activado ni ticket en el panel
        
        m_ticket = null;
        m_ticketCopy = null;
        
        printTicket();        
        
        m_jTicketEditor.reset();
        m_jTicketEditor.activate();
        
        m_panelticketedit.setActiveTicket(null, null);

        jrbSales.setSelected(true);
        
        m_jEdit.setVisible(m_App.getAppUserView().getUser().hasPermission("sales.EditTicket"));
        m_jRefund.setVisible(m_App.getAppUserView().getUser().hasPermission("sales.RefundTicket"));
        m_jPrint.setVisible(m_App.getAppUserView().getUser().hasPermission("sales.PrintTicket"));
             
        // postcondicion es que tenemos ticket activado aqui y ticket en el panel
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean deactivate() {
        
        // precondicion es que tenemos ticket activado aqui y ticket en el panel        
        m_ticket = null;   
        m_ticketCopy = null;
        return true;       
        // postcondicion es que no tenemos ticket activado ni ticket en el panel
    }
    
    /**
     *
     */
    @Override
    public void deleteTicket() {
        
        if (m_ticketCopy != null) {           
            // Para editar borramos el ticket anterior
            try {               
                m_dlSales.deleteTicket(m_ticketCopy, m_App.getInventoryLocation());
            } catch (BasicException eData) {
                MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.nosaveticket"), eData);
                msg.show(this);                
            }            
        }
        
        m_ticket = null;
        m_ticketCopy = null;
        resetToTicket(); 
    }

    /**
     *
     */
    public void canceleditionTicket() {
        
        m_ticketCopy = null;
        resetToTicket();
    }    
    
    private void resetToTicket() {       
        printTicket();
        m_jTicketEditor.reset();
        m_jTicketEditor.activate();
        m_panelticketedit.setActiveTicket(null, null); 
    }
    
    /**
     *
     * @return
     */
    @Override
    protected JComponent getBagComponent() {
        return m_TicketsBagTicketBag;
    }
    
    /**
     *
     * @return
     */
    @Override
    protected JComponent getNullComponent() {
        return this;
    }
      
    private void readTicket(int iTicketid, int iTickettype) {
        Integer findTicket=0;    
        try {
                findTicket = m_jTicketEditor.getValueInteger();
            }catch (BasicException e){            
            }

        try {
   
            TicketInfo ticket = (iTicketid==-1) 
                ? m_dlSales.loadTicket(iTickettype,  findTicket)
                : m_dlSales.loadTicket(iTickettype, iTicketid) ;

            if (ticket == null) {
                //MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.notexiststicket"));
                //msg.show(this);
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame,AppLocal.getIntString("message.notexiststicket"),AppLocal.getIntString("message.notexiststickettitle"),JOptionPane.WARNING_MESSAGE);
                
            } else {
                m_ticket = ticket;
                m_ticketCopy = null; // se asigna al pulsar el boton de editar o devolver
                
                try {
                    taxeslogic.calculateTaxes(m_ticket);
                    TicketTaxInfo[] taxlist = m_ticket.getTaxLines();
                  //  taxcollection = new ListKeyed<TaxInfo>(taxlist);
                } catch (TaxesException ex) {}
                printTicket();
            }
            
        } catch (BasicException e) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadticket"), e);
            msg.show(this);
        }
        
        m_jTicketEditor.reset();
        m_jTicketEditor.activate();
    }
    
    private void printTicket() {
        
        // imprimo m_ticket
        
        try {
            m_jEdit.setEnabled(
                    m_ticket != null
                    && (m_ticket.getTicketType() == TicketInfo.RECEIPT_NORMAL || m_ticket.getTicketType() == TicketInfo.RECEIPT_REFUND)
                    && m_dlSales.isCashActive(m_ticket.getActiveCash()));
        } catch (BasicException e) {
            m_jEdit.setEnabled(false);
        }
        m_jRefund.setEnabled(m_ticket != null && m_ticket.getTicketType() == TicketInfo.RECEIPT_NORMAL);
        m_jPrint.setEnabled(m_ticket != null);
        
        // Este deviceticket solo tiene una impresora, la de pantalla
        m_TP.getDevicePrinter("1").reset();
        
        if (m_ticket == null) {
            m_jTicketId.setText(null);            
        } else {
            m_jTicketId.setText(m_ticket.getName());
            
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                script.put("ticket", m_ticket);
                script.put("taxes", m_ticket.getTaxLines());                
                m_TTP.printTicket(script.eval(m_dlSystem.getResourceAsXML("Printer.TicketPreview")).toString());
            } catch (    ScriptException | TicketPrinterException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotprintticket"), e);
                msg.show(this);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        m_jOptions = new javax.swing.JPanel();
        m_jButtons = new javax.swing.JPanel();
        m_jTicketId = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        m_jEdit = new javax.swing.JButton();
        m_jRefund = new javax.swing.JButton();
        m_jPrint = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        m_jPanelTicket = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        m_jKeys = new com.openbravo.editor.JEditorKeys();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        m_jTicketEditor = new com.openbravo.editor.JEditorIntegerPositive();
        jPanel1 = new javax.swing.JPanel();
        jrbSales = new javax.swing.JRadioButton();
        jrbRefunds = new javax.swing.JRadioButton();

        setLayout(new java.awt.BorderLayout());

        m_jButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        m_jTicketId.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jTicketId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_jTicketId.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTicketId.setOpaque(true);
        m_jTicketId.setPreferredSize(new java.awt.Dimension(160, 25));
        m_jTicketId.setRequestFocusEnabled(false);
        m_jButtons.add(m_jTicketId);

        jButton2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/search24.png"))); // NOI18N
        jButton2.setText(AppLocal.getIntString("button.print")); // NOI18N
        jButton2.setToolTipText("Search Tickets");
        jButton2.setFocusPainted(false);
        jButton2.setFocusable(false);
        jButton2.setMargin(new java.awt.Insets(0, 4, 0, 4));
        jButton2.setMaximumSize(new java.awt.Dimension(50, 40));
        jButton2.setMinimumSize(new java.awt.Dimension(50, 40));
        jButton2.setPreferredSize(new java.awt.Dimension(50, 40));
        jButton2.setRequestFocusEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        m_jButtons.add(jButton2);

        m_jEdit.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_jEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/sale_editline.png"))); // NOI18N
        m_jEdit.setText(AppLocal.getIntString("button.print")); // NOI18N
        m_jEdit.setToolTipText("Edit current Ticket");
        m_jEdit.setFocusPainted(false);
        m_jEdit.setFocusable(false);
        m_jEdit.setMargin(new java.awt.Insets(0, 4, 0, 4));
        m_jEdit.setMaximumSize(new java.awt.Dimension(50, 40));
        m_jEdit.setMinimumSize(new java.awt.Dimension(50, 40));
        m_jEdit.setPreferredSize(new java.awt.Dimension(50, 40));
        m_jEdit.setRequestFocusEnabled(false);
        m_jEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jEditActionPerformed(evt);
            }
        });
        m_jButtons.add(m_jEdit);

        m_jRefund.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_jRefund.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/inbox.png"))); // NOI18N
        m_jRefund.setText(AppLocal.getIntString("button.print")); // NOI18N
        m_jRefund.setToolTipText("Receipt Refund");
        m_jRefund.setFocusPainted(false);
        m_jRefund.setFocusable(false);
        m_jRefund.setMargin(new java.awt.Insets(0, 4, 0, 4));
        m_jRefund.setMaximumSize(new java.awt.Dimension(50, 40));
        m_jRefund.setMinimumSize(new java.awt.Dimension(50, 40));
        m_jRefund.setPreferredSize(new java.awt.Dimension(50, 40));
        m_jRefund.setRequestFocusEnabled(false);
        m_jRefund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jRefundActionPerformed(evt);
            }
        });
        m_jButtons.add(m_jRefund);

        m_jPrint.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_jPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/printer24.png"))); // NOI18N
        m_jPrint.setText(AppLocal.getIntString("button.print")); // NOI18N
        m_jPrint.setToolTipText("Reprint Receipt");
        m_jPrint.setFocusPainted(false);
        m_jPrint.setFocusable(false);
        m_jPrint.setMargin(new java.awt.Insets(0, 4, 0, 4));
        m_jPrint.setMaximumSize(new java.awt.Dimension(50, 40));
        m_jPrint.setMinimumSize(new java.awt.Dimension(50, 40));
        m_jPrint.setPreferredSize(new java.awt.Dimension(50, 40));
        m_jPrint.setRequestFocusEnabled(false);
        m_jPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jPrintActionPerformed(evt);
            }
        });
        m_jButtons.add(m_jPrint);

        m_jOptions.add(m_jButtons);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        m_jOptions.add(jPanel2);

        add(m_jOptions, java.awt.BorderLayout.NORTH);

        m_jPanelTicket.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        m_jPanelTicket.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        m_jPanelTicket.setLayout(new java.awt.BorderLayout());
        add(m_jPanelTicket, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        m_jKeys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jKeysActionPerformed(evt);
            }
        });
        jPanel4.add(m_jKeys);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/ok.png"))); // NOI18N
        jButton1.setToolTipText("Enter Receipt and touch to Find by Number");
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.setMargin(new java.awt.Insets(8, 14, 8, 14));
        jButton1.setRequestFocusEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel5.add(jButton1, gridBagConstraints);

        m_jTicketEditor.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(m_jTicketEditor, gridBagConstraints);

        jPanel4.add(jPanel5);

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        buttonGroup1.add(jrbSales);
        jrbSales.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jrbSales.setText(AppLocal.getIntString("label.sales")); // NOI18N
        jrbSales.setToolTipText("Show Sales Only");
        jrbSales.setFocusPainted(false);
        jrbSales.setFocusable(false);
        jrbSales.setRequestFocusEnabled(false);
        jPanel1.add(jrbSales);

        buttonGroup1.add(jrbRefunds);
        jrbRefunds.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jrbRefunds.setForeground(new java.awt.Color(255, 0, 0));
        jrbRefunds.setText(AppLocal.getIntString("label.refunds")); // NOI18N
        jrbRefunds.setToolTipText("Show Refunds Only");
        jrbRefunds.setFocusPainted(false);
        jrbRefunds.setFocusable(false);
        jrbRefunds.setRequestFocusEnabled(false);
        jPanel1.add(jrbRefunds);

        jPanel3.add(jPanel1, java.awt.BorderLayout.CENTER);

        add(jPanel3, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jEditActionPerformed
                 
        m_ticketCopy = m_ticket;        
        m_TicketsBagTicketBag.showEdit();
        m_panelticketedit.showCatalog();
// Indicate that this a ticket in edit mode      
        m_ticketCopy.setOldTicket(true); 
        m_panelticketedit.setActiveTicket(m_ticket.copyTicket(), null);  
        
    }//GEN-LAST:event_m_jEditActionPerformed

    private void m_jPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jPrintActionPerformed
       
        if (m_ticket != null) {
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                script.put("ticket", m_ticket);
                script.put("taxes", m_ticket.getTaxLines());
                m_TTP2.printTicket(script.eval(m_dlSystem.getResourceAsXML("Printer.TicketPreview")).toString());
            } catch (ScriptException e) {
                JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.cannotprint"), e));
            } catch (TicketPrinterException e) {
                JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.cannotprint"), e));
            }
        }  
        
    }//GEN-LAST:event_m_jPrintActionPerformed

    private void m_jRefundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jRefundActionPerformed
        
        java.util.List aRefundLines = new ArrayList();
        
        for(int i = 0; i < m_ticket.getLinesCount(); i++) {
            TicketLineInfo newline = new TicketLineInfo(m_ticket.getLine(i));
            aRefundLines.add(newline);
        } 

        m_ticketCopy = null;
        m_TicketsBagTicketBag.showRefund();
        m_panelticketedit.showRefundLines(aRefundLines);
        
        TicketInfo refundticket = new TicketInfo();
        refundticket.setTicketType(TicketInfo.RECEIPT_REFUND);
        refundticket.setCustomer(m_ticket.getCustomer());
        refundticket.setPayments(m_ticket.getPayments());    
// Indicate that this a ticket in edit mode      
        refundticket.setOldTicket(true);         
        m_panelticketedit.setActiveTicket(refundticket, null);          
    }//GEN-LAST:event_m_jRefundActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        readTicket(-1, jrbSales.isSelected() ? 0 : 1);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void m_jKeysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jKeysActionPerformed

        readTicket(-1, jrbSales.isSelected() ? 0 : 1);
        
    }//GEN-LAST:event_m_jKeysActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JTicketsFinder finder = JTicketsFinder.getReceiptFinder(this, m_dlSales, dlCustomers);
        finder.setVisible(true);
        FindTicketsInfo selectedTicket = finder.getSelectedCustomer();
        if (selectedTicket == null) {
            m_jTicketEditor.reset();
            m_jTicketEditor.activate();
        } else {
            readTicket(selectedTicket.getTicketId(), selectedTicket.getTicketType());
        }
}//GEN-LAST:event_jButton2ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jrbRefunds;
    private javax.swing.JRadioButton jrbSales;
    private javax.swing.JPanel m_jButtons;
    private javax.swing.JButton m_jEdit;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private javax.swing.JPanel m_jOptions;
    private javax.swing.JPanel m_jPanelTicket;
    private javax.swing.JButton m_jPrint;
    private javax.swing.JButton m_jRefund;
    private com.openbravo.editor.JEditorIntegerPositive m_jTicketEditor;
    private javax.swing.JLabel m_jTicketId;
    // End of variables declaration//GEN-END:variables
    
}
