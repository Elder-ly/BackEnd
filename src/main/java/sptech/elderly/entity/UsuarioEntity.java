package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "users")
public class UsuarioEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "password", nullable = false, length = 8)
    private String senha;

    @Column(name = "document", nullable = false, length = 14)
    private String documento;

    @ManyToOne @JoinColumn(name = "gender_id", nullable = false)
    private Genero genero;

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
