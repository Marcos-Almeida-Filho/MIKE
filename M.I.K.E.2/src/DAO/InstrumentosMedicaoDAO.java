/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Bean.InstrumentosMedicaoBean;
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
public class InstrumentosMedicaoDAO {
    
    public void create(InstrumentosMedicaoBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO instrumento_medicao (codigo, status, tipo, modelo, serie, capacidade, resolucao, tolerancia, periodicidade, local, nrc, validade) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, bb.getCodigo());
            stmt.setString(2, bb.getStatus());
            stmt.setString(3, bb.getTipo());
            stmt.setString(4, bb.getModelo());
            stmt.setString(5, bb.getSerie());
            stmt.setString(6, bb.getCapacidade());
            stmt.setString(7, bb.getResolucao());
            stmt.setString(8, bb.getTolerancia());
            stmt.setInt(9, bb.getPeriodicidade());
            stmt.setString(10, bb.getLocal());
            stmt.setString(11, bb.getNrc());
            stmt.setString(12, bb.getValidade());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InstrumentosMedicaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<InstrumentosMedicaoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<InstrumentosMedicaoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM instrumento_medicao");
            rs = stmt.executeQuery();

            while (rs.next()) {
                InstrumentosMedicaoBean cb = new InstrumentosMedicaoBean();

                cb.setId(rs.getInt("id"));
                cb.setCodigo(rs.getString("codigo"));
                cb.setStatus(rs.getString("status"));
                cb.setTipo(rs.getString("tipo"));
                cb.setModelo(rs.getString("modelo"));
                cb.setSerie(rs.getString("serie"));
                cb.setCapacidade(rs.getString("capacidade"));
                cb.setResolucao(rs.getString("resolucao"));
                cb.setTolerancia(rs.getString("tolerancia"));
                cb.setPeriodicidade(rs.getInt("periodicidade"));
                cb.setLocal(rs.getString("local"));
                cb.setNrc(rs.getString("nrc"));
                cb.setValidade(rs.getString("validade"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InstrumentosMedicaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public List<InstrumentosMedicaoBean> readid(String codigo) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<InstrumentosMedicaoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM instrumento_medicao WHERE codigo = ?");
            stmt.setString(1, codigo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                InstrumentosMedicaoBean cb = new InstrumentosMedicaoBean();

                cb.setId(rs.getInt("id"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InstrumentosMedicaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<InstrumentosMedicaoBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<InstrumentosMedicaoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM instrumento_medicao WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                InstrumentosMedicaoBean cb = new InstrumentosMedicaoBean();

                cb.setId(rs.getInt("id"));
                cb.setCodigo(rs.getString("codigo"));
                cb.setStatus(rs.getString("status"));
                cb.setTipo(rs.getString("tipo"));
                cb.setModelo(rs.getString("modelo"));
                cb.setSerie(rs.getString("serie"));
                cb.setCapacidade(rs.getString("capacidade"));
                cb.setResolucao(rs.getString("resolucao"));
                cb.setTolerancia(rs.getString("tolerancia"));
                cb.setPeriodicidade(rs.getInt("periodicidade"));
                cb.setLocal(rs.getString("local"));
                cb.setNrc(rs.getString("nrc"));
                cb.setValidade(rs.getString("validade"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InstrumentosMedicaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<InstrumentosMedicaoBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<InstrumentosMedicaoBean> listub = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM instrumento_medicao WHERE codigo LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                InstrumentosMedicaoBean ub = new InstrumentosMedicaoBean();

                ub.setCodigo(rs.getString("codigo"));

                listub.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(InstrumentosMedicaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InstrumentosMedicaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listub;
    }
    
    public void update(InstrumentosMedicaoBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE instrumento_medicao SET codigo = ?, status = ?, tipo = ?, modelo = ?, serie = ?, capacidade = ?, resolucao = ?, tolerancia = ?, periodicidade = ?, local = ?, nrc = ?, validade = ? WHERE id = ?");

            stmt.setString(1, bb.getCodigo());
            stmt.setString(2, bb.getStatus());
            stmt.setString(3, bb.getTipo());
            stmt.setString(4, bb.getModelo());
            stmt.setString(5, bb.getSerie());
            stmt.setString(6, bb.getCapacidade());
            stmt.setString(7, bb.getResolucao());
            stmt.setString(8, bb.getTolerancia());
            stmt.setInt(9, bb.getPeriodicidade());
            stmt.setString(10, bb.getLocal());
            stmt.setString(11, bb.getNrc());
            stmt.setString(12, bb.getValidade());
            stmt.setInt(13, bb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InstrumentosMedicaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
