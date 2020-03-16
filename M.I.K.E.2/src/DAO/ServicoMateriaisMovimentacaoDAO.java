/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoMateriaisMovimentacaoBean;
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
public class ServicoMateriaisMovimentacaoDAO {

    public void create(ServicoMateriaisMovimentacaoBean smmb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_materiais_movimentacao (idmaterial, inicial, movimentada, tipo, saldo, data, usuario) VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, smmb.getIdmaterial());
            stmt.setInt(2, smmb.getInicial());
            stmt.setInt(3, smmb.getMovimentada());
            stmt.setString(4, smmb.getTipo());
            stmt.setInt(5, smmb.getSaldo());
            stmt.setString(6, smmb.getData());
            stmt.setString(7, smmb.getUsuario());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoMateriaisMovimentacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ServicoMateriaisMovimentacaoBean> read(int idmaterial) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ServicoMateriaisMovimentacaoBean> listso = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * from servicos_materiais_movimentacao WHERE idmaterial = ?");
            stmt.setInt(1, idmaterial);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ServicoMateriaisMovimentacaoBean sob = new ServicoMateriaisMovimentacaoBean();

                sob.setId(rs.getInt("id"));
                sob.setInicial(rs.getInt("inicial"));
                sob.setMovimentada(rs.getInt("movimentada"));
                sob.setTipo(rs.getString("tipo"));
                sob.setSaldo(rs.getInt("saldo"));
                sob.setData(rs.getString("data"));
                sob.setUsuario(rs.getString("usuario"));

                listso.add(sob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoMateriaisMovimentacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listso;

    }

    public void update(ServicoMateriaisMovimentacaoBean sob) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE servicos_materiais_movimentacao SET inicial = ?, movimentada = ?, tipo = ?, saldo = ?, data = ?, usuario = ? WHERE id = ?");
            stmt.setInt(1, sob.getInicial());
            stmt.setInt(2, sob.getMovimentada());
            stmt.setString(3, sob.getTipo());
            stmt.setInt(4, sob.getSaldo());
            stmt.setString(5, sob.getData());
            stmt.setString(6, sob.getUsuario());
            stmt.setInt(7, sob.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoMateriaisMovimentacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
