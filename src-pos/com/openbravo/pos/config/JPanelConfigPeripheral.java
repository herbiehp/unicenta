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
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.ReportUtils;
import com.openbravo.pos.util.StringParser;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.Map;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.SkinInfo;


// JG 16 May 2013 deprecated for pushingpixels
// import org.jvnet.substance.SubstanceLookAndFeel;
// import org.jvnet.substance.api.SubstanceSkin;
// import org.jvnet.substance.skin.SkinInfo;

/**
 *
 * @author JG uniCenta
 */

public class JPanelConfigPeripheral extends javax.swing.JPanel implements PanelConfig {

    private DirtyManager dirty = new DirtyManager();
    private ParametersConfig printer1printerparams;
    private ParametersConfig printer2printerparams;
    private ParametersConfig printer3printerparams;
    private ParametersConfig printer4printerparams;    
    private ParametersConfig printer5printerparams;
    private ParametersConfig printer6printerparams;

    /** Creates new form JPanelConfigGeneral */
    public JPanelConfigPeripheral() {

        initComponents();

        String[] printernames = ReportUtils.getPrintNames();

        jcboMachineDisplay.addActionListener(dirty);
        jcboConnDisplay.addActionListener(dirty);
        jcboSerialDisplay.addActionListener(dirty);
        m_jtxtJPOSName.getDocument().addDocumentListener(dirty);

                    
        jcboMachinePrinter.addActionListener(dirty);
        jcboConnPrinter.addActionListener(dirty);
        jcboSerialPrinter.addActionListener(dirty);
        m_jtxtJPOSPrinter.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer.getDocument().addDocumentListener(dirty);
        
        printer1printerparams = new ParametersPrinter(printernames);
        printer1printerparams.addDirtyManager(dirty);
        m_jPrinterParams1.add(printer1printerparams.getComponent(), "printer");

        jcboMachinePrinter2.addActionListener(dirty);
        jcboConnPrinter2.addActionListener(dirty);
        jcboSerialPrinter2.addActionListener(dirty);
        m_jtxtJPOSPrinter2.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer2.getDocument().addDocumentListener(dirty);

        printer2printerparams = new ParametersPrinter(printernames);
        printer2printerparams.addDirtyManager(dirty);
        m_jPrinterParams2.add(printer2printerparams.getComponent(), "printer");

        jcboMachinePrinter3.addActionListener(dirty);
        jcboConnPrinter3.addActionListener(dirty);
        jcboSerialPrinter3.addActionListener(dirty);
        m_jtxtJPOSPrinter3.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer3.getDocument().addDocumentListener(dirty);

        printer3printerparams = new ParametersPrinter(printernames);
        printer3printerparams.addDirtyManager(dirty);
        m_jPrinterParams3.add(printer3printerparams.getComponent(), "printer");
        
        
// New printers add JDL 10.11.12
        jcboMachinePrinter4.addActionListener(dirty);
        jcboConnPrinter4.addActionListener(dirty);
        jcboSerialPrinter4.addActionListener(dirty);
        m_jtxtJPOSPrinter4.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer4.getDocument().addDocumentListener(dirty);

        printer4printerparams = new ParametersPrinter(printernames);
        printer4printerparams.addDirtyManager(dirty);
        m_jPrinterParams4.add(printer4printerparams.getComponent(), "printer");
        
        jcboMachinePrinter5.addActionListener(dirty);
        jcboConnPrinter5.addActionListener(dirty);
        jcboSerialPrinter5.addActionListener(dirty);
        m_jtxtJPOSPrinter5.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer5.getDocument().addDocumentListener(dirty);

        printer5printerparams = new ParametersPrinter(printernames);
        printer5printerparams.addDirtyManager(dirty);
        m_jPrinterParams5.add(printer5printerparams.getComponent(), "printer");
        
        jcboMachinePrinter6.addActionListener(dirty);
        jcboConnPrinter6.addActionListener(dirty);
        jcboSerialPrinter6.addActionListener(dirty);
        m_jtxtJPOSPrinter6.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer6.getDocument().addDocumentListener(dirty);

        printer6printerparams = new ParametersPrinter(printernames);
        printer6printerparams.addDirtyManager(dirty);
        m_jPrinterParams6.add(printer6printerparams.getComponent(), "printer");
        
        
//        
        jcboMachineScale.addActionListener(dirty);
        jcboSerialScale.addActionListener(dirty);

        jcboMachineScanner.addActionListener(dirty);
        jcboSerialScanner.addActionListener(dirty);

        cboPrinters.addActionListener(dirty);


        // Printer 1
        jcboMachinePrinter.addItem("Not defined");
        jcboMachinePrinter.addItem("screen");
        jcboMachinePrinter.addItem("printer");
        jcboMachinePrinter.addItem("epson");
        jcboMachinePrinter.addItem("tmu220");
        jcboMachinePrinter.addItem("star");
        jcboMachinePrinter.addItem("ODP1000");
        jcboMachinePrinter.addItem("ithaca");
        jcboMachinePrinter.addItem("surepos");
        jcboMachinePrinter.addItem("plain");
        jcboMachinePrinter.addItem("javapos");

        jcboConnPrinter.addItem("serial");
        jcboConnPrinter.addItem("file");

        jcboSerialPrinter.addItem("COM1");
        jcboSerialPrinter.addItem("COM2");
        jcboSerialPrinter.addItem("COM3");
        jcboSerialPrinter.addItem("COM4");
        jcboSerialPrinter.addItem("COM5");
        jcboSerialPrinter.addItem("COM6");
        jcboSerialPrinter.addItem("COM7");
        jcboSerialPrinter.addItem("COM8");
        jcboSerialPrinter.addItem("LPT1");
        jcboSerialPrinter.addItem("/dev/ttyS0");
        jcboSerialPrinter.addItem("/dev/ttyS1");
        jcboSerialPrinter.addItem("/dev/ttyS2");
        jcboSerialPrinter.addItem("/dev/ttyS3");
        jcboSerialPrinter.addItem("/dev/ttyS4");
        jcboSerialPrinter.addItem("/dev/ttyS5");

        // Printer 2        
        jcboMachinePrinter2.addItem("Not defined");
        jcboMachinePrinter2.addItem("screen");
        jcboMachinePrinter2.addItem("printer");
        jcboMachinePrinter2.addItem("epson");
        jcboMachinePrinter2.addItem("tmu220");
        jcboMachinePrinter2.addItem("star");
        jcboMachinePrinter2.addItem("ODP1000");
        jcboMachinePrinter2.addItem("ithaca");
        jcboMachinePrinter2.addItem("surepos");
        jcboMachinePrinter2.addItem("plain");
        jcboMachinePrinter2.addItem("javapos");

        jcboConnPrinter2.addItem("serial");
        jcboConnPrinter2.addItem("file");

        jcboSerialPrinter2.addItem("COM1");
        jcboSerialPrinter2.addItem("COM2");
        jcboSerialPrinter2.addItem("COM3");
        jcboSerialPrinter2.addItem("COM4");
        jcboSerialPrinter2.addItem("COM5");
        jcboSerialPrinter2.addItem("COM6");
        jcboSerialPrinter2.addItem("COM7");
        jcboSerialPrinter2.addItem("COM8");        
        jcboSerialPrinter2.addItem("LPT1");
        jcboSerialPrinter2.addItem("/dev/ttyS0");
        jcboSerialPrinter2.addItem("/dev/ttyS1");
        jcboSerialPrinter2.addItem("/dev/ttyS2");
        jcboSerialPrinter2.addItem("/dev/ttyS3");
        jcboSerialPrinter2.addItem("/dev/ttyS4");
        jcboSerialPrinter2.addItem("/dev/ttyS5");

        // Printer 3
        jcboMachinePrinter3.addItem("Not defined");
        jcboMachinePrinter3.addItem("screen");
        jcboMachinePrinter3.addItem("printer");
        jcboMachinePrinter3.addItem("epson");
        jcboMachinePrinter3.addItem("tmu220");
        jcboMachinePrinter3.addItem("star");
        jcboMachinePrinter3.addItem("ODP1000");
        jcboMachinePrinter3.addItem("ithaca");
        jcboMachinePrinter3.addItem("surepos");
        jcboMachinePrinter3.addItem("plain");
        jcboMachinePrinter3.addItem("javapos");

        jcboConnPrinter3.addItem("serial");
        jcboConnPrinter3.addItem("file");

        jcboSerialPrinter3.addItem("COM1");
        jcboSerialPrinter3.addItem("COM2");
        jcboSerialPrinter3.addItem("COM3");
        jcboSerialPrinter3.addItem("COM4");
        jcboSerialPrinter3.addItem("COM5");
        jcboSerialPrinter3.addItem("COM6");
        jcboSerialPrinter3.addItem("COM7");
        jcboSerialPrinter3.addItem("COM8");        
        jcboSerialPrinter3.addItem("LPT1");
        jcboSerialPrinter3.addItem("/dev/ttyS0");
        jcboSerialPrinter3.addItem("/dev/ttyS1");
        jcboSerialPrinter3.addItem("/dev/ttyS2");
        jcboSerialPrinter3.addItem("/dev/ttyS3");
        jcboSerialPrinter3.addItem("/dev/ttyS4");
        jcboSerialPrinter3.addItem("/dev/ttyS5");

// New printer add JDL 10.11.12
        
        // Printer 4
        jcboMachinePrinter4.addItem("Not defined");
        jcboMachinePrinter4.addItem("screen");
        jcboMachinePrinter4.addItem("printer");
        jcboMachinePrinter4.addItem("epson");
        jcboMachinePrinter4.addItem("tmu220");
        jcboMachinePrinter4.addItem("star");
        jcboMachinePrinter4.addItem("ODP1000");
        jcboMachinePrinter4.addItem("ithaca");
        jcboMachinePrinter4.addItem("surepos");
        jcboMachinePrinter4.addItem("plain");
        jcboMachinePrinter4.addItem("javapos");

        jcboConnPrinter4.addItem("serial");
        jcboConnPrinter4.addItem("file");

        jcboSerialPrinter4.addItem("COM1");
        jcboSerialPrinter4.addItem("COM2");
        jcboSerialPrinter4.addItem("COM3");
        jcboSerialPrinter4.addItem("COM4");
        jcboSerialPrinter4.addItem("COM5");
        jcboSerialPrinter4.addItem("COM6");
        jcboSerialPrinter4.addItem("COM7");
        jcboSerialPrinter4.addItem("COM8");        
        jcboSerialPrinter4.addItem("LPT1");
        jcboSerialPrinter4.addItem("/dev/ttyS0");
        jcboSerialPrinter4.addItem("/dev/ttyS1");
        jcboSerialPrinter4.addItem("/dev/ttyS2");
        jcboSerialPrinter4.addItem("/dev/ttyS3");
        jcboSerialPrinter4.addItem("/dev/ttyS4");
        jcboSerialPrinter4.addItem("/dev/ttyS5");
        
        // Printer 5
        jcboMachinePrinter5.addItem("Not defined");
        jcboMachinePrinter5.addItem("screen");
        jcboMachinePrinter5.addItem("printer");
        jcboMachinePrinter5.addItem("epson");
        jcboMachinePrinter5.addItem("tmu220");
        jcboMachinePrinter5.addItem("star");
        jcboMachinePrinter5.addItem("ODP1000");
        jcboMachinePrinter5.addItem("ithaca");
        jcboMachinePrinter5.addItem("surepos");
        jcboMachinePrinter5.addItem("plain");
        jcboMachinePrinter5.addItem("javapos");

        jcboConnPrinter5.addItem("serial");
        jcboConnPrinter5.addItem("file");

        jcboSerialPrinter5.addItem("COM1");
        jcboSerialPrinter5.addItem("COM2");
        jcboSerialPrinter5.addItem("COM3");
        jcboSerialPrinter5.addItem("COM4");
        jcboSerialPrinter5.addItem("COM5");
        jcboSerialPrinter5.addItem("COM6");
        jcboSerialPrinter5.addItem("COM7");
        jcboSerialPrinter5.addItem("COM8");        
        jcboSerialPrinter5.addItem("LPT1");
        jcboSerialPrinter5.addItem("/dev/ttyS0");
        jcboSerialPrinter5.addItem("/dev/ttyS1");
        jcboSerialPrinter5.addItem("/dev/ttyS2");
        jcboSerialPrinter5.addItem("/dev/ttyS3");
        jcboSerialPrinter5.addItem("/dev/ttyS4");
        jcboSerialPrinter5.addItem("/dev/ttyS5");
     
        // Printer 6
        jcboMachinePrinter6.addItem("Not defined");
        jcboMachinePrinter6.addItem("screen");
        jcboMachinePrinter6.addItem("printer");
        jcboMachinePrinter6.addItem("epson");
        jcboMachinePrinter6.addItem("tmu220");
        jcboMachinePrinter6.addItem("star");
        jcboMachinePrinter6.addItem("ODP1000");
        jcboMachinePrinter6.addItem("ithaca");
        jcboMachinePrinter6.addItem("surepos");
        jcboMachinePrinter6.addItem("plain");
        jcboMachinePrinter6.addItem("javapos");

        jcboConnPrinter6.addItem("serial");
        jcboConnPrinter6.addItem("file");

        jcboSerialPrinter6.addItem("COM1");
        jcboSerialPrinter6.addItem("COM2");
        jcboSerialPrinter6.addItem("COM3");
        jcboSerialPrinter6.addItem("COM4");
        jcboSerialPrinter6.addItem("COM5");
        jcboSerialPrinter6.addItem("COM6");
        jcboSerialPrinter6.addItem("COM7");
        jcboSerialPrinter6.addItem("COM8");        
        jcboSerialPrinter6.addItem("LPT1");
        jcboSerialPrinter6.addItem("/dev/ttyS0");
        jcboSerialPrinter6.addItem("/dev/ttyS1");
        jcboSerialPrinter6.addItem("/dev/ttyS2");
        jcboSerialPrinter6.addItem("/dev/ttyS3");
        jcboSerialPrinter6.addItem("/dev/ttyS4");
        jcboSerialPrinter6.addItem("/dev/ttyS5");
        
        
//
        
        // Display
        jcboMachineDisplay.addItem("Not defined");
        jcboMachineDisplay.addItem("screen");
        jcboMachineDisplay.addItem("window");
        jcboMachineDisplay.addItem("javapos");
        jcboMachineDisplay.addItem("epson");
        jcboMachineDisplay.addItem("ld200");
        jcboMachineDisplay.addItem("surepos");

        jcboConnDisplay.addItem("serial");
        jcboConnDisplay.addItem("file");

        jcboSerialDisplay.addItem("COM1");
        jcboSerialDisplay.addItem("COM2");
        jcboSerialDisplay.addItem("COM3");
        jcboSerialDisplay.addItem("COM4");
        jcboSerialDisplay.addItem("COM5");
        jcboSerialDisplay.addItem("COM6");
        jcboSerialDisplay.addItem("COM7");
        jcboSerialDisplay.addItem("COM8");        
        jcboSerialDisplay.addItem("LPT1");
        jcboSerialDisplay.addItem("/dev/ttyS0");
        jcboSerialDisplay.addItem("/dev/ttyS1");
        jcboSerialDisplay.addItem("/dev/ttyS2");
        jcboSerialDisplay.addItem("/dev/ttyS3");
        jcboSerialDisplay.addItem("/dev/ttyS4");
        jcboSerialDisplay.addItem("/dev/ttyS5");

        
        // Scale
// JG 20 Aug 13 Add Casio PD1 Scale        
        jcboMachineScale.addItem("Not defined");
        jcboMachineScale.addItem("screen");
        jcboMachineScale.addItem("casiopd1");
        jcboMachineScale.addItem("dialog1");
        jcboMachineScale.addItem("samsungesp");

        jcboSerialScale.addItem("COM1");
        jcboSerialScale.addItem("COM2");
        jcboSerialScale.addItem("COM3");
        jcboSerialScale.addItem("COM4");
        jcboSerialScale.addItem("COM5");
        jcboSerialScale.addItem("COM6");
        jcboSerialScale.addItem("COM7");
        jcboSerialScale.addItem("COM8");        
        jcboSerialScale.addItem("/dev/ttyS0");
        jcboSerialScale.addItem("/dev/ttyS1");
        jcboSerialScale.addItem("/dev/ttyS2");
        jcboSerialScale.addItem("/dev/ttyS3");
        jcboSerialScale.addItem("/dev/ttyS4");
        jcboSerialScale.addItem("/dev/ttyS5");


        // Scanner
        jcboMachineScanner.addItem("Not defined");
        jcboMachineScanner.addItem("scanpal2");

        jcboSerialScanner.addItem("COM1");
        jcboSerialScanner.addItem("COM2");
        jcboSerialScanner.addItem("COM3");
        jcboSerialScanner.addItem("COM4");
        jcboSerialScanner.addItem("COM5");
        jcboSerialScanner.addItem("COM6");
        jcboSerialScanner.addItem("COM7");
        jcboSerialScanner.addItem("COM8");        
        jcboSerialScanner.addItem("/dev/ttyS0");
        jcboSerialScanner.addItem("/dev/ttyS1");
        jcboSerialScanner.addItem("/dev/ttyS2");
        jcboSerialScanner.addItem("/dev/ttyS3");
        jcboSerialScanner.addItem("/dev/ttyS4");
        jcboSerialScanner.addItem("/dev/ttyS5");


        // Printers
        cboPrinters.addItem("(Default)");
        cboPrinters.addItem("(Show dialog)");
        for (String name : printernames) {
            cboPrinters.addItem(name);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean hasChanged() {
        return dirty.isDirty();
    }

    /**
     *
     * @return
     */
    @Override
    public Component getConfigComponent() {
        return this;
    }

    /**
     *
     * @param config
     */
    @Override
    public void loadProperties(AppConfig config) {

// JG 6 May 2013 to switch
        StringParser p = new StringParser(config.getProperty("machine.printer"));
        String sparam = unifySerialInterface(p.nextToken(':'));

        switch (sparam) {
            case "serial":
            case "file":
                jcboMachinePrinter.setSelectedItem("epson");
                jcboConnPrinter.setSelectedItem(sparam);
                jcboSerialPrinter.setSelectedItem(p.nextToken(','));
                break;
            case "javapos":
                jcboMachinePrinter.setSelectedItem(sparam);
                m_jtxtJPOSPrinter.setText(p.nextToken(','));
                m_jtxtJPOSDrawer.setText(p.nextToken(','));
                break;
            case "printer":
                jcboMachinePrinter.setSelectedItem(sparam);
                printer1printerparams.setParameters(p);
                break;
            default:
                jcboMachinePrinter.setSelectedItem(sparam);
                jcboConnPrinter.setSelectedItem(unifySerialInterface(p.nextToken(',')));
                jcboSerialPrinter.setSelectedItem(p.nextToken(','));
                break;
        }

// JG 6 May 2013 to switch
        p = new StringParser(config.getProperty("machine.printer.2"));
        sparam = unifySerialInterface(p.nextToken(':'));
        switch (sparam) {
            case "serial":
            case "file":
                jcboMachinePrinter2.setSelectedItem("epson");
                jcboConnPrinter2.setSelectedItem(sparam);
                jcboSerialPrinter2.setSelectedItem(p.nextToken(','));
                break;
            case "javapos":
                jcboMachinePrinter2.setSelectedItem(sparam);
                m_jtxtJPOSPrinter2.setText(p.nextToken(','));
                m_jtxtJPOSDrawer2.setText(p.nextToken(','));
                break;
            case "printer":
                jcboMachinePrinter2.setSelectedItem(sparam);
                printer2printerparams.setParameters(p);
                break;
            default:
                jcboMachinePrinter2.setSelectedItem(sparam);
                jcboConnPrinter2.setSelectedItem(unifySerialInterface(p.nextToken(',')));
                jcboSerialPrinter2.setSelectedItem(p.nextToken(','));
                break;
        }

// JG 6 May 2013 to switch
        p = new StringParser(config.getProperty("machine.printer.3"));
        sparam = unifySerialInterface(p.nextToken(':'));
        switch (sparam) {
            case "serial":
            case "file":
                jcboMachinePrinter3.setSelectedItem("epson");
                jcboConnPrinter3.setSelectedItem(sparam);
                jcboSerialPrinter3.setSelectedItem(p.nextToken(','));
                break;
            case "javapos":
                jcboMachinePrinter3.setSelectedItem(sparam);
                m_jtxtJPOSPrinter3.setText(p.nextToken(','));
                m_jtxtJPOSDrawer3.setText(p.nextToken(','));
                break;
            case "printer":
                jcboMachinePrinter3.setSelectedItem(sparam);
                printer3printerparams.setParameters(p);
                break;
            default:
                jcboMachinePrinter3.setSelectedItem(sparam);
                jcboConnPrinter3.setSelectedItem(unifySerialInterface(p.nextToken(',')));
                jcboSerialPrinter3.setSelectedItem(p.nextToken(','));
                break;
        }

// new printers add jdl 10.11.12
        
                p = new StringParser(config.getProperty("machine.printer.4"));
        sparam = unifySerialInterface(p.nextToken(':'));
        switch (sparam) {
            case "serial":
            case "file":
                jcboMachinePrinter4.setSelectedItem("epson");
                jcboConnPrinter4.setSelectedItem(sparam);
                jcboSerialPrinter4.setSelectedItem(p.nextToken(','));
                break;
            case "javapos":
                jcboMachinePrinter4.setSelectedItem(sparam);
                m_jtxtJPOSPrinter4.setText(p.nextToken(','));
                m_jtxtJPOSDrawer4.setText(p.nextToken(','));
                break;
            case "printer":
                jcboMachinePrinter4.setSelectedItem(sparam);
                printer4printerparams.setParameters(p);
                break;
            default:
                jcboMachinePrinter4.setSelectedItem(sparam);
                jcboConnPrinter4.setSelectedItem(unifySerialInterface(p.nextToken(',')));
                jcboSerialPrinter4.setSelectedItem(p.nextToken(','));
                break;
        }


        p = new StringParser(config.getProperty("machine.printer.5"));
        sparam = unifySerialInterface(p.nextToken(':'));
        switch (sparam) {
            case "serial":
            case "file":
                jcboMachinePrinter5.setSelectedItem("epson");
                jcboConnPrinter5.setSelectedItem(sparam);
                jcboSerialPrinter5.setSelectedItem(p.nextToken(','));
                break;
            case "javapos":
                jcboMachinePrinter5.setSelectedItem(sparam);
                m_jtxtJPOSPrinter5.setText(p.nextToken(','));
                m_jtxtJPOSDrawer5.setText(p.nextToken(','));
                break;
            case "printer":
                jcboMachinePrinter5.setSelectedItem(sparam);
                printer5printerparams.setParameters(p);
                break;
            default:
                jcboMachinePrinter5.setSelectedItem(sparam);
                jcboConnPrinter5.setSelectedItem(unifySerialInterface(p.nextToken(',')));
                jcboSerialPrinter5.setSelectedItem(p.nextToken(','));
                break;
        }


        p = new StringParser(config.getProperty("machine.printer.6"));
        sparam = unifySerialInterface(p.nextToken(':'));
        switch (sparam) {
            case "serial":
            case "file":
                jcboMachinePrinter6.setSelectedItem("epson");
                jcboConnPrinter6.setSelectedItem(sparam);
                jcboSerialPrinter6.setSelectedItem(p.nextToken(','));
                break;
            case "javapos":
                jcboMachinePrinter6.setSelectedItem(sparam);
                m_jtxtJPOSPrinter6.setText(p.nextToken(','));
                m_jtxtJPOSDrawer6.setText(p.nextToken(','));
                break;
            case "printer":
                jcboMachinePrinter6.setSelectedItem(sparam);
                printer6printerparams.setParameters(p);
                break;
            default:
                jcboMachinePrinter6.setSelectedItem(sparam);
                jcboConnPrinter6.setSelectedItem(unifySerialInterface(p.nextToken(',')));
                jcboSerialPrinter6.setSelectedItem(p.nextToken(','));
                break;
        }
        
        
        
// JG 6 May 2013 to switch        
        p = new StringParser(config.getProperty("machine.display"));
        sparam = unifySerialInterface(p.nextToken(':'));
        switch (sparam) {
            case "serial":
            case "file":
                jcboMachineDisplay.setSelectedItem("epson");
                jcboConnDisplay.setSelectedItem(sparam);
                jcboSerialDisplay.setSelectedItem(p.nextToken(','));
                break;
            case "javapos":
                jcboMachineDisplay.setSelectedItem(sparam);
                m_jtxtJPOSName.setText(p.nextToken(','));
                break;
            default:
                jcboMachineDisplay.setSelectedItem(sparam);
                jcboConnDisplay.setSelectedItem(unifySerialInterface(p.nextToken(',')));
                jcboSerialDisplay.setSelectedItem(p.nextToken(','));
                break;
        }

// JG 20 Aug 13 Add Casio PD1 Scale
        p = new StringParser(config.getProperty("machine.scale"));
        sparam = p.nextToken(':');
        jcboMachineScale.setSelectedItem(sparam);
        if ("casiopd1".equals(sparam) || "dialog1".equals(sparam) || "samsungesp".equals(sparam)) {
            jcboSerialScale.setSelectedItem(p.nextToken(','));
        }

        p = new StringParser(config.getProperty("machine.scanner"));
        sparam = p.nextToken(':');
        jcboMachineScanner.setSelectedItem(sparam);
        if ("scanpal2".equals(sparam)) {
            jcboSerialScanner.setSelectedItem(p.nextToken(','));
        }

        cboPrinters.setSelectedItem(config.getProperty("machine.printername"));


        
        dirty.setDirty(false);
    }

    /**
     *
     * @param config
     */
    @Override
    public void saveProperties(AppConfig config) {

// JG 6 May 2013 to switch
        String sMachinePrinter = comboValue(jcboMachinePrinter.getSelectedItem());
        switch (sMachinePrinter) {
            case "epson":
            case "tmu220":
            case "star":
            case "ODP1000":
            case "ithaca":
            case "surepos":
                config.setProperty("machine.printer", sMachinePrinter + ":" + comboValue(jcboConnPrinter.getSelectedItem()) + "," + comboValue(jcboSerialPrinter.getSelectedItem()));
                break;
            case "javapos":
                config.setProperty("machine.printer", sMachinePrinter + ":" + m_jtxtJPOSPrinter.getText() + "," + m_jtxtJPOSDrawer.getText());
                break;
            case "printer":
                config.setProperty("machine.printer", sMachinePrinter + ":" + printer1printerparams.getParameters());
                break;
            default:
                config.setProperty("machine.printer", sMachinePrinter);
                break;
        }
// JG 6 May 2013 to switch
        String sMachinePrinter2 = comboValue(jcboMachinePrinter2.getSelectedItem());
        switch (sMachinePrinter2) {
            case "epson":
            case "tmu220":
            case "star":
            case "ODP1000":
            case "ithaca":
            case "surepos":
                config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + comboValue(jcboConnPrinter2.getSelectedItem()) + "," + comboValue(jcboSerialPrinter2.getSelectedItem()));
                break;
            case "javapos":
                config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + m_jtxtJPOSPrinter2.getText() + "," + m_jtxtJPOSDrawer2.getText());
                break;
            case "printer":
                config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + printer2printerparams.getParameters());
                break;
            default:
                config.setProperty("machine.printer.2", sMachinePrinter2);
                break;
        }

// JG 6 May 2013 to switch
        String sMachinePrinter3 = comboValue(jcboMachinePrinter3.getSelectedItem());
        switch (sMachinePrinter3) {
            case "epson":
            case "tmu220":
            case "star":
            case "ODP1000":    
            case "ithaca":
            case "surepos":
                config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + comboValue(jcboConnPrinter3.getSelectedItem()) + "," + comboValue(jcboSerialPrinter3.getSelectedItem()));
                break;
            case "javapos":
                config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + m_jtxtJPOSPrinter3.getText() + "," + m_jtxtJPOSDrawer3.getText());
                break;
            case "printer":
                config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + printer3printerparams.getParameters());
                break;
            default:
                config.setProperty("machine.printer.3", sMachinePrinter3);
                break;
        }
