/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.lang.reflect.Method;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos Filho
 */
public class GrupoDeUsuariosBean {

    private int id;
    private String nome;
    private boolean menuadministracao;
    private boolean submenuusuarios;
    private boolean submenugrupodeusuarios;
    private boolean submenurepresentantes;
    private boolean submenuregioesdeatuacao;
    private boolean menufiscal;
    private boolean submenunotasfiscais;
    private boolean submenunaturezadeoperacao;
    private boolean menucomercial;
    private boolean submenuclientes;
    private boolean submenugrupodeclientes;
    private boolean submenufornecedores;
    private boolean submenucategoriadepreco;
    private boolean menufinanceiro;
    private boolean submenucontasareceber;
    private boolean submenucontasapagar;
    private boolean submenucondicoesdepagamento;
    private boolean submenubancos;
    private boolean menucompras;
    private boolean submenusolicitacaodecompras;
    private boolean submenuorcamentodecompras;
    private boolean submenupedidodecompras;
    private boolean submenuinsumos;
    private boolean submenutipodeproduto;
    private boolean menulogistica;
    private boolean submenucarros;
    private boolean submenurastreamentodedocumentos;
    private boolean menuqualidade;
    private boolean submenuinstrumentosdemedicao;
    private boolean submenuiqf;
    private boolean submenumedicoes;
    private boolean menuvendas;
    private boolean submenuorcamentosvenda;
    private boolean submenupedidosvenda;
    private boolean submenuops;
    private boolean submenuprodutosvenda;
    private boolean submenuprocessosvenda;
    private boolean submenugrupodeprocessosvenda;
    private boolean menuservicos;
    private boolean submenuorcamentosservico;
    private boolean submenupedidosservico;
    private boolean submenuoss;
    private boolean submenuprodutosservico;
    private boolean submenuprocessosservico;
    private boolean submenugrupodeprocessosservico;
    private boolean menuconfiguracoes;
    private boolean submenumenus;

    public static String[] getMethodsBoolean() {
        GrupoDeUsuariosBean gub = new GrupoDeUsuariosBean();
        Method[] m = gub.getClass().getDeclaredMethods();

        String[] method = new String[m.length];
        
        for (int i = 0; i < m.length; i++) {
            Class methodtype = m[i].getReturnType();
            String methodname = m[i].getName();
            if (methodtype.equals(boolean.class)) {
                method[i] = "ub." + methodname;
            }
        }

        return method;
    }
    
