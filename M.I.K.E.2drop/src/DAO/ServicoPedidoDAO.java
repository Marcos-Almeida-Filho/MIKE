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
public class ServicoPedidoDAO {

    public void create(ServicoPedidoBean spb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_pedido (idtela, idorcamento, cliente, condicao, representante, vendedor, notes, status_retorno, status_cobranca, nfcliente, data) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, spb.getIdtela());
            stmt.setString(2, spb.getIdorcamento());
            stmt.setString(3, spb.getCliente());
            stmt.setString(4, spb.getCondicao());
            stmt.setString(5, spb.getRepresentante());
            stmt.setString(6, spb.getVendedor());
            stmt.setString(7, spb.getNotes());
            stmt.setString(8, spb.getStatus_retorno());
            stmt.setString(9, spb.getStatus_cobranca());
            stmt.setString(10, spb.getNfcliente());
            stmt.setString(11, spb.getData());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar o pedido!\n" + e);
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

                spb.setIdtela(rs.getString("idtela"));
                spb.setIdorcamento(rs.getString("idorcamento"));
                spb.setCliente(rs.getString("cliente"));
                spb.setCondicao(rs.getString("condicao"));
                spb.setRepresentante(rs.getString("representante"));
                spb.setVendedor(rs.getString("vendedor"));
                spb.setNotes(rs.getString("notes"));
                spb.setStatus_retorno(rs.getString("status_retorno"));
                spb.setStatus_cobranca(rs.getString("status_cobranca"));
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
            stmt = con.prepareStatement("SELECT * FROM servicos_pedido WHERE idtela = ?");
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
    
    public List<ServicoPedidoBean> readcreated(String cliente, String data) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoPedidoBean> listsp = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_pedido WHERE cliente = ? AND data = ?");
            stmt.setString(1, cliente);
            stmt.setString(2, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoPedidoBean spb = new ServicoPedidoBean();

                spb.setIdtela(rs.getString("idtela"));

                listsp.add(spb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listsp;

    }

    public List<ServicoPedidoBean> click(String id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoPedidoBean> listsp = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_pedido WHERE idtela = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoPedidoBean spb = new ServicoPedidoBean();

                spb.setIdtela(rs.getString("idtela"));
                spb.setId(rs.getInt("idorcamento"));
                spb.setCliente(rs.getString("cliente"));
                spb.setCondicao(rs.getString("condicao"));
                spb.setRepresentante(rs.getString("representante"));
                spb.setVendedor(rs.getString("vendedor"));
                spb.setNotes(rs.getString("notes"));
                spb.setStatus_retorno(rs.getString("status_retorno"));
                spb.setStatus_cobranca(rs.getString("status_cobranca"));
                spb.setNfcliente(rs.getString("nfcliente"));
                spb.setIdorcamento(rs.getString("idorcamento"));

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
            stmt = con.prepareStatement("UPDATE servicos_pedido SET cliente = ?, condicao = ?, representante = ?, vendedor = ?, notes = ?, nfcliente = ? WHERE idtela = ?");
            stmt.setString(1, spb.getCliente());
            stmt.setString(2, spb.getCondicao());
            stmt.setString(3, spb.getRepresentante());
            stmt.setString(4, spb.getVendedor());
            stmt.setString(5, spb.getNotes());
            stmt.setString(6, spb.getNfcliente());
            stmt.setString(7, spb.getIdtela());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updatestatusretorno(ServicoPedidoBean spb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_pedido SET status_retorno = ? WHERE idtela = ?");
            stmt.setString(1, spb.getStatus_retorno());
            stmt.setString(2, spb.getIdtela());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updatestatuscobranca(ServicoPedidoBean spb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_pedido SET status_cobranca = ? WHERE idtela = ?");
            stmt.setString(1, spb.getStatus_cobranca());
            stmt.setString(2, spb.getIdtela());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
