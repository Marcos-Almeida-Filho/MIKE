/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.SenhasBean;
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
//import javax.persistence.*;

/**
 *
 * @author Marcos Filho
 */
public class SenhasDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<SenhasBean> listsb;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listsb = new ArrayList<>();
    }

    public void create(SenhasBean sb) {

        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO senhas (login, nome, senha, site) VALUES (?,?,?,?)");

            stmt.setString(1, sb.getLogin());
            stmt.setString(2, sb.getNome());
            stmt.setString(3, sb.getSenha());
            stmt.setString(4, sb.getSite());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar senha!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<SenhasBean> read() {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM senhas");
            rs = stmt.executeQuery();

            while (rs.next()) {
                SenhasBean cb = new SenhasBean();

                cb.setId(rs.getInt("id"));
                cb.setLogin(rs.getString("login"));
                cb.setNome(rs.getString("nome"));
                cb.setSenha(rs.getString("senha"));
                cb.setSite(rs.getString("site"));

                listsb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(SenhasDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listsb;
    }

    public int readcreated() {

        rsList();

        int lastid = 0;

        try {
            stmt = con.prepareStatement("SELECT MAX(id) AS id FROM senhas");
            rs = stmt.executeQuery();

            while (rs.next()) {
                lastid = rs.getInt("id");
            }
        } catch (SQLException e) {
            Logger.getLogger(SenhasDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return lastid;
    }

    public List<SenhasBean> click(int id) {

        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM senhas WHERE id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                SenhasBean cb = new SenhasBean();

                cb.setId(rs.getInt("id"));
                cb.setLogin(rs.getString("login"));
                cb.setNome(rs.getString("nome"));
                cb.setSenha(rs.getString("senha"));
                cb.setSite(rs.getString("site"));

                listsb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(SenhasDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listsb;
    }

    public void update(SenhasBean bb) {

        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE senhas SET login = ?, senha = ?, nome = ?, site = ? WHERE id = ?");

            stmt.setString(1, bb.getLogin());
            stmt.setString(2, bb.getSenha());
            stmt.setString(3, bb.getNome());
            stmt.setString(4, bb.getSite());
            stmt.setInt(5, bb.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar senha!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void delete(int id) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM senhas WHERE id = " + id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao excluir senha.";
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

//    public static SenhasDAO instance;
//    protected EntityManager entityManager;
//
//    public static SenhasDAO getInstance() {
//        if (instance == null) {
//            instance = new SenhasDAO();
//        }
//
//        return instance;
//    }
//
//    public SenhasDAO() {
//        entityManager = getEntityManager();
//    }
//
//    private EntityManager getEntityManager() {
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
//        if (entityManager == null) {
//            entityManager = factory.createEntityManager();
//        }
//
//        return entityManager;
//    }
//
//    public SenhasBean getById(final int id) {
//        return entityManager.find(SenhasBean.class, id);
//    }
//
//
//    @SuppressWarnings("unchecked")
//    public List<SenhasBean> findAll() {
//        return entityManager.createQuery("FROM " + SenhasBean.class.getName()).getResultList();
//    }
//
//    public void persist(SenhasBean cliente) {
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.persist(cliente);
//            entityManager.getTransaction().commit();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            entityManager.getTransaction().rollback();
//        }
//    }
//
//    public void merge(SenhasBean cliente) {
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.merge(cliente);
//            entityManager.getTransaction().commit();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            entityManager.getTransaction().rollback();
//        }
//    }
//
//    public void remove(SenhasBean cliente) {
//        try {
//            entityManager.getTransaction().begin();
//            cliente = entityManager.find(SenhasBean.class, cliente.getId());
//            entityManager.remove(cliente);
//            entityManager.getTransaction().commit();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            entityManager.getTransaction().rollback();
//        }
//    }
//
//    public void removeById(final int id) {
//        try {
//            SenhasBean cliente = getById(id);
//            remove(cliente);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
