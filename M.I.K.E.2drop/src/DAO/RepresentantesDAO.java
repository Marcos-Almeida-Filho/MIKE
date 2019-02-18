/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.RepresentantesBean;
import Connection.ConnectionFactory;
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
public class RepresentantesDAO {

    public void create(RepresentantesBean rb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO representantes (status, nome, cpf, rg, nomeempresa, cnpj, logradouro, numero, complemento, bairro, cidade, uf, cep, regiao) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, rb.getStatus());
            stmt.setString(2, rb.getNome());
            stmt.setString(3, rb.getCpf());
            stmt.setString(4, rb.getRg());
            stmt.setString(5, rb.getNomeempresa());
            stmt.setString(6, rb.getCnpj());
            stmt.setString(7, rb.getLogradouro());
            stmt.setString(8, rb.getNumero());
            stmt.setString(9, rb.getComplemento());
            stmt.setString(10, rb.getBairro());
            stmt.setString(11, rb.getCidade());
            stmt.setString(12, rb.getUf());
            stmt.setString(13, rb.getCep());
            stmt.setString(14, rb.getRegiao());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<RepresentantesBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<RepresentantesBean> listrb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM representantes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                RepresentantesBean rb = new RepresentantesBean();

                rb.setId(rs.getInt("id"));
                rb.setNome(rs.getString("nome"));
                rb.setRegiao(rs.getString("regiao"));
                rb.setStatus(rs.getString("status"));

                listrb.add(rb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listrb;
    }

    public List<RepresentantesBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<RepresentantesBean> listrb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM representantes WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RepresentantesBean rb = new RepresentantesBean();

                rb.setStatus(rs.getString("status"));
                rb.setNome(rs.getString("nome"));
                rb.setCpf(rs.getString("cpf"));
                rb.setRg(rs.getString("rg"));
                rb.setNomeempresa(rs.getString("nomeempresa"));
                rb.setCnpj(rs.getString("cnpj"));
                rb.setLogradouro(rs.getString("logradouro"));
                rb.setNumero(rs.getString("numero"));
                rb.setComplemento(rs.getString("complemento"));
                rb.setBairro(rs.getString("bairro"));
                rb.setCidade(rs.getString("cidade"));
                rb.setUf(rs.getString("uf"));
                rb.setCep(rs.getString("cep"));
                rb.setRegiao(rs.getString("regiao"));

                //status, nome, cpf, rg, nomeempresa, cnpj, logradouro, numero, complemento, bairro, cidade, uf, cep, regiao
                listrb.add(rb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listrb;
    }

    public List<RepresentantesBean> readcbregiao() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<RepresentantesBean> listrb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM representantes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                RepresentantesBean rb = new RepresentantesBean();

                rb.setId(rs.getInt("id"));
                rb.setNome(rs.getString("nome"));
                rb.setRegiao(rs.getString("regiao"));
                rb.setStatus(rs.getString("status"));

                listrb.add(rb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listrb;
    }

    public void update(RepresentantesBean rb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE representantes SET status = ?, nome = ?, cpf = ?, rg = ?, nomeempresa = ?, cnpj = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, regiao = ? WHERE id = ?");

            stmt.setString(1, rb.getStatus());
            stmt.setString(2, rb.getNome());
            stmt.setString(3, rb.getCpf());
            stmt.setString(4, rb.getRg());
            stmt.setString(5, rb.getNomeempresa());
            stmt.setString(6, rb.getCnpj());
            stmt.setString(7, rb.getLogradouro());
            stmt.setString(8, rb.getNumero());
            stmt.setString(9, rb.getComplemento());
            stmt.setString(10, rb.getBairro());
            stmt.setString(11, rb.getCidade());
            stmt.setString(12, rb.getUf());
            stmt.setString(13, rb.getCep());
            stmt.setString(14, rb.getRegiao());
            stmt.setInt(15, rb.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void cancelarvinculo(RepresentantesBean rb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE representantes SET status = ? WHERE id = ?");

            stmt.setString(1, "Inativo");
            stmt.setInt(2, rb.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vínculo cancelado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cancelar vínculo!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
