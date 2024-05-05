package sptech.elderly.web.dto.google;

import com.google.api.services.calendar.model.Event;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.events.EventException;
import sptech.elderly.util.ListaObj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventoMapper {
    public EventoConsultaDTO toDTO(Event event) {
        int organizerIndex = event.getAttendees().get(0).getOrganizer() != null
                ? 0
                : 1;
        return new EventoConsultaDTO(
                event.getId(),
                event.getSummary(),
                event.getAttendees().get(organizerIndex).getEmail(),
                event.getAttendees().get(organizerIndex == 0 ? 1 : 0).getEmail(),
                event.getStart().getDateTime().toStringRfc3339(),
                event.getEnd().getDateTime().toStringRfc3339(),
                event.getRecurrence(),
                event.getDescription()
        );
    }

    public List<EventoConsultaDTO> toDTO(List<Event> events) {
        return events.stream().map(this::toDTO).toList();
    }

    public List<EventoConsultaDTO> toList(ListaObj<EventoConsultaDTO> listaObj) {
        List<EventoConsultaDTO> arrayList = new ArrayList<>();
        for (int i = 0; i < listaObj.getTamanho(); i++) {
            arrayList.add(listaObj.getElemento(i));
        }
        return arrayList;
    }

    public ListaObj<EventoConsultaDTO> toListaObj(List<EventoConsultaDTO> eventoConsultaDTOs) {
        ListaObj<EventoConsultaDTO> listaObj = new ListaObj<>(eventoConsultaDTOs.size());
        for (EventoConsultaDTO eventoConsultaDTO : eventoConsultaDTOs) {
            listaObj.adiciona(eventoConsultaDTO);
        }
        return listaObj;
    }
}
