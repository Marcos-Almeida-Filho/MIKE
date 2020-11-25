/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.MedicoesBean;
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
public class MedicoesDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<MedicoesBean> listmb;

    MedicoesBean mb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listmb = new ArrayList<>();
    }

    public void create(String nome, String unidade) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO medicoes (nome, unidade) VALUES ('" + nome + "', '" + unidade + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar Medida.";
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

    public List<MedicoesBean> read() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM medicoes");

            rs = stmt.executeQuery();

            while (rs.next()) {
                mb = new MedicoesBean();

                mb.setId(rs.getInt("id"));
                mb.setNome(rs.getString("nome"));
                mb.setUnidade(rs.getString("unidade"));

                listmb.add(mb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Medições.";
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

        return listmb;
    }

    public List<MedicoesBean> readMedicao(String medicao) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM medicoes WHERE nome = '" + medicao + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                mb = new MedicoesBean();

                mb.setId(rs.getInt("id"));
                mb.setNome(rs.getString("nome"));
                mb.setUnidade(rs.getString("unidade"));

                listmb.add(mb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Medições.";
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

        return listmb;
    }

    public void update(int id, String nome, String unidade) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE medicoes SET nome = '" + nome + "', unidade = '" + unidade + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Medida.";
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
            stmt = con.prepareStatement("DELETE FROM medicoes WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir Medida.";
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
