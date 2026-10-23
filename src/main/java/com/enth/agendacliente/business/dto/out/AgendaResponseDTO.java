package com.enth.agendacliente.business.dto.out;

import com.enth.agendacliente.infrastructure.entity.StatusAgendaEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaResponseDTO {

    private Long id;
    private String nomeCliente;
    private String tipoServico;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime horario;
    private StatusAgendaEnum status;
}
