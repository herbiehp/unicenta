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

package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.*;
import com.openbravo.format.Formats;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author adrianromero
 */
public class DataLogicSystem extends BeanFactoryDataSingle {
    
    /**
     *
     */
    protected String m_sInitScript;
    private SentenceFind m_version;       
    private SentenceExec m_dummy;
    private String m_dbVersion;
    
    /**
     *
     */
    protected SentenceList m_peoplevisible;  

    /**
     *
     */
    protected SentenceFind m_peoplebycard;  

    /**
     *
     */
    protected SerializerRead peopleread;

    /**
     *
     */
    protected SentenceList m_permissionlist;

    /**
     *
     */
    protected SerializerRead productIdRead;
    
    
    private SentenceFind m_rolepermissions; 
    private SentenceExec m_changepassword;    
    private SentenceFind m_locationfind;
    private SentenceExec m_insertCSVEntry;
    private SentenceFind m_getProductAllFields;
    private SentenceFind m_getProductRefAndCode;    
    private SentenceFind m_getProductRefAndName; 
    private SentenceFind m_getProductCodeAndName;  
    private SentenceFind m_getProductByReference;
    private SentenceFind m_getProductByCode;    
    private SentenceFind m_getProductByName;    
    
    private SentenceFind m_resourcebytes;
    private SentenceExec m_resourcebytesinsert;
    private SentenceExec m_resourcebytesupdate;

    protected SentenceFind m_sequencecash;
    protected SentenceFind m_activecash;
    protected SentenceExec m_insertcash;
    protected SentenceExec m_draweropened;
    protected SentenceExec m_updatepermissions;
    protected SentenceExec m_lineremoved;
    
    private String SQL;    
    private Map<String, byte[]> resourcescache;
    
    /** Creates a new instance of DataLogicSystem */
    public DataLogicSystem() {            
    }
    
    /**
     *
     * @param s
     */
    @Override
    public void init(Session s){

        m_sInitScript = "/com/openbravo/pos/scripts/" + s.DB.getName();
        m_dbVersion = s.DB.getName();

        m_version = new PreparedSentence(s, "SELECT VERSION FROM APPLICATIONS WHERE ID = ?", SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);
        m_dummy = new StaticSentence(s, "SELECT * FROM PEOPLE WHERE 1 = 0");
         
        final ThumbNailBuilder tnb = new ThumbNailBuilder(32, 32, "com/openbravo/images/sysadmin.png");        
        peopleread = new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new AppUser(
                        dr.getString(1),
                        dr.getString(2),
                        dr.getString(3),
                        dr.getString(4),
                        dr.getString(5),
                        new ImageIcon(tnb.getThumbNail(ImageUtils.readImage(dr.getBytes(6)))));                
            }
        };

