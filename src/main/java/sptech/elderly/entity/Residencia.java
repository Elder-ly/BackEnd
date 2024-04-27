package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Entity @Table(name = "residences")
public class Residencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne @JoinColumn(name = "user_id")
    private UsuarioEntity usuario;

    @ManyToOne @JoinColumn(name = "adresse_id")
    private Endereco endereco;

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
