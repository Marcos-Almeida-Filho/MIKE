/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

/**
 *
 * @author Marcos Filho
 */
public class OPProcessosBean {

    private int id;
    private String op;
    private String processo;
    private double qtdtotal;
    private double qtdok;
    private double qtdnaook;
    private String user;
    private String datainicio;
    private String datafim;
    private String obs;
    private int ordem;
    private double qtddisponivel;
    private String motivo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public double getQtdtotal() {
        return qtdtotal;
    }

    public void setQtdtotal(double qtdtotal) {
        this.qtdtotal = qtdtotal;
    }

    public double getQtdok() {
        return qtdok;
    }

    public void setQtdok(double qtdok) {
        this.qtdok = qtdok;
    }

    public double getQtdnaook() {
        return qtdnaook;
    }

    public void setQtdnaook(double qtdnaook) {
        this.qtdnaook = qtdnaook;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public String getDatafim() {
        return datafim;
    }

    public void setDatafim(String datafim) {
        this.datafim = datafim;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public double getQtddisponivel() {
        return qtddisponivel;
    }

    public void setQtddisponivel(double qtddisponivel) {
        this.qtddisponivel = qtddisponivel;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

}