//Add 23.2.14 JDL new SQL fro CVS import    
//*******************************************************************        
        productIdRead =new SerializerRead() {
            @Override
            public String readValues(DataRead dr) throws BasicException {
                return (                       
                        dr.getString(1)
                       );                
            }};

 	m_getProductAllFields = new PreparedSentence(s
		, "SELECT ID FROM PRODUCTS WHERE REFERENCE=? AND CODE=? AND NAME=? "
		, new SerializerWriteBasic(new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING})
		, productIdRead
                );
      
       m_getProductRefAndCode  = new PreparedSentence(s
		, "SELECT ID FROM PRODUCTS WHERE REFERENCE=? AND CODE=?"
		, new SerializerWriteBasic(new Datas[] {Datas.STRING, Datas.STRING})
		, productIdRead
                );
    
       m_getProductRefAndName  = new PreparedSentence(s
		, "SELECT ID FROM PRODUCTS WHERE REFERENCE=? AND NAME=? "
		, new SerializerWriteBasic(new Datas[] {Datas.STRING, Datas.STRING})
		, productIdRead
                );
    
       m_getProductCodeAndName  = new PreparedSentence(s
		, "SELECT ID FROM PRODUCTS WHERE CODE=? AND NAME=? "
		, new SerializerWriteBasic(new Datas[] {Datas.STRING, Datas.STRING})
		, productIdRead
                );       

      m_getProductByReference  = new PreparedSentence(s
		, "SELECT ID FROM PRODUCTS WHERE REFERENCE=? "
		, SerializerWriteString.INSTANCE //(Datas.STRING)
		, productIdRead
                ); 
       
      m_getProductByCode  = new PreparedSentence(s
		, "SELECT ID FROM PRODUCTS WHERE CODE=? "
              , SerializerWriteString.INSTANCE //(Datas.STRING)
		//, new SerializerWriteBasic(Datas.STRING)
		, productIdRead
                );

      m_getProductByName  = new PreparedSentence(s
		, "SELECT ID FROM PRODUCTS WHERE NAME=? "
		, SerializerWriteString.INSTANCE //(Datas.STRING)
              //, new SerializerWriteBasic(Datas.STRING)
		, productIdRead
                );     
      
 //******************************************************************      
       
        
        
        m_peoplevisible = new StaticSentence(s
            , "SELECT ID, NAME, APPPASSWORD, CARD, ROLE, IMAGE FROM PEOPLE WHERE VISIBLE = " + s.DB.TRUE() + " ORDER BY NAME"
            , null
            , peopleread);

        m_peoplebycard = new PreparedSentence(s
            , "SELECT ID, NAME, APPPASSWORD, CARD, ROLE, IMAGE FROM PEOPLE WHERE CARD = ? AND VISIBLE = " + s.DB.TRUE()
            , SerializerWriteString.INSTANCE
            , peopleread);
         
        m_resourcebytes = new PreparedSentence(s
            , "SELECT CONTENT FROM RESOURCES WHERE NAME = ?"
            , SerializerWriteString.INSTANCE
            , SerializerReadBytes.INSTANCE);
        
        Datas[] resourcedata = new Datas[] {Datas.STRING, Datas.STRING, Datas.INT, Datas.BYTES};
        m_resourcebytesinsert = new PreparedSentence(s
                , "INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES (?, ?, ?, ?)"
                , new SerializerWriteBasic(resourcedata));
        m_resourcebytesupdate = new PreparedSentence(s
                , "UPDATE RESOURCES SET NAME = ?, RESTYPE = ?, CONTENT = ? WHERE NAME = ?"
                , new SerializerWriteBasicExt(resourcedata, new int[] {1, 2, 3, 1}));
        
        m_rolepermissions = new PreparedSentence(s
                , "SELECT PERMISSIONS FROM ROLES WHERE ID = ?"
            , SerializerWriteString.INSTANCE
            , SerializerReadBytes.INSTANCE);     
        
        m_changepassword = new StaticSentence(s
                , "UPDATE PEOPLE SET APPPASSWORD = ? WHERE ID = ?"
                ,new SerializerWriteBasic(new Datas[] {Datas.STRING, Datas.STRING}));

        m_sequencecash = new StaticSentence(s,
                "SELECT MAX(HOSTSEQUENCE) FROM CLOSEDCASH WHERE HOST = ?",
                SerializerWriteString.INSTANCE,
                SerializerReadInteger.INSTANCE);
        
        m_activecash = new StaticSentence(s
            , "SELECT HOST, HOSTSEQUENCE, DATESTART, DATEEND, NOSALES FROM CLOSEDCASH WHERE MONEY = ?"
            , SerializerWriteString.INSTANCE
            , new SerializerReadBasic(new Datas[] {
                Datas.STRING, 
                Datas.INT, 
                Datas.TIMESTAMP, 
                Datas.TIMESTAMP,
                Datas.INT}));            
        
        m_insertcash = new StaticSentence(s
                , "INSERT INTO CLOSEDCASH(MONEY, HOST, HOSTSEQUENCE, DATESTART, DATEEND) " +
                  "VALUES (?, ?, ?, ?, ?)"
                , new SerializerWriteBasic(new Datas[] {
                    Datas.STRING, 
                    Datas.STRING, 
                    Datas.INT, 
                    Datas.TIMESTAMP, 
                    Datas.TIMESTAMP}));

        m_draweropened = new StaticSentence(s
                , "INSERT INTO DRAWEROPENED ( NAME, TICKETID) " +
                  "VALUES (?, ?)"
                , new SerializerWriteBasic(new Datas[] {
                    Datas.STRING, 
                    Datas.STRING}));       
        
        m_lineremoved = new StaticSentence(s,
                "INSERT INTO LINEREMOVED (NAME, TICKETID, PRODUCTID, PRODUCTNAME, UNITS) " +
                "VALUES (?, ?, ?, ?, ?)",
                new SerializerWriteBasic(new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE}));
        
        m_locationfind = new StaticSentence(s
                , "SELECT NAME FROM LOCATIONS WHERE ID = ?"
                , SerializerWriteString.INSTANCE
                , SerializerReadString.INSTANCE);   
        
