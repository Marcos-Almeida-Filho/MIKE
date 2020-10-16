/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OPBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class OPDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<OPBean> listob;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listob = new ArrayList<>();
    }

    /**
     *
     * @param op
     * @param dataabertura
     * @param dataprevista
     * @param cliente
     * @param dav
     * @param codigo
     * @param descricao
     * @param qtd
     * @param status
     */
    public void create(String op, String dataabertura, String dataprevista, String cliente, String dav, String codigo, String descricao, double qtd, String status) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO op (op, dataabertura, dataprevista, cliente, dav, codigo, descricao, qtd, status) VALUES ('" + op + "', '" + dataabertura + "', '" + dataprevista + "', '" + cliente + "', '" + dav + "', '" + codigo + "', '" + descricao + "', " + qtd + ", '" + status + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     *
     * @return
     */
    public List<OPBean> readTodasOPs() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM op");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OPBean ob = new OPBean();

                ob.setId(rs.getInt("id"));
                ob.setOp(rs.getString("op"));
                ob.setDataabertura(rs.getString("dataabertura"));
                ob.setDataprevista(rs.getString("dataprevista"));
                ob.setCliente(rs.getString("cliente"));
                ob.setCodigo(rs.getString("codigo"));
                ob.setDescricao(rs.getString("descricao"));
                ob.setStatus(rs.getString("status"));

                listob.add(ob);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler OPs.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listob;
    }

    private String readLastOP() {
        rsList();

        String last = "";

        try {
            stmt = con.prepareStatement("SELECT op FROM op ORDER BY id DESC LIMIT 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                last = rs.getString("op");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler última OP criada.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return last;
    }

    public String opAtual() {

        rsList();

        Calendar c = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(c.getTime());
        String idtela = "OP" + year + "-0001";

        try {
            stmt = con.prepareStatement("SELECT * FROM op WHERE op = '" + idtela + "'");
            rs = stmt.executeQuery();

            //checking if ResultSet is empty
            if (rs.next()) {
                String last = readLastOP();
                int yearint = Integer.parseInt(last.replace("OP" + year + "-", ""));
                int yearnovo = yearint + 1;
                idtela = "OP" + year + "-" + String.format("%04d", yearnovo);
            }
        } catch (SQLException e) {
            String msg = "Erro ao verificar próxima OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return idtela;
    }

    /**
     *
     * @param op
     * @return
     */
    public List<OPBean> readOP(String op) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM op WHERE op = '" + op + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OPBean ob = new OPBean();

                ob.setId(rs.getInt("id"));
                ob.setOp(rs.getString("op"));
                ob.setDataabertura(rs.getString("dataabertura"));
                ob.setDataprevista(rs.getString("dataprevista"));
                ob.setCliente(rs.getString("cliente"));
                ob.setDav(rs.getString("dav"));
                ob.setCodigo(rs.getString("codigo"));
                ob.setDescricao(rs.getString("descricao"));
                ob.setQtd(rs.getDouble("qtd"));
                ob.setQtdok(rs.getDouble("qtdok"));
                ob.setQtdnaook(rs.getDouble("qtdnaook"));
                ob.setStatus(rs.getString("status"));

                listob.add(ob);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listob;
    }

    /**
     *
     * @param op
     * @param cliente
     * @param codigo
     * @param descricao
     * @param qtd
     */
    public void updateOP(String op, String cliente, String codigo, String descricao, double qtd) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE op SET cliente = '" + cliente + "', codigo = '" + codigo + "', descricao = '" + descricao + "', qtd = " + qtd + " WHERE op = '" + op + "'");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     *
     * @param op
     * @param status
     */
    public void updateStatus(String op, String status) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE op SET status = '" + status + "' WHERE op = '" + op + "'");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status da OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     *
     * @param id
     */
    public void deleteOP(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM op WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao deletar OP.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
