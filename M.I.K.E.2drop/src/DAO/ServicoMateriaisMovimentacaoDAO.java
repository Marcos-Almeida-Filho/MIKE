/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoMateriaisMovimentacaoBean;
import Connection.ConnectionFactory;
import View.servicos.ServicoMateriais;
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
public class ServicoMateriaisMovimentacaoDAO {

    public void create(ServicoMateriaisMovimentacaoBean smb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO servicos_materiais_movimentacao (idmaterial, inicial, movimentada, tipo, saldo, data, usuario) VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, smb.getIdmaterial());
            stmt.setInt(2, smb.getInicial());
            stmt.setInt(3, smb.getMovimentada());
            stmt.setString(4, smb.getTipo());
            stmt.setInt(5, smb.getSaldo());
            stmt.setString(6, smb.getData());
            stmt.setString(7, smb.getUsuario());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!/n" + e);
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
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!/n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
