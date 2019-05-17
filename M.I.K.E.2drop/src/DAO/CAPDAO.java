/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CAPBean;
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
public class CAPDAO {

    public void create(CAPBean capb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO cap (datalancamento, fornecedor, notafiscal, dataemissao, total, parcela, valorparcela, dataparcela, datapagamento, banco, metodo, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, capb.getDatalancamento());
            stmt.setString(2, capb.getFornecedor());
            stmt.setString(3, capb.getNotafiscal());
            stmt.setString(4, capb.getDataemissao());
            stmt.setString(5, capb.getTotal());
            stmt.setString(6, capb.getParcela());
            stmt.setString(7, capb.getValorparcela());
            stmt.setString(8, capb.getDataparcela());
            stmt.setString(9, capb.getDatapagamento());
            stmt.setString(10, capb.getBanco());
            stmt.setString(11, capb.getMetodo());
            stmt.setString(12, capb.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<CAPBean> readtodos() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CAPBean> listcapb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM cap");
            rs = stmt.executeQuery();

            while (rs.next()) {
                CAPBean capb = new CAPBean();

                capb.setId(rs.getInt("id"));
                capb.setDatalancamento(rs.getString("datalancamento"));
                capb.setFornecedor(rs.getString("fornecedor"));
                capb.setNotafiscal(rs.getString("notafiscal"));
                capb.setDataemissao(rs.getString("dataemissao"));
                capb.setTotal(rs.getString("total"));
                capb.setParcela(rs.getString("parcela"));
                capb.setValorparcela(rs.getString("valorparcela"));
                capb.setDataparcela(rs.getString("dataparcela"));
                capb.setDatapagamento(rs.getString("datapagamento"));
                capb.setBanco(rs.getString("banco"));
                capb.setMetodo(rs.getString("metodo"));
                capb.setStatus(rs.getString("status"));

                listcapb.add(capb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcapb;
    }

    public List<CAPBean> readstatus(String status) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CAPBean> listcapb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM cap WHERE status = ?");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CAPBean capb = new CAPBean();

                capb.setId(rs.getInt("id"));
                capb.setDatalancamento(rs.getString("datalancamento"));
                capb.setFornecedor(rs.getString("fornecedor"));
                capb.setNotafiscal(rs.getString("notafiscal"));
                capb.setDataemissao(rs.getString("dataemissao"));
                capb.setTotal(rs.getString("total"));
                capb.setParcela(rs.getString("parcela"));
                capb.setValorparcela(rs.getString("valorparcela"));
                capb.setDataparcela(rs.getString("dataparcela"));
                capb.setDatapagamento(rs.getString("datapagamento"));
                capb.setBanco(rs.getString("banco"));
                capb.setMetodo(rs.getString("metodo"));

                listcapb.add(capb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcapb;
    }

    public List<CAPBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CAPBean> listcapb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM cap WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CAPBean capb = new CAPBean();

                capb.setId(rs.getInt("id"));
                capb.setDatalancamento(rs.getString("datalancamento"));
                capb.setFornecedor(rs.getString("fornecedor"));
                capb.setNotafiscal(rs.getString("notafiscal"));
                capb.setDataemissao(rs.getString("dataemissao"));
                capb.setTotal(rs.getString("total"));
                capb.setParcela(rs.getString("parcela"));
                capb.setValorparcela(rs.getString("valorparcela"));
                capb.setDataparcela(rs.getString("dataparcela"));
                capb.setDatapagamento(rs.getString("datapagamento"));
                capb.setBanco(rs.getString("banco"));
                capb.setMetodo(rs.getString("metodo"));

                listcapb.add(capb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcapb;
    }

    public void update(CAPBean capb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE cap SET datalancamento = ?, fornecedor = ?, notafiscal = ?, dataemissao = ?, total = ?, parcela = ?, valorparcela = ?, dataparcela = ?, datapagamento = ?, banco = ?, metodo = ?, status = ? WHERE id = ?");

            stmt.setString(1, capb.getDatalancamento());
            stmt.setString(2, capb.getFornecedor());
            stmt.setString(3, capb.getNotafiscal());
            stmt.setString(4, capb.getDataemissao());
            stmt.setString(5, capb.getTotal());
            stmt.setString(6, capb.getParcela());
            stmt.setString(7, capb.getValorparcela());
            stmt.setString(8, capb.getDataparcela());
            stmt.setString(9, capb.getDatapagamento());
            stmt.setString(10, capb.getBanco());
            stmt.setString(11, capb.getMetodo());
            stmt.setString(12, capb.getStatus());
            stmt.setInt(13, capb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updatepagamento(CAPBean capb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE cap SET datapagamento = ?, banco = ?, metodo = ?, status = ? WHERE id = ?");

            stmt.setString(1, capb.getDatapagamento());
            stmt.setString(2, capb.getBanco());
            stmt.setString(3, capb.getMetodo());
            stmt.setString(4, capb.getStatus());
            stmt.setInt(5, capb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
