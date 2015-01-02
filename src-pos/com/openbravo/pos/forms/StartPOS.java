//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (C) 2008-2013 Openbravo, S.L.
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

import com.openbravo.format.Formats;
import com.openbravo.pos.instance.InstanceQuery;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import com.openbravo.pos.ticket.TicketInfo;


// JG 16 May 2013 deprecated for pushingpixels
// import org.jvnet.substance.SubstanceLookAndFeel;
// import org.jvnet.substance.api.SubstanceSkin;

/**
 *
 * @author adrianromero
 */
public class StartPOS {

    private static final Logger logger = Logger.getLogger("com.openbravo.pos.forms.StartPOS");
    
    
    /** Creates a new instance of StartPOS */
    private StartPOS() {
    }

    /**
     *
     * @return
     */
    public static boolean registerApp() {
                       
        // vemos si existe alguna instancia        
        InstanceQuery i = null;
        try {
            i = new InstanceQuery();
            i.getAppMessage().restoreWindow();
            return false;
// JG 6 May 2013 to Multicatch
        } catch (RemoteException | NotBoundException e) {
            return true;
        }  
    }
    
    /**
     *
     * @param args
     */
    public static void main (final String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                if (!registerApp()) {
                    System.exit(1);
                }
                
                AppConfig config = new AppConfig(args);
                config.load();
                
                // set Locale.
                String slang = config.getProperty("user.language");
                String scountry = config.getProperty("user.country");
                String svariant = config.getProperty("user.variant");
                if (slang != null && !slang.equals("") && scountry != null && svariant != null) {                                        
                    Locale.setDefault(new Locale(slang, scountry, svariant));
                }
                
                // Set the format patterns
                Formats.setIntegerPattern(config.getProperty("format.integer"));
                Formats.setDoublePattern(config.getProperty("format.double"));
                Formats.setCurrencyPattern(config.getProperty("format.currency"));
                Formats.setPercentPattern(config.getProperty("format.percent"));
                Formats.setDatePattern(config.getProperty("format.date"));
                Formats.setTimePattern(config.getProperty("format.time"));
                Formats.setDateTimePattern(config.getProperty("format.datetime"));               
                
                // Set the look and feel.
                try {             
                    
                    Object laf = Class.forName(config.getProperty("swing.defaultlaf")).newInstance();                    
                    if (laf instanceof LookAndFeel){
                        UIManager.setLookAndFeel((LookAndFeel) laf);
                    } else if (laf instanceof SubstanceSkin) {                      
                        SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);
                    }
// JG 6 May 2013 to multicatch
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {                
                    logger.log(Level.WARNING, "Cannot set Look and Feel", e);
                }
// JG July 2014 Hostname for Tickets
                String hostname = config.getProperty("machine.hostname");
                TicketInfo.setHostname(hostname);
                
                String screenmode = config.getProperty("machine.screenmode");
                if ("fullscreen".equals(screenmode)) {
                    JRootKiosk rootkiosk = new JRootKiosk();
                    rootkiosk.initFrame(config);
                } else {
                    JRootFrame rootframe = new JRootFrame(); 
                    rootframe.initFrame(config);
                }
            }
        });    
    }    
}