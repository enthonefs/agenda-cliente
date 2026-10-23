package com.enth.agendacliente.business.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendaRequestDTO {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime horario;
}
