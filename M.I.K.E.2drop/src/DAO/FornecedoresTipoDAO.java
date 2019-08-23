/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.FornecedoresTipoBean;
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
public class FornecedoresTipoDAO {

    public void create(FornecedoresTipoBean ftb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO fornecedores_tipo (idfornecedor, mp, ferramentas, rebolo, oleo, gravacao, embalagem, calibracao, manutencao, escritorio, limpeza, revestimento, retifica) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setInt(1, ftb.getIdfornecedor());
            stmt.setBoolean(2, ftb.isMp());
            stmt.setBoolean(3, ftb.isFerramentas());
            stmt.setBoolean(4, ftb.isRebolo());
            stmt.setBoolean(5, ftb.isOleo());
            stmt.setBoolean(6, ftb.isGravacao());
            stmt.setBoolean(7, ftb.isEmbalagem());
            stmt.setBoolean(8, ftb.isCalibracao());
            stmt.setBoolean(9, ftb.isManutencao());
            stmt.setBoolean(10, ftb.isEscritorio());
            stmt.setBoolean(11, ftb.isLimpeza());
            stmt.setBoolean(12, ftb.isRevestimento());
            stmt.setBoolean(13, ftb.isRetifica());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar Tipo de Fornecedor!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresTipoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<FornecedoresTipoBean> read(int idfornecedor) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<FornecedoresTipoBean> listftb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedores_tipo WHERE idfornecedor = ?");
            stmt.setInt(1, idfornecedor);
            rs = stmt.executeQuery();

            while (rs.next()) {
                FornecedoresTipoBean ftb = new FornecedoresTipoBean();

                ftb.setIdfornecedor(rs.getInt("idfornecedor"));
                ftb.setMp(rs.getBoolean("mp"));
                ftb.setFerramentas(rs.getBoolean("ferramentas"));
                ftb.setRebolo(rs.getBoolean("rebolo"));
                ftb.setOleo(rs.getBoolean("oleo"));
                ftb.setGravacao(rs.getBoolean("gravacao"));
                ftb.setEmbalagem(rs.getBoolean("embalagem"));
                ftb.setCalibracao(rs.getBoolean("calibracao"));
                ftb.setManutencao(rs.getBoolean("manutencao"));
                ftb.setEscritorio(rs.getBoolean("escritorio"));
                ftb.setLimpeza(rs.getBoolean("limpeza"));
                ftb.setRevestimento(rs.getBoolean("revestimento"));
                ftb.setRetifica(rs.getBoolean("retifica"));

                listftb.add(ftb);
            }

        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listftb;
    }

    public void update(FornecedoresTipoBean ftb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE fornecedores_tipo SET mp = ?, ferramentas = ?, rebolo = ?, oleo = ?, gravacao = ?, embalagem = ?, calibracao = ?, manutencao = ?, escritorio = ?, limpeza = ?, revestimento = ?, retifica = ? WHERE idfornecedor = ?");
            
            stmt.setBoolean(1, ftb.isMp());
            stmt.setBoolean(2, ftb.isFerramentas());
            stmt.setBoolean(3, ftb.isRebolo());
            stmt.setBoolean(4, ftb.isOleo());
            stmt.setBoolean(5, ftb.isGravacao());
            stmt.setBoolean(6, ftb.isEmbalagem());
            stmt.setBoolean(7, ftb.isCalibracao());
            stmt.setBoolean(8, ftb.isManutencao());
            stmt.setBoolean(9, ftb.isEscritorio());
            stmt.setBoolean(10, ftb.isLimpeza());
            stmt.setBoolean(11, ftb.isRevestimento());
            stmt.setBoolean(12, ftb.isRetifica());
            stmt.setInt(13, ftb.getIdfornecedor());

        } catch (SQLException e) {
            Logger.getLogger(FornecedoresTipoDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao atualizar Tipo de Fornecedor!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresTipoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
