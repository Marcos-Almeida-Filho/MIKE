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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class OSDAO {

//    OSBean ob = new OSBean();
    int vezes = 0;

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<OSBean> listbb;

    public void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    public void rsList() {
        conStmt();

        rs = null;

        listbb = new ArrayList<>();
    }

    public void create(OSBean osb) {
        conStmt();

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
                JOptionPane.showMessageDialog(null, "Criado com sucesso!");
                PedidoServico.vezes = 0;
                vezes = 0;
            }
        } catch (SQLException e) {
            String msg = "Erro ao criar OS.";
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

    public List<OSBean> read() {
        rsList();

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
            String msg = "Erro ao ler OS's cadastradas.";
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
        return listbb;
    }

    public List<OSBean> reademaberto() {
        rsList();

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
            String msg = "Erro ao ler OS's abertas.";
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
        return listbb;
    }

    public List<OSBean> readstatus(String status) {
        rsList();

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
            String msg = "Erro ao ler OS's com status " + status;
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
        return listbb;
    }

    public List<OSBean> readcreated(String codigo, String dataabertura) {
        rsList();

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
            String msg = "Erro ao ler OS criada.";
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

        return listbb;

    }

    public Boolean readnome() throws SQLException {
        rsList();

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
            String msg = "Erro ao ler última OS.";
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

        return resp;
    }

    public List<OSBean> click(String idtela) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE idtela = ?");
            stmt.setString(1, idtela);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OSBean ob = new OSBean();

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

                //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal
                listbb.add(ob);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler OS selecionada.";
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
        return listbb;
    }

    public List<OSBean> pesquisa(String pesquisa, String status) {
        rsList();

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
            String msg = "Erro ao ler OS's cadastradas.";
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
        return listbb;
    }

    public int osaberta() {
        rsList();

        int qtd;

        try {
            stmt = con.prepareStatement("SELECT * FROM os WHERE status = 'Ativo'");

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
            String msg = "Erro ao ler OS's em aberto.";
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

        qtd = listbb.size();

        return qtd;
    }

    public void update(OSBean bb) {
        conStmt();

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
            String msg = "Erro ao atualizar OS.";
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

    public void updatestatus(OSBean bb) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE os SET status = ? WHERE idtela = ?");

            stmt.setString(1, bb.getStatus());
            stmt.setString(2, bb.getIdtela());
            //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizar status da OS.";
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

    public void updateqtd(OSBean bb) {
        conStmt();

        try {
            stmt = con.prepareStatement("UPDATE os SET qtdok = ?, qtdnaook = ? WHERE idtela = ?");

            stmt.setInt(1, bb.getQtdok());
            stmt.setInt(2, bb.getQtdnaook());
            stmt.setString(3, bb.getIdtela());
            //idtela, dataabertura, dataprevisao, status, cliente, das, codigo, desc, qtdinicial, qtdok, qtdnaook, notes, topo, reconstrucao, raio, frontal

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao atualizat quantidade da OS.";
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

    public void delete(String os) {
        conStmt();

        try {
            stmt = con.prepareStatement("DELETE FROM os WHERE idtela = '" + os + "'");

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, os + " excluída com sucesso.");
        } catch (SQLException e) {
            String msg = "Erro ao excluir a " + os;
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
