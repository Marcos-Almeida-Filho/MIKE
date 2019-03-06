/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoOrcamentoBean;
import Connection.ConnectionFactory;
import java.awt.HeadlessException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class ServicoOrcamentoDAO {

    public void create(ServicoOrcamentoBean sob) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_orcamento (idtela, cliente, condicao, representante, vendedor, notes, status, clientecadastro, data) VALUES (?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, sob.getIdtela());
            stmt.setString(2, sob.getCliente());
            stmt.setString(3, sob.getCondicao());
            stmt.setString(4, sob.getRepresentante());
            stmt.setString(5, sob.getVendedor());
            stmt.setString(6, sob.getNotes());
            stmt.setString(7, sob.getStatus());
            stmt.setString(8, sob.getClientecadastro());
            stmt.setString(9, sob.getData());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoOrcamentoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoOrcamentoBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_orcamento");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoOrcamentoBean sob = new ServicoOrcamentoBean();

                sob.setIdtela(rs.getString("idtela"));
                sob.setCliente(rs.getString("cliente"));
                sob.setCondicao(rs.getString("condicao"));
                sob.setRepresentante(rs.getString("representante"));
                sob.setVendedor(rs.getString("vendedor"));
                sob.setNotes(rs.getString("notes"));
                sob.setStatus(rs.getString("status"));
                sob.setClientecadastro(rs.getString("clientecadastro"));
                sob.setData(rs.getString("data"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }

    public Boolean readnome() throws SQLException {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        Calendar c = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(c.getTime());
        String idtela = year + "-0001";

        Boolean resp = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_orcamento WHERE idtela = ?");
            stmt.setString(1, idtela);
            rs = stmt.executeQuery();

            // checking if ResultSet is empty
            resp = rs.next();
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return resp;
    }

    public List<ServicoOrcamentoBean> click(String id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoOrcamentoBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_orcamento WHERE idtela = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoOrcamentoBean sob = new ServicoOrcamentoBean();

                sob.setIdtela(rs.getString("idtela"));
                sob.setCliente(rs.getString("cliente"));
                sob.setCondicao(rs.getString("condicao"));
                sob.setRepresentante(rs.getString("representante"));
                sob.setVendedor(rs.getString("vendedor"));
                sob.setNotes(rs.getString("notes"));
                sob.setStatus(rs.getString("status"));
                sob.setClientecadastro(rs.getString("clientecadastro"));
                sob.setData(rs.getString("data"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }
    
    public List<ServicoOrcamentoBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoOrcamentoBean> listob = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_orcamento WHERE idtela LIKE '%" + pesquisa + "%' OR cliente LIKE '%" + pesquisa + "%' OR status LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoOrcamentoBean sob = new ServicoOrcamentoBean();

                sob.setIdtela(rs.getString("idtela"));
                sob.setCliente(rs.getString("cliente"));
                sob.setStatus(rs.getString("status"));

                listob.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listob;
    }

    public void update(ServicoOrcamentoBean sob) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_orcamento SET cliente = ?, condicao = ?, representante = ?, vendedor = ?, notes = ?, status = ?, clientecadastro = ? WHERE idtela = ?");
            stmt.setString(1, sob.getCliente());
            stmt.setString(2, sob.getCondicao());
            stmt.setString(3, sob.getRepresentante());
            stmt.setString(4, sob.getVendedor());
            stmt.setString(5, sob.getNotes());
            stmt.setString(6, sob.getStatus());
            stmt.setString(7, sob.getClientecadastro());
            stmt.setString(8, sob.getIdtela());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updatestatus(ServicoOrcamentoBean sob) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_orcamento SET status = ? WHERE idtela = ?");
            stmt.setString(1, sob.getStatus());
            stmt.setString(2, sob.getIdtela());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
