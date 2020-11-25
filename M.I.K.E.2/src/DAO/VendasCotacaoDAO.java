/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasCotacaoBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
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
public class VendasCotacaoDAO {

    static Connection con;

    static PreparedStatement stmt;

    static ResultSet rs;

    static List<VendasCotacaoBean> listvc;

    static VendasCotacaoBean vcb;

    public static void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public static void rsList() {
        conStmt();

        rs = null;

        listvc = new ArrayList<>();
    }

    /**
     *
     * @param cotacao
     * @param dataAbertura
     * @param cliente
     * @param cadastrado
     * @param status
     * @param vendedor
     * @param representante
     * @param condicaoPagamento
     */
    public void create(String cotacao, String dataAbertura, String cliente, boolean cadastrado, String status, String vendedor, String representante, String condicaoPagamento) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_cotacao (cotacao, data_abertura, cliente, cadastrado, status, vendedor, representante, condicao) VALUES ('" + cotacao + "','" + dataAbertura + "','" + cliente + "'," + cadastrado + ",'" + status + "','" + vendedor + "','" + representante + "','" + condicaoPagamento + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public List<VendasCotacaoBean> readCotacoesAbertas() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao WHERE status <> 'Fechado' AND status <> 'Desativado'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vcb = new VendasCotacaoBean();

                vcb.setId(rs.getInt("id"));
                vcb.setCotacao(rs.getString("cotacao"));
                vcb.setCliente(rs.getString("cliente"));
                vcb.setStatus(rs.getString("status"));

                listvc.add(vcb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler cotações abertas.";
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

        return listvc;
    }

    public List<VendasCotacaoBean> readCotacoesFechadas() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao WHERE status = 'Fechado'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vcb = new VendasCotacaoBean();

                vcb.setId(rs.getInt("id"));
                vcb.setCotacao(rs.getString("cotacao"));
                vcb.setCliente(rs.getString("cliente"));
                vcb.setStatus(rs.getString("status"));

                listvc.add(vcb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler cotações fechadas.";
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

        return listvc;
    }

    public List<VendasCotacaoBean> readCotacoesDesativadas() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao WHERE status = 'Desativado'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vcb = new VendasCotacaoBean();

                vcb.setId(rs.getInt("id"));
                vcb.setCotacao(rs.getString("cotacao"));
                vcb.setCliente(rs.getString("cliente"));
                vcb.setStatus(rs.getString("status"));

                listvc.add(vcb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler cotações abertas.";
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

        return listvc;
    }

    public List<VendasCotacaoBean> readCotacoes() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vcb = new VendasCotacaoBean();

                vcb.setId(rs.getInt("id"));
                vcb.setCotacao(rs.getString("cotacao"));
                vcb.setCliente(rs.getString("cliente"));
                vcb.setStatus(rs.getString("status"));

                listvc.add(vcb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler cotações abertas.";
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

        return listvc;
    }

    /**
     *
     * @param cotacao
     * @return
     */
    public List<VendasCotacaoBean> readCotacao(String cotacao) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao WHERE cotacao = '" + cotacao + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vcb = new VendasCotacaoBean();

                vcb.setId(rs.getInt("id"));
                vcb.setCotacao(rs.getString("cotacao"));
                vcb.setData_abertura(rs.getString("data_abertura"));
                vcb.setCliente(rs.getString("cliente"));
                vcb.setCadastrado(rs.getBoolean("cadastrado"));
                vcb.setVendedor(rs.getString("vendedor"));
                vcb.setRepresentante(rs.getString("representante"));
                vcb.setCondicao(rs.getString("condicao"));
                vcb.setStatus(rs.getString("status"));

                listvc.add(vcb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler cotação selecionada.";
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

        return listvc;
    }

    public String readMotivo(String cotacao) {
        rsList();

        String motivo = "";

        try {
            stmt = con.prepareStatement("SELECT motivo FROM vendas_cotacao WHERE cotacao = '" + cotacao + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                motivo = rs.getString("motivo");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler cotação selecionada.";
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

    public String readLastCreated() {
        rsList();

        String last = "";

        try {
            stmt = con.prepareStatement("SELECT cotacao FROM vendas_cotacao WHERE cotacao <> '' ORDER BY id DESC LIMIT 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                last = rs.getString("cotacao");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler última cotação criada.";
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

    public String cotacaoAtual() {

        rsList();

        Calendar c = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(c.getTime());
        String idtela = "CV" + year + "-0001";

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_cotacao WHERE cotacao = '" + idtela + "'");
            rs = stmt.executeQuery();

            //checking if ResultSet is empty
            if (rs.next()) {
                String last = readLastCreated();
                int yearint = Integer.parseInt(last.replace("CV" + year + "-", ""));
                int yearnovo = yearint + 1;
                idtela = "CV" + year + "-" + String.format("%04d", yearnovo);
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

    public int idCotacao(String cotacao) {
        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT id FROM vendas_cotacao WHERE cotacao = '" + cotacao + "'");

            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro ao pegar ID da Cotação.";
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

    /**
     *
     * @param cotacao
     * @param cliente
     * @param cadastrado
     * @param vendedor
     * @param representante
     * @param condicaoPagamento
     * @throws java.sql.SQLException
     */
    public void update(String cotacao, String cliente, boolean cadastrado, String vendedor, String representante, String condicaoPagamento) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("UPDATE vendas_cotacao SET cliente = '" + cliente + "', cadastrado = " + cadastrado + ", vendedor = '" + vendedor + "', representante = '" + representante + "', condicao = '" + condicaoPagamento + "' WHERE cotacao = '" + cotacao + "'");
        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public void updateStatus(String cotacao, String status) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_cotacao SET status = '" + status + "' WHERE cotacao = '" + cotacao + "'");
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status da Cotação de Venda.";
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

    public void desativarCotacao(String cotacao, String motivo) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_cotacao SET status = 'Desativado', motivo = '" + motivo + "' WHERE cotacao = '" + cotacao + "'");
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status da Cotação de Venda.";
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
