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

import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.Session;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DriverWrapper;
import com.openbravo.pos.util.AltEncrypter;
import com.openbravo.pos.util.DirectoryEvent;
import java.awt.Component;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.io.IOException;

/**
 *
 * @author adrianromero
 */
public class JPanelConfigDatabase extends javax.swing.JPanel implements PanelConfig {
    
    private final DirtyManager dirty = new DirtyManager();
    
    /** Creates new form JPanelConfigDatabase */
    public JPanelConfigDatabase() {
        
        initComponents();
        
        jtxtDbDriverLib.getDocument().addDocumentListener(dirty);
        jtxtDbDriver.getDocument().addDocumentListener(dirty);
        jtxtDbURL.getDocument().addDocumentListener(dirty);
        jtxtDbPassword.getDocument().addDocumentListener(dirty);
        jtxtDbUser.getDocument().addDocumentListener(dirty);
        jbtnDbDriverLib.addActionListener(new DirectoryEvent(jtxtDbDriverLib));
        jcboDBDriver.addActionListener(dirty);

//  JG 31 Aug 13 - Added DB Defaults
        jcboDBDriver.addItem("Apache Derby Embedded");
        jcboDBDriver.addItem("Apache Derby Client/Server");
        jcboDBDriver.addItem("HSQLDB");
        jcboDBDriver.addItem("MySQL");
        jcboDBDriver.addItem("Oracle 11g Express");
        jcboDBDriver.addItem("PostgreSQL");
        
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
      
        jcboDBDriver.setSelectedItem(config.getProperty("db.engine"));
        jtxtDbDriverLib.setText(config.getProperty("db.driverlib"));
        jtxtDbDriver.setText(config.getProperty("db.driver"));
        jtxtDbURL.setText(config.getProperty("db.URL"));
        
        String sDBUser = config.getProperty("db.user");
        String sDBPassword = config.getProperty("db.password");        
        if (sDBUser != null && sDBPassword != null && sDBPassword.startsWith("crypt:")) {
            // La clave esta encriptada.
            AltEncrypter cypher = new AltEncrypter("cypherkey" + sDBUser);
            sDBPassword = cypher.decrypt(sDBPassword.substring(6));
        }        
        jtxtDbUser.setText(sDBUser);
        jtxtDbPassword.setText(sDBPassword);   
        
        dirty.setDirty(false);
    }
   
    /**
     *
     * @param config
     */
    @Override
    public void saveProperties(AppConfig config) {
        
        config.setProperty("db.engine", comboValue(jcboDBDriver.getSelectedItem()));
        config.setProperty("db.driverlib", jtxtDbDriverLib.getText());
        config.setProperty("db.driver", jtxtDbDriver.getText());
        config.setProperty("db.URL", jtxtDbURL.getText());
        config.setProperty("db.user", jtxtDbUser.getText());
        AltEncrypter cypher = new AltEncrypter("cypherkey" + jtxtDbUser.getText());       
        config.setProperty("db.password", "crypt:" + cypher.encrypt(new String(jtxtDbPassword.getPassword())));

        dirty.setDirty(false);
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

        jLabel6 = new javax.swing.JLabel();
        jcboDBDriver = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jtxtDbDriverLib = new javax.swing.JTextField();
        jbtnDbDriverLib = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtxtDbDriver = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtDbURL = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtDbUser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtDbPassword = new javax.swing.JPasswordField();
        jButtonTest = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(650, 500));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Database");

        jcboDBDriver.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboDBDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboDBDriverActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel18.setText(AppLocal.getIntString("label.dbdriverlib")); // NOI18N

        jtxtDbDriverLib.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtDbDriverLib.setPreferredSize(new java.awt.Dimension(450, 25));

        jbtnDbDriverLib.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/fileopen.png"))); // NOI18N
        jbtnDbDriverLib.setMaximumSize(new java.awt.Dimension(64, 32));
        jbtnDbDriverLib.setMinimumSize(new java.awt.Dimension(64, 32));
        jbtnDbDriverLib.setPreferredSize(new java.awt.Dimension(64, 40));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText(AppLocal.getIntString("Label.DbDriver")); // NOI18N

        jtxtDbDriver.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtDbDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDbDriverActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(AppLocal.getIntString("Label.DbURL")); // NOI18N

        jtxtDbURL.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtDbURL.setPreferredSize(new java.awt.Dimension(450, 25));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("Label.DbUser")); // NOI18N

        jtxtDbUser.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("Label.DbPassword")); // NOI18N

        jtxtDbPassword.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jButtonTest.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButtonTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/database.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jButtonTest.setText(bundle.getString("Button.Test")); // NOI18N
        jButtonTest.setActionCommand(bundle.getString("Button.Test")); // NOI18N
        jButtonTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/uniCenta_logo_vert_100.png"))); // NOI18N
        jLabel5.setText(bundle.getString("message.DBDefault")); // NOI18N
        jLabel5.setToolTipText("");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jButton1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton1.setText("uniCenta Web site");
        jButton1.setMaximumSize(new java.awt.Dimension(150, 45));
        jButton1.setMinimumSize(new java.awt.Dimension(150, 45));
        jButton1.setPreferredSize(new java.awt.Dimension(150, 45));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jcboDBDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtxtDbDriverLib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jbtnDbDriverLib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtxtDbDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jtxtDbURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtDbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtDbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(jButtonTest, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(262, 262, 262))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboDBDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnDbDriverLib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtDbDriverLib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDbDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDbURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtDbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtDbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButtonTest, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtDbDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDbDriverActionPerformed

    }//GEN-LAST:event_jtxtDbDriverActionPerformed

    private void jcboDBDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboDBDriverActionPerformed
