package com.enth.agendacliente.infrastructure.repository;

import com.enth.agendacliente.infrastructure.entity.Agenda;
import com.enth.agendacliente.infrastructure.entity.StatusAgendaEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    List<Agenda> findByHorarioBetween(LocalDateTime dataHoraInicial, LocalDateTime dataHoraFinal);

    boolean existsByHorarioAndStatusIn(LocalDateTime horario, List<StatusAgendaEnum> statusList);

}
