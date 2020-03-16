/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import View.TelaPrincipal;
import java.awt.AWTException;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class Telas {
    
    public static void AparecerTela(JInternalFrame frame) {
        TelaPrincipal.jDesktopPane1.add(frame);
        Dimension jif = frame.getSize();
        Dimension d = TelaPrincipal.jDesktopPane1.getSize();
        frame.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
        frame.setVisible(true);
    }
    
    public static void AparecerTelaAumentada(JInternalFrame frame) {
        try {
            TelaPrincipal.jDesktopPane1.add(frame);
            Dimension desktopsize = TelaPrincipal.jDesktopPane1.getSize();
            Dimension jinternalframesize = frame.getSize();
            frame.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            frame.setVisible(true);
            frame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
            try {
                SendEmail.EnviarErro(ex.toString());
            } catch (AWTException | IOException ex1) {
                Logger.getLogger(Telas.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
