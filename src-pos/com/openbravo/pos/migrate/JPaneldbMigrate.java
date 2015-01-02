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

package com.openbravo.pos.migrate;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.BatchSentence;
import com.openbravo.data.loader.BatchSentenceResource;
import com.openbravo.data.loader.Session;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.config.PanelConfig;
import com.openbravo.pos.forms.*;
import com.openbravo.pos.util.AltEncrypter;
import com.openbravo.pos.util.DirectoryEvent;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import javax.swing.*;

/**
 *
 * @author JG uniCenta
 */
public class JPaneldbMigrate extends JPanel implements JPanelView {

    private DirtyManager dirty = new DirtyManager();
    private AppConfig config;
    private Connection con;
    private String sdbmanager;
    private Session session;
    private AppProperties m_props;
    private Connection con2;
    private String sdbmanager2;
    private Session session2;
    private ResultSet rs;
    private Statement stmt;
    private Statement stmt2;
    private String SQL;
    private PreparedStatement pstmt;
    private String ticketsnum;
    private String ticketsnumRefund;
    private String ticketsnumPayment;
    private List<PanelConfig> m_panelconfig;
    private String eScript = "";
    private String eScript1 = "";
    private String eScript2 = "";
    private String eScript3 = "";

    /**
     * Creates new form JPaneldbMigrate
     * @param oApp
     */
    public JPaneldbMigrate(AppView oApp) {
        this(oApp.getProperties());
    }

    /**
     *
     * @param props
     */
    public JPaneldbMigrate(AppProperties props) {

        initComponents();
        jPanel2.setPreferredSize(new java.awt.Dimension(645, 209));
        config = new AppConfig(props.getConfigFile());
        m_props = props;
        m_panelconfig = new ArrayList<>();
        config.load();
        for (PanelConfig c : m_panelconfig) {
            c.loadProperties(config);
        }


        jtxtDbDriverLib.getDocument().addDocumentListener(dirty);
        jtxtDbDriver.getDocument().addDocumentListener(dirty);
        jtxtDbURL.getDocument().addDocumentListener(dirty);
        jtxtDbPassword.getDocument().addDocumentListener(dirty);
        jtxtDbUser.getDocument().addDocumentListener(dirty);
        jbtnDbDriverLib.addActionListener(new DirectoryEvent(jtxtDbDriverLib));
        jNewdbType.addActionListener(dirty);



        jNewdbType.addItem("MySQL");
        jNewdbType.addItem("PostgreSQL");



    }

