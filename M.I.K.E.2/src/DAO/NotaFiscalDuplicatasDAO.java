/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.NotaFiscalDuplicatasBean;
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
public class NotaFiscalDuplicatasDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<NotaFiscalDuplicatasBean> listnfd;

    NotaFiscalDuplicatasBean nfdb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listnfd = new ArrayList<>();
    }

    public void create(int numeroNF, String duplicata, String data, double valor) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO nf_duplicatas (numeroNf, duplicatas, data, valor) VALUES (" + numeroNF + ", '" + duplicata + "', '" + data + "', " + valor + ")");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar duplicata.";
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

    public List<NotaFiscalDuplicatasBean> readDiplicatas(int numeroNF) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM nf_duplicatas");

            rs = stmt.executeQuery();

            while (rs.next()) {
                nfdb = new NotaFiscalDuplicatasBean();

                nfdb.setDuplicata(rs.getString("duplicata"));
                nfdb.setData(rs.getString("data"));
                nfdb.setValor(rs.getDouble("valor"));
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler duplicatas da Nota Fiscal " + numeroNF;
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

        return listnfd;
    }
}
