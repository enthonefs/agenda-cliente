package com.enth.agendacliente.business.dto.in;

import com.enth.agendacliente.infrastructure.entity.StatusAgendaEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusUpdateDTO {

    private StatusAgendaEnum status;
}
