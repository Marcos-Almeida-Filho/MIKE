/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ProcessosVendasMedicoesBean;
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
public class ProcessosVendasMedicoesDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ProcessosVendasMedicoesBean> listpvmb;

    ProcessosVendasMedicoesBean pvmb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listpvmb = new ArrayList<>();
    }

    public void create(int idProcesso, String medida) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO processos_vendas_medicoes (idprocesso, medida) VALUES (" + idProcesso + ", '" + medida + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar Medições do Processo de Venda.";
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

    public List<ProcessosVendasMedicoesBean> readMedidas(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM processos_vendas_medicoes WHERE idprocesso = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                pvmb = new ProcessosVendasMedicoesBean();

                pvmb.setId(rs.getInt("id"));
                pvmb.setMedida(rs.getString("medida"));

                listpvmb.add(pvmb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Medições do Processo de Venda.";
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

        return listpvmb;
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM processos_vendas_medicoes WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao deletar Medida do Processo de Venda.";
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
