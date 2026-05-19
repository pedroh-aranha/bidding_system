/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditalBean;
import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.repository.EditalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aluno
 */
@Service
public class EditalService {
    
    @Autowired
    private EditalRepository editalRepository;

    @Autowired
    private TokenService tokenService;

    public void novoEdital(EditalBean edital, UserBean usuarioLogado) {
        String message = "";
        if (!usuarioLogado.getRole().equals("COMPRADOR")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403),
                    "Acesso negado: apenas usuários com role COMPRADOR podem criar editais"
            );
        }
        if (edital.getTitulo().isEmpty()) {
            message += "Título não preenchido!";
        }
        if (edital.getDescricao().isEmpty()) {
            message += "Descrição não preenchida!";
        }
        if (edital.getDatafechamento() == null) {
            message += "Data não preenchida!";
        }
        if (!message.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), message);
        }
        edital.setStatus("ABERTO");
        int rows = editalRepository.novoEdital(edital);
        if (rows == 0) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(500),
                    "Erro ao criar edital");
        }
    }

    public List<EditalBean> listaEdital(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Logar com conta valida!");
        } else {
            return editalRepository.listaEdital();
        }
    }
    
}
