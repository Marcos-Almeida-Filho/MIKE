/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CARObsBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class CARObsDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<CARObsBean> list;

    CARObsBean bean;

    String table = "carobs";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        list = new ArrayList<>();
    }

    public void create(int idCar, String data, String usuario, String obs) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO " + table + " (idCar, data, usuario, obs) VALUES (" + idCar + ", '" + data + "', '" + usuario + "', '" + obs + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<CARObsBean> readObs(int idCar) {
        rsList();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE idCar = " + idCar);
            
            rs = stmt.executeQuery();
            
            while(rs.next()) {
                bean = new CARObsBean();
                
                bean.setId(rs.getInt("id"));
                bean.setData(rs.getString("data"));
                bean.setUsuario(rs.getString("usuario"));
                bean.setObs(rs.getString("obs"));
                
                list.add(bean);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler comentários da Conta a Receber.";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return list;
    }
}
