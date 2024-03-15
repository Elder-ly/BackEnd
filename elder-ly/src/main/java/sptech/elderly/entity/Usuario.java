package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @JoinColumn(name = "gender_id") @ManyToOne
    private Genero generos;

    @OneToMany(mappedBy = "usuario")
    private List<Curriculo> curriculos;

    @OneToMany
    private Mensagem mensagem;
}