    /**
     *
     * @return
     */
    @SuppressWarnings("empty-statement")
    public Boolean createMigratedb() {

        if ((!"MySQL".equals(sdbmanager2)) && (!"PostgreSQL".equals(sdbmanager2))) {
            return (false);
        }

// JG Aug 2014 for 3.80
        eScript = "/com/openbravo/pos/scripts/" + sdbmanager2 + "-create.sql";
        eScript1 = "/com/openbravo/pos/scripts/" + sdbmanager2 + "-createjl.sql";
        eScript2 = "/com/openbravo/pos/scripts/" + sdbmanager2 + "-DropFK.sql";
        eScript3 = "/com/openbravo/pos/scripts/SQL-CreateFK.sql";

        if ("".equals(eScript)) {
            return (false);
        }
        // create a blank database to migrate into
        try {
            BatchSentence bsentence = new BatchSentenceResource(session2, eScript);;
            bsentence.putParameter("APP_ID", Matcher.quoteReplacement(AppLocal.APP_ID));
            bsentence.putParameter("APP_NAME", Matcher.quoteReplacement(AppLocal.APP_NAME));
            bsentence.putParameter("APP_VERSION", Matcher.quoteReplacement(AppLocal.APP_VERSION));


            java.util.List l = bsentence.list();
            if (l.size() > 0) {
                JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("migration.warning"), l.toArray(new Throwable[l.size()])));
            }

        } catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_DANGER, AppLocal.getIntString("migration.warningnodefault"), e));
            session.close();
        } finally {
        }


        try {
            BatchSentence bsentence = new BatchSentenceResource(session2, eScript2);
            java.util.List l = bsentence.list();
            if (l.size() > 0) {
                JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("migration.warning"), l.toArray(new Throwable[l.size()])));
            }

        } catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_DANGER, AppLocal.getIntString("migration.warningnofk"), e));
            session.close();
        } finally {
        }


        return (true);
    }

    /**
     *
     * @return
     */
    public Boolean addFKeys() {
        if ("".equals(eScript3)) {
            return (false);
        }
        try {
            BatchSentence bsentence = new BatchSentenceResource(session2, eScript3);
            java.util.List l = bsentence.list();
            if (l.size() > 0) {
                JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("migration.warning"), l.toArray(new Throwable[l.size()])));
            }

        } catch (BasicException e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_DANGER, AppLocal.getIntString("database.ScriptNotFound"), e));
            session.close();
        } finally {
        }
        return (true);

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
     * @return
     */
    public Boolean getSeconddbDetails() {

        String db_user2 = jtxtDbUser.getText();
        String db_url2 = jtxtDbURL.getText();
        char[] pass = jtxtDbPassword.getPassword();
        String db_password2 = new String(pass);

        Properties connectionProps = new Properties();
        connectionProps.put("user", db_user2);
        connectionProps.put("password", db_password2);
        try {
            Class.forName(jtxtDbDriver.getText());

            ClassLoader cloader = new URLClassLoader(new URL[]{new File(jtxtDbDriverLib.getText()).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver) Class.forName(jtxtDbDriver.getText(), true, cloader).newInstance()));
            con2 = (Connection) DriverManager.getConnection(db_url2, db_user2, db_password2);

            session2 = new Session(db_url2, db_user2, db_password2);
            sdbmanager2 = con2.getMetaData().getDatabaseProductName();
            return (true);
        } catch (ClassNotFoundException | MalformedURLException | InstantiationException | IllegalAccessException | SQLException e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_DANGER, AppLocal.getIntString("database.UnableToConnect"), e));
            return (false);
        }


    }

    /**
     *
     * @throws BasicException
     */
    @Override
    public void activate() throws BasicException {
        // connect to the database
        String db_user = (m_props.getProperty("db.user"));
        String db_url = (m_props.getProperty("db.URL"));
        String db_password = (m_props.getProperty("db.password"));

        if (db_user != null && db_password != null && db_password.startsWith("crypt:")) {
            // the password is encrypted
            AltEncrypter cypher = new AltEncrypter("cypherkey" + db_user);
            db_password = cypher.decrypt(db_password.substring(6));
        }

        try {
            session = AppViewConnection.createSession(m_props);
            con = DriverManager.getConnection(db_url, db_user, db_password);
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
        return (true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtnMigrate = new javax.swing.JButton();
        jbtnExit = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
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
        jLabel5 = new javax.swing.JLabel();
        jNewdbType = new javax.swing.JComboBox();
        jButtonTest = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(600, 300));

        jbtnMigrate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jbtnMigrate.setText(AppLocal.getIntString("button.migrate")); // NOI18N
        jbtnMigrate.setMaximumSize(new java.awt.Dimension(70, 33));
        jbtnMigrate.setMinimumSize(new java.awt.Dimension(70, 33));
        jbtnMigrate.setPreferredSize(new java.awt.Dimension(70, 33));
        jbtnMigrate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnMigrateActionPerformed(evt);
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

        jPanel1.setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Database details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel18.setText(AppLocal.getIntString("label.dbdriverlib")); // NOI18N

        jtxtDbDriverLib.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtDbDriverLib.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDbDriverLibActionPerformed(evt);
            }
        });

        jbtnDbDriverLib.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/fileopen.png"))); // NOI18N
        jbtnDbDriverLib.setMaximumSize(new java.awt.Dimension(64, 32));
        jbtnDbDriverLib.setMinimumSize(new java.awt.Dimension(64, 32));
        jbtnDbDriverLib.setPreferredSize(new java.awt.Dimension(64, 32));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText(AppLocal.getIntString("Label.DbDriver")); // NOI18N

        jtxtDbDriver.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(AppLocal.getIntString("Label.DbURL")); // NOI18N

        jtxtDbURL.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtDbURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDbURLActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("Label.DbUser")); // NOI18N

        jtxtDbUser.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtDbUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtDbUserActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("Label.DbPassword")); // NOI18N

        jtxtDbPassword.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel5.setText("New  Database ");

        jNewdbType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNewdbTypeActionPerformed(evt);
            }
        });

        jButtonTest.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButtonTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/database.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jButtonTest.setText(bundle.getString("Button.Test")); // NOI18N
        jButtonTest.setActionCommand(bundle.getString("Button.Test")); // NOI18N
        jButtonTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTestjButtonTestConnectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtDbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtDbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(jButtonTest, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jNewdbType, 0, 394, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtxtDbDriver, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                                    .addComponent(jtxtDbDriverLib, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtDbURL))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnDbDriverLib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbtnDbDriverLib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jNewdbType, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtDbDriverLib, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtDbDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDbURL, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtDbUser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtDbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonTest, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(364, Short.MAX_VALUE)
                .addComponent(jbtnMigrate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnMigrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnMigrateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnMigrateActionPerformed
        if (getSeconddbDetails()) {
// check if this a supported migration path         

            if (createMigratedb()) {

                try {
                    stmt = (Statement) con.createStatement();
                    stmt2 = (Statement) con2.createStatement();

// copy attribute table       
                    SQL = "SELECT * FROM ATTRIBUTE";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO ATTRIBUTE (ID, NAME) VALUES (?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.executeUpdate();
                    }

//  copy attributeinstance table        
                    SQL = "SELECT * FROM ATTRIBUTEINSTANCE";
                    while (rs.next()) {
                        SQL = "INSERT INTO ATTRIBUTEINSTANCE (ID, ATTRIBUTEINSTANCE_ID, ATTRIBUTE_ID, VALUE) VALUES (?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("ATTRIBUTEINSTANCE_ID"));
                        pstmt.setString(3, rs.getString("ATTRIBUTE_ID"));
                        pstmt.setString(4, rs.getString("VALUE"));
                        pstmt.executeUpdate();
                    }

// copy attributeset table       
                    SQL = "SELECT * FROM ATTRIBUTESET";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO ATTRIBUTESET (ID, NAME) VALUES (?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.executeUpdate();
                    }

// copy attributesetinstance table       
                    SQL = "SELECT * FROM ATTRIBUTESETINSTANCE";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO ATTRIBUTESETINSTANCE (ID, ATTRIBUTESET_ID, DESCRIPTION) VALUES (?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        //System.out.println(rs.getString("DESCRIPTION"));
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("ATTRIBUTESET_ID"));
                        pstmt.setString(3, rs.getString("DESCRIPTION"));
                        pstmt.executeUpdate();
                    }

// copy attributeuse table       
                    SQL = "SELECT * FROM ATTRIBUTEUSE";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO ATTRIBUTEUSE(ID, ATTRIBUTESET_ID, ATTRIBUTE_ID, LINENO) VALUES (?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("ATTRIBUTESET_ID"));
                        pstmt.setString(3, rs.getString("ATTRIBUTE_ID"));
                        pstmt.setInt(4, rs.getInt("LINENO"));
                        pstmt.executeUpdate();
                    }

// copy attributevalue table       
                    SQL = "SELECT * FROM ATTRIBUTEVALUE";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO ATTRIBUTEVALUE (ID, ATTRIBUTE_ID, VALUE) VALUES (?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("ATTRIBUTE_ID"));
                        pstmt.setString(3, rs.getString("VALUE"));
                        pstmt.executeUpdate();
                    }

// copy breaks table       
                    SQL = "SELECT * FROM BREAKS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO BREAKS(ID, NAME, NOTES, VISIBLE) VALUES (?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setString(3, rs.getString("NOTES"));
                        pstmt.setBoolean(4, rs.getBoolean("VISIBLE"));
                        pstmt.executeUpdate();
                    }

