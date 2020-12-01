/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OPDocBean;
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
public class OPDocDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<OPDocBean> listob;

    OPDocBean odb;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listob = new ArrayList<>();
    }

    public void create(String op, String descricao, String local) throws SQLException {
        conStmt();

        String local2 = local.replace("\\", "\\\\");

        stmt = con.prepareStatement("INSERT INTO op_docs (op, descricao, local) VALUES ('" + op + "', '" + descricao + "', '" + local2 + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<OPDocBean> read(String op) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM op_docs WHERE op = '" + op + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                odb = new OPDocBean();

                odb.setId(rs.getInt("id"));
                odb.setDescricao(rs.getString("descricao"));
                odb.setLocal(rs.getString("local"));

                listob.add(odb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Arquivos da OP.";
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

        return listob;
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM op_docs WHERE id = " + id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir documento da OP.";
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
