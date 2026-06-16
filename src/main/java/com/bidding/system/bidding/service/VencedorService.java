package com.bidding.system.bidding.service;

import com.bidding.system.bidding.model.VencedorBean;
import com.bidding.system.bidding.repository.VencedorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Serviço responsável por consultas aos vencedores de editais.
 */
@Service
public class VencedorService {

    @Autowired
    private VencedorRepository vencedorRepository;

    @Autowired
    private TokenService tokenService;

    /**
     * Lista todos os vencedores registrados. Acessível apenas por COMPRADOR.
     */
    public List<VencedorBean> listarTodos(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido");
        }
        return vencedorRepository.listarTodos();
    }

    /**
     * Busca o vencedor de um edital específico.
     * Qualquer usuário autenticado pode consultar.
     */
    public VencedorBean buscarPorEdital(Long idEdital, String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!tokenService.validarToken(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Token inválido");
        }
        VencedorBean vencedor = vencedorRepository.buscarPorEdital(idEdital);
        if (vencedor == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404),
                    "Nenhum vencedor encontrado para este edital. O edital pode ainda estar aberto.");
        }
        return vencedor;
    }
}