// copy categories table       
                    SQL = "SELECT * FROM CATEGORIES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO CATEGORIES(ID, NAME, PARENTID, IMAGE, TEXTTIP, CATSHOWNAME ) VALUES (?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setString(3, rs.getString("PARENTID"));
                        pstmt.setBytes(4, rs.getBytes("IMAGE"));
                        pstmt.setString(5, rs.getString("TEXTTIP"));
                        pstmt.setBoolean(6, rs.getBoolean("CATSHOWNAME"));

                        pstmt.executeUpdate();                        
                    }

// copy closedcash  table       
                    SQL = "SELECT * FROM CLOSEDCASH";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO CLOSEDCASH(MONEY, HOST, HOSTSEQUENCE, DATESTART, DATEEND ) VALUES (?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("MONEY"));
                        pstmt.setString(2, rs.getString("HOST"));
                        pstmt.setInt(3, rs.getInt("HOSTSEQUENCE"));
                        pstmt.setTimestamp(4, rs.getTimestamp("DATESTART"));
                        pstmt.setTimestamp(5, rs.getTimestamp("DATEEND"));
                        pstmt.executeUpdate();
                    }



// copy csvimport  table       
                    SQL = "SELECT * FROM CSVIMPORT";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO CSVIMPORT (ID, ROWNUMBER, CSVERROR, REFERENCE, CODE, NAME, PRICEBUY, PRICESELL, PREVIOUSBUY, PREVIOUSSELL, CATEGORY  ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("ROWNUMBER"));
                        pstmt.setString(3, rs.getString("CSVERROR"));
                        pstmt.setString(4, rs.getString("REFERENCE"));
                        pstmt.setString(5, rs.getString("CODE"));
                        pstmt.setString(6, rs.getString("NAME"));
                        pstmt.setDouble(7, rs.getDouble("PRICEBUY"));
                        pstmt.setDouble(8, rs.getDouble("PRICESELL"));
                        pstmt.setDouble(9, rs.getDouble("PREVIOUSBUY"));
                        pstmt.setDouble(10, rs.getDouble("PREVIOUSSELL"));
                        pstmt.setString(11, rs.getString("CATEGORY"));
                        pstmt.executeUpdate();
                    }



