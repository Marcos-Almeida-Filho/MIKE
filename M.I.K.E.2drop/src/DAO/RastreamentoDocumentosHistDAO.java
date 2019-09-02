/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.RastreamentoDocumentosHistBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class RastreamentoDocumentosHistDAO {

    public void create(RastreamentoDocumentosHistBean rdhb) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO rastreamento_doc_hist (iddoc, acao, user, data) VALUES (?,?,?,?)");
            stmt.setInt(1, rdhb.getIddoc());
            stmt.setString(2, rdhb.getAcao());
            stmt.setString(3, rdhb.getUser());
            stmt.setString(4, rdhb.getData());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosHistDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao criar Histórico de Rastreamento de Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao criar Histórico de Rastreamento de Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosHistDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<RastreamentoDocumentosHistBean> read(int iddoc) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<RastreamentoDocumentosHistBean> listrdhb = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc_hist WHERE iddoc = ?");
            stmt.setInt(1, iddoc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RastreamentoDocumentosHistBean rdhb = new RastreamentoDocumentosHistBean();

                rdhb.setAcao(rs.getString("acao"));
                rdhb.setUser(rs.getString("user"));
                rdhb.setData(rs.getString("data"));

                listrdhb.add(rdhb);
            }
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosHistDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao ler Histórico de Rastreamento de Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao ler Histórico de Rastreamento de Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosHistDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listrdhb;
    }
}
