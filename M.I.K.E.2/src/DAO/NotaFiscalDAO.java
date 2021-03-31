/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.NotaFiscalBean;
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
public class NotaFiscalDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<NotaFiscalBean> listnf;

    NotaFiscalBean nfb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listnf = new ArrayList<>();
    }

    /**
     *
     * @param numero
     * @param dataEmissao
     * @param destinatario
     * @param logradouroD
     * @param numeroD
     * @param complementoD
     * @param bairroD
     * @param cidadeD
     * @param ufD
     * @param cepD
     * @param cnpjD
     * @param ieD
     * @param natureza
     * @param transportadora
     * @param enderecoT
     * @param cidadeT
     * @param ufT
     * @param cnpjT
     * @param ieT
     * @param baseIcms
     * @param valorIcms
     * @param baseIcmsSt
     * @param valorIcmsSt
     * @param valorPis
     * @param valorCofins
     * @param valorIpi
     * @param valorProdutos
     * @param obs
     * @param valorFrete
     * @param valorTotalNotaFiscal
     * @param status
     */
    public void create(int numero, String dataEmissao, String destinatario, String logradouroD, String numeroD, String complementoD, String bairroD, String cidadeD, String ufD, String cepD, String cnpjD, String ieD, String natureza, String transportadora, String enderecoT, String cidadeT, String ufT, String cnpjT, String ieT, double baseIcms, double valorIcms, double baseIcmsSt, double valorIcmsSt, double valorPis, double valorCofins, double valorIpi, double valorProdutos, double valorFrete, double valorTotalNotaFiscal, String obs, String status) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO nf (numero, dataEmissao, destinatario, logradouroD, numeroD, complementoD, bairroD, cidadeD, ufD, cepD, cnpjD, ieD, natureza, transportadora, enderecoT, cidadeT, ufT, cnpjT, ieT, baseIcms, valorIcms, baseIcmsSt, valorIcmsSt, valorPis, valorCofins, valorIpi, valorProdutos, valorFrete, valorTotalNotaFiscal, obs, status) VALUES (" + numero + ", '" + dataEmissao + "', '" + destinatario + "', '" + logradouroD + "', '" + numeroD + "', '" + complementoD + "', '" + bairroD + "', '" + cidadeD + "', '" + ufD + "', '" + cepD + "', '" + cnpjD + "', '" + ieD + "', '" + natureza + "', '" + transportadora + "', '" + enderecoT + "', '" + cidadeT + "', '" + ufT + "', '" + cnpjT + "', '" + ieT + "', " + baseIcms + ", " + valorIcms + ", " + baseIcmsSt + ", " + valorIcmsSt + ", " + valorPis + ", " + valorCofins + ", " + valorIpi + ", " + valorProdutos + ", " + valorFrete + ", " + valorTotalNotaFiscal + ", '" + obs + "', '" + status + "')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar Nota Fiscal.";
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

    public double getValorFaturado(String dataInicio, String dataFim) {
        rsList();

        double valor = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM nf WHERE venda = " + true + " AND dataEmissao BETWEEN '" + dataInicio + "' AND '" + dataFim + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                valor += rs.getDouble("valorTotalNotaFiscal");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Notas Fiscais.";
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

    public List<NotaFiscalBean> readNotas() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM nf ORDER BY numero DESC");

            rs = stmt.executeQuery();

            while (rs.next()) {
                nfb = new NotaFiscalBean();

                nfb.setId(rs.getInt("id"));
                nfb.setNumero(rs.getInt("numero"));
                nfb.setDestinatario(rs.getString("destinatario"));
                nfb.setNatureza(rs.getString("natureza"));
                nfb.setDataEmissao(rs.getString("dataEmissao"));
                nfb.setStatus(rs.getString("status"));

                listnf.add(nfb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Notas Fiscais.";
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

        return listnf;
    }

    public List<NotaFiscalBean> readNotasNaoFaturadas() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM nf WHERE statusInterno = 'Ativo' AND venda = true ORDER BY numero DESC");

            rs = stmt.executeQuery();

            while (rs.next()) {
                nfb = new NotaFiscalBean();

                nfb.setId(rs.getInt("id"));
                nfb.setNumero(rs.getInt("numero"));
                nfb.setDestinatario(rs.getString("destinatario"));
                nfb.setNatureza(rs.getString("natureza"));
                nfb.setDataEmissao(rs.getString("dataEmissao"));
                nfb.setStatus(rs.getString("status"));

                listnf.add(nfb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Notas Fiscais.";
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

        return listnf;
    }

    public List<NotaFiscalBean> readNotasPesquisa(String pesquisa) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM nf WHERE numero LIKE '%" + pesquisa + "%' OR destinatario LIKE '%" + pesquisa + "%' ORDER BY numero DESC");

            rs = stmt.executeQuery();

            while (rs.next()) {
                nfb = new NotaFiscalBean();

                nfb.setId(rs.getInt("id"));
                nfb.setNumero(rs.getInt("numero"));
                nfb.setDestinatario(rs.getString("destinatario"));
                nfb.setNatureza(rs.getString("natureza"));
                nfb.setDataEmissao(rs.getString("dataEmissao"));
                nfb.setStatus(rs.getString("status"));

                listnf.add(nfb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Notas Fiscais.";
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

        return listnf;
    }

    public List<NotaFiscalBean> readNotasAtivas() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM nf WHERE statusInterno = 'Ativo' ORDER BY numero DESC");

            rs = stmt.executeQuery();

            while (rs.next()) {
                nfb = new NotaFiscalBean();

                nfb.setId(rs.getInt("id"));
                nfb.setNumero(rs.getInt("numero"));
                nfb.setDestinatario(rs.getString("destinatario"));
                nfb.setNatureza(rs.getString("natureza"));
                nfb.setStatus(rs.getString("status"));

                listnf.add(nfb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Notas Fiscais.";
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

        return listnf;
    }

    public List<NotaFiscalBean> readNotasPesquisaBaixa(String pesquisa) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM nf WHERE statusInterno = 'Ativo' AND (numero LIKE '%" + pesquisa + "%' OR destinatario LIKE '%" + pesquisa + "%') AND venda = true ORDER BY numero DESC");

            rs = stmt.executeQuery();

            while (rs.next()) {
                nfb = new NotaFiscalBean();

                nfb.setId(rs.getInt("id"));
                nfb.setNumero(rs.getInt("numero"));
                nfb.setDestinatario(rs.getString("destinatario"));
                nfb.setNatureza(rs.getString("natureza"));
                nfb.setStatus(rs.getString("status"));

                listnf.add(nfb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Notas Fiscais.";
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

        return listnf;
    }

    public List<NotaFiscalBean> readNota(int numero) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM nf WHERE numero = " + numero);

            rs = stmt.executeQuery();

            while (rs.next()) {
                nfb = new NotaFiscalBean();

                nfb.setDestinatario(rs.getString("destinatario"));
                nfb.setLogradouroD(rs.getString("logradouroD"));
                nfb.setNumeroD(rs.getString("numeroD"));
                nfb.setDataEmissao(rs.getString("dataEmissao"));
                nfb.setComplementoD(rs.getString("complementoD"));
                nfb.setBairroD(rs.getString("bairroD"));
                nfb.setCidadeD(rs.getString("cidadeD"));
                nfb.setUfD(rs.getString("ufD"));
                nfb.setCepD(rs.getString("cepD"));
                nfb.setCnpjD(rs.getString("cnpjD"));
                nfb.setIeD(rs.getString("ieD"));
                nfb.setNatureza(rs.getString("natureza"));
                nfb.setTransportadora(rs.getString("transportadora"));
                nfb.setEnderecoT(rs.getString("enderecoT"));
                nfb.setCidadeT(rs.getString("cidadeT"));
                nfb.setUfT(rs.getString("ufT"));
                nfb.setCnpjT(rs.getString("cnpjT"));
                nfb.setIeT(rs.getString("ieT"));
                nfb.setBaseIcms(rs.getDouble("baseIcms"));
                nfb.setValorIcms(rs.getDouble("valorIcms"));
                nfb.setBaseIcmsSt(rs.getDouble("baseIcmsSt"));
                nfb.setValorIcmsSt(rs.getDouble("valorIcmsSt"));
                nfb.setValorPis(rs.getDouble("valorPis"));
                nfb.setValorCofins(rs.getDouble("valorCofins"));
                nfb.setValorIpi(rs.getDouble("valorIpi"));
                nfb.setValorProdutos(rs.getDouble("valorProdutos"));
                nfb.setValorTotalNotaFiscal(rs.getDouble("valorTotalNotaFiscal"));
                nfb.setObs(rs.getString("obs"));
                nfb.setStatus(rs.getString("status"));
                nfb.setVenda(rs.getBoolean("venda"));

                listnf.add(nfb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler a Nota Fiscal " + numero;
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

        return listnf;
    }

    public boolean notaExiste(int numero) {
        rsList();

        boolean existe = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM nf WHERE numero = " + numero);

            rs = stmt.executeQuery();

            if (rs.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler a Nota Fiscal " + numero;
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

        return existe;
    }

    public void cancelarNota(int numero) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE nf SET status = 'Cancelada' WHERE numero = " + numero);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao cancelar Nota Fiscal " + numero;
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

    public void estornarStatus(int numero) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE nf SET statusInterno = 'Ativo' WHERE numero = " + numero);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status da NF-e.";
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

    public void updateStatus(int numero) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE nf SET statusInterno = 'Faturado' WHERE numero = " + numero);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status da NF-e.";
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

    public void updateVendas(int numero, boolean a) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("UPDATE nf SET venda = " + a + " WHERE numero = " + numero);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }
}
