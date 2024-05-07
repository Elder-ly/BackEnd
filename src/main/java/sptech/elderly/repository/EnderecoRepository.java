package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}