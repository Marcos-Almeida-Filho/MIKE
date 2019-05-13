/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.MenusBean;
import Connection.ConnectionFactory;
import View.configuracoes.Menus;
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
public class MenusDAO {

    public void create(MenusBean mb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO menus (nome) VALUES (?)");
            stmt.setString(1, mb.getNome());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!/n" + 3);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<MenusBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<MenusBean> listmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM menus ORDER BY ordem");
            rs = stmt.executeQuery();

            while (rs.next()) {
                MenusBean mb = new MenusBean();

                mb.setId(rs.getInt("id"));
                mb.setNome(rs.getString("nome"));

                listmb.add(mb);
            }
        } catch (SQLException e) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listmb;
    }

    public List<MenusBean> pesquisa() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<MenusBean> listmb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM menus WHERE id LIKE '%" + Menus.txtpesquisa.getText() + "%' OR nome LIKE '%" + Menus.txtpesquisa.getText() + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                MenusBean mb = new MenusBean();

                mb.setId(rs.getInt("id"));
                mb.setNome(rs.getString("nome"));

                listmb.add(mb);
            }
        } catch (SQLException e) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listmb;
    }

    public void update(MenusBean mb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE menus SET nome = ? WHERE id = ?");
            stmt.setString(1, mb.getNome());
            stmt.setInt(2, mb.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(MenusBean mb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM menus WHERE id = ?");
            stmt.setInt(1, mb.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
