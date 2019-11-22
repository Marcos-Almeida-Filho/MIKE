/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.InsumosMovBean;
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
public class InsumosMovDAO {
 
    public void create(InsumosMovBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO insumos_mov (idinsumo, data, tipomov, qtdinicial, qtdmov, qtdfinal, funcionario) VALUES (?,?,?,?,?,?,?)");

            stmt.setInt(1, bb.getIdinsumo());
            stmt.setString(2, bb.getData());
            stmt.setString(3, bb.getTipomov());
            stmt.setDouble(4, bb.getQtdinicial());
            stmt.setDouble(5, bb.getQtdmov());
            stmt.setDouble(6, bb.getQtdfinal());
            stmt.setString(7, bb.getFuncionario());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar movimentação do insumo!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosMovDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<InsumosMovBean> read(int idinsumo) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<InsumosMovBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos_mov WHERE idinsumo = ?");
            stmt.setInt(1, idinsumo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                InsumosMovBean cb = new InsumosMovBean();

                cb.setId(rs.getInt("id"));
                cb.setIdinsumo(rs.getInt("idinsumo"));
                cb.setData(rs.getString("data"));
                cb.setTipomov(rs.getString("tipomov"));
                cb.setQtdinicial(rs.getDouble("qtdinicial"));
                cb.setQtdmov(rs.getDouble("qtdmov"));
                cb.setQtdfinal(rs.getDouble("qtdfinal"));
                cb.setFuncionario(rs.getString("funcionario"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(InsumosMovDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosMovDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
}
