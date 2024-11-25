package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.elderly.entity.Proposta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    List<Proposta> findByDataHoraInicioBetween(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim);

    @Query("SELECT MONTH(p.dataHoraInicio), SUM(p.preco) " +
            "FROM Proposta p " +
            "WHERE YEAR(p.dataHoraInicio) = :ano " +
            "AND p.aceita = true " +
            "GROUP BY MONTH(p.dataHoraInicio)")
    List<Object[]> calcularFaturamentoPorMes(@Param("ano") int ano);

    @Query("SELECT SUM(p.preco) " +
            "FROM Proposta p " +
            "WHERE p.aceita = true " +
            "AND MONTH(p.dataHoraInicio) = :mes " +
            "AND YEAR(p.dataHoraInicio) = :ano")
    BigDecimal calcularFaturamentoMensal(@Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT EXTRACT(MONTH FROM p.dataHoraInicio), " +
            "COUNT(p) AS total, " +  // Total de propostas no mês
            "SUM(CASE WHEN p.aceita = TRUE THEN 1 ELSE 0 END) AS aceitas " +  // Propostas aceitas
            "FROM Proposta p " +
            "WHERE EXTRACT(YEAR FROM p.dataHoraInicio) = :ano " +
            "GROUP BY EXTRACT(MONTH FROM p.dataHoraInicio) " +
            "ORDER BY EXTRACT(MONTH FROM p.dataHoraInicio)")
    List<Object[]> countPropostasPorMes(@Param("ano") Integer ano);

    @Query("SELECT EXTRACT(DAY FROM p.dataHoraInicio) AS dia, SUM(p.preco) AS total " +
            "FROM Proposta p " +
            "WHERE p.aceita = TRUE " +
            "AND EXTRACT(MONTH FROM p.dataHoraInicio) = :mes " +
            "AND EXTRACT(YEAR FROM p.dataHoraInicio) = :ano " +
            "GROUP BY EXTRACT(DAY FROM p.dataHoraInicio) ")
    List<Object[]> calcularFaturamentoDiario(@Param("mes") Integer mes, @Param("ano") Integer ano);

    @Query("SELECT COALESCE(SUM(p.preco), 0) " +
            "FROM Proposta p " +
            "WHERE FUNCTION('TO_CHAR', p.dataHoraInicio, 'MM/YYYY') = :mesAno " +
            "AND p.aceita = true")
    BigDecimal getSomaPropostasAceitas(@Param("mesAno") String mesAno);
}