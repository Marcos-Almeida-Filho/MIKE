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

    public void create(ServicoPedidoItensBean spib) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_pedido_itens_orcamento (idpedido, codigo, descricao, qtde, valor, total, prazo, pedidocliente, os, nf) VALUES (?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, spib.getIdpedido());
            stmt.setString(2, spib.getCodigo());
            stmt.setString(3, spib.getDescricao());
            stmt.setString(4, spib.getQtde());
            stmt.setString(5, spib.getValor());
            stmt.setString(6, spib.getTotal());
            stmt.setString(7, spib.getPrazo());
            stmt.setString(8, spib.getPedidocliente());
            stmt.setString(9, spib.getOs());
            stmt.setString(10, spib.getNf());

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

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoPedidoItensBean> listspib = new ArrayList<>();

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

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_pedido_itens_orcamento SET idpedido = ?, codigo = ?, descricao = ?, qtde = ?, valor = ?, total = ?, prazo = ?, pedidocliente = ?, os = ?, nf = ? WHERE id = ?");
            stmt.setString(1, spib.getIdpedido());
            stmt.setString(2, spib.getCodigo());
            stmt.setString(3, spib.getDescricao());
            stmt.setString(4, spib.getQtde());
            stmt.setString(5, spib.getValor());
            stmt.setString(6, spib.getTotal());
            stmt.setString(7, spib.getPrazo());
            stmt.setString(8, spib.getPedidocliente());
            stmt.setString(9, spib.getOs());
            stmt.setString(10, spib.getNf());
            stmt.setInt(11, spib.getId());

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

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

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

}
