/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.RastreamentoDocumentosBean;
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
public class RastreamentoDocumentosDAO {

    //Variáveis
    public void create(RastreamentoDocumentosBean rdb) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO rastreamento_doc (cliente, fornecedor, outros, emitente, numero, emissao, nf, conta, outrostipo, outrosdesc, data) VALUES (?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setBoolean(1, rdb.isCliente());
            stmt.setBoolean(2, rdb.isFornecedor());
            stmt.setBoolean(3, rdb.isOutros());
            stmt.setString(4, rdb.getEmitente());
            stmt.setString(5, rdb.getNumero());
            stmt.setString(6, rdb.getEmissao());
            stmt.setBoolean(7, rdb.isNf());
            stmt.setBoolean(8, rdb.isConta());
            stmt.setBoolean(9, rdb.isOutrostipo());
            stmt.setString(10, rdb.getOutrosdesc());
            stmt.setString(11, rdb.getData());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao criar Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao criar Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<RastreamentoDocumentosBean> readtable() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<RastreamentoDocumentosBean> listrdb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc");
            rs = stmt.executeQuery();

            while (rs.next()) {
                RastreamentoDocumentosBean rdb = new RastreamentoDocumentosBean();

                rdb.setId(rs.getInt("id"));
                rdb.setEmitente(rs.getString("emitente"));
                rdb.setNumero(rs.getString("numero"));

                listrdb.add(rdb);
            }
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao ler Documentos lançados!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao ler Documentos lançados!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listrdb;
    }

    public List<RastreamentoDocumentosBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<RastreamentoDocumentosBean> listrdb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc WHERE numero LIKE '%" + pesquisa + "%' OR emitente LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                RastreamentoDocumentosBean rdb = new RastreamentoDocumentosBean();

                rdb.setId(rs.getInt("id"));
                rdb.setNumero(rs.getString("numero"));
                rdb.setEmitente(rs.getString("emitente"));

                listrdb.add(rdb);
            }
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao ler Documentos lançados!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao ler Documentos lançados!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listrdb;
    }

    public List<RastreamentoDocumentosBean> click(int iddoc) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<RastreamentoDocumentosBean> listrdb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc WHERE id = ?");
            stmt.setInt(1, iddoc);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RastreamentoDocumentosBean rdb = new RastreamentoDocumentosBean();

                rdb.setId(rs.getInt("id"));
                rdb.setCliente(rs.getBoolean("cliente"));
                rdb.setFornecedor(rs.getBoolean("fornecedor"));
                rdb.setOutros(rs.getBoolean("outros"));
                rdb.setEmitente(rs.getString("emitente"));
                rdb.setNumero(rs.getString("numero"));
                rdb.setEmissao(rs.getString("emissao"));
                rdb.setNf(rs.getBoolean("nf"));
                rdb.setConta(rs.getBoolean("conta"));
                rdb.setOutrostipo(rs.getBoolean("outrostipo"));
                rdb.setOutrosdesc(rs.getString("outrosdesc"));
                rdb.setXml(rs.getString("xml"));

                listrdb.add(rdb);
            }
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao ler Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao ler Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listrdb;
    }

    public List<RastreamentoDocumentosBean> readcreated(String data) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<RastreamentoDocumentosBean> listrdb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc WHERE data = ?");
            stmt.setString(1, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RastreamentoDocumentosBean rdb = new RastreamentoDocumentosBean();

                rdb.setId(rs.getInt("id"));

                listrdb.add(rdb);
            }
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao recuperar ID do Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao recuperar ID do Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listrdb;
    }

    public void update(RastreamentoDocumentosBean rdb) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE rastreamento_doc SET cliente = ?, fornecedor = ?, outros = ?, emitente = ?, numero = ?, emissao = ?, nf = ?, conta = ?, outrostipo = ?, outrosdesc = ? WHERE id = ?");
            stmt.setBoolean(1, rdb.isCliente());
            stmt.setBoolean(2, rdb.isFornecedor());
            stmt.setBoolean(3, rdb.isOutros());
            stmt.setString(4, rdb.getEmitente());
            stmt.setString(5, rdb.getEmitente());
            stmt.setString(6, rdb.getEmitente());
            stmt.setBoolean(7, rdb.isNf());
            stmt.setBoolean(8, rdb.isConta());
            stmt.setBoolean(9, rdb.isOutrostipo());
            stmt.setString(10, rdb.getOutrosdesc());
            stmt.setInt(11, rdb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao atualizar Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao atualizar Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updatexml(RastreamentoDocumentosBean rdb) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE rastreamento_doc SET xml = ? WHERE id = ?");
            stmt.setString(1, rdb.getXml());
            stmt.setInt(2, rdb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao lançar xml do Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao lançar xml do Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
