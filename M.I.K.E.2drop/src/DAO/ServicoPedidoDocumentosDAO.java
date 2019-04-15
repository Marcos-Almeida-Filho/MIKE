/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoPedidoDocumentosBean;
import Connection.ConnectionFactory;
import java.awt.HeadlessException;
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
public class ServicoPedidoDocumentosDAO {

    public void create(ServicoPedidoDocumentosBean spdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_pedido_documentos (idpedido, descricao, local) VALUES (?,?,?)");
            stmt.setString(1, spdb.getIdpedido());
            stmt.setString(2, spdb.getDescricao());
            stmt.setString(3, spdb.getLocal());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar os documentos do pedido!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoPedidoDocumentosBean> readitens(String idorcamento) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoPedidoDocumentosBean> listios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_pedido_documentos WHERE idpedido = ?");
            stmt.setString(1, idorcamento);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoPedidoDocumentosBean iosb = new ServicoPedidoDocumentosBean();

                iosb.setIdtela(rs.getString("idpedido"));
                iosb.setIdpedido(rs.getString("idpedido"));
                iosb.setDescricao(rs.getString("descricao"));
                iosb.setLocal(rs.getString("local"));

                listios.add(iosb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listios;
    }

    public void update(ServicoPedidoDocumentosBean spdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_pedido_documentos SET idorcamento = ?, descricao = ?, local = ? WHERE idtela = ?");
            stmt.setString(1, spdb.getIdpedido());
            stmt.setString(2, spdb.getDescricao());
            stmt.setString(3, spdb.getLocal());
            stmt.setString(4, spdb.getIdtela());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(ServicoPedidoDocumentosBean spdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM servicos_pedido_documentos WHERE id = ?");
            stmt.setInt(1, spdb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
