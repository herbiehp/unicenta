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

package com.openbravo.pos.customers;

import com.openbravo.format.Formats;
import com.openbravo.pos.util.RoundUtils;
import java.util.Date;

/**
 *
 * @author adrianromero
 * @author JG uniCenta 
 */
public class CustomerInfoExt extends CustomerInfo {
    
    protected String taxcustomerid;
    protected String notes;
    protected boolean visible;
    protected String card;
    protected Double maxdebt;
    protected Date curdate;
    protected Double curdebt;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String phone;
    protected String phone2;
    protected String fax;
    protected String address;
    protected String address2;
    protected String postal;
    protected String city;
    protected String region;
    protected String country;
    protected String image;
    
    /** Creates a new instance of UserInfoBasic
     * @param id */
    public CustomerInfoExt(String id) {
        super(id);
    }

    /**
     *
     * @return customer's tax category
     */
    public String getTaxCustCategoryID() {
        return taxcustomerid;
    }
    public void setTaxCustomerID(String taxcustomerid) {
        this.taxcustomerid = taxcustomerid;
    }
    
    /**
     *
     * @return notes string
     */
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     *
     * @return Is visible Y/N? boolean
     */
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     *
     * @return customer's hashed member/loyalty card string
     */
    public String getCard() {
        return card;
    }
    public void setCard(String card) {
        this.card = card;
    }

    /**
     *
     * @return customer's maximum allowed debt value
     */
    public Double getMaxdebt() {
        return maxdebt;
    }
    public void setMaxdebt(Double maxdebt) {
        this.maxdebt = maxdebt;
    }
    public String printMaxDebt() {       
        return Formats.CURRENCY.formatValue(RoundUtils.getValue(getMaxdebt()));
    }
    
    /**
     *
     * @return customer's last ticket transaction date
     */
    public Date getCurdate() {
        return curdate;
    }
    public void setCurdate(Date curdate) {
        this.curdate = curdate;
    }
    public String printCurDate() {       
        return Formats.DATE.formatValue(getCurdate());
    }

    /**
     *
     * @return customer's current value of account
     */
    public Double getCurdebt() {
        return curdebt;
    }
    public void setCurdebt(Double curdebt) {
        this.curdebt = curdebt;
    }
    public String printCurDebt() {       
        return Formats.CURRENCY.formatValue(RoundUtils.getValue(getCurdebt()));
    }

    
    /**
     *
     * @param amount
     * @param d
     */
    public void updateCurDebt(Double amount, Date d) {
        
        curdebt = curdebt == null ? amount : curdebt + amount;
// JG Aug 2014
        curdate =  (new Date());
        
        if (RoundUtils.compare(curdebt, 0.0) > 0) {
            if (curdate == null) {
                // new date
                curdate = d;
            }
        } else if (RoundUtils.compare(curdebt, 0.0) == 0) {
            curdebt = null;
            curdate = null;
        } else { // < 0
            curdate = null;
        }
        
    }

    /**
     *
     * @return customer's firstname string
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return customer's lastname string
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     *
     * @return customer's email string
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return customer's Primary telephone string
     */
    @Override
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return customer's Secondary telephone string
     */
    public String getPhone2() {
        return phone2;
    }

    /**
     *
     * @param phone2
     */
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    /**
     *
     * @return customer's fax number string
     */
    public String getFax() {
        return fax;
    }

    /**
     *
     * @param fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     *
     * @return customer's address line 1 string
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return customer's address line 2 string
     */
    public String getAddress2() {
        return address2;
    }

    /**
     *
     * @param address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     *
     * @return customer's postal/zip code string
     */
    @Override
    public String getPostal() {
        return postal;
    }

    /**
     *
     * @param postal
     */
    @Override
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     *
     * @return customer's address city string
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return customer's address region/state/county string
     */
    public String getRegion() {
        return region;
    }

    /**
     *
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     *
     * @return customer's address country string
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return customer's photograph / image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }
    
}
