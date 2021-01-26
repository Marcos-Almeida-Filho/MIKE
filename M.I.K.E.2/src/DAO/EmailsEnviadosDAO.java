/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.EmailsEnviadosBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class EmailsEnviadosDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<EmailsEnviadosBean> list;

    EmailsEnviadosBean eeb;

    String table = "emailsEnviados";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        list = new ArrayList<>();
    }

    public void create(String nome, String data) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (nome, data) VALUES ('" + nome + "', '" + data + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar data de envio de e-mail.";
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

    public boolean verRegistro(String nome) {
        rsList();

        boolean b = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            if (rs.next()) {
                b = true;
            }
        } catch (SQLException e) {
            String msg = "Erro ao verificar registro nos envios de e-mail.";
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

        return b;
    }

    public LocalDate getUltimoEnvio(String nome) {
        rsList();

        LocalDate d = LocalDate.now().minusDays(1);

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            if (rs.next()) {
                d = LocalDate.parse(rs.getString("data"));
            }
        } catch (SQLException e) {
            String msg = "Erro ao verificar último envio de e-mail.";
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

        return d;
    }
    
    public void updateUltimoEnvio(String data, String nome) {
        conStmt();
        
        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET data = '" + data + "' WHERE nome = '" + nome + "'");
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar último envio de e-mail.";
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
