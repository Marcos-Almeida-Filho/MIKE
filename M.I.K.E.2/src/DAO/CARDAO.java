/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.CARBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class CARDAO extends GenericDAO {

    String table = "car";

    public void create(int idCliente, String dataLancamento, String cliente, int notaFiscal, String dataEmissao, double total, String parcela, double valorParcela, String dataParcela) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (idCliente, datalancamento, cliente, notafiscal, dataemissao, total, parcela, valorparcela, dataparcela, status) VALUES (" + idCliente + ", '" + dataLancamento + "', '" + cliente + "', " + notaFiscal + ", '" + dataEmissao + "', " + total + ", '" + parcela + "', " + valorParcela + ", '" + dataParcela + "', 'Pendente')");

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

    public List<CARBean> readtodos(String dataInicio, String dataFim) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE dataparcela BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ORDER BY dataparcela");

            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setIdCliente(rs.getInt("idCliente"));
                cb.setDatalancamento(rs.getString("datalancamento"));
                cb.setCliente(rs.getString("cliente"));
                cb.setNotafiscal(rs.getInt("notafiscal"));
                cb.setDataemissao(rs.getString("dataemissao"));
                cb.setTotal(rs.getDouble("total"));
                cb.setParcela(rs.getString("parcela"));
                cb.setValorparcela(rs.getDouble("valorparcela"));
                cb.setValorrecebido(rs.getDouble("valorrecebido"));
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

    public List<CARBean> readtodosPesquisa(String pesquisa, String dataInicio, String dataFim) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE (notafiscal LIKE '%" + pesquisa + "%' OR cliente LIKE '%" + pesquisa + "%') AND dataparcela BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ORDER BY dataparcela");

            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setIdCliente(rs.getInt("idCliente"));
                cb.setDatalancamento(rs.getString("datalancamento"));
                cb.setCliente(rs.getString("cliente"));
                cb.setNotafiscal(rs.getInt("notafiscal"));
                cb.setDataemissao(rs.getString("dataemissao"));
                cb.setTotal(rs.getDouble("total"));
                cb.setParcela(rs.getString("parcela"));
                cb.setValorparcela(rs.getDouble("valorparcela"));
                cb.setValorrecebido(rs.getDouble("valorrecebido"));
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
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE datarecebimento IS NULL ORDER BY dataparcela");

            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setIdCliente(rs.getInt("idCliente"));
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

    public int readcreated(String data) {

        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE datalancamento = ?");
            stmt.setString(1, data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
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
        return id;
    }

    public List<CARBean> readstatus(String status, String dataInicio, String dataFim) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE status = ? AND dataparcela BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ORDER BY dataparcela");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setIdCliente(rs.getInt("idCliente"));
                cb.setDatalancamento(rs.getString("datalancamento"));
                cb.setCliente(rs.getString("cliente"));
                cb.setNotafiscal(rs.getInt("notafiscal"));
                cb.setDataemissao(rs.getString("dataemissao"));
                cb.setTotal(rs.getDouble("total"));
                cb.setParcela(rs.getString("parcela"));
                cb.setValorparcela(rs.getDouble("valorparcela"));
                cb.setValorrecebido(rs.getDouble("valorrecebido"));
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

    public List<CARBean> readstatusPesquisa(String status, String pesquisa, String dataInicio, String dataFim) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE status = ? AND (notafiscal LIKE '%" + pesquisa + "%' OR cliente LIKE '%" + pesquisa + "%') AND dataparcela BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ORDER BY dataparcela");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setIdCliente(rs.getInt("idCliente"));
                cb.setDatalancamento(rs.getString("datalancamento"));
                cb.setCliente(rs.getString("cliente"));
                cb.setNotafiscal(rs.getInt("notafiscal"));
                cb.setDataemissao(rs.getString("dataemissao"));
                cb.setTotal(rs.getDouble("total"));
                cb.setParcela(rs.getString("parcela"));
                cb.setValorparcela(rs.getDouble("valorparcela"));
                cb.setValorrecebido(rs.getDouble("valorrecebido"));
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
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new CARBean();

                cb.setId(rs.getInt("id"));
                cb.setIdCliente(rs.getInt("idCliente"));
                cb.setDatalancamento(rs.getString("datalancamento"));
                cb.setCliente(rs.getString("cliente"));
                cb.setNotafiscal(rs.getInt("notafiscal"));
                cb.setDataemissao(rs.getString("dataemissao"));
                cb.setTotal(rs.getDouble("total"));
                cb.setParcela(rs.getString("parcela"));
                cb.setValorparcela(rs.getDouble("valorparcela"));
                cb.setValorrecebido(rs.getDouble("valorrecebido"));
                cb.setDataparcela(rs.getString("dataparcela"));
                cb.setDatarecebimento(rs.getString("datarecebimento"));
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

    public double getValorFaturado(String dataInicio, String dataFim) {
        rsList();

        double valor = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE parcela LIKE '%1' AND dataemissao BETWEEN '" + dataInicio + "' AND '" + dataFim + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                valor += rs.getDouble("total");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Faturamento do Dia.";
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

        return valor;
    }

    /**
     *
     * @param cliente
     * @param notaFiscal
     * @param total
     * @param parcela
     * @param valorParcela
     * @param dataParcela
     * @param id
     */
    public void update(int idCliente, String cliente, int notaFiscal, double total, String parcela, double valorParcela, String dataParcela, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET idCliente = " + idCliente + ", cliente = '" + cliente + "', notafiscal = " + notaFiscal + ", total = " + total + ", parcela = '" + parcela + "', valorparcela = " + valorParcela + ", dataparcela = '" + dataParcela + "' WHERE id =" + id);

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

    public void updateStatus(String status, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET status = '" + status + "' WHERE id =" + id);

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
     * @param status
     * @param id
     */
    public void updaterecebimento(String dataRecebimento, double valorRecebido, String banco, String metodo, String cheque, String status, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET datarecebimento = '" + dataRecebimento + "', valorrecebido = " + valorRecebido + ", banco = '" + banco + "', metodo = '" + metodo + "', cheque = '" + cheque + "', status = '" + status + "' WHERE id = " + id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Recebido com sucesso!");
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
            stmt = con.prepareStatement("UPDATE " + table + " SET status = 'Cancelado' WHERE notafiscal = " + notaFiscal);

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
