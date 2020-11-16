/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OPProcessosBean;
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
public class OPProcessosDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<OPProcessosBean> listob;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listob = new ArrayList<>();
    }

    /**
     *
     * @param op
     * @param processo
     * @param qtdtotal
     */
    public void create(String op, String processo, double qtdtotal) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO op_processo (op, processo, qtdtotal) VALUES ('" + op + "','" + processo + "'," + qtdtotal + ")");
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar processo da OP.";
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

    /**
     *
     * @param op
     * @return
     */
    public List<OPProcessosBean> readProcessos(String op) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM op_processo WHERE op = '" + op + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OPProcessosBean opb = new OPProcessosBean();

                opb.setId(rs.getInt("id"));
                opb.setProcesso(rs.getString("processo"));
                opb.setUser(rs.getString("user"));
                opb.setDatainicio(rs.getString("datainicio"));
                opb.setDatafim(rs.getString("datafim"));
                opb.setQtdtotal(rs.getDouble("qtdtotal"));
                opb.setQtdok(rs.getDouble("qtdok"));
                opb.setQtdnaook(rs.getDouble("qtdnaook"));
                opb.setObs(rs.getString("obs"));

                listob.add(opb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler processos da OP.";
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

        return listob;
    }

    public List<OPProcessosBean> readProcesso(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM op_processo WHERE id = '" + id + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OPProcessosBean opb = new OPProcessosBean();

                opb.setProcesso(rs.getString("processo"));
                opb.setUser(rs.getString("user"));
                opb.setDatainicio(rs.getString("datainicio"));
                opb.setDatafim(rs.getString("datafim"));
                opb.setQtdtotal(rs.getDouble("qtdtotal"));
                opb.setQtdok(rs.getDouble("qtdok"));
                opb.setQtdnaook(rs.getDouble("qtdnaook"));
                opb.setObs(rs.getString("obs"));
                opb.setMotivo(rs.getString("motivo"));

                listob.add(opb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler processos da OP.";
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

        return listob;
    }

    public int idUltimoProcesso(String op, String processo) {
        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT id FROM op_processo WHERE op = '" + op + "' AND processo = '" + processo + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler ID do processo.";
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

        return id;
    }

    /**
     *
     * @param id
     * @param dataInicio
     * @param user
     */
    public void inicioProcesso(int id, String dataInicio, String user) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE op_processo SET datainicio = '" + dataInicio + "', user = '" + user + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao dar início ao processo.";
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

    /**
     *
     * @param id
     * @param dataFim
     * @param qtdok
     * @param qtdnaook
     * @param obs
     */
    public void fecharProcesso(int id, String dataFim, double qtdok, double qtdnaook, String obs) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE op_processo SET datafim = '" + dataFim + "', qtdok = " + qtdok + ", qtdnaook = " + qtdnaook + ", obs = '" + obs + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao fechar processo da OP.";
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

    public void lancarMotivo(int id, String motivo) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE op_processo SET motivo = '" + motivo + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao fechar processo da OP.";
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
