package com.enth.agendacliente.infrastructure.security.client;

import com.enth.agendacliente.business.dto.out.ClienteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cliente", url = "${cliente.url}")
public interface ClienteClient {

    @GetMapping("/cliente")
    ClienteResponseDTO buscarPorEmail(@RequestParam String email,
                                      @RequestHeader("Authorization") String token);
}
