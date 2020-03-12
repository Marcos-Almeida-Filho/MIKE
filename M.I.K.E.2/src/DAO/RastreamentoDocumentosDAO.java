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
import java.awt.HeadlessException;
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
            stmt = con.prepareStatement("INSERT INTO rastreamento_doc (cliente, fornecedor, outros, emitente, numero, emissao, nf, conta, outrostipo, outrosdesc, data, cap, entradauser, entradadata, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

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
            stmt.setBoolean(12, rdb.isCap());
            stmt.setString(13, rdb.getEntradauser());
            stmt.setString(14, rdb.getEntradadata());
            stmt.setString(15, rdb.getStatus());

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

    public void createrecebimento(RastreamentoDocumentosBean rdb) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE rastreamento_doc SET recebimentouser = ?, recebimentodata = ? WHERE id = ?");
            stmt.setString(1, rdb.getRecebimentouser());
            stmt.setString(2, rdb.getRecebimentodata());
            stmt.setInt(3, rdb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao criar Histórico de Rastreamento de Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao criar Histórico de Rastreamento de Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void createentrada(RastreamentoDocumentosBean rdb) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE rastreamento_doc SET entradauser = ?, entradadata = ? WHERE id = ?");
            stmt.setString(1, rdb.getEntradauser());
            stmt.setString(2, rdb.getEntradadata());
            stmt.setInt(3, rdb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao criar Histórico de Rastreamento de Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao criar Histórico de Rastreamento de Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void createaprovacao(RastreamentoDocumentosBean rdb) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE rastreamento_doc SET aprovacaouser = ?, aprovacaodata = ? WHERE id = ?");
            stmt.setString(1, rdb.getAprovacaouser());
            stmt.setString(2, rdb.getAprovacaodata());
            stmt.setInt(3, rdb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao criar Histórico de Rastreamento de Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao criar Histórico de Rastreamento de Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void createcap(RastreamentoDocumentosBean rdb) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE rastreamento_doc SET capuser = ?, capdata = ?, status = ? WHERE id = ?");
            stmt.setString(1, rdb.getCapuser());
            stmt.setString(2, rdb.getCapdata());
            stmt.setString(3, rdb.getStatus());
            stmt.setInt(4, rdb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao criar Histórico de Rastreamento de Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao criar Histórico de Rastreamento de Documento!\n" + e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void extornarsempagamento(RastreamentoDocumentosBean rdb) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE rastreamento_doc SET cap = ? , capuser = ?, capdata = ?, status = ? WHERE id = ?");
            stmt.setBoolean(1, rdb.isCap());
            stmt.setString(2, rdb.getCapuser());
            stmt.setString(3, rdb.getCapdata());
            stmt.setString(4, rdb.getStatus());
            stmt.setInt(5, rdb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao criar Histórico de Rastreamento de Documento!\n" + e);
            try {
                SendEmail.EnviarErro("Erro ao criar Histórico de Rastreamento de Documento!\n" + e.toString());
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

    public List<RastreamentoDocumentosBean> readtablestatus(String status) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<RastreamentoDocumentosBean> listrdb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc WHERE status = ?");
            stmt.setString(1, status);
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

    public List<RastreamentoDocumentosBean> readtableaprovado() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<RastreamentoDocumentosBean> listrdb = new ArrayList<>();

        try {
            //SELECT * FROM rastreamento_doc WHERE aprovacaouser <> NULL AND cap <> 1
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc WHERE aprovacaouser IS NOT NULL AND cap = 1 AND capuser IS NULL");
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
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc WHERE capuser IS NULL AND numero LIKE '%" + pesquisa + "%' OR emitente LIKE '%" + pesquisa + "%'");
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

    public List<RastreamentoDocumentosBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<RastreamentoDocumentosBean> listrdb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc WHERE id = ?");
            stmt.setInt(1, id);
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
                rdb.setRecebimentouser(rs.getString("recebimentouser"));
                rdb.setRecebimentodata(rs.getString("recebimentodata"));
                rdb.setEntradauser(rs.getString("entradauser"));
                rdb.setEntradadata(rs.getString("entradadata"));
                rdb.setAprovacaouser(rs.getString("aprovacaouser"));
                rdb.setAprovacaodata(rs.getString("aprovacaodata"));
                rdb.setCapuser(rs.getString("capuser"));
                rdb.setCapdata(rs.getString("capdata"));
                rdb.setCap(rs.getBoolean("cap"));

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

    public boolean readduplicate(String emitente, String numero) {
        boolean b = false;

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc WHERE emitente = ? AND numero = ?");
            stmt.setString(1, emitente);
            stmt.setString(2, numero);
            rs = stmt.executeQuery();

            while (rs.next()) {
                b = true;
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

        return b;
    }

    public void update(RastreamentoDocumentosBean rdb) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE rastreamento_doc SET cliente = ?, fornecedor = ?, outros = ?, emitente = ?, numero = ?, emissao = ?, nf = ?, conta = ?, outrostipo = ?, outrosdesc = ?, cap = ? WHERE id = ?");
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
            stmt.setBoolean(11, rdb.isCap());
            stmt.setInt(12, rdb.getId());

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

    public void delete(RastreamentoDocumentosBean rdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM rastreamento_doc WHERE id = ?");
            stmt.setInt(1, rdb.getId());

            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Documento excluído com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!\n" + e);
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
