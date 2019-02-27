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
import java.util.ArrayList;
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
            stmt = con.prepareStatement("INSERT INTO servicos_orcamento (cliente, condicao, representante, vendedor, notes, status, clientecadastro) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, sob.getCliente());
            stmt.setString(2, sob.getCondicao());
            stmt.setString(3, sob.getRepresentante());
            stmt.setString(4, sob.getVendedor());
            stmt.setString(5, sob.getNotes());
            stmt.setString(6, sob.getStatus());
            stmt.setString(7, sob.getClientecadastro());

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

                sob.setId(rs.getInt("id"));
                sob.setCliente(rs.getString("cliente"));
                sob.setCondicao(rs.getString("condicao"));
                sob.setRepresentante(rs.getString("representante"));
                sob.setVendedor(rs.getString("vendedor"));
                sob.setNotes(rs.getString("notes"));
                sob.setStatus(rs.getString("status"));
                sob.setClientecadastro(rs.getString("clientecadastro"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }

    public List<ServicoOrcamentoBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoOrcamentoBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_orcamento WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoOrcamentoBean sob = new ServicoOrcamentoBean();

                sob.setId(rs.getInt("id"));
                sob.setCliente(rs.getString("cliente"));
                sob.setCondicao(rs.getString("condicao"));
                sob.setRepresentante(rs.getString("representante"));
                sob.setVendedor(rs.getString("vendedor"));
                sob.setNotes(rs.getString("notes"));
                sob.setStatus(rs.getString("status"));
                sob.setClientecadastro(rs.getString("clientecadastro"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }

    public void update(ServicoOrcamentoBean sob) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_orcamento SET cliente = ?, condicao = ?, representante = ?, vendedor = ?, notes = ?, status = ?, clientecadastro = ? WHERE id = ?");
            stmt.setString(1, sob.getCliente());
            stmt.setString(2, sob.getCondicao());
            stmt.setString(3, sob.getRepresentante());
            stmt.setString(4, sob.getVendedor());
            stmt.setString(5, sob.getNotes());
            stmt.setString(6, sob.getStatus());
            stmt.setString(7, sob.getClientecadastro());
            stmt.setInt(8, sob.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
