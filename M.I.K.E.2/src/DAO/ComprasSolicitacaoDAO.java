/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ComprasSolicitacaoBean;
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
public class ComprasSolicitacaoDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ComprasSolicitacaoBean> listcs;

    ComprasSolicitacaoBean csb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listcs = new ArrayList<>();
    }

    public void create(String solicitacao, String data, String solicitante, String tipo) throws SQLException {

        conStmt();

        stmt = con.prepareStatement("INSERT INTO compras_solicitacao (solicitacao, data, solicitante, tipo, status) VALUES ('" + solicitacao + "', '" + data + "', '" + solicitante + "', '" + tipo + "', 'Ativo')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<ComprasSolicitacaoBean> read() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compras_solicitacao");
            rs = stmt.executeQuery();

            while (rs.next()) {
                csb = new ComprasSolicitacaoBean();

                csb.setId(rs.getInt("id"));
                csb.setSolicitacao(rs.getString("solicitacao"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listcs.add(csb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Solicitações de Compras.";
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
        return listcs;
    }

    public List<ComprasSolicitacaoBean> readcreated(String data) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compras_solicitacao WHERE data = " + data);
            rs = stmt.executeQuery();

            while (rs.next()) {
                csb = new ComprasSolicitacaoBean();

                csb.setSolicitacao(rs.getString("solicitacao"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listcs.add(csb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Solicitação de Compras criada.";
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
        return listcs;
    }

    public List<ComprasSolicitacaoBean> reademaberto() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compras_solicitacao WHERE status = 'Ativo' OR status = 'Pedido Parcial'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                csb = new ComprasSolicitacaoBean();

                csb.setSolicitacao(rs.getString("solicitacao"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listcs.add(csb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Solicitações de Compras em aberto.";
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
        return listcs;
    }

    public List<ComprasSolicitacaoBean> readstatus(String status) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compras_solicitacao WHERE status = '" + status + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                csb = new ComprasSolicitacaoBean();

                csb.setSolicitacao(rs.getString("solicitacao"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listcs.add(csb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Solicitações de Compras por Status.";
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
        return listcs;
    }

    private String readLastCreated() {
        rsList();

        String last = "";

        try {
            stmt = con.prepareStatement("SELECT solicitacao FROM compras_solicitacao ORDER BY id DESC LIMIT 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                last = rs.getString("solicitacao");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler última Solicitação de Compra criada.";
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

        return last;
    }

    public String solicitacaoAtual() throws SQLException {

        rsList();

        Calendar c = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(c.getTime());
        String solicitacao = "SC" + year + "-0001";

        stmt = con.prepareStatement("SELECT * FROM compras_solicitacao WHERE solicitacao = '" + solicitacao + "'");
        rs = stmt.executeQuery();

        //checking if ResultSet is empty
        if (rs.next()) {
            String last = readLastCreated();
            int yearint = Integer.parseInt(last.replace("SC" + year + "-", ""));
            int yearnovo = yearint + 1;
            solicitacao = "SC" + year + "-" + String.format("%04d", yearnovo);
        }

        ConnectionFactory.closeConnection(con, stmt, rs);

        return solicitacao;
    }

    public int idSolicitacao(String solicitacao) throws SQLException {
        rsList();

        int id = 0;

        stmt = con.prepareStatement("SELECT * FROM compras_solicitacao WHERE solicitacao = '" + solicitacao + "'");

        rs = stmt.executeQuery();

        if (rs.next()) {
            id = rs.getInt("id");
        }

        ConnectionFactory.closeConnection(con, stmt, rs);

        return id;
    }

    public List<ComprasSolicitacaoBean> click(int idSolicitacao) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compras_solicitacao WHERE id = " + idSolicitacao);

            rs = stmt.executeQuery();

            while (rs.next()) {
                csb = new ComprasSolicitacaoBean();

                csb.setSolicitacao(rs.getString("solicitacao"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listcs.add(csb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Solicitação de Compra.";
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
        return listcs;
    }

    public List<ComprasSolicitacaoBean> pesquisa(String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM compras_solicitacao WHERE solicitacao LIKE '%" + pesquisa + "%' OR solicitante LIKE '%" + pesquisa + "%' OR tipo LIKE '%" + pesquisa + "%' OR status LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                csb = new ComprasSolicitacaoBean();

                csb.setSolicitacao(rs.getString("solicitacao"));
                csb.setData(rs.getString("data"));
                csb.setSolicitante(rs.getString("solicitante"));
                csb.setTipo(rs.getString("tipo"));
                csb.setStatus(rs.getString("status"));

                listcs.add(csb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao pesquisar Solicitações de Compras.";
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
        return listcs;
    }

    public void update(String solicitante, String tipo, int idSolicitacao) throws SQLException {

        conStmt();

        stmt = con.prepareStatement("UPDATE compras_solicitacao SET solicitante = '" + solicitante + "', tipo = '" + tipo + "' WHERE id = " + idSolicitacao);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);

    }

    public void updatestatus(String status, String solicitacao) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE compras_solicitacao SET status = '" + status + "' WHERE solicitacao = '" + solicitacao + "'");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status da Solicitação de Compras.";
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
