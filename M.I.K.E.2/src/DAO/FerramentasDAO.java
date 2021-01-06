/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.FerramentasBean;
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
public class FerramentasDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<FerramentasBean> listf;

    FerramentasBean fb;

    String table = "ferramentas";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listf = new ArrayList<>();
    }

    public void create(String nome, String descricao) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO " + table + " (nome, descricao) VALUES ('" + nome + "', '" + descricao + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<FerramentasBean> readTodos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table);

            rs = stmt.executeQuery();

            while (rs.next()) {
                fb = new FerramentasBean();

                fb.setId(rs.getInt("id"));
                fb.setNome(rs.getString("nome"));
                fb.setDescricao(rs.getString("descricao"));

                listf.add(fb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Ferramentas cadastradas.";
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

        return listf;
    }

    public List<FerramentasBean> readFerramenta(String nome) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                fb = new FerramentasBean();

                fb.setId(rs.getInt("id"));
                fb.setNome(rs.getString("nome"));
                fb.setDescricao(rs.getString("descricao"));

                listf.add(fb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Ferramentas cadastradas.";
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

        return listf;
    }

    public void update(String nome, String descricao, int idFerramenta) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("UPDATE " + table + " SET nome = '" + nome + "', descricao = '" + descricao + "' WHERE id = " + idFerramenta);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }
}
