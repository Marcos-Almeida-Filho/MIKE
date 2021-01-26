/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ClientesContatosBean;
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
public class ClientesContatosDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ClientesContatosBean> list;

    ClientesContatosBean bean;

    String table = "clientes_contatos";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        list = new ArrayList<>();
    }

    public void create(int idCliente, String nome, String cargo, String email, String celular, boolean boleto, boolean orcamento) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (idCliente, nome, cargo, email, celular, boleto, orcamento) VALUES (" + idCliente + ", '" + nome + "', '" + cargo + "', '" + email + "', '" + celular + "', " + boleto + ", " + orcamento + ")");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar contato do Cliente.";
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

    public List<ClientesContatosBean> readTodos(int idCliente) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE idCliente = " + idCliente);

            rs = stmt.executeQuery();

            while (rs.next()) {
                bean = new ClientesContatosBean();

                bean.setId(rs.getInt("id"));
                bean.setNome(rs.getString("nome"));
                bean.setCargo(rs.getString("cargo"));
                bean.setEmail(rs.getString("email"));
                bean.setCelular(rs.getString("celular"));
                bean.setBoleto(rs.getBoolean("boleto"));
                bean.setOrcamento(rs.getBoolean("orcamento"));

                list.add(bean);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Contatos do Cliente.";
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

        return list;
    }
    
    public List<ClientesContatosBean> readDestinatariosBoleto(int idCliente, boolean boleto) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE idCliente = " + idCliente + " AND boleto = " + boleto);

            rs = stmt.executeQuery();

            while (rs.next()) {
                bean = new ClientesContatosBean();

                bean.setId(rs.getInt("id"));
                bean.setNome(rs.getString("nome"));
                bean.setCargo(rs.getString("cargo"));
                bean.setEmail(rs.getString("email"));
                bean.setCelular(rs.getString("celular"));
                bean.setBoleto(rs.getBoolean("boleto"));
                bean.setOrcamento(rs.getBoolean("orcamento"));

                list.add(bean);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Contatos do Cliente.";
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

        return list;
    }
    
    public List<ClientesContatosBean> readDestinatariosOrcamento(int idCliente) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE idCliente = " + idCliente + " AND orcamento = " + true);

            rs = stmt.executeQuery();

            while (rs.next()) {
                bean = new ClientesContatosBean();

                bean.setId(rs.getInt("id"));
                bean.setNome(rs.getString("nome"));
                bean.setCargo(rs.getString("cargo"));
                bean.setEmail(rs.getString("email"));
                bean.setCelular(rs.getString("celular"));
                bean.setBoleto(rs.getBoolean("boleto"));
                bean.setOrcamento(rs.getBoolean("orcamento"));

                list.add(bean);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Contatos do Cliente.";
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

        return list;
    }

    public List<ClientesContatosBean> readContato(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE id = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                bean = new ClientesContatosBean();

                bean.setId(rs.getInt("id"));
                bean.setNome(rs.getString("nome"));
                bean.setCargo(rs.getString("cargo"));
                bean.setEmail(rs.getString("email"));
                bean.setCelular(rs.getString("celular"));
                bean.setBoleto(rs.getBoolean("boleto"));
                bean.setOrcamento(rs.getBoolean("orcamento"));

                list.add(bean);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Contatos do Cliente.";
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

        return list;
    }

    public void update(String nome, String cargo, String email, String celular, boolean boleto, boolean orcamento, int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET nome = '" + nome + "', cargo = '" + cargo + "', email = '" + email + "', celular = '" + celular + "', boleto = " + boleto + ", orcamento = " + orcamento + " WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar contato do Cliente.";
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
            stmt = con.prepareStatement("DELETE FROM " + table + " WHERE id = " + id);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir contato do Cliente.";
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
