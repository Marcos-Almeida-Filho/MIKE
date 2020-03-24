/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.RastreamentoDocumentosDocBean;
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
public class RastreamentoDocumentosDocDAO {

    public void create(RastreamentoDocumentosDocBean rddb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO rastreamento_doc_doc (iddoc, descricao, local, localremoto) VALUES (?,?,?,?)");
            stmt.setInt(1, rddb.getIddoc());
            stmt.setString(2, rddb.getDescricao());
            stmt.setString(3, rddb.getLocal());
            stmt.setString(4, rddb.getLocalremoto());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDocDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao criar Documento do Rastreamento de Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao criar cliente!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<RastreamentoDocumentosDocBean> read(int iddoc) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<RastreamentoDocumentosDocBean> listrddb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc_doc WHERE iddoc = ?");
            stmt.setInt(1, iddoc);

            rs = stmt.executeQuery();

            while (rs.next()) {
                RastreamentoDocumentosDocBean rddb = new RastreamentoDocumentosDocBean();

                rddb.setId(rs.getInt("id"));
                rddb.setDescricao(rs.getString("descricao"));
                rddb.setLocal(rs.getString("local"));
                rddb.setLocalremoto(rs.getString("localremoto"));

                listrddb.add(rddb);
            }
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDocDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao ler Documento do Rastreamento de Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao ler Documento do Rastreamento de Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listrddb;
    }
}
