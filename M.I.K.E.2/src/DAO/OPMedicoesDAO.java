/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OPMedicoesBean;
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
public class OPMedicoesDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<OPMedicoesBean> listob;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listob = new ArrayList<>();
    }

    public void create(int idprocesso, String medida, String maior, String menor, String instrumento) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO op_medicoes (idprocesso, medida, maior, menor, instrumento) VALUES (" + idprocesso + ", '" + medida + "', '" + maior + "', '" + menor + "', '" + instrumento + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar medições do Processo.";
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

    public List<OPMedicoesBean> readMedicoes(int idprocesso) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM op_medicoes WHERE idprocesso = " + idprocesso);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OPMedicoesBean omb = new OPMedicoesBean();

                omb.setId(rs.getInt("id"));
                omb.setInstrumento(rs.getString("instrumento"));
                omb.setMaior(rs.getString("maior"));
                omb.setMenor(rs.getString("menor"));
                omb.setMedida(rs.getString("medida"));

                listob.add(omb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler medições do processo.";
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

    public List<OPMedicoesBean> readMedicao(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM op_medicoes WHERE id = " + id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OPMedicoesBean omb = new OPMedicoesBean();

                omb.setId(rs.getInt("id"));
                omb.setInstrumento(rs.getString("instrumento"));
                omb.setMaior(rs.getString("maior"));
                omb.setMenor(rs.getString("menor"));
                omb.setMedida(rs.getString("medida"));

                listob.add(omb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler medições do processo.";
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

    public void update(int id, String maior, String menor, String instrumento) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE op_medicoes SET maior = '" + maior + "', menor = '" + menor + "', instrumento = '" + instrumento + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Inspeção do Processo.";
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
