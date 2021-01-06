/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.MPBean;
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
public class MPDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<MPBean> listm;

    MPBean mpb;

    String table = "mps";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listm = new ArrayList<>();
    }

    public void create(String nome, String codigo, String descricao) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (nome, codigo, descricao) VALUES ('" + nome + "', '" + codigo + "', '" + descricao + "')");

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Matéria prima cadastrada com sucesso.");
        } catch (SQLException e) {
            String msg = "Erro ao cadastrar matéria prima.";
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

    public List<MPBean> read() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table);

            rs = stmt.executeQuery();

            while (rs.next()) {
                mpb = new MPBean();

                mpb.setId(rs.getInt("id"));
                mpb.setNome(rs.getString("nome"));
                mpb.setCodigo(rs.getString("codigo"));
                mpb.setDescricao(rs.getString("descricao"));

                listm.add(mpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Matérias primas cadastradas.";
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

        return listm;
    }

    public List<MPBean> readMP(String nome) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                mpb = new MPBean();

                mpb.setId(rs.getInt("id"));
                mpb.setNome(rs.getString("nome"));
                mpb.setCodigo(rs.getString("codigo"));
                mpb.setDescricao(rs.getString("descricao"));

                listm.add(mpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Matéria prima cadastrada.";
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

        return listm;
    }

    public void update(String nome, String codigo, String descricao, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET nome = '" + nome + "', codigo = '" + codigo + "', descricao = '" + descricao + "' WHERE id = " + id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Matéria prima.";
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

            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao excluir matéria prima.";
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
