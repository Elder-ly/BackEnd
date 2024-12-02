package sptech.elderly.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.elderly.entity.Calendario;
import sptech.elderly.entity.Usuario;

import java.util.List;

public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
    List<Calendario> findByUsuarioIn(List<Usuario> usuarios);
    boolean existsByUsuario(Usuario usuario);
    @Modifying
    @Transactional
    @Query("DELETE FROM Calendario c WHERE c.usuario.id = :usuarioId")
    void deleteByUsuarioId(@Param("usuarioId") Long usuarioId);
}
