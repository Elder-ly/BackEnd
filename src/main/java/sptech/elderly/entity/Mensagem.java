package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
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
}
