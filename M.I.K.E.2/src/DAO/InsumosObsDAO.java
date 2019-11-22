/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.InsumosObsBean;
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
public class InsumosObsDAO {
    
    public void create(InsumosObsBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO insumos_obs (idinsumo, data, funcionario, obs) VALUES (?,?,?,?)");

            stmt.setInt(1, bb.getIdinsumo());
            stmt.setString(2, bb.getData());
            stmt.setString(3, bb.getFuncionario());
            stmt.setString(4, bb.getObs());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar as observações do insumo!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<InsumosObsBean> read(int idinsumo) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<InsumosObsBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos_obs WHERE idinsumo = ?");
            stmt.setInt(1, idinsumo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                InsumosObsBean iob = new InsumosObsBean();

                iob.setId(rs.getInt("id"));
                iob.setIdinsumo(rs.getInt("idinsumo"));
                iob.setData(rs.getString("data"));
                iob.setFuncionario(rs.getString("funcionario"));
                iob.setObs(rs.getString("obs"));

                listbb.add(iob);
            }
        } catch (SQLException e) {
            Logger.getLogger(InsumosObsDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
}
