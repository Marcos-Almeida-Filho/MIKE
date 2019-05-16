/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Print;

import View.servicos.OS;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class PrintOS implements Printable, ActionListener {

    public int print(Graphics g, PageFormat pf, int page) throws
            PrinterException {

        if (page > 0) {
            /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        int qtd = Integer.parseInt(OS.txtfinal.getText());
        JOptionPane.showMessageDialog(null, qtd);
        double d = qtd / 3;
        JOptionPane.showMessageDialog(null, d);
        int vezes = (int) Math.ceil(d);
        JOptionPane.showMessageDialog(null, vezes);
        int col = 1;
        int lin = 0;
        int eti = 1;
        for (int i = 0; i < qtd; i++) {
            g.drawString("Speed Cut", 100 * col, 300 + lin);
            g.drawString(OS.txtcodigo.getText(), 100 * col, 310 + lin);
            g.drawString(OS.txtnumeroos.getText(), 100 * col, 320 + lin);
            col++;
            if (eti % 3 == 0) {
                lin = lin - 50;
                col = 1;
            }
            eti++;
        }

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
            }
        }
    }
}
