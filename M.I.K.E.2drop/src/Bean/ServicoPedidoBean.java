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
public class ServicoPedidoBean {

    private int id;
    private String idtela;
    private String idorcamento;
    private String cliente;
    private String condicao;
    private String representante;
    private String vendedor;
    private String notes;
    private String status_retorno;
    private String status_cobranca;
    private String nfcliente;
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdtela() {
        return idtela;
    }

    public void setIdtela(String idtela) {
        this.idtela = idtela;
    }

    public String getIdorcamento() {
        return idorcamento;
    }

    public void setIdorcamento(String idorcamento) {
        this.idorcamento = idorcamento;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus_retorno() {
        return status_retorno;
    }

    public void setStatus_retorno(String status_retorno) {
        this.status_retorno = status_retorno;
    }

    public String getStatus_cobranca() {
        return status_cobranca;
    }

    public void setStatus_cobranca(String status_cobranca) {
        this.status_cobranca = status_cobranca;
    }

    public String getNfcliente() {
        return nfcliente;
    }

    public void setNfcliente(String nfcliente) {
        this.nfcliente = nfcliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
