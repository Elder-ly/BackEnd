package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "messages")
public class MensagemEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String conteudo;

    @Column(name = "timestamp")
    private LocalDateTime horario;

    @ManyToOne @JoinColumn(name = "destination_id")
    private UsuarioEntity destinatario;

    @ManyToOne @JoinColumn(name = "sender_id")
    private UsuarioEntity remetente;
}