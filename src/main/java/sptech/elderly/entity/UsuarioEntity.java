package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity @Table(name = "users")
public class UsuarioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "document", unique = true)
    private String documento;

    @ManyToOne @JoinColumn(name = "user_type_id", referencedColumnName = "id")
    private TipoUsuario tipoUsuario;

    @ManyToOne @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Genero genero;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Curriculo> curriculos;
}