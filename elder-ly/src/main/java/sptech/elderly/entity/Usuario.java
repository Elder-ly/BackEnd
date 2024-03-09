package sptech.elderly.entity;

//import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter @NoArgsConstructor
//@Entity @Table(name = "users")
public class Usuario {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

//    @Column(name = "nome")
    @NotBlank
    private String nome;

//    @Column(name = "email")
    @NotBlank
    private String email;

//    @Column(name = "password")
    @NotBlank
    private String password;

//    @Column(name = "documento")
    @NotBlank @CPF
    private String documento;

//    @Column(name = "")
    @Valid
    private Especialidade especialidade;
}
