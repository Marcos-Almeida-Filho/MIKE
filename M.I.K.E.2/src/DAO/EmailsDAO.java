/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.EmailsBean;
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
public class EmailsDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<EmailsBean> list;

    EmailsBean eb;

    String table = "emails";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        list = new ArrayList<>();
    }

    public void create(String nome, String mensagem) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (nome, mensagem) VALUES ('" + nome + "', '" + mensagem + "')");

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Mensagem criada com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao criar mensagem de e-mail.";
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

    public List<EmailsBean> readTodos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table);

            rs = stmt.executeQuery();

            while (rs.next()) {
                eb = new EmailsBean();

                eb.setId(rs.getInt("id"));
                eb.setNome(rs.getString("nome"));
                eb.setMensagem(rs.getString("mensagem"));

                list.add(eb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Emails cadastrados.";
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

        return list;
    }

    public List<EmailsBean> getMensagem(String nome) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            if (rs.next()) {
                eb = new EmailsBean();

                eb.setId(rs.getInt("id"));
                eb.setMensagem(rs.getString("mensagem"));

                list.add(eb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Mensagem para enviar e-mail.";
            JOptionPane.showMessageDialog(null, msg + "\n" + e);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return list;
    }
    
    public String getTextoMensagem(String nome) {
        rsList();
        
        String mensagem = "";

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            if (rs.next()) {
                mensagem = rs.getString("mensagem");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Mensagem para enviar e-mail.";
            JOptionPane.showMessageDialog(null, msg + "\n" + e);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return mensagem;
    }

    public void update(String nome, String mensagem) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET mensagem = '" + mensagem + "' WHERE nome = '" + nome + "'");

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao atualizar mensagem de e-mail.";
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
