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

package com.openbravo.pos.ticket;

import java.util.List;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.ListQBFModelNumber;
import com.openbravo.data.loader.QBFCompareEnum;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.editor.JEditorKeys;
import com.openbravo.editor.JEditorString;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;


/**
 *
 * @author JG uniCenta
 */
public class ProductFilterSales extends javax.swing.JPanel implements EditorCreator {
    
    private final SentenceList m_sentcat;
    private ComboBoxValModel m_CategoryModel;

    /** Creates new form ProductFilterSales
     * @param dlSales
     * @param jKeys */
    public ProductFilterSales(DataLogicSales dlSales, JEditorKeys jKeys) {
        initComponents();

        m_jtxtBarCode.addEditorKeys(jKeys);
        m_jtxtName.addEditorKeys(jKeys);
        
        // El modelo de categorias
        m_sentcat = dlSales.getCategoriesList();
        m_CategoryModel = new ComboBoxValModel();           
        
        m_jCboPriceBuy.setModel(ListQBFModelNumber.getMandatoryNumber());
        m_jPriceBuy.addEditorKeys(jKeys);
        
        m_jCboPriceSell.setModel(ListQBFModelNumber.getMandatoryNumber());
        m_jPriceSell.addEditorKeys(jKeys);
        
// JG July 2014 - added for Stock count
        m_jCboStockUnits.setModel(ListQBFModelNumber.getOverrideMandatoryNumber());
        m_jStockUnits.addEditorKeys(jKeys);                
    }
    
    /**
     *
     */
    public void activate() {
        
        m_jtxtBarCode.reset();
        m_jtxtBarCode.setEditModeEnum(JEditorString.MODE_123);
        m_jtxtBarCode.activate();
        m_jtxtName.reset();
        m_jPriceBuy.reset();
        m_jPriceSell.reset();
// JG July 2014 - added for Stock count        
        m_jCboStockUnits.setSelectedIndex(2);
        m_jStockUnits.setDoubleValue(0.0);        
        
        try {
            List catlist = m_sentcat.list();
            catlist.add(0, null);
            m_CategoryModel = new ComboBoxValModel(catlist);
            m_jCategory.setModel(m_CategoryModel);
        } catch (BasicException eD) {
            // no hay validacion
        }
    }
    
