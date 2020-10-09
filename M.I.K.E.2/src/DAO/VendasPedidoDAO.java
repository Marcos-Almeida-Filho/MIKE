/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasPedidoBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import View.vendas.PedidoVenda;
import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class VendasPedidoDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<VendasPedidoBean> listvp;

    static VendasPedidoBean vpb;

    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listvp = new ArrayList<>();
    }

    /**
     *
     * @param pedido
     * @param dataAbertura
     * @param cliente
     * @param status
     * @param vendedor
     * @param representante
     * @param condicaoPagamento
     */
    public void create(String pedido, String dataAbertura, String cliente, String status, String vendedor, String representante, String condicaoPagamento) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO vendas_pedido (pedido, data_abertura, cliente, status, vendedor, representante, condicao) VALUES ('" + pedido + "','" + dataAbertura + "','" + cliente + "','" + status + "','" + vendedor + "','" + representante + "','" + condicaoPagamento + "')");

            stmt.executeUpdate();

            PedidoVenda.pedidoCriado = true;
        } catch (SQLException e) {
            String msg = "Erro ao criar Pedido de Venda!";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + "\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public List<VendasPedidoBean> readPedidos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpb = new VendasPedidoBean();

                vpb.setId(rs.getInt("id"));
                vpb.setPedido(rs.getString("pedido"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setStatus(rs.getString("status"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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

        return listvp;
    }

    public List<VendasPedidoBean> readPedidosAbertos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE status <> 'Fechado' AND status <> 'Desativado'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpb = new VendasPedidoBean();

                vpb.setId(rs.getInt("id"));
                vpb.setPedido(rs.getString("pedido"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setStatus(rs.getString("status"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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

        return listvp;
    }
    
    public List<VendasPedidoBean> readPedidosStatus(String status) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE status = '" + status + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpb = new VendasPedidoBean();

                vpb.setId(rs.getInt("id"));
                vpb.setPedido(rs.getString("pedido"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setStatus(rs.getString("status"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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

        return listvp;
    }

    /**
     *
     * @param pedido
     * @return
     */
    public List<VendasPedidoBean> readPedido(String pedido) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE pedido = '" + pedido + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpb = new VendasPedidoBean();

                vpb.setData_abertura(rs.getString("data_abertura"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setVendedor(rs.getString("vendedor"));
                vpb.setRepresentante(rs.getString("representante"));
                vpb.setCondicao(rs.getString("condicao"));
                vpb.setStatus(rs.getString("status"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedido selecionado.";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + "\n" + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listvp;
    }

    public Boolean readnome() throws SQLException {

        rsList();

        Calendar c = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(c.getTime());
        String idtela = "PV" + year + "-0001";

        Boolean resp = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE pedido = '" + idtela + "'");
            rs = stmt.executeQuery();

            // checking if ResultSet is empty
            resp = rs.next();
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return resp;
    }

    public String readLastCreated() {
        rsList();

        String last = "";

        try {
            stmt = con.prepareStatement("SELECT pedido FROM vendas_pedido ORDER BY id DESC LIMIT 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                last = rs.getString("pedido");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler último Pedido criado.";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return last;
    }

    public String readMotivo(String pedido) {
        rsList();

        String motivo = "";

        try {
            stmt = con.prepareStatement("SELECT motivo FROM vendas_pedido WHERE pedido = '" + pedido + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                motivo = rs.getString("motivo");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler último Pedido criado.";
            JOptionPane.showMessageDialog(null, msg);
            SendEmail.EnviarErro2(msg + e);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return motivo;
    }

    public String pedidoAtual() {

        rsList();

        Calendar c = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(c.getTime());
        String idtela = "PV" + year + "-0001";

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE pedido = '" + idtela + "'");
            rs = stmt.executeQuery();

            //checking if ResultSet is empty
            if (rs.next()) {
                String last = readLastCreated();
                int yearint = Integer.parseInt(last.replace("PV" + year + "-", ""));
                int yearnovo = yearint + 1;
                idtela = "PV" + year + "-" + String.format("%04d", yearnovo);
            }
        } catch (SQLException e) {
            Logger.getLogger(VendasCotacaoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasCotacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return idtela;
    }

    /**
     *
     * @param pedido
     * @param cliente
     * @param vendedor
     * @param representante
     * @param condicaoPagamento
     */
    public void update(String pedido, String cliente, String vendedor, String representante, String condicaoPagamento) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido SET cliente = '" + cliente + "', vendedor = '" + vendedor + "', representante = '" + representante + "', condicao = '" + condicaoPagamento + "' WHERE pedido = '" + pedido + "'");
            stmt.executeUpdate();

            PedidoVenda.pedidoAtualizado = true;
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Pedido de Venda!";
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

    public void desativarPedido(String pedido, String motivo) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido SET status = 'Desativado', motivo = '" + motivo + "' WHERE pedido = '" + pedido + "'");
            stmt.executeUpdate();

            PedidoVenda.pedidoAtualizado = true;
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Pedido de Venda!";
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
