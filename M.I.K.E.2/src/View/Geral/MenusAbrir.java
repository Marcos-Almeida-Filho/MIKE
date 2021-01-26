/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View.Geral;

import Connection.Session;
import DAO.GrupoDeUsuariosPermDAO;
import Methods.Telas;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class MenusAbrir {
    
    static GrupoDeUsuariosPermDAO gupd = new GrupoDeUsuariosPermDAO();
    
    public static void acessoTela(ActionEvent evt, JInternalFrame frame) {
        JMenuItem menuItem = (JMenuItem) evt.getSource();
        String menuItemName = menuItem.getName();

        if (gupd.readPerm(Session.idnivel, menuItemName)) {
            Telas.AparecerTela(frame);
        } else {
            JOptionPane.showMessageDialog(null, "Acesso não permitido.");
        }
    }
}
