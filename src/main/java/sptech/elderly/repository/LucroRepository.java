package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.elderly.entity.Lucro;
import sptech.elderly.entity.Usuario;

import java.math.BigDecimal;

public interface LucroRepository extends JpaRepository<Lucro, Long> {

    @Query("SELECT COALESCE(l.lucro, 0) FROM Lucro l")
    BigDecimal getLucroEsperado();

    boolean existsByUsuario(Usuario usuario);
}