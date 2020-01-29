/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.TiposInsumoBean;
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
public class TiposInsumoDAO {
    
    public void create(TiposInsumoBean tib) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tipos_insumo (nome, responsavel, datacriacao) VALUES (?,?,?)");

            stmt.setString(1, tib.getNome());
            stmt.setString(2, tib.getResponsavel());
            stmt.setString(3, tib.getDatacriacao());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(TiposInsumoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<TiposInsumoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<TiposInsumoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tipos_insumo");
            rs = stmt.executeQuery();

            while (rs.next()) {
                TiposInsumoBean cb = new TiposInsumoBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setResponsavel(rs.getString("responsavel"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(TiposInsumoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(TiposInsumoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public List<TiposInsumoBean> readcreated(String data) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<TiposInsumoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tipos_insumo WHERE datacriacao = ?");
            stmt.setString(1, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TiposInsumoBean cb = new TiposInsumoBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setResponsavel(rs.getString("responsavel"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(TiposInsumoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(TiposInsumoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<TiposInsumoBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<TiposInsumoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tipos_insumo WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TiposInsumoBean cb = new TiposInsumoBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setResponsavel(rs.getString("responsavel"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(TiposInsumoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(TiposInsumoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public void update(TiposInsumoBean tib) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tipos_insumo SET nome = ?, responsavel = ? WHERE id = ?");

            stmt.setString(1, tib.getNome());
            stmt.setString(2, tib.getResponsavel());
            stmt.setInt(5, tib.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(TiposInsumoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void delete(TiposInsumoBean tib) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM tipos_insumo WHERE id = ?");
            stmt.setInt(1, tib.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