    public static int getNumberMethodsBoolean() {
        GrupoDeUsuariosBean gub = new GrupoDeUsuariosBean();
        Method[] m = gub.getClass().getDeclaredMethods();
        
        int qtd = 0;

        for (Method m1 : m) {
            Class methodtype = m1.getReturnType();
            if (methodtype.equals(boolean.class)) {
                qtd++;
            }
        }
        
        JOptionPane.showMessageDialog(null, qtd);
        return qtd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isMenuadministracao() {
        return menuadministracao;
    }

    public void setMenuadministracao(boolean menuadministracao) {
        this.menuadministracao = menuadministracao;
    }

    public boolean isSubmenuusuarios() {
        return submenuusuarios;
    }

    public void setSubmenuusuarios(boolean submenuusuarios) {
        this.submenuusuarios = submenuusuarios;
    }

    public boolean isSubmenugrupodeusuarios() {
        return submenugrupodeusuarios;
    }

    public void setSubmenugrupodeusuarios(boolean submenugrupodeusuarios) {
        this.submenugrupodeusuarios = submenugrupodeusuarios;
    }

    public boolean isSubmenurepresentantes() {
        return submenurepresentantes;
    }

    public void setSubmenurepresentantes(boolean submenurepresentantes) {
        this.submenurepresentantes = submenurepresentantes;
    }

    public boolean isSubmenuregioesdeatuacao() {
        return submenuregioesdeatuacao;
    }

    public void setSubmenuregioesdeatuacao(boolean submenuregioesdeatuacao) {
        this.submenuregioesdeatuacao = submenuregioesdeatuacao;
    }

    public boolean isMenufiscal() {
        return menufiscal;
    }

    public void setMenufiscal(boolean menufiscal) {
        this.menufiscal = menufiscal;
    }

    public boolean isSubmenunotasfiscais() {
        return submenunotasfiscais;
    }

    public void setSubmenunotasfiscais(boolean submenunotasfiscais) {
        this.submenunotasfiscais = submenunotasfiscais;
    }

    public boolean isSubmenunaturezadeoperacao() {
        return submenunaturezadeoperacao;
    }

    public void setSubmenunaturezadeoperacao(boolean submenunaturezadeoperacao) {
        this.submenunaturezadeoperacao = submenunaturezadeoperacao;
    }

    public boolean isMenucomercial() {
        return menucomercial;
    }

    public void setMenucomercial(boolean menucomercial) {
        this.menucomercial = menucomercial;
    }

    public boolean isSubmenuclientes() {
        return submenuclientes;
    }

    public void setSubmenuclientes(boolean submenuclientes) {
        this.submenuclientes = submenuclientes;
    }

    public boolean isSubmenugrupodeclientes() {
        return submenugrupodeclientes;
    }

    public void setSubmenugrupodeclientes(boolean submenugrupodeclientes) {
        this.submenugrupodeclientes = submenugrupodeclientes;
    }

    public boolean isSubmenufornecedores() {
        return submenufornecedores;
    }

    public void setSubmenufornecedores(boolean submenufornecedores) {
        this.submenufornecedores = submenufornecedores;
    }

    public boolean isSubmenucategoriadepreco() {
        return submenucategoriadepreco;
    }

    public void setSubmenucategoriadepreco(boolean submenucategoriadepreco) {
        this.submenucategoriadepreco = submenucategoriadepreco;
    }

    public boolean isMenufinanceiro() {
        return menufinanceiro;
    }

    public void setMenufinanceiro(boolean menufinanceiro) {
        this.menufinanceiro = menufinanceiro;
    }

    public boolean isSubmenucontasareceber() {
        return submenucontasareceber;
    }

    public void setSubmenucontasareceber(boolean submenucontasareceber) {
        this.submenucontasareceber = submenucontasareceber;
    }

    public boolean isSubmenucontasapagar() {
        return submenucontasapagar;
    }

    public void setSubmenucontasapagar(boolean submenucontasapagar) {
        this.submenucontasapagar = submenucontasapagar;
    }

    public boolean isSubmenucondicoesdepagamento() {
        return submenucondicoesdepagamento;
    }

    public void setSubmenucondicoesdepagamento(boolean submenucondicoesdepagamento) {
        this.submenucondicoesdepagamento = submenucondicoesdepagamento;
    }

    public boolean isSubmenubancos() {
        return submenubancos;
    }

    public void setSubmenubancos(boolean submenubancos) {
        this.submenubancos = submenubancos;
    }

    public boolean isMenucompras() {
        return menucompras;
    }

    public void setMenucompras(boolean menucompras) {
        this.menucompras = menucompras;
    }

    public boolean isSubmenusolicitacaodecompras() {
        return submenusolicitacaodecompras;
    }

    public void setSubmenusolicitacaodecompras(boolean submenusolicitacaodecompras) {
        this.submenusolicitacaodecompras = submenusolicitacaodecompras;
    }

    public boolean isSubmenuorcamentodecompras() {
        return submenuorcamentodecompras;
    }

    public void setSubmenuorcamentodecompras(boolean submenuorcamentodecompras) {
        this.submenuorcamentodecompras = submenuorcamentodecompras;
    }

    public boolean isSubmenupedidodecompras() {
        return submenupedidodecompras;
    }

    public void setSubmenupedidodecompras(boolean submenupedidodecompras) {
        this.submenupedidodecompras = submenupedidodecompras;
    }

    public boolean isSubmenuinsumos() {
        return submenuinsumos;
    }

    public void setSubmenuinsumos(boolean submenuinsumos) {
        this.submenuinsumos = submenuinsumos;
    }

    public boolean isSubmenutipodeproduto() {
        return submenutipodeproduto;
    }

    public void setSubmenutipodeproduto(boolean submenutipodeproduto) {
        this.submenutipodeproduto = submenutipodeproduto;
    }

    public boolean isMenulogistica() {
        return menulogistica;
    }

    public void setMenulogistica(boolean menulogistica) {
        this.menulogistica = menulogistica;
    }

    public boolean isSubmenucarros() {
        return submenucarros;
    }

    public void setSubmenucarros(boolean submenucarros) {
        this.submenucarros = submenucarros;
    }

    public boolean isSubmenurastreamentodedocumentos() {
        return submenurastreamentodedocumentos;
    }

    public void setSubmenurastreamentodedocumentos(boolean submenurastreamentodedocumentos) {
        this.submenurastreamentodedocumentos = submenurastreamentodedocumentos;
    }

    public boolean isMenuqualidade() {
        return menuqualidade;
    }

    public void setMenuqualidade(boolean menuqualidade) {
        this.menuqualidade = menuqualidade;
    }

    public boolean isSubmenuinstrumentosdemedicao() {
        return submenuinstrumentosdemedicao;
    }

    public void setSubmenuinstrumentosdemedicao(boolean submenuinstrumentosdemedicao) {
        this.submenuinstrumentosdemedicao = submenuinstrumentosdemedicao;
    }

    public boolean isSubmenuiqf() {
        return submenuiqf;
    }

    public void setSubmenuiqf(boolean submenuiqf) {
        this.submenuiqf = submenuiqf;
    }

    public boolean isSubmenumedicoes() {
        return submenumedicoes;
    }

    public void setSubmenumedicoes(boolean submenumedicoes) {
        this.submenumedicoes = submenumedicoes;
    }

    public boolean isMenuvendas() {
        return menuvendas;
    }

    public void setMenuvendas(boolean menuvendas) {
        this.menuvendas = menuvendas;
    }

    public boolean isSubmenuorcamentosvenda() {
        return submenuorcamentosvenda;
    }

    public void setSubmenuorcamentosvenda(boolean submenuorcamentosvenda) {
        this.submenuorcamentosvenda = submenuorcamentosvenda;
    }

    public boolean isSubmenupedidosvenda() {
        return submenupedidosvenda;
    }

    public void setSubmenupedidosvenda(boolean submenupedidosvenda) {
        this.submenupedidosvenda = submenupedidosvenda;
    }

    public boolean isSubmenuops() {
        return submenuops;
    }

    public void setSubmenuops(boolean submenuops) {
        this.submenuops = submenuops;
    }

    public boolean isSubmenuprodutosvenda() {
        return submenuprodutosvenda;
    }

    public void setSubmenuprodutosvenda(boolean submenuprodutosvenda) {
        this.submenuprodutosvenda = submenuprodutosvenda;
    }

    public boolean isSubmenuprocessosvenda() {
        return submenuprocessosvenda;
    }

    public void setSubmenuprocessosvenda(boolean submenuprocessosvenda) {
        this.submenuprocessosvenda = submenuprocessosvenda;
    }

    public boolean isSubmenugrupodeprocessosvenda() {
        return submenugrupodeprocessosvenda;
    }

    public void setSubmenugrupodeprocessosvenda(boolean submenugrupodeprocessosvenda) {
        this.submenugrupodeprocessosvenda = submenugrupodeprocessosvenda;
    }

    public boolean isMenuservicos() {
        return menuservicos;
    }

    public void setMenuservicos(boolean menuservicos) {
        this.menuservicos = menuservicos;
    }

    public boolean isSubmenuorcamentosservico() {
        return submenuorcamentosservico;
    }

    public void setSubmenuorcamentosservico(boolean submenuorcamentosservico) {
        this.submenuorcamentosservico = submenuorcamentosservico;
    }

    public boolean isSubmenupedidosservico() {
        return submenupedidosservico;
    }

    public void setSubmenupedidosservico(boolean submenupedidosservico) {
        this.submenupedidosservico = submenupedidosservico;
    }

    public boolean isSubmenuoss() {
        return submenuoss;
    }

    public void setSubmenuoss(boolean submenuoss) {
        this.submenuoss = submenuoss;
    }

    public boolean isSubmenuprodutosservico() {
        return submenuprodutosservico;
    }

    public void setSubmenuprodutosservico(boolean submenuprodutosservico) {
        this.submenuprodutosservico = submenuprodutosservico;
    }

    public boolean isSubmenuprocessosservico() {
        return submenuprocessosservico;
    }

    public void setSubmenuprocessosservico(boolean submenuprocessosservico) {
        this.submenuprocessosservico = submenuprocessosservico;
    }

    public boolean isSubmenugrupodeprocessosservico() {
        return submenugrupodeprocessosservico;
    }

    public void setSubmenugrupodeprocessosservico(boolean submenugrupodeprocessosservico) {
        this.submenugrupodeprocessosservico = submenugrupodeprocessosservico;
    }

    public boolean isMenuconfiguracoes() {
        return menuconfiguracoes;
    }

    public void setMenuconfiguracoes(boolean menuconfiguracoes) {
        this.menuconfiguracoes = menuconfiguracoes;
    }

    public boolean isSubmenumenus() {
        return submenumenus;
    }

    public void setSubmenumenus(boolean submenumenus) {
        this.submenumenus = submenumenus;
    }

}
