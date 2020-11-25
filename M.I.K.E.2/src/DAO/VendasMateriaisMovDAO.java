/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasMateriaisMovBean;
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
public class VendasMateriaisMovDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<VendasMateriaisMovBean> listvmmb;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listvmmb = new ArrayList<>();
    }

    /**
     * Método para criar Movimentação de estoque de Materiais de Venda.
     * <p>
     * Stmt = INSERT INTO vendas_materiais_mov (idmaterial, qtdinicial,
     * qtdmovimentada, saldo, tipo, data, usuario)
     *
     * @param idmaterial Inteiro do ID do material no Banco de Dados
     * @param qtdinicial Double do estoque antes da movimentação
     * @param qtdmovimentada Double da quantidade da movimentação
     * @param saldo Double do resultado de qtdinicial + qtdmovimentada
     * @param tipo String contendo o tipo de movimentação. (Ex: "Encerramento de
     * OS", "Emissão de Nota Fiscal", etc.)
     * @param data Date com a data da movimentação
     * @param user String com o nome do usuário que fez a movimentação
     * @throws java.sql.SQLException
     *
     * @since 2.0.0
     */
    public void create(int idmaterial, double qtdinicial, double qtdmovimentada, double saldo, String tipo, String data, String user) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_materiais_mov (idmaterial, qtdinicial, qtdmov, saldo, tipo, data, usuario) VALUES (" + idmaterial + "," + qtdinicial + "," + qtdmovimentada + "," + saldo + ",'" + tipo + "','" + data + "','" + user + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    /**
     * Método para retornar todas as movimentações de devido material.
     *
     * @param id Int do ID do material no Banco de Dados.
     * @return List com as movimentações do material.
     *
     * @since 2.0.0
     */
    public List<VendasMateriaisMovBean> read(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais_mov WHERE idmaterial = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisMovBean vmmb = new VendasMateriaisMovBean();

                vmmb.setId(rs.getInt("id"));
                vmmb.setQtdInicial(rs.getDouble("qtdinicial"));
                vmmb.setQtdMovimentada(rs.getDouble("qtdmov"));
                vmmb.setSaldo(rs.getDouble("saldo"));
                vmmb.setTipo(rs.getString("tipo"));
                vmmb.setData(rs.getString("data"));
                vmmb.setUsuario(rs.getString("usuario"));

                listvmmb.add(vmmb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Movimentação do Material.";
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

        return listvmmb;
    }
}
