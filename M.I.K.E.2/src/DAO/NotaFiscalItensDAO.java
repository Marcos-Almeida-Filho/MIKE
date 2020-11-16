/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.NotaFiscalItensBean;
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
public class NotaFiscalItensDAO {

    Connection con;

    PreparedStatement stmt;

    ResultSet rs;

    List<NotaFiscalItensBean> listnfi;

    NotaFiscalItensBean nfib;

    private void conStmt() {
        con = ConnectionFactory.getConnection();

        stmt = null;
    }

    private void rsList() {
        conStmt();

        rs = null;

        listnfi = new ArrayList<>();
    }

    /**
     *
     * @param numeroNF
     * @param codigo
     * @param descricao
     * @param obs
     * @param un
     * @param cfop
     * @param ncm
     * @param csosn
     * @param cst
     * @param qtd
     * @param valorUnitario
     * @param valorTotal
     * @param valorIcms
     * @param baseIcmsSt
     * @param valorIcmsSt
     * @param aliquotaIcms
     * @param aliquotaIcmsSt
     */
    public void create(int numeroNF, String codigo, String descricao, String obs, String un, String cfop, String ncm, String csosn, String cst, double qtd, double valorUnitario, double valorTotal, double valorIcms, double baseIcmsSt, double valorIcmsSt, double aliquotaIcms, double aliquotaIcmsSt) {
        conStmt();

        try {
            stmt = con.prepareStatement("INSERT INTO nf_itens (numeroNF, codigo, descricao, obs, un, cfop, ncm, csosn, cst, qtd, valorUnitario, valorTotal, valorIcms, baseIcmsSt, valorIcmsSt, aliquotaIcms, aliquotaIcmsSt) VALUES (" + numeroNF + ", '" + codigo + "', '" + descricao + "', '" + obs + "', '" + un + "', '" + cfop + "', '" + ncm + "', '" + csosn + "', '" + cst + "', " + qtd + ", " + valorUnitario + ", " + valorTotal + ", " + valorIcms + ", " + baseIcmsSt + ", " + valorIcmsSt + ", " + aliquotaIcms + ", " + aliquotaIcmsSt + ")");

            stmt.executeUpdate();
        } catch (SQLException e) {
            String msg = "Erro ao criar item da Nota Fiscal " + numeroNF;
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<NotaFiscalItensBean> readItens(int numeroNF) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM nf_itens WHERE numeroNF = " + numeroNF);

            rs = stmt.executeQuery();

            while (rs.next()) {
                nfib = new NotaFiscalItensBean();

                nfib.setId(rs.getInt("id"));
                nfib.setIdMaterial(rs.getInt("idMaterial"));
                nfib.setCodigo(rs.getString("codigo"));
                nfib.setDescricao(rs.getString("descricao"));
                nfib.setQtd(rs.getDouble("qtd"));
                nfib.setValorUnitario(rs.getDouble("valorUnitario"));
                nfib.setValorTotal(rs.getDouble("valorTotal"));

                listnfi.add(nfib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Itens da Nota Fiscal " + numeroNF;
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listnfi;
    }

    public List<NotaFiscalItensBean> readItem(int id) {
        rsList();

        try {
            stmt = con.prepareStatement("SELECT * FROM nf_itens WHERE id = " + id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                nfib = new NotaFiscalItensBean();

                nfib.setCodigo(rs.getString("codigo"));
                nfib.setDescricao(rs.getString("descricao"));
                nfib.setObs(rs.getString("obs"));
                nfib.setUn(rs.getString("un"));
                nfib.setCfop(rs.getString("cfop"));
                nfib.setNcm(rs.getString("ncm"));
                nfib.setCsosn(rs.getString("csosn"));
                nfib.setCst(rs.getString("cst"));
                nfib.setQtd(rs.getDouble("qtd"));
                nfib.setValorUnitario(rs.getDouble("valorUnitario"));
                nfib.setValorTotal(rs.getDouble("valorTotal"));
                nfib.setValorIcms(rs.getDouble("valorIcms"));
                nfib.setBaseIcmsSt(rs.getDouble("baseIcmsSt"));
                nfib.setValorIcmsSt(rs.getDouble("valorIcmsSt"));
                nfib.setAliquotaIcms(rs.getDouble("aliquotaIcms"));
                nfib.setAliquotaIcmsSt(rs.getDouble("aliquotaIcmsSt"));

                listnfi.add(nfib);
            }
        } catch (SQLException e) {
            String msg = "Erro ao ler Item da Nota Fiscal.";
            JOptionPane.showMessageDialog(null, msg);

            new Thread() {

                @Override
                public void run() {
                    SendEmail.EnviarErro2(msg + "\n" + e);
                }
            }.start();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return listnfi;
    }
}
