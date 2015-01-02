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

import bsh.EvalError;
import bsh.Interpreter;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.JMessageDialog;
import com.openbravo.data.gui.ListKeyed;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.customers.JCustomerFinder;
import com.openbravo.pos.forms.*;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.panels.JProductFinder;
import com.openbravo.pos.payment.JPaymentSelect;
import com.openbravo.pos.payment.JPaymentSelectReceipt;
import com.openbravo.pos.payment.JPaymentSelectRefund;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.sales.restaurant.RestaurantDBUtils;
import com.openbravo.pos.scale.ScaleException;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.util.AltEncrypter;
import com.openbravo.pos.util.InactivityListener;
import com.openbravo.pos.util.JRPrinterAWT300;
import com.openbravo.pos.util.ReportUtils;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author adrianromero
 */
public abstract class JPanelTicket extends JPanel implements JPanelView, BeanFactoryApp, TicketsEditor {
   
    // Variable numerica
    private final static int NUMBERZERO = 0;
    private final static int NUMBERVALID = 1;
    
    private final static int NUMBER_INPUTZERO = 0;
    private final static int NUMBER_INPUTZERODEC = 1;
    private final static int NUMBER_INPUTINT = 2;
    private final static int NUMBER_INPUTDEC = 3; 
    private final static int NUMBER_PORZERO = 4; 
    private final static int NUMBER_PORZERODEC = 5; 
    private final static int NUMBER_PORINT = 6; 
    private final static int NUMBER_PORDEC = 7; 

    /**
     *
     */
    protected JTicketLines m_ticketlines;
        
    // private Template m_tempLine;
    private TicketParser m_TTP;
    
    /**
     *
     */
    protected TicketInfo m_oTicket; 

    /**
     *
     */
    protected Object m_oTicketExt; 
    
    // Estas tres variables forman el estado...
    private int m_iNumberStatus;
    private int m_iNumberStatusInput;
    private int m_iNumberStatusPor;
    private StringBuffer m_sBarcode;
            
    private JTicketsBag m_ticketsbag;
    
    private SentenceList senttax;
    private ListKeyed taxcollection;
    // private ComboBoxValModel m_TaxModel;
    
    private SentenceList senttaxcategories;
    private ListKeyed taxcategoriescollection;
    private ComboBoxValModel taxcategoriesmodel;
    
    private TaxesLogic taxeslogic;
    
    /**
     *
     */
    protected JPanelButtons m_jbtnconfig;
    
    /**
     *
     */
    protected AppView m_App;

    /**
     *
     */
    protected DataLogicSystem dlSystem;

    /**
     *
     */
    protected DataLogicSales dlSales;

    /**
     *
     */
    protected DataLogicCustomers dlCustomers;
    
    private JPaymentSelect paymentdialogreceipt;
    private JPaymentSelect paymentdialogrefund;

// added 27.04.13 JDL    
    private JRootApp root;
    private Object m_principalapp;
    private Boolean restaurant;

// added 27.04.13 JDL for inactivity and Auto logoff    
    private Action logout;
    private InactivityListener listener;
    private Integer delay = 0;
    private final String m_sCurrentTicket = null;

    /**
     *
     */
    protected TicketsEditor m_panelticket; 
    private DataLogicReceipts dlReceipts = null;
// added 16.05.13 JDL customer name on table    
    private Boolean priceWith00;
    private final String temp_jPrice="";
    private String tableDetails;
    private RestaurantDBUtils restDB;
    private KitchenDisplay kitchenDisplay;
    private String ticketPrintType;
    
// added 25.05.13 JDl warranty receipt
    private Boolean warrantyPrint=false;
//   private String loyaltyCardNumber=null;
    
    
    /** Creates new form JTicketView */
    public JPanelTicket() {
        
        initComponents ();
    }
   
    /**
     *
     * @param app
     * @throws BeanFactoryException
     */
    @Override
    public void init(AppView app) throws BeanFactoryException {
       
        m_App = app;
        restDB = new  RestaurantDBUtils(m_App);
       
        dlSystem = (DataLogicSystem) m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
        dlSales = (DataLogicSales) m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        dlCustomers = (DataLogicCustomers) m_App.getBean("com.openbravo.pos.customers.DataLogicCustomers");
        dlReceipts = (DataLogicReceipts) app.getBean("com.openbravo.pos.sales.DataLogicReceipts");
                    
        // borramos el boton de bascula si no hay bascula conectada
        if (!m_App.getDeviceScale().existsScale()) {
            m_jbtnScale.setVisible(false);
        }
//      JG 19 Feb 14 unnecessary parse - if (Boolean.valueOf(m_App.getProperties().getProperty("till.amountattop")).booleanValue()){
        if (Boolean.valueOf(m_App.getProperties().getProperty("till.amountattop"))){
            m_jPanEntries.remove(jPanel9);
            m_jPanEntries.remove(m_jNumberKeys);        
            m_jPanEntries.add(jPanel9);
            m_jPanEntries.add(m_jNumberKeys);
        }        
 
//      JG 19 Feb 14 unnecessary parse -  jbtnMooring.setVisible(Boolean.valueOf(m_App.getProperties().getProperty("till.marineoption")).booleanValue());
        jbtnMooring.setVisible(Boolean.valueOf(m_App.getProperties().getProperty("till.marineoption")));

        priceWith00 = ("true".equals(m_App.getProperties().getProperty("till.pricewith00")));
        if (priceWith00) {
            // use '00' instead of '.'
            m_jNumberKeys.dotIs00(true);
        }
           
        m_ticketsbag = getJTicketsBag();

        m_jPanelBag.add(m_ticketsbag.getBagComponent(), BorderLayout.LINE_START);
        add(m_ticketsbag.getNullComponent(), "null");
        
        m_ticketlines = new JTicketLines(dlSystem.getResourceAsXML("Ticket.Line"));
        m_jPanelCentral.add(m_ticketlines, java.awt.BorderLayout.CENTER);
        
        m_TTP = new TicketParser(m_App.getDeviceTicket(), dlSystem);
               
        // Los botones configurables...
        m_jbtnconfig = new JPanelButtons("Ticket.Buttons", this);
        m_jButtonsExt.add(m_jbtnconfig);           
       
        // El panel de los productos o de las lineas...        
        catcontainer.add(getSouthComponent(), BorderLayout.CENTER);
        
        // El modelo de impuestos
        senttax = dlSales.getTaxList();
        senttaxcategories = dlSales.getTaxCategoriesList();
        
        taxcategoriesmodel = new ComboBoxValModel();    
              
        // ponemos a cero el estado
        stateToZero();  
        
        // inicializamos
        m_oTicket = null;
        m_oTicketExt = null;      
        
    }

