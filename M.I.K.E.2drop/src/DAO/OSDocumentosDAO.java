/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OSDocumentosBean;
import Connection.ConnectionFactory;
import java.awt.HeadlessException;
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
public class OSDocumentosDAO {

    public void create(OSDocumentosBean sodb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO os_documentos (idos, descricao, local) VALUES (?,?,?)");
            stmt.setString(1, sodb.getIdos());
            stmt.setString(2, sodb.getDesc());
            stmt.setString(3, sodb.getLocal());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<OSDocumentosBean> readitens(String idos) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSDocumentosBean> listios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os_documentos WHERE idos = ?");
            stmt.setString(1, idos);

            rs = stmt.executeQuery();

            while (rs.next()) {
                OSDocumentosBean iosb = new OSDocumentosBean();

                iosb.setId(rs.getInt("id"));
                iosb.setIdos(rs.getString("idos"));
                iosb.setDesc(rs.getString("descricao"));
                iosb.setLocal(rs.getString("local"));

                listios.add(iosb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listios;
    }

    public void update(OSDocumentosBean sodb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os_documentos SET idos = ?, descricao = ?, local = ? WHERE id = ?");
            stmt.setString(1, sodb.getIdos());
            stmt.setString(2, sodb.getDesc());
            stmt.setString(3, sodb.getLocal());
            stmt.setInt(4, sodb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(OSDocumentosBean sodb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM os_documentos WHERE id = ?");
            stmt.setInt(1, sodb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
