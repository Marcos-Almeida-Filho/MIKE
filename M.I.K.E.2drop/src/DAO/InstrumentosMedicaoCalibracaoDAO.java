/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.InstrumentosMedicaoCalibracaoBean;
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
public class InstrumentosMedicaoCalibracaoDAO {

    public void create(InstrumentosMedicaoCalibracaoBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO instrumento_medicao_calibracao (codigoinstrumento, certificado, datacalibracao, validade, local) VALUES (?,?,?,?,?)");

            stmt.setString(1, bb.getCodigoinstrumento());
            stmt.setString(2, bb.getCertificado());
            stmt.setString(3, bb.getDatacalibracao());
            stmt.setString(4, bb.getValidade());
            stmt.setString(5, bb.getLocal());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<InstrumentosMedicaoCalibracaoBean> read(String codigo) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<InstrumentosMedicaoCalibracaoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM instrumento_medicao_calibracao WHERE codigoinstrumento = ?");
            stmt.setString(1, codigo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                InstrumentosMedicaoCalibracaoBean cb = new InstrumentosMedicaoCalibracaoBean();

                cb.setId(rs.getInt("id"));
                cb.setCodigoinstrumento(rs.getString("codigoinstrumento"));
                cb.setCertificado(rs.getString("certificado"));
                cb.setDatacalibracao(rs.getString("datacalibracao"));
                cb.setValidade(rs.getString("validade"));
                cb.setLocal(rs.getString("local"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public void update(InstrumentosMedicaoCalibracaoBean bb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE instrumento_medicao_calibracao SET codigoinstrumento = ?, certificado = ?, datacalibracao = ?, validade = ?, local = ? WHERE id = ?");

            stmt.setString(1, bb.getCodigoinstrumento());
            stmt.setString(2, bb.getCertificado());
            stmt.setString(3, bb.getDatacalibracao());
            stmt.setString(4, bb.getValidade());
            stmt.setString(5, bb.getLocal());
            stmt.setInt(6, bb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
