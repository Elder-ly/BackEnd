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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service @RequiredArgsConstructor
public class GoogleCalendarService {
    private Calendar service;

    private void autenticarCalendar(String accessToken) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        Credential credentials = new GoogleCredential().setAccessToken(accessToken);
        service = new Calendar(HTTP_TRANSPORT, JSON_FACTORY, credentials);
    }

    public Event inserirEvento(String acessToken,
                               String nomeProposta,
                               String emailCliente,
                               String emailFuncionario,
                               DateTime dataHoraInicio,
                               DateTime dataHoraFim) throws IOException, GeneralSecurityException {
        autenticarCalendar(acessToken);
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
}
