package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity @Table(name = "proposals")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private String descricao;

    @Column(name = "day_start_time")
    private LocalDateTime dataHoraInicio;

    @Column(name = "day_time_end")
    private LocalDateTime dataHoraFim;

    @Column(name = "price")
    private BigDecimal preco;

    @Column(name = "accepted")
    private Boolean aceita;

    @OneToOne @JoinColumn(name = "message_id")
    private Mensagem mensagem;
}
