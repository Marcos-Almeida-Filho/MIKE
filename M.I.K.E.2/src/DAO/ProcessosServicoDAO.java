/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ProcessosServicoBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class ProcessosServicoDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ProcessosServicoBean> listpsb;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listpsb = new ArrayList<>();
    }

    public void create(ProcessosServicoBean psb) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO processosservico (nome) VALUES (?)");

            stmt.setString(1, psb.getNome());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProcessosServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ProcessosServicoBean> read() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM processosservico");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProcessosServicoBean psb = new ProcessosServicoBean();

                psb.setId(rs.getInt("id"));
                psb.setNome(rs.getString("nome"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProcessosServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }

    public int qtdProcessos() {

        rsList();

        int qtd = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM processosservico");
            rs = stmt.executeQuery();

            while (rs.next()) {
                qtd++;
            }
        } catch (SQLException e) {
            Logger.getLogger(ProcessosServicoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProcessosServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return qtd;
    }

    public void update(ProcessosServicoBean psb) {

        conStmt();

        try {

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "");
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProcessosServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(String nome) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM processosservico WHERE nome = '" + nome + "'");
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir Processo de Serviço.";
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
