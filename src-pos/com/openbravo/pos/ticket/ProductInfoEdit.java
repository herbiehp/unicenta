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

import java.awt.image.BufferedImage;

/**
 *
 * @author adrianromero
 * Created on 21 de marzo de 2007, 21:28
 *
 */
public class ProductInfoEdit {
    
    /**
     *
     */
    protected String m_ID;

    /**
     *
     */
    protected String m_sRef;

    /**
     *
     */
    protected String m_sCode;

    /**
     *
     */
    protected String m_sName;

    /**
     *
     */
    protected boolean m_bCom;

    /**
     *
     */
    protected boolean m_bScale;

    /**
     *
     */
    protected String m_sCategoryID;    

    /**
     *
     */
    protected String m_sTaxID;

    /**
     *
     */
    protected String attributeuseid;

    /**
     *
     */
    protected double m_dPriceBuy;

    /**
     *
     */
    protected double m_dPriceSell; 

    /**
     *
     */
    protected BufferedImage m_Image;
    
    /**
     *
     */
    protected Double m_dStockCost;

    /**
     *
     */
    protected Double m_dStockVolume;

    /**
     *
     */
    protected Integer m_iCatalogOrder;
    
    /** Creates a new instance of ProductInfoEdit */
    public ProductInfoEdit() {
        m_ID = null;
        m_sRef = "0000";
        m_sCode = "0000";
        m_sName = null;
        m_bCom = false;
        m_bScale = false;
        m_sCategoryID = null;
        m_sTaxID = null;
        attributeuseid = null;
        m_dPriceBuy = 0.0;
        m_dPriceSell = 0.0;
        m_Image = null;
        m_dStockCost = null;
        m_dStockVolume = null;
        m_iCatalogOrder = null;            
    }
   
    /**
     *
     * @return
     */
    public final String getID() {
        return m_ID;
    }
    
    /**
     *
     * @param id
     */
    public final void setID(String id) {
        m_ID = id;
    }
    
    /**
     *
     * @return
     */
    public final String getReference(){
        return m_sRef;
    }

    /**
     *
     * @param sRef
     */
    public final void setReference(String sRef){
        m_sRef = sRef;
    }    

    /**
     *
     * @return
     */
    public final String getCode(){
        return m_sCode;
    }

    /**
     *
     * @param sCode
     */
    public final void setCode(String sCode){
        m_sCode = sCode;
    }

    /**
     *
     * @return
     */
    public final String getName() {            
        return m_sName;
    }

    /**
     *
     * @param sName
     */
    public final void setName(String sName){            
        m_sName = sName;
    }

    /**
     *
     * @return
     */
    public final boolean isCom() {            
        return m_bCom;
    }

    /**
     *
     * @param bValue
     */
    public final void setCom(boolean bValue){            
        m_bCom = bValue;
    }

    /**
     *
     * @return
     */
    public final boolean isScale() {            
        return m_bScale;
    }

    /**
     *
     * @param bValue
     */
    public final void setScale(boolean bValue){            
        m_bScale = bValue;
    }

    /**
     *
     * @return
     */
    public final String getCategoryID() {
        return m_sCategoryID;
    }

    /**
     *
     * @param sCategoryID
     */
    public final void setCategoryID(String sCategoryID) {
        m_sCategoryID = sCategoryID;
    }

    /**
     *
     * @return
     */
    public final String getTaxID() {
        return m_sTaxID;
    }

    /**
     *
     * @param sTaxID
     */
    public final void setTaxID(String sTaxID) {
        m_sTaxID = sTaxID;
    }

    /**
     *
     * @return
     */
    public final String getAttributeUseID() {
        return attributeuseid;
    }

    /**
     *
     * @param value
     */
    public final void setAttributeUseID(String value) {
        attributeuseid = value;
    }

    /**
     *
     * @return
     */
    public final double getPriceBuy(){
        return m_dPriceBuy;
    }    

    /**
     *
     * @param dPrice
     */
    public final void setPriceBuy(double dPrice) {
        m_dPriceBuy = dPrice;
    }        

    /**
     *
     * @return
     */
    public final double getPriceSell(){        
        return m_dPriceSell;
    }

    /**
     *
     * @param dPrice
     */
    public final void setPriceSell(double dPrice) {        
        m_dPriceSell = dPrice;
    }      

    /**
     *
     * @return
     */
    public BufferedImage getImage() {
        return m_Image;
    }

    /**
     *
     * @param img
     */
    public void setImage(BufferedImage img) {
        m_Image = img;
    }
    
    @Override
    public final String toString() {
        return m_sRef + " - " + m_sName;
    }    
}
