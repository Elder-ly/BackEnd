package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@Entity @Table(name = "tb_calendars", schema = "elder_ly")
@SequenceGenerator(name = "sq_co_calendar", sequenceName = "seq_co_calendar", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Calendario implements Serializable {
    /**
    *
    * */

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sq_co_calendar", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_calendar")
    private Long id;

    @Column(name = "calendar_id")
    private String calendarId;

    @Column(name = "type")
    private String tipo;

    @ManyToOne @JoinColumn(name = "user_id")
    private Usuario usuario;
}
