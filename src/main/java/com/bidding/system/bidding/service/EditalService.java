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
        } else if (edital.getDatafechamento().before(new java.sql.Date(System.currentTimeMillis()))) {
        message += "Data de fechamento não pode ser no passado!";
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
        }
        editalRepository.encerrarVencidos(); // ✅ encerra antes de listar
        return editalRepository.listaEdital();
    }

    public List<EditalBean> listaUrgentes(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Logar com conta valida!");
        }
        editalRepository.encerrarVencidos(); // ✅ encerra antes de listar
        return editalRepository.listaUrgentes();
    }

    public EditalBean getEditalById(Long id, String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Logar com conta valida!");
        }
        editalRepository.encerrarVencidos();
        EditalBean edital = editalRepository.getEditalCompleto(id);
        if (edital == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Edital não encontrado");
        }
        return edital;
    }

    /**
     * Encerra manualmente um edital específico e registra o vencedor.
     * Somente COMPRADOR pode executar.
     */
    public void encerrarEdital(Long id, String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido");
        }
        UserBean usuario = tokenService.extrairClaim(token);
        if (!"COMPRADOR".equals(usuario.getRole())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403),
                    "Apenas COMPRADOR pode encerrar editais");
        }

        EditalBean edital = editalRepository.getEditalCompleto(id);
        if (edital == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Edital não encontrado");
        }
        if ("ENCERRADO".equals(edital.getStatus())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Edital já está encerrado");
        }

        // Encerra o edital e registra o vencedor
        editalRepository.encerrarEditalPorId(id);
    }

}
