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

package com.openbravo.pos.dbupdate;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.BatchSentence;
import com.openbravo.data.loader.BatchSentenceResource;
import com.openbravo.data.loader.Session;
import com.openbravo.pos.forms.*;
import com.openbravo.pos.util.AltEncrypter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;

/**
 *
 * @author JG uniCenta
 */
public class JPaneldbUpdate extends JPanel implements JPanelView {
        


    private AppConfig config;
    private Connection con;
    private String sdbmanager;
    private Session session;
    private AppProperties m_props;
    private String eScript;
    
    
    /** Creates new form JPaneldbUpdate
     * @param oApp */
    public JPaneldbUpdate(AppView oApp) {
        this(oApp.getProperties());       
    }

    /**
     *
     * @param props
     */
    public JPaneldbUpdate(AppProperties props) {

        initComponents();
        config = new AppConfig(props.getConfigFile());      
        m_props=props;
     
 /*       jMessageBox.setText("This updater is for version 3.02 of Unicenta oPOS. \n\n"
                + "This update will add the following to the database. \n\n"
                + "Variable Price flag, column to Products table \n"
                + "Mandatory Attribute flag, column to Products table. \n"
                + "TextTip column will be added to the Products table  \n\n"
                + "New Resource file creation for Printer.TicketClose. \n\n"
                + "The table required for CSV import reports and the \n"
                + "table required for the marine option will also be created");

   */
         jMessageBox.setText("This updater is no longer required.\n"
                 + "All John L updates, are now handled in the main program. \n");
              
        jbtnUpdate.setVisible(false);
        
        
    }

    /**
     *
     * @param sScript
     */
    public void performUpdate(String sScript){
        switch (sdbmanager) {
            case "HSQL Database Engine":
                eScript = "/com/openbravo/pos/scripts/HSQLDB" + sScript;
                break;
            case "MySQL":
                eScript = "/com/openbravo/pos/scripts/MySQL" + sScript;
                break;
            case "PostgreSQL":
                eScript = "/com/openbravo/pos/scripts/PostgreSQL" + sScript;
                break;
            case "Oracle":
                eScript = "/com/openbravo/pos/scripts/Oracle" + sScript;
                break;
            case "Apache Derby":
                eScript = "/com/openbravo/pos/scripts/Derby" + sScript;
                break;
            default:
                eScript = "/com/openbravo/pos/scripts/Derby" + sScript;
                break;
        }

                // update database using updater scripts
                    try {
                        BatchSentence bsentence = new BatchSentenceResource(session,  eScript);
                       
                        java.util.List l = bsentence.list();
                        if (l.size() > 0) {
                            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("database.UpdaterWarning"), l.toArray(new Throwable[l.size()])));
                        }else{
                                JOptionPane.showMessageDialog(this,"Update complete.");
                        }

                   } catch (BasicException e) {
                        JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_DANGER, AppLocal.getIntString("database.ScriptNotFound"), e));
                        session.close();
                    } finally{

                    }         
   }

    /**
     *
     * @return
     */
    @Override
    public JComponent getComponent() {
        return this;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String getTitle() {
        return AppLocal.getIntString("Menu.Configuration");
    } 
    
    /**
     *
     * @throws BasicException
     */
    @Override
    public void activate() throws BasicException {
       // connect to the database
         String db_user =(m_props.getProperty("db.user"));
         String db_url = (m_props.getProperty("db.URL"));
         String db_password = (m_props.getProperty("db.password"));     
         
         if (db_user != null && db_password != null && db_password.startsWith("crypt:")) {
                // the password is encrypted
                AltEncrypter cypher = new AltEncrypter("cypherkey" + db_user);
                db_password = cypher.decrypt(db_password.substring(6));
         }
        
         try{
            session = AppViewConnection.createSession(m_props);
            con = DriverManager.getConnection(db_url,db_user,db_password);                   
            sdbmanager = con.getMetaData().getDatabaseProductName(); 
            } catch (BasicException | SQLException e) {    
// put some error trap here  
                
         JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_DANGER, AppLocal.getIntString("database.UnableToConnect"), e));
         System.exit(0);
       }        
      
    }

    /**
     *
     * @return
     */
    @Override
    public boolean deactivate() {
    return(true);
    }      

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtnUpdate = new javax.swing.JButton();
        jbtnExit = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jMessageBox = new javax.swing.JTextPane();

        setPreferredSize(new java.awt.Dimension(420, 329));

        jbtnUpdate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnUpdate.setText(AppLocal.getIntString("Button.Restore")); // NOI18N
        jbtnUpdate.setMaximumSize(new java.awt.Dimension(70, 33));
        jbtnUpdate.setMinimumSize(new java.awt.Dimension(70, 33));
        jbtnUpdate.setPreferredSize(new java.awt.Dimension(70, 33));
        jbtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnUpdateActionPerformed(evt);
            }
        });

        jbtnExit.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnExit.setText(AppLocal.getIntString("Button.Exit")); // NOI18N
        jbtnExit.setMaximumSize(new java.awt.Dimension(70, 33));
        jbtnExit.setMinimumSize(new java.awt.Dimension(70, 33));
        jbtnExit.setPreferredSize(new java.awt.Dimension(70, 33));
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });

        jMessageBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(jMessageBox);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addComponent(jbtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnUpdateActionPerformed
        performUpdate("-updater.sql");
        
    }//GEN-LAST:event_jbtnUpdateActionPerformed

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        deactivate();
        System.exit(0);
    }//GEN-LAST:event_jbtnExitActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane jMessageBox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnUpdate;
    // End of variables declaration//GEN-END:variables
    
}
