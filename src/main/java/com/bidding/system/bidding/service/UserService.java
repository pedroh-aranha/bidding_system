/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.model.UserRequestBean;
import com.bidding.system.bidding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class UserService {
      
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private TokenService tservice;
    
    public void register(UserBean user) {
        String mensagem = "";
        if(user.getNome().equals("")) {
            mensagem = "Nome nao preenchido";
        } else if (user.getEmail().equals("")) {
            mensagem = "Email não preenchida";
        } else if(user.getSenha().equals("")) {
            mensagem = "Senha não preenchida";
        }else if(user.getRole().equals("")) {
            user.setRole("Fornecedor");
        }
        
        if(!mensagem.equals("")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);
        }
        repository.register(user);
    }
    
    public String login(UserRequestBean user){
        String mensagem = "";
        if(user.getEmail().equals("")) {
            mensagem = "Email não preenchido";
        }else if(user.getSenha().equals("")) {
            mensagem = "Senha não preenchida";
        }
        
        if(!mensagem.equals("")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), mensagem);        
        }
        
        UserBean usuarioLogado = repository.login(user.getEmail(), user.getSenha());
        return tservice.gerarToken(usuarioLogado);
       }
}
