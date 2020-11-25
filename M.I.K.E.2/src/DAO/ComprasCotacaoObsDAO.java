/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ComprasCotacaoObsBean;
import Connection.ConnectionFactory;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Marcos Filho
 */
public class ComprasCotacaoObsDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ComprasCotacaoObsBean> listccob;

    ComprasCotacaoObsBean ccob;

    String tabledb = "compras_cotacao_obs";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listccob = new ArrayList<>();
    }

    /**
     *
     * @param cotacao
     * @param data
     * @param funcionario
     * @param obs
     * @throws SQLException
     */
    public void create(String cotacao, String data, String funcionario, String obs) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO " + tabledb + " (cotacao, data, funcionario, obs) VALUES ('" + cotacao + "', '" + data + "', '" + funcionario + "', '" + obs + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    /**
     *
     * @param cotacao
     * @return
     * @throws SQLException
     */
    public List<ComprasCotacaoObsBean> read(String cotacao) throws SQLException {
        rsList();

        stmt = con.prepareStatement("SELECT * FROM " + tabledb + " WHERE cotacao + '" + cotacao + "'");

        rs = stmt.executeQuery();

        while (rs.next()) {
            ccob = new ComprasCotacaoObsBean();

            ccob.setId(rs.getInt("id"));
            ccob.setData(rs.getString("data"));
            ccob.setFuncionario(rs.getString("funcionario"));
            ccob.setObs(rs.getString("obs"));

            listccob.add(ccob);
        }

        ConnectionFactory.closeConnection(con, stmt, rs);

        return listccob;
    }

    /**
     * 
     * @param id
     * @throws SQLException 
     */
    public void delete(int id) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("DELETE FROM " + tabledb + " WHERE id = " + id);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }
}
