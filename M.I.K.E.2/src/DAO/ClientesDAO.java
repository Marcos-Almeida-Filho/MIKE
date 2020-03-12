/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ClientesBean;
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
public class ClientesDAO {

    public void create(ClientesBean cb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO clientes (nome, razaosocial, cnpj, ie, telefone, grupo, vendedor, idrepresentante, representante,condicaodepagamento, logradouro, numero, complemento, bairro, cidade, uf, cep, im) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, cb.getNome());
            stmt.setString(2, cb.getRazaosocial());
            stmt.setString(3, cb.getCnpj());
            stmt.setString(4, cb.getIe());
            stmt.setString(5, cb.getTelefone());
            stmt.setString(6, cb.getGrupo());
            stmt.setString(7, cb.getVendedor());
            stmt.setInt(8, cb.getIdrepresentante());
            stmt.setString(9, cb.getRepresentante());
            stmt.setString(10, cb.getCondicaodepagamento());
            stmt.setString(11, cb.getLogradouro());
            stmt.setString(12, cb.getNumero());
            stmt.setString(13, cb.getComplemento());
            stmt.setString(14, cb.getBairro());
            stmt.setString(15, cb.getCidade());
            stmt.setString(16, cb.getUf());
            stmt.setString(17, cb.getCep());
            stmt.setString(18, cb.getIm());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar cliente!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao criar cliente!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ClientesBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ClientesBean> listcb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientesBean cb = new ClientesBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));
                cb.setCnpj(rs.getString("cnpj"));
                cb.setIe(rs.getString("ie"));
                cb.setTelefone(rs.getString("telefone"));
                cb.setGrupo(rs.getString("grupo"));
                cb.setVendedor(rs.getString("vendedor"));
                cb.setIdrepresentante(rs.getInt("idrepresentante"));
                cb.setRepresentante(rs.getString("representante"));
                cb.setCondicaodepagamento(rs.getString("condicaodepagamento"));
                cb.setLogradouro(rs.getString("logradouro"));
                cb.setNumero(rs.getString("numero"));
                cb.setComplemento(rs.getString("complemento"));
                cb.setBairro(rs.getString("bairro"));
                cb.setCidade(rs.getString("cidade"));
                cb.setUf(rs.getString("uf"));
                cb.setCep(rs.getString("cep"));
                cb.setIm(rs.getString("im"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcb;
    }

    public boolean checkcnpj(String cnpj) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        boolean check = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes WHERE cnpj = ?");
            stmt.setString(1, cnpj);
            rs = stmt.executeQuery();

            while (rs.next()) {
                check = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public List<ClientesBean> readcnpj(String cnpj) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ClientesBean> listfb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes WHERE cnpj = ?");
            stmt.setString(1, cnpj);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientesBean cb = new ClientesBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));
                cb.setCnpj(rs.getString("cnpj"));
                cb.setIe(rs.getString("ie"));
                cb.setTelefone(rs.getString("telefone"));
                cb.setLogradouro(rs.getString("logradouro"));
                cb.setNumero(rs.getString("numero"));
                cb.setComplemento(rs.getString("complemento"));
                cb.setBairro(rs.getString("bairro"));
                cb.setCidade(rs.getString("cidade"));
                cb.setUf(rs.getString("uf"));
                cb.setCep(rs.getString("cep"));
                cb.setIm(rs.getString("im"));

                listfb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listfb;
    }

    public List<ClientesBean> readcondicoes(String nome) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ClientesBean> listcb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes WHERE nome = ?");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientesBean cb = new ClientesBean();

                cb.setVendedor(rs.getString("vendedor"));
                cb.setRepresentante(rs.getString("representante"));
                cb.setCondicaodepagamento(rs.getString("condicaodepagamento"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcb;
    }

    public List<ClientesBean> readrepresentantexcliente(Integer idrep) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ClientesBean> listcb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes WHERE idrepresentante = ?");
            stmt.setInt(1, idrep);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientesBean cb = new ClientesBean();

                cb.setNome(rs.getString("nome"));
                cb.setVisita(rs.getString("visita"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcb;
    }

    public List<ClientesBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ClientesBean> listcb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientesBean cb = new ClientesBean();

                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));
                cb.setCnpj(rs.getString("cnpj"));
                cb.setIe(rs.getString("ie"));
                cb.setTelefone(rs.getString("telefone"));
                cb.setGrupo(rs.getString("grupo"));
                cb.setVendedor(rs.getString("vendedor"));
                cb.setIdrepresentante(rs.getInt("idrepresentante"));
                cb.setRepresentante(rs.getString("representante"));
                cb.setCondicaodepagamento(rs.getString("condicaodepagamento"));
                cb.setLogradouro(rs.getString("logradouro"));
                cb.setNumero(rs.getString("numero"));
                cb.setComplemento(rs.getString("complemento"));
                cb.setBairro(rs.getString("bairro"));
                cb.setCidade(rs.getString("cidade"));
                cb.setUf(rs.getString("uf"));
                cb.setCep(rs.getString("cep"));
                cb.setIm(rs.getString("im"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcb;
    }

    public List<ClientesBean> preenchertabelaclientes() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ClientesBean> listcb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientesBean cb = new ClientesBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcb;
    }

    public void update(ClientesBean cb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE clientes SET nome = ?, razaosocial = ?, cnpj = ?, ie = ?, telefone = ?, grupo = ?, vendedor = ?, idrepresentante = ?, representante = ?,condicaodepagamento = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, im = ? WHERE id = ?");

            stmt.setString(1, cb.getNome());
            stmt.setString(2, cb.getRazaosocial());
            stmt.setString(3, cb.getCnpj());
            stmt.setString(4, cb.getIe());
            stmt.setString(5, cb.getTelefone());
            stmt.setString(6, cb.getGrupo());
            stmt.setString(7, cb.getVendedor());
            stmt.setInt(8, cb.getIdrepresentante());
            stmt.setString(9, cb.getRepresentante());
            stmt.setString(10, cb.getCondicaodepagamento());
            stmt.setString(11, cb.getLogradouro());
            stmt.setString(12, cb.getNumero());
            stmt.setString(13, cb.getComplemento());
            stmt.setString(14, cb.getBairro());
            stmt.setString(15, cb.getCidade());
            stmt.setString(16, cb.getUf());
            stmt.setString(17, cb.getCep());
            stmt.setString(18, cb.getIm());
            stmt.setInt(19, cb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updatevisita(ClientesBean cb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE clientes SET visita = ? WHERE nome = ?");

            stmt.setString(1, cb.getVisita());
            stmt.setString(2, cb.getNome());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ClientesBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ClientesBean> listcb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes WHERE id LIKE '%" + pesquisa + "%' OR nome LIKE '%" + pesquisa + "%' OR razaosocial LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientesBean mb = new ClientesBean();

                mb.setId(rs.getInt("id"));
                mb.setNome(rs.getString("nome"));
                mb.setRazaosocial(rs.getString("razaosocial"));

                listcb.add(mb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcb;
    }

    public List<ClientesBean> pesquisanome(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ClientesBean> listcb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes WHERE nome LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientesBean mb = new ClientesBean();

                mb.setId(rs.getInt("id"));
                mb.setNome(rs.getString("nome"));
                mb.setRazaosocial(rs.getString("razaosocial"));

                listcb.add(mb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcb;
    }
}
