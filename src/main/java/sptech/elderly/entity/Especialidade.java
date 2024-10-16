package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "tb_specialties")
@SequenceGenerator(name = "sq_co_specialtie", sequenceName = "seq_co_specialtie", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Especialidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sq_co_specialtie", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_specialtie")
    private Long id;

    @Column(name = "name")
    private String nome;
}