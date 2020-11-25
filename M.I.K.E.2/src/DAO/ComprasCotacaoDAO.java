/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.ConnectionFactory;
import Bean.ComprasCotacaoBean;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Marcos Filho
 */
public class ComprasCotacaoDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ComprasCotacaoBean> listccb;

    ComprasCotacaoBean ccb;

    String tabledb = "compras_cotacao";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listccb = new ArrayList<>();
    }

    /**
     * 
     * @param cotacao
     * @param dataCriacao
     * @throws SQLException 
     */
    public void create(String cotacao, String dataCriacao) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO " + tabledb + " (cotacao, dataCriacao, status) VALUES ('" + cotacao + "', '" + dataCriacao + "', 'Ativo')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }
    
    public List<ComprasCotacaoBean> readCotacoes() throws SQLException {
        rsList();
        
        stmt = con.prepareStatement("SELECT * FROM " + tabledb);
        
        rs = stmt.executeQuery();
        
        while(rs.next()) {
            ccb = new ComprasCotacaoBean();
            
            ccb.setCotacao(rs.getString("cotacao"));
            ccb.setDataCriacao(rs.getString("dataCriacao"));
            ccb.setStatus(rs.getString("status"));
            
            listccb.add(ccb);
        }
        
        ConnectionFactory.closeConnection(con, stmt, rs);
        
        return listccb;
    }
    
    public void update(String status) throws SQLException {
        conStmt();
        
        stmt = con.prepareStatement("UPDATE " + tabledb + " SET status = " + status);
        
        stmt.executeUpdate();
        
        ConnectionFactory.closeConnection(con, stmt);
    }
}
