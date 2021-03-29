/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.VendasMateriaisBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 *
 * @author Marcos Filho
 */
public class VendasMateriaisDAO {

    Connection con;
    PreparedStatement stmt;
    ResultSet rs;
    List<VendasMateriaisBean> listvmb;
    VendasMateriaisBean vmb;

    /**
     * Método para criar uma nova conexão e deixar o PreparedStatement nulo de
     * novo.
     */
    public void conStmt() {
        con = ConnectionFactory.getConnection();
        stmt = null;
    }

    /**
     * Método para deixar o ResultSet nulo novamente e zerar a list. Chama o
     * método conStmt() que cria uma nova conexão e deixa o PreparedStatement
     * nulo novamente.
     */
    public void rsList() {
        conStmt();
        rs = null;
        listvmb = new ArrayList<>();
    }

    public void create(String codigo, String descricao, double estoque, double estoqueMinimo, String status, String local, String d1, String d2, String d3, String d4, String d5, String l1, String l2, String l3, String l4, String l5, String materialOrigem, String rev, String raio, boolean importada, boolean weldon, boolean ri, boolean md, boolean hss, String tipo, String familia, String tamanho, String cortes, String topo, String canal, String extra, String helice, String nucleo, String concavidade, String topo1, String topo2, String alivio1, String alivio2, String filete, String agressividade, double qtdMinimaOP, boolean desbaste, String tolD1, String tolD2, String mp, boolean aparecerExtra, boolean cs11, boolean detalonado, String mpDados, String conicidade, String tipoFilete, String alturaCostela, String tipoRaio, String anguloFrontal, String tipoFrontal) throws SQLException {
        conStmt();

        stmt = con.prepareStatement("INSERT INTO vendas_materiais (codigo, descricao, estoque, estoqueMinimo, status, local, d1, d2, d3, d4, d5, l1, l2, l3, l4, l5, materialOrigem, rev, raio, importada, weldon, ri, md, hss, tipo, familia, tamanho, cortes, topo, canal, extra, helice, nucleo, concavidade, topo1, topo2, alivio1, alivio2, filete, agressividade, qtdMinimaOP, desbaste, tolD1, tolD2, mp, aparecerExtra, cs11, detalonado, mpDados, conicidade, tipoFilete, alturaCostela, tipoRaio, anguloFrontal, tipoFrontal) VALUES ('" + codigo + "','" + descricao + "'," + estoque + "," + estoqueMinimo + ",'" + status + "','" + local + "','" + d1 + "','" + d2 + "','" + d3 + "','" + d4 + "','" + d5 + "','" + l1 + "','" + l2 + "','" + l3 + "','" + l4 + "','" + l5 + "','" + materialOrigem + "','" + rev + "','" + raio + "'," + importada + "," + weldon + "," + ri + "," + md + "," + hss + ",'" + tipo + "','" + familia + "','" + tamanho + "','" + cortes + "','" + topo + "','" + canal + "','" + extra + "','" + helice + "','" + nucleo + "','" + concavidade + "','" + topo1 + "','" + topo2 + "','" + alivio1 + "','" + alivio2 + "','" + filete + "','" + agressividade + "', " + qtdMinimaOP + ", " + desbaste + ", '" + tolD1 + "', '" + tolD2 + "', '" + mp + "', " + aparecerExtra + ", " + cs11 + ", " + detalonado + ", '" + mpDados + "', '" + conicidade + "', '" + tipoFilete + "', '" + alturaCostela + "', '" + tipoRaio + "', '" + anguloFrontal + "', '" + tipoFrontal + "')");

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    /**
     * Método para recuperar todos os materiais cadastrados no banco de dados.
     *
     * @return List<VendasMateriasBean> com os materiais cadastrados.
     */
    public List<VendasMateriaisBean> readtodos() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));
                vmb.setLocal(rs.getString("local"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Materiais cadastrados.";
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
        return listvmb;
    }

    public List<VendasMateriaisBean> readTodosPesquisa(String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE (codigo LIKE '%" + pesquisa + "%' OR descricao LIKE '%" + pesquisa + "%') AND status = 'Ativo'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));
                vmb.setLocal(rs.getString("local"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Materiais cadastrados.";
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
        return listvmb;
    }

    public List<VendasMateriaisBean> readStatus(String status) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE status = '" + status + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));
                vmb.setLocal(rs.getString("local"));
                vmb.setQtdMinimaOP(rs.getDouble("qtdMinimaOp"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Materiais cadastrados.";
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
        return listvmb;
    }

    public List<VendasMateriaisBean> readStatusPesquisa(String status, String pesquisa) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE status = '" + status + "' AND codigo LIKE '%" + pesquisa + "%' OR status = '" + status + "' AND descricao LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                vmb = new VendasMateriaisBean();

                vmb.setId(rs.getInt("id"));
                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setStatus(rs.getString("status"));
                vmb.setLocal(rs.getString("local"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Materiais cadastrados.";
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
        return listvmb;
    }

    /**
     * Método para retornar o último ID criado no banco de dados.
     *
     * @return Integer do ID.
     */
    public int readcreated() {

        rsList();

        int lastid = 0;

        try {
            stmt = con.prepareStatement("SELECT MAX(id) AS id FROM vendas_materiais");
            rs = stmt.executeQuery();

            while (rs.next()) {
                lastid = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Material cadastrado.";
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
        return lastid;
    }

    public double readEstoque(int idMaterial) {

        rsList();

        double estoque = 0;

        try {
            stmt = con.prepareStatement("SELECT estoque FROM vendas_materiais WHERE id = " + idMaterial);
            rs = stmt.executeQuery();

            while (rs.next()) {
                estoque = rs.getDouble("estoque");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler estoque do Material.";
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
        return estoque;
    }
    
    public String codigoProduto(int id) {
        String codigo = "";

        rsList();

        try {
            stmt = con.prepareStatement("SELECT codigo FROM vendas_materiais WHERE id = " + id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                codigo = rs.getString("codigo");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler ID do produto.";
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

        return codigo;
    }

    public int idProduto(String codigo) {
        int produto = 0;

        rsList();

        try {
            stmt = con.prepareStatement("SELECT id FROM vendas_materiais WHERE codigo = '" + codigo + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                produto = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler ID do produto.";
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

        return produto;
    }

    public double qtdMinimaOP(String codigo) {
        double qtd = 0;

        rsList();

        try {
            stmt = con.prepareStatement("SELECT qtdMinimaOP FROM vendas_materiais WHERE codigo = '" + codigo + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                qtd = rs.getInt("qtdMinimaOP");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler ID do produto.";
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

        return qtd;
    }

    public boolean produtoCadastrado(String codigo) {
        rsList();

        boolean existe = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE codigo = '" + codigo + "'");

            rs = stmt.executeQuery();

            existe = rs.next();
        } catch (SQLException e) {
            String msg = "Erro ao verificar se produto já existe.";
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

    public List<VendasMateriaisBean> click(int id) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM vendas_materiais WHERE id = " + id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                vmb = new VendasMateriaisBean();

                vmb.setCodigo(rs.getString("codigo"));
                vmb.setDescricao(rs.getString("descricao"));
                vmb.setEstoque(rs.getDouble("estoque"));
                vmb.setEstoqueMinimo(rs.getDouble("estoqueMinimo"));
                vmb.setQtdMinimaOP(rs.getDouble("qtdMinimaOP"));
                vmb.setStatus(rs.getString("status"));
                vmb.setLocal(rs.getString("local"));
                vmb.setD1(rs.getString("d1"));
                vmb.setD2(rs.getString("d2"));
                vmb.setD3(rs.getString("d3"));
                vmb.setD4(rs.getString("d4"));
                vmb.setD5(rs.getString("d5"));
                vmb.setL1(rs.getString("l1"));
                vmb.setL2(rs.getString("l2"));
                vmb.setL3(rs.getString("l3"));
                vmb.setL4(rs.getString("l4"));
                vmb.setL5(rs.getString("l5"));
                vmb.setMaterialOrigem(rs.getString("materialOrigem"));
                vmb.setRev(rs.getString("rev"));
                vmb.setRaio(rs.getString("raio"));
                vmb.setImportada(rs.getBoolean("importada"));
                vmb.setWeldon(rs.getBoolean("weldon"));
                vmb.setRi(rs.getBoolean("ri"));
                vmb.setMd(rs.getBoolean("md"));
                vmb.setHss(rs.getBoolean("hss"));
                vmb.setDesbaste(rs.getBoolean("desbaste"));
                vmb.setTipo(rs.getString("tipo"));
                vmb.setFamilia(rs.getString("familia"));
                vmb.setTamanho(rs.getString("tamanho"));
                vmb.setCortes(rs.getString("cortes"));
                vmb.setTopo(rs.getString("topo"));
                vmb.setCanal(rs.getString("canal"));
                vmb.setExtra(rs.getString("extra"));
                vmb.setTolD1(rs.getString("tolD1"));
                vmb.setTolD2(rs.getString("tolD2"));
                vmb.setHelice(rs.getString("helice"));
                vmb.setNucleo(rs.getString("nucleo"));
                vmb.setConcavidade(rs.getString("concavidade"));
                vmb.setTopo1(rs.getString("topo1"));
                vmb.setTopo2(rs.getString("topo2"));
                vmb.setAlivio1(rs.getString("alivio1"));
                vmb.setAlivio2(rs.getString("alivio2"));
                vmb.setFilete(rs.getString("filete"));
                vmb.setAgressividade(rs.getString("agressividade"));
                vmb.setMp(rs.getString("mp"));
                vmb.setAparecerExtra(rs.getBoolean("aparecerExtra"));
                vmb.setCs11(rs.getBoolean("cs11"));
                vmb.setDetalonado(rs.getBoolean("detalonado"));
                vmb.setMpDados(rs.getString("mpDados"));
                vmb.setConicidade(rs.getString("conicidade"));
                vmb.setTipoFilete(rs.getString("tipoFilete"));
                vmb.setAlturaCostela(rs.getString("alturaCostela"));
                vmb.setTipoRaio(rs.getString("tipoRaio"));
                vmb.setAnguloFrontal(rs.getString("anguloFrontal"));
                vmb.setTipoFrontal(rs.getString("tipoFrontal"));

                listvmb.add(vmb);
            }
        } catch (SQLException e) {
            Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listvmb;
    }

    public void update(String codigo, String descricao, double estoqueMinimo, String local, String d1, String d2, String d3, String d4, String d5, String l1, String l2, String l3, String l4, String l5, String materialOrigem, String rev, String raio, boolean importada, boolean weldon, boolean ri, boolean md, boolean hss, String tipo, String familia, String tamanho, String cortes, String topo, String canal, String extra, String helice, String nucleo, String concavidade, String topo1, String topo2, String alivio1, String alivio2, String filete, String agressividade, double qtdMinimaOP, boolean desbaste, String tolD1, String tolD2, String mp, boolean aparecerExtra, boolean cs11, boolean detalonado, String mpDados, String conicidade, String tipoFilete, String alturaCostela, String tipoRaio, String anguloFrontal, String tipoFrontal, int id) throws SQLException {

        conStmt();

        stmt = con.prepareStatement("UPDATE vendas_materiais SET codigo = '" + codigo + "', descricao = '" + descricao + "', local = '" + local + "', estoqueMinimo = " + estoqueMinimo + ", d1 = '" + d1 + "', d2 = '" + d2 + "', d3 = '" + d3 + "', d4 = '" + d4 + "', d5 = '" + d5 + "', l1 = '" + l1 + "', l2 = '" + l2 + "', l3 = '" + l3 + "', l4 = '" + l4 + "', l5 = '" + l5 + "', materialOrigem = '" + materialOrigem + "', rev = '" + rev + "', raio = '" + raio + "', importada = " + importada + ", weldon = " + weldon + ", ri = " + ri + ", md = " + md + ", hss = " + hss + ", tipo = '" + tipo + "', familia = '" + familia + "', tamanho = '" + tamanho + "', cortes = '" + cortes + "', topo = '" + topo + "', canal = '" + canal + "', extra = '" + extra + "', helice = '" + helice + "', nucleo = '" + nucleo + "', concavidade = '" + concavidade + "', topo1 = '" + topo1 + "', topo2 = '" + topo2 + "', alivio1 = '" + alivio1 + "', alivio2 = '" + alivio2 + "', filete = '" + filete + "', agressividade = '" + agressividade + "', qtdMinimaOP = " + qtdMinimaOP + ", desbaste = " + desbaste + ", tolD1 = '" + tolD1 + "', tolD2 = '" + tolD2 + "', mp = '" + mp + "', aparecerExtra = " + aparecerExtra + ", cs11 = " + cs11 + ", detalonado = " + detalonado + ", mpDados = '" + mpDados + "', conicidade = '" + conicidade + "', tipoFilete = '" + tipoFilete + "', alturaCostela = '" + alturaCostela + "', tipoRaio = '" + tipoRaio + "', anguloFrontal = '" + anguloFrontal + "', tipoFrontal = '" + tipoFrontal + "' WHERE id = " + id);

        stmt.executeUpdate();

        ConnectionFactory.closeConnection(con, stmt);
    }

    public void updateStatus(VendasMateriaisBean vmb) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_materiais SET status = ? WHERE id = ?");

            stmt.setString(1, vmb.getStatus());
            stmt.setInt(2, vmb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar status!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(VendasMateriaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateEstoque(double estoque, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE vendas_materiais SET estoque = " + estoque + " WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar estoque!";

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

    public void delete(VendasMateriaisBean cb) {

        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM vendas_materiais WHERE id = ?");
            stmt.setInt(1, cb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir Material de Venda.";
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
