package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.elderly.entity.Proposta;

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

}