// copy CUSTOMERS  table       
                    SQL = "SELECT * FROM CUSTOMERS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO CUSTOMERS (ID, SEARCHKEY, TAXID, NAME, TAXCATEGORY, CARD, MAXDEBT, ADDRESS, ADDRESS2, POSTAL, CITY,  REGION, COUNTRY, FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX, NOTES, VISIBLE, CURDATE, CURDEBT )"
                                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("SEARCHKEY"));
                        pstmt.setString(3, rs.getString("TAXID"));
                        pstmt.setString(4, rs.getString("NAME"));
                        pstmt.setString(5, rs.getString("TAXCATEGORY"));
                        pstmt.setString(6, rs.getString("CARD"));
                        pstmt.setDouble(7, rs.getDouble("MAXDEBT"));
                        pstmt.setString(8, rs.getString("ADDRESS"));
                        pstmt.setString(9, rs.getString("ADDRESS2"));
                        pstmt.setString(10, rs.getString("POSTAL"));
                        pstmt.setString(11, rs.getString("CITY"));
                        pstmt.setString(12, rs.getString("REGION"));
                        pstmt.setString(13, rs.getString("COUNTRY"));
                        pstmt.setString(14, rs.getString("FIRSTNAME"));
                        pstmt.setString(15, rs.getString("LASTNAME"));
                        pstmt.setString(16, rs.getString("EMAIL"));
                        pstmt.setString(17, rs.getString("PHONE"));
                        pstmt.setString(18, rs.getString("PHONE2"));
                        pstmt.setString(19, rs.getString("FAX"));
                        pstmt.setString(20, rs.getString("NOTES"));
                        pstmt.setBoolean(21, rs.getBoolean("VISIBLE"));
                        pstmt.setTimestamp(22, rs.getTimestamp("CURDATE"));
                        pstmt.setDouble(23, rs.getDouble("CURDEBT"));
                        pstmt.executeUpdate();
                    }


// copy FLOORS table       
                    SQL = "SELECT * FROM FLOORS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO FLOORS (ID, NAME, IMAGE) VALUES (?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setBytes(3, rs.getBytes("IMAGE"));
                        pstmt.executeUpdate();
                    }



// copy LEAVES table       
                    SQL = "SELECT * FROM LEAVES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO LEAVES (ID, PPLID, NAME, STARTDATE, ENDDATE, NOTES) VALUES (?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("PPLID"));
                        pstmt.setString(3, rs.getString("NAME"));
                        pstmt.setTimestamp(4, rs.getTimestamp("STARTDATE"));
                        pstmt.setTimestamp(5, rs.getTimestamp("ENDDATE"));
                        pstmt.setString(6, rs.getString("NOTES"));
                        pstmt.executeUpdate();
                    }



// copy LOCATIONS table       
                    SQL = "SELECT * FROM LOCATIONS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO LOCATIONS (ID, NAME, ADDRESS) VALUES (?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setString(3, rs.getString("ADDRESS"));
                        pstmt.executeUpdate();
                    }


// copy MOORERS TABLE     
                    SQL = "SELECT * FROM MOORERS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO MOORERS (VESSELNAME, SIZE, DAYS, POWER) VALUES (?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("VESSELNAME"));
                        pstmt.setInt(2, rs.getInt("SIZE"));
                        pstmt.setInt(3, rs.getInt("DAYS"));
                        pstmt.setBoolean(4, rs.getBoolean("POWER"));
                        pstmt.executeUpdate();
                    }



// copy payments table       
                    SQL = "SELECT * FROM PAYMENTS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO PAYMENTS (ID, RECEIPT, PAYMENT, TOTAL, TRANSID, NOTES, RETURNMSG) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("RECEIPT"));
                        pstmt.setString(3, rs.getString("PAYMENT"));
                        pstmt.setDouble(4, rs.getDouble("TOTAL"));
                        pstmt.setString(5, rs.getString("TRANSID"));
                        pstmt.setString(6, rs.getString("NOTES"));
                        pstmt.setBytes(7, rs.getBytes("RETURNMSG"));
                        pstmt.executeUpdate();
                    }


// copy PEOPLE table       
                    SQL = "SELECT * FROM PEOPLE";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO PEOPLE (ID, NAME, APPPASSWORD, CARD, ROLE, VISIBLE, IMAGE) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setString(3, rs.getString("APPPASSWORD"));
                        pstmt.setString(4, rs.getString("CARD"));
                        pstmt.setString(5, rs.getString("ROLE"));
                        pstmt.setBoolean(6, rs.getBoolean("VISIBLE"));
                        pstmt.setBytes(7, rs.getBytes("IMAGE"));
                        pstmt.executeUpdate();
                    }

// copy Places table         
                    SQL = "SELECT * FROM PLACES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO PLACES (ID, NAME, X, Y, FLOOR, CUSTOMER, WAITER, TICKETID, TABLEMOVED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setInt(3, rs.getInt("X"));
                        pstmt.setInt(4, rs.getInt("Y"));
                        pstmt.setString(5, rs.getString("FLOOR"));
                        pstmt.setString(6, rs.getString("CUSTOMER"));
                        pstmt.setString(7, rs.getString("WAITER"));
                        pstmt.setString(8, rs.getString("TICKETID"));
                        pstmt.setBoolean(9, rs.getBoolean("TABLEMOVED"));
                        pstmt.executeUpdate();
                    }


