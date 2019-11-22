/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import View.servicos.DocumentosOrcamentoServico;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class Docs {
    
    public static void open(String doc) {
        Desktop desk = Desktop.getDesktop();
            try {
                desk.open(new File(doc));
            } catch (IOException ex) {
                Logger.getLogger(DocumentosOrcamentoServico.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro ao abrir documento!");
            }
    }
    
}
