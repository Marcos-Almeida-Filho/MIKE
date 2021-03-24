/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.RelatorioVendaDeProdutoBean;
import Bean.RelatorioVendasPorClienteBean;
import Bean.VendasPedidoItensBean;
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
public class VendasPedidoItensDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<VendasPedidoItensBean> listvpi;
    
    static List<RelatorioVendaDeProdutoBean> listrvdpb;

    static List<RelatorioVendasPorClienteBean> listrvpcb;
    
    static VendasPedidoItensBean vpib;

    static RelatorioVendaDeProdutoBean rvdpb;
    
    static RelatorioVendasPorClienteBean rvpcb;
    
    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listvpi = new ArrayList<>();
        
        listrvdpb = new ArrayList<>();
        
        listrvpcb = new ArrayList<>();
    }

    /**
     *
     * @param dav
     * @param idMaterial
     * @param codigo
     * @param descricao
     * @param qtd
     * @param valorunitario
     * @param valortotal
     * @param prazo
     * @param nf
     * @param op
     * @throws java.sql.SQLException
     */
    public void create(String dav, int idMaterial, String codigo, String descricao, double qtd, double valorunitario, double valortotal, String prazo, String nf, String op) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_pedido_itens (dav, idmaterial, codigo, descricao, qtd, valorunitario, valortotal, prazo, nf, op) VALUES ('" + dav + "', " + idMaterial + ", '" + codigo + "', '" + descricao + "', " + qtd + ", " + valorunitario + ", " + valortotal + ", '" + prazo + "', '" + nf + "', '" + op + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<VendasPedidoItensBean> readItens(String dav) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_itens WHERE dav = '" + dav + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpib = new VendasPedidoItensBean();

                vpib.setId(rs.getInt("id"));
                vpib.setIdMaterial(rs.getInt("idmaterial"));
                vpib.setCodigo(rs.getString("codigo"));
                vpib.setDescricao(rs.getString("descricao"));
                vpib.setQtd(rs.getDouble("qtd"));
                vpib.setValorunitario(rs.getDouble("valorunitario"));
                vpib.setValortotal(rs.getDouble("valortotal"));
                vpib.setPrazo(rs.getString("prazo"));
                vpib.setPedido(rs.getString("pedido"));
                vpib.setNf(rs.getString("nf"));
                vpib.setOp(rs.getString("op"));

                listvpi.add(vpib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler os itens do Pedido de Venda.";
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

        return listvpi;
    }

    public List<VendasPedidoItensBean> readItensSemNF(String ordem, String dataInicio, String dataFim) {
        rsList();

        String order = "vendas_pedido_itens." + ordem;

        try {
            stmt = con.prepareStatement("SELECT vendas_pedido_itens.* FROM vendas_pedido_itens, vendas_pedido WHERE vendas_pedido_itens.nf = '' AND vendas_pedido.status <> 'Desativado' AND vendas_pedido.pedido = vendas_pedido_itens.dav AND vendas_pedido_itens.prazo BETWEEN '" + dataInicio + "' AND '" + dataFim + "' ORDER BY " + order);
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpib = new VendasPedidoItensBean();

                vpib.setId(rs.getInt("id"));//
                vpib.setIdMaterial(rs.getInt("idmaterial"));//
                vpib.setDav(rs.getString("dav"));//
                vpib.setCodigo(rs.getString("codigo"));//
                vpib.setDescricao(rs.getString("descricao"));
                vpib.setQtd(rs.getDouble("qtd"));//
                vpib.setValorunitario(rs.getDouble("valorunitario"));//
                vpib.setValortotal(rs.getDouble("valortotal"));//
                vpib.setPrazo(rs.getString("prazo"));//
                vpib.setPedido(rs.getString("pedido"));
                vpib.setNf(rs.getString("nf"));
                vpib.setOp(rs.getString("op"));//
                vpib.setSeparado(rs.getBoolean("separado"));//

                listvpi.add(vpib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler os itens do Pedido de Venda.";
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

        return listvpi;
    }

    public List<RelatorioVendaDeProdutoBean> readItensRelatorioDeVendas(int id, String dataInicio, String dataFim) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT vendas_pedido.data_abertura, vendas_pedido.pedido, vendas_pedido.cliente, vendas_pedido_itens.valorunitario, vendas_pedido_itens.qtd, vendas_pedido_itens.nf FROM vendas_pedido_itens, vendas_pedido WHERE vendas_pedido_itens.idmaterial = " + id + " AND vendas_pedido_itens.dav = vendas_pedido.pedido AND vendas_pedido.data_abertura BETWEEN '" + dataInicio + "' AND '" + dataFim + "' AND vendas_pedido.status <> 'Desativado'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                rvdpb = new RelatorioVendaDeProdutoBean();

                rvdpb.setDataAbertura(rs.getString("vendas_pedido.data_abertura"));
                rvdpb.setPedido(rs.getString("vendas_pedido.pedido"));
                rvdpb.setCliente(rs.getString("vendas_pedido.cliente"));
                rvdpb.setQtd(rs.getDouble("vendas_pedido_itens.qtd"));
                rvdpb.setValorUnitario(rs.getDouble("vendas_pedido_itens.valorunitario"));
                rvdpb.setNf(rs.getString("vendas_pedido_itens.nf"));

                listrvdpb.add(rvdpb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler vendas do produto.";
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

        return listrvdpb;
    }
    
    public List<RelatorioVendasPorClienteBean> readItensVendasPorCliente(String cliente, String inicio, String fim) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_itens, vendas_pedido WHERE vendas_pedido.cliente = '" + cliente + "' AND vendas_pedido.status <> 'Desativado' AND vendas_pedido.pedido = vendas_pedido_itens.dav AND vendas_pedido.data_abertura BETWEEN '" + inicio + "' AND '" + fim + "' ORDER BY vendas_pedido_itens.codigo");
            rs = stmt.executeQuery();

            while (rs.next()) {
                rvpcb = new RelatorioVendasPorClienteBean();
                
                rvpcb.setData(rs.getString("vendas_pedido.data_abertura"));
                rvpcb.setDav(rs.getString("vendas_pedido_itens.dav"));
                rvpcb.setCodigo(rs.getString("vendas_pedido_itens.codigo"));
                rvpcb.setDescricao(rs.getString("vendas_pedido_itens.descricao"));
                rvpcb.setValorUnitario(rs.getDouble("vendas_pedido_itens.valorunitario"));
                rvpcb.setQtd(rs.getDouble("vendas_pedido_itens.qtd"));
                rvpcb.setNf(rs.getString("vendas_pedido_itens.nf"));
                rvpcb.setPedido(rs.getString("vendas_pedido.pedidocliente"));

                listrvpcb.add(rvpcb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler os itens do Pedido de Venda.";
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

        return listrvpcb;
    }
    
    public List<RelatorioVendasPorClienteBean> readItensVendasPorPeriodo(String inicio, String fim) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_itens, vendas_pedido WHERE vendas_pedido.status <> 'Desativado' AND vendas_pedido.pedido = vendas_pedido_itens.dav AND vendas_pedido.data_abertura BETWEEN '" + inicio + "' AND '" + fim + "' ORDER BY vendas_pedido_itens.codigo, vendas_pedido_itens.dav");
            rs = stmt.executeQuery();

            while (rs.next()) {
                rvpcb = new RelatorioVendasPorClienteBean();
                
                rvpcb.setData(rs.getString("vendas_pedido.data_abertura"));
                rvpcb.setDav(rs.getString("vendas_pedido_itens.dav"));
                rvpcb.setCliente(rs.getString("vendas_pedido.cliente"));
                rvpcb.setCodigo(rs.getString("vendas_pedido_itens.codigo"));
                rvpcb.setDescricao(rs.getString("vendas_pedido_itens.descricao"));
                rvpcb.setValorUnitario(rs.getDouble("vendas_pedido_itens.valorunitario"));
                rvpcb.setQtd(rs.getDouble("vendas_pedido_itens.qtd"));
                rvpcb.setNf(rs.getString("vendas_pedido_itens.nf"));
                rvpcb.setPedido(rs.getString("vendas_pedido.pedidocliente"));

                listrvpcb.add(rvpcb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler os itens do Pedido de Venda.";
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

        return listrvpcb;
    }
    
    public List<VendasPedidoItensBean> readItensSemOp(String dav, String codigo) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_itens WHERE dav = '" + dav + "' AND op = '' AND codigo = '" + codigo + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpib = new VendasPedidoItensBean();

                vpib.setId(rs.getInt("id"));
                vpib.setIdMaterial(rs.getInt("idmaterial"));
                vpib.setCodigo(rs.getString("codigo"));
                vpib.setDescricao(rs.getString("descricao"));
                vpib.setQtd(rs.getDouble("qtd"));
                vpib.setValorunitario(rs.getDouble("valorunitario"));
                vpib.setValortotal(rs.getDouble("valortotal"));
                vpib.setPrazo(rs.getString("prazo"));
                vpib.setPedido(rs.getString("pedido"));
                vpib.setNf(rs.getString("nf"));
                vpib.setOp(rs.getString("op"));

                listvpi.add(vpib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler os itens do Pedido de Venda.";
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

        return listvpi;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<VendasPedidoItensBean> readItem(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_itens WHERE id = " + id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                vpib = new VendasPedidoItensBean();

                vpib.setIdMaterial(rs.getInt("idmaterial"));
                vpib.setCodigo(rs.getString("codigo"));
                vpib.setDescricao(rs.getString("descricao"));
                vpib.setQtd(rs.getDouble("qtd"));
                vpib.setValorunitario(rs.getDouble("valorunitario"));
                vpib.setValortotal(rs.getDouble("valortotal"));
                vpib.setPrazo(rs.getString("prazo"));
                vpib.setNf(rs.getString("nf"));
                vpib.setOp(rs.getString("op"));

                listvpi.add(vpib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler item do Pedido de Venda.";
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

        return listvpi;
    }
    
    public int getIdItemNota(int id) {
        rsList();

        int idItemNota = 0;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_pedido_itens WHERE id = " + id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                idItemNota = rs.getInt("idItemNota");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler item do Pedido de Venda.";
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

        return idItemNota;
    }

    /**
     *
     * @param idMaterial
     * @param codigo
     * @param descricao
     * @param qtd
     * @param valorunitario
     * @param valortotal
     * @param prazo
     * @param id
     * @throws java.sql.SQLException
     */
    public void update(int idMaterial, String codigo, String descricao, double qtd, double valorunitario, double valortotal, String prazo, int id) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("UPDATE vendas_pedido_itens SET idmaterial = " + idMaterial + ", codigo = '" + codigo + "', descricao = '" + descricao + "', qtd = " + qtd + ", valorunitario = " + valorunitario + ", valortotal = " + valortotal + ", prazo = '" + prazo + "' WHERE id = " + id);
        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }
    
    public void updateNotaFiscalParcial(String nf, int idItemPedido, int idItemNota, double qtd) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_itens SET nf = '" + nf + "', idItemNota = " + idItemNota + ", qtd = " + qtd + " WHERE id = " + idItemPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar nota fiscal do item do Pedido de Venda.";
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

    public void updateNotaFiscal(String nf, int idItemPedido, int idItemNota) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_itens SET nf = '" + nf + "', idItemNota = " + idItemNota + " WHERE id = " + idItemPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar nota fiscal do item do Pedido de Venda.";
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

    public void updateOP(String op, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_itens SET op = '" + op + "' WHERE id = " + id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar OP do item do Pedido de Venda.";
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

    public void updateMaterial(String codigo, String descricao, int idmaterial) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_itens SET codigo = '" + codigo + "', descricao = '" + descricao + "' WHERE idmaterial = " + idmaterial);
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar OP do item do Pedido de Venda.";
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

    public void updateDataEntrega(String op, String dataPrevista) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_itens SET prazo = '" + dataPrevista + "' WHERE op = '" + op + "'");
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar OP do item do Pedido de Venda.";
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

    public void updateSeparado(int idItemPedido) {
        conStmt();
        
        try {
            stmt = con.prepareStatement("UPDATE vendas_pedido_itens set separado = " + true + " WHERE id = " + idItemPedido);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao separar Item do Pedido de Venda.";
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
            stmt = con.prepareStatement("DELETE FROM vendas_pedido_itens WHERE id = " + id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Item excluído com sucesso!");
        } catch (SQLException e) {
            String msg = "Erro ao excluir item do Pedido de Venda";
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
