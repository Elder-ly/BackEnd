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

    @Query("SELECT DISTINCT u FROM UsuarioEntity u " +
            "WHERE u.id <> ?1 AND (" +
            "EXISTS (SELECT m FROM Mensagem m WHERE m.remetente.id = ?1 AND m.destinatario.id = u.id) OR " +
            "EXISTS (SELECT m FROM Mensagem m WHERE m.remetente.id = u.id AND m.destinatario.id = ?1))")
    List<UsuarioEntity> findConversas(Integer userId);
}