// copy Products  table 
// JG Aug 2014 - INSERT STOCKUNITS                    
                    SQL = "SELECT * FROM PRODUCTS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO PRODUCTS (ID, REFERENCE, CODE, CODETYPE, NAME, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, STOCKCOST, STOCKVOLUME, IMAGE, ISCOM, ISSCALE, ISKITCHEN, PRINTKB, SENDSTATUS, ISSERVICE, DISPLAY, ATTRIBUTES, ISVPRICE, ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS)"
                                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("REFERENCE"));
                        pstmt.setString(3, rs.getString("CODE"));
                        pstmt.setString(4, rs.getString("CODETYPE"));
                        pstmt.setString(5, rs.getString("NAME"));
                        pstmt.setDouble(6, rs.getDouble("PRICEBUY"));
                        pstmt.setDouble(7, rs.getDouble("PRICESELL"));
                        pstmt.setString(8, rs.getString("CATEGORY"));
                        pstmt.setString(9, rs.getString("TAXCAT"));
                        pstmt.setString(10, rs.getString("ATTRIBUTESET_ID"));
                        pstmt.setDouble(11, rs.getDouble("STOCKCOST"));
                        pstmt.setDouble(12, rs.getDouble("STOCKVOLUME"));
                        pstmt.setBytes(13, rs.getBytes("IMAGE"));
                        pstmt.setBoolean(14, rs.getBoolean("ISCOM"));
                        pstmt.setBoolean(15, rs.getBoolean("ISSCALE"));
                        pstmt.setBoolean(16, rs.getBoolean("ISKITCHEN"));
                        pstmt.setBoolean(17, rs.getBoolean("PRINTKB"));
                        pstmt.setBoolean(18, rs.getBoolean("SENDSTATUS"));
                        pstmt.setBoolean(19, rs.getBoolean("ISSERVICE"));
                        pstmt.setString(20, rs.getString("DISPLAY"));
                        pstmt.setBytes(21, rs.getBytes("ATTRIBUTES"));
                        pstmt.setBoolean(22, rs.getBoolean("ISVPRICE"));
                        pstmt.setBoolean(23, rs.getBoolean("ISVERPATRIB"));
                        pstmt.setString(24, rs.getString("TEXTTIP"));
                        pstmt.setBoolean(25, rs.getBoolean("WARRANTY"));
// JG Aug 2014 for 3.80 from 3.70
                        pstmt.setDouble(26, rs.getDouble("STOCKUNITS"));                        
// Dis-Allow Product Control account
                    if (!"xxx999_999xxx_x9x9x9".equals(rs.getString(1))) {
                        pstmt.executeUpdate();
                    }
                }


// copy PRODUCTS_CAT table       
                    SQL = "SELECT * FROM PRODUCTS_CAT";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO PRODUCTS_CAT(PRODUCT, CATORDER) VALUES (?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("PRODUCT"));
                        pstmt.setInt(2, rs.getInt("CATORDER"));
// JG Aug 2014 for 3.80
// Dis-Allow Product Control account
if (!"xxx999_999xxx_x9x9x9".equals(rs.getString(1))) {
                        pstmt.executeUpdate();
                    }
                }


                    // copy PRODUCTS_COM table       
                    SQL = "SELECT * FROM PRODUCTS_COM";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO PRODUCTS_COM(ID, PRODUCT, PRODUCT2 ) VALUES (?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("PRODUCT"));
                        pstmt.setString(3, rs.getString("PRODUCT2"));
                        pstmt.executeUpdate();
                    }

                    // copy RECEIPTS table       
                    SQL = "SELECT * FROM RECEIPTS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO RECEIPTS(ID, MONEY, DATENEW, ATTRIBUTES, PERSON ) VALUES (?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("MONEY"));
                        pstmt.setTimestamp(3, rs.getTimestamp("DATENEW"));
                        pstmt.setBytes(4, rs.getBytes("ATTRIBUTES"));
                        pstmt.setString(5, rs.getString("PERSON"));
                        pstmt.executeUpdate();
                    }

// copy reservation_customers table       
                    SQL = "SELECT * FROM RESERVATION_CUSTOMERS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO RESERVATION_CUSTOMERS(ID, CUSTOMER) VALUES (?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("CUSTOMER"));
                        pstmt.executeUpdate();
                    }

                    // copy reservationS table       
                    SQL = "SELECT * FROM RESERVATIONS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO RESERVATIONS(ID, CREATED, DATENEW, TITLE, CHAIRS, ISDONE, DESCRIPTION ) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setTimestamp(2, rs.getTimestamp("CREATED"));
                        pstmt.setTimestamp(3, rs.getTimestamp("DATENEW"));
                        pstmt.setString(4, rs.getString("TITLE"));
                        pstmt.setInt(5, rs.getInt("CHAIRS"));
                        pstmt.setBoolean(6, rs.getBoolean("ISDONE"));
                        pstmt.setString(7, rs.getString("DESCRIPTION"));
                        pstmt.executeUpdate();
                    }


