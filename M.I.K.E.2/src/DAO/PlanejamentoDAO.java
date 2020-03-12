/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.PlanejamentoBean;
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
public class PlanejamentoDAO {

    public List<PlanejamentoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<PlanejamentoBean> listpb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM planejamento");
            rs = stmt.executeQuery();

            while (rs.next()) {
                PlanejamentoBean pb = new PlanejamentoBean();

                pb.setPlanejamento(rs.getDouble("planejamento"));
                pb.setAtingidomensal(rs.getDouble("atingidomensal"));
                pb.setAtingido1(rs.getDouble("atingido1"));
                pb.setAtingido2(rs.getDouble("atingido2"));
                pb.setAtingido3(rs.getDouble("atingido3"));

                listpb.add(pb);
            }
        } catch (SQLException e) {
            Logger.getLogger(PlanejamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(PlanejamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpb;
    }

    public void update(PlanejamentoBean pb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE planejamento SET planejamento = ? WHERE id = 1");

            stmt.setDouble(1, pb.getPlanejamento());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(PlanejamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updateAtingido(PlanejamentoBean pb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE planejamento SET atingidomensal = ?, atingido1 = ?, atingido2 = ?, atingido3 = ? WHERE id = 1");

            stmt.setDouble(1, pb.getAtingidomensal());
            stmt.setDouble(2, pb.getAtingido1());
            stmt.setDouble(3, pb.getAtingido2());
            stmt.setDouble(4, pb.getAtingido3());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(PlanejamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void zerarAtingido(PlanejamentoBean pb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE planejamento SET atingidomensal = ?, atingido1 = ?, atingido2 = ?, atingido3 = ? WHERE id = 1");

            stmt.setDouble(1, pb.getAtingidomensal());
            stmt.setDouble(2, pb.getAtingido1());
            stmt.setDouble(3, pb.getAtingido2());
            stmt.setDouble(4, pb.getAtingido3());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(PlanejamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
