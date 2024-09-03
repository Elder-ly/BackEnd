package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity @Table(name = "tb_adresses", schema = "elder_ly")
@SequenceGenerator(name = "sq_co_adresse", sequenceName = "seq_co_adresse", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sq_co_adresse", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_adresse")
    private Long id;

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