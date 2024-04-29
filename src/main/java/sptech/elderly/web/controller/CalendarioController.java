package sptech.elderly.web.controller;

import com.google.api.services.calendar.model.Event;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.service.GoogleCalendarService;
import sptech.elderly.web.dto.google.CriarEventoInput;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/calendarios")
public class CalendarioController {
    private final GoogleCalendarService service;

    public CalendarioController(GoogleCalendarService service) {
        this.service = service;
    }

    @PostMapping("/eventos")
    public Event inserirEvento(
            @RequestBody CriarEventoInput eventoInput
            ) throws GeneralSecurityException, IOException {

        return service.inserirEvento(
                eventoInput.accessToken(),
                eventoInput.nomeProposta(),
                eventoInput.emailCliente(),
                eventoInput.emailFuncionario(),
                eventoInput.dataHoraInicio(),
                eventoInput.dataHoraFim());
    }
}
