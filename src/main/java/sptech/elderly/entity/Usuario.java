package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "tb_users", schema = "elder_ly")
@SequenceGenerator(name = "sq_co_user", sequenceName = "seq_co_user", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sq_co_user", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_user")
    private Long id;

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

    @ManyToOne @JoinColumn(name = "user_type_id", referencedColumnName = "co_user_type")
    private TipoUsuario tipoUsuario;

    @ManyToOne @JoinColumn(name = "gender_id", referencedColumnName = "co_gender")
    private Genero genero;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Residencia> residencias;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Curriculo> curriculos;

}