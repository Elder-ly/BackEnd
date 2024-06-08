package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.Proposta;

public interface PropostaRepository extends JpaRepository<Proposta, Integer> {
}
