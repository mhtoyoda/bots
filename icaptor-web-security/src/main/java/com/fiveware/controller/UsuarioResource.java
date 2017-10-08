package com.fiveware.controller;

import com.fiveware.model.user.IcaptorUser;
import com.fiveware.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {


    @Autowired
    private ServiceUser serviceUser;

    @GetMapping
    public Iterable<IcaptorUser> getAll() {
        return serviceUser.getAll();
    }

}