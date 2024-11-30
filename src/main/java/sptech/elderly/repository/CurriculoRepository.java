package sptech.elderly.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import sptech.elderly.entity.Curriculo;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.entity.Usuario;

public interface CurriculoRepository extends JpaRepository<Curriculo, Long> {
    @Transactional
    void deleteByUsuarioId(Long usuarioId);

    Curriculo findByUsuarioAndEspecialidade(Usuario usuario, Especialidade especialidade);

    @Transactional
    void deleteByEspecialidadeId(Long especialidadeId);
}