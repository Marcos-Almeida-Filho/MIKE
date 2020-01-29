/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Bean.ComprasSolicitacaoBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
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
public class ComprasSolicitacaoDAO {
    
    public void create(ComprasSolicitacaoBean csb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO solicitacao_compras (idtela, data, solicitante, tipo, status) VALUES (?,?,?,?,?)");
            stmt.setString(1, csb.getIdtela());
            stmt.setString(2, csb.getData());
            stmt.setString(3, csb.getSolicitante());
            stmt.setString(4, csb.getTipo());
            stmt.setString(5, csb.getStatus());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar a solicitação de compra!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ComprasSolicitacaoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoBean csb = new ComprasSolicitacaoBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listso.add(csb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listso;
    }
    
    public String readultimoidtela() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;
        
        String ultimoidtela = "";

        try {
            stmt = con.prepareStatement("SELECT MAX(idtela) FROM solicitacao_compras");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ultimoidtela = rs.getString("idtela");
            }
        } catch (SQLException e) {
            Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return ultimoidtela;
    }
    
    public List<ComprasSolicitacaoBean> readcreated(String data) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras WHERE data = " + data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoBean csb = new ComprasSolicitacaoBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listso.add(csb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listso;
    }
    
    public List<ComprasSolicitacaoBean> reademaberto() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras WHERE status = ? OR status = ?");
            stmt.setString(1, "Ativo");
            stmt.setString(2, "Pedido Parcial");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoBean csb = new ComprasSolicitacaoBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listso.add(csb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listso;
    }
    
    public List<ComprasSolicitacaoBean> readstatus(String status) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras WHERE status = ?");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoBean csb = new ComprasSolicitacaoBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listso.add(csb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        String idtela = "SC" + year + "-0001";

        Boolean resp = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM solicitacao_compras WHERE idtela = ?");
            stmt.setString(1, idtela);
            rs = stmt.executeQuery();

            // checking if ResultSet is empty
            resp = rs.next();
        } catch (SQLException e) {
            Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return resp;
    }

    public List<ComprasSolicitacaoBean> click(String id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras WHERE idtela = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoBean csb = new ComprasSolicitacaoBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listso.add(csb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listso;
    }
    
    public List<ComprasSolicitacaoBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoBean> listob = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM solicitacao_compras WHERE idtela LIKE '%" + pesquisa + "%' OR solicitante LIKE '%" + pesquisa + "%' OR tipo LIKE '%" + pesquisa + "%' OR status LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoBean csb = new ComprasSolicitacaoBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listob.add(csb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listob;
    }

    public void update(ComprasSolicitacaoBean csb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE solicitacao_compras SET solicitante = ?, tipo = ? WHERE idtela = ?");
            stmt.setString(1, csb.getSolicitante());
            stmt.setString(2, csb.getTipo());
            stmt.setString(3, csb.getIdtela());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updatestatus(ComprasSolicitacaoBean csb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE solicitacao_compras SET status = ? WHERE idtela = ?");
            stmt.setString(1, csb.getStatus());
            stmt.setString(2, csb.getIdtela());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
