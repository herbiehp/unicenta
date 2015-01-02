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
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>

package com.openbravo.pos.forms;

import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creation and Editing of stored settings
 * unicentaopos.properties
 * @author JG uniCenta
 * @author adrianromero
 */
public class AppConfig implements AppProperties {

    private static final Logger logger = Logger.getLogger("com.openbravo.pos.forms.AppConfig");
     
    private static AppConfig m_instance = null;
    private Properties m_propsconfig;
    private File configfile;
      
    /**
     *
     * @param args
     */
    public AppConfig(String[] args) {
        if (args.length == 0) {
            init(getDefaultConfig());
        } else {
            init(new File(args[0]));
        }
    }
    
    /**
     *
     * @param configfile
     */
    public AppConfig(File configfile) {
        init(configfile);
    }
    
    private void init(File configfile) {
        this.configfile = configfile;
        m_propsconfig = new Properties();

        logger.log(Level.INFO, "Reading configuration file: {0}", configfile.getAbsolutePath());
    }
    
    private File getDefaultConfig() {
        return new File(new File(System.getProperty("user.home")), AppLocal.APP_ID + ".properties");
    }
    
    /**
     *
     * @param sKey
     * @return keypair from .properties filename
     */
    @Override
    public String getProperty(String sKey) {
        return m_propsconfig.getProperty(sKey);
    }
    
    /**
     *
     * @return Machine name
     */
    @Override
    public String getHost() {
        return getProperty("machine.hostname");
    }

    /**
     *
     * @return .properties filename
     */
    @Override
    public File getConfigFile() {
        return configfile;
    }
    
    /**
     * Update .properties resource keypair values
     * @param sKey
     * @param sValue
     */
    public void setProperty(String sKey, String sValue) {
        if (sValue == null) {
            m_propsconfig.remove(sKey);
        } else {
            m_propsconfig.setProperty(sKey, sValue);
        }
    }
    
