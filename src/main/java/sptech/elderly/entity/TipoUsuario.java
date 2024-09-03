package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity @Table(name = "tb_user_types", schema = "elder_ly")
@SequenceGenerator(name = "seq_co_user_type", sequenceName = "seq_co_user_type", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class TipoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "co_user_type")
    private Long id;

    @Column(name = "name")
    private String nome;
}
