/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ComprasSolicitacaoItensBean;
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
public class ComprasSolicitacaoItensDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ComprasSolicitacaoItensBean> listcsi;

    ComprasSolicitacaoItensBean csib;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listcsi = new ArrayList<>();
    }

    /**
     *
     * @param idSolicitacao
     * @param item
     * @param unidade
     * @param qtd
     * @param obs
     * @throws java.sql.SQLException
     */
    public void create(int idSolicitacao, String item, String unidade, double qtd, String obs) throws SQLException {

        conStmt();

        stmt = con.prepareStatement("INSERT INTO compras_solicitacao_itens (idSolicitacao, item, unidade, qtd, obs) VALUES (" + idSolicitacao + ", '" + item + "', '" + unidade + "', " + qtd + ", '" + obs + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<ComprasSolicitacaoItensBean> read(int idSolicitacao) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compras_solicitacao_itens WHERE idSolicitacao = " + idSolicitacao);
            rs = stmt.executeQuery();

            while (rs.next()) {
                csib = new ComprasSolicitacaoItensBean();

                csib.setIdSolicitacao(rs.getInt("idSolicitacao"));
                csib.setItem(rs.getString("item"));
                csib.setUnidade(rs.getString("unidade"));
                csib.setQtd(rs.getInt("qtd"));
                csib.setObs(rs.getString("obs"));
                csib.setPedido(rs.getString("pedido"));

                listcsi.add(csib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler itens da Solicitação de Compras.";
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
        return listcsi;
    }

    public void update(String item, String unidade, double qtd, String obs, int id) throws SQLException {

        conStmt();

        stmt = con.prepareStatement("UPDATE compras_solicitacao_itens SET item = '" + item + "', unidade = '" + unidade + "', qtd = " + qtd + ", obs = '" + obs + "' WHERE id = " + id);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public void updatepedido(String pedido, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE compras_solicitacao_itens SET pedido = '" + pedido + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Pedido da Solicitação de Compras.";
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
