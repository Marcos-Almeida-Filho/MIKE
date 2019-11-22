/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.UsuariosSalarioBean;
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
public class UsuariosSalarioDAO {
    
    UsuariosSalarioBean usb = new UsuariosSalarioBean();
    
    public void create(UsuariosSalarioBean ub) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO usuarios_salario (idusuario, data, valor, motivo) VALUES (?,?,?,?)");
            stmt.setInt(1, ub.getIdusuario());
            stmt.setString(2, ub.getData());
            stmt.setDouble(3, ub.getValor());
            stmt.setString(4, ub.getMotivo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Erro ao Salvar!\nLogin já existente!\nCódigo do erro: " + e.getErrorCode());
                try {
                    SendEmail.EnviarErro(e.toString());
                } catch (AWTException | IOException ex) {
                    Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao Salvar!\nCódigo do erro: " + e.getErrorCode());
                try {
                    SendEmail.EnviarErro(e.toString());
                } catch (AWTException | IOException ex) {
                    Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public List<UsuariosSalarioBean> reada(int idusuario) {

        Connection con = ConnectionFactory.getConnectionlogin();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<UsuariosSalarioBean> listu = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios_salario WHERE idusuario = ?");
            stmt.setInt(1, idusuario);
            rs = stmt.executeQuery();

            while (rs.next()) {

                usb.setId(rs.getInt("id"));
                usb.setData(rs.getString("data"));
                usb.setValor(rs.getDouble("valor"));
                usb.setMotivo(rs.getString("motivo"));

                listu.add(usb);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listu;
    }
}
