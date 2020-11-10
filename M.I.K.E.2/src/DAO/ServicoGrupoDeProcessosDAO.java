/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ProcessosServicoBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.awt.AWTException;
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
public class ServicoGrupoDeProcessosDAO {

    public void create(ProcessosServicoBean psb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_grupo_processos (nome) VALUES (?)");

            stmt.setString(1, psb.getNome());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ProcessosServicoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ProcessosServicoBean> listpsb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_grupo_processos");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProcessosServicoBean psb = new ProcessosServicoBean();

                psb.setId(rs.getInt("id"));
                psb.setNome(rs.getString("nome"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }

    public List<ProcessosServicoBean> readidgrupo(String nome) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ProcessosServicoBean> listpsb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT id FROM servicos_grupo_processos WHERE nome = ?");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProcessosServicoBean psb = new ProcessosServicoBean();

                psb.setId(rs.getInt("id"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }

    public List<ProcessosServicoBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ProcessosServicoBean> listpsb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_grupo_processos WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProcessosServicoBean psb = new ProcessosServicoBean();

                psb.setId(rs.getInt("id"));
                psb.setNome(rs.getString("nome"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }

    public List<ProcessosServicoBean> readcreated(String nome) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ProcessosServicoBean> listpsb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_grupo_processos WHERE nome = ?");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProcessosServicoBean psb = new ProcessosServicoBean();

                psb.setId(rs.getInt("id"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }

    public List<ProcessosServicoBean> pesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ProcessosServicoBean> listub = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE id LIKE '%" + pesquisa + "%' OR nome LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProcessosServicoBean ub = new ProcessosServicoBean();

                ub.setId(rs.getInt("id"));
                ub.setNome(rs.getString("nome"));

                listub.add(ub);
            }
        } catch (SQLException e) {
            Logger.getLogger(MenusDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listub;
    }

    public void update(ProcessosServicoBean psb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_grupo_processos SET nome = ? WHERE id = ?");

            stmt.setString(1, psb.getNome());
            stmt.setInt(2, psb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
