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
    
    Connection con;
    
    PreparedStatement stmt;
    
    ResultSet rs;
    
    List<AltBean> listab;

    private void conStmt() {
        con = ConnectionFactory.getConnection();
        
        stmt = null;
    }
    
    private void rsList() {
        conStmt();
        
        rs = null;
        
        listab = new ArrayList<>();
    }
    
    /**
     * Método para criar alterações em qualquer item.
     * 
     * @param id Int contendo o ID do item
     * @param tipo String do tipo do item
     * @param data String com data completa invertida para o Banco de Dados
     * @param user String com o nome do usuário fazendo a alteração
     * @param valor String com o valor que foi alterado. (Ex: Nome, Código, Descrição, etc.)
     * @param valoranterior String com o valor original
     * @param valornovo String com o novo valor que foi inserido
     */
    public void create(int id, String tipo, String data, String user, String valor, String valoranterior, String valornovo) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO alteracoes (iditem, tipo, data, user, valor, valoranterior, valornovo) VALUES (" + id + ",'" + tipo + "','" + data + "','" + user + "','" + valor + "','" + valoranterior + "','" + valornovo + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar as alterações!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(AltDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    /**
     * Método para leitura de todas as modificações de qualquer item.
     * 
     * @param id Int contendo o id do item a ser procuradas as alterações
     * @param tipo String com o tipo do item para achar as alterações
     * @return List<> com todas as alterações do item.
     */
    public List<AltBean> read(int id, String tipo) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM alteracoes WHERE iditem = " + id + " AND tipo = '" + tipo + "'");

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
