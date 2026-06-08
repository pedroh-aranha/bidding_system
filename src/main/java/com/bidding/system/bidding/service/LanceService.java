/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.EditalBean;
import com.bidding.system.bidding.model.LancesBean;
import com.bidding.system.bidding.model.UserBean;
import com.bidding.system.bidding.repository.EditalRepository;
import com.bidding.system.bidding.repository.LanceRepository;
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
public class LanceService {

    @Autowired
    private TokenService tservice;

    @Autowired
    private EditalRepository eservice;

    @Autowired
    private LanceRepository lservice;

    public void criarLance(Long id, LancesBean lance, String token) {
        if (tservice.validarToken(token)) {
            UserBean userLogado = tservice.extrairClaim(token);
            EditalBean edital = eservice.getbyid(id);
            if (!userLogado.getRole().equals("FORNECEDOR")) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Você precisa ser fornecedor para cadastrar um lance");
            }

            if (!edital.getStatus().equals("ABERTO")) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Edital ja fechado para lances");
            }

            if (edital.getDatafechamento().before(lance.getData_lance())) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Data do lance posterior ao fechamento");
            }

            lance.setIdEdital(id);
            lance.setIdusuario(userLogado.getId());

            int linhasafetadas = lservice.criarLance(lance);
            if (linhasafetadas == 0) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(500), "Erro ao criar lance");
            }

        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token invalido");
        }

    }

    public List<LancesBean> listarLancesDoEdital(Long idEdital, String token) {
        if (!tservice.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token invalido");
        }
        return lservice.listarPorEdital(idEdital);
    }

}
