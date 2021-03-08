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
public class VendasMateriaisMovBean {

    private int id;
    private int idmaterial;
    private double qtdInicial;
    private double qtdMovimentada;
    private double saldo;
    private String tipo;
    private String data;
    private String usuario;
    private double valorCobrado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdmaterial() {
        return idmaterial;
    }

    public void setIdmaterial(int idmaterial) {
        this.idmaterial = idmaterial;
    }

    public double getQtdInicial() {
        return qtdInicial;
    }

    public void setQtdInicial(double qtdInicial) {
        this.qtdInicial = qtdInicial;
    }

    public double getQtdMovimentada() {
        return qtdMovimentada;
    }

    public void setQtdMovimentada(double qtdMovimentada) {
        this.qtdMovimentada = qtdMovimentada;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public double getValorCobrado() {
        return valorCobrado;
    }

    public void setValorCobrado(double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

}
