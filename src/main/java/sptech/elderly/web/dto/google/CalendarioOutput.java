package sptech.elderly.web.dto.google;

import sptech.elderly.web.dto.usuario.UsuarioConsultaCalendario;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.Calendario}
 */
public record CalendarioOutput(Integer id,
                               String calendarId,
                               UsuarioConsultaCalendario usuario) implements Serializable {
}