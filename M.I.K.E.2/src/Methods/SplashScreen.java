/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Methods;

/**
 *
 * @author Marcos Filho
 */
import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JWindow {

    private int duration;

    public SplashScreen(int d) {
        duration = d;
    }

    public SplashScreen() {

    }

// Este é um método simples para mostrar uma tela de apresentção
// no centro da tela durante a quantidade de tempo passada no construtor
    public void showSplash() {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);

        // Configura a posição e o tamanho da janela
        int width = 450;
        int height = 115;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        // Constrói o splash screen
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("/Images/loading.gif")));
        JLabel copyrt = new JLabel("Copyright 2020, Marcos Antonio de Almeida Filho", JLabel.CENTER);
        copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        Color oraRed = new Color(156, 20, 20, 255);
        Color gray = new Color(173, 173, 173, 255);
        content.setBorder(BorderFactory.createLineBorder(gray, 10));
        // Torna visível
        setVisible(true);

        // Espera ate que os recursos estejam carregados
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            String msg = "Erro.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        }
        setVisible(false);
    }

    public void showSplashAndExit() {
        showSplash();
//        System.exit(0);
//        SplashScreen ss = new SplashScreen();
//        ss.dispose();
    }

    public void closeSplash() {
        System.exit(0);
    }

    public static void main(String[] args) {
        // Mostra uma imagem com o título da aplicação
        SplashScreen splash = new SplashScreen(3000);
        splash.showSplashAndExit();
    }
}
