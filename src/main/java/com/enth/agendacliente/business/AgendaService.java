package com.enth.agendacliente.business;

import com.enth.agendacliente.business.converter.AgendaConverter;
import com.enth.agendacliente.business.dto.in.AgendaRequestDTO;
import com.enth.agendacliente.business.dto.in.StatusUpdateDTO;
import com.enth.agendacliente.business.dto.out.AgendaResponseDTO;
import com.enth.agendacliente.business.dto.out.ClienteResponseDTO;
import com.enth.agendacliente.business.dto.out.ServicoResponseDTO;
import com.enth.agendacliente.infrastructure.exceptions.ResourceNotFoundException;
import com.enth.agendacliente.infrastructure.security.client.ClienteClient;
import com.enth.agendacliente.infrastructure.security.client.ServicoClient;
import com.enth.agendacliente.infrastructure.entity.Agenda;
import com.enth.agendacliente.infrastructure.entity.StatusAgendaEnum;
import com.enth.agendacliente.infrastructure.exceptions.ConflictException;
import com.enth.agendacliente.infrastructure.repository.AgendaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final ClienteClient clienteClient;
    private final ServicoClient servicoClient;
    private final AgendaConverter agendaConverter;

    public AgendaResponseDTO agendar(String email, Long idServico, String token, AgendaRequestDTO agendaRequestDTO){

        ClienteResponseDTO cliente = clienteClient.buscarPorEmail(email, token);
        ServicoResponseDTO servico = servicoClient.buscarServicoPorId(idServico, token);
        Agenda agendamento = agendaConverter.paraAgendaEntity(agendaRequestDTO);

        LocalDateTime horarioFormatado = agendaRequestDTO.getHorario().withSecond(0).withNano(0);
        agendamento.setHorario(horarioFormatado);

        if (agendamento.getHorario().isBefore(LocalDateTime.now())){
            throw new ConflictException("Horário deve ser no futuro!");
        }

        validarRegraDeHorario(agendamento.getHorario());

        boolean horarioOcupado = agendaRepository.existsByHorarioAndStatusIn(
                agendamento.getHorario(),
                List.of(StatusAgendaEnum.AGENDADO, StatusAgendaEnum.ATENDENDO)
        );

        if (horarioOcupado){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este horário já está ocupado ou em atendimento."
            );
        }

        agendamento.setNomeCliente(cliente.getNome());
        agendamento.setTipoServico(servico.getServico());
        agendamento.setStatus(StatusAgendaEnum.AGENDADO);

        try {
            return agendaConverter.paraAgendaResponseDto(agendaRepository.save(agendamento));
        } catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este horário acabou de ser ocupado por outro usuário."
            );
        }
    }

    private void validarRegraDeHorario(LocalDateTime horario){

        LocalTime hora = horario.toLocalTime();

        LocalTime inicioExpediente = LocalTime.of(9, 0);
        LocalTime fimExpediente = LocalTime.of(20, 0);

        if (hora.isBefore(inicioExpediente) || hora.isAfter(fimExpediente)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O agendamento deve ser feito entre 09:00 e 20:00."
            );
        }

        int minuto = hora.getMinute();
        if (minuto % 20 != 0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os horários permitidos devem ser em intervalos de 20 minutos (ex: 09:00, 09:20, 09:40)."
            );
        }
    }

    public List<AgendaResponseDTO> listarAgendas(){

        List<Agenda> agendados = agendaRepository.findAll();
        return agendaConverter.paraListaAgendaResponseDto(agendados);
    }

    public List<AgendaResponseDTO> listarAgendasDoDia(LocalDate data){

        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime ultimaHoraDia = data.atTime(23, 59,59);

        return agendaConverter.paraListaAgendaResponseDto(agendaRepository.findByHorarioBetween(primeiraHoraDia, ultimaHoraDia));
    }

    @Transactional
    public AgendaResponseDTO atualizarStatus(Long id, StatusUpdateDTO status){

        Agenda agenda = agendaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Agendamento não encontrado."));

        String statusFormatado = String.valueOf(status.getStatus());
        agenda.setStatus(agendaConverter.paraStatusEnum(statusFormatado));

        return agendaConverter.paraAgendaResponseDto(agendaRepository.save(agenda));

    }

    public void deleteAgendaPorId(Long id){
        try {
            agendaRepository.deleteById(id);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Agendamento não encontrado");
        }
    }
}
