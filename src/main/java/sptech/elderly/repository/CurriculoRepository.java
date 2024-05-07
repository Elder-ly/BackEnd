package sptech.elderly.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sptech.elderly.entity.Curriculo;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.entity.UsuarioEntity;

import java.util.Optional;

public interface CurriculoRepository extends JpaRepository<Curriculo, Integer> {
    Curriculo findByEspecialidade(Especialidade especialidade);

    Curriculo findByUsuarioAndEspecialidade(UsuarioEntity usuario, Especialidade especialidade);

    Curriculo findByUsuario(UsuarioEntity usuario);

    @Transactional @Modifying
    @Query("UPDATE Curriculo c SET c.usuario = ?1 WHERE c.especialidade = ?2")
    void atualizarCurriculo(UsuarioEntity usuario, Especialidade especialidade);
}