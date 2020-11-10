/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OPMPBean;
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
public class OPMPDAO {

    Connection con;
    PreparedStatement stmt;
    ResultSet rs;
    List<OPMPBean> listopmp;
    OPMPBean omb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listopmp = new ArrayList<>();
    }

    public void create(String op, String codigo, String descricao, double qtd) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO op_mp (op, codigo, descricao, qtd) VALUES ('" + op + "', '" + codigo + "', '" + descricao + "', " + qtd + ")");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar Matéria Prima da OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<OPMPBean> readMP(String op) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM op_mp WHERE op = '" + op + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                omb = new OPMPBean();

                omb.setCodigo(rs.getString("codigo"));
                omb.setDescricao(rs.getString("descricao"));
                omb.setQtd(rs.getDouble("qtd"));
                omb.setBaixa(rs.getBoolean("baixa"));

                listopmp.add(omb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Matéria da Prima da OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listopmp;
    }

    public void updateBaixa(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE op_mp SET baixa = true WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar matéria prima da OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM op_mp WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao deletar Matéria Prima da OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
