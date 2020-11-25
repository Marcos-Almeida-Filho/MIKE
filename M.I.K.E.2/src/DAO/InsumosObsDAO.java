/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.InsumosObsBean;
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
public class InsumosObsDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<InsumosObsBean> listiob;

    InsumosObsBean iob;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listiob = new ArrayList<>();
    }

    public void create(int idInsumo, String data, String funcionario, String obs) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO insumos_obs (idinsumo, data, funcionario, obs) VALUES (" + idInsumo + ", '" + data + "', '" + funcionario + "', '" + obs + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar as Observações do Insumo.";
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

    public List<InsumosObsBean> read(int idinsumo) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos_obs WHERE idinsumo = ?");
            stmt.setInt(1, idinsumo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                iob = new InsumosObsBean();

                iob.setId(rs.getInt("id"));
                iob.setIdinsumo(rs.getInt("idinsumo"));
                iob.setData(rs.getString("data"));
                iob.setFuncionario(rs.getString("funcionario"));
                iob.setObs(rs.getString("obs"));

                listiob.add(iob);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Observações do Insumo.";
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
        return listiob;
    }

    public void delete(int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir Observação do Insumo.";
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
