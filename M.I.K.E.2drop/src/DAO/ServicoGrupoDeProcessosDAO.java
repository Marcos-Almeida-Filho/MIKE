/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoGrupoDeProcessosBean;
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
public class ServicoGrupoDeProcessosDAO {

    public void create(ServicoGrupoDeProcessosBean psb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_grupo_processos (nome) VALUES (?)");

            stmt.setString(1, psb.getNome());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoGrupoDeProcessosBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoGrupoDeProcessosBean> listpsb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_grupo_processos");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoGrupoDeProcessosBean psb = new ServicoGrupoDeProcessosBean();

                psb.setId(rs.getInt("id"));
                psb.setNome(rs.getString("nome"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }
    
    public List<ServicoGrupoDeProcessosBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoGrupoDeProcessosBean> listpsb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_grupo_processos WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoGrupoDeProcessosBean psb = new ServicoGrupoDeProcessosBean();

                psb.setId(rs.getInt("id"));
                psb.setNome(rs.getString("nome"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }
    
    public List<ServicoGrupoDeProcessosBean> readcreated(String nome) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoGrupoDeProcessosBean> listpsb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_grupo_processos WHERE nome = ?");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoGrupoDeProcessosBean psb = new ServicoGrupoDeProcessosBean();

                psb.setId(rs.getInt("id"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }

    public List<ServicoGrupoDeProcessosBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoGrupoDeProcessosBean> listub = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE id LIKE '%" + pesquisa + "%' OR nome LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoGrupoDeProcessosBean ub = new ServicoGrupoDeProcessosBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));

                listub.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listub;
    }
    
    public void update(ServicoGrupoDeProcessosBean psb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_grupo_processos SET nome = ? WHERE id = ?");

            stmt.setString(1, psb.getNome());
            stmt.setInt(2, psb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "");
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
