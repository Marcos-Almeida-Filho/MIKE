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
public class InsumosMovBean {
    
    private int id;
    private int idinsumo;
    private String data;
    private String tipomov;
    private double qtdinicial;
    private double qtdmov;
    private double qtdfinal;
    private String funcionario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdinsumo() {
        return idinsumo;
    }

    public void setIdinsumo(int idinsumo) {
        this.idinsumo = idinsumo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipomov() {
        return tipomov;
    }

    public void setTipomov(String tipomov) {
        this.tipomov = tipomov;
    }

    public double getQtdinicial() {
        return qtdinicial;
    }

    public void setQtdinicial(double qtdinicial) {
        this.qtdinicial = qtdinicial;
    }

    public double getQtdmov() {
        return qtdmov;
    }

    public void setQtdmov(double qtdmov) {
        this.qtdmov = qtdmov;
    }

    public double getQtdfinal() {
        return qtdfinal;
    }

    public void setQtdfinal(double qtdfinal) {
        this.qtdfinal = qtdfinal;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }
    
    
}
