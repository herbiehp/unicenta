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

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoMagcard;
import com.openbravo.pos.util.StringUtils;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author adrianromero
 */
public final class TicketInfo implements SerializableRead, Externalizable {

    private static final long serialVersionUID = 2765650092387265178L;

    /**
     *
     */
    public static final int RECEIPT_NORMAL = 0;

    /**
     *
     */
    public static final int RECEIPT_REFUND = 1;

    /**
     *
     */
    public static final int RECEIPT_PAYMENT = 2;

    /**
     *
     */
    public static final int RECEIPT_NOSALE = 3;

    private static final DateFormat m_dateformat = new SimpleDateFormat("hh:mm");

    private String m_sHost;
    private String m_sId;
    private int tickettype;
    private int m_iTicketId;
    private int m_iPickupId;
    private java.util.Date m_dDate;
    private Properties attributes;
    private UserInfo m_User;
    private CustomerInfoExt m_Customer;
    private String m_sActiveCash;
    private List<TicketLineInfo> m_aLines;
    private List<PaymentInfo> payments;
    private List<TicketTaxInfo> taxes;
    private final String m_sResponse;
    private String loyaltyCardNumber;
    private Boolean oldTicket;


 // JG July 2014 Ticket creator Host - for ticket print
    private static String Hostname;

    public static void setHostname(String name) {
        Hostname=name;
    }

    public static String getHostname() {
        return Hostname;
    }

    
    /** Creates new TicketModel */
    public TicketInfo() {
        m_sId = UUID.randomUUID().toString();
        tickettype = RECEIPT_NORMAL;
        m_iTicketId = 0; // incrementamos
        m_dDate = new Date();
        attributes = new Properties();
        m_User = null;
        m_Customer = null;
        m_sActiveCash = null;
        m_aLines = new ArrayList<>(); // JG June 2102 diamond inference

        payments = new ArrayList<>(); // JG June 2102 diamond inference
        taxes = null;
        m_sResponse = null;
        oldTicket=false;

    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        // esto es solo para serializar tickets que no estan en la bolsa de tickets pendientes
        out.writeObject(m_sId);
        out.writeInt(tickettype);
        out.writeInt(m_iTicketId);
        out.writeObject(m_Customer);
        out.writeObject(m_dDate);
        out.writeObject(attributes);
        out.writeObject(m_aLines);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        // esto es solo para serializar tickets que no estan en la bolsa de tickets pendientes
        m_sId = (String) in.readObject();
        tickettype = in.readInt();
        m_iTicketId = in.readInt();
        m_Customer = (CustomerInfoExt) in.readObject();
        m_dDate = (Date) in.readObject();
        attributes = (Properties) in.readObject();
        m_aLines = (List<TicketLineInfo>) in.readObject();
        m_User = null;
        m_sActiveCash = null;
        payments = new ArrayList<>(); // JG June 2102 diamond inference
        taxes = null;
    }

    /**
     *
     * @param dr
     * @throws BasicException
     */
    @Override
    public void readValues(DataRead dr) throws BasicException {
        m_sId = dr.getString(1);
        tickettype = dr.getInt(2);
        m_iTicketId = dr.getInt(3);
        m_dDate = dr.getTimestamp(4);
        m_sActiveCash = dr.getString(5);
        try {
            byte[] img = dr.getBytes(6);
            if (img != null) {
                attributes.loadFromXML(new ByteArrayInputStream(img));
            }
        } catch (IOException e) {
        }
        m_User = new UserInfo(dr.getString(7), dr.getString(8));
        m_Customer = new CustomerInfoExt(dr.getString(9));
        m_aLines = new ArrayList<>(); // JG June 2102 diamond inference

        payments = new ArrayList<>(); // JG June 2102 diamond inference
        taxes = null;
  
    }

