package sptech.elderly.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.Calendario;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.CalendarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.util.ListaObj;
import sptech.elderly.web.dto.google.EventoConsultaDTO;
import sptech.elderly.web.dto.google.EventoMapper;
import sptech.elderly.web.dto.usuario.UsuarioConsultaDto;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GoogleCalendarService {

    private final UsuarioRepository usuarioRepository;
    private final CalendarioRepository calendarioRepository;
    private final EventoMapper eventoMapper;

    public GoogleCalendarService(UsuarioRepository usuarioRepository, CalendarioRepository calendarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.calendarioRepository = calendarioRepository;
        this.eventoMapper = new EventoMapper();
    }

    private Calendar service;

    private void autenticarCalendar(String accessToken) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        Credential credentials = new GoogleCredential().setAccessToken(accessToken);
        service = new Calendar(HTTP_TRANSPORT, JSON_FACTORY, credentials);
    }

    public EventoConsultaDTO inserirEvento(String accessToken,
                                           String nomeProposta,
                                           String emailCliente,
                                           String emailFuncionario,
                                           DateTime dataHoraInicio,
                                           DateTime dataHoraFim,
                                           String recorrencia,
                                           String descricao) throws IOException, GeneralSecurityException {
        autenticarCalendar(accessToken);
        Event event = new Event()
                .setSummary("Elder.ly - " + nomeProposta)
                // .setLocation("Endereço") TODO: Pegar endereço pelo email do cliente
                .setDescription(
                        descricao != null
                                ? descricao
                                : "Evento combinado com seu cuidador na Elder.ly."
                );

        DateTime startDateTime = new DateTime(String.valueOf(dataHoraInicio));
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Sao_Paulo");
        event.setStart(start);

        DateTime endDateTime = new DateTime(String.valueOf(dataHoraFim));
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Sao_Paulo");
        event.setEnd(end);

        String[] recurrence = recorrencia != null
                ? new String[] {recorrencia}
                : new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail(emailCliente),
                new EventAttendee().setEmail(emailFuncionario),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(30),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        return eventoMapper.toDTO(event);
    }

    public List<EventoConsultaDTO> listarEventos(String accessToken, String ordenarPor) throws IOException, GeneralSecurityException {
        autenticarCalendar(accessToken);
        Events events = service.events().list("primary").setQ("Elder.ly").execute();
        List<Event> items = events.getItems();
        var eventsListaObj = eventoMapper.toListaObj(eventoMapper.toDTO(items));
        if (ordenarPor.equals("nomeCliente")) {
            return eventoMapper.toList(ordenarEventosPorNomeCliente(eventsListaObj, 0, eventsListaObj.getTamanho() - 1));
        } else {
            return eventoMapper.toList(ordenarEventosPorDataInicio(eventsListaObj, 0, eventsListaObj.getTamanho() - 1));
        }
    }

    public List<Event> ordenarEventosPorNomeCliente(List<Event> events, Integer indInicio, Integer indFim) {
        int i = indInicio;
        int j = indFim;
        String pivo = encontrarEmailCliente(events.get((indInicio + indFim) / 2).getAttendees()); // índice do meio

        while (i <= j) { // enquanto o início não ultrapassa o fim
            String emailAtualI = encontrarEmailCliente(events.get(i).getAttendees());
            String emailAtualJ = encontrarEmailCliente(events.get(j).getAttendees());

            // move o i para a direita até encontrar alguém maior ou igual ao pivô
            while (emailAtualI.compareTo(pivo) < 0) {
                i++;
                emailAtualI = encontrarEmailCliente(events.get(i).getAttendees());
            }
            // move o j para a esquerda até encontrar alguém menor ou igual ao pivô
            while (emailAtualJ.compareTo(pivo) > 0) {
                j--;
                emailAtualJ = encontrarEmailCliente(events.get(j).getAttendees());
            }

            if (i <= j) { // se i não ultrapassou j
                // troca
                Event aux = events.get(i);
                events.set(i, events.get(j));
                events.set(j, aux);
                i++;
                j--;
            }
        }

        // particionando: dividindo em partes menores
        if (indInicio < j) { // se houver elementos à esquerda do pivô
            ordenarEventosPorNomeCliente(events, indInicio, j);
        }
        if (i < indFim) { // se houver elementos à direita do pivô
            ordenarEventosPorNomeCliente(events, i, indFim);
        }
        return events;
    }

    public ListaObj<EventoConsultaDTO> ordenarEventosPorNomeCliente(ListaObj<EventoConsultaDTO> events, Integer indInicio, Integer indFim) {
        int i = indInicio;
        int j = indFim;
        String pivo = events.getElemento((indInicio + indFim) / 2).getEmailCliente(); // índice do meio

        while (i <= j) { // enquanto o início não ultrapassa o fim
            String emailAtualI = events.getElemento(i).getEmailCliente();
            String emailAtualJ = events.getElemento(j).getEmailCliente();

            // move o i para a direita até encontrar alguém maior ou igual ao pivô
            while (emailAtualI.compareTo(pivo) < 0) {
                i++;
                emailAtualI = events.getElemento(i).getEmailCliente();
            }
            // move o j para a esquerda até encontrar alguém menor ou igual ao pivô
            while (emailAtualJ.compareTo(pivo) > 0) {
                j--;
                emailAtualJ = events.getElemento(j).getEmailCliente();
            }

            if (i <= j) { // se i não ultrapassou j
                // troca
                EventoConsultaDTO aux = events.getElemento(i);
                events.setElemento(events.getElemento(j), i);
                events.setElemento(aux, j);
                i++;
                j--;
            }
        }

        // particionando: dividindo em partes menores
        if (indInicio < j) { // se houver elementos à esquerda do pivô
            ordenarEventosPorNomeCliente(events, indInicio, j);
        }
        if (i < indFim) { // se houver elementos à direita do pivô
            ordenarEventosPorNomeCliente(events, i, indFim);
        }
        return events;
    }

    public String encontrarEmailCliente(List<EventAttendee> attendees) {
        for (EventAttendee attendee : attendees) {
            if (attendee.getOrganizer() != null) {
                return attendee.getEmail();
            }
        }
        return null;
    }

    public ListaObj<EventoConsultaDTO> ordenarEventosPorDataInicio(ListaObj<EventoConsultaDTO> events, Integer indInicio, Integer indFim) {
        int i = indInicio;
        int j = indFim;
        String pivo = events.getElemento((indInicio + indFim) / 2).getDataHoraInicio(); // índice do meio

        while (i <= j) { // enquanto o início não ultrapassa o fim
            String emailAtualI = events.getElemento(i).getDataHoraInicio();
            String emailAtualJ = events.getElemento(j).getDataHoraInicio();

            // move o i para a direita até encontrar alguém maior ou igual ao pivô
            while (emailAtualI.compareTo(pivo) < 0) {
                i++;
                emailAtualI = events.getElemento(i).getDataHoraInicio();
            }
            // move o j para a esquerda até encontrar alguém menor ou igual ao pivô
            while (emailAtualJ.compareTo(pivo) > 0) {
                j--;
                emailAtualJ = events.getElemento(j).getDataHoraInicio();
            }

            if (i <= j) { // se i não ultrapassou j
                // troca
                EventoConsultaDTO aux = events.getElemento(i);
                events.setElemento(events.getElemento(j), i);
                events.setElemento(aux, j);
                i++;
                j--;
            }
        }

        // particionando: dividindo em partes menores
        if (indInicio < j) { // se houver elementos à esquerda do pivô
            ordenarEventosPorDataInicio(events, indInicio, j);
        }
        if (i < indFim) { // se houver elementos à direita do pivô
            ordenarEventosPorDataInicio(events, i, indFim);
        }
        return events;
    }

    public List<UsuarioEntity> filtrarFuncionariosPorDisponibilidade(
            String accessToken,
            DateTime dataHoraInicio,
            DateTime dataHoraFim,
            List<UsuarioEntity> usuarios
    ) throws GeneralSecurityException, IOException {
        autenticarCalendar(accessToken);

        List<Calendario> calendarios = calendarioRepository.findByUsuarioIn(usuarios);

        FreeBusyRequest request = new FreeBusyRequest();
        request.setTimeMin(dataHoraInicio);
        request.setTimeMax(dataHoraFim);

        List<FreeBusyRequestItem> itemList = new ArrayList<>();
        for (Calendario calendario : calendarios) {
            FreeBusyRequestItem item = new FreeBusyRequestItem();
            item.setId(calendario.getCalendarId());
            itemList.add(item);
        }
        request.setItems(itemList);

        Calendar.Freebusy.Query fbq = service.freebusy().query(request);
        FreeBusyResponse response = fbq.execute();

        // Analisando calendários de "Disponibilidade"
        for (Calendario calendario : calendarios) {
            if (response.getCalendars().get(calendario.getCalendarId()).getBusy().isEmpty()) {
                usuarios.remove(calendario.getUsuario());
            }
        }

        // Analisando calendários principais
        usuarios.removeIf(usuario -> !response.getCalendars().get(usuario.getEmail()).getBusy().isEmpty());

        return usuarios;
    }

    public String inserirCalendarioDisponibilidade(String accessToken) throws GeneralSecurityException, IOException {
        autenticarCalendar(accessToken);

        com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary("Disponibilidade");
        calendar.setTimeZone("America/Sao_Paulo");

        com.google.api.services.calendar.model.Calendar calendarioCriado = service.calendars().insert(calendar).execute();

        return calendarioCriado.getId();
    }
}
