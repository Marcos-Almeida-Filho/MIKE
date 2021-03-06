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
     * @param frete
     * @param pedidocliente
     * @throws java.sql.SQLException
     */
    public void create(String pedido, String dataAbertura, String cliente, String status, String vendedor, String representante, String condicaoPagamento, double frete, String pedidocliente) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_pedido (pedido, data_abertura, cliente, status, vendedor, representante, condicao, frete, pedidocliente) VALUES ('" + pedido + "','" + dataAbertura + "','" + cliente + "','" + status + "','" + vendedor + "','" + representante + "','" + condicaoPagamento + "', " + frete + ", '" + pedidocliente + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
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
                vpb.setPedidocliente(rs.getString("pedidocliente"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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

        return listvp;
    }

    public List<VendasPedidoBean> readPedidosPesquisa(String pesquisa) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE cliente LIKE '%" + pesquisa + "%' OR pedido LIKE '%" + pesquisa + "%' OR pedidocliente LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpb = new VendasPedidoBean();

                vpb.setId(rs.getInt("id"));
                vpb.setPedido(rs.getString("pedido"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setStatus(rs.getString("status"));
                vpb.setPedidocliente(rs.getString("pedidocliente"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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

        return listvp;
    }

    public List<VendasPedidoBean> readPedidosAbertos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE status <> 'Faturado' AND status <> 'Desativado'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpb = new VendasPedidoBean();

                vpb.setId(rs.getInt("id"));
                vpb.setPedido(rs.getString("pedido"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setStatus(rs.getString("status"));
                vpb.setPedidocliente(rs.getString("pedidocliente"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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

        return listvp;
    }

    public List<VendasPedidoBean> readPedidosAbertosPesquisa(String pesquisa) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE status <> 'Fechado' AND status <> 'Desativado' AND pedido LIKE '%" + pesquisa + "%' OR status <> 'Fechado' AND status <> 'Desativado' AND cliente LIKE '%" + pesquisa + "%' OR status <> 'Fechado' AND status <> 'Desativado' AND pedidocliente LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpb = new VendasPedidoBean();

                vpb.setId(rs.getInt("id"));
                vpb.setPedido(rs.getString("pedido"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setStatus(rs.getString("status"));
                vpb.setPedidocliente(rs.getString("pedidocliente"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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

        return listvp;
    }

    public List<VendasPedidoBean> readPedidosAbertosPorCliente(String cliente) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE status <> 'Fechado' AND status <> 'Desativado' AND cliente = '" + cliente + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpb = new VendasPedidoBean();

                vpb.setId(rs.getInt("id"));
                vpb.setPedido(rs.getString("pedido"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setStatus(rs.getString("status"));
                vpb.setPedidocliente(rs.getString("pedidocliente"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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
                vpb.setPedidocliente(rs.getString("pedidocliente"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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

        return listvp;
    }

    public List<VendasPedidoBean> readPedidosStatusPesquisa(String status, String pesquisa) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido WHERE status = '" + status + "' AND pedido LIKE '%" + pesquisa + "%' OR status = '" + status + "' AND cliente LIKE '%" + pesquisa + "%' OR pedidocliente LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpb = new VendasPedidoBean();

                vpb.setId(rs.getInt("id"));
                vpb.setPedido(rs.getString("pedido"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setStatus(rs.getString("status"));
                vpb.setPedidocliente(rs.getString("pedidocliente"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedidos abertos.";
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

                vpb.setPedido(rs.getString("pedido"));
                vpb.setData_abertura(rs.getString("data_abertura"));
                vpb.setCliente(rs.getString("cliente"));
                vpb.setVendedor(rs.getString("vendedor"));
                vpb.setRepresentante(rs.getString("representante"));
                vpb.setCondicao(rs.getString("condicao"));
                vpb.setFrete(rs.getDouble("frete"));
                vpb.setStatus(rs.getString("status"));
                vpb.setPedidocliente(rs.getString("pedidocliente"));

                listvp.add(vpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Pedido selecionado.";
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

        return listvp;
    }

    public int readIdPedido(String pedido) {
        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT id FROM vendas_pedido WHERE pedido = '" + pedido + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler último Pedido criado.";
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

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return motivo;
    }

    public String readCliente(String pedido) {
        rsList();

        String cliente = "";

        try {
            stmt = con.prepareStatement("SELECT cliente FROM vendas_pedido WHERE pedido = '" + pedido + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                cliente = rs.getString("cliente");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler último Pedido criado.";
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

        return cliente;
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
            String msg = "Erro ao ler último Pedido de Venda.";
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

    /**
     *
     * @param pedido
     * @param cliente
     * @param vendedor
     * @param representante
     * @param condicaoPagamento
     * @param frete
     * @param pedidocliente
     * @throws java.sql.SQLException
     */
    public void update(String pedido, String cliente, String vendedor, String representante, String condicaoPagamento, double frete, String pedidocliente) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("UPDATE vendas_pedido SET cliente = '" + cliente + "', vendedor = '" + vendedor + "', representante = '" + representante + "', condicao = '" + condicaoPagamento + "', frete = " + frete + ", pedidocliente = '" + pedidocliente + "' WHERE pedido = '" + pedido + "'");
        stmt.executeUpdate();

        PedidoVenda.pedidoAtualizado = true;

        ConnectionFactory.closeConnection(con, stmt);
    }

    public void updateStatus(String pedido, String status) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido SET status = '" + status + "' WHERE pedido = '" + pedido + "'");
            stmt.executeUpdate();

            PedidoVenda.pedidoAtualizado = true;
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Pedido de Venda!";
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
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