    /**
     *
     * @return
     * @throws BasicException
     */
    @Override
    public Object createValue() throws BasicException {
        
        Object[] afilter = new Object[12];

        // BarCode
        if (m_jtxtBarCode.getText() == null || m_jtxtBarCode.getText().equals("")) {
            afilter[8] = QBFCompareEnum.COMP_NONE;
            afilter[9] = null;
        } else{
            afilter[8] = QBFCompareEnum.COMP_RE;
            afilter[9] = "%" + m_jtxtBarCode.getText() + "%";
        }
        
        // Product Name/Description
        if (m_jtxtName.getText() == null || m_jtxtName.getText().equals("")) {
            afilter[0] = QBFCompareEnum.COMP_NONE;
            afilter[1] = null;
        } else {
            afilter[0] = QBFCompareEnum.COMP_RE;
            afilter[1] = "%" + m_jtxtName.getText() + "%";
        }
        
        // Precio de compra
        afilter[3] = m_jPriceBuy.getDoubleValue();
        afilter[2] = afilter[3] == null ? QBFCompareEnum.COMP_NONE : m_jCboPriceBuy.getSelectedItem();

        // Precio de venta
        afilter[5] = m_jPriceSell.getDoubleValue();
        afilter[4] = afilter[5] == null ? QBFCompareEnum.COMP_NONE : m_jCboPriceSell.getSelectedItem();
        
        // Categoria
        if (m_CategoryModel.getSelectedKey() == null) {
            afilter[6] = QBFCompareEnum.COMP_NONE;
            afilter[7] = null;
        } else {
            afilter[6] = QBFCompareEnum.COMP_EQUALS;
            afilter[7] = m_CategoryModel.getSelectedKey();
        }
        
        // Inventory
// JG July 2014 - added for Stock Count
//        afilter[11] = m_jStockUnits.getDoubleValue();
//        afilter[10] = afilter[11] == null ? QBFCompareEnum.COMP_NONE : m_jCboStockUnits.getSelectedItem();

        if (m_jStockUnits.getDoubleValue() == null) {
            m_jCboStockUnits.setSelectedIndex(2);
            m_jStockUnits.setDoubleValue(0.0);
            repaint();
            afilter[10] = QBFCompareEnum.COMP_GREATER;
            afilter[11] = 0;
        } else {
            afilter[10] = m_jCboStockUnits.getSelectedItem();
            afilter[11] = m_jStockUnits.getDoubleValue();
        }
        
System.out.println(afilter[10]);
System.out.println(afilter[11]);
System.out.println(afilter);

return afilter;
    } 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_jtxtBarCode = new com.openbravo.editor.JEditorString();
        m_jtxtName = new com.openbravo.editor.JEditorString();
        m_jCategory = new javax.swing.JComboBox();
        m_jCboPriceBuy = new javax.swing.JComboBox();
        m_jPriceBuy = new com.openbravo.editor.JEditorCurrency();
        m_jCboPriceSell = new javax.swing.JComboBox();
        m_jCboStockUnits = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        m_jPriceSell = new com.openbravo.editor.JEditorCurrency();
        m_jStockUnits = new com.openbravo.editor.JEditorDouble();

        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(370, 200));
        setLayout(null);

        m_jtxtBarCode.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        add(m_jtxtBarCode);
        m_jtxtBarCode.setBounds(130, 10, 290, 25);

        m_jtxtName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        add(m_jtxtName);
        m_jtxtName.setBounds(130, 40, 290, 25);

        m_jCategory.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        add(m_jCategory);
        m_jCategory.setBounds(130, 70, 260, 25);

        m_jCboPriceBuy.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jCboPriceBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jCboPriceBuyActionPerformed(evt);
            }
        });
        add(m_jCboPriceBuy);
        m_jCboPriceBuy.setBounds(130, 100, 150, 25);

        m_jPriceBuy.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jPriceBuy.setPreferredSize(new java.awt.Dimension(130, 25));
        add(m_jPriceBuy);
        m_jPriceBuy.setBounds(290, 100, 130, 25);

        m_jCboPriceSell.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        add(m_jCboPriceSell);
        m_jCboPriceSell.setBounds(130, 130, 150, 25);

        m_jCboStockUnits.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        add(m_jCboStockUnits);
        m_jCboStockUnits.setBounds(130, 160, 150, 25);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText(AppLocal.getIntString("label.prodbarcode")); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(20, 10, 110, 25);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText(AppLocal.getIntString("label.prodname")); // NOI18N
        add(jLabel5);
        jLabel5.setBounds(20, 40, 110, 25);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(AppLocal.getIntString("label.prodcategory")); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(20, 70, 110, 25);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(AppLocal.getIntString("label.prodpricebuy")); // NOI18N
        add(jLabel4);
        jLabel4.setBounds(20, 100, 110, 25);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText(AppLocal.getIntString("label.prodpricesell")); // NOI18N
        add(jLabel6);
        jLabel6.setBounds(20, 130, 110, 25);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(AppLocal.getIntString("label.stockunits")); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(20, 160, 110, 25);

        m_jPriceSell.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jPriceSell.setPreferredSize(new java.awt.Dimension(130, 25));
        add(m_jPriceSell);
        m_jPriceSell.setBounds(290, 130, 130, 25);

        m_jStockUnits.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jStockUnits.setPreferredSize(new java.awt.Dimension(130, 25));
        add(m_jStockUnits);
        m_jStockUnits.setBounds(290, 160, 130, 25);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jCboPriceBuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jCboPriceBuyActionPerformed

    }//GEN-LAST:event_m_jCboPriceBuyActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JComboBox m_jCategory;
    private javax.swing.JComboBox m_jCboPriceBuy;
    private javax.swing.JComboBox m_jCboPriceSell;
    private javax.swing.JComboBox m_jCboStockUnits;
    private com.openbravo.editor.JEditorCurrency m_jPriceBuy;
    private com.openbravo.editor.JEditorCurrency m_jPriceSell;
    private com.openbravo.editor.JEditorDouble m_jStockUnits;
    private com.openbravo.editor.JEditorString m_jtxtBarCode;
    private com.openbravo.editor.JEditorString m_jtxtName;
    // End of variables declaration//GEN-END:variables
    
}
