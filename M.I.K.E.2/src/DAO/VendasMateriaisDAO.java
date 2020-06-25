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
 *
 * @author Marcos Filho
 */
public class VendasMateriaisDAO {

    Connection con;
    PreparedStatement stmt;
    ResultSet rs;
    List<VendasMateriaisBean> listvmb;

    /** 
     * Método para criar uma nova conexão e deixar o PreparedStatement nulo de novo.
     */
    public void conStmt() {
        con = ConnectionFactory.getConnection();
        stmt = null;
    }

    /**
     * Método para deixar o ResultSet nulo novamente e zerar a list. Chama o método conStmt() que cria uma nova conexão e deixa o PreparedStatement nulo novamente.
     */
    public void rsList() {
        conStmt();
        rs = null;
        listvmb = new ArrayList<>();
    }

    /**
     * Método para criar um novo Material de Venda.Stmt = INSERT INTO vendas_materiais (codigo, descricao, estoque, status)
     *
     * @param codigo String do código do material
     * @param descricao String da descrição do material
     * @param estoque Double contendo o estoque do material criado
     * @param status String com o status do material (Ex: "Ativo", "Desativado", etc.)
     * @param local String do Local de Armazenagem
     */
    public void create(String codigo, String descricao, double estoque, String status, String local) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO vendas_materiais (codigo, descricao, estoque, status, local) VALUES ('" + codigo + "','" + descricao + "'," + estoque + ",'" + status + "','" + local + "')");

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

    /**
     * Método para recuperar todos os materiais cadastrados no banco de dados.
     * @return List<VendasMateriasBean> com os materiais cadastrados.
     */
    public List<VendasMateriaisBean> readtodos() {

        rsList();

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
                vmb.setLocal(rs.getString("local"));

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

        rsList();

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
                vmb.setLocal(rs.getString("local"));

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

        rsList();

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
                vmb.setLocal(rs.getString("local"));

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

        rsList();

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
                vmb.setLocal(rs.getString("local"));

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

    /**
     * Método para retornar o último ID criado no banco de dados.
     * @return Integer do ID.
     */
    public int readcreated() {

        rsList();

        int lastid = 0;

        try {
            stmt = con.prepareStatement("SELECT MAX(id) AS id FROM vendas_materiais");
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

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE id = " + id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisBean vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));
                vmb.setLocal(rs.getString("local"));

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

    public void update(String codigo, String descricao, String local, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_materiais SET codigo = '" + codigo + "', descricao = '" + descricao + "' , local = '" + local + "' WHERE id = " + id);

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

        conStmt();

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

        conStmt();

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

        conStmt();

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
