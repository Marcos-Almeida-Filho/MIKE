/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ProgramacaoBean;
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
public class ProgramacaoDAO {

    public void create(ProgramacaoBean pb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO programacao (nome, dataprogramada, datainicio, datatermino, status) VALUES (?,?,?,?,?)");

            stmt.setString(1, pb.getNome());
            stmt.setString(2, pb.getDataProgramada());
            stmt.setString(3, pb.getDataInicio());
            stmt.setString(4, pb.getDataTermino());
            stmt.setString(5, pb.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar programação!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProgramacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ProgramacaoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ProgramacaoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM programacao WHERE status <> 'Finalizado' ORDER BY dataprogramada, id");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProgramacaoBean pb = new ProgramacaoBean();

                pb.setId(rs.getInt("id"));
                pb.setNome(rs.getString("nome"));
                pb.setDataProgramada(rs.getString("dataprogramada"));
                pb.setDataInicio(rs.getString("datainicio"));
                pb.setDataTermino(rs.getString("datatermino"));
                pb.setStatus(rs.getString("status"));

                listbb.add(pb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ProgramacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProgramacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public List<ProgramacaoBean> readStatus(String status) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ProgramacaoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM programacao WHERE status = '" + status + "' ORDER BY dataprogramada, id");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ProgramacaoBean pb = new ProgramacaoBean();

                pb.setId(rs.getInt("id"));
                pb.setNome(rs.getString("nome"));
                pb.setDataProgramada(rs.getString("dataprogramada"));
                pb.setDataInicio(rs.getString("datainicio"));
                pb.setDataTermino(rs.getString("datatermino"));
                pb.setStatus(rs.getString("status"));

                listbb.add(pb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ProgramacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProgramacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public void updateStatus(ProgramacaoBean pb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE programacao SET status = ? WHERE id = ?");

            stmt.setString(1, pb.getStatus());
            stmt.setInt(2, pb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProgramacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateInicio(ProgramacaoBean pb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE programacao SET datainicio = ?, status = 'Fazendo' WHERE id = ?");

            stmt.setString(1, pb.getDataInicio());
            stmt.setInt(2, pb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProgramacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateFinal(ProgramacaoBean pb) {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE programacao SET datatermino = ?, status = 'Finalizado' WHERE id = ?");

            stmt.setString(1, pb.getDataTermino());
            stmt.setInt(2, pb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar programação!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ProgramacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
