/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.TopoBean;
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
public class TopoDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<TopoBean> listt;

    TopoBean tb;

    String table = "topos";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listt = new ArrayList<>();
    }

    public void create(String nome) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (nome) VALUES ('" + nome + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar Topo.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<TopoBean> readTodos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table);

            rs = stmt.executeQuery();

            while (rs.next()) {
                tb = new TopoBean();

                tb.setNome(rs.getString("nome"));

                listt.add(tb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Topos cadastrados.";
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

        return listt;
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM " + table + " WHERE id = " + id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excluído com sucesso.");
        } catch (SQLException e) {
            String msg = "Erro ao excluir Topo.";
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
