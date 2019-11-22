/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.InsumosBean;
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
public class InsumosDAO {
    
    public void create(InsumosBean ib) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO insumos (codigo, descricao, tipo, estoque, datacriacao, status) VALUES (?,?,?,?,?,?)");

            stmt.setString(1, ib.getCodigo());
            stmt.setString(2, ib.getDescricao());
            stmt.setString(3, ib.getTipo());
            stmt.setDouble(4, ib.getEstoque());
            stmt.setString(5, ib.getDatacriacao());
            stmt.setString(6, ib.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar insumo!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<InsumosBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<InsumosBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos");
            rs = stmt.executeQuery();

            while (rs.next()) {
                InsumosBean ib = new InsumosBean();

                ib.setId(rs.getInt("id"));
                ib.setCodigo(rs.getString("codigo"));
                ib.setDescricao(rs.getString("descricao"));
                ib.setTipo(rs.getString("tipo"));
                ib.setEstoque(rs.getDouble("estoque"));
                ib.setDatacriacao(rs.getString("datacriacao"));
                ib.setStatus(rs.getString("status"));

                listbb.add(ib);
            }
        } catch (SQLException e) {
            Logger.getLogger(InsumosDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public List<InsumosBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<InsumosBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                InsumosBean ib = new InsumosBean();

                ib.setId(rs.getInt("id"));
                ib.setCodigo(rs.getString("codigo"));
                ib.setDescricao(rs.getString("descricao"));
                ib.setTipo(rs.getString("tipo"));
                ib.setEstoque(rs.getDouble("estoque"));
                ib.setDatacriacao(rs.getString("datacriacao"));
                ib.setStatus(rs.getString("status"));

                listbb.add(ib);
            }
        } catch (SQLException e) {
            Logger.getLogger(InsumosDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public void update(InsumosBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE bancos SET codigo = ?, descricao = ?, tipo = ?, estoque = ? WHERE id = ?");

            stmt.setString(1, bb.getCodigo());
            stmt.setString(2, bb.getDescricao());
            stmt.setString(3, bb.getTipo());
            stmt.setDouble(4, bb.getEstoque());
            stmt.setInt(5, bb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
