/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoGrupoDeProcessosItensBean;
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
public class ServicoGrupoDeProcessosItensDAO {

    public void create(ServicoGrupoDeProcessosItensBean sgdpib) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_grupo_processos_itens (id_grupo, processo) VALUES (?,?)");

            stmt.setInt(1, sgdpib.getId_grupo());
            stmt.setString(2, sgdpib.getProcesso());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoGrupoDeProcessosItensBean> read(int id_grupo) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoGrupoDeProcessosItensBean> listpsb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_grupo_processos_itens WHERE id_grupo = ?");
            stmt.setInt(1, id_grupo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoGrupoDeProcessosItensBean psb = new ServicoGrupoDeProcessosItensBean();

                psb.setId(rs.getInt("id"));
                psb.setProcesso(rs.getString("processo"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }

    public List<ServicoGrupoDeProcessosItensBean> reados(String nome) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoGrupoDeProcessosItensBean> listpsb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM servicos_grupo_processos_itens WHERE id_grupo = ?");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoGrupoDeProcessosItensBean psb = new ServicoGrupoDeProcessosItensBean();

                psb.setId(rs.getInt("id"));
                psb.setProcesso(rs.getString("processo"));

                listpsb.add(psb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listpsb;
    }

    public void update(ServicoGrupoDeProcessosItensBean psb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_grupo_processos_itens SET id_grupo = ?, processo = ? WHERE id = ?");

            stmt.setInt(1, psb.getId_grupo());
            stmt.setString(2, psb.getProcesso());
            stmt.setInt(3, psb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(ServicoGrupoDeProcessosItensBean sgdpib) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM servicos_grupo_processos_itens WHERE id = ?");
            stmt.setInt(1, sgdpib.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoGrupoDeProcessosItensDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
