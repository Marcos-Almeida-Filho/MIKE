/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.InsumosMovBean;
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
public class InsumosMovDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<InsumosMovBean> listimb;

    InsumosMovBean imb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listimb = new ArrayList<>();
    }

    public void create(int idInsumo, double qtdInicial, double qtdMov, double qtdFinal, String data, String tipoMov, String funcionario) throws SQLException {

        conStmt();

        stmt = con.prepareStatement("INSERT INTO insumos_mov (idinsumo, data, tipomov, qtdinicial, qtdmov, qtdfinal, funcionario) VALUES (" + idInsumo + ", '" + data + "', '" + tipoMov + "', " + qtdInicial + ", " + qtdMov + ", " + qtdFinal + ", '" + funcionario + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<InsumosMovBean> read(int idInsumo) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos_mov WHERE idinsumo = " + idInsumo);

            rs = stmt.executeQuery();

            while (rs.next()) {
                imb = new InsumosMovBean();

                imb.setId(rs.getInt("id"));
                imb.setIdinsumo(rs.getInt("idinsumo"));
                imb.setData(rs.getString("data"));
                imb.setTipomov(rs.getString("tipomov"));
                imb.setQtdinicial(rs.getDouble("qtdinicial"));
                imb.setQtdmov(rs.getDouble("qtdmov"));
                imb.setQtdfinal(rs.getDouble("qtdfinal"));
                imb.setFuncionario(rs.getString("funcionario"));

                listimb.add(imb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Movimentações do Insumo.";

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
        return listimb;
    }
}
