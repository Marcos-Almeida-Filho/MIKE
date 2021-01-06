/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.InsumosBean;
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
public class InsumosDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<InsumosBean> listi;

    InsumosBean ib;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listi = new ArrayList<>();
    }

    public void create(String codigo, String descricao, String unidade, String tipo, double estoque, String dataCriacao) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO insumos (codigo, descricao, unidade, tipo, estoque, datacriacao, status) VALUES ('" + codigo + "', '" + descricao + "', '" + unidade + "', '" + tipo + "', " + estoque + ", '" + dataCriacao + "', 'Ativo')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar Insumo.";
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

    public List<InsumosBean> read() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos WHERE status = 'Ativo'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ib = new InsumosBean();

                ib.setId(rs.getInt("id"));
                ib.setCodigo(rs.getString("codigo"));
                ib.setDescricao(rs.getString("descricao"));
                ib.setUnidade(rs.getString("unidade"));
                ib.setTipo(rs.getString("tipo"));
                ib.setEstoque(rs.getDouble("estoque"));
                ib.setDatacriacao(rs.getString("datacriacao"));
                ib.setStatus(rs.getString("status"));

                listi.add(ib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Insumos.";
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
        return listi;
    }

    public List<InsumosBean> readPesquisa(String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos WHERE status = 'Ativo' AND codigo LIKE '%" + pesquisa + "%' OR status = 'Ativo' AND descricao LIKE '%" + pesquisa + "%'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ib = new InsumosBean();

                ib.setId(rs.getInt("id"));
                ib.setCodigo(rs.getString("codigo"));
                ib.setDescricao(rs.getString("descricao"));
                ib.setUnidade(rs.getString("unidade"));
                ib.setTipo(rs.getString("tipo"));
                ib.setEstoque(rs.getDouble("estoque"));
                ib.setDatacriacao(rs.getString("datacriacao"));
                ib.setStatus(rs.getString("status"));

                listi.add(ib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Insumos.";
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
        return listi;
    }

    public List<InsumosBean> readPorTipo(String tipo) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos WHERE tipo = '" + tipo + "' AND status = 'Ativo'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ib = new InsumosBean();

                ib.setId(rs.getInt("id"));
                ib.setCodigo(rs.getString("codigo"));
                ib.setDescricao(rs.getString("descricao"));
                ib.setUnidade(rs.getString("unidade"));
                ib.setTipo(rs.getString("tipo"));
                ib.setEstoque(rs.getDouble("estoque"));
                ib.setDatacriacao(rs.getString("datacriacao"));
                ib.setStatus(rs.getString("status"));

                listi.add(ib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Insumos por tipo.";
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
        return listi;
    }

    public List<InsumosBean> readPorStatus(String status) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos WHERE status = '" + status + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ib = new InsumosBean();

                ib.setId(rs.getInt("id"));
                ib.setCodigo(rs.getString("codigo"));
                ib.setDescricao(rs.getString("descricao"));
                ib.setUnidade(rs.getString("unidade"));
                ib.setTipo(rs.getString("tipo"));
                ib.setEstoque(rs.getDouble("estoque"));
                ib.setDatacriacao(rs.getString("datacriacao"));
                ib.setStatus(rs.getString("status"));

                listi.add(ib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Insumos por tipo.";
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
        return listi;
    }

    public List<InsumosBean> readPorStatusEPesquisa(String status, String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos WHERE status = '" + status + "' AND codigo LIKE '%" + pesquisa + "%' OR status = '" + status + "' AND descricao LIKE '%" + pesquisa + "%'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                ib = new InsumosBean();

                ib.setId(rs.getInt("id"));
                ib.setCodigo(rs.getString("codigo"));
                ib.setDescricao(rs.getString("descricao"));
                ib.setUnidade(rs.getString("unidade"));
                ib.setTipo(rs.getString("tipo"));
                ib.setEstoque(rs.getDouble("estoque"));
                ib.setDatacriacao(rs.getString("datacriacao"));
                ib.setStatus(rs.getString("status"));

                listi.add(ib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Insumos por tipo.";
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
        return listi;
    }

    public int idCreated(String codigo) {

        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT id FROM insumos WHERE codigo = '" + codigo + "'");

            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Insumos por Data de Criação.";
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

    public double readEstoque(int id) {
        rsList();

        double estoque = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos WHERE id = " + id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                estoque = rs.getDouble("estoque");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler estoque do Insumo.";
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

    public List<InsumosBean> click(int id) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM insumos WHERE id = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                ib = new InsumosBean();

                ib.setId(rs.getInt("id"));
                ib.setCodigo(rs.getString("codigo"));
                ib.setDescricao(rs.getString("descricao"));
                ib.setUnidade(rs.getString("unidade"));
                ib.setTipo(rs.getString("tipo"));
                ib.setEstoque(rs.getDouble("estoque"));
                ib.setDatacriacao(rs.getString("datacriacao"));
                ib.setStatus(rs.getString("status"));

                listi.add(ib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Insumo.";
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
        return listi;
    }

    public void update(String codigo, String descricao, String tipo, int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE insumos SET codigo = '" + codigo + "', descricao = '" + descricao + "', tipo = '" + tipo + "' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Insumo.";
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

    public void updateEstoque(double estoque, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE insumos SET estoque = " + estoque + " WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar estoque.";
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

    public void desativar(int id) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE insumos SET status = 'Desativado' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Insumo.";
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
