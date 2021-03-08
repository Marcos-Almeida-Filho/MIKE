/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ProcessosVendasBean;
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
public class ProcessosVendasDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ProcessosVendasBean> listpvb;

    ProcessosVendasBean pvb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listpvb = new ArrayList<>();
    }

    public void create(String nome, int ordem) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO processos_vendas (nome, ordem, status) VALUES ('" + nome + "', " + ordem + ", 'Ativo')");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar o Processo de Venda.";
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

    public List<ProcessosVendasBean> readTodos() {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM processos_vendas WHERE status <> 'Desativado' ORDER BY ordem");

            rs = stmt.executeQuery();

            while (rs.next()) {
                pvb = new ProcessosVendasBean();

                pvb.setId(rs.getInt("id"));
                pvb.setNome(rs.getString("nome"));
                pvb.setOrdem(rs.getInt("ordem"));
                pvb.setStatus(rs.getString("status"));

                listpvb.add(pvb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Processos de Vendas.";
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

        return listpvb;
    }

    public List<ProcessosVendasBean> readProcesso(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM processos_vendas WHERE id = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                pvb = new ProcessosVendasBean();

                pvb.setNome(rs.getString("nome"));
                pvb.setOrdem(rs.getInt("ordem"));
                pvb.setStatus(rs.getString("status"));

                listpvb.add(pvb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Processo de Venda.";
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

        return listpvb;
    }

    public int idProcesso(String nome) {
        int id = 0;

        rsList();

        try {
            stmt = con.prepareStatement("SELECT id FROM processos_vendas WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Processo de Venda.";
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

        return id;
    }

    public void updateProcesso(int id, String nome, int ordem) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE processos_vendas SET nome = '" + nome + "', ordem = " + ordem + " WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Processo de Venda.";
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
    
    public void updateOrdem(int id, int ordem) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE processos_vendas SET ordem = " + ordem + " WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar Processo de Venda.";
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

    public void desativarProcesso(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE processos_vendas SET status = 'Desativado' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status do Processo de Venda.";
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