    /**
     *
     * @return
     */
    public TicketInfo copyTicket() {
        TicketInfo t = new TicketInfo();

        t.tickettype = tickettype;
        t.m_iTicketId = m_iTicketId;
        t.m_dDate = m_dDate;
        t.m_sActiveCash = m_sActiveCash;
        t.attributes = (Properties) attributes.clone();
        t.m_User = m_User;
        t.m_Customer = m_Customer;

        t.m_aLines = new ArrayList<>(); // JG June 2102 diamond inference
        for (TicketLineInfo l : m_aLines) {
            t.m_aLines.add(l.copyTicketLine());
        }
        t.refreshLines();

        t.payments = new LinkedList<>(); // JG June 2102 diamond inference
        for (PaymentInfo p : payments) {
            t.payments.add(p.copyPayment());
        }
        t.oldTicket=oldTicket;
        // taxes are not copied, must be calculated again.

        return t;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return m_sId;
    }

    /**
     *
     * @return
     */
    public int getTicketType() {
        return tickettype;
    }

    /**
     *
     * @param tickettype
     */
    public void setTicketType(int tickettype) {
        this.tickettype = tickettype;
    }

    /**
     *
     * @return
     */
    public int getTicketId() {
        return m_iTicketId;
    }

    /**
     *
     * @param iTicketId
     */
    public void setTicketId(int iTicketId) {
        m_iTicketId = iTicketId;
    // refreshLines();
    }

    /**
     *
     * @param iTicketId
     */
    public void setPickupId(int iTicketId) {
        m_iPickupId = iTicketId;
    }

    /**
     *
     * @return
     */
    public int getPickupId() {
        return m_iPickupId;
    }
    
    /**
     *
     * @param info
     * @return
     */
    public String getName(Object info) {
// JG Aug 2014 - Add User info
        StringBuilder name = new StringBuilder();

        if (m_User != null) {
            name.append(m_User.getName());
            name.append(" - ");
        }
        
        if (info == null) {
            if (m_iTicketId == 0) {
                name.append("(").append(m_dateformat.format(m_dDate)).append(" ").append(Long.toString(m_dDate.getTime() % 1000)).append(")");
            } else {
                name.append(Integer.toString(m_iTicketId));
            }
        } else {
            name.append(info.toString());
          
            }
        if (getCustomerId() != null) {
            name.append(" - ");
            name.append(m_Customer.toString());
        }
        return name.toString();
    }
    
    /**
     *
     * @return
     */
    public String getName() {
        return getName(null);
    }

    /**
     *
     * @return
     */
    public java.util.Date getDate() {
        return m_dDate;
    }

    /**
     *
     * @param dDate
     */
    public void setDate(java.util.Date dDate) {
        m_dDate = dDate;
    }

    /**
     *
     * @return
     */
    public UserInfo getUser() {
        return m_User;
    }

    /**
     *
     * @param value
     */
    public void setUser(UserInfo value) {
        m_User = value;
    }

    /**
     *
     * @return
     */
    public CustomerInfoExt getCustomer() {
        return m_Customer;
    }

    /**
     *
     * @param value
     */
    public void setCustomer(CustomerInfoExt value) {
        m_Customer = value;
    }

    /**
     *
     * @return
     */
    public String getCustomerId() {
        if (m_Customer == null) {
            return null;
        } else {
            return m_Customer.getId();
        }
    }
    
    /**
     *
     * @return
     */
    public String getTransactionID(){
        return (getPayments().size()>0)
            ? ( getPayments().get(getPayments().size()-1) ).getTransactionID()
            : StringUtils.getCardNumber(); //random transaction ID
    }
    
    /**
     *
     * @return
     */
    public String getReturnMessage(){
        return ( (getPayments().get(getPayments().size()-1)) instanceof PaymentInfoMagcard )
            ? ((PaymentInfoMagcard)(getPayments().get(getPayments().size()-1))).getReturnMessage()
            : LocalRes.getIntString("button.ok");

    }

    /**
     *
     * @param value
     */
    public void setActiveCash(String value) {
        m_sActiveCash = value;
    }

    /**
     *
     * @return
     */
    public String getActiveCash() {
        return m_sActiveCash;
    }

    /**
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return attributes.getProperty(key);
    }

    /**
     *
     * @param key
     * @param defaultvalue
     * @return
     */
    public String getProperty(String key, String defaultvalue) {
        return attributes.getProperty(key, defaultvalue);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        attributes.setProperty(key, value);
    }

    /**
     *
     * @return
     */
    public Properties getProperties() {
        return attributes;
    }

