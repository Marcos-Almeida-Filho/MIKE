/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.TicketsBean;
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
public class TicketsDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<TicketsBean> listt;

    static TicketsBean tb;

    static String table = "tickets";

    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listt = new ArrayList<>();
    }

    public void create(String ticket, String dataAbertura, String solicitante, String assunto, int nivel, String descricao) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (ticket, dataAbertura, solicitante, assunto, nivel, descricao, status) VALUES ('" + ticket + "', '" + dataAbertura + "', '" + solicitante + "', '" + assunto + "', " + nivel + ", '" + descricao + "', 'Pendente')");

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Ticket criado com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao criar Ticket.";
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

    public List<TicketsBean> readTodos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table);

            rs = stmt.executeQuery();

            while (rs.next()) {
                tb = new TicketsBean();

                tb.setId(rs.getInt("id"));
                tb.setTicket(rs.getString("ticket"));
                tb.setAssunto(rs.getString("assunto"));
                tb.setSolicitante(rs.getString("solicitante"));
                tb.setNivel(rs.getInt("nivel"));
                tb.setStatus(rs.getString("status"));

                listt.add(tb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Tickets.";
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

        return listt;
    }

    public List<TicketsBean> readTodosPesquisa(String pesquisa) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE ticket LIKE '%" + pesquisa + "%' OR assunto LIKE '%" + pesquisa + "%' OR solicitante LIKE '%" + pesquisa + "%'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                tb = new TicketsBean();

                tb.setId(rs.getInt("id"));
                tb.setTicket(rs.getString("ticket"));
                tb.setAssunto(rs.getString("assunto"));
                tb.setSolicitante(rs.getString("solicitante"));
                tb.setNivel(rs.getInt("nivel"));
                tb.setStatus(rs.getString("status"));

                listt.add(tb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Tickets.";
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

        return listt;
    }

    public List<TicketsBean> readStatus(String status) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE status = '" + status + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                tb = new TicketsBean();

                tb.setId(rs.getInt("id"));
                tb.setTicket(rs.getString("ticket"));
                tb.setAssunto(rs.getString("assunto"));
                tb.setSolicitante(rs.getString("solicitante"));
                tb.setNivel(rs.getInt("nivel"));
                tb.setStatus(rs.getString("status"));

                listt.add(tb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Tickets.";
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

        return listt;
    }

    public List<TicketsBean> readStatusPesquisa(String status, String pesquisa) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE status = '" + status + "' AND ticket LIKE '%" + pesquisa + "%' OR status = '" + status + "' AND assunto LIKE '%" + pesquisa + "%' OR status = '" + status + "' AND solicitante LIKE '%" + pesquisa + "%'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                tb = new TicketsBean();

                tb.setId(rs.getInt("id"));
                tb.setTicket(rs.getString("ticket"));
                tb.setAssunto(rs.getString("assunto"));
                tb.setSolicitante(rs.getString("solicitante"));
                tb.setNivel(rs.getInt("nivel"));
                tb.setStatus(rs.getString("status"));

                listt.add(tb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Tickets.";
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

        return listt;
    }

    public List<TicketsBean> readTicket(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE id = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                tb = new TicketsBean();

                tb.setTicket(rs.getString("ticket"));
                tb.setDataAbertura(rs.getString("dataAbertura"));
                tb.setDataEncerramento(rs.getString("dataEncerramento"));
                tb.setStatus(rs.getString("status"));
                tb.setSolicitante(rs.getString("solicitante"));
                tb.setAssunto(rs.getString("assunto"));
                tb.setNivel(rs.getInt("nivel"));
                tb.setDescricao(rs.getString("descricao"));
                tb.setResposta(rs.getString("resposta"));

                listt.add(tb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Ticket.";
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

        return listt;
    }

    public int ticketId(String ticket) {
        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT id FROM " + table + " WHERE ticket = '" + ticket + "'");

            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler ID do Ticket.";
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

    private String readLastCreated() {
        rsList();

        String last = "";

        try {
            stmt = con.prepareStatement("SELECT ticket FROM " + table + " ORDER BY id DESC LIMIT 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                last = rs.getString("ticket");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler último Ticket criado.";
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

    public String ticketAtual() {

        rsList();

        Calendar c = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(c.getTime());
        String idtela = "TI" + year + "-0001";

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE ticket = '" + idtela + "'");
            rs = stmt.executeQuery();

            //checking if ResultSet is empty
            if (rs.next()) {
                String last = readLastCreated();
                int yearint = Integer.parseInt(last.replace("TI" + year + "-", ""));
                int yearnovo = yearint + 1;
                idtela = "TI" + year + "-" + String.format("%04d", yearnovo);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler último Ticket.";
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

        return idtela;
    }

    public void ticketFazendo(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET status = 'Fazendo' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status do Ticket.";
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

    public void encerrarTicket(int id, String data) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET status = 'Fechado', dataEncerramento = '" + data + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status do Ticket.";
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

    public void updateTicket(String assunto, int nivel, String descricao, String resposta, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET assunto = '" + assunto + "', nivel = " + nivel + ", descricao = '" + descricao + "', resposta = '" + resposta + "' WHERE id = " + id);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso.");
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Ticket.";
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
