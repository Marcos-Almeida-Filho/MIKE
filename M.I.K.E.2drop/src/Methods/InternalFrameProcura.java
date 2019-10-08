/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import View.servicos.ProcuraCliente;
import java.awt.Container;
import java.awt.Point;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author Marcos Filho
 */
public class InternalFrameProcura {
    
    public static void procuraCliente (JTextField text, String origin) {
        int l = text.getText().length();
        if (l == 1 | l == 0) {
            Container c = text.getParent().getParent();
            Point p = text.getLocation();
            ProcuraCliente t = new ProcuraCliente(origin);
            BasicInternalFrameUI bi = (BasicInternalFrameUI) t.getUI();
            bi.setNorthPane(null);
            t.setSize(text.getWidth(), t.getHeight());
            t.setLocation(p.x + 7, p.y + text.getHeight() + 7);
            c.add(t);
            t.setVisible(true);
            c.setComponentZOrder(t, 0);
            ProcuraCliente.table(text);
            text.requestFocus();
        } else {
            ProcuraCliente.table(text);
            text.requestFocus();
        }
    }
}
