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
import java.util.ArrayList;
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
            stmt = con.prepareStatement("INSERT INTO os (idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtd, notes, topo, reconstrucao, raio, frontal) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, osb.getIdtela());
            stmt.setString(2, osb.getDataabertura());
            stmt.setString(3, osb.getDataprevisao());
            stmt.setString(4, osb.getStatus());
            stmt.setString(5, osb.getCliente());
            stmt.setString(6, osb.getDas());
            stmt.setString(7, osb.getCodigo());
            stmt.setString(8, osb.getDesc());
            stmt.setString(9, osb.getQtd());
            stmt.setString(10, osb.getNotes());
            stmt.setString(11, osb.getTopo());
            stmt.setString(12, osb.getReconstrucao());
            stmt.setString(13, osb.getRaio());
            stmt.setString(14, osb.getFrontal());


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
                cb.setDesc(rs.getString("desc"));
                cb.setQtd(rs.getString("qtd"));
                cb.setNotes(rs.getString("notes"));
                cb.setTopo(rs.getString("topo"));
                cb.setReconstrucao(rs.getString("reconstrucao"));
                cb.setRaio(rs.getString("raio"));
                cb.setFrontal(rs.getString("frontal"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtd, notes, topo, reconstrucao, raio, frontal
                
                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
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
                cb.setDesc(rs.getString("desc"));
                cb.setQtd(rs.getString("qtd"));
                cb.setNotes(rs.getString("notes"));
                cb.setTopo(rs.getString("topo"));
                cb.setReconstrucao(rs.getString("reconstrucao"));
                cb.setRaio(rs.getString("raio"));
                cb.setFrontal(rs.getString("frontal"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtd, notes, topo, reconstrucao, raio, frontal
                
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
            stmt = con.prepareStatement("UPDATE os SET dataabertura = ?, dataprevisao = ?, status = ?, cliente = ?, das = ?, codigo = ?, desc = ?, qtd = ?, notes = ?, topo = ?, reconstrucao = ?, raio = ?, frontal = ? WHERE idtela = ?");

            stmt.setString(1, bb.getDataabertura());
            stmt.setString(2, bb.getDataprevisao());
            stmt.setString(3, bb.getStatus());
            stmt.setString(4, bb.getCliente());
            stmt.setString(5, bb.getDas());
            stmt.setString(6, bb.getCodigo());
            stmt.setString(7, bb.getDesc());
            stmt.setString(8, bb.getQtd());
            stmt.setString(9, bb.getNotes());
            stmt.setString(10, bb.getTopo());
            stmt.setString(11, bb.getReconstrucao());
            stmt.setString(12, bb.getRaio());
            stmt.setString(13, bb.getFrontal());
            stmt.setString(14, bb.getIdtela());
            //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtd, notes, topo, reconstrucao, raio, frontal

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
