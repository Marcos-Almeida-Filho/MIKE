/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import View.TelaPrincipal;
import View.administracao.Relatorios;
import java.awt.Dimension;
import java.beans.PropertyVetoException;
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
    
//    public static void AparecerJWindow(JFrame frame) {
////        TelaPrincipal.jDesktopPane1.add(frame);
////        Dimension jif = frame.getSize();
////        Dimension d = TelaPrincipal.jDesktopPane1.getSize();
////        frame.setLocation((d.width - jif.width) / 2, (d.height - jif.height) / 2);
//        frame.setVisible(true);
//        frame.setUndecorated(true);
//    }
    
    public static void AparecerTelaAumentada(JInternalFrame frame) {
        try {
            TelaPrincipal.jDesktopPane1.add(frame);
            Dimension desktopsize = TelaPrincipal.jDesktopPane1.getSize();
            Dimension jinternalframesize = frame.getSize();
            frame.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            frame.setVisible(true);
            frame.setMaximum(true);
        } catch (PropertyVetoException e) {
            String msg = "Erro.";
            
            JOptionPane.showMessageDialog(null, msg + "\n" + e);
            
            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        }
    }
    
    public static void AbrirRelatorio(JInternalFrame frame) {
        int comp = Relatorios.jDesktopPane1.getComponentCount();
        if (comp > 0) {
            Relatorios.jDesktopPane1.removeAll();
        }
        try {
            Relatorios.jDesktopPane1.add(frame);
            Dimension desktopsize = TelaPrincipal.jDesktopPane1.getSize();
            Dimension jinternalframesize = frame.getSize();
            frame.setLocation((desktopsize.width - jinternalframesize.width) / 2, (desktopsize.height - jinternalframesize.height) / 2);
            frame.setVisible(true);
            frame.setMaximum(true);
        } catch (PropertyVetoException e) {
            String msg = "Erro.";
            
            JOptionPane.showMessageDialog(null, msg + "\n" + e);
            
            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        }
    }
}
