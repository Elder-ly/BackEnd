package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity @Table(name = "resumes")
public class Curriculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne @JoinColumn(name = "user_id", nullable = true)
    private UsuarioEntity usuario;

    @ManyToOne @JoinColumn(name = "specialtie_id")
    private Especialidade especialidade;
}
