/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.RegioesBean;
import Connection.ConnectionFactory;
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
public class RegioesDAO {

    public void create(RegioesBean rb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO regioes (nome) VALUES (?)");
            stmt.setString(1, rb.getNome());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<RegioesBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<RegioesBean> listrb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM regioes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                RegioesBean rb = new RegioesBean();

                rb.setId(rs.getInt("id"));
                rb.setNome(rs.getString("nome"));

                listrb.add(rb);
            }
        } catch (SQLException e) {

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listrb;
    }

    public void update(RegioesBean rb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE regioes SET nome = ? WHERE id = ?");
            stmt.setString(1, rb.getNome());
            stmt.setInt(2, rb.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
