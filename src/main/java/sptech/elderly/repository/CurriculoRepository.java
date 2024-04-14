package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.Curriculo;

public interface CurriculoRepository extends JpaRepository<Curriculo, Integer> {
}