//Add 13.2.14 JDL for new gui based permissions                
        m_permissionlist = new StaticSentence(s
                , "SELECT PERMISSIONS FROM PERMISSIONS WHERE ID = ?"
                , SerializerWriteString.INSTANCE
                , new SerializerReadBasic(new Datas[] {
                    Datas.STRING
                }));         
         
        m_updatepermissions = new StaticSentence(s
                , "INSERT INTO PERMISSIONS (ID, PERMISSIONS) " +
                  "VALUES (?, ?)"
                , new SerializerWriteBasic(new Datas[] {
                    Datas.STRING, 
                    Datas.STRING})); 
        
        
// added 1.3.14.14 JDL new routine to write to CSV table, to clean up CSV import routine        
        m_insertCSVEntry = new StaticSentence(s
                , "INSERT INTO CSVIMPORT (ID, ROWNUMBER, CSVERROR, REFERENCE, CODE, NAME, PRICEBUY, PRICESELL, PREVIOUSBUY, PREVIOUSSELL,CATEGORY) " +
                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                , new SerializerWriteBasic(new Datas[] {
                    Datas.STRING,
                    Datas.STRING,
                    Datas.STRING,
                    Datas.STRING,
                    Datas.STRING,
                    Datas.STRING,
                    Datas.DOUBLE,
                    Datas.DOUBLE,
                    Datas.DOUBLE,
                    Datas.DOUBLE,
                    Datas.STRING
                })); 

        resetResourcesCache();        
    }

    /**
     *
     * @return
     */
    public String getInitScript() {
        return m_sInitScript;
    }
    
    /**
     *
     * @return
     */
    public String getDBVersion(){
        return m_dbVersion;        
    }

    /**
     *
     * @return
     * @throws BasicException
     */
    public final String findVersion() throws BasicException {
        return (String) m_version.find(AppLocal.APP_ID);
    }
    
    /**
     *
     * @return
     * @throws BasicException
     */
    public final String getUser() throws BasicException {
        return ("");
        
    }

    /**
     *
     * @throws BasicException
     */
    public final void execDummy() throws BasicException {
        m_dummy.exec();
    }

    /**
     *
     * @return
     * @throws BasicException
     */
    public final List listPeopleVisible() throws BasicException {
        return m_peoplevisible.list();
    }

    /**
     *
     * @param role
     * @return
     * @throws BasicException
     */
    public final List<String> getPermissions(String role)throws BasicException {         
        return m_permissionlist.list(role);
    }
    
    /**
     *
     * @param card
     * @return
     * @throws BasicException
     */
    public final AppUser findPeopleByCard(String card) throws BasicException {
        return (AppUser) m_peoplebycard.find(card);
    }

    /**
     *
     * @param sRole
     * @return
     */
    public final String findRolePermissions(String sRole) {
        
        try {
            return Formats.BYTEA.formatValue(m_rolepermissions.find(sRole));        
        } catch (BasicException e) {
            return null;                    
        }             
    }
    
    /**
     *
     * @param userdata
     * @throws BasicException
     */
    public final void execChangePassword(Object[] userdata) throws BasicException {
        m_changepassword.exec(userdata);
    }
    
    /**
     *
     */
    public final void resetResourcesCache() {
// JG 16 May use multicatch
        resourcescache = new HashMap<>();      
    }
    
