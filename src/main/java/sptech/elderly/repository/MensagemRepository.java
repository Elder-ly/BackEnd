package sptech.elderly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sptech.elderly.entity.Mensagem;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
    @Query("SELECT m FROM Mensagem m WHERE (m.remetente.id = ?1 AND m.destinatario.id = ?2) OR (m.remetente.id = ?2 AND m.destinatario.id = ?1) ORDER BY m.dataHora")
    List<Mensagem> findByRemetenteAndDestinatario(Integer remetenteId, Integer destinatarioId);
}
