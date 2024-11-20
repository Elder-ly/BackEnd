package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "tb_profit", schema = "elder_ly")
@SequenceGenerator(name = "sq_co_profit", sequenceName = "seq_co_profit", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Lucro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sq_co_profit", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_profit")
    private Long id;

    @Column(name = "profit")
    private BigDecimal lucro;

    @ManyToOne @JoinColumn(name = "user_id")
    private Usuario usuario;
}
