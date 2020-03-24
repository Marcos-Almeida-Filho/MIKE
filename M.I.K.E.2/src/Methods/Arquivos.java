/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import View.Geral.ProcurarDocumento;
import java.awt.AWTException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Marcos Filho
 */
public class Arquivos {

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

    public static void subirArquivoFTP(String file, String fileFtp, String folderFtp) throws IOException {
        String server = "ftp.speedcut.com.br";
        int port = 21;
        String user = "speedcut";
        String pass = "cacapava2000";

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            
            InputStream inputStream;
            
            //Criar Folder
            ftpClient.makeDirectory(folderFtp);

//            ftpClient.setFileType(FTP.);
//
            // APPROACH #1: uploads first file using an InputStream
//            File firstLocalFile = new File(file);
//
//            String firstRemoteFile = fileName;
//            inputStream = new FileInputStream(firstLocalFile);
//
//            System.out.println("Start uploading first file");
//            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
//            inputStream.close();
//            if (done) {
//                System.out.println("The first file is uploaded successfully.");
//                JOptionPane.showMessageDialog(null, "Arquivo salvo no FTP com sucesso.");
//            }
            
            // APPROACH #2: uploads second file using an OutputStream
            File secondLocalFile = new File(file);
            String secondRemoteFile = fileFtp;
            inputStream = new FileInputStream(secondLocalFile);
 
            System.out.println("Start uploading second file");
            OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
            byte[] bytesIn = new byte[4096];
            int read = 0;
 
            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();
 
            boolean completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The second file is uploaded successfully.");
                JOptionPane.showMessageDialog(null, "Arquivo enviado para o FTP com sucesso.");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex);
            try {
                SendEmail.EnviarErro(ex.toString());
            } catch (AWTException ex1) {
                Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Erro!\n" + ex);
                try {
                    SendEmail.EnviarErro(ex.toString());
                } catch (AWTException ex1) {
                    Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }
    
    public static void abrirArquivoFTP (String fileFtp) {
        String server = "ftp.speedcut.com.br";
        int port = 21;
        String user = "speedcut";
        String pass = "cacapava2000";

        FTPClient ftpClient = new FTPClient();
        
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            Desktop desk = Desktop.getDesktop();
            
            desk.open(new File((String) fileFtp));
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex);
            try {
                SendEmail.EnviarErro(ex.toString());
            } catch (AWTException | IOException ex1) {
                Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Erro!\n" + ex);
                try {
                    SendEmail.EnviarErro(ex.toString());
                } catch (AWTException | IOException ex1) {
                    Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }
}
