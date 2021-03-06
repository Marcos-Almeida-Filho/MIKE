/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoOrcamentoItensBean;
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
public class ServicoOrcamentoItensDAO {

    public void create(ServicoOrcamentoItensBean iosb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_orcamento_itens (idorcamento, codigo, descricao, qtd, valor, total, prazo, das) VALUES (?,?,?,?,?,?,?,?)");
            stmt.setString(1, iosb.getIdorcamento());
            stmt.setString(2, iosb.getCodigo());
            stmt.setString(3, iosb.getDesc());
            stmt.setString(4, iosb.getQtd());
            stmt.setString(5, iosb.getValor());
            stmt.setString(6, iosb.getTotal());
            stmt.setString(7, iosb.getPrazo());
            stmt.setString(8, iosb.getDas());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoOrcamentoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoOrcamentoItensBean> readitens(String idorcamento) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoOrcamentoItensBean> listios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_orcamento_itens WHERE idorcamento = '" + idorcamento + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoOrcamentoItensBean iosb = new ServicoOrcamentoItensBean();

                iosb.setId(rs.getInt("id"));
                iosb.setCodigo(rs.getString("codigo"));
                iosb.setDesc(rs.getString("descricao"));
                iosb.setQtd(rs.getString("qtd"));
                iosb.setValor(rs.getString("valor"));
                iosb.setTotal(rs.getString("total"));
                iosb.setPrazo(rs.getString("prazo"));
                iosb.setDas(rs.getString("das"));

                listios.add(iosb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoOrcamentoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listios;
    }

    public void update(ServicoOrcamentoItensBean iosb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_orcamento_itens SET codigo = ?, descricao = ?, qtd = ?, valor = ?, total = ?, prazo = ?, das = ? WHERE id = ?");
            stmt.setString(1, iosb.getCodigo());
            stmt.setString(2, iosb.getDesc());
            stmt.setString(3, iosb.getQtd());
            stmt.setString(4, iosb.getValor());
            stmt.setString(5, iosb.getTotal());
            stmt.setString(6, iosb.getPrazo());
            stmt.setString(7, iosb.getDas());
            stmt.setInt(8, iosb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoOrcamentoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void delete(ServicoOrcamentoItensBean soib) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM servicos_orcamento_itens WHERE id = ?");
            stmt.setInt(1, soib.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoOrcamentoItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
