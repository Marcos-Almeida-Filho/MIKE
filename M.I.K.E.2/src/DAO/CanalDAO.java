/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CanalBean;
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
public class CanalDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<CanalBean> listc;

    CanalBean cb;

    String table = "canais";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listc = new ArrayList<>();
    }

    public void create(String nome) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO " + table + " (nome) VALUES ('" + nome + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<CanalBean> readTodos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table);

            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CanalBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));

                listc.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Canais cadastrados.";
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

        return listc;
    }
    
    public List<CanalBean> readCanal(String canal) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = '" + canal + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CanalBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));

                listc.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Canais cadastrados.";
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

        return listc;
    }

    public void delete(int id) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("DELETE FROM " + table + " WHERE id = " + id);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }
}
