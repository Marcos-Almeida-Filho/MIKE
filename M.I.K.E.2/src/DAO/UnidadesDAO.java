/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.UnidadesBean;
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
public class UnidadesDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<UnidadesBean> listu;

    UnidadesBean ub;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listu = new ArrayList<>();
    }

    public void create(String nome, String abv) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO unidades (nome, abv) VALUES ('" + nome + "', '" + abv + "')");

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Unidade salva com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao criar Unidade.";
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

    public List<UnidadesBean> read() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM unidades");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ub = new UnidadesBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));
                ub.setAbv(rs.getString("abv"));

                listu.add(ub);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Unidades.";
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
        return listu;
    }

    public List<UnidadesBean> click(int id) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM unidades WHERE id = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ub = new UnidadesBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));
                ub.setAbv(rs.getString("abv"));

                listu.add(ub);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Unidade.";
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
        return listu;
    }

    public int idUnidade(String nome) {
        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT id FROM unidades WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler ID da Unidade.";
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

        return id;
    }

    public void update(String nome, String abv, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE unidades SET nome = '" + nome + "', abv = '" + abv + "' WHERE id = " + id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Unidade.";
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
            stmt = con.prepareStatement("DELETE FROM unidades WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir Unidade.";
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
