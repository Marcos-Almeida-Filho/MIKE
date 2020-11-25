/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.InsumosDocBean;
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
public class InsumosDocDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<InsumosDocBean> listidb;

    InsumosDocBean idb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listidb = new ArrayList<>();
    }

    public void create(int idInsumo, String descricao, String local) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO insumos_docs (idinsumo, descricao, local) VALUES (" + idInsumo + ", '" + descricao + "', '" + local + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao salvar arquivos do insumo!";
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

    public List<InsumosDocBean> readDocs(int idInsumo) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos_docs WHERE idinsumo = " + idInsumo);

            rs = stmt.executeQuery();

            while (rs.next()) {
                InsumosDocBean iosb = new InsumosDocBean();

                iosb.setId(rs.getInt("id"));
                iosb.setIdinsumo(rs.getInt("idinsumo"));
                iosb.setDescricao(rs.getString("descricao"));
                iosb.setLocal(rs.getString("local"));

                listidb.add(iosb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler documentos do Insumo.";
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

        return listidb;
    }

    public void update(int idInsumo, String descricao, String local, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE insumos_docs SET idinsumo = " + idInsumo + ", descricao = '" + descricao + "', local = '" + local + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar documento do Insumo.";
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

    public void delete(int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM insumos_docs WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir documento do Insumo.";
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
}
