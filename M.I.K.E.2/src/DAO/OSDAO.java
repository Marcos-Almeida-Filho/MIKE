/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.OSBean;
import Connection.ConnectionFactory;
import Methods.SendEmail;
import View.servicos.PedidoServico;
import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class OSDAO {

//    OSBean ob = new OSBean();
    
    int vezes = 0;
    
    public void create(OSBean osb) {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO os (idtela, dateabertura, dateprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topob, reconstrucaob, completab, desenhob, raio, frontal) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(1, osb.getIdtela());
            stmt.setString(2, osb.getDateabertura());
            stmt.setString(3, osb.getDateprevisao());
            stmt.setString(4, osb.getStatus());
            stmt.setString(5, osb.getCliente());
            stmt.setString(6, osb.getDas());
            stmt.setString(7, osb.getCodigo());
            stmt.setString(8, osb.getDescricao());
            stmt.setInt(9, osb.getQtdinicial());
            stmt.setInt(10, osb.getQtdok());
            stmt.setInt(11, osb.getQtdnaook());
            stmt.setString(12, osb.getNotes());
            stmt.setBoolean(13, osb.isTopob());
            stmt.setBoolean(14, osb.isReconstrucaob());
            stmt.setBoolean(15, osb.isCompletab());
            stmt.setBoolean(16, osb.isDesenhob());
            stmt.setString(17, osb.getRaio());
            stmt.setString(18, osb.getFrontal());

            stmt.executeUpdate();
            
            vezes++;
            
            if (vezes == PedidoServico.vezes) {
                JOptionPane.showMessageDialog(null,"Criado com sucesso!");
                PedidoServico.vezes = 0;
                vezes = 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<OSBean> read() {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean ob = new OSBean();

                ob.setId(rs.getInt("id"));
                ob.setIdtela(rs.getString("idtela"));
                ob.setDataabertura(rs.getString("dataabertura"));
                ob.setDataprevisao(rs.getString("dataprevisao"));
                ob.setDateabertura(rs.getString("dateabertura"));
                ob.setDateprevisao(rs.getString("dateprevisao"));
                ob.setStatus(rs.getString("status"));
                ob.setCliente(rs.getString("cliente"));
                ob.setDas(rs.getString("das"));
                ob.setCodigo(rs.getString("codigo"));
                ob.setDescricao(rs.getString("descricao"));
                ob.setQtdinicial(rs.getInt("qtdinicial"));
                ob.setQtdok(rs.getInt("qtdok"));
                ob.setQtdnaook(rs.getInt("qtdnaook"));
                ob.setNotes(rs.getString("notes"));
                ob.setTopo(rs.getString("topo"));
                ob.setReconstrucao(rs.getString("reconstrucao"));
                ob.setCompleta(rs.getString("completa"));
                ob.setDesenho(rs.getString("desenho"));
                ob.setRaio(rs.getString("raio"));
                ob.setFrontal(rs.getString("frontal"));
                ob.setTopob(rs.getBoolean("topob"));
                ob.setReconstrucaob(rs.getBoolean("reconstrucaob"));
                ob.setCompletab(rs.getBoolean("completab"));
                ob.setDesenhob(rs.getBoolean("desenhob"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(ob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<OSBean> reademaberto() {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE status = 'Rascunho' OR status = 'Ativo'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean ob = new OSBean();

                ob.setId(rs.getInt("id"));
                ob.setIdtela(rs.getString("idtela"));
                ob.setDataabertura(rs.getString("dataabertura"));
                ob.setDataprevisao(rs.getString("dataprevisao"));
                ob.setDateabertura(rs.getString("dateabertura"));
                ob.setDateprevisao(rs.getString("dateprevisao"));
                ob.setStatus(rs.getString("status"));
                ob.setCliente(rs.getString("cliente"));
                ob.setDas(rs.getString("das"));
                ob.setCodigo(rs.getString("codigo"));
                ob.setDescricao(rs.getString("descricao"));
                ob.setQtdinicial(rs.getInt("qtdinicial"));
                ob.setQtdok(rs.getInt("qtdok"));
                ob.setQtdnaook(rs.getInt("qtdnaook"));
                ob.setNotes(rs.getString("notes"));
                ob.setTopo(rs.getString("topo"));
                ob.setReconstrucao(rs.getString("reconstrucao"));
                ob.setCompleta(rs.getString("completa"));
                ob.setDesenho(rs.getString("desenho"));
                ob.setRaio(rs.getString("raio"));
                ob.setFrontal(rs.getString("frontal"));
                ob.setTopob(rs.getBoolean("topob"));
                ob.setReconstrucaob(rs.getBoolean("reconstrucaob"));
                ob.setCompletab(rs.getBoolean("completab"));
                ob.setDesenhob(rs.getBoolean("desenhob"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(ob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<OSBean> readstatus(String status) {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE status = ?");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean ob = new OSBean();

                ob.setId(rs.getInt("id"));
                ob.setIdtela(rs.getString("idtela"));
                ob.setDataabertura(rs.getString("dataabertura"));
                ob.setDataprevisao(rs.getString("dataprevisao"));
                ob.setDateabertura(rs.getString("dateabertura"));
                ob.setDateprevisao(rs.getString("dateprevisao"));
                ob.setStatus(rs.getString("status"));
                ob.setCliente(rs.getString("cliente"));
                ob.setDas(rs.getString("das"));
                ob.setCodigo(rs.getString("codigo"));
                ob.setDescricao(rs.getString("descricao"));
                ob.setQtdinicial(rs.getInt("qtdinicial"));
                ob.setQtdok(rs.getInt("qtdok"));
                ob.setQtdnaook(rs.getInt("qtdnaook"));
                ob.setNotes(rs.getString("notes"));
                ob.setTopo(rs.getString("topo"));
                ob.setReconstrucao(rs.getString("reconstrucao"));
                ob.setCompleta(rs.getString("completa"));
                ob.setRaio(rs.getString("raio"));
                ob.setFrontal(rs.getString("frontal"));
                ob.setTopob(rs.getBoolean("topob"));
                ob.setReconstrucaob(rs.getBoolean("reconstrucaob"));
                ob.setCompletab(rs.getBoolean("completab"));
                ob.setDesenhob(rs.getBoolean("desenhob"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(ob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<OSBean> readcreated(String codigo, String dataabertura) {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE codigo = ? AND dataabertura = ?");
            stmt.setString(1, codigo);
            stmt.setString(2, dataabertura);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean ob = new OSBean();

                ob.setIdtela(rs.getString("idtela"));

                listbb.add(ob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listbb;

    }

    public Boolean readnome() throws SQLException {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        Calendar c = Calendar.getInstance();
        String patterny = "yy";
        SimpleDateFormat simpleDateFormaty = new SimpleDateFormat(patterny);
        String year = simpleDateFormaty.format(c.getTime());
        String idtela = "OS" + year + "-0001";

        Boolean resp = false;

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE idtela = ?");
            stmt.setString(1, idtela);
            rs = stmt.executeQuery();

            // checking if ResultSet is empty
            resp = rs.next();
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return resp;
    }

    public List<OSBean> click(String idtela) {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE idtela = ?");
            stmt.setString(1, idtela);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean ob = new OSBean();

                ob.setDataabertura(rs.getString("dataabertura"));
                ob.setDataprevisao(rs.getString("dataprevisao"));
                ob.setStatus(rs.getString("status"));
                ob.setCliente(rs.getString("cliente"));
                ob.setDas(rs.getString("das"));
                ob.setCodigo(rs.getString("codigo"));
                ob.setDescricao(rs.getString("descricao"));
                ob.setQtdinicial(rs.getInt("qtdinicial"));
                ob.setQtdok(rs.getInt("qtdok"));
                ob.setQtdnaook(rs.getInt("qtdnaook"));
                ob.setNotes(rs.getString("notes"));
                ob.setTopo(rs.getString("topo"));
                ob.setReconstrucao(rs.getString("reconstrucao"));
                ob.setCompleta(rs.getString("completa"));
                ob.setDesenho(rs.getString("desenho"));
                ob.setRaio(rs.getString("raio"));
                ob.setFrontal(rs.getString("frontal"));
                ob.setTopob(rs.getBoolean("topob"));
                ob.setReconstrucaob(rs.getBoolean("reconstrucaob"));
                ob.setCompletab(rs.getBoolean("completab"));
                ob.setDesenhob(rs.getBoolean("desenhob"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(ob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public List<OSBean> pesquisa(String pesquisa, String status) {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE idtela LIKE '%" + pesquisa + "%' OR cliente LIKE '%" + pesquisa + "%' OR codigo LIKE '%" + pesquisa + "%' AND status = ?");
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean ob = new OSBean();

                ob.setId(rs.getInt("id"));
                ob.setIdtela(rs.getString("idtela"));
                ob.setDataabertura(rs.getString("dataabertura"));
                ob.setDataprevisao(rs.getString("dataprevisao"));
                ob.setDateabertura(rs.getString("dateabertura"));
                ob.setDateprevisao(rs.getString("dateprevisao"));
                ob.setStatus(rs.getString("status"));
                ob.setCliente(rs.getString("cliente"));
                ob.setDas(rs.getString("das"));
                ob.setCodigo(rs.getString("codigo"));
                ob.setDescricao(rs.getString("descricao"));
                ob.setQtdinicial(rs.getInt("qtdinicial"));
                ob.setQtdok(rs.getInt("qtdok"));
                ob.setQtdnaook(rs.getInt("qtdnaook"));
                ob.setNotes(rs.getString("notes"));
                ob.setTopo(rs.getString("topo"));
                ob.setReconstrucao(rs.getString("reconstrucao"));
                ob.setCompleta(rs.getString("completa"));
                ob.setRaio(rs.getString("raio"));
                ob.setFrontal(rs.getString("frontal"));
                ob.setTopob(rs.getBoolean("topob"));
                ob.setReconstrucaob(rs.getBoolean("reconstrucaob"));
                ob.setCompletab(rs.getBoolean("completab"));
                ob.setDesenhob(rs.getBoolean("desenhob"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(ob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return listbb;
    }

    public int osaberta() {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        ResultSet rs = null;

        List<OSBean> listbb = new ArrayList<>();

        int qtd;

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE status = ?");
            stmt.setString(1, "Ativo");
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean ob = new OSBean();

                ob.setId(rs.getInt("id"));
                ob.setIdtela(rs.getString("idtela"));
                ob.setDataabertura(rs.getString("dataabertura"));
                ob.setDataprevisao(rs.getString("dataprevisao"));
                ob.setDateabertura(rs.getString("dateabertura"));
                ob.setDateprevisao(rs.getString("dateprevisao"));
                ob.setStatus(rs.getString("status"));
                ob.setCliente(rs.getString("cliente"));
                ob.setDas(rs.getString("das"));
                ob.setCodigo(rs.getString("codigo"));
                ob.setDescricao(rs.getString("descricao"));
                ob.setQtdinicial(rs.getInt("qtdinicial"));
                ob.setQtdok(rs.getInt("qtdok"));
                ob.setQtdnaook(rs.getInt("qtdnaook"));
                ob.setNotes(rs.getString("notes"));
                ob.setTopo(rs.getString("topo"));
                ob.setReconstrucao(rs.getString("reconstrucao"));
                ob.setCompleta(rs.getString("completa"));
                ob.setRaio(rs.getString("raio"));
                ob.setFrontal(rs.getString("frontal"));
                ob.setTopob(rs.getBoolean("topob"));
                ob.setReconstrucaob(rs.getBoolean("reconstrucaob"));
                ob.setCompletab(rs.getBoolean("completab"));
                ob.setDesenhob(rs.getBoolean("desenhob"));

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, descricao, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(ob);
            }
        } catch (SQLException e) {
            Logger.getLogger(ServicoOrcamentoDAO.class.getName()).log(Level.SEVERE, null, e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        qtd = listbb.size();

        return qtd;
    }

    public void update(OSBean bb) {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os SET cliente = ?, das = ?, codigo = ?, descricao = ?, qtdinicial = ?, qtdok = ?, qtdnaook = ?, notes = ?, topob = ?, reconstrucaob = ?, completab = ?, desenhob = ?, raio = ?, frontal = ? WHERE idtela = ?");

            stmt.setString(1, bb.getCliente());
            stmt.setString(2, bb.getDas());
            stmt.setString(3, bb.getCodigo());
            stmt.setString(4, bb.getDescricao());
            stmt.setInt(5, bb.getQtdinicial());
            stmt.setInt(6, bb.getQtdok());
            stmt.setInt(7, bb.getQtdnaook());
            stmt.setString(8, bb.getNotes());
            stmt.setBoolean(9, bb.isTopob());
            stmt.setBoolean(10, bb.isReconstrucaob());
            stmt.setBoolean(11, bb.isCompletab());
            stmt.setBoolean(12, bb.isDesenhob());
            stmt.setString(13, bb.getRaio());
            stmt.setString(14, bb.getFrontal());
            stmt.setString(15, bb.getIdtela());
            //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updatestatus(OSBean bb) {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os SET status = ? WHERE idtela = ?");

            stmt.setString(1, bb.getStatus());
            stmt.setString(2, bb.getIdtela());
            //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void updateqtd(OSBean bb) {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE os SET qtdok = ?, qtdnaook = ? WHERE idtela = ?");

            stmt.setInt(1, bb.getQtdok());
            stmt.setInt(2, bb.getQtdnaook());
            stmt.setString(3, bb.getIdtela());
            //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal

            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!\n" + e);
            try {
                SendEmail.EnviarErro(e.toString());
            } catch (AWTException | IOException ex) {
                Logger.getLogger(OSDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void delete(String os) {
        Connection con = ConnectionFactory.getConnection();
        
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM os WHERE idtela = '" + os + "'");
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao excluir.\n" + e);
            SendEmail.EnviarErro2(e.toString());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
