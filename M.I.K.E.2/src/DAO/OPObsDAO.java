/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OPObsBean;
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
public class OPObsDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<OPObsBean> listob;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listob = new ArrayList<>();
    }

    /**
     * Método para criação de observações de OP.
     *
     * @param op - Código da OP.
     * @param data - Data que a observação foi gerada.
     * @param user - Usuário que fez a observação.
     * @param obs - Observação feita.
     * @throws java.sql.SQLException
     */
    public void create(String op, String data, String user, String obs) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO op_obs (op, data, usuario, obs) VALUES ('" + op + "','" + data + "','" + user + "','" + obs + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    /**
     * Método para leitura de observações de uma cotação de venda.
     *
     * @param op - Código da OP. Ex: OP20-0001
     * @return
     */
    public List<OPObsBean> readObs(String op) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM op_obs WHERE op = '" + op + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OPObsBean oob = new OPObsBean();

                oob.setId(rs.getInt("id"));
                oob.setData(rs.getString("data"));
                oob.setUsuario(rs.getString("usuario"));
                oob.setObs(rs.getString("obs"));

                listob.add(oob);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler observações da OP!";
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

        return listob;
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
            stmt = con.prepareStatement("UPDATE op_obs SET data = '" + data + "', obs = '" + obs + "' WHERE id = " + id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar observação da OP!";
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
            stmt = con.prepareStatement("DELETE FROM op_obs WHERE id = " + id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Observação excluída com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao excluir observação da OP!";
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
