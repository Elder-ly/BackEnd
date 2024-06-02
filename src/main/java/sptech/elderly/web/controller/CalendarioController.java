package sptech.elderly.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.Calendario;
import sptech.elderly.service.GoogleCalendarService;
import sptech.elderly.web.dto.google.CalendarioOutput;
import sptech.elderly.web.dto.google.CriarEventoInput;
import sptech.elderly.web.dto.google.EventoConsultaDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/calendarios")
public class CalendarioController {

    private final GoogleCalendarService service;

    @Operation(summary = "Insere um novo evento no calendário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento inserido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para a criação do evento."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @PostMapping("/eventos")
    public EventoConsultaDTO inserirEvento(
            @RequestHeader String accessToken,
            @RequestBody CriarEventoInput eventoInput
    ) throws GeneralSecurityException, IOException {
        return service.inserirEvento(
                accessToken,
                eventoInput.nomeProposta(),
                eventoInput.emailCliente(),
                eventoInput.emailFuncionario(),
                eventoInput.dataHoraInicio(),
                eventoInput.dataHoraFim(),
                eventoInput.recorrencia(),
                eventoInput.descricao()
        );
    }

    @Operation(summary = "Lista eventos do cuidador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos listados com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhum evento encontrado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @GetMapping("/eventos")
    public List<EventoConsultaDTO> listarEventosCuidador(
            @RequestHeader String accessToken,
            @RequestParam(value = "ordenarPor", required = false, defaultValue = "dataHoraInicio") String ordenarPor
    ) throws IOException, GeneralSecurityException {
        return service.listarEventos(accessToken, ordenarPor);
    }

    @PostMapping
    public ResponseEntity<CalendarioOutput> criarCalendario(
            @RequestHeader String acessToken,
            @RequestParam Integer usuarioId
    ) throws GeneralSecurityException, IOException {
        return status(201).body(service.salvarCalendario(usuarioId, acessToken));
    }
}
