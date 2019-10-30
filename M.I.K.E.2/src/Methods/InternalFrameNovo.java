/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Methods;

import static View.TelaPrincipal.jDesktopPane1;
import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author Marcos Filho
 */
public class InternalFrameNovo {
    public static void NovoInternalFrame(JInternalFrame p){
        JDesktopPane desk = p.getDesktopPane();
        desk.add(p);
        Dimension desktopsize = jDesktopPane1.getSize();
        Dimension jinternalframesize = p.getSize();
        p.setLocation((desktopsize.width - jinternalframesize.width)/2,(desktopsize.height- jinternalframesize.height)/2);
        p.setVisible(true); 
    }
}
