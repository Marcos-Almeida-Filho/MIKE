/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoPedidoBean;
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
public class ServicoPedidoDAO {

    public void create(ServicoPedidoBean spb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_pedido (idorcamento, cliente, condicao, representante, vendedor, notes, status, nfcliente) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setInt(1, spb.getIdorcamento());
            stmt.setString(2, spb.getCliente());
            stmt.setString(3, spb.getCondicao());
            stmt.setString(4, spb.getRepresentante());
            stmt.setString(5, spb.getVendedor());
            stmt.setString(6, spb.getNotes());
            stmt.setString(7, spb.getStatus());
            stmt.setString(8, spb.getNfcliente());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoPedidoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoPedidoBean> listsp = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_pedido");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoPedidoBean spb = new ServicoPedidoBean();

                spb.setId(rs.getInt("id"));
                spb.setIdorcamento(rs.getInt("idorcamento"));
                spb.setCliente(rs.getString("cliente"));
                spb.setCondicao(rs.getString("condicao"));
                spb.setRepresentante(rs.getString("representante"));
                spb.setVendedor(rs.getString("vendedor"));
                spb.setNotes(rs.getString("notes"));
                spb.setStatus(rs.getString("status"));
                spb.setNfcliente(rs.getString("nfcliente"));

                listsp.add(spb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listsp;

    }
    
    public List<ServicoPedidoBean> readcreated(String cliente, String idorcamento) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoPedidoBean> listsp = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_pedido WHERE cliente = ? AND idorcamento = ?");
            stmt.setString(1, cliente);
            stmt.setString(2, idorcamento);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoPedidoBean spb = new ServicoPedidoBean();

                spb.setId(rs.getInt("id"));

                listsp.add(spb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listsp;

    }

    public List<ServicoPedidoBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoPedidoBean> listsp = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_pedido WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoPedidoBean spb = new ServicoPedidoBean();

                spb.setId(rs.getInt("id"));
                spb.setId(rs.getInt("idorcamento"));
                spb.setCliente(rs.getString("cliente"));
                spb.setCondicao(rs.getString("condicao"));
                spb.setRepresentante(rs.getString("representante"));
                spb.setVendedor(rs.getString("vendedor"));
                spb.setNotes(rs.getString("notes"));
                spb.setStatus(rs.getString("status"));
                spb.setNfcliente(rs.getString("nfcliente"));

                listsp.add(spb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listsp;

    }

    public void update(ServicoPedidoBean spb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_pedido SET cliente = ?, condicao = ?, representante = ?, vendedor = ?, notes = ?, status = ?, nfcliente = ? WHERE id = ?");
            stmt.setString(1, spb.getCliente());
            stmt.setString(2, spb.getCondicao());
            stmt.setString(3, spb.getRepresentante());
            stmt.setString(4, spb.getVendedor());
            stmt.setString(5, spb.getNotes());
            stmt.setString(6, spb.getStatus());
            stmt.setString(7, spb.getNfcliente());
            stmt.setInt(8, spb.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
