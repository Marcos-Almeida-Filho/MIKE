/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Methods;

import java.awt.Color;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class Buttons {
    
    public static void btnexcluir (JInternalFrame internalframe) {
        int comp = internalframe.getComponentCount();
        JOptionPane.showMessageDialog(null, comp);
        for (int i = 0; i < comp; i++) {
            JOptionPane.showMessageDialog(null, internalframe.getComponent(i).getName());
//            if (internalframe.getComponent(i).getName().equals("btnexcluir")) {
//                internalframe.getComponent(i).setBackground(Color.red);
//            }
        }
    }
    
}
