/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasCotacaoItensBean;
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
public class VendasCotacaoItensDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<VendasCotacaoItensBean> listvci;

    static VendasCotacaoItensBean vcib;

    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listvci = new ArrayList<>();
    }

    /**
     *
     * @param cotacao
     * @param idMaterial
     * @param codigo
     * @param descricao
     * @param qtd
     * @param valorunitario
     * @param valortotal
     * @param prazo
     * @param cadastrado
     * @throws java.sql.SQLException
     */
    public void create(String cotacao, int idMaterial, String codigo, String descricao, double qtd, double valorunitario, double valortotal, String prazo, boolean cadastrado) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_cotacao_itens (cotacao, idmaterial, codigo, descricao, qtd, valorunitario, valortotal, prazo, cadastrado) VALUES ('" + cotacao + "', " + idMaterial + ", '" + codigo + "', '" + descricao + "', " + qtd + ", " + valorunitario + ", " + valortotal + ", '" + prazo + "', " + cadastrado + ")");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<VendasCotacaoItensBean> readItens(String cotacao) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao_itens WHERE cotacao = '" + cotacao + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vcib = new VendasCotacaoItensBean();

                vcib.setId(rs.getInt("id"));
                vcib.setIdmaterial(rs.getInt("idmaterial"));
                vcib.setCodigo(rs.getString("codigo"));
                vcib.setDescricao(rs.getString("descricao"));
                vcib.setQtd(rs.getDouble("qtd"));
                vcib.setValorunitario(rs.getDouble("valorunitario"));
                vcib.setValortotal(rs.getDouble("valortotal"));
                vcib.setPrazo(rs.getString("prazo"));
                vcib.setDav(rs.getString("dav"));
                vcib.setCadastrado(rs.getBoolean("cadastrado"));

                listvci.add(vcib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler os itens da cotação de Venda.";
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

        return listvci;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<VendasCotacaoItensBean> readItem(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao_itens WHERE id = " + id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                vcib = new VendasCotacaoItensBean();

                vcib.setIdmaterial(rs.getInt("idmaterial"));
                vcib.setCodigo(rs.getString("codigo"));
                vcib.setDescricao(rs.getString("descricao"));
                vcib.setQtd(rs.getDouble("qtd"));
                vcib.setValorunitario(rs.getDouble("valorunitario"));
                vcib.setValortotal(rs.getDouble("valortotal"));
                vcib.setPrazo(rs.getString("prazo"));
                vcib.setDav(rs.getString("dav"));
                vcib.setCadastrado(rs.getBoolean("cadastrado"));

                listvci.add(vcib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler item da cotação de venda.";
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

        return listvci;
    }

    /**
     *
     * @param idMaterial
     * @param codigo
     * @param descricao
     * @param qtd
     * @param valorunitario
     * @param valortotal
     * @param prazo
     * @param cadastrado
     * @param id
     * @throws java.sql.SQLException
     */
    public void update(int idMaterial, String codigo, String descricao, double qtd, double valorunitario, double valortotal, String prazo, boolean cadastrado, int id) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("UPDATE vendas_cotacao_itens SET idmaterial = " + idMaterial + ", codigo = '" + codigo + "', descricao = '" + descricao + "', qtd = " + qtd + ", valorunitario = " + valorunitario + ", valortotal = " + valortotal + ", prazo = '" + prazo + "', cadastrado = " + cadastrado + " WHERE id = " + id);
        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public void updateDAV(String pedido, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_cotacao_itens SET dav = '" + pedido + "' WHERE id = " + id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao lançar Pedido do item da cotação de venda";
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
    
    public void updateMaterial(String codigo, String descricao, int idmaterial) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_cotacao_itens SET codigo = '" + codigo + "', descricao = '" + descricao + "' WHERE idmaterial = " + idmaterial);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao lançar Pedido do item da cotação de venda";
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

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_cotacao_itens WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir item da Cotação de Venda";
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
