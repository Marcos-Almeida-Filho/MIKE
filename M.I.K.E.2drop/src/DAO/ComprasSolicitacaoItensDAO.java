/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Bean.ComprasSolicitacaoItensBean;
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
public class ComprasSolicitacaoItensDAO {
    
    public void create(ComprasSolicitacaoItensBean csb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO solicitacao_compras (idtela, data, solicitante, tipo, notes, status) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, csb.getIdtela());
            stmt.setString(2, csb.getData());
            stmt.setString(3, csb.getSolicitante());
            stmt.setString(4, csb.getTipo());
            stmt.setString(5, csb.getNotes());
            stmt.setString(6, csb.getStatus());

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

    public List<ComprasSolicitacaoItensBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoItensBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoItensBean csb = new ComprasSolicitacaoItensBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setNotes(rs.getString("notes"));
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
    
    public List<ComprasSolicitacaoItensBean> reademaberto() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoItensBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras WHERE status = ? OR status = ?");
            stmt.setString(1, "Ativo");
            stmt.setString(2, "Pedido Parcial");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoItensBean csb = new ComprasSolicitacaoItensBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setNotes(rs.getString("notes"));
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
    
    public List<ComprasSolicitacaoItensBean> readstatus(String status) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoItensBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras WHERE status = ?");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoItensBean csb = new ComprasSolicitacaoItensBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setNotes(rs.getString("notes"));
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
        String idtela = "CS" + year + "-0001";

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

    public List<ComprasSolicitacaoItensBean> click(String id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoItensBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras WHERE idtela = ?");
            stmt.setString(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoItensBean csb = new ComprasSolicitacaoItensBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setNotes(rs.getString("notes"));
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
    
    public List<ComprasSolicitacaoItensBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoItensBean> listob = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM solicitacao_compras WHERE idtela LIKE '%" + pesquisa + "%' OR solicitante LIKE '%" + pesquisa + "%' OR tipo LIKE '%" + pesquisa + "%' OR status LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoItensBean csb = new ComprasSolicitacaoItensBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setNotes(rs.getString("notes"));
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

    public void update(ComprasSolicitacaoItensBean csb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE solicitacao_compras SET solicitante = ?, tipo = ?, notes = ?, status = ? WHERE idtela = ?");
            stmt.setString(1, csb.getSolicitante());
            stmt.setString(2, csb.getTipo());
            stmt.setString(3, csb.getNotes());
            stmt.setString(4, csb.getStatus());
            stmt.setString(5, csb.getIdtela());

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

    public void updatestatus(ComprasSolicitacaoItensBean csb) {

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
