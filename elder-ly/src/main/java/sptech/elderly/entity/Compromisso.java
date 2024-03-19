package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "appointments")
public class Compromisso {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String descricao;

    @Column(name = "timestamp")
    private LocalDateTime horario;

    @ManyToOne @JoinColumn(name = "caregiver_id")
    private UsuarioEntity funcionario;

    @ManyToOne @JoinColumn(name = "client_id")
    private UsuarioEntity cliente;
}