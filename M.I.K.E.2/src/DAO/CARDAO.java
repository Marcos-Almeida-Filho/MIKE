/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CARBean;
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
public class CARDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<CARBean> listcb;

    CARBean cb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listcb = new ArrayList<>();
    }

    public void create(String dataLancamento, String cliente, int notaFiscal, String dataEmissao, double total, String parcela, double valorParcela, String dataParcela) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO car (datalancamento, cliente, notafiscal, dataemissao, total, parcela, valorparcela, dataparcela, status) VALUES ('" + dataLancamento + "', '" + cliente + "', " + notaFiscal + ", '" + dataEmissao + "', " + total + ", '" + parcela + "', " + valorParcela + ", '" + dataParcela + "', 'Pendente')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar Conta a Receber.";
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

    public List<CARBean> readtodos() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM car ORDER BY dataparcela");

            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setDatalancamento(rs.getString("datalancamento"));
                cb.setCliente(rs.getString("cliente"));
                cb.setNotafiscal(rs.getInt("notafiscal"));
                cb.setDataemissao(rs.getString("dataemissao"));
                cb.setTotal(rs.getDouble("total"));
                cb.setParcela(rs.getString("parcela"));
                cb.setValorparcela(rs.getDouble("valorparcela"));
                cb.setDataparcela(rs.getString("dataparcela"));
                cb.setDatarecebimento(rs.getString("datarecebimento"));
                cb.setBanco(rs.getString("banco"));
                cb.setMetodo(rs.getString("metodo"));
                cb.setStatus(rs.getString("status"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler CAR.";
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
        return listcb;
    }

    public List<CARBean> readaberto() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM car WHERE datarecebimento IS NULL ORDER BY dataparcela");

            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setDatalancamento(rs.getString("datalancamento"));
                cb.setCliente(rs.getString("cliente"));
                cb.setNotafiscal(rs.getInt("notafiscal"));
                cb.setDataemissao(rs.getString("dataemissao"));
                cb.setTotal(rs.getDouble("total"));
                cb.setParcela(rs.getString("parcela"));
                cb.setValorparcela(rs.getDouble("valorparcela"));
                cb.setDataparcela(rs.getString("dataparcela"));
                cb.setDatarecebimento(rs.getString("datarecebimento"));
                cb.setBanco(rs.getString("banco"));
                cb.setMetodo(rs.getString("metodo"));
                cb.setStatus(rs.getString("status"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler CAR.";
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
        return listcb;
    }

    public List<CARBean> readcreated(String data) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM car WHERE datalancamento = ?");
            stmt.setString(1, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler último CAR lançado.";
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
        return listcb;
    }

    public List<CARBean> readstatus(String status) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM car WHERE status = ? ORDER BY dataparcela");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setDatalancamento(rs.getString("datalancamento"));
                cb.setCliente(rs.getString("cliente"));
                cb.setNotafiscal(rs.getInt("notafiscal"));
                cb.setDataemissao(rs.getString("dataemissao"));
                cb.setTotal(rs.getDouble("total"));
                cb.setParcela(rs.getString("parcela"));
                cb.setValorparcela(rs.getDouble("valorparcela"));
                cb.setDataparcela(rs.getString("dataparcela"));
                cb.setDatarecebimento(rs.getString("datarecebimento"));
                cb.setBanco(rs.getString("banco"));
                cb.setMetodo(rs.getString("metodo"));
                cb.setStatus(rs.getString("status"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler CAR.";
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
        return listcb;
    }

    public List<CARBean> click(int id) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM car WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setDatalancamento(rs.getString("datalancamento"));
                cb.setCliente(rs.getString("cliente"));
                cb.setNotafiscal(rs.getInt("notafiscal"));
                cb.setDataemissao(rs.getString("dataemissao"));
                cb.setTotal(rs.getDouble("total"));
                cb.setParcela(rs.getString("parcela"));
                cb.setValorparcela(rs.getDouble("valorparcela"));
                cb.setDataparcela(rs.getString("dataparcela"));
                cb.setDatarecebimento(rs.getString("datarecebimento"));

                listcb.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler CAR.";
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
        return listcb;
    }

    /**
     *
     * @param cliente
     * @param notaFiscal
     * @param total
     * @param parcela
     * @param valorParcela
     * @param dataParcela
     * @param dataRecebimento
     * @param valorRecebido
     * @param banco
     * @param metodo
     * @param id
     */
    public void update(String cliente, int notaFiscal, double total, String parcela, double valorParcela, String dataParcela, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE car SET cliente = '" + cliente + "', notafiscal = " + notaFiscal + ", total = " + total + ", parcela = '" + parcela + "', valorparcela = " + valorParcela + ", dataparcela = '" + dataParcela + "' WHERE id =" + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar CAR.";
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

    /**
     *
     * @param dataRecebimento
     * @param valorRecebido
     * @param banco
     * @param metodo
     * @param cheque
     * @param id
     */
    public void updaterecebimento(String dataRecebimento, double valorRecebido, String banco, String metodo, String cheque, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE car SET datarecebimento = '" + dataRecebimento + "', valorrecebido = " + valorRecebido + ", banco = '" + banco + "', metodo = '" + metodo + "', cheque = '" + cheque + "', status = 'Pago' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar CAR.";
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

    public void cancelarConta(int notaFiscal) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE car SET status = 'Cancelado' WHERE notafiscal = " + notaFiscal);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar CAR.";
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
