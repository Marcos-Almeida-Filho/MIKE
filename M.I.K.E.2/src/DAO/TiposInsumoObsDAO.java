/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.TiposInsumoObsBean;
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
public class TiposInsumoObsDAO {
    
    public void create(TiposInsumoObsBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO tipos_insumo_obs (idtipoinsumo, data, funcionario, obs) VALUES (?,?,?,?)");

            stmt.setInt(1, bb.getIdtipoinsumo());
            stmt.setString(2, bb.getData());
            stmt.setString(3, bb.getFuncionario());
            stmt.setString(4, bb.getObs());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar as observações do tipo de insumo!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(TiposInsumoObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<TiposInsumoObsBean> read(int idtipoinsumo) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<TiposInsumoObsBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tipos_insumo_obs WHERE idtipoinsumo = ?");
            stmt.setInt(1, idtipoinsumo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TiposInsumoObsBean iob = new TiposInsumoObsBean();

                iob.setId(rs.getInt("id"));
                iob.setIdtipoinsumo(rs.getInt("idtipoinsumo"));
                iob.setData(rs.getString("data"));
                iob.setFuncionario(rs.getString("funcionario"));
                iob.setObs(rs.getString("obs"));

                listbb.add(iob);
            }
        } catch (SQLException e) {
            Logger.getLogger(TiposInsumoObsDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(TiposInsumoObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
}
