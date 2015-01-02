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

package com.openbravo.pos.sales.restaurant;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import com.openbravo.data.gui.NullIcon;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.basic.BasicException;
import java.awt.Insets;

/**
 *
 * @author JG uniCenta
 */
public class Place implements SerializableRead, java.io.Serializable {
    
    private static final long serialVersionUID = 8652254694281L;
    private static final Icon ICO_OCU = new ImageIcon(Place.class.getResource("/com/openbravo/images/edit_group.png"));
    private static final Icon ICO_FRE = new NullIcon(22, 22);
    
    private String m_sId;
    private String m_sName;
    private int m_ix;
    private int m_iy;
    private String m_sfloor;
    private String m_customer;
    private String m_waiter;
    private String m_ticketId;
    private Boolean m_tableMoved;
    
    private boolean m_bPeople;
    private JButton m_btn;
        
    /** Creates a new instance of TablePlace */
    public Place() {
    }

    /**
     *
     * @param dr
     * @throws BasicException
     */
    @Override
    public void readValues(DataRead dr) throws BasicException {
        m_sId = dr.getString(1);
        m_sName = dr.getString(2);
        m_ix = dr.getInt(3).intValue();
        m_iy = dr.getInt(4).intValue();
        m_sfloor = dr.getString(5);
        m_customer = dr.getString(6);
        m_waiter = dr.getString(7);
        m_ticketId = dr.getString(8);
        m_tableMoved = dr.getBoolean(9);
        
        m_bPeople = false;
        m_btn = new JButton();

        m_btn.setFocusPainted(false);
        m_btn.setFocusable(false);
        m_btn.setRequestFocusEnabled(false);
        m_btn.setHorizontalTextPosition(SwingConstants.CENTER);
        m_btn.setVerticalTextPosition(SwingConstants.BOTTOM);            
        m_btn.setIcon(ICO_FRE);
        m_btn.setText(m_sName);
        m_btn.setMargin(new Insets(2,5,2,5));

    }

    /**
     *
     * @return
     */
    public String getId() { return m_sId; }
    
    /**
     *
     * @return
     */
    public String getName() { return m_sName; }

    /**
     *
     * @return
     */
    public int getX() { return m_ix; }

    /**
     *
     * @return
     */
    public int getY() { return m_iy; }

    /**
     *
     * @return
     */
    public String getFloor() { return m_sfloor; }
   
    /**
     *
     * @return
     */
    public JButton getButton() { return m_btn; }
    
    /**
     *
     * @return
     */
    public String getCustomer(){ return m_customer; }

    /**
     *
     * @return
     */
    public String getWaiter(){ return m_waiter;}
    
    /**
     *
     * @return
     */
    public boolean hasPeople() {
        return m_bPeople;
    }   

    /**
     *
     * @param bValue
     */
    public void setPeople(boolean bValue) {
        m_bPeople = bValue;
        m_btn.setIcon(bValue ? ICO_OCU : ICO_FRE); 
    }     

    /**
     *
     */
    public void setButtonBounds() {
        Dimension d = m_btn.getPreferredSize();
        m_btn.setPreferredSize(new Dimension(d.width +30,d.height + 15));
        d = m_btn.getPreferredSize();
        m_btn.setBounds(m_ix - d.width / 2, m_iy - d.height / 2, d.width, d.height); 
    }
    
    /**
     *
     * @param btnText
     */
    public void setButtonText(String btnText){
        m_btn.setText(btnText);         
    }
    
}    

    
