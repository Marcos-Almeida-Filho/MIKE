/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasMateriaisObsBean;
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
public class VendasMateriaisObsDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<VendasMateriaisObsBean> listvmob;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        rs = null;

        listvmob = new ArrayList<>();
    }

    /**
     * Método para criação de observação em material de venda.
     *
     * @param idmaterial Int com o ID do material no Banco de Dados
     * @param data String da data que a observação foi criada
     * @param usuario String com o nome do usuário que fez a observação
     * @param obs String da observação feita
     * @throws java.sql.SQLException
     *
     * @since 2.0.0
     */
    public void create(int idmaterial, String data, String usuario, String obs) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_materiais_obs (idmaterial, data, usuario, obs) VALUES (" + idmaterial + ",'" + data + "','" + usuario + "','" + obs + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<VendasMateriaisObsBean> read(int id) {
        conStmt();

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais_obs WHERE idmaterial = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                VendasMateriaisObsBean vmob = new VendasMateriaisObsBean();

                vmob.setId(rs.getInt("id"));
                vmob.setData(rs.getString("data"));
                vmob.setUsuario(rs.getString("usuario"));
                vmob.setObs(rs.getString("obs"));

                listvmob.add(vmob);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler observações.\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisObsDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listvmob;
    }

    public void update(VendasMateriaisObsBean vmob) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_materiais_obs SET ");

        } catch (SQLException e) {
            String msg = "Erro ao atualizar observação do Material.";
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
            stmt = con.prepareStatement("DELETE FROM vendas_materiais_obs WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir observação do Material.";
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
