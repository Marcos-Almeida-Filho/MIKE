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
public class InstrumentosMedicaoCalibracaoBean {

    private int id;
    private String codigoinstrumento;
    private String certificado;
    private String datacalibracao;
    private String validade;
    private String local;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoinstrumento() {
        return codigoinstrumento;
    }

    public void setCodigoinstrumento(String codigoinstrumento) {
        this.codigoinstrumento = codigoinstrumento;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getDatacalibracao() {
        return datacalibracao;
    }

    public void setDatacalibracao(String datacalibracao) {
        this.datacalibracao = datacalibracao;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}
