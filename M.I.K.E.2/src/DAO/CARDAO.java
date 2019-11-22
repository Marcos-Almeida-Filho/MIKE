/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CARBean;
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
public class CARDAO {

    public void create(CARBean capb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO car (datalancamento, cliente, notafiscal, dataemissao, total, parcela, valorparcela, dataparcela, status) VALUES (?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, capb.getDatalancamento());
            stmt.setString(2, capb.getCliente());
            stmt.setInt(3, capb.getNotafiscal());
            stmt.setString(4, capb.getDataemissao());
            stmt.setDouble(5, capb.getTotal());
            stmt.setString(6, capb.getParcela());
            stmt.setDouble(7, capb.getValorparcela());
            stmt.setString(8, capb.getDataparcela());
            stmt.setString(9, capb.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar CAR!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<CARBean> readtodos() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CARBean> listcapb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM car");
            rs = stmt.executeQuery();

            while (rs.next()) {
                CARBean capb = new CARBean();

                capb.setId(rs.getInt("id"));
                capb.setDatalancamento(rs.getString("datalancamento"));
                capb.setCliente(rs.getString("cliente"));
                capb.setNotafiscal(rs.getInt("notafiscal"));
                capb.setDataemissao(rs.getString("dataemissao"));
                capb.setTotal(rs.getDouble("total"));
                capb.setParcela(rs.getString("parcela"));
                capb.setValorparcela(rs.getDouble("valorparcela"));
                capb.setDataparcela(rs.getString("dataparcela"));
                capb.setDatarecebimento(rs.getString("datarecebimento"));
                capb.setBanco(rs.getString("banco"));
                capb.setMetodo(rs.getString("metodo"));
                capb.setStatus(rs.getString("status"));

                listcapb.add(capb);
            }
        } catch (SQLException e) {
            Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcapb;
    }
    
    public List<CARBean> readaberto() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CARBean> listcapb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM car WHERE datarecebimento IS NULL ORDER BY dataparcela");
            rs = stmt.executeQuery();

            while (rs.next()) {
                CARBean capb = new CARBean();

                capb.setId(rs.getInt("id"));
                capb.setDatalancamento(rs.getString("datalancamento"));
                capb.setCliente(rs.getString("cliente"));
                capb.setNotafiscal(rs.getInt("notafiscal"));
                capb.setDataemissao(rs.getString("dataemissao"));
                capb.setTotal(rs.getDouble("total"));
                capb.setParcela(rs.getString("parcela"));
                capb.setValorparcela(rs.getDouble("valorparcela"));
                capb.setDataparcela(rs.getString("dataparcela"));
                capb.setDatarecebimento(rs.getString("datarecebimento"));
                capb.setBanco(rs.getString("banco"));
                capb.setMetodo(rs.getString("metodo"));
                capb.setStatus(rs.getString("status"));

                listcapb.add(capb);
            }
        } catch (SQLException e) {
            Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcapb;
    }
    
    public List<CARBean> readcreated(String data) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CARBean> listcapb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM car WHERE datalancamento = ?");
            stmt.setString(1, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CARBean capb = new CARBean();

                capb.setId(rs.getInt("id"));

                listcapb.add(capb);
            }
        } catch (SQLException e) {
            Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcapb;
    }

    public List<CARBean> readstatus(String status) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CARBean> listcapb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM car WHERE status = ?");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CARBean capb = new CARBean();

                capb.setId(rs.getInt("id"));
                capb.setDatalancamento(rs.getString("datalancamento"));
                capb.setCliente(rs.getString("cliente"));
                capb.setNotafiscal(rs.getInt("notafiscal"));
                capb.setDataemissao(rs.getString("dataemissao"));
                capb.setTotal(rs.getDouble("total"));
                capb.setParcela(rs.getString("parcela"));
                capb.setValorparcela(rs.getDouble("valorparcela"));
                capb.setDataparcela(rs.getString("dataparcela"));
                capb.setDatarecebimento(rs.getString("datarecebimento"));
                capb.setBanco(rs.getString("banco"));
                capb.setMetodo(rs.getString("metodo"));

                listcapb.add(capb);
            }
        } catch (SQLException e) {
            Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcapb;
    }

    public List<CARBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CARBean> listcapb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM car WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CARBean capb = new CARBean();

                capb.setId(rs.getInt("id"));
                capb.setDatalancamento(rs.getString("datalancamento"));
                capb.setCliente(rs.getString("cliente"));
                capb.setNotafiscal(rs.getInt("notafiscal"));
                capb.setDataemissao(rs.getString("dataemissao"));
                capb.setTotal(rs.getDouble("total"));
                capb.setParcela(rs.getString("parcela"));
                capb.setValorparcela(rs.getDouble("valorparcela"));
                capb.setDataparcela(rs.getString("dataparcela"));
                capb.setDatarecebimento(rs.getString("datarecebimento"));

                listcapb.add(capb);
            }
        } catch (SQLException e) {
            Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcapb;
    }

    public void update(CARBean capb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE car SET cliente = ?, notafiscal = ?, dataemissao = ?, total = ?, parcela = ?, valorparcela = ?, dataparcela = ?, datarecebimento = ?, banco = ?, metodo = ?, status = ? WHERE id = ?");

            stmt.setString(1, capb.getCliente());
            stmt.setInt(2, capb.getNotafiscal());
            stmt.setString(3, capb.getDataemissao());
            stmt.setDouble(4, capb.getTotal());
            stmt.setString(5, capb.getParcela());
            stmt.setDouble(6, capb.getValorparcela());
            stmt.setString(7, capb.getDataparcela());
            stmt.setString(8, capb.getDatarecebimento());
            stmt.setString(9, capb.getBanco());
            stmt.setString(10, capb.getMetodo());
            stmt.setString(11, capb.getStatus());
            stmt.setInt(12, capb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar CAR!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updaterecebimento(CARBean capb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE car SET datarecebimento = ?, banco = ?, metodo = ?, status = ? WHERE id = ?");

            stmt.setString(1, capb.getDatarecebimento());
            stmt.setString(2, capb.getBanco());
            stmt.setString(3, capb.getMetodo());
            stmt.setString(4, capb.getStatus());
            stmt.setInt(5, capb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar CAR!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CARDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
