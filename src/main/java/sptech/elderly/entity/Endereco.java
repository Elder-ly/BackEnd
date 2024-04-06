package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "ratings")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "zip_code")
    private String cep;

    @Column(name = "public_place")
    private String logradouro;

    @Column(name = "number")
    private String numeroCasa;

    @Column(name = "complement")
    private String complemento;

    @Column(name = "city")
    private String cidade;

    @Column(name = "uf")
    private String uf;

    @OneToMany
    private List<Residencia> residencias;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}