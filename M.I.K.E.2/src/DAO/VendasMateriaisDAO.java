/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasMateriaisBean;
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
public class VendasMateriaisDAO {

    public void create(VendasMateriaisBean vmb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO vendas_materiais (codigo, descricao, estoque, status) VALUES (?,?,?,?)");

            stmt.setString(1, vmb.getCodigo());
            stmt.setString(2, vmb.getDescricao());
            stmt.setDouble(3, vmb.getEstoque());
            stmt.setString(4, vmb.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar Material!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<VendasMateriaisBean> readtodos() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<VendasMateriaisBean> listvmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais");
            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisBean vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listvmb;
    }
    
    public List<VendasMateriaisBean> readTodosPesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<VendasMateriaisBean> listvmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE codigo LIKE '%" + pesquisa + "%' OR descricao LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisBean vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listvmb;
    }
    
    public List<VendasMateriaisBean> readStatus(String status) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<VendasMateriaisBean> listvmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE status = " + status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisBean vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listvmb;
    }
    
    public List<VendasMateriaisBean> readStatusPesquisa(String status, String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<VendasMateriaisBean> listvmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE status = " + status + " AND codigo LIKE '%" + pesquisa + "%' OR status = " + pesquisa + " AND descricao LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisBean vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listvmb;
    }
    
    public int readcreated(String data) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        int lastid = 0;

        try {
            stmt = con.prepareStatement("SELECT MAX(id) AS id FROM vendas_materiais");
            stmt.setString(1, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                lastid = rs.getInt("id");
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return lastid;
    }

    public List<VendasMateriaisBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<VendasMateriaisBean> listvmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisBean vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listvmb;
    }

    public void update(VendasMateriaisBean vmb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE vendas_materiais SET codigo = ?, descricao = ? WHERE id = ?");

            stmt.setString(1, vmb.getCodigo());
            stmt.setString(2, vmb.getDescricao());
            stmt.setInt(3, vmb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar CAP!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updateStatus(VendasMateriaisBean vmb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE vendas_materiais SET status = ? WHERE id = ?");

            stmt.setString(1, vmb.getStatus());
            stmt.setInt(2, vmb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar CAP!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updateEstoque(VendasMateriaisBean vmb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE vendas_materiais SET estoque = ? WHERE id = ?");

            stmt.setDouble(1, vmb.getEstoque());
            stmt.setInt(2, vmb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar CAP!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void delete(VendasMateriaisBean cb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_materiais WHERE id = ?");
            stmt.setInt(1, cb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
