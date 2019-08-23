/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.FornecedoresAltBean;
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
public class FornecedoresAltDAO {

    public void create(FornecedoresAltBean fab) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO fornecedores_alt (idfornecedor, data, user, valor, valoranterior, valornovo) VALUES (?,?,?,?,?,?)");
            stmt.setInt(1, fab.getIdfornecedor());
            stmt.setString(2, fab.getData());
            stmt.setString(3, fab.getUser());
            stmt.setString(4, fab.getValor());
            stmt.setString(5, fab.getValoranterior());
            stmt.setString(6, fab.getValornovo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar as alterações do Fornecedor!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresAltDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<FornecedoresAltBean> read(int idfornecedor) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<FornecedoresAltBean> listfab = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedores_alt WHERE idfornecedor = ?");
            stmt.setInt(1, idfornecedor);
            rs = stmt.executeQuery();

            while (rs.next()) {
                FornecedoresAltBean fab = new FornecedoresAltBean();

                fab.setData(rs.getString("data"));
                fab.setUser(rs.getString("user"));
                fab.setValor(rs.getString("valor"));
                fab.setValoranterior(rs.getString("valoranterior"));
                fab.setValornovo(rs.getString("valornovo"));

                listfab.add(fab);
            }
        } catch (SQLException e) {
            Logger.getLogger(FornecedoresAltDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresAltDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listfab;
    }
}
