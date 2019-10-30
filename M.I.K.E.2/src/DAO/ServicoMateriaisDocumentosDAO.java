/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoMateriaisDocumentosBean;
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
public class ServicoMateriaisDocumentosDAO {

    public void create(ServicoMateriaisDocumentosBean smdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_materiais_documentos (idmaterial, descricao, local) VALUES (?,?,?)");
            stmt.setInt(1, smdb.getIdmaterial());
            stmt.setString(2, smdb.getDescricao());
            stmt.setString(3, smdb.getLocal());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoMateriaisDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoMateriaisDocumentosBean> read(int idmaterial) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoMateriaisDocumentosBean> listmd = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_materiais_documentos WHERE idmaterial = ?");
            stmt.setInt(1, idmaterial);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoMateriaisDocumentosBean smdb = new ServicoMateriaisDocumentosBean();

                smdb.setId(rs.getInt("id"));
                smdb.setIdmaterial(rs.getInt("idmaterial"));
                smdb.setDescricao(rs.getString("descricao"));
                smdb.setLocal(rs.getString("local"));

                listmd.add(smdb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoMateriaisDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listmd;

    }

    public void update(ServicoMateriaisDocumentosBean sob) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_materiais_documentos SET idmaterial = ?, descricao = ?, local = ? WHERE id = ?");
            stmt.setInt(1, sob.getIdmaterial());
            stmt.setString(2, sob.getDescricao());
            stmt.setString(3, sob.getLocal());
            stmt.setInt(4, sob.getId());

            stmt.executeUpdate();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoMateriaisDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void delete(ServicoMateriaisDocumentosBean sodb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM servicos_materiais_documentos WHERE id = ?");
            stmt.setInt(1, sodb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoMateriaisDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
