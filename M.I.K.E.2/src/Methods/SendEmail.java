/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

import View.arquivo.Email;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.*;

/*
 * @author Marcos Filho
 */
public class SendEmail {

    public static void Enviar() {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor
         */
        props.put("mail.smtp.host", "email-ssl.com.br");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sistema@speedcut.com.br", "Sistema271113=");
            }
        });

        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sistema@speedcut.com.br")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(Email.TxtDestinatario.getText());//"financeiro@speedcut.com.br, seucolega@hotmail.com, seuparente@yahoo.com.br"

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(Email.TxtAssunto.getText());//Assunto
            message.setText(Email.TxtMensagem.getText());
            /**
             * Método para enviar a mensagem criada*
             */
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void EnviarAviso(String destinatario, String assunto, String mensagem) {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor
         */
        props.put("mail.smtp.host", "email-ssl.com.br");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sistema@speedcut.com.br", "Sistema271113=");
            }
        });

        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sistema@speedcut.com.br")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(destinatario);//"financeiro@speedcut.com.br, seucolega@hotmail.com, seuparente@yahoo.com.br"

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);//Assunto
            message.setText(mensagem);
            /**
             * Método para enviar a mensagem criada*
             */
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void EnviarErro(String erro) throws AWTException, IOException {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor
         */
        props.put("mail.smtp.host", "email-ssl.com.br");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sistema@speedcut.com.br", "Sistema271113=");
            }
        });

        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);

        String hostname = "Unknown";

        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sistema@speedcut.com.br")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse("financeiro@speedcut.com.br");//"financeiro@speedcut.com.br, seucolega@hotmail.com, seuparente@yahoo.com.br"

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Erro");//Assunto

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Erro encontrado em " + hostname + "\n" + erro);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            File temp = File.createTempFile("screenshot", ".png");
            ImageIO.write(capture, "png", temp);
            DataSource source = new FileDataSource(temp); // ex : "C:\\test.pdf"
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("erro.png"); // ex : "test.pdf"

            multipart.addBodyPart(textBodyPart);  // add the text part
            multipart.addBodyPart(attachmentBodyPart); // add the attachment part

            message.setContent(multipart);

            /**
             * Método para enviar a mensagem criada*
             */
            Transport.send(message);

            temp.delete();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void EnviarErro2(String erro) {
//        JOptionPane.showMessageDialog(null,"Enviando e-mail para suporte.");
//        SplashScreen ss = new SplashScreen(3000);
//        ss.showSplash();
        
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor
         */
        props.put("mail.smtp.host", "email-ssl.com.br");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sistema@speedcut.com.br", "Sistema271113=");
            }
        });

        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);

        String hostname = "Unknown";

        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sistema@speedcut.com.br")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse("financeiro@speedcut.com.br");//"financeiro@speedcut.com.br, seucolega@hotmail.com, seuparente@yahoo.com.br"

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Erro");//Assunto

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Erro encontrado em " + hostname + "\n" + erro);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            File temp = File.createTempFile("screenshot", ".png");
            ImageIO.write(capture, "png", temp);
            DataSource source = new FileDataSource(temp); // ex : "C:\\test.pdf"
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("erro.png"); // ex : "test.pdf"

            multipart.addBodyPart(textBodyPart);  // add the text part
            multipart.addBodyPart(attachmentBodyPart); // add the attachment part

            message.setContent(multipart);

            /**
             * Método para enviar a mensagem criada*
             */
            Transport.send(message);

//            ss.dispose();
//            JOptionPane.showMessageDialog(null,"E-mail enviado com sucesso para suporte.");
            temp.delete();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (AWTException | IOException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
