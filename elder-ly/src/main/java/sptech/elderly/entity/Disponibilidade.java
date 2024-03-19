package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "availabilities")
public class Disponibilidade {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "timestamp")
    private LocalDateTime horario;

    @Column(name = "description")
    private String descricao;

    @ManyToOne @JoinColumn(name = "user_id")
    private UsuarioEntity usuarioEntity;
}
