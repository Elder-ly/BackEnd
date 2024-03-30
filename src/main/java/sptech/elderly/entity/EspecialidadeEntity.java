package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "specialites")
public class EspecialidadeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String nome;

    @OneToMany(mappedBy = "especialidadeEntity")
    private List<Curriculo> curriculos;
}
