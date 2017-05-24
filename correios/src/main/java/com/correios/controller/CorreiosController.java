package com.correios.controller;

import com.correios.domain.Endereco;
import com.correios.bot.ConsultaCEP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by valdisnei on 01/04/17.
 */
@RestController
@RefreshScope
public class CorreiosController {

    @Autowired
    private ConsultaCEP consultaCEP;

    @GetMapping("/buscaCep/{numeroCep}")
    public Endereco buscaCep(@PathVariable String numeroCep) throws Exception {
        return consultaCEP.getEndereco(numeroCep);
    }
}