    /**
     *
     * @return
     */
    @Override
    public Object getBean() {
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

    private class logout extends AbstractAction {
        public logout() {
        }
        @Override
    public void actionPerformed(ActionEvent ae){
       // timer.stop();   
       // lets check what mode we are operating in   
       switch (m_App.getProperties().getProperty("machine.ticketsbag")){
           case "restaurant":                   
//Go back to the main login screen if not set to go back to the tables.               
        if ("false".equals(m_App.getProperties().getProperty("till.autoLogoffrestaurant")))  {
            deactivate();
            ((JRootApp)m_App).closeAppView();
            break;
        }     
        deactivate();
        setActiveTicket(null, null);      
            break;                
       default:
            deactivate();
          ((JRootApp)m_App).closeAppView();
       }
       }
    }

    private void saveCurrentTicket() {
        String currentTicket =(String)m_oTicketExt;
        if (currentTicket != null) {
            try {
                dlReceipts.updateSharedTicket(currentTicket, m_oTicket,m_oTicket.getPickupId());
            } catch (BasicException e) {
                new MessageInf(e).show(this);
            }  
        }    
    }

    /**
     *
     * @throws BasicException
     */
    @Override
    public void activate() throws BasicException {
// added by JDL 26.04.13
// lets look at adding a timer event fot auto logoff if required
        Action logout = new logout();        
        String autoLogoff = (m_App.getProperties().getProperty("till.autoLogoff"));
        if (autoLogoff != null){
        if (autoLogoff.equals("true")){
            try{
            delay = Integer.parseInt(m_App.getProperties().getProperty("till.autotimer"));
// JG 19 Feb 14 -  }catch (Exception e){
            }catch (NumberFormatException e){
            delay=0;
            }
            delay *= 1000;
        }}
// if the delay period is not zero create a inactivitylistener instance        
        if (delay != 0){
            listener = new InactivityListener(logout,delay); 
            listener.start();
        } 
        
        paymentdialogreceipt = JPaymentSelectReceipt.getDialog(this);
        paymentdialogreceipt.init(m_App);
        paymentdialogrefund = JPaymentSelectRefund.getDialog(this); 
        paymentdialogrefund.init(m_App);
        
        // impuestos incluidos seleccionado ?
        m_jaddtax.setSelected("true".equals(m_jbtnconfig.getProperty("taxesincluded")));

// JG May 2013 use Diamond inference
        java.util.List<TaxInfo> taxlist = senttax.list();
        taxcollection = new ListKeyed<>(taxlist);
        java.util.List<TaxCategoryInfo> taxcategorieslist = senttaxcategories.list();
        taxcategoriescollection = new ListKeyed<>(taxcategorieslist);
        
        taxcategoriesmodel = new ComboBoxValModel(taxcategorieslist);
        m_jTax.setModel(taxcategoriesmodel);

        String taxesid = m_jbtnconfig.getProperty("taxcategoryid");
        if (taxesid == null) {
            if (m_jTax.getItemCount() > 0) {
                m_jTax.setSelectedIndex(0);
            }
        } else {
            taxcategoriesmodel.setSelectedKey(taxesid);
        }              
                
        taxeslogic = new TaxesLogic(taxlist);
        
        
// Added JDL change the startup state of addtax button
// JG 19 Feb 14 unnecessary parse -  m_jaddtax.setSelected((Boolean.valueOf(m_App.getProperties().getProperty("till.taxincluded")).booleanValue()));          
          m_jaddtax.setSelected((Boolean.parseBoolean(m_App.getProperties().getProperty("till.taxincluded"))));  
           

        // Show taxes options
        if (m_App.getAppUserView().getUser().hasPermission("sales.ChangeTaxOptions")) {
            m_jTax.setVisible(true);
            m_jaddtax.setVisible(true);
        } else {
            m_jTax.setVisible(false);
            m_jaddtax.setVisible(false);
        }

        // Authorization for buttons
        btnSplit.setEnabled(m_App.getAppUserView().getUser().hasPermission("sales.Total"));
        m_jDelete.setEnabled(m_App.getAppUserView().getUser().hasPermission("sales.EditLines"));
        m_jNumberKeys.setMinusEnabled(m_App.getAppUserView().getUser().hasPermission("sales.EditLines"));
        m_jNumberKeys.setEqualsEnabled(m_App.getAppUserView().getUser().hasPermission("sales.Total"));
        m_jbtnconfig.setPermissions(m_App.getAppUserView().getUser());  
               
        m_ticketsbag.activate();  
        
            }

    @Override
    public boolean deactivate() {
        if (listener  != null) {
            listener.stop();
        }
        
        return m_ticketsbag.deactivate();
    }
    
    /**
     *
     * @return
     */
    protected abstract JTicketsBag getJTicketsBag();

    /**
     *
     * @return
     */
    protected abstract Component getSouthComponent();

    /**
     *
     */
    protected abstract void resetSouthComponent();

    /**
     *
     * @param oTicket
     * @param oTicketExt
     */
    @SuppressWarnings("empty-statement")
    @Override
    public void setActiveTicket(TicketInfo oTicket, Object oTicketExt) {
// check if a inactivity timer has been created, and if it is not running start up again
// this is required for autologoff mode in restaurant and it is set to return to the table view.        
       switch (m_App.getProperties().getProperty("machine.ticketsbag")){
           case "restaurant":                              
        if ("true".equals(m_App.getProperties().getProperty("till.autoLogoffrestaurant"))) {
            if (listener  != null) {
                listener.restart();
            }
        }          
       }
         
        m_oTicket = oTicket;
        m_oTicketExt = oTicketExt;
       
        if (m_oTicket != null) {            
            // Asign preliminary properties to the receipt
            m_oTicket.setUser(m_App.getAppUserView().getUser().getUserInfo());
            m_oTicket.setActiveCash(m_App.getActiveCashIndex());
            m_oTicket.setDate(new Date()); // Set the edition date.
            
// Set some of the table details here if in restaurant mode
//      if ("restaurant".equals(m_App.getProperties().getProperty("machine.ticketsbag"))&& m_oTicket.getTicketType()!=1){
        if ("restaurant".equals(m_App.getProperties().getProperty("machine.ticketsbag"))&& !oTicket.getOldTicket()){            
// Check if there is a customer name in the database for this table

                if (restDB.getCustomerNameInTable(oTicketExt.toString())== null ){
                    if (m_oTicket.getCustomer() != null){
                       restDB.setCustomerNameInTable(m_oTicket.getCustomer().toString(), oTicketExt.toString()); 
                    }
                } 
//Check if the waiters name is in the table, this will be the person who opened the ticket                        
                if (restDB.getWaiterNameInTable(oTicketExt.toString())==null || "".equals(restDB.getWaiterNameInTable(oTicketExt.toString()))){
// JG 19 Feb 14 unnecessary .toString used - restDB.setWaiterNameInTable(m_App.getAppUserView().getUser().getName().toString(),oTicketExt.toString());
                    restDB.setWaiterNameInTable(m_App.getAppUserView().getUser().getName(),oTicketExt.toString());
                    }              
                        restDB.setTicketIdInTable(m_oTicket.getId(),oTicketExt.toString());
                           
        }}
    
      
// lets check if this is a moved ticket        
// JG 19 Feb 14 mod booleans and strings
//        if ((m_oTicket != null) && (((Boolean.valueOf(m_App.getProperties().getProperty("table.showwaiterdetails")).booleanValue()) || 
//                 (Boolean.valueOf(m_App.getProperties().getProperty("table.showcustomerdetails")).booleanValue())))){
        if ((m_oTicket != null) && (((Boolean.parseBoolean(m_App.getProperties().getProperty("table.showwaiterdetails"))) || 
                 (Boolean.valueOf(m_App.getProperties().getProperty("table.showcustomerdetails")))))){
            
        }        
// ditto + flipped operands
//        if ((m_oTicket != null) && (((Boolean.valueOf(m_App.getProperties().getProperty("table.showwaiterdetails")).booleanValue()) || 
//                 (Boolean.valueOf(m_App.getProperties().getProperty("table.showcustomerdetails")).booleanValue())))){        
        if ((m_oTicket != null) && (((Boolean.valueOf(m_App.getProperties().getProperty("table.showcustomerdetails"))) || 
                (Boolean.parseBoolean(m_App.getProperties().getProperty("table.showwaiterdetails")))))){
// check if the old table and the new table are the same                      
                if (restDB.getTableMovedFlag(m_oTicket.getId())){
                        restDB.moveCustomer(oTicketExt.toString(),m_oTicket.getId());
                    }                                                
        }

        executeEvent(m_oTicket, m_oTicketExt, "ticket.show");
    
        if ("restaurant".equals(m_App.getProperties().getProperty("machine.ticketsbag"))){
      //      j_btnKitchenPrt.setVisible(false); 
        }else{
            j_btnKitchenPrt.setVisible(m_App.getAppUserView().getUser().hasPermission("sales.PrintKitchen")); 
         }
        refreshTicket();               
    }
    
    /**
     *
     * @return
     */
    @Override
    public TicketInfo getActiveTicket() {
        return m_oTicket;
    }
    
    private void refreshTicket() {
        
        CardLayout cl = (CardLayout)(getLayout());
        
        if (m_oTicket == null) {        
            m_jTicketId.setText(null);            
            m_ticketlines.clearTicketLines();
           
            m_jSubtotalEuros.setText(null);
            m_jTaxesEuros.setText(null);
            m_jTotalEuros.setText(null); 
        
            stateToZero();
            repaint();
            
            // Muestro el panel de nulos.
            cl.show(this, "null");  
// John L June 2014
//            resetSouthComponent();
            if ((m_oTicket != null) &&  (m_oTicket.getLinesCount()==0)) {
              resetSouthComponent();                
            }

        } else {
            if (m_oTicket.getTicketType() == TicketInfo.RECEIPT_REFUND) {
                //Make disable Search and Edit Buttons
                m_jEditLine.setVisible(false);
                m_jList.setVisible(false);
            }
            
            // Refresh ticket taxes
            for (TicketLineInfo line : m_oTicket.getLines()) {
                line.setTaxInfo(taxeslogic.getTaxInfo(line.getProductTaxCategoryID(), m_oTicket.getCustomer()));
            }  
        
            // The ticket name
            m_jTicketId.setText(m_oTicket.getName(m_oTicketExt));

            // Limpiamos todas las filas y anadimos las del ticket actual
            m_ticketlines.clearTicketLines();

            for (int i = 0; i < m_oTicket.getLinesCount(); i++) {
                m_ticketlines.addTicketLine(m_oTicket.getLine(i));
            }
            printPartialTotals();
            stateToZero();
            
            // Muestro el panel de tickets.
            cl.show(this, "ticket");
// JG June 2014 - Thanks John L
//            resetSouthComponent();
            if (m_oTicket.getLinesCount()==0) {
                  resetSouthComponent();
            }
            
            // activo el tecleador...
            m_jKeyFactory.setText(null);       
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    m_jKeyFactory.requestFocus();
                }
            });
        }
    }
     
    private void printPartialTotals(){
               
        if (m_oTicket.getLinesCount() == 0) {
            m_jSubtotalEuros.setText(null);
            m_jTaxesEuros.setText(null);
            m_jTotalEuros.setText(null);
            repaint();
        } else {
            m_jSubtotalEuros.setText(m_oTicket.printSubTotal());
            m_jTaxesEuros.setText(m_oTicket.printTax());
            m_jTotalEuros.setText(m_oTicket.printTotal());
        }
    }
    
    private void paintTicketLine(int index, TicketLineInfo oLine){
        
        if (executeEventAndRefresh("ticket.setline", new ScriptArg("index", index), new ScriptArg("line", oLine)) == null) {

            m_oTicket.setLine(index, oLine);
            m_ticketlines.setTicketLine(index, oLine);
            m_ticketlines.setSelectedIndex(index);

            visorTicketLine(oLine); // Y al visor tambien...
            printPartialTotals();   
            stateToZero();  

            // event receipt
            executeEventAndRefresh("ticket.change");
        }
   }

    private void addTicketLine(ProductInfoExt oProduct, double dMul, double dPrice) {           
// Added JDL 19.12.12 Variable Price Product    
// take the number entered and convert to an amount rather than quantity. 
// modified 02.05.13 read tax selected from the panel
// modified 22.06.13 to allow mulitplier to be used with variable price           
//        oProduct.setTaxCategoryID(((TaxCategoryInfo) taxcategoriesmodel.getSelectedItem()).getID()); 
        
        if (oProduct.isVprice()){
            TaxInfo tax = taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), m_oTicket.getCustomer());
            if (m_jaddtax.isSelected()) {
                dPrice /= (1 + tax.getRate());
            }
                addTicketLine(new TicketLineInfo(oProduct, dMul, dPrice, tax, (java.util.Properties) (oProduct.getProperties().clone())));
        } else {        
                TaxInfo tax = taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), m_oTicket.getCustomer());
                addTicketLine(new TicketLineInfo(oProduct, dMul, dPrice, tax, (java.util.Properties) (oProduct.getProperties().clone())));                
            }
        }
    
    /**
     *
     * @param oLine
     */
    protected void addTicketLine(TicketLineInfo oLine) {  
        if (executeEventAndRefresh("ticket.addline", new ScriptArg("line", oLine)) == null) {        
            if (oLine.isProductCom()) {
                // Comentario entonces donde se pueda
                int i = m_ticketlines.getSelectedIndex();
                // me salto el primer producto normal...
                if (i >= 0 && !m_oTicket.getLine(i).isProductCom()) {
                    i++;
                }
                // me salto todos los productos auxiliares...
                while (i >= 0 && i < m_oTicket.getLinesCount() && m_oTicket.getLine(i).isProductCom()) {
                    i++;
                }
                if (i >= 0) {
                    m_oTicket.insertLine(i, oLine);
                    m_ticketlines.insertTicketLine(i, oLine); // Pintamos la linea en la vista...                 
                } else {
                    Toolkit.getDefaultToolkit().beep();                                   
                }
            } else {    
                // Producto normal, entonces al finalnewline.getMultiply() 
                m_oTicket.addLine(oLine);            
                m_ticketlines.addTicketLine(oLine); // Pintamos la linea en la vista... 
             
                try {
                int i =  m_ticketlines.getSelectedIndex();
                TicketLineInfo line = m_oTicket.getLine(i);                
                if (line.isProductVerpatrib()){
                    JProductAttEdit attedit = JProductAttEdit.getAttributesEditor(this, m_App.getSession());
                    attedit.editAttributes(line.getProductAttSetId(), line.getProductAttSetInstId());
                    attedit.setVisible(true);
                if (attedit.isOK()) {
                    // The user pressed OK
                    line.setProductAttSetInstId(attedit.getAttributeSetInst());
                    line.setProductAttSetInstDesc(attedit.getAttributeSetInstDescription());
                    paintTicketLine(i, line);
                }}
            } catch (BasicException ex) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotfindattributes"), ex);
                msg.show(this);
            }          
            }
               
            visorTicketLine(oLine);
            printPartialTotals();   
            stateToZero();  

            // event receipt
//            executeEventAndRefresh("ticket.change");
//  sunnytang change added by JG 3 July 2013 forum post
            executeEvent(m_oTicket, m_oTicketExt, "ticket.change");             
        }
    }    
    
    private void removeTicketLine(int i){

        if (executeEventAndRefresh("ticket.removeline", new ScriptArg("index", i)) == null) {
// JN uniCenta record removed line
            String ticketID = Integer.toString(m_oTicket.getTicketId());
            if (m_oTicket.getTicketId()==0){ticketID="No Sale";}
        
            dlSystem.execLineRemoved(
                    new Object[] {
                        m_App.getAppUserView().getUser().getName(),
                        ticketID,
                        m_oTicket.getLine(i).getProductID(),
                        m_oTicket.getLine(i).getProductName(),
                        m_oTicket.getLine(i).getMultiply()
                    });
            
            if (m_oTicket.getLine(i).isProductCom()) {
                // Es un producto auxiliar, lo borro y santas pascuas.
                m_oTicket.removeLine(i);
                m_ticketlines.removeTicketLine(i);   
            } else {
                // Es un producto normal, lo borro.
                m_oTicket.removeLine(i);
                m_ticketlines.removeTicketLine(i); 
                // Y todos lo auxiliaries que hubiera debajo.
                while(i < m_oTicket.getLinesCount() && m_oTicket.getLine(i).isProductCom()) {
                    m_oTicket.removeLine(i);
                    m_ticketlines.removeTicketLine(i);
                }
            }  
                    
            visorTicketLine(null); // borro el visor 
            printPartialTotals(); // pinto los totales parciales...                           
            stateToZero(); // Pongo a cero    

            // event receipt
            executeEventAndRefresh("ticket.change");
        }
    }
    
    private ProductInfoExt getInputProduct() { 
        ProductInfoExt oProduct = new ProductInfoExt(); // Es un ticket
// JG 6 May 14 - always add Default Prod ID + Null Name if free line entry
        oProduct.setID("xxx999_999xxx_x9x9x9");
        oProduct.setReference(null);
        oProduct.setCode(null);
        oProduct.setName("");
        oProduct.setTaxCategoryID(((TaxCategoryInfo) taxcategoriesmodel.getSelectedItem()).getID());
        oProduct.setPriceSell(includeTaxes(oProduct.getTaxCategoryID(), getInputValue())); 

        return oProduct;
    }
    
    private double includeTaxes(String tcid, double dValue) {
        if (m_jaddtax.isSelected()) {
            TaxInfo tax = taxeslogic.getTaxInfo(tcid, m_oTicket.getCustomer());
            double dTaxRate = tax == null ? 0.0 : tax.getRate();           
            return dValue / (1.0 + dTaxRate);      
        } else {
            return dValue;
        }
    }
    
   private double excludeTaxes(String tcid, double dValue) {
            TaxInfo tax = taxeslogic.getTaxInfo(tcid, m_oTicket.getCustomer());
            double dTaxRate = tax == null ? 0.0 : tax.getRate();           
            return dValue / (1.0 + dTaxRate);  
    } 
    
     
    private double getInputValue() {
        try {
           // Double ret = Double.parseDouble(m_jPrice.getText());
           // return priceWith00 ? ret / 100 : ret;
            return Double.parseDouble(m_jPrice.getText());
        } catch (NumberFormatException e){
            return 0.0;
        }
    }

    private double getPorValue() {
        try {
            return Double.parseDouble(m_jPor.getText().substring(1));                
// JG May 2013 replaced with Multicatch
        } catch (NumberFormatException | StringIndexOutOfBoundsException e){
            return 1.0;
        }
    }
    
    private void stateToZero(){
        m_jPor.setText("");
        m_jPrice.setText("");
        m_sBarcode = new StringBuffer();
            
        m_iNumberStatus = NUMBER_INPUTZERO;
        m_iNumberStatusInput = NUMBERZERO;
        m_iNumberStatusPor = NUMBERZERO;
        repaint();
    }
    
    private void incProductByCode(String sCode) {
    // precondicion: sCode != null
        try {
            ProductInfoExt oProduct = dlSales.getProductInfoByCode(sCode);
            if (oProduct == null) {                  
                Toolkit.getDefaultToolkit().beep();                   
// JG Aug 2014 - MessageInf Type inappropriate
//                new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.noproduct")).show(this);           
                JOptionPane.showMessageDialog(null,
                   sCode + " - " + AppLocal.getIntString("message.noproduct"),"Check", JOptionPane.WARNING_MESSAGE);                
                stateToZero();
            } else {
                // Se anade directamente una unidad con el precio y todo
                incProduct(oProduct);
            }
        } catch (BasicException eData) {
            stateToZero();           
            new MessageInf(eData).show(this);           
        }
    }
    
    private void incProductByCodePrice(String sCode, double dPriceSell) {
    // precondicion: sCode != null
        try {
            ProductInfoExt oProduct = dlSales.getProductInfoByCode(sCode);
            if (oProduct == null) {                  
                Toolkit.getDefaultToolkit().beep();                   
                new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.noproduct")).show(this);           
                stateToZero();
            } else {
                // Se anade directamente una unidad con el precio y todo
                if (m_jaddtax.isSelected()) {
                    // debemos quitarle los impuestos ya que el precio es con iva incluido...
                    TaxInfo tax = taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), m_oTicket.getCustomer());
                    addTicketLine(oProduct, 1.0, dPriceSell / (1.0 + tax.getRate()));
                } else {
                    addTicketLine(oProduct, 1.0, dPriceSell);
                }                
            }
        } catch (BasicException eData) {
            stateToZero();
            new MessageInf(eData).show(this);               
        }
    }
    
    private void incProduct(ProductInfoExt prod) {

        if (prod.isScale() && m_App.getDeviceScale().existsScale()) {
            try {
                Double value = m_App.getDeviceScale().readWeight();
                if (value != null) {
                    incProduct(value, prod);
                }
            } catch (ScaleException e) {
                Toolkit.getDefaultToolkit().beep();                
                    new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.noweight"), e).show(this);           
                stateToZero(); 
            }
        } else {
            
        // Amended JG uniCenta Apr 14 Variable Price Product - Thanks Ron Isaacson
        // if variable price product, and no amount entered before product pressed, ensure that the multiplier is 0 so no item is added to the ticket           
        if (!prod.isVprice()){
               incProduct(1.0, prod);          
        } else {
                Toolkit.getDefaultToolkit().beep();                
                JOptionPane.showMessageDialog(null, 
                AppLocal.getIntString("message.novprice"));
                }
            }
         }
 
    
    private void incProduct(double dPor, ProductInfoExt prod) {
        // precondicion: prod != null
        if (prod.isVprice()){
            addTicketLine(prod, getPorValue(), getInputValue());    
        }else {        
            addTicketLine(prod, dPor, prod.getPriceSell());
        }
      
    }
       
    /**
     *
     * @param prod
     */
    protected void buttonTransition(ProductInfoExt prod) {
    // precondicion: prod != null       
         if (m_iNumberStatusInput == NUMBERZERO && m_iNumberStatusPor == NUMBERZERO) {
            incProduct(prod);
        } else if (m_iNumberStatusInput == NUMBERVALID && m_iNumberStatusPor == NUMBERZERO) {          
        //} else if (m_iNumberStatusInput == NUMBERZERO && m_iNumberStatusPor == NUMBERVALID) {                      
            incProduct(getInputValue(), prod);
        } else if (prod.isVprice()){           
            addTicketLine(prod, getPorValue(), getInputValue()) ;                
        } else {
            Toolkit.getDefaultToolkit().beep();
        }       
    }
    
    @SuppressWarnings("empty-statement")
    private void stateTransition(char cTrans) {

        if ((cTrans == '\n') || (cTrans == '?')) {
            // Codigo de barras introducido
            if (m_sBarcode.length() > 0) { 
// added JDL 23.05.13                 
                String sCode = m_sBarcode.toString();
 // added JDL 23.05.13 if stirng is longer than 10 remove the
 //               if ((sCode.length() > 10) && priceWith00) {
 //                   sCode = sCode.replace(".","");
 //                   }
                if (sCode.startsWith("c")) {
                    // barcode of a customers card
                    try {
                        CustomerInfoExt newcustomer = dlSales.findCustomerExt(sCode);
                        if (newcustomer == null) {
                            Toolkit.getDefaultToolkit().beep();                   
                            new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.nocustomer")).show(this);           
                        } else {
                            m_oTicket.setCustomer(newcustomer);
                            m_jTicketId.setText(m_oTicket.getName(m_oTicketExt));
                        }
                    } catch (BasicException e) {
                        Toolkit.getDefaultToolkit().beep();                   
                        new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.nocustomer"), e).show(this);           
                    }
                    stateToZero();
                } else if (sCode.startsWith(";")){
                                                        
                   
                   stateToZero();
                /*  commented out by janar153 @ 24.12.2013 to add scale barcode support  
                    }else if (sCode.length() == 13 && sCode.startsWith("250")) {
                    // barcode of the other machine
                    ProductInfoExt oProduct = new ProductInfoExt(); // Es un ticket
                    oProduct.setReference(null); // para que no se grabe
                    oProduct.setCode(sCode);
                    oProduct.setName("Ticket " + sCode.substring(3, 7));
                    oProduct.setPriceSell(Double.parseDouble(sCode.substring(7, 12)) / 100);   
                    oProduct.setTaxCategoryID(((TaxCategoryInfo) taxcategoriesmodel.getSelectedItem()).getID());
                    // Se anade directamente una unidad con el precio y todo
                    addTicketLine(oProduct, 1.0, includeTaxes(oProduct.getTaxCategoryID(), oProduct.getPriceSell()));
                } else if (sCode.length() == 13 && sCode.startsWith("210")) {
                    // barcode of a weigth product
                    incProductByCodePrice(sCode.substring(0, 7), Double.parseDouble(sCode.substring(7, 12)) / 100);
                */
                // added by janar153 @ 24.12.2013 to add scale barcode support
// JG 27 Apr 2014 - missing bracket enclosures                } else if((sCode.length() == 13) && sCode.startsWith("2") || sCode.startsWith("02")) {
//                } else if((sCode.length() == 13) || ((sCode.length() == 8)) && (sCode.startsWith("2") || sCode.startsWith("02"))) {
                } else if((sCode.length() == 13) || (sCode.length() == 8) || (sCode.startsWith("2") || sCode.startsWith("02"))) {                    
                    try {
                        ProductInfoExt oProduct = dlSales.getProductInfoByCode(sCode);
                        if(oProduct == null) {
                            Toolkit.getDefaultToolkit().beep();
//JG Aug 2014 - MessageInf Type inappropriate
//                            new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.noproduct")).show(this);
                            JOptionPane.showMessageDialog(null,
                                sCode + " - " + AppLocal.getIntString("message.noproduct"),
                                "Check", JOptionPane.WARNING_MESSAGE);  
                            stateToZero();
                        } else {
                            // set product properties that they can be used later in oPOS resources
                            oProduct.setProperty("product.barcode", sCode);
                            // get product base from barcode
                            double dPriceSell = oProduct.getPriceSell(); // default price for product
                            double weight = 1.0; // used if barcode includes weight
                            
                            String sVariableTypePrefix = sCode.substring(0, 2);
// JG Aug 2014 - Check for EAN-8 construct
                            String sVariableNum; 
                            if (sCode.length() > 8) {
                                sVariableNum = sCode.substring(8, 12);
                            } else {
                                sVariableNum = null;
                            }
//                            
                            if(sVariableTypePrefix.equals("20")) {
                                dPriceSell = Double.parseDouble(sVariableNum) / 100; // price with two decimals
                            } 
                            else if (sVariableTypePrefix.equals("21")) {
                                dPriceSell = Double.parseDouble(sVariableNum) / 10; //price with one decimal
                            } 
                            else if (sVariableTypePrefix.equals("22")) {
                                dPriceSell = Double.parseDouble(sVariableNum); //price with no decimals
                            } 
                            else if (sVariableTypePrefix.equals("23")) {
                                weight = Double.parseDouble(sVariableNum) / 1000; //weight in kg with three decimals (eg. 1,234 kg)
                            } 
                            else if (sVariableTypePrefix.equals("24")) {
                                weight = Double.parseDouble(sVariableNum) / 100; //weight in kg with two decimals (eg. 12,34 kg)
                            } 
                            else if (sVariableTypePrefix.equals("25")) {
                                weight = Double.parseDouble(sVariableNum) / 10; //weight in kg with one decimal (eg. 123,4 kg)
                            } 
                            else if(sVariableTypePrefix.equals("28")) {
                                sVariableNum = sCode.substring(7, 12);
                                dPriceSell = Double.parseDouble(sVariableNum) / 100; // price with two decimals
                            }
                            
                            if((sVariableTypePrefix.equals("20")) || (sVariableTypePrefix.equals("21")) || (sVariableTypePrefix.equals("22"))) {
                                // price in barcode includes tax, so remove tax  from sPriceSell
                                TaxInfo tax = taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), m_oTicket.getCustomer());
                                dPriceSell /= (1.0 + tax.getRate());
                                oProduct.setProperty("product.price", Double.toString(dPriceSell));
                            } else if((sVariableTypePrefix.equals("23")) || (sVariableTypePrefix.equals("24")) || (sVariableTypePrefix.equals("25"))) {
                                oProduct.setProperty("product.weight", Double.toString(weight)); 
                            } else if((sVariableTypePrefix.equals("28"))) {
                                // price in barcode includes tax, so remove tax  from sPriceSell
                                TaxInfo tax = taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), m_oTicket.getCustomer());
                                dPriceSell /= (1.0 + tax.getRate());
                                oProduct.setProperty("product.price", Double.toString(dPriceSell));
                                weight = -1.0;
                            }
                            
                            if(m_jaddtax.isSelected()) {
                                TaxInfo tax = taxeslogic.getTaxInfo(oProduct.getTaxCategoryID(), m_oTicket.getCustomer());
                                addTicketLine(oProduct, weight, dPriceSell / (1.0 + tax.getRate()));
                            } else {
                                addTicketLine(oProduct, weight, dPriceSell);
                            }
                            
                        }
                    } catch(BasicException eData) {
                        stateToZero();
                        new MessageInf(eData).show(this);
                    }
                } else {
                    incProductByCode(sCode);
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        } else {
            // otro caracter
            // Esto es para el codigo de barras...
            m_sBarcode.append(cTrans);

            // Esto es para el los productos normales...
            if (cTrans == '\u007f') { 
                stateToZero();

            } else if ((cTrans == '0') && (m_iNumberStatus == NUMBER_INPUTZERO)) {
                m_jPrice.setText("0");
            } else if ((cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && (m_iNumberStatus == NUMBER_INPUTZERO)) {
                // Un numero entero - an integer
                if (!priceWith00){
                    m_jPrice.setText(Character.toString(cTrans));
                }else{ 
                    m_jPrice.setText(setTempjPrice(Character.toString(cTrans)));  
                }
                
                
                m_iNumberStatus = NUMBER_INPUTINT;
                m_iNumberStatusInput = NUMBERVALID;
            } else if ((cTrans == '0' || cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && (m_iNumberStatus == NUMBER_INPUTINT)) {
                // Un numero entero - an integer
                if (!priceWith00){
                    m_jPrice.setText(m_jPrice.getText() + cTrans);
                }else{ 
                   m_jPrice.setText(setTempjPrice( m_jPrice.getText() + cTrans));  
                }


            } else if (cTrans == '.' && m_iNumberStatus == NUMBER_INPUTZERO && !priceWith00) {
                m_jPrice.setText("0.");
                m_iNumberStatus = NUMBER_INPUTZERODEC;
            } else if (cTrans == '.' && m_iNumberStatus == NUMBER_INPUTZERO) {
                m_jPrice.setText("");
                m_iNumberStatus = NUMBER_INPUTZERO;
            } else if (cTrans == '.' && m_iNumberStatus == NUMBER_INPUTINT && !priceWith00) {
                m_jPrice.setText(m_jPrice.getText() + ".");
                m_iNumberStatus = NUMBER_INPUTDEC;
            } else if (cTrans == '.' && m_iNumberStatus == NUMBER_INPUTINT) {
                if (!priceWith00){
                    m_jPrice.setText(m_jPrice.getText() + "00");
                }else{ 
                   m_jPrice.setText(setTempjPrice( m_jPrice.getText() + "00"));  
                }                

                m_iNumberStatus = NUMBER_INPUTINT;

            } else if ((cTrans == '0') && (m_iNumberStatus == NUMBER_INPUTZERODEC || m_iNumberStatus == NUMBER_INPUTDEC)) {
                // Un numero decimal - a decimal number

                if (!priceWith00){
                    m_jPrice.setText(m_jPrice.getText() + cTrans);
                }else{ 
                    m_jPrice.setText(setTempjPrice( m_jPrice.getText() + cTrans)); 
                }                
                 
                //
            } else if ((cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && (m_iNumberStatus == NUMBER_INPUTZERODEC || m_iNumberStatus == NUMBER_INPUTDEC)) {
                // Un numero decimal- a decimal number
                m_jPrice.setText(m_jPrice.getText() + cTrans);
                m_iNumberStatus = NUMBER_INPUTDEC;
                m_iNumberStatusInput = NUMBERVALID;

            } else if (cTrans == '*' && (m_iNumberStatus == NUMBER_INPUTINT || m_iNumberStatus == NUMBER_INPUTDEC)) {
                m_jPor.setText("x");
                m_iNumberStatus = NUMBER_PORZERO;
            } else if (cTrans == '*' && (m_iNumberStatus == NUMBER_INPUTZERO || m_iNumberStatus == NUMBER_INPUTZERODEC)) {
                m_jPrice.setText("0");
                m_jPor.setText("x");
                m_iNumberStatus = NUMBER_PORZERO;

            } else if ((cTrans == '0') && (m_iNumberStatus == NUMBER_PORZERO)) {
                m_jPor.setText("x0");
            } else if ((cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && (m_iNumberStatus == NUMBER_PORZERO)) {
                // Un numero entero
                m_jPor.setText("x" + Character.toString(cTrans));
                m_iNumberStatus = NUMBER_PORINT;
                m_iNumberStatusPor = NUMBERVALID;
            } else if ((cTrans == '0' || cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') && (m_iNumberStatus == NUMBER_PORINT)) {
                // Un numero entero
                m_jPor.setText(m_jPor.getText() + cTrans);

            } else if (cTrans == '.' && m_iNumberStatus == NUMBER_PORZERO && !priceWith00) {
                m_jPor.setText("x0.");
                m_iNumberStatus = NUMBER_PORZERODEC;
            } else if (cTrans == '.' && m_iNumberStatus == NUMBER_PORZERO) {
                m_jPor.setText("x");
                m_iNumberStatus = NUMBERVALID;
            } else if (cTrans == '.' && m_iNumberStatus == NUMBER_PORINT && !priceWith00) {
                m_jPor.setText(m_jPor.getText() + ".");
                m_iNumberStatus = NUMBER_PORDEC;
            } else if (cTrans == '.' && m_iNumberStatus == NUMBER_PORINT) {
                m_jPor.setText(m_jPor.getText() + "00");
                m_iNumberStatus = NUMBERVALID;

            } else if ((cTrans == '0') && (m_iNumberStatus == NUMBER_PORZERODEC || m_iNumberStatus == NUMBER_PORDEC)) {
                // Un numero decimal
                m_jPor.setText(m_jPor.getText() + cTrans);
            } else if ((cTrans == '1' || cTrans == '2' || cTrans == '3' || cTrans == '4' || cTrans == '5' || cTrans == '6' || cTrans == '7' || cTrans == '8' || cTrans == '9') 
                    && (m_iNumberStatus == NUMBER_PORZERODEC || m_iNumberStatus == NUMBER_PORDEC)) {
                // Un numero decimal
                m_jPor.setText(m_jPor.getText() + cTrans);
                m_iNumberStatus = NUMBER_PORDEC;
                m_iNumberStatusPor = NUMBERVALID;

            
            } else if (cTrans == '\u00a7'   
                    && m_iNumberStatusInput == NUMBERVALID && m_iNumberStatusPor == NUMBERZERO) {
                // Scale button pressed and a number typed as a price
                if (m_App.getDeviceScale().existsScale() && m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                    try {
                        Double value = m_App.getDeviceScale().readWeight();
                        if (value != null) {
                            ProductInfoExt product = getInputProduct();
                            addTicketLine(product, value, product.getPriceSell());
                        }
                    } catch (ScaleException e) {
                        Toolkit.getDefaultToolkit().beep();
                        new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.noweight"), e).show(this);           
                        stateToZero(); 
                    }
                } else {
                    // No existe la balanza;
                    Toolkit.getDefaultToolkit().beep();
                }
            } else if (cTrans == '\u00a7' 
                    && m_iNumberStatusInput == NUMBERZERO && m_iNumberStatusPor == NUMBERZERO) {
                // Scale button pressed and no number typed.
                int i = m_ticketlines.getSelectedIndex();
                if (i < 0){
                    Toolkit.getDefaultToolkit().beep();
                } else if (m_App.getDeviceScale().existsScale()) {
                    try {
                        Double value = m_App.getDeviceScale().readWeight();
                        if (value != null) {
                            TicketLineInfo newline = new TicketLineInfo(m_oTicket.getLine(i));
                            newline.setMultiply(value);
                            newline.setPrice(Math.abs(newline.getPrice()));
                            paintTicketLine(i, newline);
                        }
                    } catch (ScaleException e) {
                        // Error de pesada.
                        Toolkit.getDefaultToolkit().beep();
                        new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.noweight"), e).show(this);           
                        stateToZero(); 
                    }
                } else {
                    // No existe la balanza;
                    Toolkit.getDefaultToolkit().beep();
                }      
                
            // Add one product more to the selected line
            } else if (cTrans == '+' 
                    && m_iNumberStatusInput == NUMBERZERO && m_iNumberStatusPor == NUMBERZERO) {
                int i = m_ticketlines.getSelectedIndex();
                if (i < 0){
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    TicketLineInfo newline = new TicketLineInfo(m_oTicket.getLine(i));
                    //If it's a refund + button means one unit less
                    if (m_oTicket.getTicketType() == TicketInfo.RECEIPT_REFUND){
                        newline.setMultiply(newline.getMultiply() - 1.0);
                        paintTicketLine(i, newline);                   
                    }
                    else {
                        // add one unit to the selected line
                        newline.setMultiply(newline.getMultiply() + 1.0);
                        paintTicketLine(i, newline); 
                    }
                }

            // Delete one product of the selected line
            } else if (cTrans == '-' 
                    && m_iNumberStatusInput == NUMBERZERO && m_iNumberStatusPor == NUMBERZERO
                    && m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                
                int i = m_ticketlines.getSelectedIndex();
                if (i < 0){
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    TicketLineInfo newline = new TicketLineInfo(m_oTicket.getLine(i));
                    //If it's a refund - button means one unit more
                    if (m_oTicket.getTicketType() == TicketInfo.RECEIPT_REFUND){
                        newline.setMultiply(newline.getMultiply() + 1.0);
                        if (newline.getMultiply() >= 0) {
                            removeTicketLine(i);
                        } else {
                            paintTicketLine(i, newline);
                        }
                    } else {
                        // substract one unit to the selected line
                        newline.setMultiply(newline.getMultiply() - 1.0);
                        if (newline.getMultiply() <= 0.0) {                   
                            removeTicketLine(i); // elimino la linea
                        } else {
                            paintTicketLine(i, newline);                   
                        }
                    }
                }

            // Set n products to the selected line
            } else if (cTrans == '+' 
                    && m_iNumberStatusInput == NUMBERZERO && m_iNumberStatusPor == NUMBERVALID) {
                int i = m_ticketlines.getSelectedIndex();
                if (i < 0){
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    double dPor = getPorValue();
                    TicketLineInfo newline = new TicketLineInfo(m_oTicket.getLine(i)); 
                    if (m_oTicket.getTicketType() == TicketInfo.RECEIPT_REFUND) {
                        newline.setMultiply(-dPor);
                        newline.setPrice(Math.abs(newline.getPrice()));
                        paintTicketLine(i, newline);                
                    } else {
                        newline.setMultiply(dPor);
                        newline.setPrice(Math.abs(newline.getPrice()));
                        paintTicketLine(i, newline);
                    }
                }

            // Set n negative products to the selected line
            } else if (cTrans == '-' 
                    && m_iNumberStatusInput == NUMBERZERO && m_iNumberStatusPor == NUMBERVALID
                    && m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                
                int i = m_ticketlines.getSelectedIndex();
                if (i < 0){
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    double dPor = getPorValue();
                    TicketLineInfo newline = new TicketLineInfo(m_oTicket.getLine(i));
                    if (m_oTicket.getTicketType() == TicketInfo.RECEIPT_NORMAL) {
                        newline.setMultiply(dPor);
                        newline.setPrice(-Math.abs(newline.getPrice()));
                        paintTicketLine(i, newline);
                    }           
                }

            // Anadimos 1 producto
            } else if (cTrans == '+' 
                    && m_iNumberStatusInput == NUMBERVALID && m_iNumberStatusPor == NUMBERZERO
                    && m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                ProductInfoExt product = getInputProduct();
                addTicketLine(product, 1.0, product.getPriceSell());
// JG May 2014 - Allow line free entry amendment
                m_jEditLine.doClick();
            // Anadimos 1 producto con precio negativo
            } else if (cTrans == '-' 
                    && m_iNumberStatusInput == NUMBERVALID && m_iNumberStatusPor == NUMBERZERO
                    && m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                ProductInfoExt product = getInputProduct();
                addTicketLine(product, 1.0, -product.getPriceSell());

            // Anadimos n productos
            } else if (cTrans == '+' 
                    && m_iNumberStatusInput == NUMBERVALID && m_iNumberStatusPor == NUMBERVALID
                    && m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                ProductInfoExt product = getInputProduct();
                addTicketLine(product, getPorValue(), product.getPriceSell());

            // Anadimos n productos con precio negativo ?
            } else if (cTrans == '-' 
                    && m_iNumberStatusInput == NUMBERVALID && m_iNumberStatusPor == NUMBERVALID
                    && m_App.getAppUserView().getUser().hasPermission("sales.EditLines")) {
                ProductInfoExt product = getInputProduct();
                addTicketLine(product, getPorValue(), -product.getPriceSell());

            // Totals() Igual;
            } else if (cTrans == ' ' || cTrans == '=') {
                if (m_oTicket.getLinesCount() > 0) {
                    
                    if (closeTicket(m_oTicket, m_oTicketExt)) {
                        // Ends edition of current receipt
                        m_ticketsbag.deleteTicket();  
                        
//added by JDL Autologoff after sales            
            String autoLogoff = (m_App.getProperties().getProperty("till.autoLogoff"));
            if (autoLogoff != null){               
                if (autoLogoff.equals("true")){                    
                   if ("restaurant".equals(m_App.getProperties().getProperty("machine.ticketsbag"))&&
                           ("true".equals(m_App.getProperties().getProperty("till.autoLogoffrestaurant")))){
                        deactivate();
                        setActiveTicket(null, null); 
                   }else {
                        ((JRootApp)m_App).closeAppView();   
                         }    
                }                   
            };                       
                    } else {
                        // repaint current ticket
                        refreshTicket();
                    }
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }
    
    private boolean closeTicket(TicketInfo ticket, Object ticketext) {
        if (listener  != null) {
            listener.stop();
        }
        boolean resultok = false;
        
        if (m_App.getAppUserView().getUser().hasPermission("sales.Total")) {  
// Check if we have a warranty to print                         
            warrantyCheck(ticket);

            try {
                // reset the payment info
                taxeslogic.calculateTaxes(ticket);
                if (ticket.getTotal()>=0.0){
                    ticket.resetPayments(); //Only reset if is sale
                }
                
                if (executeEvent(ticket, ticketext, "ticket.total") == null) {
                    if (listener  != null) {
                        listener.stop();
                    }
                    // Muestro el total
                    printTicket("Printer.TicketTotal", ticket, ticketext);
                    
                    
                    // Select the Payments information
                    JPaymentSelect paymentdialog = ticket.getTicketType() == TicketInfo.RECEIPT_NORMAL
                            ? paymentdialogreceipt
                            : paymentdialogrefund;
                    paymentdialog.setPrintSelected("true".equals(m_jbtnconfig.getProperty("printselected", "true")));

                    paymentdialog.setTransactionID(ticket.getTransactionID());

                    if (paymentdialog.showDialog(ticket.getTotal(), ticket.getCustomer())) {

                        // assign the payments selected and calculate taxes.         
                        ticket.setPayments(paymentdialog.getSelectedPayments());

                        // Asigno los valores definitivos del ticket...
                        ticket.setUser(m_App.getAppUserView().getUser().getUserInfo()); // El usuario que lo cobra
                        ticket.setActiveCash(m_App.getActiveCashIndex());
                        ticket.setDate(new Date()); // Le pongo la fecha de cobro

                        if (executeEvent(ticket, ticketext, "ticket.save") == null) {
                            // Save the receipt and assign a receipt number
                            try {
                                dlSales.saveTicket(ticket, m_App.getInventoryLocation());
                            } catch (BasicException eData) {
                                MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.nosaveticket"), eData);
                                msg.show(this);
                            }

                            executeEvent(ticket, ticketext, "ticket.close", new ScriptArg("print", paymentdialog.isPrintSelected()));

                            // Print receipt. 
// John L July 2014 previous || warranty print reinstated
                            printTicket(paymentdialog.isPrintSelected() || warrantyPrint
                                    ? "Printer.Ticket"
//                                    ? ticketPrintType
                                    : "Printer.Ticket2", ticket, ticketext);  
                            
//                            if (m_oTicket.getLoyaltyCardNumber() != null){
// add points to the card
//                                System.out.println("Point added to card = " + ticket.getTotal()/100);
// reset card pointer                                
                              //  loyaltyCardNumber = null;
                                
//                            }
                            resultok = true;
// if restaurant clear any customer name in table for this table once receipt is printed
//                            if ("restaurant".equals(m_App.getProperties().getProperty("machine.ticketsbag"))&&  m_oTicket.getTicketType() !=1) {  
                            if ("restaurant".equals(m_App.getProperties().getProperty("machine.ticketsbag"))&&  !ticket.getOldTicket()) { 
                                restDB.clearCustomerNameInTable(ticketext.toString());
                                restDB.clearWaiterNameInTable(ticketext.toString());
                                restDB.clearTicketIdInTable(ticketext.toString());
//                                restDB.
                            }                                
                        }
                    }
                }
            } catch (TaxesException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotcalculatetaxes"));
                msg.show(this);
                resultok = false;
            }
            
            // reset the payment info
            m_oTicket.resetTaxes();
            m_oTicket.resetPayments();
        }
        
        // cancelled the ticket.total script
        // or canceled the payment dialog
        // or canceled the ticket.close script
   
        
        return resultok;        
    }
       
// John L July 2014
//    private void warrantyCheck(TicketInfo ticket){
    private boolean warrantyCheck(TicketInfo ticket) {    
        warrantyPrint=false;
        int lines=0;
        while (lines < ticket.getLinesCount()) {             
            if (!warrantyPrint){
                warrantyPrint = ticket.getLine(lines).isProductWarranty();
                return (true);
            }
            lines++;
            }
        return false;
    }
   
    /**
     *
     * @param pTicket
     * @return
     */
    public String getPickupString(TicketInfo pTicket){ 
    if (pTicket == null){    
//        return("");
                return("0");
    }
     String tmpPickupId=Integer.toString(pTicket.getPickupId());
     String pickupSize =(m_App.getProperties().getProperty("till.pickupsize"));    
if (pickupSize!=null && (Integer.parseInt(pickupSize) >= tmpPickupId.length())){        
    while (tmpPickupId.length()< (Integer.parseInt(pickupSize))){
                tmpPickupId="0"+tmpPickupId;}
    } 
       return (tmpPickupId);      
    }
    
    
    private void printTicket(String sresourcename, TicketInfo ticket, Object ticketext) {

        String sresource = dlSystem.getResourceAsXML(sresourcename);
        if (sresource == null) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotprintticket"));
            msg.show(JPanelTicket.this);
        } else {
            
// if this is ticket does not have a pickup code assign on now            
            if (ticket.getPickupId()== 0){
            try{
            ticket.setPickupId(dlSales.getNextPickupIndex());
            }catch (BasicException e){
            ticket.setPickupId(0);
            }
          }
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
// JG 19 Feb 14 unnecessary boolean parse - if (Boolean.valueOf(m_App.getProperties().getProperty("receipt.newlayout")).booleanValue()){
                if (Boolean.parseBoolean(m_App.getProperties().getProperty("receipt.newlayout"))){
                        script.put("taxes",ticket.getTaxLines());                       
                } else {
                script.put("taxes", taxcollection);            
                }                
                script.put("taxeslogic", taxeslogic);
                script.put("ticket", ticket);
                script.put("place", ticketext);
                script.put("warranty", warrantyPrint);
                script.put("pickupid",getPickupString(ticket));

// JG Aug 2014
                refreshTicket();                

                m_TTP.printTicket(script.eval(sresource).toString(), ticket);

// JG May 2013 replaced with Multicatch            
            } catch (    ScriptException | TicketPrinterException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotprintticket"), e);
                msg.show(JPanelTicket.this);
            }
        }
    }
    
    private void printReport(String resourcefile, TicketInfo ticket, Object ticketext) {
        
        try {     
         
            JasperReport jr;
           
            InputStream in = getClass().getResourceAsStream(resourcefile + ".ser");
            if (in == null) {      
                // read and compile the report
                JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream(resourcefile + ".jrxml"));            
                jr = JasperCompileManager.compileReport(jd);    
            } else {
                try (ObjectInputStream oin = new ObjectInputStream(in)) {
                    jr = (JasperReport) oin.readObject();
                }
                }
           
            // Construyo el mapa de los parametros.
            Map reportparams = new HashMap();
            // reportparams.put("ARG", params);
            try {
                reportparams.put("REPORT_RESOURCE_BUNDLE", ResourceBundle.getBundle(resourcefile + ".properties"));
            } catch (MissingResourceException e) {
            }
            reportparams.put("TAXESLOGIC", taxeslogic); 
            
            Map reportfields = new HashMap();
            reportfields.put("TICKET", ticket);
            reportfields.put("PLACE", ticketext);

            JasperPrint jp = JasperFillManager.fillReport(jr, reportparams, new JRMapArrayDataSource(new Object[] { reportfields } ));
            
            PrintService service = ReportUtils.getPrintService(m_App.getProperties().getProperty("machine.printername"));
            
            JRPrinterAWT300.printPages(jp, 0, jp.getPages().size() - 1, service);
            
// JG May 2013 replaced with Multicatch
        } catch (JRException | IOException | ClassNotFoundException e) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotloadreport"), e);
            msg.show(this);
        }               
    }

    private void visorTicketLine(TicketLineInfo oLine){
        if (oLine == null) { 
             m_App.getDeviceTicket().getDeviceDisplay().clearVisor();
        } else {                 
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                script.put("ticketline", oLine);
                m_TTP.printTicket(script.eval(dlSystem.getResourceAsXML("Printer.TicketLine")).toString());
// JG May 2013 replaced with Multicatch
            } catch (    ScriptException | TicketPrinterException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotprintline"), e);
                msg.show(JPanelTicket.this);
            }
        } 
    }    
    
    
    private Object evalScript(ScriptObject scr, String resource, ScriptArg... args) {
        
        // resource here is guaranteed to be not null
         try {
            scr.setSelectedIndex(m_ticketlines.getSelectedIndex());
            return scr.evalScript(dlSystem.getResourceAsXML(resource), args);                
        } catch (ScriptException e) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotexecute"), e);
            msg.show(this);
            return msg;
        } 
    }
        
    /**
     *
     * @param resource
     * @param args
     */
    public void evalScriptAndRefresh(String resource, ScriptArg... args) {

        if (resource == null) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotexecute"));
            msg.show(this);            
        } else {
            ScriptObject scr = new ScriptObject(m_oTicket, m_oTicketExt);
            scr.setSelectedIndex(m_ticketlines.getSelectedIndex());
            evalScript(scr, resource, args);   
            refreshTicket();
            setSelectedIndex(scr.getSelectedIndex());
        }
    }

    /**
     *
     * @param resource
     */
    public void printTicket(String resource) {
        printTicket(resource, m_oTicket, m_oTicketExt);
    }
    
    private Object executeEventAndRefresh(String eventkey, ScriptArg ... args) {
        
        String resource = m_jbtnconfig.getEvent(eventkey);
        if (resource == null) {
            return null;
        } else {
            ScriptObject scr = new ScriptObject(m_oTicket, m_oTicketExt);
            scr.setSelectedIndex(m_ticketlines.getSelectedIndex());
            Object result = evalScript(scr, resource, args);   
            refreshTicket();
            setSelectedIndex(scr.getSelectedIndex());
            return result;
        }
    }
   
    
    private Object executeEvent(TicketInfo ticket, Object ticketext, String eventkey, ScriptArg ... args) {
        
        String resource = m_jbtnconfig.getEvent(eventkey);
        if (resource == null) {
            return null;
        } else {
            ScriptObject scr = new ScriptObject(ticket, ticketext);
            return evalScript(scr, resource, args);
        }
    }
    
    /**
     *
     * @param sresourcename
     * @return
     */
    public String getResourceAsXML(String sresourcename) {
        return dlSystem.getResourceAsXML(sresourcename);
    }

    /**
     *
     * @param sresourcename
     * @return
     */
    public BufferedImage getResourceAsImage(String sresourcename) {
        return dlSystem.getResourceAsImage(sresourcename);
    }
    
    private void setSelectedIndex(int i) {
        
        if (i >= 0 && i < m_oTicket.getLinesCount()) {
            m_ticketlines.setSelectedIndex(i);
        } else if (m_oTicket.getLinesCount() > 0) {
            m_ticketlines.setSelectedIndex(m_oTicket.getLinesCount() - 1);
        }    
    }
     
    /**
     *
     */
    public static class ScriptArg {
        private final String key;
        private final Object value;
        
        /**
         *
         * @param key
         * @param value
         */
        public ScriptArg(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        /**
         *
         * @return
         */
        public String getKey() {
            return key;
        }

        /**
         *
         * @return
         */
        public Object getValue() {
            return value;
        }
    }

    
/* Added JDL 13.04.13 routine
 * routine to set the amount appearance to show '.'
 */ 
    private String setTempjPrice(String jPrice){
        jPrice = jPrice.replace(".","");
// remove all leading zeros from the string        
        long tempL=Long.parseLong(jPrice);
        jPrice = Long.toString(tempL);
        
        while (jPrice.length()<3){
            jPrice="0"+jPrice;                        
        }
        return (jPrice.length()<= 2)? jPrice : (new StringBuffer(jPrice).insert(jPrice.length()-2,".").toString());
    }
    
    /**
     *
     */
    public class ScriptObject {
        
        private final TicketInfo ticket;
        private final Object ticketext;
        
        private int selectedindex;
        
        private ScriptObject(TicketInfo ticket, Object ticketext) {
            this.ticket = ticket;
            this.ticketext = ticketext;
        }
        
        /**
         *
         * @return
         */
        public double getInputValue() {
            if (m_iNumberStatusInput == NUMBERVALID && m_iNumberStatusPor == NUMBERZERO) {
                return JPanelTicket.this.getInputValue();
            } else {
                return 0.0;
            }
        }
        
        /**
         *
         * @return
         */
        public int getSelectedIndex() {
            return selectedindex;
        }
        
        /**
         *
         * @param i
         */
        public void setSelectedIndex(int i) {
            selectedindex = i;
        }

        /**
         *
         * @param resourcefile
         */
        public void printReport(String resourcefile) {
            JPanelTicket.this.printReport(resourcefile, ticket, ticketext);
        }
        
        /**
         *
         * @param sresourcename
         */
        public void printTicket(String sresourcename) {
            JPanelTicket.this.printTicket(sresourcename, ticket, ticketext);   
        }

        /**
         *
         * @param code
         * @param args
         * @return Script object and value
         * @throws ScriptException
         */
        public Object evalScript(String code, ScriptArg... args) throws ScriptException {
            
        ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.BEANSHELL);            

// Mod JG Sept 2011 - Used inside scripts i.e.: Stock Checking
        String sDBUser = m_App.getProperties().getProperty("db.user");
        String sDBPassword = m_App.getProperties().getProperty("db.password");
        
        if (sDBUser != null && sDBPassword != null && sDBPassword.startsWith("crypt:")) {
            AltEncrypter cypher = new AltEncrypter("cypherkey" + sDBUser);
            sDBPassword = cypher.decrypt(sDBPassword.substring(6));
        } 
            script.put("hostname", m_App.getProperties().getProperty("machine.hostname"));
            script.put("dbURL", m_App.getProperties().getProperty("db.URL")); 
            script.put("dbUser", sDBUser);
            script.put("dbPassword", sDBPassword);
// End mod
            
            script.put("ticket", ticket);
            script.put("place", ticketext);
            script.put("taxes", taxcollection);
            script.put("taxeslogic", taxeslogic);             
            script.put("user", m_App.getAppUserView().getUser());
            script.put("sales", this);
            script.put("taxesinc",m_jaddtax.isSelected());
            script.put("warranty",warrantyPrint);
            script.put("pickupid",getPickupString(ticket));
       

            // more arguments
            for(ScriptArg arg : args) {
                script.put(arg.getKey(), arg.getValue());
//                System.out.println(arg.getKey());
//                System.out.println(arg.getValue());
            }             

            return script.eval(code);
        }            
    }
    

 
    
    
