/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.util;

import javax.swing.*;
import java.awt.event.*;

// this is a dialog that will dispose of itself after a given amount of time
public class Test extends JDialog {

private int lifeTime = 0;

// if lifeTime is set to zero, this behaves like a normal dialog
public void setLifeTime(int millis) {
lifeTime = millis;
}

public void setVisible(boolean b) {
System.out.println("setVisible(" + b + ")");
super.setVisible(b);
if(b && lifeTime != 0) {
Action disposeAction = new AbstractAction() {
public void actionPerformed(ActionEvent e) {
System.out.println("disposing");
dispose();
}
};
Timer t = new Timer(lifeTime, disposeAction);
t.setRepeats(false);
t.start();
}
}
}

