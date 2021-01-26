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
public class VendasMateriaisBean {

    private int id;
    private String codigo;
    private String descricao;
    private double estoque, estoqueMinimo, qtdMinimaOP;
    private String local;
    private String status;
    private String d1, d2, d3, d4, d5, l1, l2, l3, l4, l5, materialOrigem, rev, raio, tipo, familia, tamanho, cortes, topo, canal, extra, helice, nucleo, concavidade, topo1, topo2, alivio1, alivio2, filete, agressividade, frontal, tolD1, tolD2, tolD3, tolD4, tolD5, mp;
    private boolean importada, weldon, ri, md, hss, desbaste, aparecerExtra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getEstoque() {
        return estoque;
    }

    public void setEstoque(double estoque) {
        this.estoque = estoque;
    }

    public double getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(double estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public double getQtdMinimaOP() {
        return qtdMinimaOP;
    }

    public void setQtdMinimaOP(double qtdMinimaOP) {
        this.qtdMinimaOP = qtdMinimaOP;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public String getD5() {
        return d5;
    }

    public void setD5(String d5) {
        this.d5 = d5;
    }

    public String getL1() {
        return l1;
    }

    public void setL1(String l1) {
        this.l1 = l1;
    }

    public String getL2() {
        return l2;
    }

    public void setL2(String l2) {
        this.l2 = l2;
    }

    public String getL3() {
        return l3;
    }

    public void setL3(String l3) {
        this.l3 = l3;
    }

    public String getL4() {
        return l4;
    }

    public void setL4(String l4) {
        this.l4 = l4;
    }

    public String getL5() {
        return l5;
    }

    public void setL5(String l5) {
        this.l5 = l5;
    }

    public String getMaterialOrigem() {
        return materialOrigem;
    }

    public void setMaterialOrigem(String materialOrigem) {
        this.materialOrigem = materialOrigem;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getRaio() {
        return raio;
    }

    public void setRaio(String raio) {
        this.raio = raio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCortes() {
        return cortes;
    }

    public void setCortes(String cortes) {
        this.cortes = cortes;
    }

    public String getTopo() {
        return topo;
    }

    public void setTopo(String topo) {
        this.topo = topo;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getHelice() {
        return helice;
    }

    public void setHelice(String helice) {
        this.helice = helice;
    }

    public String getNucleo() {
        return nucleo;
    }

    public void setNucleo(String nucleo) {
        this.nucleo = nucleo;
    }

    public String getConcavidade() {
        return concavidade;
    }

    public void setConcavidade(String concavidade) {
        this.concavidade = concavidade;
    }

    public String getTopo1() {
        return topo1;
    }

    public void setTopo1(String topo1) {
        this.topo1 = topo1;
    }

    public String getTopo2() {
        return topo2;
    }

    public void setTopo2(String topo2) {
        this.topo2 = topo2;
    }

    public String getAlivio1() {
        return alivio1;
    }

    public void setAlivio1(String alivio1) {
        this.alivio1 = alivio1;
    }

    public String getAlivio2() {
        return alivio2;
    }

    public void setAlivio2(String alivio2) {
        this.alivio2 = alivio2;
    }

    public String getFilete() {
        return filete;
    }

    public void setFilete(String filete) {
        this.filete = filete;
    }

    public String getAgressividade() {
        return agressividade;
    }

    public void setAgressividade(String agressividade) {
        this.agressividade = agressividade;
    }

    public String getFrontal() {
        return frontal;
    }

    public void setFrontal(String frontal) {
        this.frontal = frontal;
    }

    public boolean isImportada() {
        return importada;
    }

    public void setImportada(boolean importada) {
        this.importada = importada;
    }

    public boolean isWeldon() {
        return weldon;
    }

    public void setWeldon(boolean weldon) {
        this.weldon = weldon;
    }

    public boolean isRi() {
        return ri;
    }

    public void setRi(boolean ri) {
        this.ri = ri;
    }

    public boolean isMd() {
        return md;
    }

    public void setMd(boolean md) {
        this.md = md;
    }

    public boolean isHss() {
        return hss;
    }

    public void setHss(boolean hss) {
        this.hss = hss;
    }

    public boolean isDesbaste() {
        return desbaste;
    }

    public void setDesbaste(boolean desbaste) {
        this.desbaste = desbaste;
    }

    public String getTolD1() {
        return tolD1;
    }

    public void setTolD1(String tolD1) {
        this.tolD1 = tolD1;
    }

    public String getTolD2() {
        return tolD2;
    }

    public void setTolD2(String tolD2) {
        this.tolD2 = tolD2;
    }

    public String getTolD3() {
        return tolD3;
    }

    public void setTolD3(String tolD3) {
        this.tolD3 = tolD3;
    }

    public String getTolD4() {
        return tolD4;
    }

    public void setTolD4(String tolD4) {
        this.tolD4 = tolD4;
    }

    public String getTolD5() {
        return tolD5;
    }

    public void setTolD5(String tolD5) {
        this.tolD5 = tolD5;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public boolean isAparecerExtra() {
        return aparecerExtra;
    }

    public void setAparecerExtra(boolean aparecerExtra) {
        this.aparecerExtra = aparecerExtra;
    }
    
    
}