/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        m_jPanContainer = new javax.swing.JPanel();
        m_jOptions = new javax.swing.JPanel();
        m_jButtons = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnCustomer = new javax.swing.JButton();
        btnSplit = new javax.swing.JButton();
        m_jPanelScripts = new javax.swing.JPanel();
        m_jButtonsExt = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        m_jbtnScale = new javax.swing.JButton();
        jbtnMooring = new javax.swing.JButton();
        j_btnKitchenPrt = new javax.swing.JButton();
        m_jPanelBag = new javax.swing.JPanel();
        m_jPanTicket = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        m_jUp = new javax.swing.JButton();
        m_jDown = new javax.swing.JButton();
        m_jDelete = new javax.swing.JButton();
        m_jList = new javax.swing.JButton();
        m_jEditLine = new javax.swing.JButton();
        jEditAttributes = new javax.swing.JButton();
        m_jPanelCentral = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        m_jTicketId = new javax.swing.JLabel();
        m_jPanTotals = new javax.swing.JPanel();
        m_jLblTotalEuros3 = new javax.swing.JLabel();
        m_jLblTotalEuros2 = new javax.swing.JLabel();
        m_jLblTotalEuros1 = new javax.swing.JLabel();
        m_jSubtotalEuros = new javax.swing.JLabel();
        m_jTaxesEuros = new javax.swing.JLabel();
        m_jTotalEuros = new javax.swing.JLabel();
        m_jContEntries = new javax.swing.JPanel();
        m_jPanEntries = new javax.swing.JPanel();
        m_jNumberKeys = new com.openbravo.beans.JNumberKeys();
        jPanel9 = new javax.swing.JPanel();
        m_jPrice = new javax.swing.JLabel();
        m_jPor = new javax.swing.JLabel();
        m_jEnter = new javax.swing.JButton();
        m_jTax = new javax.swing.JComboBox();
        m_jaddtax = new javax.swing.JToggleButton();
        m_jKeyFactory = new javax.swing.JTextField();
        catcontainer = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 204, 153));
        setLayout(new java.awt.CardLayout());

        m_jPanContainer.setLayout(new java.awt.BorderLayout());

        m_jOptions.setLayout(new java.awt.BorderLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/customer_add_sml.png"))); // NOI18N
        jButton1.setToolTipText("Go to Customers");
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.setMargin(new java.awt.Insets(0, 4, 0, 4));
        jButton1.setMaximumSize(new java.awt.Dimension(50, 40));
        jButton1.setMinimumSize(new java.awt.Dimension(50, 40));
        jButton1.setPreferredSize(new java.awt.Dimension(50, 40));
        jButton1.setRequestFocusEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/customer_sml.png"))); // NOI18N
        btnCustomer.setToolTipText("Find Customers");
        btnCustomer.setFocusPainted(false);
        btnCustomer.setFocusable(false);
        btnCustomer.setMargin(new java.awt.Insets(0, 4, 0, 4));
        btnCustomer.setMaximumSize(new java.awt.Dimension(50, 40));
        btnCustomer.setMinimumSize(new java.awt.Dimension(50, 40));
        btnCustomer.setPreferredSize(new java.awt.Dimension(50, 40));
        btnCustomer.setRequestFocusEnabled(false);
        btnCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerActionPerformed(evt);
            }
        });

        btnSplit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/sale_split_sml.png"))); // NOI18N
        btnSplit.setToolTipText("Split Sale");
        btnSplit.setFocusPainted(false);
        btnSplit.setFocusable(false);
        btnSplit.setMargin(new java.awt.Insets(0, 4, 0, 4));
        btnSplit.setMaximumSize(new java.awt.Dimension(50, 40));
        btnSplit.setMinimumSize(new java.awt.Dimension(50, 40));
        btnSplit.setPreferredSize(new java.awt.Dimension(50, 40));
        btnSplit.setRequestFocusEnabled(false);
        btnSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSplitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout m_jButtonsLayout = new javax.swing.GroupLayout(m_jButtons);
        m_jButtons.setLayout(m_jButtonsLayout);
        m_jButtonsLayout.setHorizontalGroup(
            m_jButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(m_jButtonsLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSplit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        m_jButtonsLayout.setVerticalGroup(
            m_jButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(m_jButtonsLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(m_jButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSplit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        m_jOptions.add(m_jButtons, java.awt.BorderLayout.LINE_START);

        m_jPanelScripts.setLayout(new java.awt.BorderLayout());

        m_jButtonsExt.setLayout(new javax.swing.BoxLayout(m_jButtonsExt, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setMinimumSize(new java.awt.Dimension(235, 50));

        m_jbtnScale.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        m_jbtnScale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/scale.png"))); // NOI18N
        m_jbtnScale.setText(AppLocal.getIntString("button.scale")); // NOI18N
        m_jbtnScale.setToolTipText("Scale");
        m_jbtnScale.setFocusPainted(false);
        m_jbtnScale.setFocusable(false);
        m_jbtnScale.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jbtnScale.setMaximumSize(new java.awt.Dimension(85, 44));
        m_jbtnScale.setMinimumSize(new java.awt.Dimension(85, 44));
        m_jbtnScale.setPreferredSize(new java.awt.Dimension(85, 40));
        m_jbtnScale.setRequestFocusEnabled(false);
        m_jbtnScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtnScaleActionPerformed(evt);
            }
        });
        jPanel1.add(m_jbtnScale);

        jbtnMooring.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jbtnMooring.setText(bundle.getString("button.moorings")); // NOI18N
        jbtnMooring.setMargin(new java.awt.Insets(8, 14, 8, 14));
        jbtnMooring.setMaximumSize(new java.awt.Dimension(80, 40));
        jbtnMooring.setMinimumSize(new java.awt.Dimension(80, 40));
        jbtnMooring.setPreferredSize(new java.awt.Dimension(80, 40));
        jbtnMooring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnMooringActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnMooring);

        j_btnKitchenPrt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/printer24.png"))); // NOI18N
        j_btnKitchenPrt.setText(bundle.getString("button.sendorder")); // NOI18N
        j_btnKitchenPrt.setToolTipText("Send to Kichen Printer");
        j_btnKitchenPrt.setMargin(new java.awt.Insets(0, 4, 0, 4));
        j_btnKitchenPrt.setMaximumSize(new java.awt.Dimension(50, 40));
        j_btnKitchenPrt.setMinimumSize(new java.awt.Dimension(50, 40));
        j_btnKitchenPrt.setPreferredSize(new java.awt.Dimension(50, 40));
        j_btnKitchenPrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                j_btnKitchenPrtActionPerformed(evt);
            }
        });
        jPanel1.add(j_btnKitchenPrt);

        m_jButtonsExt.add(jPanel1);

        m_jPanelScripts.add(m_jButtonsExt, java.awt.BorderLayout.LINE_END);

        m_jOptions.add(m_jPanelScripts, java.awt.BorderLayout.LINE_END);

        m_jPanelBag.setPreferredSize(new java.awt.Dimension(0, 50));
        m_jPanelBag.setLayout(new java.awt.BorderLayout());
        m_jOptions.add(m_jPanelBag, java.awt.BorderLayout.CENTER);

        m_jPanContainer.add(m_jOptions, java.awt.BorderLayout.NORTH);

        m_jPanTicket.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        m_jPanTicket.setLayout(new java.awt.BorderLayout());

        jPanel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(60, 200));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        jPanel2.setLayout(new java.awt.GridLayout(0, 1, 5, 5));

        m_jUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/1uparrow.png"))); // NOI18N
        m_jUp.setToolTipText("Scroll Up a Line");
        m_jUp.setFocusPainted(false);
        m_jUp.setFocusable(false);
        m_jUp.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jUp.setMaximumSize(new java.awt.Dimension(42, 36));
        m_jUp.setMinimumSize(new java.awt.Dimension(42, 36));
        m_jUp.setPreferredSize(new java.awt.Dimension(50, 36));
        m_jUp.setRequestFocusEnabled(false);
        m_jUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jUpActionPerformed(evt);
            }
        });
        jPanel2.add(m_jUp);

        m_jDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/1downarrow.png"))); // NOI18N
        m_jDown.setToolTipText("Scroll Down a Line");
        m_jDown.setFocusPainted(false);
        m_jDown.setFocusable(false);
        m_jDown.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jDown.setMaximumSize(new java.awt.Dimension(42, 36));
        m_jDown.setMinimumSize(new java.awt.Dimension(42, 36));
        m_jDown.setPreferredSize(new java.awt.Dimension(50, 36));
        m_jDown.setRequestFocusEnabled(false);
        m_jDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jDownActionPerformed(evt);
            }
        });
        jPanel2.add(m_jDown);

        m_jDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/editdelete.png"))); // NOI18N
        m_jDelete.setToolTipText("Remove Line");
        m_jDelete.setFocusPainted(false);
        m_jDelete.setFocusable(false);
        m_jDelete.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jDelete.setMaximumSize(new java.awt.Dimension(42, 36));
        m_jDelete.setMinimumSize(new java.awt.Dimension(42, 36));
        m_jDelete.setPreferredSize(new java.awt.Dimension(50, 36));
        m_jDelete.setRequestFocusEnabled(false);
        m_jDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jDeleteActionPerformed(evt);
            }
        });
        jPanel2.add(m_jDelete);

        m_jList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/search32.png"))); // NOI18N
        m_jList.setToolTipText("Product Search");
        m_jList.setFocusPainted(false);
        m_jList.setFocusable(false);
        m_jList.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jList.setMaximumSize(new java.awt.Dimension(42, 36));
        m_jList.setMinimumSize(new java.awt.Dimension(42, 36));
        m_jList.setPreferredSize(new java.awt.Dimension(50, 36));
        m_jList.setRequestFocusEnabled(false);
        m_jList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jListActionPerformed(evt);
            }
        });
        jPanel2.add(m_jList);

        m_jEditLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/sale_editline.png"))); // NOI18N
        m_jEditLine.setToolTipText("Edit Line");
        m_jEditLine.setFocusPainted(false);
        m_jEditLine.setFocusable(false);
        m_jEditLine.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jEditLine.setMaximumSize(new java.awt.Dimension(42, 36));
        m_jEditLine.setMinimumSize(new java.awt.Dimension(42, 36));
        m_jEditLine.setPreferredSize(new java.awt.Dimension(50, 36));
        m_jEditLine.setRequestFocusEnabled(false);
        m_jEditLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jEditLineActionPerformed(evt);
            }
        });
        jPanel2.add(m_jEditLine);

        jEditAttributes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/attributes.png"))); // NOI18N
        jEditAttributes.setToolTipText("Choose Attributes");
        jEditAttributes.setFocusPainted(false);
        jEditAttributes.setFocusable(false);
        jEditAttributes.setMargin(new java.awt.Insets(8, 14, 8, 14));
        jEditAttributes.setMaximumSize(new java.awt.Dimension(42, 36));
        jEditAttributes.setMinimumSize(new java.awt.Dimension(42, 36));
        jEditAttributes.setPreferredSize(new java.awt.Dimension(50, 36));
        jEditAttributes.setRequestFocusEnabled(false);
        jEditAttributes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEditAttributesActionPerformed(evt);
            }
        });
        jPanel2.add(jEditAttributes);

        jPanel5.add(jPanel2, java.awt.BorderLayout.NORTH);

        m_jPanTicket.add(jPanel5, java.awt.BorderLayout.LINE_END);

        m_jPanelCentral.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jPanelCentral.setPreferredSize(new java.awt.Dimension(450, 240));
        m_jPanelCentral.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        m_jTicketId.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jTicketId.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        m_jTicketId.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        m_jTicketId.setAutoscrolls(true);
        m_jTicketId.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        m_jTicketId.setOpaque(true);
        m_jTicketId.setPreferredSize(new java.awt.Dimension(300, 40));
        m_jTicketId.setRequestFocusEnabled(false);
        m_jTicketId.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel4.add(m_jTicketId, java.awt.BorderLayout.CENTER);

        m_jPanTotals.setPreferredSize(new java.awt.Dimension(375, 60));
        m_jPanTotals.setLayout(new java.awt.GridLayout(2, 3, 4, 0));

        m_jLblTotalEuros3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        m_jLblTotalEuros3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_jLblTotalEuros3.setLabelFor(m_jSubtotalEuros);
        m_jLblTotalEuros3.setText(AppLocal.getIntString("label.subtotalcash")); // NOI18N
        m_jPanTotals.add(m_jLblTotalEuros3);

        m_jLblTotalEuros2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        m_jLblTotalEuros2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_jLblTotalEuros2.setLabelFor(m_jSubtotalEuros);
        m_jLblTotalEuros2.setText(AppLocal.getIntString("label.taxcash")); // NOI18N
        m_jPanTotals.add(m_jLblTotalEuros2);

        m_jLblTotalEuros1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        m_jLblTotalEuros1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_jLblTotalEuros1.setLabelFor(m_jTotalEuros);
        m_jLblTotalEuros1.setText(AppLocal.getIntString("label.totalcash")); // NOI18N
        m_jPanTotals.add(m_jLblTotalEuros1);

        m_jSubtotalEuros.setBackground(m_jEditLine.getBackground());
        m_jSubtotalEuros.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        m_jSubtotalEuros.setForeground(m_jEditLine.getForeground());
        m_jSubtotalEuros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_jSubtotalEuros.setLabelFor(m_jSubtotalEuros);
        m_jSubtotalEuros.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        m_jSubtotalEuros.setMaximumSize(new java.awt.Dimension(125, 25));
        m_jSubtotalEuros.setMinimumSize(new java.awt.Dimension(80, 25));
        m_jSubtotalEuros.setOpaque(true);
        m_jSubtotalEuros.setPreferredSize(new java.awt.Dimension(80, 25));
        m_jSubtotalEuros.setRequestFocusEnabled(false);
        m_jPanTotals.add(m_jSubtotalEuros);

        m_jTaxesEuros.setBackground(m_jEditLine.getBackground());
        m_jTaxesEuros.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        m_jTaxesEuros.setForeground(m_jEditLine.getForeground());
        m_jTaxesEuros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_jTaxesEuros.setLabelFor(m_jTaxesEuros);
        m_jTaxesEuros.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        m_jTaxesEuros.setMaximumSize(new java.awt.Dimension(125, 25));
        m_jTaxesEuros.setMinimumSize(new java.awt.Dimension(80, 25));
        m_jTaxesEuros.setOpaque(true);
        m_jTaxesEuros.setPreferredSize(new java.awt.Dimension(80, 25));
        m_jTaxesEuros.setRequestFocusEnabled(false);
        m_jPanTotals.add(m_jTaxesEuros);

        m_jTotalEuros.setBackground(m_jEditLine.getBackground());
        m_jTotalEuros.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        m_jTotalEuros.setForeground(m_jEditLine.getForeground());
        m_jTotalEuros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        m_jTotalEuros.setLabelFor(m_jTotalEuros);
        m_jTotalEuros.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        m_jTotalEuros.setMaximumSize(new java.awt.Dimension(125, 25));
        m_jTotalEuros.setMinimumSize(new java.awt.Dimension(80, 25));
        m_jTotalEuros.setOpaque(true);
        m_jTotalEuros.setPreferredSize(new java.awt.Dimension(100, 25));
        m_jTotalEuros.setRequestFocusEnabled(false);
        m_jPanTotals.add(m_jTotalEuros);

        jPanel4.add(m_jPanTotals, java.awt.BorderLayout.LINE_END);

        m_jPanelCentral.add(jPanel4, java.awt.BorderLayout.SOUTH);

        m_jPanTicket.add(m_jPanelCentral, java.awt.BorderLayout.CENTER);

        m_jPanContainer.add(m_jPanTicket, java.awt.BorderLayout.CENTER);

        m_jContEntries.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jContEntries.setLayout(new java.awt.BorderLayout());

        m_jPanEntries.setLayout(new javax.swing.BoxLayout(m_jPanEntries, javax.swing.BoxLayout.Y_AXIS));

        m_jNumberKeys.setMinimumSize(new java.awt.Dimension(200, 200));
        m_jNumberKeys.setPreferredSize(new java.awt.Dimension(250, 250));
        m_jNumberKeys.addJNumberEventListener(new com.openbravo.beans.JNumberEventListener() {
            public void keyPerformed(com.openbravo.beans.JNumberEvent evt) {
                m_jNumberKeysKeyPerformed(evt);
            }
        });
        m_jPanEntries.add(m_jNumberKeys);

        jPanel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        m_jPrice.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jPrice.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jPrice.setOpaque(true);
        m_jPrice.setPreferredSize(new java.awt.Dimension(100, 25));
        m_jPrice.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel9.add(m_jPrice, gridBagConstraints);

        m_jPor.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jPor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jPor.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jPor.setOpaque(true);
        m_jPor.setPreferredSize(new java.awt.Dimension(22, 25));
        m_jPor.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel9.add(m_jPor, gridBagConstraints);

        m_jEnter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/barcode.png"))); // NOI18N
        m_jEnter.setToolTipText("Get Barcode");
        m_jEnter.setFocusPainted(false);
        m_jEnter.setFocusable(false);
        m_jEnter.setRequestFocusEnabled(false);
        m_jEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jEnterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel9.add(m_jEnter, gridBagConstraints);

        m_jTax.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        m_jTax.setFocusable(false);
        m_jTax.setPreferredSize(new java.awt.Dimension(28, 25));
        m_jTax.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        jPanel9.add(m_jTax, gridBagConstraints);

        m_jaddtax.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        m_jaddtax.setText("+");
        m_jaddtax.setFocusPainted(false);
        m_jaddtax.setFocusable(false);
        m_jaddtax.setPreferredSize(new java.awt.Dimension(40, 25));
        m_jaddtax.setRequestFocusEnabled(false);
        m_jaddtax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jaddtaxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel9.add(m_jaddtax, gridBagConstraints);

        m_jPanEntries.add(jPanel9);

        m_jKeyFactory.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        m_jKeyFactory.setForeground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        m_jKeyFactory.setBorder(null);
        m_jKeyFactory.setCaretColor(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        m_jKeyFactory.setPreferredSize(new java.awt.Dimension(1, 1));
        m_jKeyFactory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                m_jKeyFactoryKeyTyped(evt);
            }
        });
        m_jPanEntries.add(m_jKeyFactory);

        m_jContEntries.add(m_jPanEntries, java.awt.BorderLayout.NORTH);

        m_jPanContainer.add(m_jContEntries, java.awt.BorderLayout.LINE_END);

        catcontainer.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        catcontainer.setLayout(new java.awt.BorderLayout());
        m_jPanContainer.add(catcontainer, java.awt.BorderLayout.SOUTH);

        add(m_jPanContainer, "ticket");
    }// </editor-fold>//GEN-END:initComponents

    private void m_jbtnScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnScaleActionPerformed

        stateTransition('\u00a7');
        
    }//GEN-LAST:event_m_jbtnScaleActionPerformed

    private void m_jEditLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jEditLineActionPerformed
        int i = m_ticketlines.getSelectedIndex();
        if (i < 0){
            Toolkit.getDefaultToolkit().beep(); // no line selected
        } else {
            try {
                TicketLineInfo newline = JProductLineEdit.showMessage(this, m_App, m_oTicket.getLine(i));
                if (newline != null) {
                    // line has been modified
                    paintTicketLine(i, newline);
                }
            } catch (BasicException e) {
                new MessageInf(e).show(this);
            }
        }

    }//GEN-LAST:event_m_jEditLineActionPerformed

    private void m_jEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jEnterActionPerformed

        stateTransition('\n');

    }//GEN-LAST:event_m_jEnterActionPerformed

    private void m_jNumberKeysKeyPerformed(com.openbravo.beans.JNumberEvent evt) {//GEN-FIRST:event_m_jNumberKeysKeyPerformed

        stateTransition(evt.getKey());
    }//GEN-LAST:event_m_jNumberKeysKeyPerformed

    private void m_jKeyFactoryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_m_jKeyFactoryKeyTyped

        m_jKeyFactory.setText(null);
        stateTransition(evt.getKeyChar());

    }//GEN-LAST:event_m_jKeyFactoryKeyTyped

    private void m_jDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jDeleteActionPerformed

        int i = m_ticketlines.getSelectedIndex();
        if (i < 0){
            Toolkit.getDefaultToolkit().beep(); // No hay ninguna seleccionada
        } else {               
            removeTicketLine(i); // elimino la linea           
        }     
         
    }//GEN-LAST:event_m_jDeleteActionPerformed

    private void m_jUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jUpActionPerformed
        
        m_ticketlines.selectionUp();

    }//GEN-LAST:event_m_jUpActionPerformed

    private void m_jDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jDownActionPerformed

        m_ticketlines.selectionDown();

    }//GEN-LAST:event_m_jDownActionPerformed

    private void m_jListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jListActionPerformed

        ProductInfoExt prod = JProductFinder.showMessage(JPanelTicket.this, dlSales);    
        if (prod != null) {
            buttonTransition(prod);
        }
        
    }//GEN-LAST:event_m_jListActionPerformed

    private void btnCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerActionPerformed
       if (listener  != null) {
            listener.stop();
        }
       JCustomerFinder finder = JCustomerFinder.getCustomerFinder(this, dlCustomers);
                finder.search(m_oTicket.getCustomer());
                finder.setVisible(true);
        
        try {            
           if (finder.getSelectedCustomer() == null){
               m_oTicket.setCustomer(null);
           }else {
               m_oTicket.setCustomer(dlSales.loadCustomerExt(finder.getSelectedCustomer().getId()));
           if ("restaurant".equals(m_App.getProperties().getProperty("machine.ticketsbag"))) { 
// JG 30 Apr 14 Redundant String() to String() assignment
//               restDB.setCustomerNameInTableByTicketId (dlSales.loadCustomerExt(finder.getSelectedCustomer().getId()).toString(), m_oTicket.getId().toString());  
               restDB.setCustomerNameInTableByTicketId (dlSales.loadCustomerExt(finder.getSelectedCustomer().getId()).toString(), m_oTicket.getId());  
               }
           }

        } catch (BasicException e) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotfindcustomer"), e);
            msg.show(this);            
        }

        refreshTicket();     
}//GEN-LAST:event_btnCustomerActionPerformed

    private void btnSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSplitActionPerformed

        if (m_oTicket.getLinesCount() > 0) {
            ReceiptSplit splitdialog = ReceiptSplit.getDialog(this, dlSystem.getResourceAsXML("Ticket.Line"), dlSales, dlCustomers, taxeslogic);
            
            TicketInfo ticket1 = m_oTicket.copyTicket();
            TicketInfo ticket2 = new TicketInfo();
            ticket2.setCustomer(m_oTicket.getCustomer());
            
            if (splitdialog.showDialog(ticket1, ticket2, m_oTicketExt)) {
                if (closeTicket(ticket2, m_oTicketExt)) { // already checked  that number of lines > 0                            
                    setActiveTicket(ticket1, m_oTicketExt);// set result ticket
// maybe look at returning to table set up after splitting the bill

                    
                }
            }
        }
        
}//GEN-LAST:event_btnSplitActionPerformed

    private void jEditAttributesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEditAttributesActionPerformed
       if (listener  != null) {
            listener.stop();
        } 
        int i = m_ticketlines.getSelectedIndex();
        if (i < 0) {
            Toolkit.getDefaultToolkit().beep(); // no line selected
        } else {
            try {
                TicketLineInfo line = m_oTicket.getLine(i);
                JProductAttEdit attedit = JProductAttEdit.getAttributesEditor(this, m_App.getSession());
                attedit.editAttributes(line.getProductAttSetId(), line.getProductAttSetInstId());
                attedit.setVisible(true);
                if (attedit.isOK()) {
                    // The user pressed OK
                    line.setProductAttSetInstId(attedit.getAttributeSetInst());
                    line.setProductAttSetInstDesc(attedit.getAttributeSetInstDescription());
                    paintTicketLine(i, line);
                }
            } catch (BasicException ex) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotfindattributes"), ex);
                msg.show(this);
            }
        }
       if (listener  != null){           
       listener.restart(); 
       }
}//GEN-LAST:event_jEditAttributesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

