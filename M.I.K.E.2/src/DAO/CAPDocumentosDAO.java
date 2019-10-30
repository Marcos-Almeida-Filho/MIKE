/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CAPDocumentosBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.awt.AWTException;
import java.awt.HeadlessException;
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
public class CAPDocumentosDAO {

    public void create(CAPDocumentosBean spdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO cap_documentos (idcap, descricao, local) VALUES (?,?,?)");
            stmt.setInt(1, spdb.getIdcap());
            stmt.setString(2, spdb.getDescricao());
            stmt.setString(3, spdb.getLocal());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar os documentos do pedido!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<CAPDocumentosBean> readitens(int idcap) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CAPDocumentosBean> listios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM cap_documentos WHERE idcap = ?");
            stmt.setInt(1, idcap);

            rs = stmt.executeQuery();

            while (rs.next()) {
                CAPDocumentosBean iosb = new CAPDocumentosBean();

                iosb.setIdcap(rs.getInt("idcap"));
                iosb.setDescricao(rs.getString("descricao"));
                iosb.setLocal(rs.getString("local"));

                listios.add(iosb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listios;
    }

    public void update(CAPDocumentosBean spdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE cap_documentos SET descricao = ?, local = ? WHERE idtela = ?");
            stmt.setString(1, spdb.getDescricao());
            stmt.setString(2, spdb.getLocal());
            stmt.setInt(3, spdb.getIdcap());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(CAPDocumentosBean spdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM cap_documentos WHERE id = ?");
            stmt.setInt(1, spdb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
