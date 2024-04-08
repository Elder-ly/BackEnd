package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "messages")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String conteudo;

    @Column(name = "date_time")
    private LocalDateTime dataHora;

    @ManyToOne @JoinColumn(name = "recipient_id")
    private UsuarioEntity destinatario;

    @ManyToOne @JoinColumn(name = "sender_id")
    private UsuarioEntity remetente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem mensagem = (Mensagem) o;
        return Objects.equals(id, mensagem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
