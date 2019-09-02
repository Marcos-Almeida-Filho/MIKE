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
public class RastreamentoDocumentosBean {

    private int id;
    private String data;
    private boolean cliente;
    private boolean fornecedor;
    private boolean outros;
    private String emitente;
    private String numero;
    private String emissao;
    private boolean nf;
    private boolean conta;
    private boolean outrostipo;
    private String outrosdesc;
    private String xml;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isCliente() {
        return cliente;
    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    public boolean isFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(boolean fornecedor) {
        this.fornecedor = fornecedor;
    }

    public boolean isOutros() {
        return outros;
    }

    public void setOutros(boolean outros) {
        this.outros = outros;
    }

    public String getEmitente() {
        return emitente;
    }

    public void setEmitente(String emitente) {
        this.emitente = emitente;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmissao() {
        return emissao;
    }

    public void setEmissao(String emissao) {
        this.emissao = emissao;
    }

    public boolean isNf() {
        return nf;
    }

    public void setNf(boolean nf) {
        this.nf = nf;
    }

    public boolean isConta() {
        return conta;
    }

    public void setConta(boolean conta) {
        this.conta = conta;
    }

    public boolean isOutrostipo() {
        return outrostipo;
    }

    public void setOutrostipo(boolean outrostipo) {
        this.outrostipo = outrostipo;
    }

    public String getOutrosdesc() {
        return outrosdesc;
    }

    public void setOutrosdesc(String outrosdesc) {
        this.outrosdesc = outrosdesc;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}
