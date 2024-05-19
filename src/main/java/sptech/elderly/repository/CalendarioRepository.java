package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.Calendario;
import sptech.elderly.entity.UsuarioEntity;

import java.util.List;

public interface CalendarioRepository extends JpaRepository<Calendario, Integer> {
    List<Calendario> findByUsuarioIn(List<UsuarioEntity> usuarios);
}
