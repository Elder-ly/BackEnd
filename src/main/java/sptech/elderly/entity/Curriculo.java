package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "resumes")
public class Curriculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")
    private UsuarioEntity usuario;

    @ManyToOne @JoinColumn(name = "specialtie_id")
    private Especialidade especialidade;
}
