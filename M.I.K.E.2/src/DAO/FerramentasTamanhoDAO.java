/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.FerramentasTamanhoBean;
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
public class FerramentasTamanhoDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<FerramentasTamanhoBean> listft;

    FerramentasTamanhoBean ftb;

    private final String table = "ferramentas_tamanho";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listft = new ArrayList<>();
    }

    public void create(String nomeFerramenta, String codigo) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO " + table + " (nomeFerramenta, codigo) VALUES ('" + nomeFerramenta + "', '" + codigo + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<FerramentasTamanhoBean> read(String nomeFerramenta) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nomeFerramenta = '" + nomeFerramenta + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ftb = new FerramentasTamanhoBean();

                ftb.setId(rs.getInt("id"));
                ftb.setCodigo(rs.getString("codigo"));

                listft.add(ftb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Tamanhos da Ferramenta.";
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

        return listft;
    }
}
