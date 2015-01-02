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

package com.openbravo.pos.sales.shared;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.sales.DataLogicReceipts;
import com.openbravo.pos.sales.JTicketsBag;
import com.openbravo.pos.sales.SharedTicketInfo;
import com.openbravo.pos.sales.TicketsEditor;
import com.openbravo.pos.ticket.TicketInfo;
import java.util.List;
import java.util.UUID;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author JG uniCenta
 */
public class JTicketsBagShared extends JTicketsBag {
    
    private String m_sCurrentTicket = null;
    private DataLogicReceipts dlReceipts = null;
    
    
    /** Creates new form JTicketsBagShared
     * @param app
     * @param panelticket */
    public JTicketsBagShared(AppView app, TicketsEditor panelticket) {
        
        super(app, panelticket);
        
        dlReceipts = (DataLogicReceipts) app.getBean("com.openbravo.pos.sales.DataLogicReceipts");
        
        initComponents();
    }
    
    /**
     *
     */
    @Override
    public void activate() {
        
        // precondicion es que no tenemos ticket activado ni ticket en el panel
        m_sCurrentTicket = null;
        selectValidTicket();     
        
        // Authorisation
        m_jDelTicket.setEnabled(m_App.getAppUserView().getUser().hasPermission("com.openbravo.pos.sales.JPanelTicketEdits"));
       
    }
    
    /**
     *
     * @return
     */
    @Override
    public boolean deactivate() {
        
        // precondicion es que tenemos ticket activado aqui y ticket en el panel 
        saveCurrentTicket();
        
        m_sCurrentTicket = null;
        m_panelticket.setActiveTicket(null, null);       
        
        return true;
        
        // postcondicion es que no tenemos ticket activado ni ticket en el panel
    }
        
    /**
     *
     */
    @Override
    public void deleteTicket() {
        m_sCurrentTicket = null;
        selectValidTicket();      
    }
    
    /**
     *
     * @return
     */
    @Override
    protected JComponent getBagComponent() {
        return this;
    }
    
    /**
     *
     * @return
     */
    @Override
    protected JComponent getNullComponent() {
        return new JPanel();
    }
   
    private void saveCurrentTicket() {
// Thanks Ibastavd & sunnytang 2012/2013 - Mod JG 23 Jul 13    
// save current ticket, if exists,
        if (m_sCurrentTicket != null) {
            try {
                dlReceipts.insertSharedTicket(m_sCurrentTicket, m_panelticket.getActiveTicket(),m_panelticket.getActiveTicket().getPickupId());
                m_jListTickets.setText("*");
                TicketInfo l = dlReceipts.getSharedTicket(m_sCurrentTicket);
                    if(l.getLinesCount() == 0) {
//                      throw new BasicException(AppLocal.getIntString("message.nullticket"));
//                    }else{
                        dlReceipts.deleteSharedTicket(m_sCurrentTicket);
                    }             
            } catch (BasicException e) {
                new MessageInf(e).show(this);
            }  
        }    
    }
    
    private void setActiveTicket(String id) throws BasicException{
          
        // BEGIN TRANSACTION
        TicketInfo ticket = dlReceipts.getSharedTicket(id);
        if (ticket == null)  {
            m_jListTickets.setText("");
            // Does it exist
            throw new BasicException(AppLocal.getIntString("message.noticket"));
        } else {
            dlReceipts.getPickupId(id);
            Integer pickUp = dlReceipts.getPickupId(id);
            dlReceipts.deleteSharedTicket(id);
            m_sCurrentTicket = id;
            m_panelticket.setActiveTicket(ticket, null);
            ticket.setPickupId(pickUp);         
            
        } 
        // END TRANSACTION                 
    }
    
    private void selectValidTicket() {
        
        try {
            List<SharedTicketInfo> l = dlReceipts.getSharedTicketList();
            if (l.isEmpty()) {
                m_jListTickets.setText("");                
                 newTicket();
            } else {
//                m_jListTickets.setText("*");
//                setActiveTicket(l.get(0).getId());
                newTicket();
            }
        } catch (BasicException e) {
            new MessageInf(e).show(this);
            newTicket();
        }    
    }    
    
