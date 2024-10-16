package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.Especialidade;

public interface EspecialidadeRepository extends
        JpaRepository<Especialidade, Long> {
}