    /**
     *
     * @param index
     * @return
     */
    public TicketLineInfo getLine(int index) {
        return m_aLines.get(index);
    }

    /**
     *
     * @param oLine
     */
    public void addLine(TicketLineInfo oLine) {

        oLine.setTicket(m_sId, m_aLines.size());
        m_aLines.add(oLine);
    }

    /**
     *
     * @param index
     * @param oLine
     */
    public void insertLine(int index, TicketLineInfo oLine) {
        m_aLines.add(index, oLine);
        refreshLines();
    }

    /**
     *
     * @param index
     * @param oLine
     */
    public void setLine(int index, TicketLineInfo oLine) {
        oLine.setTicket(m_sId, index);
        m_aLines.set(index, oLine);
    }

    /**
     *
     * @param index
     */
    public void removeLine(int index) {
        m_aLines.remove(index);
        refreshLines();
    }

    private void refreshLines() {
        for (int i = 0; i < m_aLines.size(); i++) {
            getLine(i).setTicket(m_sId, i);
        }
    }

    /**
     *
     * @return
     */
    public int getLinesCount() {
        return m_aLines.size();
    }
    
    /**
     *
     * @return
     */
    public double getArticlesCount() {
        double dArticles = 0.0;
        TicketLineInfo oLine;

        for (Iterator<TicketLineInfo> i = m_aLines.iterator(); i.hasNext();) {
            oLine = i.next();
            dArticles += oLine.getMultiply();
        }

        return dArticles;
    }

    /**
     *
     * @return
     */
    public double getSubTotal() {
        double sum = 0.0;
        for (TicketLineInfo line : m_aLines) {
            sum += line.getSubValue();
        }
        return sum;
    }

    /**
     *
     * @return
     */
    public double getTax() {

        double sum = 0.0;
        if (hasTaxesCalculated()) {
            for (TicketTaxInfo tax : taxes) {
                sum += tax.getTax(); // Taxes are already rounded...
            }
        } else {
            for (TicketLineInfo line : m_aLines) {
                sum += line.getTax();
            }
        }
        return sum;
    }

    /**
     *
     * @return
     */
    public double getTotal() {
        return getSubTotal() + getTax();
    }
    
    /**
     *
     * @return
     */
    public double getTotalPaid() {
        double sum = 0.0;
        for (PaymentInfo p : payments) {
            if (!"debtpaid".equals(p.getName())) {
                sum += p.getTotal();
            }
        }
        return sum;
          }

    /**
     *
     * @return
     */
    public double getTendered() {
        return getTotalPaid();
    }    

    /**
     *
     * @return
     */
    public List<TicketLineInfo> getLines() {
        return m_aLines;
    }

    /**
     *
     * @param l
     */
    public void setLines(List<TicketLineInfo> l) {
        m_aLines = l;
    }

    /**
     *
     * @return
     */
    public List<PaymentInfo> getPayments() {
        return payments;
    }

    /**
     *
     * @param l
     */
    public void setPayments(List<PaymentInfo> l) {
        payments = l;
    }

    /**
     *
     */
    public void resetPayments() {
        payments = new ArrayList<>(); // JG June 2102 diamond inference
    }

    /**
     *
     * @return
     */
    public List<TicketTaxInfo> getTaxes() {
        return taxes;
    }

    /**
     *
     * @return
     */
    public boolean hasTaxesCalculated() {
        return taxes != null;
    }

    /**
     *
     * @param l
     */
    public void setTaxes(List<TicketTaxInfo> l) {
        taxes = l;
    }

    /**
     *
     */
    public void resetTaxes() {
        taxes = null;
    }

    /**
     *
     * @param tax
     * @return
     */
    public TicketTaxInfo getTaxLine(TaxInfo tax) {

        for (TicketTaxInfo taxline : taxes) {
            if (tax.getId().equals(taxline.getTaxInfo().getId())) {
                return taxline;
            }
        }

        return new TicketTaxInfo(tax);
    }

