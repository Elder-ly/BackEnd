package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "tb_residences")
@SequenceGenerator(name = "sq_co_residence", sequenceName = "seq_co_residence", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Residencia implements Serializable {

    @Id
    @GeneratedValue(generator = "sq_co_residence", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_residence")
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")
    private Usuario usuario;

    @ManyToOne @JoinColumn(name = "adresse_id")
    private Endereco endereco;
}
