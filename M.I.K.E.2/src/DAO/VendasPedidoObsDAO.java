/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasPedidoObsBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import View.vendas.CotacaoVenda;
import java.awt.HeadlessException;
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
public class VendasPedidoObsDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<VendasPedidoObsBean> listvpo;

    static VendasPedidoObsBean vpob;

    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listvpo = new ArrayList<>();
    }

    /**
     * Método para criação de observações de Pedido de Venda.
     *
     * @param pedido - Código do pedido de venda.
     * @param data - Data que a observação foi gerada.
     * @param user - Usuário que fez a observação.
     * @param obs - Observação feita.
     */
    public void create(String pedido, String data, String user, String obs) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO vendas_pedido_obs (pedido, data, usuario, obs) VALUES ('" + pedido + "','" + data + "','" + user + "','" + obs + "')");

            stmt.executeUpdate();

            CotacaoVenda.obsCriadas = true;
        } catch (SQLException e) {
            String msg = "Erro ao criar observação do Pedido de Venda!";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + "\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Método para leitura de observações de um pedido de venda.
     *
     * @param pedido - Código do pedido. Ex: PV20-0001
     * @return
     */
    public List<VendasPedidoObsBean> readObs(String pedido) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_obs WHERE pedido = '" + pedido + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpob = new VendasPedidoObsBean();

                vpob.setId(rs.getInt("id"));
                vpob.setData(rs.getString("data"));
                vpob.setUsuario(rs.getString("usuario"));
                vpob.setObs(rs.getString("obs"));

                listvpo.add(vpob);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler observações do pedido de venda!";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + "\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listvpo;
    }

    /**
     * Método para atualizar uma observação de pedido de venda.
     *
     * @param data - Data que a observação está sendo atualizada.
     * @param obs - Nova observação.
     * @param id - ID da observação para atualizar.
     */
    public void update(String data, String obs, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_obs SET data = '" + data + "', obs = '" + obs + "' WHERE id = " + id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar observação do Pedido de Venda!";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + "\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Método para excluir uma observação de um pedido de venda.
     *
     * @param id - ID da observação.
     */
    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_pedido_obs WHERE id = " + id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Observação excluída com sucesso!");
        } catch (HeadlessException | SQLException e) {
            String msg = "Erro ao excluir observação do Pedido de Venda!";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + "\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
