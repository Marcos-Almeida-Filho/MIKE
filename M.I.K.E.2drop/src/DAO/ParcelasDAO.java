/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ParcelasBean;
import Connection.ConnectionFactory;
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
public class ParcelasDAO {

    public void create(ParcelasBean pb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO parcelas (idcondicao, parcela, diaparcela) VALUES (?,?,?)");
            stmt.setInt(1, pb.getIdcondicao());
            stmt.setInt(2, pb.getParcela());
            stmt.setInt(3, pb.getDiaparcela());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ParcelasBean> read(int idcondicao) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ParcelasBean> listpb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM parcelas WHERE idcondicao = ?");
            stmt.setInt(1, idcondicao);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ParcelasBean pb = new ParcelasBean();

                pb.setParcela(rs.getInt("parcela"));
                pb.setDiaparcela(rs.getInt("diaparcela"));

                listpb.add(pb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpb;
    }

    public void update(ParcelasBean pb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE parcelas parcela = ?, diaparcela = ? WHERE id = ?");
            stmt.setInt(1, pb.getParcela());
            stmt.setInt(2, pb.getDiaparcela());
            stmt.setInt(3, pb.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
