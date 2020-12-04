/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasMateriaisDocBean;
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
public class VendasMateriaisDocDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<VendasMateriaisDocBean> listvmdb;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listvmdb = new ArrayList<>();
    }

    /**
     * Método para criar documentos do material de Vendas.
     *
     * @param idmaterial
     * @param descricao
     * @param local
     * @throws java.sql.SQLException
     */
    public void create(int idmaterial, String descricao, String local) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_materiais_doc (idmaterial, descricao, local) VALUES (" + idmaterial + ",'" + descricao + "',?)");
        stmt.setString(1, local);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<VendasMateriaisDocBean> read(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais_doc WHERE idmaterial = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisDocBean vmdb = new VendasMateriaisDocBean();

                vmdb.setId(rs.getInt("id"));
                vmdb.setDescricao(rs.getString("descricao"));
                vmdb.setLocal(rs.getString("local"));

                listvmdb.add(vmdb);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler documentos.");
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listvmdb;
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_materiais_doc WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir documento.");
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
