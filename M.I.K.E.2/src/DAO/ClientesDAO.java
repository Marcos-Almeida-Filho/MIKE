/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ClientesBean;
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
public class ClientesDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<ClientesBean> list;

    ClientesBean cb;

    String table = "clientes";

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        list = new ArrayList<>();
    }

    public void create(ClientesBean cb) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO " + table + " (nome, razaosocial, cnpj, ie, telefone, grupo, vendedor, idrepresentante, representante,condicaodepagamento, logradouro, numero, complemento, bairro, cidade, uf, cep, im, cpf, rg, boleto, pj) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, cb.getNome());
            stmt.setString(2, cb.getRazaosocial());
            stmt.setString(3, cb.getCnpj());
            stmt.setString(4, cb.getIe());
            stmt.setString(5, cb.getTelefone());
            stmt.setString(6, cb.getGrupo());
            stmt.setString(7, cb.getVendedor());
            stmt.setInt(8, cb.getIdrepresentante());
            stmt.setString(9, cb.getRepresentante());
            stmt.setString(10, cb.getCondicaodepagamento());
            stmt.setString(11, cb.getLogradouro());
            stmt.setString(12, cb.getNumero());
            stmt.setString(13, cb.getComplemento());
            stmt.setString(14, cb.getBairro());
            stmt.setString(15, cb.getCidade());
            stmt.setString(16, cb.getUf());
            stmt.setString(17, cb.getCep());
            stmt.setString(18, cb.getIm());
            stmt.setString(19, cb.getCpf());
            stmt.setString(20, cb.getRg());
            stmt.setBoolean(21, cb.isBoleto());
            stmt.setBoolean(22, cb.isPj());

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar Cliente.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<ClientesBean> read() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new ClientesBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));
                cb.setCnpj(rs.getString("cnpj"));
                cb.setIe(rs.getString("ie"));
                cb.setTelefone(rs.getString("telefone"));
                cb.setGrupo(rs.getString("grupo"));
                cb.setVendedor(rs.getString("vendedor"));
                cb.setIdrepresentante(rs.getInt("idrepresentante"));
                cb.setRepresentante(rs.getString("representante"));
                cb.setCondicaodepagamento(rs.getString("condicaodepagamento"));
                cb.setLogradouro(rs.getString("logradouro"));
                cb.setNumero(rs.getString("numero"));
                cb.setComplemento(rs.getString("complemento"));
                cb.setBairro(rs.getString("bairro"));
                cb.setCidade(rs.getString("cidade"));
                cb.setUf(rs.getString("uf"));
                cb.setCep(rs.getString("cep"));
                cb.setIm(rs.getString("im"));
                cb.setCpf(rs.getString("cpf"));
                cb.setRg(rs.getString("rg"));
                cb.setBoleto(rs.getBoolean("boleto"));
                cb.setPj(rs.getBoolean("pj"));

                list.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Clientes.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<ClientesBean> readPorNome(String cliente) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = '" + cliente + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new ClientesBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));
                cb.setCnpj(rs.getString("cnpj"));
                cb.setIe(rs.getString("ie"));
                cb.setTelefone(rs.getString("telefone"));
                cb.setGrupo(rs.getString("grupo"));
                cb.setVendedor(rs.getString("vendedor"));
                cb.setIdrepresentante(rs.getInt("idrepresentante"));
                cb.setRepresentante(rs.getString("representante"));
                cb.setCondicaodepagamento(rs.getString("condicaodepagamento"));
                cb.setLogradouro(rs.getString("logradouro"));
                cb.setNumero(rs.getString("numero"));
                cb.setComplemento(rs.getString("complemento"));
                cb.setBairro(rs.getString("bairro"));
                cb.setCidade(rs.getString("cidade"));
                cb.setUf(rs.getString("uf"));
                cb.setCep(rs.getString("cep"));
                cb.setIm(rs.getString("im"));
                cb.setCpf(rs.getString("cpf"));
                cb.setRg(rs.getString("rg"));
                cb.setBoleto(rs.getBoolean("boleto"));
                cb.setPj(rs.getBoolean("pj"));

                list.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler dados do Cliente.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public boolean checkcnpj(String cnpj) {

        rsList();

        boolean check = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE cnpj = ?");
            stmt.setString(1, cnpj);
            rs = stmt.executeQuery();

            while (rs.next()) {
                check = true;
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

            new Thread() {
                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg, e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }
    
    public int getIdCnpj(String cnpj) {

        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE cnpj = '" + cnpj + "'");
            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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
    
    public int getIdCpf(String cpf) {

        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE cpf = '" + cpf + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<ClientesBean> readcnpj(String cnpj) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE cnpj = ?");
            stmt.setString(1, cnpj);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new ClientesBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));
                cb.setCnpj(rs.getString("cnpj"));
                cb.setIe(rs.getString("ie"));
                cb.setTelefone(rs.getString("telefone"));
                cb.setLogradouro(rs.getString("logradouro"));
                cb.setNumero(rs.getString("numero"));
                cb.setComplemento(rs.getString("complemento"));
                cb.setBairro(rs.getString("bairro"));
                cb.setCidade(rs.getString("cidade"));
                cb.setUf(rs.getString("uf"));
                cb.setCep(rs.getString("cep"));
                cb.setIm(rs.getString("im"));
                cb.setCpf(rs.getString("cpf"));
                cb.setRg(rs.getString("rg"));
                cb.setBoleto(rs.getBoolean("boleto"));
                cb.setPj(rs.getBoolean("pj"));

                list.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<ClientesBean> readcondicoes(String nome) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome = ?");
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new ClientesBean();

                cb.setVendedor(rs.getString("vendedor"));
                cb.setRepresentante(rs.getString("representante"));
                cb.setCondicaodepagamento(rs.getString("condicaodepagamento"));

                list.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<ClientesBean> readrepresentantexcliente(Integer idrep) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE idrepresentante = ?");
            stmt.setInt(1, idrep);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new ClientesBean();

                cb.setNome(rs.getString("nome"));
                cb.setVisita(rs.getString("visita"));

                list.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<ClientesBean> click(int id) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new ClientesBean();

                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));
                cb.setCnpj(rs.getString("cnpj"));
                cb.setIe(rs.getString("ie"));
                cb.setTelefone(rs.getString("telefone"));
                cb.setGrupo(rs.getString("grupo"));
                cb.setVendedor(rs.getString("vendedor"));
                cb.setIdrepresentante(rs.getInt("idrepresentante"));
                cb.setRepresentante(rs.getString("representante"));
                cb.setCondicaodepagamento(rs.getString("condicaodepagamento"));
                cb.setLogradouro(rs.getString("logradouro"));
                cb.setNumero(rs.getString("numero"));
                cb.setComplemento(rs.getString("complemento"));
                cb.setBairro(rs.getString("bairro"));
                cb.setCidade(rs.getString("cidade"));
                cb.setUf(rs.getString("uf"));
                cb.setCep(rs.getString("cep"));
                cb.setIm(rs.getString("im"));
                cb.setCpf(rs.getString("cpf"));
                cb.setRg(rs.getString("rg"));
                cb.setBoleto(rs.getBoolean("boleto"));
                cb.setPj(rs.getBoolean("pj"));

                list.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<ClientesBean> preenchertabelaclientes() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table);
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new ClientesBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));

                list.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public void update(ClientesBean cb) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET nome = ?, razaosocial = ?, cnpj = ?, ie = ?, telefone = ?, grupo = ?, vendedor = ?, idrepresentante = ?, representante = ?,condicaodepagamento = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ?, cep = ?, im = ?, cpf = ?, rg = ?, boleto = ?, pj = ? WHERE id = ?");

            stmt.setString(1, cb.getNome());
            stmt.setString(2, cb.getRazaosocial());
            stmt.setString(3, cb.getCnpj());
            stmt.setString(4, cb.getIe());
            stmt.setString(5, cb.getTelefone());
            stmt.setString(6, cb.getGrupo());
            stmt.setString(7, cb.getVendedor());
            stmt.setInt(8, cb.getIdrepresentante());
            stmt.setString(9, cb.getRepresentante());
            stmt.setString(10, cb.getCondicaodepagamento());
            stmt.setString(11, cb.getLogradouro());
            stmt.setString(12, cb.getNumero());
            stmt.setString(13, cb.getComplemento());
            stmt.setString(14, cb.getBairro());
            stmt.setString(15, cb.getCidade());
            stmt.setString(16, cb.getUf());
            stmt.setString(17, cb.getCep());
            stmt.setString(18, cb.getIm());
            stmt.setString(19, cb.getCpf());
            stmt.setString(20, cb.getRg());
            stmt.setBoolean(21, cb.isBoleto());
            stmt.setBoolean(22, cb.isPj());
            stmt.setInt(23, cb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public void updatevisita(ClientesBean cb) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE " + table + " SET visita = ? WHERE nome = ?");

            stmt.setString(1, cb.getVisita());
            stmt.setString(2, cb.getNome());

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<ClientesBean> pesquisa(String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE id LIKE '%" + pesquisa + "%' OR nome LIKE '%" + pesquisa + "%' OR razaosocial LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new ClientesBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));

                list.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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

    public List<ClientesBean> pesquisanome(String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM " + table + " WHERE nome LIKE '%" + pesquisa + "%'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                cb = new ClientesBean();

                cb.setId(rs.getInt("id"));
                cb.setNome(rs.getString("nome"));
                cb.setRazaosocial(rs.getString("razaosocial"));

                list.add(cb);
            }
        } catch (SQLException e) {
            String msg = "Erro.";

            JOptionPane.showMessageDialog(null, msg + "\n" + e);

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
}
