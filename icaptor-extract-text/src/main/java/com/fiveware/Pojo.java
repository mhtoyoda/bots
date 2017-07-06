package com.fiveware;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

/**
 * Created by valdisnei on 26/06/17.
 */
@AutoProperty
public class Pojo {
    private String cnpj;
    private String icms;
    private String valorpagar;
    private String vencimento;
    private String referencia;
    private String numeroconta;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIcms() {
        return icms;
    }

    public void setIcms(String icms) {
        this.icms = icms;
    }

    public String getValorpagar() {
        return valorpagar;
    }

    public void setValorpagar(String valorpagar) {
        this.valorpagar = valorpagar;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNumeroconta() {
        return numeroconta;
    }

    public void setNumeroconta(String numeroconta) {
        this.numeroconta = numeroconta;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
