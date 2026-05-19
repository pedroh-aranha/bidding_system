/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.controller;

import com.bidding.system.bidding.model.EditalBean;
import com.bidding.system.bidding.model.LancesBean;
import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.service.EditalService;
import com.bidding.system.bidding.service.LanceService;
import com.bidding.system.bidding.service.TokenService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Aluno
 */
@RestController
@RequestMapping("/api/editais")
public class EditalController {
    
    @Autowired
    private EditalService editalService;

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private LanceService lservice;

    @PostMapping("/criar")
    public String criarEdital(@RequestBody EditalBean edital, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        UserBean usuarioLogado = tokenService.extrairClaim(token);
        editalService.novoEdital(edital, usuarioLogado);
        return "Edital criado com sucesso";
    }

    @GetMapping
    public List<EditalBean> listaEdital(@RequestHeader("Authorization") String authHeader) {
        return editalService.listaEdital(authHeader);
    }

    @PostMapping("/{id}/laces")
    public String registrarlance(@RequestHeader ("Authorization") String auth, @RequestBody LancesBean lance, @PathVariable long id) {
        String token = auth.replace("Bearer", "");
        lservice.criarLance(id, lance, token);
        return "Lance registrado com sucesso";
        
    }
    
}
