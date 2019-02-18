/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoOrcamentoDocumentosBean;
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
public class ServicoOrcamentoDocumentosDAO {

    public void create(ServicoOrcamentoDocumentosBean sodb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_orcamento_documentos (idorcamento, descricao, local) VALUES (?,?,?)");
            stmt.setInt(1, sodb.getIdorcamento());
            stmt.setString(2, sodb.getDescricao());
            stmt.setString(3, sodb.getLocal());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoOrcamentoDocumentosBean> readitens(String idorcamento) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoOrcamentoDocumentosBean> listios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_orcamento_documentos WHERE idorcamento = ?");
            stmt.setString(1, idorcamento);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoOrcamentoDocumentosBean iosb = new ServicoOrcamentoDocumentosBean();

                iosb.setId(rs.getInt("id"));
                iosb.setIdorcamento(rs.getInt("idorcamento"));
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

    public void update(ServicoOrcamentoDocumentosBean sodb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_orcamento_documentos SET idorcamento = ?, descricao = ?, local = ? WHERE id = ?");
            stmt.setInt(1, sodb.getIdorcamento());
            stmt.setString(2, sodb.getDescricao());
            stmt.setString(3, sodb.getLocal());
            stmt.setInt(4, sodb.getIdorcamento());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(ServicoOrcamentoDocumentosBean sodb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM servicos_orcamento_documentos WHERE id = ?");
            stmt.setInt(1, sodb.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
