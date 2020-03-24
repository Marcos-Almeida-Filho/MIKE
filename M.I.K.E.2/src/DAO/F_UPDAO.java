/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.F_UPBean;
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
 * @author Marcos Filho
 */
public class F_UPDAO {

    public void create(F_UPBean fb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO f_up (dav, op, dataentrega, material, processo, datacriacao, nivel, valor, observacao, cliente) VALUES (?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, fb.getDav());
            stmt.setString(2, fb.getOp());
            stmt.setString(3, fb.getDataentrega());
            stmt.setString(4, fb.getMaterial());
            stmt.setString(5, fb.getProcesso());
            stmt.setString(6, fb.getDatacriacao());
            stmt.setInt(7, fb.getNivel());
            stmt.setDouble(8, fb.getValor());
            stmt.setString(9, fb.getObservacao());
            stmt.setString(10, fb.getCliente());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Criado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar OP no Follow Up!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<F_UPBean> readtable() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<F_UPBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up ORDER BY nivel, dataentrega, dav");
            rs = stmt.executeQuery();

            while (rs.next()) {
                F_UPBean cb = new F_UPBean();

                cb.setId(rs.getInt("id"));
                cb.setMaterial(rs.getString("material"));
                cb.setDav(rs.getString("dav"));
                cb.setOp(rs.getString("op"));
                cb.setDataentrega(rs.getString("dataentrega"));
                cb.setProcesso(rs.getString("processo"));
                cb.setNivel(rs.getInt("nivel"));
                cb.setValor(rs.getDouble("valor"));
                cb.setObservacao(rs.getString("observacao"));
                cb.setCliente(rs.getString("cliente"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public List<F_UPBean> readtableplanejamento() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<F_UPBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo <> 'Encerrado' ORDER BY nivel, dataentrega");
            rs = stmt.executeQuery();

            while (rs.next()) {
                F_UPBean cb = new F_UPBean();

                cb.setId(rs.getInt("id"));
                cb.setMaterial(rs.getString("material"));
                cb.setDav(rs.getString("dav"));
                cb.setOp(rs.getString("op"));
                cb.setDataentrega(rs.getString("dataentrega"));
                cb.setProcesso(rs.getString("processo"));
                cb.setNivel(rs.getInt("nivel"));
                cb.setValor(rs.getDouble("valor"));
                cb.setObservacao(rs.getString("observacao"));
                cb.setCliente(rs.getString("cliente"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<F_UPBean> readtableepesquisa(String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<F_UPBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE op LIKE '%" + pesquisa + "%' OR dav LIKE '%" + pesquisa + "%' ORDER BY nivel, dataentrega, dav");
            rs = stmt.executeQuery();

            while (rs.next()) {
                F_UPBean cb = new F_UPBean();

                cb.setId(rs.getInt("id"));
                cb.setMaterial(rs.getString("material"));
                cb.setDav(rs.getString("dav"));
                cb.setOp(rs.getString("op"));
                cb.setDataentrega(rs.getString("dataentrega"));
                cb.setProcesso(rs.getString("processo"));
                cb.setNivel(rs.getInt("nivel"));
                cb.setValor(rs.getDouble("valor"));
                cb.setObservacao(rs.getString("observacao"));
                cb.setCliente(rs.getString("cliente"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<F_UPBean> readtableporprocesso(String processo) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<F_UPBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo = ? ORDER BY nivel, dataentrega, dav");
            stmt.setString(1, processo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                F_UPBean cb = new F_UPBean();

                cb.setId(rs.getInt("id"));
                cb.setMaterial(rs.getString("material"));
                cb.setDav(rs.getString("dav"));
                cb.setOp(rs.getString("op"));
                cb.setDataentrega(rs.getString("dataentrega"));
                cb.setProcesso(rs.getString("processo"));
                cb.setNivel(rs.getInt("nivel"));
                cb.setValor(rs.getDouble("valor"));
                cb.setObservacao(rs.getString("observacao"));
                cb.setCliente(rs.getString("cliente"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<F_UPBean> readtableporprocessoepesquisa(String processo, String pesquisa) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<F_UPBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo = ? AND op LIKE '%" + pesquisa + "%' ORDER BY nivel, dataentrega, dav");
            stmt.setString(1, processo);
            rs = stmt.executeQuery();

            while (rs.next()) {
                F_UPBean cb = new F_UPBean();

                cb.setId(rs.getInt("id"));
                cb.setMaterial(rs.getString("material"));
                cb.setOp(rs.getString("op"));
                cb.setDav(rs.getString("dav"));
                cb.setDataentrega(rs.getString("dataentrega"));
                cb.setProcesso(rs.getString("processo"));
                cb.setNivel(rs.getInt("nivel"));
                cb.setValor(rs.getDouble("valor"));
                cb.setObservacao(rs.getString("observacao"));
                cb.setCliente(rs.getString("cliente"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<F_UPBean> readcreated(String datacriacao) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<F_UPBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE datacriacao = ?");
            stmt.setString(1, datacriacao);
            rs = stmt.executeQuery();

            while (rs.next()) {
                F_UPBean cb = new F_UPBean();

                cb.setId(rs.getInt("id"));
                cb.setMaterial(rs.getString("material"));
                cb.setOp(rs.getString("op"));
                cb.setDav(rs.getString("dav"));
                cb.setDataentrega(rs.getString("dataentrega"));
                cb.setProcesso(rs.getString("processo"));
                cb.setNivel(rs.getInt("nivel"));
                cb.setValor(rs.getDouble("valor"));
                cb.setObservacao(rs.getString("observacao"));
                cb.setCliente(rs.getString("cliente"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<F_UPBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<F_UPBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                F_UPBean cb = new F_UPBean();

                cb.setId(rs.getInt("id"));
                cb.setDav(rs.getString("dav"));
                cb.setMaterial(rs.getString("material"));
                cb.setOp(rs.getString("op"));
                cb.setDataentrega(rs.getString("dataentrega"));
                cb.setProcesso(rs.getString("processo"));
                cb.setNivel(rs.getInt("nivel"));
                cb.setValor(rs.getDouble("valor"));
                cb.setObservacao(rs.getString("observacao"));
                cb.setCliente(rs.getString("cliente"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public void update(F_UPBean fb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE f_up SET dav = ?, op = ?, material = ?, dataentrega = ?, cliente = ?, nivel = ?, valor = ?, observacao = ? WHERE id = ?");

            stmt.setString(1, fb.getDav());
            stmt.setString(2, fb.getOp());
            stmt.setString(3, fb.getMaterial());
            stmt.setString(4, fb.getDataentrega());
            stmt.setString(5, fb.getCliente());
            stmt.setInt(6, fb.getNivel());
            stmt.setDouble(7, fb.getValor());
            stmt.setString(8, fb.getObservacao());
            stmt.setInt(9, fb.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateprocesso(F_UPBean fb) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE f_up SET processo = ? WHERE id = ?");

            stmt.setString(1, fb.getProcesso());
            stmt.setInt(2, fb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
