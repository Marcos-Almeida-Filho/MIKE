/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasPedidoDocsBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.io.File;
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
public class VendasPedidoDocsDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<VendasPedidoDocsBean> listvpd;

    static VendasPedidoDocsBean vpdb;

    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listvpd = new ArrayList<>();
    }

    /**
     * Criar documentos pertencentes à um pedido de venda.
     *
     * @param pedido - Código do Pedido
     * @param descricao - Descrição do documento. Ex: Boleto, Desenho, etc.
     * @param local - Local onde o documento está salvo.
     * @throws java.sql.SQLException
     */
    public void create(String pedido, String descricao, String local) throws SQLException {
        conStmt();

        String local2 = local.replace("\\", "\\\\");

        stmt = con.prepareStatement("INSERT INTO vendas_pedido_docs (pedido, descricao, local) VALUES ('" + pedido + "', '" + descricao + "', '" + local2 + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    /**
     *
     * @param pedido
     * @return
     */
    public List<VendasPedidoDocsBean> readDocs(String pedido) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_docs WHERE pedido = '" + pedido + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpdb = new VendasPedidoDocsBean();

                vpdb.setId(rs.getInt("id"));
                vpdb.setDescricao(rs.getString("descricao"));
                vpdb.setLocal(rs.getString("local"));

                listvpd.add(vpdb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler documentos do Pedido de Venda.";
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

        return listvpd;
    }

    public void delete(int id, String location) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_pedido_docs WHERE id = " + id);
            stmt.executeUpdate();

            File file = new File(location);
            file.delete();

            JOptionPane.showMessageDialog(null, "Arquivo excluído com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao excluir documento do Pedido de Venda.";
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
