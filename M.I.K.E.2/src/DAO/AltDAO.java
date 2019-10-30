/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.AltBean;
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
public class AltDAO {

    public void create(AltBean ab, String tabela, String idlocal) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO " + tabela + " (" + idlocal + " , data, user, valor, valoranterior, valornovo) VALUES (?,?,?,?,?,?)");
            stmt.setInt(1, ab.getIdlocal());
            stmt.setString(2, ab.getData());
            stmt.setString(3, ab.getUser());
            stmt.setString(4, ab.getValor());
            stmt.setString(5, ab.getValoranterior());
            stmt.setString(6, ab.getValornovo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar as alterações do Fornecedor!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(AltDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<AltBean> read(int idfornecedor, String tabela, String idlocal) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt;

        ResultSet rs;

        List<AltBean> listab = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + tabela + " WHERE " + idlocal + " = ?");
            stmt.setInt(1, idfornecedor);
            rs = stmt.executeQuery();

            while (rs.next()) {
                AltBean ab = new AltBean();

                ab.setData(rs.getString("data"));
                ab.setUser(rs.getString("user"));
                ab.setValor(rs.getString("valor"));
                ab.setValoranterior(rs.getString("valoranterior"));
                ab.setValornovo(rs.getString("valornovo"));

                listab.add(ab);
            }
        } catch (SQLException e) {
            Logger.getLogger(AltDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(AltDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listab;
    }
}
