/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ComprasSolicitacaoItensBean;
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
public class ComprasSolicitacaoItensDAO {

    public void create(ComprasSolicitacaoItensBean csb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO solicitacao_compras_itens (idtela, item, qtd, obs) VALUES (?,?,?,?)");
            stmt.setString(1, csb.getIdtela());
            stmt.setString(2, csb.getItem());
            stmt.setInt(3, csb.getQtd());
            stmt.setString(4, csb.getObs());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar a solicitação de compra!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ComprasSolicitacaoItensBean> read(String idtela) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ComprasSolicitacaoItensBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from solicitacao_compras_itens WHERE idtela = " + idtela);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ComprasSolicitacaoItensBean csb = new ComprasSolicitacaoItensBean();

                csb.setIdtela(rs.getString("idtela"));
                csb.setItem(rs.getString("item"));
                csb.setQtd(rs.getInt("qtd"));
                csb.setObs(rs.getString("obs"));
                csb.setPedido(rs.getString("pedido"));

                listso.add(csb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listso;
    }

    public void update(ComprasSolicitacaoItensBean csb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE solicitacao_compras SET item = ?, qtd = ?, obs = ? WHERE id = ?");
            stmt.setString(1, csb.getItem());
            stmt.setInt(2, csb.getQtd());
            stmt.setString(3, csb.getObs());
            stmt.setInt(4, csb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void updatepedido(ComprasSolicitacaoItensBean csb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE solicitacao_compras SET pedido = ? WHERE id = ?");
            stmt.setString(1, csb.getPedido());
            stmt.setInt(2, csb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ComprasSolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
