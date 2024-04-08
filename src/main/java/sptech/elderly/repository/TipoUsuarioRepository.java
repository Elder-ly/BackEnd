package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {
}