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
public class FornecedoresAltBean {
    
    private int id;
    private int idfornecedor;
    private String data;
    private String user;
    private String valor;
    private String valoranterior;
    private String valornovo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdfornecedor() {
        return idfornecedor;
    }

    public void setIdfornecedor(int idfornecedor) {
        this.idfornecedor = idfornecedor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValoranterior() {
        return valoranterior;
    }

    public void setValoranterior(String valoranterior) {
        this.valoranterior = valoranterior;
    }

    public String getValornovo() {
        return valornovo;
    }

    public void setValornovo(String valornovo) {
        this.valornovo = valornovo;
    }
    
    
}
