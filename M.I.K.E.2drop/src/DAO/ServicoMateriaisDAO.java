/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoMateriaisBean;
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
public class ServicoMateriaisDAO {

    public void create(ServicoMateriaisBean smb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_materiais (codigo, descricao, estoque, grupo_de_processos, data) VALUES (?,?,?,?,?)");
            stmt.setString(1, smb.getCodigo());
            stmt.setString(2, smb.getDescricao());
            stmt.setInt(3, smb.getEstoque());
            stmt.setString(4, smb.getGrupo_de_processos());
            stmt.setString(5, smb.getData());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoMateriaisBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoMateriaisBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_materiais");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoMateriaisBean sob = new ServicoMateriaisBean();

                sob.setId(rs.getInt("id"));
                sob.setCodigo(rs.getString("codigo"));
                sob.setDescricao(rs.getString("descricao"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }
    
    public List<ServicoMateriaisBean> readid(String codigo) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoMateriaisBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT id FROM servicos_materiais WHERE codigo = ?");
            stmt.setString(1, codigo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoMateriaisBean sob = new ServicoMateriaisBean();

                sob.setId(rs.getInt("id"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }
    
    public List<ServicoMateriaisBean> readgrupo(String codigo) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoMateriaisBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT grupo_de_processos FROM servicos_materiais WHERE codigo = ?");
            stmt.setString(1, codigo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoMateriaisBean sob = new ServicoMateriaisBean();

                sob.setGrupo_de_processos(rs.getString("grupo_de_processos"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }
    
    public List<ServicoMateriaisBean> readcreated(String nome, String data) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoMateriaisBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_materiais WHERE codigo = ? AND data = ?");
            stmt.setString(1, nome);
            stmt.setString(2, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoMateriaisBean sob = new ServicoMateriaisBean();

                sob.setId(rs.getInt("id"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }
    
    public List<ServicoMateriaisBean> readestoque(int idmaterial) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoMateriaisBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT estoque FROM servicos_materiais WHERE id = ?");
            stmt.setInt(1, idmaterial);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoMateriaisBean sob = new ServicoMateriaisBean();

                sob.setEstoque(rs.getInt("estoque"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }

    public List<ServicoMateriaisBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoMateriaisBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_materiais WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoMateriaisBean sob = new ServicoMateriaisBean();

                sob.setId(rs.getInt("id"));
                sob.setCodigo(rs.getString("codigo"));
                sob.setDescricao(rs.getString("descricao"));
                sob.setEstoque(rs.getInt("estoque"));
                sob.setGrupo_de_processos(rs.getString("grupo_de_processos"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }
    
    public List<ServicoMateriaisBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoMateriaisBean> listub = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_materiais WHERE id LIKE '%" + pesquisa + "%' OR codigo LIKE '%" + pesquisa + "%' OR descricao LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoMateriaisBean ub = new ServicoMateriaisBean();

                ub.setId(rs.getInt("id"));
                ub.setCodigo(rs.getString("codigo"));
                ub.setDescricao(rs.getString("descricao"));

                listub.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listub;
    }

    public void update(ServicoMateriaisBean sob) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_materiais SET codigo = ?, descricao = ?, estoque = ?, grupo_de_processos = ? WHERE id = ?");
            stmt.setString(1, sob.getCodigo());
            stmt.setString(2, sob.getDescricao());
            stmt.setInt(3, sob.getEstoque());
            stmt.setString(4, sob.getGrupo_de_processos());
            stmt.setInt(5, sob.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updateestoque(ServicoMateriaisBean sob) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_materiais SET estoque = ? WHERE id = ?");
            stmt.setInt(1, sob.getEstoque());
            stmt.setInt(2, sob.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
