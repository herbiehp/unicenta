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

package com.openbravo.pos.printer;

import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 *
 * @author JG uniCenta
 */
public interface DevicePrinter {
    
    /**
     *
     */
    public static final int SIZE_0 = 0;

    /**
     *
     */
    public static final int SIZE_1 = 1;

    /**
     *
     */
    public static final int SIZE_2 = 2;

    /**
     *
     */
    public static final int SIZE_3 = 3;
    
    /**
     *
     */
    public static final int STYLE_PLAIN = 0;

    /**
     *
     */
    public static final int STYLE_BOLD = 1;

    /**
     *
     */
    public static final int STYLE_UNDERLINE = 2;

    /**
     *
     */
    public static final int ALIGN_LEFT = 0;

    /**
     *
     */
    public static final int ALIGN_RIGHT = 1;

    /**
     *
     */
    public static final int ALIGN_CENTER = 2;
    
    /**
     *
     */
    public static final String BARCODE_EAN13 = "EAN13";

    /**
     *
     */
    public static final String BARCODE_CODE128 = "CODE128";
    
    /**
     *
     */
    public static final String POSITION_BOTTOM = "bottom";

    /**
     *
     */
    public static final String POSITION_NONE = "none";
    
    // INTERFAZ DESCRIPCION

    /**
     *
     * @return
     */
        public String getPrinterName();

    /**
     *
     * @return
     */
    public String getPrinterDescription();

    /**
     *
     * @return
     */
    public JComponent getPrinterComponent();

    /**
     *
     */
    public void reset();
    
    // INTERFAZ PRINTER

    /**
     *
     */
        public void beginReceipt();

    /**
     *
     * @param image
     */
    public void printImage(BufferedImage image);

    /**
     *
     */
    public void printLogo();

    /**
     *
     * @param type
     * @param position
     * @param code
     */
    public void printBarCode(String type, String position, String code);

    /**
     *
     * @param iTextSize
     */
    public void beginLine(int iTextSize);

    /**
     *
     * @param iStyle
     * @param sText
     */
    public void printText(int iStyle, String sText);

    /**
     *
     */
    public void endLine();

    /**
     *
     */
    public void endReceipt();   
    
    // INTERFAZ VARIOUS
    
    /**
     *
     */
        public void openDrawer();    
}
