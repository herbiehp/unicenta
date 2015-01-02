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

import com.openbravo.pos.util.StringUtils;
import java.io.Serializable;

/** @author adrianromero */

// JG 20 Sep 12 Extended for Postal
// JG 2 Sep 13 Extended for Phone + Email
public class CustomerInfo implements Serializable {
    
    private static final long serialVersionUID = 9083257536541L;

    /**
     * Customer unique ID
     */
    protected String id;

    /**
     * Customer searchkey
     */
    protected String searchkey;

    /**
     * Customer tax ID
     */
    protected String taxid;

    /**
     *Customer Account Name
     */
    protected String name;

    /**
     * Customer post/zip code
     */
    protected String postal;

    /**
     * Customer Primary telephone
     */
    protected String phone;

    /**
     * Customer Email
     */
    protected String email;    
    
    /** Creates a new instance of UserInfoBasic
     * @param id */
    public CustomerInfo(String id) {
        this.id = id;
        this.searchkey = null;
        this.taxid = null;
        this.name = null;
        this.postal = null;
        this.phone = null;
        this.email = null;
    }
    
    /**
     *
     * @return id string
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return taxid string
     */
    public String getTaxid() {
        return taxid;
    }    

    /**
     *
     * @param taxid
     */
    public void setTaxid(String taxid) {
        this.taxid = taxid;
    }
    
    /**
     *
     * @return searchkey string
     */
    public String getSearchkey() {
        return searchkey;
    }

    /**
     *
     * @param searchkey
     */
    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }
    
    /**
     *
     * @return name string
     */
    public String getName() {
        return name;
    }   

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return postal/zip code string
     */
    public String getPostal() {
        return postal;
    }   

    /**
     *
     * @param postal
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }
    
    /**
     *
     * @return Primary Telephone string
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     *
     * @return email string
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     *
     * @return
     */
    public String printTaxid() {
        return StringUtils.encodeXML(taxid);
    }

    /**
     *
     * @return
     */
    public String printName() {
        return StringUtils.encodeXML(name);
    }
    
    @Override
    public String toString() {
        return getName();
    }    
}