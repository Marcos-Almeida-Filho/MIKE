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
public class ServicoPedidoDocumentosBean {

    private int id;
    private String idtela;
    private String idpedido;
    private String descricao;
    private String local;

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

    public String getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(String idpedido) {
        this.idpedido = idpedido;
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
