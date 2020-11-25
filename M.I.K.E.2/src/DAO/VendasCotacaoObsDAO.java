/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasCotacaoObsBean;
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
public class VendasCotacaoObsDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<VendasCotacaoObsBean> listvco;

    static VendasCotacaoObsBean vcob;

    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listvco = new ArrayList<>();
    }

    /**
     * Método para criação de observações de Cotação de Venda.
     *
     * @param cotacao - Código da cotação de venda.
     * @param data - Data que a observação foi gerada.
     * @param user - Usuário que fez a observação.
     * @param obs - Observação feita.
     * @throws java.sql.SQLException
     */
    public void create(String cotacao, String data, String user, String obs) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_cotacao_obs (cotacao, data, usuario, obs) VALUES ('" + cotacao + "','" + data + "','" + user + "','" + obs + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    /**
     * Método para leitura de observações de uma cotação de venda.
     *
     * @param cotacao - Código da cotação. Ex: CV20-0001
     * @return
     */
    public List<VendasCotacaoObsBean> readObs(String cotacao) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao_obs WHERE cotacao = '" + cotacao + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vcob = new VendasCotacaoObsBean();

                vcob.setId(rs.getInt("id"));
                vcob.setData(rs.getString("data"));
                vcob.setUsuario(rs.getString("usuario"));
                vcob.setObs(rs.getString("obs"));

                listvco.add(vcob);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler observações da cotação de venda!";
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

        return listvco;
    }

    /**
     * Método para atualizar uma observação de cotação de venda.
     *
     * @param data - Data que a observação está sendo atualizada.
     * @param obs - Nova observação.
     * @param id - ID da observação para atualizar.
     */
    public void update(String data, String obs, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_cotacao_obs SET data = '" + data + "', obs = '" + obs + "' WHERE id = " + id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar observação da Cotação de Venda!";
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
     * Método para excluir uma observação de uma cotação de venda.
     *
     * @param id - ID da observação.
     */
    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_cotacao_obs WHERE id = " + id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Observação excluída com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao excluir observação da Cotação de Venda!";
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
