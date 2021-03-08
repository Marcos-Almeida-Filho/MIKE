/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoPedidoItensBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class ServicoPedidoItensDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ServicoPedidoItensBean> listspib;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listspib = new ArrayList<>();
    }

    public void create(ServicoPedidoItensBean spib) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_pedido_itens_orcamento (idpedido, codigo, descricao, qtde, valor, total, prazo, os, nf, prazoDate) VALUES (?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, spib.getIdpedido());
            stmt.setString(2, spib.getCodigo());
            stmt.setString(3, spib.getDescricao());
            stmt.setString(4, spib.getQtde());
            stmt.setString(5, spib.getValor());
            stmt.setString(6, spib.getTotal());
            stmt.setString(7, spib.getPrazo());
            stmt.setString(8, spib.getOs());
            stmt.setString(9, spib.getNf());
            stmt.setString(10, spib.getPrazoDate());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar os itens do pedido!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoPedidoItensBean> readitens(String idpedido) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_pedido_itens_orcamento WHERE idpedido = ?");
            stmt.setString(1, idpedido);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoPedidoItensBean spib = new ServicoPedidoItensBean();

                spib.setId(rs.getInt("id"));
                spib.setIdpedido(rs.getString("idpedido"));
                spib.setCodigo(rs.getString("codigo"));
                spib.setDescricao(rs.getString("descricao"));
                spib.setQtde(rs.getString("qtde"));
                spib.setValor(rs.getString("valor"));
                spib.setTotal(rs.getString("total"));
                spib.setPrazo(rs.getString("prazo"));
                spib.setPrazoDate(rs.getString("prazoDate"));
                spib.setPedidocliente(rs.getString("pedidocliente"));
                spib.setOs(rs.getString("os"));
                spib.setNf(rs.getString("nf"));

                listspib.add(spib);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listspib;
    }
    
    public List<ServicoPedidoItensBean> readitensSemNota(String dataInicio, String dataFim) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_pedido_itens_orcamento, servicos_pedido WHERE servicos_pedido_itens_orcamento.nf = '' AND servicos_pedido.status_cobranca <> 'Cancelado' AND servicos_pedido_itens_orcamento.idpedido = servicos_pedido.idtela AND servicos_pedido_itens_orcamento.prazoDate BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ORDER BY prazoDate");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoPedidoItensBean spib = new ServicoPedidoItensBean();

                spib.setId(rs.getInt("id"));
                spib.setIdpedido(rs.getString("idpedido"));
                spib.setCodigo(rs.getString("codigo"));
                spib.setDescricao(rs.getString("descricao"));
                spib.setQtde(rs.getString("qtde"));
                spib.setValor(rs.getString("valor"));
                spib.setTotal(rs.getString("total"));
                spib.setPrazo(rs.getString("prazo"));
                spib.setPrazoDate(rs.getString("prazoDate"));
                spib.setPedidocliente(rs.getString("pedidocliente"));
                spib.setOs(rs.getString("os"));
                spib.setNf(rs.getString("nf"));

                listspib.add(spib);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listspib;
    }

    public void update(ServicoPedidoItensBean spib) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE servicos_pedido_itens_orcamento SET codigo = ?, descricao = ?, qtde = ?, valor = ?, total = ?, prazo = ? WHERE id = ?");
            stmt.setString(1, spib.getCodigo());
            stmt.setString(2, spib.getDescricao());
            stmt.setString(3, spib.getQtde());
            stmt.setString(4, spib.getValor());
            stmt.setString(5, spib.getTotal());
            stmt.setString(6, spib.getPrazo());
            stmt.setInt(7, spib.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updateOS(ServicoPedidoItensBean spib) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE servicos_pedido_itens_orcamento SET os = ? WHERE id = ?");
            stmt.setString(1, spib.getOs());
            stmt.setInt(2, spib.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updatenotacobranca(ServicoPedidoItensBean spib) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE servicos_pedido_itens_orcamento SET nf = ? WHERE id = ?");
            stmt.setString(1, spib.getNf());
            stmt.setInt(2, spib.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM servicos_pedido_itens_orcamento WHERE id = " + id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Item excluído com sucesso.");
        } catch (SQLException e) {
            String msg = "Erro ao excluir item do Pedido de Compra.";
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