// copy resources table       
                    SQL = "SELECT * FROM RESOURCES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES (?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setInt(3, rs.getInt("RESTYPE"));
                        pstmt.setBytes(4, rs.getBytes("CONTENT"));
                        pstmt.executeUpdate();
                    }


                    // copy ROLES table       
                    SQL = "SELECT * FROM ROLES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO ROLES(ID, NAME, PERMISSIONS ) VALUES (?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setBytes(3, rs.getBytes("PERMISSIONS"));
                        pstmt.executeUpdate();
                    }


                    // copy SHAREDTICKETS table       
                    SQL = "SELECT * FROM SHAREDTICKETS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO SHAREDTICKETS(ID, NAME, CONTENT, APPUSER, PICKUPID ) VALUES (?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setBytes(3, rs.getBytes("CONTENT"));
                        pstmt.setBytes(4, rs.getBytes("APPUSER"));
                        pstmt.setInt(5, rs.getInt("PICKUPID"));
                        pstmt.executeUpdate();
                    }


                    // copy SHIFT_BREAKS table       
                    SQL = "SELECT * FROM SHIFT_BREAKS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO SHIFT_BREAKS(ID, SHIFTID, BREAKID, STARTTIME, ENDTIME ) VALUES (?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("SHIFTID"));
                        pstmt.setString(3, rs.getString("BREAKID"));
                        pstmt.setTimestamp(4, rs.getTimestamp("STARTTIME"));
                        pstmt.setTimestamp(5, rs.getTimestamp("ENDTIME"));
                        pstmt.executeUpdate();
                    }


                    // copy SHIFTS table       
                    SQL = "SELECT * FROM SHIFTS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO SHIFTS(ID, STARTSHIFT, ENDSHIFT, PPLID ) VALUES (?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setTimestamp(2, rs.getTimestamp("STARTSHIFT"));
                        pstmt.setTimestamp(3, rs.getTimestamp("ENDSHIFT"));
                        pstmt.setString(4, rs.getString("PPLID"));
                        pstmt.executeUpdate();
                    }


                    // copy STOCKCURRENT table       
                    SQL = "SELECT * FROM STOCKCURRENT";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO STOCKCURRENT(LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS ) VALUES (?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("LOCATION"));
                        pstmt.setString(2, rs.getString("PRODUCT"));
                        pstmt.setString(3, rs.getString("ATTRIBUTESETINSTANCE_ID"));
                        pstmt.setDouble(4, rs.getDouble("UNITS"));
                        pstmt.executeUpdate();
                    }


                    // copy STOCKDIARY table       
                    SQL = "SELECT * FROM STOCKDIARY";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO STOCKDIARY(ID, DATENEW, REASON, LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, APPUSER ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setTimestamp(2, rs.getTimestamp("DATENEW"));
                        pstmt.setInt(3, rs.getInt("REASON"));
                        pstmt.setString(4, rs.getString("LOCATION"));
                        pstmt.setString(5, rs.getString("PRODUCT"));
                        pstmt.setString(6, rs.getString("ATTRIBUTESETINSTANCE_ID"));
                        pstmt.setDouble(7, rs.getDouble("UNITS"));
                        pstmt.setDouble(8, rs.getDouble("PRICE"));
                        pstmt.setString(9, rs.getString("APPUSER"));
                        pstmt.executeUpdate();
                    }


                    // copy STOCKLEVEL table       
                    SQL = "SELECT * FROM STOCKLEVEL";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO STOCKLEVEL(ID, LOCATION, PRODUCT, STOCKSECURITY, STOCKMAXIMUM ) VALUES (?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("LOCATION"));
                        pstmt.setString(3, rs.getString("PRODUCT"));
                        pstmt.setDouble(4, rs.getDouble("STOCKSECURITY"));
                        pstmt.setDouble(5, rs.getDouble("STOCKMAXIMUM"));
                        pstmt.executeUpdate();
                    }

// copy TAXCATEGORIES table       
                    SQL = "SELECT * FROM TAXCATEGORIES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO TAXCATEGORIES (ID, NAME) VALUES (?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.executeUpdate();
                    }


// copy TAXCUSTCATEGORIES table       
                    SQL = "SELECT * FROM TAXCUSTCATEGORIES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO TAXCUSTCATEGORIES (ID, NAME) VALUES (?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.executeUpdate();
                    }



// copy TAXES table       
                    SQL = "SELECT * FROM TAXES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO TAXES (ID, NAME, CATEGORY, CUSTCATEGORY, PARENTID, RATE, RATECASCADE, RATEORDER ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("NAME"));
                        pstmt.setString(3, rs.getString("CATEGORY"));
                        pstmt.setString(4, rs.getString("CUSTCATEGORY"));
                        pstmt.setString(5, rs.getString("PARENTID"));
                        pstmt.setDouble(6, rs.getDouble("RATE"));
                        pstmt.setBoolean(7, rs.getBoolean("RATECASCADE"));
                        pstmt.setInt(8, rs.getInt("RATEORDER"));
                        pstmt.executeUpdate();
                    }


