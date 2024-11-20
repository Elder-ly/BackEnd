package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.Residencia;

public interface ResidenciaRepository extends JpaRepository<Residencia, Long> {
}