    /**
     *
     * @return
     */
    public TicketTaxInfo[] getTaxLines() {

        Map<String, TicketTaxInfo> m = new HashMap<>(); // JG June 2102 diamond inference

        TicketLineInfo oLine;
        for (Iterator<TicketLineInfo> i = m_aLines.iterator(); i.hasNext();) {
            oLine = i.next();

            TicketTaxInfo t = m.get(oLine.getTaxInfo().getId());
            if (t == null) {
                t = new TicketTaxInfo(oLine.getTaxInfo());
                m.put(t.getTaxInfo().getId(), t);
            }
            t.add(oLine.getSubValue());
        }

        // return dSuma;       
        Collection<TicketTaxInfo> avalues = m.values();
        return avalues.toArray(new TicketTaxInfo[avalues.size()]);
    }

    /**
     *
     * @return
     */
    public String printId() {
// We need acces to the config file        
      AppConfig m_config =  new AppConfig(new File((System.getProperty("user.home")), AppLocal.APP_ID + ".properties"));        
      m_config.load();
      String receiptSize =(m_config.getProperty("till.receiptsize"));
      String receiptPrefix =(m_config.getProperty("till.receiptprefix"));
// we have finished with m_config so unload it      
      m_config =null;


        if (m_iTicketId > 0) {
            String tmpTicketId=Integer.toString(m_iTicketId);
            if (receiptSize == null || (Integer.parseInt(receiptSize) <= tmpTicketId.length())){
                if (receiptPrefix != null){
                    tmpTicketId=receiptPrefix+tmpTicketId;
                } 
                return tmpTicketId;
            }            
            while (tmpTicketId.length()<Integer.parseInt(receiptSize)){
                tmpTicketId="0"+tmpTicketId;
            }
            if (receiptPrefix != null){
                    tmpTicketId=receiptPrefix+tmpTicketId;
            }             
            return tmpTicketId;
        } else {
            return "";
        }
    }
    
    /**
     *
     * @return
     */
    public String printDate() {
        return Formats.TIMESTAMP.formatValue(m_dDate);
    }

    /**
     *
     * @return
     */
    public String printUser() {
        return m_User == null ? "" : m_User.getName();
        
    }
    /**
     * JG July 2014
     * @return
     */
    /**
     *
     * @return
     */
    public String getHost() {
        return m_sHost;
    }
    
    public String printHost() {
        return StringUtils.encodeXML(m_sHost);
    }
   
    
    
// Added JDL 28.05.13 for loyalty card functions

    /**
     *
     */
        public void clearCardNumber(){
        loyaltyCardNumber=null;
    }
    
    /**
     *
     * @param cardNumber
     */
    public void setLoyaltyCardNumber(String cardNumber){
        loyaltyCardNumber=cardNumber;
    }
    
    /**
     *
     * @return
     */
    public String getLoyaltyCardNumber(){
        return (loyaltyCardNumber);
    }
// Loyalty card functions added
         
    /**
     *
     * @return
     */
    public String printCustomer() {
        return m_Customer == null ? "" : m_Customer.getName();
    }

    /**
     *
     * @return
     */
    public String printArticlesCount() {
        return Formats.DOUBLE.formatValue(getArticlesCount());
    }

    /**
     *
     * @return
     */
    public String printSubTotal() {
        return Formats.CURRENCY.formatValue(getSubTotal());
    }

    /**
     *
     * @return
     */
    public String printTax() {
        return Formats.CURRENCY.formatValue(getTax());
    }

    /**
     *
     * @return
     */
    public String printTotal() {
        return Formats.CURRENCY.formatValue(getTotal());
    }

    /**
     *
     * @return
     */
    public String printTotalPaid() {
        return Formats.CURRENCY.formatValue(getTotalPaid());
    }

    /**
     *
     * @return
     */
    public String printTendered() {
        return Formats.CURRENCY.formatValue(getTendered());
    }

    /**
     *
     * @return
     */
    public String VoucherReturned(){
        return Formats.CURRENCY.formatValue(getTotalPaid()- getTotal());
    }
//Added JDl 03.07.13

    /**
     *
     * @return
     */
    public boolean getOldTicket() {
	return (oldTicket);
}

    /**
     *
     * @param otState
     */
    public void setOldTicket(Boolean otState) {
	oldTicket = otState;
}
    
}