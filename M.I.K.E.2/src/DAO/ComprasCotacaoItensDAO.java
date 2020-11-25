/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Connection.ConnectionFactory;
import Bean.ComprasCotacaoItensBean;
import Methods.SendEmail;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class ComprasCotacaoItensDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ComprasCotacaoItensBean> listccib;

    ComprasCotacaoItensBean ccib;

    String tabledb = "compras_cotacao_itens";
    String msg;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listccib = new ArrayList<>();
    }

    public void create(String cotacao, String codigo, String descricao, double qtd) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO " + tabledb + " (cotacao, codigo, descricao, qtd) VALUES ('" + cotacao + "', '" + codigo + "', '" + descricao + "', " + qtd + ")");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<ComprasCotacaoItensBean> readItensCotacao(String cotacao) throws SQLException {
        rsList();

        stmt = con.prepareStatement("SELECT * FROM " + tabledb + " WHERE cotacao = '" + cotacao + "'");

        rs = stmt.executeQuery();

        while (rs.next()) {
            ccib = new ComprasCotacaoItensBean();

            ccib.setId(rs.getInt("id"));
            ccib.setCodigo(rs.getString("codigo"));
            ccib.setDescricao(rs.getString("descricao"));
            ccib.setQtd(rs.getDouble("qtd"));
            ccib.setPedido(rs.getString("pedido"));

            listccib.add(ccib);
        }

        ConnectionFactory.closeConnection(con, stmt, rs);

        return listccib;
    }

    /**
     * 
     * @param id
     * @return 
     */
    public List<ComprasCotacaoItensBean> readItem(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + tabledb + " WHERE id = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ccib = new ComprasCotacaoItensBean();

                ccib.setId(rs.getInt("id"));
                ccib.setCodigo(rs.getString("codigo"));
                ccib.setDescricao(rs.getString("descricao"));
                ccib.setQtd(rs.getDouble("qtd"));
                ccib.setFor1(rs.getString("for1"));
                ccib.setValor1(rs.getDouble("valor1"));
                ccib.setPrazo1(rs.getString("prazo1"));
                ccib.setFor2(rs.getString("for2"));
                ccib.setValor2(rs.getDouble("valor2"));
                ccib.setPrazo2(rs.getString("prazo2"));
                ccib.setFor3(rs.getString("for3"));
                ccib.setValor3(rs.getDouble("valor3"));
                ccib.setPrazo3(rs.getString("prazo3"));
                ccib.setPedido(rs.getString("pedido"));

                listccib.add(ccib);
            }
        } catch (SQLException e) {
            msg = "Erro ao ler item da Cotação de Compras.";
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

        return listccib;
    }

    /**
     * 
     * @param codigo
     * @param descricao
     * @param qtd
     * @param for1
     * @param valor1
     * @param prazo1
     * @param for2
     * @param valor2
     * @param prazo2
     * @param for3
     * @param valor3
     * @param prazo3
     * @param id 
     */
    public void updateItem(String codigo, String descricao, double qtd, String for1, double valor1, String prazo1, String for2, double valor2, String prazo2, String for3, double valor3, String prazo3, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + tabledb + " SET codigo = '" + codigo + ", descricao = '" + descricao + "', qtd = " + qtd + ", for1 = '" + for1 + "', valor1 = " + valor1 + ", prazo1 = '" + prazo1 + "', for2 = '" + for2 + "', valor2 = " + valor2 + ", prazo2 = '" + prazo2 + "', for3 = '" + for3 + "', valor3 = " + valor3 + ", prazo3 = '" + prazo3 + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            msg = "Erro ao atualizar item da Cotação de Compras.";
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
