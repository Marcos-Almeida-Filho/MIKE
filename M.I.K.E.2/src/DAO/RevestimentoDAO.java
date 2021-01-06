/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.RevestimentoBean;
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
public class RevestimentoDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<RevestimentoBean> listr;

    RevestimentoBean rb;

    String table = "revestimentos";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listr = new ArrayList<>();
    }

    public void create(String nome, String codigo) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (nome, codigo) VALUES ('" + nome + "', '" + codigo + "')");

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao cadastrar Revestimento.";
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

    public List<RevestimentoBean> readTodos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table);

            rs = stmt.executeQuery();

            while (rs.next()) {
                rb = new RevestimentoBean();

                rb.setNome(rs.getString("nome"));
                rb.setCodigo(rs.getString("codigo"));

                listr.add(rb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Revestimentos cadastrados.";
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

        return listr;
    }

    public List<RevestimentoBean> readRevestimento(String nome) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                rb = new RevestimentoBean();

                rb.setNome(rs.getString("nome"));
                rb.setCodigo(rs.getString("codigo"));

                listr.add(rb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Revestimentos cadastrados.";
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

        return listr;
    }

    public void update(String nome, String codigo, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET nome = '" + nome + "', codigo = '" + codigo + "' WHERE id = " + id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Revestimento atualizado com sucesso.");
        } catch (SQLException e) {
            String msg = "Erro ao atualizar revestimento.";
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

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM " + table + " WHERE id = " + id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Revestimento excluído com sucesso.");
        } catch (SQLException e) {
            String msg = "Erro ao excluir revestimento.";
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
