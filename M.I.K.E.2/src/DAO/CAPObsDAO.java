/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CAPObsBean;
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
public class CAPObsDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<CAPObsBean> listcob;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listcob = new ArrayList<>();
    }

    public void create(CAPObsBean cob) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO cap_obs (idcap, usuario, data, obs) VALUES (?,?,?,?)");

            stmt.setInt(1, cob.getIdcap());
            stmt.setString(2, cob.getUsuario());
            stmt.setString(3, cob.getData());
            stmt.setString(4, cob.getObs());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<CAPObsBean> read(int idcap) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM cap_obs WHERE idcap = ?");
            stmt.setInt(1, idcap);

            rs = stmt.executeQuery();

            while (rs.next()) {
                CAPObsBean rdcb = new CAPObsBean();

                rdcb.setId(rs.getInt("id"));
                rdcb.setUsuario(rs.getString("usuario"));
                rdcb.setData(rs.getString("data"));
                rdcb.setObs(rs.getString("obs"));

                listcob.add(rdcb);
            }
        } catch (SQLException e) {
            Logger.getLogger(CAPObsDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listcob;
    }

    public boolean checkObs(int idcap) {
        boolean obs = false;

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM cap_obs WHERE idcap = ?");
            stmt.setInt(1, idcap);

            rs = stmt.executeQuery();

            while (rs.next()) {
                obs = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(CAPObsDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(CAPObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return obs;
    }

    public void delete(int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM cap_obs WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao deletar Observação do CAP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
