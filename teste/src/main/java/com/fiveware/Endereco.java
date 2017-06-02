package com.fiveware;

import java.io.Serializable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fiveware.annotation.Field;

/**
 * Created by valdisnei on 16/05/17.
 */
@AutoProperty
public class Endereco implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4321879773490723975L;
	@Field(name = "logradouro")	
	private final String logradouro;
    private final String bairro;
    private final String localidade;
    private final String cep;

    public Endereco(String logradouro, String bairro, String localidade, String cep) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.localidade = localidade;
        this.cep = cep;
    }
    
    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getCep() {
        return cep;
    }


    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
