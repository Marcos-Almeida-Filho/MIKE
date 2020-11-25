/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.LocalBean;
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
public class LocalDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<LocalBean> listlb;

    public int vezesDao = 0;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        rs = null;

        listlb = new ArrayList<>();
    }

    public void create(String nome, int vezesMethod) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO local (nome, status) VALUES ('" + nome + "','Ativo')");

            stmt.executeUpdate();

            vezesDao++;
            if (vezesDao == vezesMethod) {
                JOptionPane.showMessageDialog(null, "Criado com sucesso!");
                vezesDao = 0;
            }
        } catch (SQLException e) {
            String msg = "Erro ao criar Local de Armazenagem.";
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

    public boolean readLocal(String nome) {
        conStmt();

        rsList();

        boolean name = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM local WHERE nome = '" + nome + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                name = true;
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Locais de Armazenagem.";
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

        return name;
    }

    public List<LocalBean> read() {
        conStmt();

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM local");

            rs = stmt.executeQuery();

            while (rs.next()) {
                LocalBean lb = new LocalBean();

                lb.setId(rs.getInt("id"));
                lb.setNome(rs.getString("nome"));
                lb.setStatus(rs.getString("status"));

                listlb.add(lb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Locais de Armazenagem.";
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

        return listlb;
    }

    public List<LocalBean> readAtivo() {
        conStmt();

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM local WHERE status = 'Ativo'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                LocalBean lb = new LocalBean();

                lb.setId(rs.getInt("id"));
                lb.setNome(rs.getString("nome"));
                lb.setStatus(rs.getString("status"));

                listlb.add(lb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Locais de Armazenagem.";
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

        return listlb;
    }

    public List<LocalBean> readStatus(String status) {
        conStmt();

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM local WHERE status = '" + status + "'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                LocalBean lb = new LocalBean();

                lb.setId(rs.getInt("id"));
                lb.setNome(rs.getString("nome"));
                lb.setStatus(rs.getString("status"));

                listlb.add(lb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Locais de Armazenagem.";
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

        return listlb;
    }

    public List<LocalBean> readPesquisa(String pesquisa) {
        conStmt();

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM local WHERE nome LIKE '%" + pesquisa + "%'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                LocalBean lb = new LocalBean();

                lb.setId(rs.getInt("id"));
                lb.setNome(rs.getString("nome"));
                lb.setStatus(rs.getString("status"));

                listlb.add(lb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Locais de Armazenagem.";
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

        return listlb;
    }

    public List<LocalBean> readPesquisaAtivo(String pesquisa) {
        conStmt();

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM local WHERE status = 'Ativo' AND nome LIKE '%" + pesquisa + "%'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                LocalBean lb = new LocalBean();

                lb.setId(rs.getInt("id"));
                lb.setNome(rs.getString("nome"));
                lb.setStatus(rs.getString("status"));

                listlb.add(lb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Locais de Armazenagem.";
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

        return listlb;
    }

    public List<LocalBean> readStatusPesquisa(String status, String pesquisa) {
        conStmt();

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM local WHERE status = '" + status + "' AND nome LIKE '%" + pesquisa + "%'");

            rs = stmt.executeQuery();

            while (rs.next()) {
                LocalBean lb = new LocalBean();

                lb.setId(rs.getInt("id"));
                lb.setNome(rs.getString("nome"));
                lb.setStatus(rs.getString("status"));

                listlb.add(lb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Locais de Armazenagem.";
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

        return listlb;
    }

    public void updateNome(LocalBean lb) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE local SET nome = ? WHERE id = ?");

            stmt.setString(1, lb.getNome());
            stmt.setInt(2, lb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar nome do Local de Armazenagem.";
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
            stmt = con.prepareStatement("UPDATE local SET status = 'Desativado' WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status do Local de Armazenagem.";
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
