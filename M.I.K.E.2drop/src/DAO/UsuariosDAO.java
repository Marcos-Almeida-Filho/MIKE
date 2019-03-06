/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.UsuariosBean;
import Connection.ConnectionFactory;
import View.administracao.Usuarios;
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
public class UsuariosDAO {

    public void create(UsuariosBean ub) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO usuarios (nome, emailpessoal, dataadmissao, telefonefixo, telefonecelular, datanascimento, datademissao, emailfabrica, vendedor, status, login, senha, cargo, cpf, pis, rg, livrofolha, nivel) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, ub.getNome());
            stmt.setString(2, ub.getEmailpessoal());
            stmt.setString(3, ub.getDataadmissao());
            stmt.setString(4, ub.getTelefonefixo());
            stmt.setString(5, ub.getTelefonecelular());
            stmt.setString(6, ub.getDatanascimento());
            stmt.setString(7, ub.getDatademissao());
            stmt.setString(8, ub.getEmailfabrica());
            stmt.setString(9, ub.getVendedor());
            stmt.setString(10, ub.getStatus());
            stmt.setString(11, ub.getLogin());
            stmt.setString(12, ub.getSenha());
            stmt.setString(13, ub.getCargo());
            stmt.setString(14, ub.getCpf());
            stmt.setString(15, ub.getPis());
            stmt.setString(16, ub.getRg());
            stmt.setString(17, ub.getLivrofolha());
            stmt.setString(18, ub.getNivel());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Erro ao Salvar!\nLogin já existente!\nCódigo do erro: " + e.getErrorCode());
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao Salvar!\nCódigo do erro: " + e.getErrorCode());
            }

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<UsuariosBean> readusuarioclick() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<UsuariosBean> listu = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(Usuarios.txtid.getText()));
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuariosBean ub = new UsuariosBean();

                ub.setNome(rs.getString("nome"));
                ub.setCpf(rs.getString("cpf"));
                ub.setRg(rs.getString("rg"));
                ub.setDatanascimento(rs.getString("datanascimento"));
                ub.setEmailpessoal(rs.getString("emailpessoal"));
                ub.setTelefonefixo(rs.getString("telefonefixo"));
                ub.setTelefonecelular(rs.getString("telefonecelular"));
                ub.setCargo(rs.getString("cargo"));
                ub.setDataadmissao(rs.getString("dataadmissao"));
                ub.setDatademissao(rs.getString("datademissao"));
                ub.setLivrofolha(rs.getString("livrofolha"));
                ub.setStatus(rs.getString("status"));
                ub.setEmailfabrica(rs.getString("emailfabrica"));
                ub.setPis(rs.getString("pis"));
                ub.setLogin(rs.getString("login"));
                ub.setSenha(rs.getString("senha"));
                ub.setVendedor(rs.getString("vendedor"));
                ub.setNivel(rs.getString("nivel"));

                listu.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listu;

    }

    public List<UsuariosBean> vendedores() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<UsuariosBean> listu = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE vendedor = 'true'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuariosBean ub = new UsuariosBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));

                listu.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listu;

    }

    public List<UsuariosBean> readtabelausuariosativo() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<UsuariosBean> listu = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE status = 'ativo'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuariosBean ub = new UsuariosBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));
                ub.setCargo(rs.getString("cargo"));
                ub.setStatus(rs.getString("status"));

                listu.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listu;

    }

    public List<UsuariosBean> readtabelausuariosinativo() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<UsuariosBean> listu = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE status = 'inativo'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuariosBean ub = new UsuariosBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));
                ub.setCargo(rs.getString("cargo"));
                ub.setStatus(rs.getString("status"));

                listu.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listu;

    }

    public List<UsuariosBean> readtabelausuariostodos() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<UsuariosBean> listu = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios");
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuariosBean ub = new UsuariosBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));
                ub.setCargo(rs.getString("cargo"));
                ub.setStatus(rs.getString("status"));

                listu.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listu;

    }
    
    public List<UsuariosBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<UsuariosBean> listub = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE id LIKE '%" + pesquisa + "%' OR nome LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuariosBean ub = new UsuariosBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));

                listub.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listub;
    }

    public boolean checklogin(String login, String senha) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        Boolean check = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE login = ? AND senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();

            if (rs.next()) {
                check = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public List<UsuariosBean> checknivel(String nivel) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<UsuariosBean> listub = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE login = ?");
            stmt.setString(1, nivel);
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuariosBean ub = new UsuariosBean();

                ub.setNivel(rs.getString("nivel"));

                listub.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listub;
    }

    public void update(UsuariosBean ub) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE usuarios SET nome = ?, emailpessoal = ?, dataadmissao = ?, telefonefixo = ?, telefonecelular = ?, datanascimento = ?, datademissao = ?, emailfabrica = ?, vendedor = ?, status = ?, login = ?, senha = ?, cargo = ?, cpf = ?, pis = ?, rg = ?, livrofolha = ?, nivel = ? WHERE id = ?");
            stmt.setString(1, ub.getNome());
            stmt.setString(2, ub.getEmailpessoal());
            stmt.setString(3, ub.getDataadmissao());
            stmt.setString(4, ub.getTelefonefixo());
            stmt.setString(5, ub.getTelefonecelular());
            stmt.setString(6, ub.getDatanascimento());
            stmt.setString(7, ub.getDatademissao());
            stmt.setString(8, ub.getEmailfabrica());
            stmt.setString(9, ub.getVendedor());
            stmt.setString(10, ub.getStatus());
            stmt.setString(11, ub.getLogin());
            stmt.setString(12, ub.getSenha());
            stmt.setString(13, ub.getCargo());
            stmt.setString(14, ub.getCpf());
            stmt.setString(15, ub.getPis());
            stmt.setString(16, ub.getRg());
            stmt.setString(17, ub.getLivrofolha());
            stmt.setString(18, ub.getNivel());
            stmt.setInt(19, ub.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
