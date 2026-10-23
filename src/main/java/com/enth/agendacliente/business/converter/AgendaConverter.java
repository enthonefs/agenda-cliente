package com.enth.agendacliente.business.converter;

import com.enth.agendacliente.business.dto.in.AgendaRequestDTO;

import com.enth.agendacliente.business.dto.out.AgendaResponseDTO;
import com.enth.agendacliente.infrastructure.entity.Agenda;
import com.enth.agendacliente.infrastructure.entity.StatusAgendaEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgendaConverter {

    public AgendaResponseDTO paraAgendaResponseDto(Agenda agendaEntity){
        return AgendaResponseDTO.builder()
                .id(agendaEntity.getId())
                .nomeCliente(agendaEntity.getNomeCliente())
                .tipoServico(agendaEntity.getTipoServico())
                .horario(agendaEntity.getHorario())
                .status(agendaEntity.getStatus())
                .build();
    }

    public Agenda paraAgendaEntity(AgendaRequestDTO agendaRequestDTO){
        return Agenda.builder()
                .horario(agendaRequestDTO.getHorario())
                .build();
    }

    public List<AgendaResponseDTO> paraListaAgendaResponseDto(List<Agenda> agendas){
        return agendas.stream()
                .map(this::paraAgendaResponseDto)
                .toList();
    }

    public StatusAgendaEnum paraStatusEnum(String status){
        if (status == null || status.isBlank()){
            return null;
        }
        return StatusAgendaEnum.valueOf(status.trim().toUpperCase());
    }

}
