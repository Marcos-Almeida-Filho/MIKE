/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CartoesBean;
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
public class CartoesDAO {

    public void create(CartoesBean cb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO cartoes (responsavel, numero, limite, diavencimento, banco, status) VALUES (?,?,?,?,?,?)");

            stmt.setString(1, cb.getIdentificacao());
            stmt.setString(2, cb.getNumero());
            stmt.setDouble(3, cb.getLimite());
            stmt.setInt(4, cb.getDiavencimento());
            stmt.setString(5, cb.getBanco());
            stmt.setString(6, cb.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CartoesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<CartoesBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CartoesBean> listcb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM cartoes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                CartoesBean cb = new CartoesBean();

                cb.setId(rs.getInt("id"));
                cb.setIdentificacao(rs.getString("responsavel"));
                cb.setNumero(rs.getString("numero"));
                cb.setLimite(rs.getInt("limite"));
                cb.setDiavencimento(rs.getInt("diavencimento"));
                cb.setBanco(rs.getString("banco"));
                cb.setStatus(rs.getString("status"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(CartoesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CartoesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcb;
    }

    public List<CartoesBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CartoesBean> listcb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM cartoes WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CartoesBean cb = new CartoesBean();

                cb.setId(rs.getInt("id"));
                cb.setIdentificacao(rs.getString("responsavel"));
                cb.setNumero(rs.getString("numero"));
                cb.setLimite(rs.getInt("limite"));
                cb.setDiavencimento(rs.getInt("diavencimento"));
                cb.setBanco(rs.getString("banco"));
                cb.setStatus(rs.getString("status"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(CartoesDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CartoesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcb;
    }

    public void update(CartoesBean cb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE cartoes SET responsavel = ?, numero = ?, limite = ?, diavencimento = ?, banco = ? WHERE id = ?");

            stmt.setString(1, cb.getIdentificacao());
            stmt.setString(2, cb.getNumero());
            stmt.setDouble(3, cb.getLimite());
            stmt.setInt(4, cb.getDiavencimento());
            stmt.setString(5, cb.getBanco());
            stmt.setInt(6, cb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CartoesDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