// copy TAXLINES table       
                    SQL = "SELECT * FROM TAXLINES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO TAXLINES (ID, RECEIPT, TAXID, BASE, AMOUNT ) VALUES (?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("RECEIPT"));
                        pstmt.setString(3, rs.getString("TAXID"));
                        pstmt.setDouble(4, rs.getDouble("BASE"));
                        pstmt.setDouble(5, rs.getDouble("AMOUNT"));
                        pstmt.executeUpdate();
                    }


// copy THIRDPARTIES table       
                    SQL = "SELECT * FROM THIRDPARTIES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO THIRDPARTIES (ID, CIF, NAME, ADDRESS, CONTACTCOMM, CONTACTFACT, PAYRULE, FAXNUMBER, PHONENUMBER, MOBILENUMBER, EMAIL, WEBPAGE, NOTES  ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setString(2, rs.getString("CIF"));
                        pstmt.setString(3, rs.getString("NAME"));
                        pstmt.setString(4, rs.getString("ADDRESS"));
                        pstmt.setString(5, rs.getString("CONTACTCOMM"));
                        pstmt.setString(6, rs.getString("CONTACTFACT"));
                        pstmt.setString(7, rs.getString("PAYRULE"));
                        pstmt.setString(8, rs.getString("FAXNUMBER"));
                        pstmt.setString(9, rs.getString("PHONENUMBER"));
                        pstmt.setString(10, rs.getString("MOBILENUMBER"));
                        pstmt.setString(11, rs.getString("EMAIL"));
                        pstmt.setString(12, rs.getString("WEBPAGE"));
                        pstmt.setString(13, rs.getString("NOTES"));
                        pstmt.executeUpdate();
                    }

// copy TICKETLINES table       
                    SQL = "SELECT * FROM TICKETLINES";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO TICKETLINES (TICKET, LINE, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, TAXID, ATTRIBUTES ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("TICKET"));
                        pstmt.setInt(2, rs.getInt("LINE"));
                        pstmt.setString(3, rs.getString("PRODUCT"));
                        pstmt.setString(4, rs.getString("ATTRIBUTESETINSTANCE_ID"));
                        pstmt.setDouble(5, rs.getDouble("UNITS"));
                        pstmt.setDouble(6, rs.getDouble("PRICE"));
                        pstmt.setString(7, rs.getString("TAXID"));
                        pstmt.setBytes(8, rs.getBytes("ATTRIBUTES"));
                        pstmt.executeUpdate();
                    }


// copy TICKETS table       
                    SQL = "SELECT * FROM TICKETS";
                    rs = stmt.executeQuery(SQL);
                    while (rs.next()) {
                        SQL = "INSERT INTO TICKETS (ID, TICKETTYPE, TICKETID, PERSON, CUSTOMER, STATUS ) VALUES (?, ?, ?, ?, ?, ?)";
                        pstmt = con2.prepareStatement(SQL);
                        pstmt.setString(1, rs.getString("ID"));
                        pstmt.setInt(2, rs.getInt("TICKETTYPE"));
                        pstmt.setInt(3, rs.getInt("TICKETID"));
                        pstmt.setString(4, rs.getString("PERSON"));
                        pstmt.setString(5, rs.getString("CUSTOMER"));
                        pstmt.setInt(6, rs.getInt("STATUS"));
                        pstmt.executeUpdate();
                    }


// GET THE SEQUENCE NUMBERS
                    if (("Apache Derby".equals(sdbmanager)) || ("MySQL".equals(sdbmanager))) {
                        SQL = "SELECT * FROM TICKETSNUM";
                        rs = stmt.executeQuery(SQL);
                        while (rs.next()) {
                            ticketsnum = rs.getString("ID");
                        }
                        SQL = "SELECT * FROM TICKETSNUM_PAYMENT";
                        rs = stmt.executeQuery(SQL);
                        while (rs.next()) {
                            ticketsnumPayment = rs.getString("ID");
                        }
                        SQL = "SELECT * FROM TICKETSNUM_REFUND";
                        rs = stmt.executeQuery(SQL);
                        while (rs.next()) {
                            ticketsnumRefund = rs.getString("ID");
                        }
                    } else {
                        SQL = "SELECT * FROM TICKETSNUM";
                        rs = stmt.executeQuery(SQL);
                        while (rs.next()) {
                            ticketsnum = rs.getString("LAST_VALUE");
                        }
                        SQL = "SELECT * FROM TICKETSNUM_PAYMENT";
                        rs = stmt.executeQuery(SQL);
                        while (rs.next()) {
                            ticketsnumPayment = rs.getString("LAST_VALUE");
                        }
                        SQL = "SELECT * FROM TICKETSNUM_REFUND";
                        rs = stmt.executeQuery(SQL);
                        while (rs.next()) {
                            ticketsnumRefund = rs.getString("LAST_VALUE");
                        }
                    }



