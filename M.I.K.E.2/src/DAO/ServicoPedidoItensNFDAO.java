/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoPedidoItensNFBean;
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
public class ServicoPedidoItensNFDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ServicoPedidoItensNFBean> list;

    String table = "servicos_pedido_itens_nota";

    ServicoPedidoItensNFBean spib;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        list = new ArrayList<>();
    }

    public void create(ServicoPedidoItensNFBean spib) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (idpedido, codigo, descricao, qtde, valor, total, nfretorno) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, spib.getIdpedido());
            stmt.setString(2, spib.getCodigo());
            stmt.setString(3, spib.getDescricao());
            stmt.setString(4, spib.getQtde());
            stmt.setString(5, spib.getValor());
            stmt.setString(6, spib.getTotal());
            stmt.setString(7, spib.getNfretorno());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensNFDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoPedidoItensNFBean> readitens(String idpedido) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE idpedido = ?");
            stmt.setString(1, idpedido);

            rs = stmt.executeQuery();

            while (rs.next()) {
                spib = new ServicoPedidoItensNFBean();

                spib.setId(rs.getInt("id"));
                spib.setIdpedido(rs.getString("idpedido"));
                spib.setCodigo(rs.getString("codigo"));
                spib.setDescricao(rs.getString("descricao"));
                spib.setQtde(rs.getString("qtde"));
                spib.setValor(rs.getString("valor"));
                spib.setTotal(rs.getString("total"));
                spib.setNfretorno(rs.getString("nfretorno"));

                list.add(spib);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensNFDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return list;
    }

    public void update(ServicoPedidoItensNFBean spib) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET idpedido = ?, codigo = ?, descricao = ?, qtde = ?, valor = ?, total = ?,  nfretorno = ? WHERE id = ?");
            stmt.setString(1, spib.getIdpedido());
            stmt.setString(2, spib.getCodigo());
            stmt.setString(3, spib.getDescricao());
            stmt.setString(4, spib.getQtde());
            stmt.setString(5, spib.getValor());
            stmt.setString(6, spib.getTotal());
            stmt.setString(7, spib.getNfretorno());
            stmt.setInt(8, spib.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensNFDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updatenotaretorno(ServicoPedidoItensNFBean spib) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET nfretorno = ? WHERE id = ?");
            stmt.setString(1, spib.getNfretorno());
            stmt.setInt(2, spib.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoPedidoItensNFDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM " + table + " WHERE id = " + id);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir Item de Retorno do Pedido de Serviço.";
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
