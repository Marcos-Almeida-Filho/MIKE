/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.io.Serializable;
//import javax.persistence.Id;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;

/**
 *
 * @author Marcos Filho
 */

//@Entity
//@Table(name = "senhas_acesso")
public class SenhasAcessoBean implements Serializable {
    
//    @Id
//    @Column
//    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
//    @Column
    private int idsenha;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdsenha() {
        return idsenha;
    }

    public void setIdsenha(int idsenha) {
        this.idsenha = idsenha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
