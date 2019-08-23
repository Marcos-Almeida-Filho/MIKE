/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.FornecedoresContatosBean;
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
 * @author marco
 */
public class FornecedoresContatosDAO {
    
    public void create (FornecedoresContatosBean fcb) {
        
        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO fornecedores_contatos (idfornecedor, nome, cargo, telefone, email) VALUES (?,?,?,?,?)");
            stmt.setInt(1, fcb.getIdfornecedor());
            stmt.setString(2, fcb.getNome());
            stmt.setString(3, fcb.getCargo());
            stmt.setString(4, fcb.getTelefone());
            stmt.setString(5, fcb.getEmail());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar Contatos do Fornecedor!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresContatosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public List<FornecedoresContatosBean> read(int idfornecedor) {
        
        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        ResultSet rs = null;
        
        List<FornecedoresContatosBean> listfcb = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM fornecedores_contatos WHERE idfornecedor = ?");
            stmt.setInt(1, idfornecedor);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                FornecedoresContatosBean fcb = new FornecedoresContatosBean();
                
                fcb.setId(rs.getInt("idfornecedor"));
                fcb.setNome(rs.getString("nome"));
                fcb.setCargo(rs.getString("cargo"));
                fcb.setTelefone(rs.getString("telefone"));
                fcb.setEmail(rs.getString("email"));
                
                listfcb.add(fcb);
            }
        }catch (SQLException e) {
            Logger.getLogger(FornecedoresContatosDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresContatosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return listfcb;
    }
    
    public void update (FornecedoresContatosBean fcb) {
        
        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE fornecedores_contatos SET nome = ?, cargo = ?, telefone = ?, email = ? WHERE idfornecedor = ?");
            stmt.setString(1, fcb.getNome());
            stmt.setString(2, fcb.getCargo());
            stmt.setString(3, fcb.getTelefone());
            stmt.setString(4, fcb.getEmail());
            stmt.setInt(5, fcb.getIdfornecedor());
        } catch (SQLException e) {
            Logger.getLogger(FornecedoresContatosDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Erro ao atualizar Contatos do Fornecedor!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(FornecedoresContatosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
