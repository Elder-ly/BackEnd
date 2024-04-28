package sptech.elderly.web.controller;

import com.google.api.services.calendar.model.Event;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sptech.elderly.service.GoogleCalendarService;

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
            @RequestParam String accessToken,
            @RequestParam String emailCliente,
            @RequestParam String emailFuncionario
            ) throws GeneralSecurityException, IOException {

        return service.inserirEvento(accessToken, emailCliente, emailFuncionario);
    }
}
