package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity @Table(name = "calendars")
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "calendar_id")
    private String calendarId;

    @Column(name = "type")
    private String tipo;

    @ManyToOne @JoinColumn(name = "user_id")
    private UsuarioEntity usuario;
}
