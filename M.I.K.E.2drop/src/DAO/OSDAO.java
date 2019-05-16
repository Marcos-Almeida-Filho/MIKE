/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OSBean;
import Connection.ConnectionFactory;
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
public class OSDAO {

    public void create(OSBean osb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO os (idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, completa, raio, frontal) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, osb.getIdtela());
            stmt.setString(2, osb.getDataabertura());
            stmt.setString(3, osb.getDataprevisao());
            stmt.setString(4, osb.getStatus());
            stmt.setString(5, osb.getCliente());
            stmt.setString(6, osb.getDas());
            stmt.setString(7, osb.getCodigo());
            stmt.setString(8, osb.getDescricao());
            stmt.setInt(9, osb.getQtdinicial());
            stmt.setInt(10, osb.getQtdok());
            stmt.setInt(11, osb.getQtdnaook());
            stmt.setString(12, osb.getNotes());
            stmt.setString(13, osb.getTopo());
            stmt.setString(14, osb.getReconstrucao());
            stmt.setString(15, osb.getCompleta());
            stmt.setString(16, osb.getRaio());
            stmt.setString(17, osb.getFrontal());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<OSBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean cb = new OSBean();

                cb.setId(rs.getInt("id"));
                cb.setIdtela(rs.getString("idtela"));
                cb.setDataabertura(rs.getString("dataabertura"));
                cb.setDataprevisao(rs.getString("dataprevisao"));
                cb.setStatus(rs.getString("status"));
                cb.setCliente(rs.getString("cliente"));
                cb.setDas(rs.getString("das"));
                cb.setCodigo(rs.getString("codigo"));
                cb.setDescricao(rs.getString("descricao"));
                cb.setQtdinicial(rs.getInt("qtdinicial"));
                cb.setQtdok(rs.getInt("qtdok"));
                cb.setQtdnaook(rs.getInt("qtdnaook"));
                cb.setNotes(rs.getString("notes"));
                cb.setTopo(rs.getString("topo"));
                cb.setReconstrucao(rs.getString("reconstrucao"));
                cb.setCompleta(rs.getString("completa"));
                cb.setRaio(rs.getString("raio"));
                cb.setFrontal(rs.getString("frontal"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public List<OSBean> reademaberto() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE status = 'Rascunho' OR status = 'Ativo'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean cb = new OSBean();

                cb.setId(rs.getInt("id"));
                cb.setIdtela(rs.getString("idtela"));
                cb.setDataabertura(rs.getString("dataabertura"));
                cb.setDataprevisao(rs.getString("dataprevisao"));
                cb.setStatus(rs.getString("status"));
                cb.setCliente(rs.getString("cliente"));
                cb.setDas(rs.getString("das"));
                cb.setCodigo(rs.getString("codigo"));
                cb.setDescricao(rs.getString("descricao"));
                cb.setQtdinicial(rs.getInt("qtdinicial"));
                cb.setQtdok(rs.getInt("qtdok"));
                cb.setQtdnaook(rs.getInt("qtdnaook"));
                cb.setNotes(rs.getString("notes"));
                cb.setTopo(rs.getString("topo"));
                cb.setReconstrucao(rs.getString("reconstrucao"));
                cb.setCompleta(rs.getString("completa"));
                cb.setRaio(rs.getString("raio"));
                cb.setFrontal(rs.getString("frontal"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public List<OSBean> readstatus(String status) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE status = ?");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean cb = new OSBean();

                cb.setId(rs.getInt("id"));
                cb.setIdtela(rs.getString("idtela"));
                cb.setDataabertura(rs.getString("dataabertura"));
                cb.setDataprevisao(rs.getString("dataprevisao"));
                cb.setStatus(rs.getString("status"));
                cb.setCliente(rs.getString("cliente"));
                cb.setDas(rs.getString("das"));
                cb.setCodigo(rs.getString("codigo"));
                cb.setDescricao(rs.getString("descricao"));
                cb.setQtdinicial(rs.getInt("qtdinicial"));
                cb.setQtdok(rs.getInt("qtdok"));
                cb.setQtdnaook(rs.getInt("qtdnaook"));
                cb.setNotes(rs.getString("notes"));
                cb.setTopo(rs.getString("topo"));
                cb.setReconstrucao(rs.getString("reconstrucao"));
                cb.setCompleta(rs.getString("completa"));
                cb.setRaio(rs.getString("raio"));
                cb.setFrontal(rs.getString("frontal"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public List<OSBean> readcreated(String codigo, String dataabertura) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listsp = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE codigo = ? AND dataabertura = ?");
            stmt.setString(1, codigo);
            stmt.setString(2, dataabertura);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean spb = new OSBean();

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

    public Boolean readnome() throws SQLException {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        Calendar c = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(c.getTime());
        String idtela = "OS" + year + "-0001";

        Boolean resp = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE idtela = ?");
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

    public List<OSBean> click(String idtela) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE idtela = ?");
            stmt.setString(1, idtela);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean cb = new OSBean();

                cb.setDataabertura(rs.getString("dataabertura"));
                cb.setDataprevisao(rs.getString("dataprevisao"));
                cb.setStatus(rs.getString("status"));
                cb.setCliente(rs.getString("cliente"));
                cb.setDas(rs.getString("das"));
                cb.setCodigo(rs.getString("codigo"));
                cb.setDescricao(rs.getString("descricao"));
                cb.setQtdinicial(rs.getInt("qtdinicial"));
                cb.setQtdok(rs.getInt("qtdok"));
                cb.setQtdnaook(rs.getInt("qtdnaook"));
                cb.setNotes(rs.getString("notes"));
                cb.setTopo(rs.getString("topo"));
                cb.setReconstrucao(rs.getString("reconstrucao"));
                cb.setCompleta(rs.getString("completa"));
                cb.setRaio(rs.getString("raio"));
                cb.setFrontal(rs.getString("frontal"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public void update(OSBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os SET dataabertura = ?, dataprevisao = ?, status = ?, cliente = ?, das = ?, codigo = ?, descricao = ?, qtdinicial = ?, qtdok = ?, qtdnaook = ?, notes = ?, topo = ?, reconstrucao = ?, completa = ?, raio = ?, frontal = ? WHERE idtela = ?");

            stmt.setString(1, bb.getDataabertura());
            stmt.setString(2, bb.getDataprevisao());
            stmt.setString(3, bb.getStatus());
            stmt.setString(4, bb.getCliente());
            stmt.setString(5, bb.getDas());
            stmt.setString(6, bb.getCodigo());
            stmt.setString(7, bb.getDescricao());
            stmt.setInt(8, bb.getQtdinicial());
            stmt.setInt(9, bb.getQtdok());
            stmt.setInt(10, bb.getQtdnaook());
            stmt.setString(11, bb.getNotes());
            stmt.setString(12, bb.getTopo());
            stmt.setString(13, bb.getReconstrucao());
            stmt.setString(14, bb.getCompleta());
            stmt.setString(15, bb.getRaio());
            stmt.setString(16, bb.getFrontal());
            stmt.setString(17, bb.getIdtela());
            //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updatestatus(OSBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os SET status = ? WHERE idtela = ?");

            stmt.setString(1, bb.getStatus());
            stmt.setString(2, bb.getIdtela());
            //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updateqtd(OSBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os SET qtdok = ?, qtdnaook = ? WHERE idtela = ?");

            stmt.setInt(1, bb.getQtdok());
            stmt.setInt(2, bb.getQtdnaook());
            stmt.setString(3, bb.getIdtela());
            //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
