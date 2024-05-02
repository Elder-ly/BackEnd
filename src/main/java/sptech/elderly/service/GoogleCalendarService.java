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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.UsuarioRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service @RequiredArgsConstructor
public class GoogleCalendarService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    private Calendar service;

    private void autenticarCalendar(String accessToken) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        Credential credentials = new GoogleCredential().setAccessToken(accessToken);
        service = new Calendar(HTTP_TRANSPORT, JSON_FACTORY, credentials);
    }

    public Event inserirEvento(String accessToken,
                               String nomeProposta,
                               String emailCliente,
                               String emailFuncionario,
                               DateTime dataHoraInicio,
                               DateTime dataHoraFim) throws IOException, GeneralSecurityException {
        autenticarCalendar(accessToken);
        Event event = new Event()
                .setSummary("Elder.ly - " + nomeProposta)
                .setLocation("Endereço")
                .setDescription("Evento indicando o combinado com seu cuidador.");
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

        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
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
        System.out.printf("Event created: %s\n", event.getHtmlLink());
        return event;
    }

    public List<Event> listarEventos(String accessToken) throws IOException, GeneralSecurityException {
        autenticarCalendar(accessToken);
        Events events = service.events().list("primary").setQ("Elder.ly").execute();
        List<Event> items = events.getItems();
        return ordenarEventosPorNomeCliente(items, 0, items.size() - 1);
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


    public String encontrarEmailCliente(List<EventAttendee> attendees) {
        for (EventAttendee attendee : attendees) {
            if (attendee.getOrganizer() != null) {
                return attendee.getEmail();
            }
        }
        return null;
    }
}
