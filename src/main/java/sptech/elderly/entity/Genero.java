package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
@NoArgsConstructor
@Entity @Table(name = "tb_genders", schema = "elder_ly")
@SequenceGenerator(name = "sq_co_gender", sequenceName = "seq_co_gender", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sq_co_gender", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_gender")
    private Long id;

    @Column(name = "name")
    private String nome;
}
