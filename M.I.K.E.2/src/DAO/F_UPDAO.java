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

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<F_UPBean> listbb;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listbb = new ArrayList<>();
    }

    public void create(F_UPBean fb) {

        conStmt();

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

    public List<F_UPBean> readOPTodos() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE op NOT LIKE 'OS%' ORDER BY nivel, dataentrega, dav");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtable");
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

    public List<F_UPBean> readOSTodos() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE op LIKE 'OS%' ORDER BY nivel, dataentrega, dav");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtable");
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

        rsList();

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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtableplanejamento");
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

    public List<F_UPBean> readOPEmAberto() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo <> 'Encerrado' AND op NOT LIKE 'os%' ORDER BY nivel, dataentrega");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtableplanejamento");
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

    public List<F_UPBean> readOSEmAberto() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo <> 'Encerrado' AND op LIKE 'OS%' ORDER BY nivel, dataentrega");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtableplanejamento");
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

    public List<F_UPBean> readOPEmAbertoPesquisa(String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE op NOT LIKE 'OS%' AND processo <> 'Encerrado' AND op LIKE '%" + pesquisa + "%' OR op NOT LIKE 'OS%' AND processo <> 'Encerrado' AND dav LIKE '%" + pesquisa + "%' ORDER BY nivel, dataentrega");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtableplanejamento");
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

    public List<F_UPBean> readOSEmAbertoPesquisa(String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo <> 'Encerrado' AND op LIKE '%" + pesquisa + "%' AND op LIKE 'OS%' OR processo <> 'Encerrado' AND dav LIKE '%" + pesquisa + "%' AND op LIKE 'OS%' ORDER BY nivel, dataentrega");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtableplanejamento");
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

    public List<F_UPBean> readOPTodosPesquisa(String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE op LIKE '%" + pesquisa + "%' AND op NOT LIKE 'OS%' OR dav LIKE '%" + pesquisa + "%' AND op NOT LIKE 'OS%' ORDER BY nivel, dataentrega, dav");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtablepesquisa");
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

    public List<F_UPBean> readOSTodosPesquisa(String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE op LIKE '%" + pesquisa + "%' AND op LIKE 'OS%' OR dav LIKE '%" + pesquisa + "%' AND op LIKE 'OS%' ORDER BY nivel, dataentrega, dav");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtablepesquisa");
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

    public List<F_UPBean> readOPPorProcesso(String processo) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo = '" + processo + "' AND op NOT LIKE 'OS%' ORDER BY nivel, dataentrega, dav");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtableporprocesso");
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

    public List<F_UPBean> readOSPorProcesso(String processo) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo = '" + processo + "' AND op LIKE 'OS%' ORDER BY nivel, dataentrega, dav");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtableporprocesso");
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

    public List<F_UPBean> readOPProcessoPesquisa(String processo, String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo = '" + processo + "' AND op LIKE '%" + pesquisa + "%' AND op NOT LIKE 'OS%' OR processo = '" + processo + "' AND dav LIKE '%" + pesquisa + "%' AND op NOT LIKE 'OS%' ORDER BY nivel, dataentrega, dav");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtableporprocessoepesquisa");
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

    public List<F_UPBean> readOSProcessoPesquisa(String processo, String pesquisa) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE processo = '" + processo + "' AND op LIKE '%" + pesquisa + "%' AND op LIKE 'OS%' OR processo = '" + processo + "' AND dav LIKE '%" + pesquisa + "%' AND op LIKE 'OS%' ORDER BY nivel, dataentrega, dav");
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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readtableporprocessoepesquisa");
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

        rsList();

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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - readcreated");
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

        rsList();

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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - click");
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

    public int getId(String op) {

        rsList();

        int id = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM f_up WHERE op = '" + op + "'");
            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - getId");
            Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(F_UPDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return id;
    }

    public void update(F_UPBean fb) {

        conStmt();

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
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - update");
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

    public void updateProcessoById(F_UPBean fb) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE f_up SET processo = ? WHERE id = ?");

            stmt.setString(1, fb.getProcesso());
            stmt.setInt(2, fb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro em " + this.getClass().getSimpleName() + " - updateProcessoById");
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

    public void updateProcessoByOs(F_UPBean fb) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE f_up SET processo = ? WHERE op = ?");

            stmt.setString(1, fb.getProcesso());
            stmt.setString(2, fb.getOp());

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar F-UP.";
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
