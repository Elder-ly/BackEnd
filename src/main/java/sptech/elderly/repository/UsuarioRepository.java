package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.UsuarioEntity;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    boolean existsByEmail(String email);
    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByDocumento(String documento);
}
