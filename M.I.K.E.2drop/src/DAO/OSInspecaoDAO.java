/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OSInspecaoBean;
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
public class OSInspecaoDAO {

    public void create(OSInspecaoBean oib) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO os_inspecao (idos, idprocesso, processo, medida, medidamaior, medidamenor, funcionario, instrumento) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setString(1, oib.getIdos());
            stmt.setString(2, oib.getIdprocesso());
            stmt.setString(3, oib.getProcesso());
            stmt.setString(4, oib.getMedida());
            stmt.setString(5, oib.getMedidamaior());
            stmt.setString(6, oib.getMedidamenor());
            stmt.setString(7, oib.getFuncionario());
            stmt.setString(8, oib.getInstrumento());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<OSInspecaoBean> read(String idprocesso) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSInspecaoBean> listmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os_inspecao WHERE idprocesso = ?");
            stmt.setString(1, idprocesso);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSInspecaoBean mb = new OSInspecaoBean();


                mb.setId(rs.getInt("id"));
                mb.setIdos(rs.getString("idos"));
                mb.setIdprocesso(rs.getString("idprocesso"));
                mb.setProcesso(rs.getString("processo"));
                mb.setMedida(rs.getString("medida"));
                mb.setMedidamaior(rs.getString("medidamaior"));
                mb.setMedidamenor(rs.getString("medidamenor"));
                mb.setFuncionario(rs.getString("funcionario"));
                mb.setInstrumento(rs.getString("instrumento"));

                listmb.add(mb);
            }
        } catch (SQLException e) {
            Logger.getLogger(OSInspecaoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listmb;
    }
    
    public List<OSInspecaoBean> reados(String idos) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSInspecaoBean> listmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os_inspecao WHERE idos = ?");
            stmt.setString(1, idos);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSInspecaoBean mb = new OSInspecaoBean();


                mb.setId(rs.getInt("id"));
                mb.setIdos(rs.getString("idos"));
                mb.setIdprocesso(rs.getString("idprocesso"));
                mb.setProcesso(rs.getString("processo"));
                mb.setMedida(rs.getString("medida"));
                mb.setMedidamaior(rs.getString("medidamaior"));
                mb.setMedidamenor(rs.getString("medidamenor"));
                mb.setFuncionario(rs.getString("funcionario"));
                mb.setInstrumento(rs.getString("instrumento"));

                listmb.add(mb);
            }
        } catch (SQLException e) {
            Logger.getLogger(OSInspecaoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listmb;
    }
}
