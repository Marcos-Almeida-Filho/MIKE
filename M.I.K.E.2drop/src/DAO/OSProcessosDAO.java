/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OSProcessosBean;
import Connection.ConnectionFactory;
import java.awt.HeadlessException;
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
public class OSProcessosDAO {

    public void create(OSProcessosBean mb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO os_processos (idos, processo, inicio, termino, qtdok, qtdnaook, usuario, ordem, disponivel) VALUES (?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, mb.getIdos());
            stmt.setString(2, mb.getProcesso());
            stmt.setString(3, mb.getInicio());
            stmt.setString(4, mb.getTermino());
            stmt.setInt(5, mb.getQtdok());
            stmt.setInt(6, mb.getQtdnaook());
            stmt.setString(7, mb.getUsuario());
            stmt.setInt(8, mb.getOrdem());
            stmt.setString(9, mb.getDisponivel());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar os processos da OS!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<OSProcessosBean> read(String idos) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSProcessosBean> listmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os_processos WHERE idos = ? ORDER BY ordem");
            stmt.setString(1, idos);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSProcessosBean mb = new OSProcessosBean();

                mb.setId(rs.getInt("id"));
                mb.setProcesso(rs.getString("processo"));
                mb.setInicio(rs.getString("inicio"));
                mb.setTermino(rs.getString("termino"));
                mb.setQtdok(rs.getInt("qtdok"));
                mb.setQtdnaook(rs.getInt("qtdnaook"));
                mb.setUsuario(rs.getString("usuario"));
                mb.setOrdem(rs.getInt("ordem"));
                mb.setDisponivel(rs.getString("disponivel"));

                listmb.add(mb);
            }
        } catch (SQLException e) {
            Logger.getLogger(OSProcessosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listmb;
    }

    public List<OSProcessosBean> readprocesso(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSProcessosBean> listmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os_processos WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSProcessosBean mb = new OSProcessosBean();

                mb.setId(rs.getInt("id"));
                mb.setProcesso(rs.getString("processo"));
                mb.setInicio(rs.getString("inicio"));
                mb.setTermino(rs.getString("termino"));
                mb.setQtdok(rs.getInt("qtdok"));
                mb.setQtdnaook(rs.getInt("qtdnaook"));
                mb.setUsuario(rs.getString("usuario"));
                mb.setOrdem(rs.getInt("ordem"));
                mb.setObservacao(rs.getString("observacao"));
                mb.setMotivo(rs.getString("motivo"));
                mb.setDisponivel(rs.getString("disponivel"));

                listmb.add(mb);
            }
        } catch (SQLException e) {
            Logger.getLogger(OSProcessosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listmb;
    }

    public void update(OSProcessosBean mb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os_processos SET termino = ?, qtdok = ?, qtdnaook = ?, observacao = ?, motivo = ? WHERE id = ?");
            stmt.setString(1, mb.getTermino());
            stmt.setInt(2, mb.getQtdok());
            stmt.setInt(3, mb.getQtdnaook());
            stmt.setString(4, mb.getObservacao());
            stmt.setString(5, mb.getMotivo());
            stmt.setInt(6, mb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updatetotal(OSProcessosBean mb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os_processos SET idos = ?, processo = ?, inicio = ?, termino = ?, qtdok = ?, qtdnaook = ?, usuario = ?, ordem = ?, disponivel = ? WHERE id = ?");
            stmt.setString(1, mb.getIdos());
            stmt.setString(2, mb.getProcesso());
            stmt.setString(3, mb.getInicio());
            stmt.setString(4, mb.getTermino());
            stmt.setInt(5, mb.getQtdok());
            stmt.setInt(6, mb.getQtdnaook());
            stmt.setString(7, mb.getUsuario());
            stmt.setInt(8, mb.getOrdem());
            stmt.setString(9, mb.getDisponivel());
            stmt.setInt(10, mb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateinicio(OSProcessosBean mb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os_processos SET inicio = ?, usuario = ? WHERE id = ?");
            stmt.setString(1, mb.getInicio());
            stmt.setString(2, mb.getUsuario());
            stmt.setInt(3, mb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updatedisponivel(OSProcessosBean mb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os_processos SET disponivel = ? WHERE id = ?");
            stmt.setString(1, mb.getDisponivel());
            stmt.setInt(2, mb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar processo!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(OSProcessosBean mb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM os_processos WHERE id = ?");
            stmt.setInt(1, mb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
