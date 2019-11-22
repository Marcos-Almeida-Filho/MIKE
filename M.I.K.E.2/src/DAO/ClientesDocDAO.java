/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ClientesDocBean;
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
public class ClientesDocDAO {
    
    public void create(ClientesDocBean cdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO clientes_documentos (idcliente, descricao, local) VALUES (?,?,?)");
            stmt.setInt(1, cdb.getIdcliente());
            stmt.setString(2, cdb.getDescricao());
            stmt.setString(3, cdb.getLocal());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar arquivos do cliente!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<ClientesDocBean> readitens(String idcliente) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<ClientesDocBean> listios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM clientes_documentos WHERE idcliente = ?");
            stmt.setString(1, idcliente);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientesDocBean cdb = new ClientesDocBean();

                cdb.setId(rs.getInt("id"));
                cdb.setIdcliente(rs.getInt("idcliente"));
                cdb.setDescricao(rs.getString("descricao"));
                cdb.setLocal(rs.getString("local"));

                listios.add(cdb);
            }
        } catch (SQLException e) {
            Logger.getLogger(InsumosDocDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listios;
    }
    
    public void delete(ClientesDocBean cdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM clientes_documentos WHERE id = ?");
            stmt.setInt(1, cdb.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir documento do cliente!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(InsumosDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
