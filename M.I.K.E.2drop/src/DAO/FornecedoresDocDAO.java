/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.FornecedoresDocBean;
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
public class FornecedoresDocDAO {

    public void create(FornecedoresDocBean fdb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO fornecedores_doc (idfornecedor, descricao, local) VALUES (?,?,?)");
            stmt.setInt(1, fdb.getIdfornecedor());
            stmt.setString(2, fdb.getDescricao());
            stmt.setString(3, fdb.getLocal());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar os documentos do Fornecedor!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<FornecedoresDocBean> read(int idfornecedor) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<FornecedoresDocBean> listfdb = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedores_doc WHERE idfornecedor = ?");
            stmt.setInt(1, idfornecedor);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                FornecedoresDocBean fdb = new FornecedoresDocBean();
                
                fdb.getId();
                fdb.getDescricao();
                fdb.getLocal();
                
                listfdb.add(fdb);
            }
        } catch (SQLException e) {
            Logger.getLogger(FornecedoresDocDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresDocDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return listfdb;
    }
}
