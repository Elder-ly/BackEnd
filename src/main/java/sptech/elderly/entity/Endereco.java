package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity @Table(name = "adresses")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "zip_code")
    private String cep;

    @Column(name = "street")
    private String logradouro;

    @Column(name = "complement")
    private String complemento;

    @Column(name = "neighborhood")
    private String bairro;

    @Column(name = "number")
    private String numero;

    @Column(name = "city")
    private String cidade;

    @Column(name = "uf")
    private String uf;

    @OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL)
    private List<Residencia> residencias;
}