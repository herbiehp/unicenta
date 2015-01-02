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
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import java.awt.Component;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.SkinInfo;
import com.openbravo.pos.util.DirectoryEvent;

// JG 16 May 2013 deprecated for pushingpixels
// import org.jvnet.substance.SubstanceLookAndFeel;
// import org.jvnet.substance.api.SubstanceSkin;
// import org.jvnet.substance.skin.SkinInfo;

/**
 *
 * @author JG uniCenta
 */

public class JPanelConfigGeneral extends javax.swing.JPanel implements PanelConfig {

    private final DirtyManager dirty = new DirtyManager();

    /** Creates new form JPanelConfigGeneral */
    public JPanelConfigGeneral() {

        initComponents();

        jtxtMachineHostname.getDocument().addDocumentListener(dirty);
        jcboLAF.addActionListener(dirty);
        jcboMachineScreenmode.addActionListener(dirty);
        jcboTicketsBag.addActionListener(dirty);
        jchkHideInfo.addActionListener(dirty);  
        jtxtStartupText.getDocument().addDocumentListener(dirty);
        jbtnLogoText.addActionListener(new DirectoryEvent(jtxtStartupText));        
        jtxtStartupLogo.getDocument().addDocumentListener(dirty);
        jbtnLogoName.addActionListener(new DirectoryEvent(jtxtStartupLogo));
        
        // Installed skins
        LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
        for (LookAndFeelInfo laf : lafs) {
            jcboLAF.addItem(new LAFInfo(laf.getName(), laf.getClassName()));
        }

        // Substance skins
        // new SubstanceLookAndFeel()
        Map<String, SkinInfo> skins = SubstanceLookAndFeel.getAllSkins();

        for (SkinInfo skin : skins.values()) {
            jcboLAF.addItem(new LAFInfo(skin.getDisplayName(), skin.getClassName()));
        }

        jcboLAF.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeLAF();
            }
        });

        jcboMachineScreenmode.addItem("window");
        jcboMachineScreenmode.addItem("fullscreen");

        jcboTicketsBag.addItem("simple");
        jcboTicketsBag.addItem("standard");
        jcboTicketsBag.addItem("restaurant");
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

        jtxtMachineHostname.setText(config.getProperty("machine.hostname"));

        String lafclass = config.getProperty("swing.defaultlaf");
        jcboLAF.setSelectedItem(null);
        for (int i = 0; i < jcboLAF.getItemCount(); i++) {
            LAFInfo lafinfo = (LAFInfo) jcboLAF.getItemAt(i);
            if (lafinfo.getClassName().equals(lafclass)) {
                jcboLAF.setSelectedIndex(i);
                break;
            }
        }
        // jcboLAF.setSelectedItem(new LookAndFeelInfo());

        jcboMachineScreenmode.setSelectedItem(config.getProperty("machine.screenmode"));
        jcboTicketsBag.setSelectedItem(config.getProperty("machine.ticketsbag"));
        jchkHideInfo.setSelected(Boolean.valueOf(config.getProperty("till.hideinfo")).booleanValue());        
        jtxtStartupLogo.setText(config.getProperty("start.logo"));
        jtxtStartupText.setText(config.getProperty("start.text"));          
  
        dirty.setDirty(false);
    }

    /**
     *
     * @param config
     */
    @Override
    public void saveProperties(AppConfig config) {

        config.setProperty("machine.hostname", jtxtMachineHostname.getText());
        
        LAFInfo laf = (LAFInfo) jcboLAF.getSelectedItem();
        config.setProperty("swing.defaultlaf", laf == null
                ? System.getProperty("swing.defaultlaf", "javax.swing.plaf.metal.MetalLookAndFeel")
                : laf.getClassName());

        config.setProperty("machine.screenmode", comboValue(jcboMachineScreenmode.getSelectedItem()));
        config.setProperty("machine.ticketsbag", comboValue(jcboTicketsBag.getSelectedItem()));
        config.setProperty("till.hideinfo", Boolean.toString(jchkHideInfo.isSelected()));         
        config.setProperty("start.logo", jtxtStartupLogo.getText());
        config.setProperty("start.text", jtxtStartupText.getText());
        
        dirty.setDirty(false);
    }

    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }

    private void changeLAF() {

        final LAFInfo laf = (LAFInfo) jcboLAF.getSelectedItem();
        if (laf != null && !laf.getClassName().equals(UIManager.getLookAndFeel().getClass().getName())) {
            // The selected look and feel is different from the current look and feel.
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    try {
                        String lafname = laf.getClassName();
                        Object laf = Class.forName(lafname).newInstance();

                        if (laf instanceof LookAndFeel) {
                            UIManager.setLookAndFeel((LookAndFeel) laf);
                        } else if (laf instanceof SubstanceSkin) {
                            SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);
                        }

                        SwingUtilities.updateComponentTreeUI(JPanelConfigGeneral.this.getTopLevelAncestor());
// JG 6 May 2013 to Multicatch
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    }
                }
            });
        }
    }

    private static class LAFInfo {

        private final String name;
        private final String classname;

        public LAFInfo(String name, String classname) {
            this.name = name;
            this.classname = classname;
        }

        public String getName() {
            return name;
        }

        public String getClassName() {
            return classname;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxtMachineHostname = new javax.swing.JTextField();
        jcboLAF = new javax.swing.JComboBox();
        jcboMachineScreenmode = new javax.swing.JComboBox();
        jcboTicketsBag = new javax.swing.JComboBox();
        jchkHideInfo = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jtxtStartupLogo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jtxtStartupText = new javax.swing.JTextField();
        jbtnLogoName = new javax.swing.JButton();
        jbtnLogoText = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(650, 450));

        jPanel11.setPreferredSize(new java.awt.Dimension(650, 450));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText(AppLocal.getIntString("Label.MachineName")); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(AppLocal.getIntString("label.looknfeel")); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("Label.MachineScreen")); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 30));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("Label.Ticketsbag")); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 30));

        jtxtMachineHostname.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtMachineHostname.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jtxtMachineHostname.setMinimumSize(new java.awt.Dimension(130, 25));
        jtxtMachineHostname.setPreferredSize(new java.awt.Dimension(200, 30));

        jcboLAF.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboLAF.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboLAF.setPreferredSize(new java.awt.Dimension(200, 30));
        jcboLAF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboLAFActionPerformed(evt);
            }
        });

        jcboMachineScreenmode.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachineScreenmode.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachineScreenmode.setPreferredSize(new java.awt.Dimension(200, 30));

        jcboTicketsBag.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboTicketsBag.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboTicketsBag.setPreferredSize(new java.awt.Dimension(200, 30));

        jchkHideInfo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jchkHideInfo.setText(bundle.getString("label.Infopanel")); // NOI18N
        jchkHideInfo.setToolTipText("");
        jchkHideInfo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jchkHideInfo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jchkHideInfo.setMaximumSize(new java.awt.Dimension(0, 25));
        jchkHideInfo.setMinimumSize(new java.awt.Dimension(0, 0));
        jchkHideInfo.setPreferredSize(new java.awt.Dimension(150, 30));
        jchkHideInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkHideInfoActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("label.startuppanel"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel1.setLayout(null);

        jLabel18.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel18.setText(bundle.getString("label.startuplogo")); // NOI18N
        jLabel18.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabel18.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabel18.setPreferredSize(new java.awt.Dimension(0, 30));
        jPanel1.add(jLabel18);
        jLabel18.setBounds(10, 20, 90, 30);

        jtxtStartupLogo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtStartupLogo.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtStartupLogo.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtStartupLogo.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel1.add(jtxtStartupLogo);
        jtxtStartupLogo.setBounds(110, 20, 350, 30);

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setText(AppLocal.getIntString("label.startuptext")); // NOI18N
        jLabel19.setMaximumSize(new java.awt.Dimension(0, 25));
        jLabel19.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabel19.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel1.add(jLabel19);
        jLabel19.setBounds(10, 60, 70, 25);

        jtxtStartupText.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtxtStartupText.setMaximumSize(new java.awt.Dimension(0, 25));
        jtxtStartupText.setMinimumSize(new java.awt.Dimension(0, 0));
        jtxtStartupText.setPreferredSize(new java.awt.Dimension(350, 30));
        jtxtStartupText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtStartupTextActionPerformed(evt);
            }
        });
        jtxtStartupText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtStartupTextjTetxtStartupTextFocusGained(evt);
            }
        });
        jPanel1.add(jtxtStartupText);
        jtxtStartupText.setBounds(110, 60, 350, 30);

        jbtnLogoName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/fileopen.png"))); // NOI18N
        jbtnLogoName.setMaximumSize(new java.awt.Dimension(64, 32));
        jbtnLogoName.setMinimumSize(new java.awt.Dimension(64, 32));
        jbtnLogoName.setPreferredSize(new java.awt.Dimension(64, 32));
        jPanel1.add(jbtnLogoName);
        jbtnLogoName.setBounds(480, 20, 64, 32);

        jbtnLogoText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/fileopen.png"))); // NOI18N
        jbtnLogoText.setMaximumSize(new java.awt.Dimension(64, 32));
        jbtnLogoText.setMinimumSize(new java.awt.Dimension(64, 32));
        jbtnLogoText.setPreferredSize(new java.awt.Dimension(64, 32));
        jPanel1.add(jbtnLogoText);
        jbtnLogoText.setBounds(480, 60, 64, 32);

        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 153));
        jButton1.setText("X");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(60, 63, 40, 23);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboLAF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboMachineScreenmode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtMachineHostname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jchkHideInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcboTicketsBag, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtMachineHostname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboLAF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboMachineScreenmode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboTicketsBag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchkHideInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jcboLAFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboLAFActionPerformed

    }//GEN-LAST:event_jcboLAFActionPerformed

    private void jtxtStartupTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtStartupTextActionPerformed

    }//GEN-LAST:event_jtxtStartupTextActionPerformed

    private void jtxtStartupTextjTetxtStartupTextFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtStartupTextjTetxtStartupTextFocusGained
        // JG 31 August 2103 GNU GPL License Warning

        transferFocus();

        JOptionPane.showMessageDialog(jPanel1,"<html>Changing default Startup Text content may violate the <br>"
            + " Free Software Foundation's GNU General Public License GPL","GNU GPL Warning",JOptionPane.WARNING_MESSAGE);

    }//GEN-LAST:event_jtxtStartupTextjTetxtStartupTextFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jtxtStartupText.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jchkHideInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkHideInfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkHideInfoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JButton jbtnLogoName;
    private javax.swing.JButton jbtnLogoText;
    private javax.swing.JComboBox jcboLAF;
    private javax.swing.JComboBox jcboMachineScreenmode;
    private javax.swing.JComboBox jcboTicketsBag;
    private javax.swing.JCheckBox jchkHideInfo;
    private javax.swing.JTextField jtxtMachineHostname;
    private javax.swing.JTextField jtxtStartupLogo;
    private javax.swing.JTextField jtxtStartupText;
    // End of variables declaration//GEN-END:variables
}
