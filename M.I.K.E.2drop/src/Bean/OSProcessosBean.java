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
public class OSProcessosBean {
    
    private int id;
    private String idos;
    private String processo;
    private String inicio;
    private String termino;
    private int qtdok;
    private int qtdnaook;
    private String usuario;
    private int ordem;
    private String observacao;
    private String motivo;
    private String disponivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdos() {
        return idos;
    }

    public void setIdos(String idos) {
        this.idos = idos;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public int getQtdok() {
        return qtdok;
    }

    public void setQtdok(int qtdok) {
        this.qtdok = qtdok;
    }

    public int getQtdnaook() {
        return qtdnaook;
    }

    public void setQtdnaook(int qtdnaook) {
        this.qtdnaook = qtdnaook;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(String disponivel) {
        this.disponivel = disponivel;
    }
    
    
}