//    private final byte[] getResource(String name) {
        private byte[] getResource(String name) {

        byte[] resource;
        
        resource = resourcescache.get(name);
        
        if (resource == null) {       
            // Primero trato de obtenerlo de la tabla de recursos
            try {
                resource = (byte[]) m_resourcebytes.find(name);
                resourcescache.put(name, resource);
            } catch (BasicException e) {
                resource = null;
            }
        }
        
        return resource;
    }
    
    /**
     *
     * @param name
     * @param type
     * @param data
     */
    public final void setResource(String name, int type, byte[] data) {
        
        Object[] value = new Object[] {UUID.randomUUID().toString(), name, type, data};
        try {
            if (m_resourcebytesupdate.exec(value) == 0) {
                m_resourcebytesinsert.exec(value);
            }
            resourcescache.put(name, data);
        } catch (BasicException e) {
        }
    }
    
    /**
     *
     * @param sName
     * @param data
     */
    public final void setResourceAsBinary(String sName, byte[] data) {
        setResource(sName, 2, data);
    }
    
    /**
     *
     * @param sName
     * @return
     */
    public final byte[] getResourceAsBinary(String sName) {
        return getResource(sName);
    }
    
    /**
     *
     * @param sName
     * @return
     */
    public final String getResourceAsText(String sName) {
        return Formats.BYTEA.formatValue(getResource(sName));
    }
    
    /**
     *
     * @param sName
     * @return
     */
    public final String getResourceAsXML(String sName) {
        return Formats.BYTEA.formatValue(getResource(sName));
    }

    /**
     *
     * @param sName
     * @return
     */
    public final BufferedImage getResourceAsImage(String sName) {
        try {
            byte[] img = getResource(sName); // , ".png"
            return img == null ? null : ImageIO.read(new ByteArrayInputStream(img));
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     *
     * @param sName
     * @param p
     */
    public final void setResourceAsProperties(String sName, Properties p) {
        if (p == null) {
            setResource(sName, 0, null); // texto
        } else {
            try {
                ByteArrayOutputStream o = new ByteArrayOutputStream();
                p.storeToXML(o, AppLocal.APP_NAME, "UTF8");
                setResource(sName, 0, o.toByteArray()); // El texto de las propiedades   
            } catch (IOException e) { // no deberia pasar nunca
            }            
        }
    }
    
    /**
     *
     * @param sName
     * @return
     */
    public final Properties getResourceAsProperties(String sName) {
        
        Properties p = new Properties();
        try {
            byte[] img = getResourceAsBinary(sName);
            if (img != null) {
                p.loadFromXML(new ByteArrayInputStream(img));
            }
        } catch (IOException e) {
        }
        return p;
    }

    /**
     *
     * @param host
     * @return
     * @throws BasicException
     */
    public final int getSequenceCash(String host) throws BasicException {
        Integer i = (Integer) m_sequencecash.find(host);
        return (i == null) ? 1 : i;
    }

    /**
     *
     * @param sActiveCashIndex
     * @return
     * @throws BasicException
     */
    public final Object[] findActiveCash(String sActiveCashIndex) throws BasicException {
        return (Object[]) m_activecash.find(sActiveCashIndex);
    }
    
    /**
     *
     * @param cash
     * @throws BasicException
     */
    public final void execInsertCash(Object[] cash) throws BasicException {
        m_insertcash.exec(cash);
    }

    /**
     *
     * @param drawer
     * @throws BasicException
     */
    public final void execDrawerOpened(Object[] drawer) throws BasicException {
        m_draweropened.exec(drawer);
    }

    /**
     *
     * @param permissions
     * @throws BasicException
     */
    public final void execUpdatePermissions(Object[] permissions) throws BasicException {
        m_updatepermissions.exec(permissions);
    }

    /**
     *
     * @param line
     */
    public final void execLineRemoved(Object[] line) {
        try {
            m_lineremoved.exec(line);
        } catch (BasicException e) {
        }
    }
    
    /**
     *
     * @param iLocation
     * @return
     * @throws BasicException
     */
    public final String findLocationName(String iLocation) throws BasicException {
        return (String) m_locationfind.find(iLocation);
    }

    /**
     *
     * @param csv
     * @throws BasicException
     */
    public final void execAddCSVEntry(Object[] csv) throws BasicException {
        m_insertCSVEntry.exec(csv);
}
    
   
   // This is used by CSVimport to detect what type of product insert we are looking at, or what error occured

    /**
     *
     * @param myProduct
     * @return
     * @throws BasicException
     */
        public final String getProductRecordType(Object[] myProduct) throws BasicException {
        // check if the product exist with all the details, if so return product ID
        if (m_getProductAllFields.find(myProduct) != null){
            return m_getProductAllFields.find(myProduct).toString();
        } 
        // check if the product exists with matching reference and code, but a different name
        if (m_getProductRefAndCode.find(myProduct[0],myProduct[1]) != null){
            return "name error";
        }
        
       if (m_getProductRefAndName.find(myProduct[0],myProduct[2]) != null){
            return "barcode error";
        }

       if (m_getProductCodeAndName.find(myProduct[1],myProduct[2]) != null){
            return "reference error";
        }
        
       if (m_getProductByReference.find(myProduct[0]) != null){
            return "Duplicate Reference found.";
        }       

       if (m_getProductByCode.find(myProduct[1]) != null){
            return "Duplicate Barcode found.";
        }
       
       if (m_getProductByName.find(myProduct[2]) != null){
            return "Duplicate Description found.";
        }       
       
       return "new";
    } 
   
   
}
