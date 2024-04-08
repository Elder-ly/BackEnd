package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "proposals")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String conteudo;

    @Column(name = "date_time")
    private LocalDateTime dataHora;

    @Column(name = "day_start_time")
    private LocalDateTime dataHoraInicio;

    @Column(name = "day_time_end")
    private LocalDateTime dataHoraFim;

    @Column(name = "price")
    private BigDecimal preco;

    @ManyToOne @JoinColumn(name = "recipient_id")
    private UsuarioEntity destinatario;

    @ManyToOne @JoinColumn(name = "sender_id")
    private UsuarioEntity remetente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta mensagem = (Proposta) o;
        return Objects.equals(id, mensagem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
