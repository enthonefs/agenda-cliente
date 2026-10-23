package com.enth.agendacliente.controller;

import com.enth.agendacliente.business.AgendaService;
import com.enth.agendacliente.business.dto.in.AgendaRequestDTO;
import com.enth.agendacliente.business.dto.in.StatusUpdateDTO;
import com.enth.agendacliente.business.dto.out.AgendaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agendamento")
public class AgendaController {

    private final AgendaService service;

    @PostMapping
    public ResponseEntity<AgendaResponseDTO> agendar(@RequestParam String email, @RequestParam Long idServico,
                                                     @RequestHeader("Authorization") String token, @RequestBody AgendaRequestDTO agendaRequestDTO){
        return ResponseEntity.ok(service.agendar(email, idServico, token, agendaRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<AgendaResponseDTO>> listarAgendas(){
        return ResponseEntity.ok(service.listarAgendas());
    }

    @GetMapping(params = "data")
    public ResponseEntity<List<AgendaResponseDTO>> listarAgendasDoDia(@RequestParam LocalDate data){
        return ResponseEntity.ok(service.listarAgendasDoDia(data));
    }

    @PatchMapping(params = "id")
    public ResponseEntity<AgendaResponseDTO> atualizarStatus(@RequestParam Long id, @RequestBody StatusUpdateDTO status){
        return ResponseEntity.ok(service.atualizarStatus(id, status));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAgendaPorId(@PathVariable Long id){
        service.deleteAgendaPorId(id);
        return ResponseEntity.ok().build();
    }

}
