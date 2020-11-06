/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasCotacaoDocsBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import View.vendas.CotacaoVenda;
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
public class VendasCotacaoDocsDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<VendasCotacaoDocsBean> listvcd;

    static VendasCotacaoDocsBean vcdb;

    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listvcd = new ArrayList<>();
    }

    /**
     * Criar documentos pertencentes à uma cotação de venda.
     *
     * @param cotacao - Código da cotação
     * @param descricao - Descrição do documento. Ex: Boleto, Desenho, etc.
     * @param local - Local onde o documento está salvo.
     */
    public void create(String cotacao, String descricao, String local) {
        conStmt();

        String local2 = local.replace("\\", "\\\\");

        try {
            stmt = con.prepareStatement("INSERT INTO vendas_cotacao_docs (cotacao, descricao, local) VALUES ('" + cotacao + "','" + descricao + "','" + local2 + "')");

            stmt.executeUpdate();

            CotacaoVenda.docsCriados = true;
        } catch (SQLException e) {
            String msg = "Erro ao criar documento da Cotação de Venda!";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + "\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     *
     * @param cotacao
     * @return
     */
    public List<VendasCotacaoDocsBean> readDocs(String cotacao) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao_docs WHERE cotacao = '" + cotacao + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vcdb = new VendasCotacaoDocsBean();

                vcdb.setId(rs.getInt("id"));
                vcdb.setDescricao(rs.getString("descricao"));
                vcdb.setLocal(rs.getString("local"));

                listvcd.add(vcdb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler documentos da cotação.";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + "\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listvcd;
    }

    public void delete(int id, String location) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_cotacao_docs WHERE id = " + id);
            stmt.executeUpdate();

            File file = new File(location);
            file.delete();

            JOptionPane.showMessageDialog(null, "Arquivo excluído com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao excluir documento da cotação de venda.";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + "\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
