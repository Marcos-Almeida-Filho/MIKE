/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.F_UP_HistBean;
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
public class F_UP_HistDAO {
    
    Connection con;

    PreparedStatement stmt;
    
    ResultSet rs;

    List<F_UP_HistBean> listbb;
    
    public void conStmt() {
        con = ConnectionFactory.getConnection();
        
        stmt = null;
    }
    
    public void rsList() {
        conStmt();
        
        rs = null;
        
        listbb = new ArrayList<>();
    }
    
    public void create(F_UP_HistBean fhb) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO f_up_hist (idfup, processo, funcionario, data) VALUES (?,?,?,?)");

            stmt.setInt(1, fhb.getIdfup());
            stmt.setString(2, fhb.getProcesso());
            stmt.setString(3, fhb.getFuncionario());
            stmt.setString(4, fhb.getData());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro em " + this.getClass().getSimpleName() + " - create");
            JOptionPane.showMessageDialog(null, "Erro ao salvar OP no Follow Up!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UP_HistDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public List<F_UP_HistBean> click(int id) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up_hist WHERE idfup = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                F_UP_HistBean cb = new F_UP_HistBean();

                cb.setId(rs.getInt("id"));
                cb.setProcesso(rs.getString("processo"));
                cb.setFuncionario(rs.getString("funcionario"));
                cb.setData(rs.getString("data"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro em " + this.getClass().getSimpleName() + " - click");
            Logger.getLogger(F_UP_HistDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UP_HistDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public int getId(int idfup, String processo) {
        rsList();
        
        int id = 0;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM f_up_hist WHERE idfup = " + idfup + " AND processo = '" + processo + "'");
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro em " + this.getClass().getSimpleName() + " - getId");
            JOptionPane.showMessageDialog(null,"Erro ao resgatar id do F-UP.");
            SendEmail.EnviarErro2(e.toString());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return id;
    }
    
    public void update(String funcionario, String data, int idfup, String processo) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE f_up_hist SET funcionario = '" + funcionario + "', data = '" + data + "' WHERE idfup = " + idfup + " AND processo = '" + processo + "' AND funcionario IS NULL");

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro em " + this.getClass().getSimpleName() + " - update");
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UP_HistDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