// new printers added 10.11.12
                String sMachinePrinter4 = comboValue(jcboMachinePrinter4.getSelectedItem());
        switch (sMachinePrinter4) {
            case "epson":
            case "tmu220":
            case "star":
            case "ODP1000":
            case "ithaca":
            case "surepos":
                config.setProperty("machine.printer.4", sMachinePrinter4 + ":" + comboValue(jcboConnPrinter4.getSelectedItem()) + "," + comboValue(jcboSerialPrinter4.getSelectedItem()));
                break;
            case "javapos":
                config.setProperty("machine.printer.4", sMachinePrinter4 + ":" + m_jtxtJPOSPrinter4.getText() + "," + m_jtxtJPOSDrawer4.getText());
                break;
            case "printer":
                config.setProperty("machine.printer.4", sMachinePrinter4 + ":" + printer4printerparams.getParameters());
                break;
            default:
                config.setProperty("machine.printer.4", sMachinePrinter4);
                break;
        }

        String sMachinePrinter5 = comboValue(jcboMachinePrinter5.getSelectedItem());
        switch (sMachinePrinter5) {
            case "epson":
            case "tmu220":
            case "star":
            case "ODP1000":    
            case "ithaca":
            case "surepos":
                config.setProperty("machine.printer.5", sMachinePrinter5 + ":" + comboValue(jcboConnPrinter5.getSelectedItem()) + "," + comboValue(jcboSerialPrinter5.getSelectedItem()));
                break;
            case "javapos":
                config.setProperty("machine.printer.5", sMachinePrinter5 + ":" + m_jtxtJPOSPrinter5.getText() + "," + m_jtxtJPOSDrawer5.getText());
                break;
            case "printer":
                config.setProperty("machine.printer.5", sMachinePrinter5 + ":" + printer5printerparams.getParameters());
                break;
            default:
                config.setProperty("machine.printer.5", sMachinePrinter5);
                break;
        }


        String sMachinePrinter6 = comboValue(jcboMachinePrinter6.getSelectedItem());
        switch (sMachinePrinter6) {
            case "epson":
            case "tmu220":
            case "star":
            case "ODP1000":    
            case "ithaca":
            case "surepos":
                config.setProperty("machine.printer.6", sMachinePrinter6 + ":" + comboValue(jcboConnPrinter6.getSelectedItem()) + "," + comboValue(jcboSerialPrinter6.getSelectedItem()));
                break;
            case "javapos":
                config.setProperty("machine.printer.6", sMachinePrinter6 + ":" + m_jtxtJPOSPrinter6.getText() + "," + m_jtxtJPOSDrawer6.getText());
                break;
            case "printer":
                config.setProperty("machine.printer.6", sMachinePrinter6 + ":" + printer6printerparams.getParameters());
                break;
            default:
                config.setProperty("machine.printer.6", sMachinePrinter6);
                break;
        }

        
