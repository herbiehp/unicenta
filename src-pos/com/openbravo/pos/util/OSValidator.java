/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.util;

import org.jdesktop.swingx.util.OS;

/**
 *
 * @author JG uniCenta
 */
public class OSValidator {
   
    private String OS = System.getProperty("os.name").toLowerCase();

    /**
     *
     */
    public OSValidator() {
            
        }
        
    /**
     *
     * @return
     */
    public String getOS(){
      if (isWindows()) {
                    return("w");
		} else if (isMac()) {
                    return("m");
		} else if (isUnix()) {
                    return("l");
		} else if (isSolaris()) {
                    return("s");
		} else {
                    return("x");
		}
    }

    /**
     *
     * @return
     */
    public boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

    /**
     *
     * @return
     */
    public  boolean isMac() {
		return (OS.indexOf("mac") >= 0); 
	}

    /**
     *
     * @return
     */
    public  boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}

    /**
     *
     * @return
     */
    public  boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0); 
	}

        
        
        
        
}