    private void newTicket() {      
        
        saveCurrentTicket();

        TicketInfo ticket = new TicketInfo();    
        m_sCurrentTicket = UUID.randomUUID().toString(); // m_fmtid.format(ticket.getId());
        m_panelticket.setActiveTicket(ticket, null);
 
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        m_jNewTicket = new javax.swing.JButton();
        m_jDelTicket = new javax.swing.JButton();
        m_jListTickets = new javax.swing.JButton();

        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setLayout(new java.awt.BorderLayout());

        m_jNewTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/sale_new.png"))); // NOI18N
        m_jNewTicket.setToolTipText("New Sale");
        m_jNewTicket.setFocusPainted(false);
        m_jNewTicket.setFocusable(false);
        m_jNewTicket.setMargin(new java.awt.Insets(0, 4, 0, 4));
        m_jNewTicket.setMaximumSize(new java.awt.Dimension(50, 40));
        m_jNewTicket.setMinimumSize(new java.awt.Dimension(50, 40));
        m_jNewTicket.setPreferredSize(new java.awt.Dimension(50, 40));
        m_jNewTicket.setRequestFocusEnabled(false);
        m_jNewTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jNewTicketActionPerformed(evt);
            }
        });
        jPanel1.add(m_jNewTicket);

        m_jDelTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/sale_delete.png"))); // NOI18N
        m_jDelTicket.setToolTipText("Cancel Sale");
        m_jDelTicket.setFocusPainted(false);
        m_jDelTicket.setFocusable(false);
        m_jDelTicket.setMargin(new java.awt.Insets(0, 4, 0, 4));
        m_jDelTicket.setMaximumSize(new java.awt.Dimension(50, 40));
        m_jDelTicket.setMinimumSize(new java.awt.Dimension(50, 40));
        m_jDelTicket.setPreferredSize(new java.awt.Dimension(50, 40));
        m_jDelTicket.setRequestFocusEnabled(false);
        m_jDelTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jDelTicketActionPerformed(evt);
            }
        });
        jPanel1.add(m_jDelTicket);

        m_jListTickets.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jListTickets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/sale_pending.png"))); // NOI18N
        m_jListTickets.setToolTipText("Layaways");
        m_jListTickets.setFocusPainted(false);
        m_jListTickets.setFocusable(false);
        m_jListTickets.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        m_jListTickets.setMargin(new java.awt.Insets(0, 4, 0, 4));
        m_jListTickets.setMaximumSize(new java.awt.Dimension(50, 40));
        m_jListTickets.setMinimumSize(new java.awt.Dimension(50, 40));
        m_jListTickets.setPreferredSize(new java.awt.Dimension(50, 40));
        m_jListTickets.setRequestFocusEnabled(false);
        m_jListTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jListTicketsActionPerformed(evt);
            }
        });
        jPanel1.add(m_jListTickets);

        add(jPanel1, java.awt.BorderLayout.WEST);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jListTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jListTicketsActionPerformed

        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                try {
                    List<SharedTicketInfo> l = dlReceipts.getSharedTicketList();
//                    String itemCount = Integer.toString(l.size());
//                    m_jListTickets.setText(itemCount);
//                    m_jListTickets.setIcon(null);
                    JTicketsBagSharedList listDialog = JTicketsBagSharedList.newJDialog(JTicketsBagShared.this);
                    String id = listDialog.showTicketsList(l); 

                    if (id != null) {
                        saveCurrentTicket();
                        setActiveTicket(id); 
                    }
                } catch (BasicException e) {
                    new MessageInf(e).show(JTicketsBagShared.this);
                    newTicket();
                }                    
            }
        });
        
    }//GEN-LAST:event_m_jListTicketsActionPerformed

    private void m_jDelTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jDelTicketActionPerformed
        
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannadelete"), AppLocal.getIntString("title.editor"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            deleteTicket();

        }
        
    }//GEN-LAST:event_m_jDelTicketActionPerformed

    private void m_jNewTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jNewTicketActionPerformed

        newTicket();
        
    }//GEN-LAST:event_m_jNewTicketActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton m_jDelTicket;
    private javax.swing.JButton m_jListTickets;
    private javax.swing.JButton m_jNewTicket;
    // End of variables declaration//GEN-END:variables
    
}
