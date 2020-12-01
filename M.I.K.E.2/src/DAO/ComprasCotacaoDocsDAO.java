/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.ConnectionFactory;
import Bean.ComprasCotacaoDocsBean;
import Methods.SendEmail;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class ComprasCotacaoDocsDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ComprasCotacaoDocsBean> listccdb;

    ComprasCotacaoDocsBean ccdb;

    String tabledb = "compras_cotacao_docs";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listccdb = new ArrayList<>();
    }

    /**
     *
     * @param cotacao
     * @param descricao
     * @param local
     * @throws SQLException
     */
    public void create(String cotacao, String descricao, String local) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO " + tabledb + " (cotacao, descricao, local) VALUES ('" + cotacao + "', '" + descricao + "', '" + local + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<ComprasCotacaoDocsBean> read(String cotacao) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + tabledb);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ccdb = new ComprasCotacaoDocsBean();

                ccdb.setId(rs.getInt("id"));
                ccdb.setDescricao(rs.getString("descricao"));
                ccdb.setLocal(rs.getString("local"));

                listccdb.add(ccdb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Documentos da Cotação de Compras.";
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

        return listccdb;
    }

    public void delete(int id) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("DELETE FROM " + tabledb + " WHERE id = " + id);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }
}
