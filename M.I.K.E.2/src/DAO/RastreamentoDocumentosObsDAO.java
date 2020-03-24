/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.RastreamentoDocumentosObsBean;
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
public class RastreamentoDocumentosObsDAO {

    public void create(RastreamentoDocumentosObsBean rdcb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO rastreamento_doc_comentario (idrastreamento, funcionario, data, comentario) VALUES (?,?,?,?)");

            stmt.setInt(1, rdcb.getIdrastreamento());
            stmt.setString(2, rdcb.getUsuario());
            stmt.setString(3, rdcb.getData());
            stmt.setString(4, rdcb.getObs());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<RastreamentoDocumentosObsBean> read(int idrastreamento) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<RastreamentoDocumentosObsBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM rastreamento_doc_comentario WHERE idrastreamento = ?");
            stmt.setInt(1, idrastreamento);
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                RastreamentoDocumentosObsBean rdcb = new RastreamentoDocumentosObsBean();

                rdcb.setId(rs.getInt("id"));
                rdcb.setUsuario(rs.getString("funcionario"));
                rdcb.setData(rs.getString("data"));
                rdcb.setObs(rs.getString("comentario"));

                listbb.add(rdcb);
            }
        } catch (SQLException e) {
            Logger.getLogger(RastreamentoDocumentosObsDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(RastreamentoDocumentosObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
}
