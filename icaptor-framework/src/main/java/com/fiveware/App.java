package com.fiveware;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;


/**
 * Created by valdisnei on 26/06/17.
 */
@AutoProperty
public class App {

    @JsonIgnore
    public static void main(String[] args) {
        String path = "/home/valdisnei/dev/VOTORANTIM_ENERGIA_LTDA_0282682038_03-2017.pdf";


//        Map map = new HashMap();
//        map.put(FromTo("cnpj:","cnpj"),TypeSearch.CNPJ);
//        map.put("icms",  TypeSearch.MONEY);
//        map.put(FromTo("Total a Pagar - ","valorpagar"),TypeSearch.MONEY);
//        map.put("vencimento",  TypeSearch.DATE);
//        map.put(FromTo("Data de emissão: ","dataemissao"),TypeSearch.DATE);
//        map.put(FromTo("Conta","numeroconta")," ([0-9]{10})");
//
//        Object buildToFile = new PdfImpl(path).seek(map, App.class).buildToFile();
//
//
//
//        System.out.println("buildToFile = " + buildToFile);
    }

    private String cnpj;
    private String icms;
    private String valorpagar;
    private String vencimento;
    private String dataemissao;
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

    public String getDataemissao() {
        return dataemissao;
    }

    public void setDataemissao(String dataemissao) {
        this.dataemissao = dataemissao;
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