//  JG 31 Aug 13 - Added DB Defaults
        String dirname = System.getProperty("dirname.path");
        dirname = dirname == null ? "./" : dirname;
        
        if ("Apache Derby Embedded".equals(jcboDBDriver.getSelectedItem())) {
            jtxtDbDriverLib.setText(new File(new File(dirname), "lib/derby.jar").getAbsolutePath());
            jtxtDbDriver.setText("org.apache.derby.jdbc.EmbeddedDriver");
            jtxtDbURL.setText("jdbc:derby:" + new File(new File(System.getProperty("user.home")), AppLocal.APP_ID + "-database").getAbsolutePath() + ";create=true");
            jtxtDbUser.setText("");
            jtxtDbPassword.setText("");            
            
        } else if ("Apache Derby Client/Server".equals(jcboDBDriver.getSelectedItem())) {
            jtxtDbDriverLib.setText(new File(new File(dirname), "lib/derbyclient.jar").getAbsolutePath());
            jtxtDbDriver.setText("org.apache.derby.jdbc.ClientDriver");
            jtxtDbURL.setText("jdbc:derby://localhost:1527/unicentaopos");
            
        } else if ("HSQLDB".equals(jcboDBDriver.getSelectedItem())) {
            jtxtDbDriverLib.setText(new File(new File(dirname), "lib/hsqldb.jar").getAbsolutePath());
            jtxtDbDriver.setText("org.hsqldb.jdbcDriver");
            jtxtDbURL.setText("jdbc:hsqldb:file:" + new File(new File(System.getProperty("user.home")), AppLocal.APP_ID + "-db").getAbsolutePath() + ";shutdown=true");
            jtxtDbUser.setText("SA");
            jtxtDbPassword.setText("");
            
        } else if ("MySQL".equals(jcboDBDriver.getSelectedItem())) {
            jtxtDbDriverLib.setText(new File(new File(dirname), "lib/mysql-connector-java-5.1.26-bin.jar").getAbsolutePath());
            jtxtDbDriver.setText("com.mysql.jdbc.Driver");
            jtxtDbURL.setText("jdbc:mysql://localhost:3306/unicentaopos");
            
        } else if ("Oracle 11g Express".equals(jcboDBDriver.getSelectedItem())) {
            jtxtDbDriverLib.setText(new File(new File(dirname), "lib/ojdbc6.jar").getAbsolutePath());
            jtxtDbDriver.setText("oracle.jdbc.driver.OracleDriver");
            jtxtDbURL.setText("jdbc:oracle:thin://localhost:1521/unicentaopos");

        } else if ("PostgreSQL".equals(jcboDBDriver.getSelectedItem())) {
            jtxtDbDriverLib.setText(new File(new File(dirname), "lib/postgresql-9.2-1003.jdbc4.jar").getAbsolutePath());
            jtxtDbDriver.setText("org.postgresql.Driver");
            jtxtDbURL.setText("jdbc:postgresql://localhost:5432/unicentaopos");            
        } else {
            
        }
    }//GEN-LAST:event_jcboDBDriverActionPerformed

//  JG 3 Oct 13 - Test DB Connection
    private void jButtonTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestActionPerformed
        try {
            String driverlib = jtxtDbDriverLib.getText();
            String driver = jtxtDbDriver.getText();
            String url = jtxtDbURL.getText();
            String user = jtxtDbUser.getText();
            String password = new String(jtxtDbPassword.getPassword());

            ClassLoader cloader = new URLClassLoader(new URL[]{new File(driverlib).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver) Class.forName(driver, true, cloader).newInstance()));

            Session session =  new Session(url, user, password);
            Connection connection = session.getConnection();
            boolean isValid;
            isValid = (connection == null) ? false : connection.isValid(1000);

            if (isValid) {
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.databasesuccess"), "Connection Test", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, "Connection Error"));
            }
        } catch (InstantiationException | IllegalAccessException | MalformedURLException | ClassNotFoundException e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.databasedrivererror"), e));
        } catch (SQLException e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.databaseconnectionerror"), e));
        } catch (Exception e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, "Unknown exception", e));
        }
    }//GEN-LAST:event_jButtonTestActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            String URL ="http://www.unicenta.com";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonTest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbtnDbDriverLib;
    private javax.swing.JComboBox jcboDBDriver;
    private javax.swing.JTextField jtxtDbDriver;
    private javax.swing.JTextField jtxtDbDriverLib;
    private javax.swing.JPasswordField jtxtDbPassword;
    private javax.swing.JTextField jtxtDbURL;
    private javax.swing.JTextField jtxtDbUser;
    // End of variables declaration//GEN-END:variables
    
}
