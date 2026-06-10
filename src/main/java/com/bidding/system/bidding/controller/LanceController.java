package com.bidding.system.bidding.controller;

import com.bidding.system.bidding.model.LancesBean;
import com.bidding.system.bidding.service.LanceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lances")
public class LanceController {

    @Autowired
    private LanceService lservice;

    @GetMapping("/meus-lances")
    public List<LancesBean> meusLances(@RequestHeader("Authorization") String auth) {
        String token = auth.replace("Bearer ", "");
        return lservice.listarMeusLances(token);
    }
}
