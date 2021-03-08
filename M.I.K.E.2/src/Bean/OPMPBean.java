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
public class OPMPBean {

    private int id;
    private String op;
    private String codigo;
    private String descricao;
    private double qtd;
    private boolean baixa;
    private boolean insumo;
    private String lote;
    private int idInsumo;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public boolean isBaixa() {
        return baixa;
    }

    public void setBaixa(boolean baixa) {
        this.baixa = baixa;
    }

    public boolean isInsumo() {
        return insumo;
    }

    public void setInsumo(boolean insumo) {
        this.insumo = insumo;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    
    public int getIdInsumo() {
        return idInsumo;
    }
    
    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }
}
