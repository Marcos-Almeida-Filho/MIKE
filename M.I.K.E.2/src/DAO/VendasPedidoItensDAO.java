/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasPedidoItensBean;
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
public class VendasPedidoItensDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<VendasPedidoItensBean> listvpi;

    static VendasPedidoItensBean vpib;

    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listvpi = new ArrayList<>();
    }

    /**
     *
     * @param dav
     * @param idMaterial
     * @param pedido
     * @param codigo
     * @param descricao
     * @param qtd
     * @param valorunitario
     * @param valortotal
     * @param prazo
     * @param nf
     * @param op
     */
    public void create(String dav, int idMaterial, String codigo, String descricao, double qtd, double valorunitario, double valortotal, String prazo, String pedido, String nf, String op) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO vendas_pedido_itens (dav, idmaterial, codigo, descricao, qtd, valorunitario, valortotal, prazo, pedido, nf, op) VALUES ('" + dav + "', " + idMaterial + ", '" + codigo + "', '" + descricao + "', " + qtd + ", " + valorunitario + ", " + valortotal + ", '" + prazo + "', '" + pedido + "', '" + nf + "', '" + op + "')");

            stmt.executeUpdate();

            CotacaoVenda.itensCriados = true;
        } catch (SQLException e) {
            String msg = "Erro ao criar item do Pedido de Venda!";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<VendasPedidoItensBean> readItens(String dav) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_itens WHERE dav = '" + dav + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpib = new VendasPedidoItensBean();

                vpib.setId(rs.getInt("id"));
                vpib.setIdMaterial(rs.getInt("idmaterial"));
                vpib.setCodigo(rs.getString("codigo"));
                vpib.setDescricao(rs.getString("descricao"));
                vpib.setQtd(rs.getDouble("qtd"));
                vpib.setValorunitario(rs.getDouble("valorunitario"));
                vpib.setValortotal(rs.getDouble("valortotal"));
                vpib.setPrazo(rs.getString("prazo"));
                vpib.setPedido(rs.getString("pedido"));
                vpib.setNf(rs.getString("nf"));
                vpib.setOp(rs.getString("op"));

                listvpi.add(vpib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler os itens do Pedido de Venda.";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listvpi;
    }

    public List<VendasPedidoItensBean> readItensSemOp(String dav, String codigo) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_itens WHERE dav = '" + dav + "' AND op = '' AND codigo = '" + codigo + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpib = new VendasPedidoItensBean();

                vpib.setId(rs.getInt("id"));
                vpib.setIdMaterial(rs.getInt("idmaterial"));
                vpib.setCodigo(rs.getString("codigo"));
                vpib.setDescricao(rs.getString("descricao"));
                vpib.setQtd(rs.getDouble("qtd"));
                vpib.setValorunitario(rs.getDouble("valorunitario"));
                vpib.setValortotal(rs.getDouble("valortotal"));
                vpib.setPrazo(rs.getString("prazo"));
                vpib.setPedido(rs.getString("pedido"));
                vpib.setNf(rs.getString("nf"));
                vpib.setOp(rs.getString("op"));

                listvpi.add(vpib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler os itens do Pedido de Venda.";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listvpi;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<VendasPedidoItensBean> readItem(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_itens WHERE id = " + id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpib = new VendasPedidoItensBean();

                vpib.setIdMaterial(rs.getInt("idmaterial"));
                vpib.setCodigo(rs.getString("codigo"));
                vpib.setDescricao(rs.getString("descricao"));
                vpib.setQtd(rs.getDouble("qtd"));
                vpib.setValorunitario(rs.getDouble("valorunitario"));
                vpib.setValortotal(rs.getDouble("valortotal"));
                vpib.setPrazo(rs.getString("prazo"));
                vpib.setNf(rs.getString("nf"));
                vpib.setOp(rs.getString("op"));

                listvpi.add(vpib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler item do Pedido de Venda.";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listvpi;
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
     * @param pedido
     * @param id
     */
    public void update(int idMaterial, String codigo, String descricao, double qtd, double valorunitario, double valortotal, String prazo, String pedido, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_itens SET idmaterial = " + idMaterial + ", codigo = '" + codigo + "', descricao = '" + descricao + "', qtd = " + qtd + ", valorunitario = " + valorunitario + ", valortotal = " + valortotal + ", prazo = '" + prazo + "', pedido = '" + pedido + "' WHERE id = " + id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar item do Pedido de Venda.";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateNotaFiscal(String nf, int idItemPedido) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_itens SET nf = '" + nf + "' WHERE id = " + idItemPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar nota fiscal do item do Pedido de Venda.";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateOP(String op, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_itens SET op = '" + op + "' WHERE id = " + id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar OP do item do Pedido de Venda.";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_pedido_itens WHERE id = " + id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item excluído com sucesso!");
        } catch (HeadlessException | SQLException e) {
            String msg = "Erro ao excluir item do Pedido de Venda";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
