/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasMateriaisCodigoClienteBean;
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
public class VendasMateriaisCodigoClienteDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<VendasMateriaisCodigoClienteBean> listvmccb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listvmccb = new ArrayList<>();
    }

    public void create(int idmaterial, String cliente, String codigo, String descricao) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_materiais_cod_cli (idmaterial, cliente, codigo, descricao) VALUES (" + idmaterial + ",'" + cliente + "','" + codigo + "','" + descricao + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<VendasMateriaisCodigoClienteBean> read(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais_cod_cli WHERE idmaterial = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisCodigoClienteBean vmccb = new VendasMateriaisCodigoClienteBean();

                vmccb.setId(rs.getInt("id"));
                vmccb.setCliente(rs.getString("cliente"));
                vmccb.setCodigo(rs.getString("codigo"));
                vmccb.setDescricao(rs.getString("descricao"));

                listvmccb.add(vmccb);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler códigos por cliente.\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisCodigoClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listvmccb;
    }

    public void update(VendasMateriaisCodigoClienteBean vmccb) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_materiais_cod_cli SET cliente = ?, codigo = ?, descricao = ? WHERE id = ?");

            stmt.setString(1, vmccb.getCliente());
            stmt.setString(2, vmccb.getCodigo());
            stmt.setString(3, vmccb.getDescricao());
            stmt.setInt(4, vmccb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Código por Cliente.";
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

    public void delete(int idmaterial) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_materiais_cod_cli WHERE id = " + idmaterial);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir Código por Cliente.";
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
