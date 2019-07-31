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
public class CAPDocumentosBean {

    private int id;
    private String idcap;
    private String idorcamento;
    private String descricao;
    private String local;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdcap() {
        return idcap;
    }

    public void setIdcap(String idcap) {
        this.idcap = idcap;
    }

    public String getIdorcamento() {
        return idorcamento;
    }

    public void setIdorcamento(String idorcamento) {
        this.idorcamento = idorcamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}
