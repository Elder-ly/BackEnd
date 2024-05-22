package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.elderly.entity.Especialidade;

import java.util.List;

public interface EspecialidadeRepository extends
        JpaRepository<Especialidade, Integer> {

    @Query("SELECT e FROM Especialidade e WHERE " +
            "(:especialidades IS NULL OR LOWER(e.nome) LIKE LOWER(CONCAT('%', :especialidades, '%')))")
    List<Especialidade> findByNameContaining(@Param("especialidades") String... especialidades);

}