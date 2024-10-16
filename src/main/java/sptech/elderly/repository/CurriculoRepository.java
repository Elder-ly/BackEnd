package sptech.elderly.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sptech.elderly.entity.Curriculo;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.entity.Usuario;

public interface CurriculoRepository extends JpaRepository<Curriculo, Long> {
    @Transactional
    void deleteByUsuarioId(Long usuarioId);

    Curriculo findByUsuarioAndEspecialidade(Usuario usuario, Especialidade especialidade);

    Curriculo findByUsuario(Usuario usuario);

    @Transactional @Modifying
    @Query("UPDATE Curriculo c SET c.usuario = ?1 WHERE c.especialidade = ?2")
    void atualizarCurriculo(Usuario usuario, Especialidade especialidade);
}