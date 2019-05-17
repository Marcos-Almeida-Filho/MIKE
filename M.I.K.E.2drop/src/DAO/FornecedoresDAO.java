/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.FornecedoresBean;
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
public class FornecedoresDAO {

    public void create(FornecedoresBean fb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO fornecedores (nome, razaosocial, cnpj, ie, telefone, logradouro, numero, complemento, bairro, cidade, uf, cep) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, fb.getNome());
            stmt.setString(2, fb.getRazaosocial());
            stmt.setString(3, fb.getCnpj());
            stmt.setString(4, fb.getIe());
            stmt.setString(5, fb.getTelefone());
            stmt.setString(6, fb.getLogradouro());
            stmt.setString(7, fb.getNumero());
            stmt.setString(8, fb.getComplemento());
            stmt.setString(9, fb.getBairro());
            stmt.setString(10, fb.getCidade());
            stmt.setString(11, fb.getUf());
            stmt.setString(12, fb.getCep());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<FornecedoresBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<FornecedoresBean> listfb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedores");
            rs = stmt.executeQuery();

            while (rs.next()) {
                FornecedoresBean fb = new FornecedoresBean();

                fb.setId(rs.getInt("id"));
                fb.setNome(rs.getString("nome"));
                fb.setRazaosocial(rs.getString("razaosocial"));
                fb.setCnpj(rs.getString("cnpj"));
                fb.setIe(rs.getString("ie"));
                fb.setTelefone(rs.getString("telefone"));
                fb.setLogradouro(rs.getString("logradouro"));
                fb.setNumero(rs.getString("numero"));
                fb.setComplemento(rs.getString("complemento"));
                fb.setBairro(rs.getString("bairro"));
                fb.setCidade(rs.getString("cidade"));
                fb.setUf(rs.getString("uf"));
                fb.setCep(rs.getString("cep"));

                listfb.add(fb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listfb;
    }

    public List<FornecedoresBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<FornecedoresBean> listfb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedores WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                FornecedoresBean fb = new FornecedoresBean();

                fb.setNome(rs.getString("nome"));
                fb.setRazaosocial(rs.getString("razaosocial"));
                fb.setCnpj(rs.getString("cnpj"));
                fb.setIe(rs.getString("ie"));
                fb.setTelefone(rs.getString("telefone"));
                fb.setLogradouro(rs.getString("logradouro"));
                fb.setNumero(rs.getString("numero"));
                fb.setComplemento(rs.getString("complemento"));
                fb.setBairro(rs.getString("bairro"));
                fb.setCidade(rs.getString("cidade"));
                fb.setUf(rs.getString("uf"));
                fb.setCep(rs.getString("cep"));

                listfb.add(fb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listfb;
    }

    public List<FornecedoresBean> preenchertabelafornecedores() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<FornecedoresBean> listfb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedores");
            rs = stmt.executeQuery();

            while (rs.next()) {
                FornecedoresBean fb = new FornecedoresBean();

                fb.setId(rs.getInt("id"));
                fb.setNome(rs.getString("nome"));
                fb.setRazaosocial(rs.getString("razaosocial"));

                listfb.add(fb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listfb;
    }

    public void update(FornecedoresBean fb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE clientes SET nome = ?, razaosocial = ?, cnpj = ?, ie = ?, telefone = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ? WHERE id = ?");

            stmt.setString(1, fb.getNome());
            stmt.setString(2, fb.getRazaosocial());
            stmt.setString(3, fb.getCnpj());
            stmt.setString(4, fb.getIe());
            stmt.setString(5, fb.getTelefone());
            stmt.setString(6, fb.getLogradouro());
            stmt.setString(7, fb.getNumero());
            stmt.setString(8, fb.getComplemento());
            stmt.setString(9, fb.getBairro());
            stmt.setString(10, fb.getCidade());
            stmt.setString(11, fb.getUf());
            stmt.setString(12, fb.getCep());
            stmt.setInt(13, fb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
