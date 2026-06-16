package com.bidding.system.bidding.controller;

import com.bidding.system.bidding.model.VencedorBean;
import com.bidding.system.bidding.service.VencedorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para consulta de vencedores de editais.
 */
@RestController
@RequestMapping("/api/vencedores")
public class VencedorController {

    @Autowired
    private VencedorService vencedorService;

    /**
     * GET /api/vencedores
     * Lista todos os vencedores registrados.
     */
    @GetMapping
    public List<VencedorBean> listarTodos(@RequestHeader("Authorization") String authHeader) {
        return vencedorService.listarTodos(authHeader);
    }

    /**
     * GET /api/vencedores/edital/{idEdital}
     * Retorna o vencedor de um edital específico.
     */
    @GetMapping("/edital/{idEdital}")
    public VencedorBean vencedorDoEdital(
            @PathVariable Long idEdital,
            @RequestHeader("Authorization") String authHeader) {
        return vencedorService.buscarPorEdital(idEdital, authHeader);
    }
}
