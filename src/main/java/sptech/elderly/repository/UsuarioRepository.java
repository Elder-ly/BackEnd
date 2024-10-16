package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.elderly.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);

    Boolean existsByDocumento(String documento);

    @Query("SELECT u FROM Usuario u WHERE u.id IN (SELECT c.usuario.id FROM Curriculo c WHERE c.especialidade.nome IN ?1 GROUP BY c.usuario.id)")
    List<Usuario> findByEspecialidades(List<String> especialidades);

    @Query("SELECT DISTINCT u FROM Usuario u " +
            "WHERE u.id <> ?1 AND (" +
            "EXISTS (SELECT m FROM Mensagem m WHERE m.remetente.id = ?1 AND m.destinatario.id = u.id) OR " +
            "EXISTS (SELECT m FROM Mensagem m WHERE m.remetente.id = u.id AND m.destinatario.id = ?1))")
    List<Usuario> findConversas(Long userId);
}
