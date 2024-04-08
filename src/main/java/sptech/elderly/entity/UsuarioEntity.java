package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "users")
public class UsuarioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String senha;

    @Column(name = "document")
    private String documento;

    @ManyToOne @JoinColumn(name = "user_type_id", referencedColumnName = "id")
    private TipoUsuario tipoUsuario;

    @ManyToOne @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Genero genero;

    @OneToMany(mappedBy = "usuario")
    private List<Residencia> residencias;

    @OneToMany(mappedBy = "usuario")
    private List<Curriculo> curriculos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity usuarioEntity = (UsuarioEntity) o;
        return Objects.equals(id, usuarioEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
