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
public class OPMedicoesBean {

    private int id;
    private int idprocesso;
    private String medida;
    private String maior;
    private String menor;
    private String instrumento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdprocesso() {
        return idprocesso;
    }

    public void setIdprocesso(int idprocesso) {
        this.idprocesso = idprocesso;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getMaior() {
        return maior;
    }

    public void setMaior(String maior) {
        this.maior = maior;
    }

    public String getMenor() {
        return menor;
    }

    public void setMenor(String menor) {
        this.menor = menor;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

}
