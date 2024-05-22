package sptech.elderly.web.controller;

import com.google.api.services.calendar.model.Event;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.service.GoogleCalendarService;
import sptech.elderly.util.ListaObj;
import sptech.elderly.web.dto.google.CriarEventoInput;
import sptech.elderly.web.dto.google.EventoConsultaDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/calendarios")
public class CalendarioController {

    private final GoogleCalendarService service;

    public CalendarioController(GoogleCalendarService service) {
        this.service = service;
    }

    @Operation(summary = "Insere um novo evento no calendário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento inserido com sucesso."),
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
}