   /**
     * Local machine identity
     * @return Machine name from OS
     */
    private String getLocalHostName() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException eUH) {
            return "localhost";
        }
    }
   
    /**
     *
     * @return Delete .properties filename
     */
    public boolean delete() {
        loadDefault();
        return configfile.delete();
    }

    /**
     * Get instance settings
     * @Read .properties resource files
     */
    public void load() {

        loadDefault();

        try {
            InputStream in = new FileInputStream(configfile);
            if (in != null) {
                m_propsconfig.load(in);
                in.close();
            }
        } catch (IOException e){
            loadDefault();
        }
    
    }

    /**
     *
     * @return 0 or 00 number keypad boolean true/false
     */
    public Boolean isPriceWith00() {
        String prop = getProperty("pricewith00");
        if (prop == null) {
            return false;
        } else {
            return prop.equals("true");
        }
    }

    /**
     * Save values to .properties file
     * @throws IOException
     */
    public void save() throws IOException {
        
        OutputStream out = new FileOutputStream(configfile);
        if (out != null) {
            m_propsconfig.store(out, AppLocal.APP_NAME + ". Configuration file.");
            out.close();
        }
    }
    
    /**
     * Settings over-rides
     * @throws IOException
     */
    
    private void loadDefault() {
        
        m_propsconfig = new Properties();
        
        String dirname = System.getProperty("dirname.path");
        dirname = dirname == null ? "./" : dirname;
        
        m_propsconfig.setProperty("db.driverlib", new File(new File(dirname), "lib/derby.jar").getAbsolutePath());
        m_propsconfig.setProperty("db.driver", "org.apache.derby.jdbc.EmbeddedDriver");
        m_propsconfig.setProperty("db.URL", "jdbc:derby:" + new File(new File(System.getProperty("user.home")), AppLocal.APP_ID + "-database").getAbsolutePath() + ";create=true");
        m_propsconfig.setProperty("db.user", "");
        m_propsconfig.setProperty("db.password", "");

//        m_propsconfig.setProperty("db.driverlib", new File(new File(dirname), "lib/hsqldb.jar").getAbsolutePath());
//        m_propsconfig.setProperty("db.driver", "org.hsqldb.jdbcDriver");
//        m_propsconfig.setProperty("db.URL", "jdbc:hsqldb:file:" + new File(new File(System.getProperty("user.home")), AppLocal.APP_ID + "-db").getAbsolutePath() + ";shutdown=true");
//        m_propsconfig.setProperty("db.user", "SA");
//        m_propsconfig.setProperty("db.password", "");
        
//        m_propsconfig.setProperty("db.driver", "com.mysql.jdbc.Driver");
//        m_propsconfig.setProperty("db.URL", "jdbc:mysql://localhost:3306/database");
//        m_propsconfig.setProperty("db.user", "user");         
//        m_propsconfig.setProperty("db.password", "password");
        
//        m_propsconfig.setProperty("db.driver", "org.postgresql.Driver");
//        m_propsconfig.setProperty("db.URL", "jdbc:postgresql://localhost:5432/database");
//        m_propsconfig.setProperty("db.user", "user");         
//        m_propsconfig.setProperty("db.password", "password");        


 /**
  * 
  * Default component settings
  */       
        m_propsconfig.setProperty("machine.hostname", getLocalHostName());
        
        Locale l = Locale.getDefault();
        m_propsconfig.setProperty("user.language", l.getLanguage());
        m_propsconfig.setProperty("user.country", l.getCountry());
        m_propsconfig.setProperty("user.variant", l.getVariant());     
        
        m_propsconfig.setProperty("swing.defaultlaf", System.getProperty("swing.defaultlaf", "javax.swing.plaf.metal.MetalLookAndFeel"));
//        m_propsconfig.setProperty("swing.defaultlaf", System.getProperty("swing.defaultlaf", "javax.swing.plaf.synth.SynthLookAndFeel"));        
        
        m_propsconfig.setProperty("machine.printer", "screen");
        m_propsconfig.setProperty("machine.printer.2", "Not defined");
        m_propsconfig.setProperty("machine.printer.3", "Not defined");
        m_propsconfig.setProperty("machine.printer.4", "Not defined");
        m_propsconfig.setProperty("machine.printer.5", "Not defined");
        m_propsconfig.setProperty("machine.printer.6", "Not defined");
                
        m_propsconfig.setProperty("machine.display", "screen");
        m_propsconfig.setProperty("machine.scale", "Not defined");
        m_propsconfig.setProperty("machine.screenmode", "window"); // fullscreen / window
        m_propsconfig.setProperty("machine.ticketsbag", "standard");
        m_propsconfig.setProperty("machine.scanner", "Not defined");
        
        m_propsconfig.setProperty("payment.gateway", "external");
        m_propsconfig.setProperty("payment.magcardreader", "Not defined");
        m_propsconfig.setProperty("payment.testmode", "false");
        m_propsconfig.setProperty("payment.commerceid", "");
        m_propsconfig.setProperty("payment.commercepassword", "password");
        
        m_propsconfig.setProperty("machine.printername", "(Default)");

        // Receipt printer paper set to 72mmx200mm

// JG 7 May 14 Epson ESC/POS settings
        m_propsconfig.setProperty("paper.receipt.x", "10");
        m_propsconfig.setProperty("paper.receipt.y", "10");
// JG 7 May 14 Star Micronics settings
//        m_propsconfig.setProperty("paper.receipt.x", "10");
//        m_propsconfig.setProperty("paper.receipt.y", "287");
        m_propsconfig.setProperty("paper.receipt.width", "190");
        m_propsconfig.setProperty("paper.receipt.height", "546");
        m_propsconfig.setProperty("paper.receipt.mediasizename", "A4");

        // Normal printer paper for A4
        m_propsconfig.setProperty("paper.standard.x", "72");
        m_propsconfig.setProperty("paper.standard.y", "72");
        m_propsconfig.setProperty("paper.standard.width", "451");
        m_propsconfig.setProperty("paper.standard.height", "698");
        m_propsconfig.setProperty("paper.standard.mediasizename", "A4");

        m_propsconfig.setProperty("machine.uniqueinstance", "false");
        
//JG July 2014 - Thank you Ron Isaacson On-screen receipt defauls to 42 columns
        m_propsconfig.setProperty("screen.receipt.columns", "42");        
        

    }
}