/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.GrupoDeUsuariosPermBean;
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
public class GrupoDeUsuariosPermDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<GrupoDeUsuariosPermBean> listg;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listg = new ArrayList<>();
    }

    public void create(int idgrupo, String nome, boolean permissao) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO grupo_usuarios_perm (idgrupo, nome, permissao) VALUES (" + idgrupo + ",'" + nome + "'," + permissao + ")");

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar permissão de grupo.\n" + e);
            SendEmail.EnviarErro2(e.toString());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<GrupoDeUsuariosPermBean> read(String nome, int idgrupo) {
        rsList();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM grupo_usuarios_perm WHERE idgrupo = " + idgrupo + " AND nome = '" + nome + "'");
            
            rs = stmt.executeQuery();
            
            while(rs.next()) {
                GrupoDeUsuariosPermBean gupb = new GrupoDeUsuariosPermBean();
                
                gupb.setId(rs.getInt("id"));
                gupb.setPerm(rs.getBoolean("permissao"));
                
                listg.add(gupb);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao ler permissões do grupo de usuários.\n" + e);
            SendEmail.EnviarErro2("Erro ao ler permissões do grupo de usuários.\n" + e.toString());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return listg;
    }
    
    public boolean readPerm(int idgrupo, String nome) {
        rsList();

        boolean perm = false;

        try {
            stmt = con.prepareStatement("SELECT permissao FROM grupo_usuarios_perm WHERE nome = '" + nome + "' AND idgrupo = " + idgrupo);

            rs = stmt.executeQuery();

            while (rs.next()) {
                perm = rs.getBoolean("permissao");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler permissão do grupo.\n" + e);
            SendEmail.EnviarErro2(e.toString());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return perm;
    }

    public int readId(int idgrupo, String nome) {
        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT id FROM grupo_usuarios_perm WHERE nome = '" + nome + "' AND idgrupo = " + idgrupo);

            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler permissão do grupo.\n" + e);
            SendEmail.EnviarErro2(e.toString());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return id;
    }

    public void update(int id, boolean perm) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE grupo_usuarios_perm SET permissao = " + perm + " WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar permissão.\n" + e);
            SendEmail.EnviarErro2(e.toString());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
