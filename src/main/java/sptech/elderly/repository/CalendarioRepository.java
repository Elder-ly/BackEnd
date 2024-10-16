package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.Calendario;
import sptech.elderly.entity.Usuario;

import java.util.List;

public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
    List<Calendario> findByUsuarioIn(List<Usuario> usuarios);

    boolean existsByUsuario(Usuario usuario);
}