// WRITE SEQUENCE NUMBER
                    if (("Apache Derby".equals(sdbmanager2)) || ("MySQL".equals(sdbmanager2))) {
                        SQL = "UPDATE TICKETSNUM SET ID=" + ticketsnum;
                        stmt2.executeUpdate(SQL);
                        SQL = "UPDATE TICKETSNUM_PAYMENT SET ID=" + ticketsnumPayment;
                        stmt2.executeUpdate(SQL);
                        SQL = "UPDATE TICKETSNUM_REFUND SET ID=" + ticketsnumRefund;
                        stmt2.executeUpdate(SQL);
                    } else {
                        SQL = "ALTER SEQUENCE TICKETSNUM RESTART WITH " + ticketsnum;
                        stmt2.executeUpdate(SQL);
                        SQL = "ALTER SEQUENCE TICKETSNUM_PAYMENT RESTART WITH " + ticketsnumPayment;
                        stmt2.executeUpdate(SQL);
                        SQL = "ALTER SEQUENCE TICKETSNUM_REFUND RESTART WITH " + ticketsnumRefund;
                        stmt2.executeUpdate(SQL);
                    }

// Add foreign keys back into the datbase
                    addFKeys();

// Write new database settings to properties file


//JG Aug 2014 - added to .properties
                    if ("MySQL".equals(sdbmanager2)) {
                        config.setProperty("db.engine", "MySQL");
                    }else{
                        config.setProperty("db.engine", "PostgreSQL");
                    }
//                    
                    config.setProperty("db.driverlib", jtxtDbDriverLib.getText());
                    config.setProperty("db.driver", jtxtDbDriver.getText());
                    config.setProperty("db.URL", jtxtDbURL.getText());
                    config.setProperty("db.user", jtxtDbUser.getText());
                    AltEncrypter cypher = new AltEncrypter("cypherkey" + jtxtDbUser.getText());
                    config.setProperty("db.password", "crypt:" + cypher.encrypt(new String(jtxtDbPassword.getPassword())));
                    dirty.setDirty(false);


                    for (PanelConfig c : m_panelconfig) {
                        c.saveProperties(config);
                    }

                    try {
                        config.save();
                        JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.restartchanges"), AppLocal.getIntString("message.title"), JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException e) {
                        JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotsaveconfig"), e));
                    }


                    JOptionPane.showMessageDialog(this, "Migration complete.");
                    jbtnMigrate.setEnabled(false);


                } catch (SQLException | HeadlessException e) {
                    JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, SQL, e));
                }
            } else {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, AppLocal.getIntString("message.migratenotsupported"), AppLocal.getIntString("message.nigratemessage"), JOptionPane.WARNING_MESSAGE);

            }
        }
    }//GEN-LAST:event_jbtnMigrateActionPerformed

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        deactivate();
        System.exit(0);
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jtxtDbURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDbURLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDbURLActionPerformed

    private void jtxtDbDriverLibActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDbDriverLibActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDbDriverLibActionPerformed

    private void jNewdbTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNewdbTypeActionPerformed
        if ("MySQL".equals(jNewdbType.getSelectedItem())) {
            jtxtDbDriverLib.setText(System.getProperty("user.dir") + "/lib/mysql-connector-java-5.1.26-bin.jar");
            jtxtDbDriver.setText("com.mysql.jdbc.Driver");
            jtxtDbURL.setText("jdbc:mysql://localhost:3306/unicentaopos");

        } else if ("PostgreSQL".equals(jNewdbType.getSelectedItem())) {
            jtxtDbDriverLib.setText(System.getProperty("user.dir") + "/lib/postgresql-9.2-1003.jdbc4.jar");
            jtxtDbDriver.setText("org.postgresql.Driver");
            jtxtDbURL.setText("jdbc:postgresql://localhost:5432/unicentaopos");
        } else {
        }
    }//GEN-LAST:event_jNewdbTypeActionPerformed

    private void jtxtDbUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtDbUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtDbUserActionPerformed

    private void jButtonTestjButtonTestConnectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTestjButtonTestConnectionActionPerformed
        try {
            String driverlib = jtxtDbDriverLib.getText();
            String driver = jtxtDbDriver.getText();
            String url = jtxtDbURL.getText();
            String user = jtxtDbUser.getText();
            String password = new String(jtxtDbPassword.getPassword());

            ClassLoader cloader = new URLClassLoader(new URL[]{new File(driverlib).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver) Class.forName(driver, true, cloader).newInstance()));

            Session session = new Session(url, user, password);
            Connection connection = session.getConnection();
            boolean isValid = (connection == null) ? false : connection.isValid(1000);

            if (isValid) {
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.databaseconnectsuccess"), "Connection Test", JOptionPane.INFORMATION_MESSAGE);
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
    }//GEN-LAST:event_jButtonTestjButtonTestConnectionActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonTest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox jNewdbType;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbtnDbDriverLib;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnMigrate;
    private javax.swing.JTextField jtxtDbDriver;
    private javax.swing.JTextField jtxtDbDriverLib;
    private javax.swing.JPasswordField jtxtDbPassword;
    private javax.swing.JTextField jtxtDbURL;
    private javax.swing.JTextField jtxtDbUser;
    // End of variables declaration//GEN-END:variables
}
