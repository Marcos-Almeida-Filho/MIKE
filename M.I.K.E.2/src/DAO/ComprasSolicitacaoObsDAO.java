/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ComprasSolicitacaoObsBean;
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
public class ComprasSolicitacaoObsDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ComprasSolicitacaoObsBean> listcsob;

    ComprasSolicitacaoObsBean csob;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listcsob = new ArrayList<>();
    }

    public void create(int idSolicitacao, String data, String funcionario, String obs) throws SQLException {

        conStmt();

        stmt = con.prepareStatement("INSERT INTO compras_solicitacao_obs (idSolicitacao, data, funcionario, obs) VALUES (" + idSolicitacao + ", '" + data + "', '" + funcionario + "', '" + obs + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<ComprasSolicitacaoObsBean> read(int idSolicitacao) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compras_solicitacao_obs WHERE idSolicitacao = " + idSolicitacao);

            rs = stmt.executeQuery();

            while (rs.next()) {
                csob = new ComprasSolicitacaoObsBean();

                csob.setId(rs.getInt("id"));
                csob.setIdSolicitacao(rs.getInt("idSolicitacao"));
                csob.setData(rs.getString("data"));
                csob.setFuncionario(rs.getString("funcionario"));
                csob.setObs(rs.getString("obs"));

                listcsob.add(csob);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler observações da Solicitação de Compra.";
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
        return listcsob;
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM compras_solicitacao_obs WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir observação da Solicitação de Compras.";
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
