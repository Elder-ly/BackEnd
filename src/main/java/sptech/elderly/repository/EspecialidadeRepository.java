package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.entity.UsuarioEntity;

import java.util.List;

public interface EspecialidadeRepository extends
        JpaRepository<Especialidade, Integer> {

    @Query("SELECT u FROM UsuarioEntity u JOIN u.especialidades e WHERE e.nome IN :nomesEspecialidades")
    List<UsuarioEntity> findByEspecialidades(@Param("nomesEspecialidades") List<String> nomesEspecialidades);
}