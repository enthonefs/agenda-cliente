package com.enth.agendacliente.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nomeCliente", length = 100)
    private String nomeCliente;
    @Column(name = "tipoServico", length = 100)
    private String tipoServico;
    @Column(name = "horario", length = 30)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime horario;
    @Column(name = "status", length = 15)
    @Enumerated(EnumType.STRING)
    private StatusAgendaEnum status;


}
