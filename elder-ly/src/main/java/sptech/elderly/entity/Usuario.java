package sptech.elderly.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "users")
public class Usuario{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String senha;

    @Column(name = "document", nullable = false)
    private String documento;

    @ManyToOne @JoinColumn(name = "gender_id")
    private Genero genero;

//    @ManyToOne @JoinColumn(name = "user_type_id")
//    private TipoUsuario tipoUsuario;
}
