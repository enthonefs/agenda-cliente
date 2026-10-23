package com.enth.agendacliente.infrastructure.security.client;

import com.enth.agendacliente.business.dto.out.ServicoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "servico", url = "${servico.url}")
public interface ServicoClient {

    @GetMapping("/servico")
    ServicoResponseDTO buscarServicoPorId(@RequestParam Long id,
                                          @RequestHeader("Authorization") String token);
}