package com.openbravo.pos.promotion;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.SerializableRead;

/**
 *
 * @author aurelien escartin
 *
 */
public class PromoTypeInfo implements SerializableRead, IKeyed {
    
    private static final long serialVersionUID = 8906929819402L;
    private String m_sID;
    private String m_sName;
    
    /** Creates a new instance of FloorsInfo */
    public PromoTypeInfo() {
        m_sID = null;
        m_sName = null;
    }
   
    /**
     *
     * @return
     */
    public Object getKey() {
        return m_sID;
    }
    
    /**
     *
     * @param dr
     * @throws BasicException
     */
    public void readValues(DataRead dr) throws BasicException {
        m_sID = dr.getString(1);
        m_sName = dr.getString(2);
    }

    /**
     *
     * @param sID
     */
    public void setID(String sID) {
        m_sID = sID;
    }
    
    /**
     *
     * @return
     */
    public String getID() {
        return m_sID;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return m_sName;
    }
    
    /**
     *
     * @param sName
     */
    public void setName(String sName) {
        m_sName = sName;
    } 
    
    public String toString(){
        return m_sName;
    }       
}