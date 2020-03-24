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

    public void create(CAPObsBean cob) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

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

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<CAPObsBean> listbb = new ArrayList<>();

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

                listbb.add(rdcb);
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
        return listbb;
    }

    public boolean checkObs(int idcap) {
        boolean obs = false;

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

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
}
