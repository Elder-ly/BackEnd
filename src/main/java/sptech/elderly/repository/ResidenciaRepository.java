package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.elderly.entity.Residencia;

public interface ResidenciaRepository extends JpaRepository<Residencia, Long> {
    @Query("SELECT r.endereco.id FROM Residencia r WHERE r.usuario.id = :codigoUser")
    Long findEnderecoIdByUsuarioId(@Param("codigoUser") Long codigoUser);
}