/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import View.Geral.ProcurarDocumento;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcos Filho
 */
public class Files {

    public static void AdicionarArquivoEmTable(JTable table, String fileoriginal, JInternalFrame jif) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{
            "",
            false,
            ProcurarDocumento.txtdescarquivo.getText(),
            "",
            fileoriginal
        });
        jif.dispose();
        JOptionPane.showMessageDialog(null, "Incluído com sucesso!");
    }
}
