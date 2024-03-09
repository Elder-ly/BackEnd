package sptech.elderly.entity;

//import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
//@Entity @Table(name = "/especialidades")
public class Especialidade {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    @NotNull
    private Long id;

//    @Column(name = "nome")
    @NotBlank
    private String nome;
}