// JG 6 May 2013 to switch
        String sMachineDisplay = comboValue(jcboMachineDisplay.getSelectedItem());
        switch (sMachineDisplay) {
            case "epson":
            case "ld200":
            case "surepos":
                config.setProperty("machine.display", sMachineDisplay + ":" + comboValue(jcboConnDisplay.getSelectedItem()) + "," + comboValue(jcboSerialDisplay.getSelectedItem()));
                break;
            case "javapos":
                config.setProperty("machine.display", sMachineDisplay + ":" + m_jtxtJPOSName.getText());
                break;
            default:
                config.setProperty("machine.display", sMachineDisplay);
                break;
        }

//JG 20 Aug 2013 Add Casio PD1 Scale
        String sMachineScale = comboValue(jcboMachineScale.getSelectedItem());
        if ("casiopd1".equals(sMachineScale) || 
                "dialog1".equals(sMachineScale) || 
                "samsungesp".equals(sMachineScale)) {
            config.setProperty("machine.scale", sMachineScale + ":" + comboValue(jcboSerialScale.getSelectedItem()));
        } else {
            config.setProperty("machine.scale", sMachineScale);
        }

        // El scanner
        String sMachineScanner = comboValue(jcboMachineScanner.getSelectedItem());
        if ("scanpal2".equals(sMachineScanner)) {
            config.setProperty("machine.scanner", sMachineScanner + ":" + comboValue(jcboSerialScanner.getSelectedItem()));
        } else {
            config.setProperty("machine.scanner", sMachineScanner);
        }

        config.setProperty("machine.printername", comboValue(cboPrinters.getSelectedItem()));

        dirty.setDirty(false);
    }

    private String unifySerialInterface(String sparam) {

        // for backward compatibility
        return ("rxtx".equals(sparam))
                ? "serial"
                : sparam;
    }

    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jcboMachineDisplay = new javax.swing.JComboBox();
        jcboMachinePrinter = new javax.swing.JComboBox();
        jcboMachinePrinter2 = new javax.swing.JComboBox();
        jcboMachinePrinter3 = new javax.swing.JComboBox();
        jcboMachinePrinter4 = new javax.swing.JComboBox();
        jcboMachinePrinter5 = new javax.swing.JComboBox();
        jcboMachinePrinter6 = new javax.swing.JComboBox();
        jcboMachineScale = new javax.swing.JComboBox();
        jcboMachineScanner = new javax.swing.JComboBox();
        cboPrinters = new javax.swing.JComboBox();
        m_jDisplayParams = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jlblConnDisplay = new javax.swing.JLabel();
        jcboConnDisplay = new javax.swing.JComboBox();
        jlblDisplayPort = new javax.swing.JLabel();
        jcboSerialDisplay = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        m_jtxtJPOSName = new javax.swing.JTextField();
        m_jPrinterParams1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jlblConnPrinter = new javax.swing.JLabel();
        jcboConnPrinter = new javax.swing.JComboBox();
        jlblPrinterPort = new javax.swing.JLabel();
        jcboSerialPrinter = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        m_jtxtJPOSPrinter = new javax.swing.JTextField();
        m_jtxtJPOSDrawer = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        m_jPrinterParams2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jlblConnPrinter2 = new javax.swing.JLabel();
        jcboConnPrinter2 = new javax.swing.JComboBox();
        jlblPrinterPort2 = new javax.swing.JLabel();
        jcboSerialPrinter2 = new javax.swing.JComboBox();
        jPanel11 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter2 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        m_jPrinterParams3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jlblConnPrinter3 = new javax.swing.JLabel();
        jcboConnPrinter3 = new javax.swing.JComboBox();
        jlblPrinterPort3 = new javax.swing.JLabel();
        jcboSerialPrinter3 = new javax.swing.JComboBox();
        jPanel12 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter3 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer3 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        m_jPrinterParams4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jlblConnPrinter4 = new javax.swing.JLabel();
        jcboConnPrinter4 = new javax.swing.JComboBox();
        jlblPrinterPort6 = new javax.swing.JLabel();
        jcboSerialPrinter4 = new javax.swing.JComboBox();
        jPanel18 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter4 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer4 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        m_jPrinterParams5 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jlblConnPrinter5 = new javax.swing.JLabel();
        jcboConnPrinter5 = new javax.swing.JComboBox();
        jlblPrinterPort7 = new javax.swing.JLabel();
        jcboSerialPrinter5 = new javax.swing.JComboBox();
        jPanel22 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter5 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer5 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        m_jPrinterParams6 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jlblConnPrinter6 = new javax.swing.JLabel();
        jcboConnPrinter6 = new javax.swing.JComboBox();
        jlblPrinterPort8 = new javax.swing.JLabel();
        jcboSerialPrinter6 = new javax.swing.JComboBox();
        jPanel26 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter6 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer6 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        m_jScaleParams = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jlblPrinterPort4 = new javax.swing.JLabel();
        jcboSerialScale = new javax.swing.JComboBox();
        m_jScannerParams = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jlblPrinterPort5 = new javax.swing.JLabel();
        jcboSerialScanner = new javax.swing.JComboBox();

        setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(700, 400));

        jPanel13.setPreferredSize(new java.awt.Dimension(700, 400));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText(AppLocal.getIntString("Label.MachineDisplay")); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(110, 25));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText(AppLocal.getIntString("Label.MachinePrinter")); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(110, 25));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText(AppLocal.getIntString("Label.MachinePrinter2")); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(110, 25));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText(AppLocal.getIntString("Label.MachinePrinter3")); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(110, 25));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText(AppLocal.getIntString("Label.MachinePrinter4")); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(110, 25));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText(AppLocal.getIntString("Label.MachinePrinter5")); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(110, 25));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText(AppLocal.getIntString("Label.MachinePrinter6")); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(110, 25));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText(AppLocal.getIntString("label.scale")); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(110, 25));

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText(AppLocal.getIntString("label.scanner")); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(110, 25));

        jLabel14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel14.setText(AppLocal.getIntString("label.reportsprinter")); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(110, 25));

        jcboMachineDisplay.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachineDisplay.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachineDisplay.setPreferredSize(new java.awt.Dimension(200, 23));
        jcboMachineDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachineDisplayActionPerformed(evt);
            }
        });

        jcboMachinePrinter.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachinePrinter.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachinePrinter.setPreferredSize(new java.awt.Dimension(200, 23));
        jcboMachinePrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinterActionPerformed(evt);
            }
        });

        jcboMachinePrinter2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachinePrinter2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachinePrinter2.setPreferredSize(new java.awt.Dimension(200, 23));
        jcboMachinePrinter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter2ActionPerformed(evt);
            }
        });

        jcboMachinePrinter3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachinePrinter3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachinePrinter3.setPreferredSize(new java.awt.Dimension(200, 23));
        jcboMachinePrinter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter3ActionPerformed(evt);
            }
        });

        jcboMachinePrinter4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachinePrinter4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachinePrinter4.setPreferredSize(new java.awt.Dimension(200, 23));
        jcboMachinePrinter4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter4ActionPerformed(evt);
            }
        });

        jcboMachinePrinter5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachinePrinter5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachinePrinter5.setPreferredSize(new java.awt.Dimension(200, 23));
        jcboMachinePrinter5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter5ActionPerformed(evt);
            }
        });

        jcboMachinePrinter6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachinePrinter6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachinePrinter6.setPreferredSize(new java.awt.Dimension(200, 23));
        jcboMachinePrinter6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter6ActionPerformed(evt);
            }
        });

        jcboMachineScale.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachineScale.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachineScale.setPreferredSize(new java.awt.Dimension(200, 23));
        jcboMachineScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachineScaleActionPerformed(evt);
            }
        });

        jcboMachineScanner.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jcboMachineScanner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jcboMachineScanner.setPreferredSize(new java.awt.Dimension(200, 23));
        jcboMachineScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachineScannerActionPerformed(evt);
            }
        });

        cboPrinters.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboPrinters.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cboPrinters.setPreferredSize(new java.awt.Dimension(200, 23));

        m_jDisplayParams.setPreferredSize(new java.awt.Dimension(200, 25));
        m_jDisplayParams.setLayout(new java.awt.CardLayout());
        m_jDisplayParams.add(jPanel2, "empty");

        jlblConnDisplay.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblConnDisplay.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N
        jlblConnDisplay.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboConnDisplay.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboConnDisplay.setPreferredSize(new java.awt.Dimension(100, 23));

        jlblDisplayPort.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblDisplayPort.setText(AppLocal.getIntString("label.machinedisplayport")); // NOI18N
        jlblDisplayPort.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboSerialDisplay.setEditable(true);
        jcboSerialDisplay.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboSerialDisplay.setPreferredSize(new java.awt.Dimension(100, 23));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboConnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblDisplayPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboSerialDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblDisplayPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboSerialDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jDisplayParams.add(jPanel1, "comm");

        jLabel20.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel20.setText(AppLocal.getIntString("Label.Name")); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(50, 25));

        m_jtxtJPOSName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSName.setPreferredSize(new java.awt.Dimension(120, 25));
        m_jtxtJPOSName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jtxtJPOSNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jDisplayParams.add(jPanel3, "javapos");

        m_jPrinterParams1.setPreferredSize(new java.awt.Dimension(200, 25));
        m_jPrinterParams1.setLayout(new java.awt.CardLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(10, 25));
        m_jPrinterParams1.add(jPanel5, "empty");

        jlblConnPrinter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblConnPrinter.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N
        jlblConnPrinter.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboConnPrinter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboConnPrinter.setPreferredSize(new java.awt.Dimension(100, 23));

        jlblPrinterPort.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblPrinterPort.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N
        jlblPrinterPort.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboSerialPrinter.setEditable(true);
        jcboSerialPrinter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboSerialPrinter.setPreferredSize(new java.awt.Dimension(100, 23));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboConnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblPrinterPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboSerialPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboSerialPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams1.add(jPanel6, "comm");

        jLabel21.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel21.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(50, 25));

        m_jtxtJPOSPrinter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSPrinter.setPreferredSize(new java.awt.Dimension(120, 25));

        m_jtxtJPOSDrawer.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSDrawer.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel24.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel24.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(50, 25));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSDrawer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jtxtJPOSDrawer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams1.add(jPanel4, "javapos");

        m_jPrinterParams2.setPreferredSize(new java.awt.Dimension(200, 25));
        m_jPrinterParams2.setLayout(new java.awt.CardLayout());
        m_jPrinterParams2.add(jPanel7, "empty");

        jlblConnPrinter2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblConnPrinter2.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N
        jlblConnPrinter2.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboConnPrinter2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboConnPrinter2.setPreferredSize(new java.awt.Dimension(100, 23));

        jlblPrinterPort2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblPrinterPort2.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N
        jlblPrinterPort2.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboSerialPrinter2.setEditable(true);
        jcboSerialPrinter2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboSerialPrinter2.setPreferredSize(new java.awt.Dimension(100, 23));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboConnPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblPrinterPort2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboSerialPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboSerialPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams2.add(jPanel8, "comm");

        m_jtxtJPOSPrinter2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSPrinter2.setPreferredSize(new java.awt.Dimension(120, 25));

        m_jtxtJPOSDrawer2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSDrawer2.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel27.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel27.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N
        jLabel27.setPreferredSize(new java.awt.Dimension(50, 25));

        jLabel22.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel22.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(50, 25));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSDrawer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jtxtJPOSDrawer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams2.add(jPanel11, "javapos");

        m_jPrinterParams3.setPreferredSize(new java.awt.Dimension(200, 25));
        m_jPrinterParams3.setLayout(new java.awt.CardLayout());
        m_jPrinterParams3.add(jPanel9, "empty");

        jlblConnPrinter3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblConnPrinter3.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N
        jlblConnPrinter3.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboConnPrinter3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboConnPrinter3.setPreferredSize(new java.awt.Dimension(100, 23));

        jlblPrinterPort3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblPrinterPort3.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N
        jlblPrinterPort3.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboSerialPrinter3.setEditable(true);
        jcboSerialPrinter3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboSerialPrinter3.setPreferredSize(new java.awt.Dimension(100, 23));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboConnPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblPrinterPort3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboSerialPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboSerialPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams3.add(jPanel10, "comm");

        m_jtxtJPOSPrinter3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSPrinter3.setPreferredSize(new java.awt.Dimension(120, 25));

        m_jtxtJPOSDrawer3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSDrawer3.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel28.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel28.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N
        jLabel28.setPreferredSize(new java.awt.Dimension(50, 25));

        jLabel23.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel23.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(50, 25));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSDrawer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jtxtJPOSDrawer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams3.add(jPanel12, "javapos");

        m_jPrinterParams4.setPreferredSize(new java.awt.Dimension(200, 25));
        m_jPrinterParams4.setLayout(new java.awt.CardLayout());
        m_jPrinterParams4.add(jPanel14, "empty");

        jlblConnPrinter4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblConnPrinter4.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N
        jlblConnPrinter4.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboConnPrinter4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboConnPrinter4.setPreferredSize(new java.awt.Dimension(100, 23));

        jlblPrinterPort6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblPrinterPort6.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N
        jlblPrinterPort6.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboSerialPrinter4.setEditable(true);
        jcboSerialPrinter4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboSerialPrinter4.setPreferredSize(new java.awt.Dimension(100, 23));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboConnPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblPrinterPort6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboSerialPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboSerialPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams4.add(jPanel15, "comm");

        m_jtxtJPOSPrinter4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSPrinter4.setPreferredSize(new java.awt.Dimension(120, 25));

        m_jtxtJPOSDrawer4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSDrawer4.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel30.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel30.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N
        jLabel30.setPreferredSize(new java.awt.Dimension(50, 25));

        jLabel31.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel31.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N
        jLabel31.setPreferredSize(new java.awt.Dimension(50, 25));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSDrawer4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jtxtJPOSDrawer4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams4.add(jPanel18, "javapos");

        m_jPrinterParams5.setPreferredSize(new java.awt.Dimension(200, 25));
        m_jPrinterParams5.setLayout(new java.awt.CardLayout());
        m_jPrinterParams5.add(jPanel20, "empty");

        jlblConnPrinter5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblConnPrinter5.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N
        jlblConnPrinter5.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboConnPrinter5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboConnPrinter5.setPreferredSize(new java.awt.Dimension(100, 23));

        jlblPrinterPort7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblPrinterPort7.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N
        jlblPrinterPort7.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboSerialPrinter5.setEditable(true);
        jcboSerialPrinter5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboSerialPrinter5.setPreferredSize(new java.awt.Dimension(100, 23));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboConnPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblPrinterPort7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboSerialPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboSerialPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams5.add(jPanel21, "comm");

        m_jtxtJPOSPrinter5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSPrinter5.setPreferredSize(new java.awt.Dimension(120, 25));

        m_jtxtJPOSDrawer5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSDrawer5.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel33.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel33.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N
        jLabel33.setPreferredSize(new java.awt.Dimension(50, 25));

        jLabel34.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel34.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N
        jLabel34.setPreferredSize(new java.awt.Dimension(50, 25));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSDrawer5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jtxtJPOSDrawer5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams5.add(jPanel22, "javapos");

        m_jPrinterParams6.setPreferredSize(new java.awt.Dimension(200, 25));
        m_jPrinterParams6.setLayout(new java.awt.CardLayout());
        m_jPrinterParams6.add(jPanel23, "empty");

        jlblConnPrinter6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblConnPrinter6.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N
        jlblConnPrinter6.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboConnPrinter6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboConnPrinter6.setPreferredSize(new java.awt.Dimension(100, 23));

        jlblPrinterPort8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblPrinterPort8.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N
        jlblPrinterPort8.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboSerialPrinter6.setEditable(true);
        jcboSerialPrinter6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboSerialPrinter6.setPreferredSize(new java.awt.Dimension(100, 23));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboConnPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblPrinterPort8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboSerialPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcboSerialPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams6.add(jPanel25, "comm");

        m_jtxtJPOSPrinter6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSPrinter6.setPreferredSize(new java.awt.Dimension(120, 25));

        m_jtxtJPOSDrawer6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        m_jtxtJPOSDrawer6.setPreferredSize(new java.awt.Dimension(120, 25));

        jLabel36.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel36.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N
        jLabel36.setPreferredSize(new java.awt.Dimension(50, 25));

        jLabel37.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel37.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N
        jLabel37.setPreferredSize(new java.awt.Dimension(50, 25));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_jtxtJPOSDrawer6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_jtxtJPOSDrawer6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jPrinterParams6.add(jPanel26, "javapos");

        m_jScaleParams.setPreferredSize(new java.awt.Dimension(200, 25));
        m_jScaleParams.setLayout(new java.awt.CardLayout());
        m_jScaleParams.add(jPanel16, "empty");

        jlblPrinterPort4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblPrinterPort4.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N
        jlblPrinterPort4.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboSerialScale.setEditable(true);
        jcboSerialScale.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboSerialScale.setPreferredSize(new java.awt.Dimension(100, 23));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblPrinterPort4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboSerialScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(224, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboSerialScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jScaleParams.add(jPanel17, "comm");

        m_jScannerParams.setPreferredSize(new java.awt.Dimension(200, 25));
        m_jScannerParams.setLayout(new java.awt.CardLayout());
        m_jScannerParams.add(jPanel24, "empty");

        jlblPrinterPort5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jlblPrinterPort5.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N
        jlblPrinterPort5.setPreferredSize(new java.awt.Dimension(50, 25));

        jcboSerialScanner.setEditable(true);
        jcboSerialScanner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcboSerialScanner.setPreferredSize(new java.awt.Dimension(100, 23));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblPrinterPort5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcboSerialScanner, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(234, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboSerialScanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        m_jScannerParams.add(jPanel19, "comm");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachinePrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachinePrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachineScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jScaleParams, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboMachineScanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboPrinters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jScannerParams, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachinePrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachinePrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachinePrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jcboMachineDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m_jDisplayParams, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jcboMachinePrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(m_jPrinterParams1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcboMachineDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jDisplayParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcboMachinePrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jPrinterParams1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcboMachinePrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jPrinterParams2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcboMachinePrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jPrinterParams3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcboMachinePrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jPrinterParams4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcboMachinePrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jPrinterParams5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcboMachinePrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jPrinterParams6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcboMachineScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jScaleParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboMachineScanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboPrinters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(m_jScannerParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jcboMachinePrinter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter3ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams3.getLayout());

        if ("epson".equals(jcboMachinePrinter3.getSelectedItem()) || "ODP1000".equals(jcboMachinePrinter3.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter3.getSelectedItem()) || "star".equals(jcboMachinePrinter3.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter3.getSelectedItem()) || "surepos".equals(jcboMachinePrinter3.getSelectedItem())) {
            cl.show(m_jPrinterParams3, "comm");
        } else if ("javapos".equals(jcboMachinePrinter3.getSelectedItem())) {
            cl.show(m_jPrinterParams3, "javapos");
        } else if ("printer".equals(jcboMachinePrinter3.getSelectedItem())) {
            cl.show(m_jPrinterParams3, "printer");
        } else {
            cl.show(m_jPrinterParams3, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter3ActionPerformed

    private void jcboMachinePrinter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter2ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams2.getLayout());

        if ("epson".equals(jcboMachinePrinter2.getSelectedItem()) || "ODP1000".equals(jcboMachinePrinter2.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter2.getSelectedItem()) || "star".equals(jcboMachinePrinter2.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter2.getSelectedItem()) || "surepos".equals(jcboMachinePrinter2.getSelectedItem())) {
            cl.show(m_jPrinterParams2, "comm");
        } else if ("javapos".equals(jcboMachinePrinter2.getSelectedItem())) {
            cl.show(m_jPrinterParams2, "javapos");
        } else if ("printer".equals(jcboMachinePrinter2.getSelectedItem())) {
            cl.show(m_jPrinterParams2, "printer");
         } else {
            cl.show(m_jPrinterParams2, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter2ActionPerformed

    private void jcboMachinePrinterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinterActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams1.getLayout());

        if ("epson".equals(jcboMachinePrinter.getSelectedItem()) || "ODP1000".equals(jcboMachinePrinter.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter.getSelectedItem()) || "star".equals(jcboMachinePrinter.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter.getSelectedItem()) || "surepos".equals(jcboMachinePrinter.getSelectedItem())) {
            cl.show(m_jPrinterParams1, "comm");
        } else if ("javapos".equals(jcboMachinePrinter.getSelectedItem())) {
            cl.show(m_jPrinterParams1, "javapos");
        } else if ("printer".equals(jcboMachinePrinter.getSelectedItem())) {
            cl.show(m_jPrinterParams1, "printer");
        } else {
            cl.show(m_jPrinterParams1, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinterActionPerformed

    private void jcboMachinePrinter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter4ActionPerformed
       CardLayout cl = (CardLayout) (m_jPrinterParams4.getLayout());

        if ("epson".equals(jcboMachinePrinter4.getSelectedItem()) || "ODP1000".equals(jcboMachinePrinter4.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter4.getSelectedItem()) || "star".equals(jcboMachinePrinter4.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter4.getSelectedItem()) || "surepos".equals(jcboMachinePrinter4.getSelectedItem())) {
            cl.show(m_jPrinterParams4, "comm");
        } else if ("javapos".equals(jcboMachinePrinter4.getSelectedItem())) {
            cl.show(m_jPrinterParams4, "javapos");
        } else if ("printer".equals(jcboMachinePrinter4.getSelectedItem())) {
            cl.show(m_jPrinterParams4, "printer");
         } else {
            cl.show(m_jPrinterParams4, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter4ActionPerformed

    private void jcboMachinePrinter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter5ActionPerformed
       CardLayout cl = (CardLayout) (m_jPrinterParams5.getLayout());

        if ("epson".equals(jcboMachinePrinter5.getSelectedItem()) || "ODP1000".equals(jcboMachinePrinter5.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter5.getSelectedItem()) || "star".equals(jcboMachinePrinter5.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter5.getSelectedItem()) || "surepos".equals(jcboMachinePrinter5.getSelectedItem())) {
            cl.show(m_jPrinterParams5, "comm");
        } else if ("javapos".equals(jcboMachinePrinter5.getSelectedItem())) {
            cl.show(m_jPrinterParams5, "javapos");
        } else if ("printer".equals(jcboMachinePrinter5.getSelectedItem())) {
            cl.show(m_jPrinterParams5, "printer");
         } else {
            cl.show(m_jPrinterParams5, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter5ActionPerformed

    private void jcboMachinePrinter6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter6ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams6.getLayout());

        if ("epson".equals(jcboMachinePrinter6.getSelectedItem()) || "ODP1000".equals(jcboMachinePrinter6.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter6.getSelectedItem()) || "star".equals(jcboMachinePrinter6.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter6.getSelectedItem()) || "surepos".equals(jcboMachinePrinter6.getSelectedItem())) {
            cl.show(m_jPrinterParams6, "comm");
        } else if ("javapos".equals(jcboMachinePrinter6.getSelectedItem())) {
            cl.show(m_jPrinterParams6, "javapos");
        } else if ("printer".equals(jcboMachinePrinter6.getSelectedItem())) {
            cl.show(m_jPrinterParams6, "printer");
         } else {
            cl.show(m_jPrinterParams6, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter6ActionPerformed

    private void m_jtxtJPOSNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jtxtJPOSNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_m_jtxtJPOSNameActionPerformed

    private void jcboMachineScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachineScannerActionPerformed
        CardLayout cl = (CardLayout) (m_jScannerParams.getLayout());

        if ("scanpal2".equals(jcboMachineScanner.getSelectedItem())) {
            cl.show(m_jScannerParams, "comm");
        } else {
            cl.show(m_jScannerParams, "empty");
        }
    }//GEN-LAST:event_jcboMachineScannerActionPerformed

    private void jcboMachineScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachineScaleActionPerformed
        CardLayout cl = (CardLayout) (m_jScaleParams.getLayout());

        // JG 29 Aug 13 - Add Casio PD1 Scale
        if ("casiopd1".equals(jcboMachineScale.getSelectedItem()) ||
            "dialog1".equals(jcboMachineScale.getSelectedItem()) ||
            "samsungesp".equals(jcboMachineScale.getSelectedItem())) {
            cl.show(m_jScaleParams, "comm");
        } else {
            cl.show(m_jScaleParams, "empty");
        }
    }//GEN-LAST:event_jcboMachineScaleActionPerformed

    private void jcboMachineDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachineDisplayActionPerformed
        CardLayout cl = (CardLayout) (m_jDisplayParams.getLayout());

        if ("epson".equals(jcboMachineDisplay.getSelectedItem()) || "ld200".equals(jcboMachineDisplay.getSelectedItem()) || "surepos".equals(jcboMachineDisplay.getSelectedItem())) {
            cl.show(m_jDisplayParams, "comm");
        } else if ("javapos".equals(jcboMachineDisplay.getSelectedItem())) {
            cl.show(m_jDisplayParams, "javapos");
        } else {
            cl.show(m_jDisplayParams, "empty");
        }
    }//GEN-LAST:event_jcboMachineDisplayActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboPrinters;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JComboBox jcboConnDisplay;
    private javax.swing.JComboBox jcboConnPrinter;
    private javax.swing.JComboBox jcboConnPrinter2;
    private javax.swing.JComboBox jcboConnPrinter3;
    private javax.swing.JComboBox jcboConnPrinter4;
    private javax.swing.JComboBox jcboConnPrinter5;
    private javax.swing.JComboBox jcboConnPrinter6;
    private javax.swing.JComboBox jcboMachineDisplay;
    private javax.swing.JComboBox jcboMachinePrinter;
    private javax.swing.JComboBox jcboMachinePrinter2;
    private javax.swing.JComboBox jcboMachinePrinter3;
    private javax.swing.JComboBox jcboMachinePrinter4;
    private javax.swing.JComboBox jcboMachinePrinter5;
    private javax.swing.JComboBox jcboMachinePrinter6;
    private javax.swing.JComboBox jcboMachineScale;
    private javax.swing.JComboBox jcboMachineScanner;
    private javax.swing.JComboBox jcboSerialDisplay;
    private javax.swing.JComboBox jcboSerialPrinter;
    private javax.swing.JComboBox jcboSerialPrinter2;
    private javax.swing.JComboBox jcboSerialPrinter3;
    private javax.swing.JComboBox jcboSerialPrinter4;
    private javax.swing.JComboBox jcboSerialPrinter5;
    private javax.swing.JComboBox jcboSerialPrinter6;
    private javax.swing.JComboBox jcboSerialScale;
    private javax.swing.JComboBox jcboSerialScanner;
    private javax.swing.JLabel jlblConnDisplay;
    private javax.swing.JLabel jlblConnPrinter;
    private javax.swing.JLabel jlblConnPrinter2;
    private javax.swing.JLabel jlblConnPrinter3;
    private javax.swing.JLabel jlblConnPrinter4;
    private javax.swing.JLabel jlblConnPrinter5;
    private javax.swing.JLabel jlblConnPrinter6;
    private javax.swing.JLabel jlblDisplayPort;
    private javax.swing.JLabel jlblPrinterPort;
    private javax.swing.JLabel jlblPrinterPort2;
    private javax.swing.JLabel jlblPrinterPort3;
    private javax.swing.JLabel jlblPrinterPort4;
    private javax.swing.JLabel jlblPrinterPort5;
    private javax.swing.JLabel jlblPrinterPort6;
    private javax.swing.JLabel jlblPrinterPort7;
    private javax.swing.JLabel jlblPrinterPort8;
    private javax.swing.JPanel m_jDisplayParams;
    private javax.swing.JPanel m_jPrinterParams1;
    private javax.swing.JPanel m_jPrinterParams2;
    private javax.swing.JPanel m_jPrinterParams3;
    private javax.swing.JPanel m_jPrinterParams4;
    private javax.swing.JPanel m_jPrinterParams5;
    private javax.swing.JPanel m_jPrinterParams6;
    private javax.swing.JPanel m_jScaleParams;
    private javax.swing.JPanel m_jScannerParams;
    private javax.swing.JTextField m_jtxtJPOSDrawer;
    private javax.swing.JTextField m_jtxtJPOSDrawer2;
    private javax.swing.JTextField m_jtxtJPOSDrawer3;
    private javax.swing.JTextField m_jtxtJPOSDrawer4;
    private javax.swing.JTextField m_jtxtJPOSDrawer5;
    private javax.swing.JTextField m_jtxtJPOSDrawer6;
    private javax.swing.JTextField m_jtxtJPOSName;
    private javax.swing.JTextField m_jtxtJPOSPrinter;
    private javax.swing.JTextField m_jtxtJPOSPrinter2;
    private javax.swing.JTextField m_jtxtJPOSPrinter3;
    private javax.swing.JTextField m_jtxtJPOSPrinter4;
    private javax.swing.JTextField m_jtxtJPOSPrinter5;
    private javax.swing.JTextField m_jtxtJPOSPrinter6;
    // End of variables declaration//GEN-END:variables
}
