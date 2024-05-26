package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.elderly.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Boolean existsByEmail(String email);
    Optional<UsuarioEntity> findByEmail(String email);

    Boolean existsByDocumento(String documento);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.id IN (SELECT c.usuario.id FROM Curriculo c WHERE c.especialidade.nome IN ?1 GROUP BY c.usuario.id)")
    List<UsuarioEntity> findByEspecialidades(List<String> especialidades);
}