// Show the custmer panel - this does deactivate
        {                                        
m_App.getAppUserView().showTask("com.openbravo.pos.customers.CustomersPanel");



}
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jbtnMooringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnMooringActionPerformed
// Display vessel selection box on screen if reply is good add to the ticket
       if (listener  != null) {
            listener.stop();
        } 
       JMooringDetails mooring = JMooringDetails.getMooringDetails(this, m_App.getSession());
       mooring.setVisible(true);
       if (mooring.isCreate()){ 
           if (((mooring.getVesselDays()>0 )) &&  ((mooring.getVesselSize()>1))){          
           try{
           ProductInfoExt vProduct = dlSales.getProductInfoByCode("BFeesDay1");
           vProduct.setName("Berth Fees 1st Day " + mooring.getVesselName());
           addTicketLine(vProduct, mooring.getVesselSize(), vProduct.getPriceSell());
           if (mooring.getVesselDays()>1){
           vProduct = dlSales.getProductInfoByCode("BFeesDay2");
           vProduct.setName("Additional Days " +(mooring.getVesselDays()-1));
           addTicketLine(vProduct, mooring.getVesselSize() * (mooring.getVesselDays()-1), vProduct.getPriceSell());               
           }
           if (mooring.getVesselPower()){
           vProduct = dlSales.getProductInfoByCode("PowerSupplied");
           addTicketLine(vProduct, mooring.getVesselDays(), vProduct.getPriceSell());               
           }         
           }catch (BasicException e){}
       }
       }
               refreshTicket(); 
    }//GEN-LAST:event_jbtnMooringActionPerformed

    private void j_btnKitchenPrtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_j_btnKitchenPrtActionPerformed
