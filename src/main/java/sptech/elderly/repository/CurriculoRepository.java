package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.Curriculo;
import sptech.elderly.entity.Especialidade;

public interface CurriculoRepository extends JpaRepository<Curriculo, Integer> {
    Curriculo findByEspecialidade(Especialidade especialidade);
}