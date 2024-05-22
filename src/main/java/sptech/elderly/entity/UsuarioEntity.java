package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
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

    @Column(name = "birth_date")
    private LocalDate dataNascimento;

    @Column(name = "biography")
    private String biografia;

    @Column(name = "profile_picture")
    private String fotoPerfil;

    @ManyToOne @JoinColumn(name = "user_type_id", referencedColumnName = "id")
    private TipoUsuario tipoUsuario;

    @ManyToOne @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Genero genero;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Residencia> residencias;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Curriculo> curriculos;
}