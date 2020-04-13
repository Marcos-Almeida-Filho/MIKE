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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.net.ftp.FTPClient;

/**
 * Classe com métodos para criar ou abrir arquivos.
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

    /**
     * Método para criar arquivos em rede e em servidor FTP.
     *
     * @param origem Classe que está chamando o método. Modificará a pasta na
     * qual será salvo o arquivo.
     * @param file O arquivo que será enviado.
     * @param numero Número do Pedido/OS/OP/Cotação para que seja armazenado
     * corretamente.
     */
    public void criarArquivosEmGeral(String origem, String file, int numero) {
        Arquivos a = new Arquivos();
//        a.subirArquivoFTP(origem, file, numero);
//        a.criarArquivoEmRede(origem, file, numero);
    }

    /**
     * Método para colocar arquivos locais no servidor FTP.
     *
     * @param fileFtp
     * @param origem Qual a classe que está requerendo o método. Será colocado
     * em pastas diferentes no servidor FTP dependendo da classe.
     * @param file O arquivo que está sendo enviado para o servidor FTP.
     * @param numero Número para organizar onde colocar o arquivo. Ex: O arquivo
     * é da OS 1000. O número deverá ser 1000.
     *
     */
    /*public static void subirArquivoFTP(String origem, String file, int numero) {
        String server = "ftp.speedcut.com.br";
        int port = 21;
        String user = "speedcut";
        String pass = "cacapava2000";
        String folderFtp = "";

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            InputStream inputStream;

            switch (origem) {
                case "OS1":
                    folderFtp = "MIKE_ERP/os_arq/" + numero;
                    break;
                case "OS":
                    folderFtp = "MIKE_ERP/os_arq/" + numero;
                    break;
                case "RastreamentoDocumentos":
                    folderFtp = "MIKE_ERP/ras_doq_arq/" + numero;
                    break;
            }

            //Criar Folder
            ftpClient.makeDirectory(folderFtp);

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);

//             APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(file);

            String firstRemoteFile = folderFtp + "/" + firstLocalFile.getName();
            inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
                JOptionPane.showMessageDialog(null, "Arquivo salvo no servidor FTP com sucesso.");
            }
//            //APPROACH #2: uploads second file using an OutputStream
//            File secondLocalFile = new File(file);
//            String secondRemoteFile = folderFtp + "/" + secondLocalFile.getName();
//            inputStream = new FileInputStream(secondLocalFile);
//
//            System.out.println("Start uploading second file");
//            OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
//            byte[] bytesIn = new byte[4096];
//            int read = 0;
//
//            while ((read = inputStream.read(bytesIn)) != -1) {
//                outputStream.write(bytesIn, 0, read);
//            }
//            inputStream.close();
//            outputStream.close();
//
//            boolean completed = ftpClient.completePendingCommand();
//            if (completed) {
//                System.out.println("The second file is uploaded successfully.");
//                JOptionPane.showMessageDialog(null, "Arquivo enviado para o FTP com sucesso.");
//            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Erro!\n" + ex);
            try {
                try {
                    SendEmail.EnviarErro(ex.toString());
                } catch (IOException ex1) {
                    Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex1);
                }
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
                JOptionPane.showMessageDialog(null, "Erro!\n" + ex);
                try {
                    try {
                        SendEmail.EnviarErro(ex.toString());
                    } catch (IOException ex1) {
                        Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (AWTException ex1) {
                    Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }*/
    public static void abrirArquivoFTP(String fileFtp) {
        String server = "ftp.speedcut.com.br";
        int port = 21;
        String user = "speedcut";
        String pass = "cacapava2000";

        FTPClient ftpClient = new FTPClient();
        File file = new File(fileFtp);
        File home = FileSystemView.getFileSystemView().getHomeDirectory();
        String fosString = home.getAbsoluteFile() + "/" + file.getName();
        File fosFile = new File(fosString);
        FileOutputStream fos;

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            fos = new FileOutputStream(fosFile);

//            ftpClient.retrieveFile(fileFtp, fos);
            ftpClient.retrieveFileStream(fileFtp);
//            JOptionPane.showMessageDialog(null, ftpClient.retrieveFile(fileFtp, fos));
//            JOptionPane.showMessageDialog(null, ftpClient.retrieveFileStream(fileFtp));
//            ftpClient.completePendingCommand();

            Desktop desk = Desktop.getDesktop();

            desk.open(fosFile);
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
                JOptionPane.showMessageDialog(null, "Erro!\n" + ex);
                try {
                    SendEmail.EnviarErro(ex.toString());
                } catch (AWTException | IOException ex1) {
                    Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    /*public static void criarArquivoEmRede(String origem, String file, int numero) {
        File fileoriginal = new File(file);
        File folder = new File("");

        switch (origem) {
            case "OS":
                folder = new File("Q:/MIKE_ERP/os_arq/" + numero);
                break;
            case "OS1":
                folder = new File("Q:/MIKE_ERP/os_arq/" + numero);
                break;
            case "RastreamentoDocumentos":
                folder = new File("Q:/MIKE_ERP/ras_doc_arq/" + numero);
                break;
        }
        File filecopy = new File(folder + "/" + fileoriginal.getName());

        folder.mkdirs();
        try {
            Files.copy(fileoriginal.toPath(), filecopy.toPath(), COPY_ATTRIBUTES);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + ex + "\nEnviando e-mail para suporte.");
            try {
                SendEmail.EnviarErro(ex.toString());
                JOptionPane.showMessageDialog(null, "E-mail com erro enviado com sucesso!");
            } catch (HeadlessException hex) {
                JOptionPane.showMessageDialog(null, "Erro!\n" + hex);
            } catch (AWTException | IOException ex1) {
                JOptionPane.showMessageDialog(null, "Erro!\n" + ex1 + "\nEnviando email para suporte.");
                try {
                    SendEmail.EnviarErro(ex1.toString());
                } catch (AWTException | IOException ex2) {
                    Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex2);
                }
            }
        }
    }*/
    public static String localArquivoEmRede(String origem, String file, int numero) {
        String local = "";
        File fileoriginal = new File(file);

        switch (origem) {
            case "OS1":
                local = "Q:/MIKE_ERP/os_arq/" + numero + "/" + fileoriginal.getName();
                break;
            case "RastreamentoDocumentos":
                local = "Q:/MIKE_ERP/ras_doc_arq/" + numero + "/" + fileoriginal.getName();
                break;
        }

        return local;
    }

    public static String localArquivoFTP(String origem, String file, int numero) {
        String local = "";
        File fileoriginal = new File(file);

        switch (origem) {
            case "OS1":
                local = "ftp://ftp.speedcut.com.br/MIKE_ERP/os_arq/" + numero + "/" + fileoriginal.getName();
                break;
            case "OS":
                local = "ftp://ftp.speedcut.com.br/MIKE_ERP/os_arq/" + numero + "/" + fileoriginal.getName();
                break;
            case "RastreamentoDocumentos":
                local = "ftp://ftp.speedcut.com.br/MIKE_ERP/ras_doc_arq/" + numero + "/" + fileoriginal.getName();
                break;
        }

        return local;
    }
}
