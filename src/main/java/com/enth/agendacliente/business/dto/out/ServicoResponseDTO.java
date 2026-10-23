package com.enth.agendacliente.business.dto.out;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicoResponseDTO {

    private Long id;
    private String servico;
    private double valor;
}
