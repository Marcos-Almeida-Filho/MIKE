/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import Connection.Session;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class Obs {
    
    public static void AdicionarObs (JTable table, String obs) {
        DefaultTableModel modelobs = (DefaultTableModel) table.getModel();
        modelobs.addRow(new Object[]{
            "",
            Dates.CriarDataCurta(),
            Session.nome,
            obs
        });
    }
}