// John L - replace older SendOrder script
        
        String rScript = (dlSystem.getResourceAsText("script.SendOrder"));

            Interpreter i = new Interpreter(); 
        try {                       
            i.set("ticket", m_oTicket);  
            i.set("place",  m_oTicketExt);             
            i.set("user", m_App.getAppUserView().getUser());
            i.set("sales", this);
            i.set("pickupid", m_oTicket.getPickupId());
            Object result;
            result = i.eval(rScript);
        } catch (EvalError ex) {
            Logger.getLogger(JPanelTicket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
// Autologoff after sending to kitchen   
       
            String autoLogoff = (m_App.getProperties().getProperty("till.autoLogoff"));
            if (autoLogoff != null){
                if (autoLogoff.equals("true")){  
                    ((JRootApp)m_App).closeAppView();    
                      }
                }    

    }//GEN-LAST:event_j_btnKitchenPrtActionPerformed

    private void m_jaddtaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jaddtaxActionPerformed
        if ("+".equals(m_jaddtax.getText())){
            m_jaddtax.setText("-");
        }else{
            m_jaddtax.setText("+");
        }
    }//GEN-LAST:event_m_jaddtaxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomer;
    private javax.swing.JButton btnSplit;
    private javax.swing.JPanel catcontainer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jEditAttributes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JButton j_btnKitchenPrt;
    private javax.swing.JButton jbtnMooring;
    private javax.swing.JPanel m_jButtons;
    private javax.swing.JPanel m_jButtonsExt;
    private javax.swing.JPanel m_jContEntries;
    private javax.swing.JButton m_jDelete;
    private javax.swing.JButton m_jDown;
    private javax.swing.JButton m_jEditLine;
    private javax.swing.JButton m_jEnter;
    private javax.swing.JTextField m_jKeyFactory;
    private javax.swing.JLabel m_jLblTotalEuros1;
    private javax.swing.JLabel m_jLblTotalEuros2;
    private javax.swing.JLabel m_jLblTotalEuros3;
    private javax.swing.JButton m_jList;
    private com.openbravo.beans.JNumberKeys m_jNumberKeys;
    private javax.swing.JPanel m_jOptions;
    private javax.swing.JPanel m_jPanContainer;
    private javax.swing.JPanel m_jPanEntries;
    private javax.swing.JPanel m_jPanTicket;
    private javax.swing.JPanel m_jPanTotals;
    private javax.swing.JPanel m_jPanelBag;
    private javax.swing.JPanel m_jPanelCentral;
    private javax.swing.JPanel m_jPanelScripts;
    private javax.swing.JLabel m_jPor;
    private javax.swing.JLabel m_jPrice;
    private javax.swing.JLabel m_jSubtotalEuros;
    private javax.swing.JComboBox m_jTax;
    private javax.swing.JLabel m_jTaxesEuros;
    private javax.swing.JLabel m_jTicketId;
    private javax.swing.JLabel m_jTotalEuros;
    private javax.swing.JButton m_jUp;
    private javax.swing.JToggleButton m_jaddtax;
    private javax.swing.JButton m_jbtnScale;
    // End of variables declaration//GEN-END:variables

}
