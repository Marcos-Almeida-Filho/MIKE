/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.FerramentasFamiliaBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class FerramentasFamiliaDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<FerramentasFamiliaBean> listff;

    FerramentasFamiliaBean ffb;

    String table = "ferramentas_familia";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listff = new ArrayList<>();
    }

    public void create(String nomeFerramenta, String nome, String codigo, String descricao) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO " + table + " (nomeFerramenta, nome, codigo, descricao) VALUES ('" + nomeFerramenta + "', '" + nome + "', '"+ codigo + "', '" + descricao + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<FerramentasFamiliaBean> read(String nomeFerramenta) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nomeFerramenta = '" + nomeFerramenta + "' ORDER BY codigo");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ffb = new FerramentasFamiliaBean();

                ffb.setId(rs.getInt("id"));
                ffb.setNome(rs.getString("nome"));
                ffb.setCodigo(rs.getString("codigo"));
                ffb.setDescricao(rs.getString("descricao"));

                listff.add(ffb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Famílias da Ferramenta.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listff;
    }
    
    public List<FerramentasFamiliaBean> readCodigoEDescricao(String nomeFerramenta, String nome) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nomeFerramenta = '" + nomeFerramenta + "' AND nome = '" + nome + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ffb = new FerramentasFamiliaBean();

                ffb.setId(rs.getInt("id"));
                ffb.setNome(rs.getString("nome"));
                ffb.setCodigo(rs.getString("codigo"));
                ffb.setDescricao(rs.getString("descricao"));

                listff.add(ffb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Famílias da Ferramenta.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listff;
    }
    
    public void update(String nome, String codigo, String descricao, int idFamilia) {
        conStmt();
        
        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET nome = '" + nome + "', codigo = '" + codigo + "', descricao = '" + descricao + "' WHERE id = " + idFamilia);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar família de ferramenta.";
            JOptionPane.showMessageDialog(null, msg);
            
            new Thread() {
                
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(int id) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("DELETE FROM " + table + " WHERE id = " + id);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }
}
