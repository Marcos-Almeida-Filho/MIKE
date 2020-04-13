/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.SenhasAcessoBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import java.awt.AWTException;
import java.awt.HeadlessException;
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
public class SenhasAcessoDAO {
    
    public void create(SenhasAcessoBean sab) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO senhas_acesso (idsenha, nome) VALUES (?,?)");

            stmt.setInt(1, sab.getIdsenha());
            stmt.setString(2, sab.getNome());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar acesso à senha!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasAcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<SenhasAcessoBean> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<SenhasAcessoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM senhas_acesso");
            rs = stmt.executeQuery();

            while (rs.next()) {
                SenhasAcessoBean cb = new SenhasAcessoBean();

                cb.setId(rs.getInt("id"));
                cb.setIdsenha(rs.getInt("idsenha"));
                cb.setNome(rs.getString("nome"));

                listbb.add(cb);
            }
        } catch (SQLException e) {
            Logger.getLogger(SenhasAcessoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasAcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<SenhasAcessoBean> click(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<SenhasAcessoBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM senhas_acesso WHERE idsenha = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                SenhasAcessoBean sab = new SenhasAcessoBean();

                sab.setId(rs.getInt("id"));
                sab.setIdsenha(rs.getInt("idsenha"));
                sab.setNome(rs.getString("nome"));

                listbb.add(sab);
            }
        } catch (SQLException e) {
            Logger.getLogger(SenhasAcessoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasAcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }
    
    public int numeroAcessos(int id) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        int acessos = 0;

        try {
            stmt = con.prepareStatement("SELECT * FROM senhas_acesso WHERE idsenha = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                acessos++;
            }
        } catch (SQLException e) {
            Logger.getLogger(SenhasAcessoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasAcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return acessos;
    }
    
    public void delete(SenhasAcessoBean sab) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM senhas_acesso WHERE id = ?");
            stmt.setInt(1, sab.getId());

            stmt.executeUpdate();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasAcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /*public void update(SenhasAcessoBean sab) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE senhas_acesso SET login = ?, senha = ?, nome = ?, site = ? WHERE id = ?");

            stmt.setString(1, sab.getLogin());
            stmt.setString(2, sab.getSenha());
            stmt.setString(3, sab.getNome());
            stmt.setString(4, sab.getSite());
            stmt.setInt(5, sab.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar senha!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(SenhasAcessoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }*/

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
//    public SenhasAcessoBean getById(final int id) {
//        return entityManager.find(SenhasAcessoBean.class, id);
//    }
//
//
//    @SuppressWarnings("unchecked")
//    public List<SenhasAcessoBean> findAll() {
//        return entityManager.createQuery("FROM " + SenhasAcessoBean.class.getName()).getResultList();
//    }
//
//    public void persist(SenhasAcessoBean cliente) {
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
//    public void merge(SenhasAcessoBean cliente) {
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
//    public void remove(SenhasAcessoBean cliente) {
//        try {
//            entityManager.getTransaction().begin();
//            cliente = entityManager.find(SenhasAcessoBean.class, cliente.getId());
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
//            SenhasAcessoBean cliente = getById(id);
//            remove(cliente);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
