package sptech.elderly.service;

import com.google.api.client.util.DateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Mensagem;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.MensagemRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.mensagem.MensagemInput;
import sptech.elderly.web.dto.mensagem.MensagemOutput;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class MensagemService {
    private final MensagemRepository mensagemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper mapper;

    public MensagemOutput enviarMensagem(MensagemInput input) {
        if (Objects.equals(input.destinatarioId(), input.remetenteId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Remetende e Destinatário não podem ser iguais");
        }
        if (!usuarioRepository.existsById(input.remetenteId()) || !usuarioRepository.existsById(input.destinatarioId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado");
        }
        Mensagem novaMensagem = new Mensagem();
        novaMensagem.setDataHora(LocalDateTime.now());
        novaMensagem.setRemetente(usuarioRepository.findById(input.remetenteId()).get());
        novaMensagem.setDestinatario(usuarioRepository.findById(input.destinatarioId()).get());
        novaMensagem.setConteudo(input.conteudo());

        return mapper.map(mensagemRepository.save(novaMensagem), MensagemOutput.class);
    }

    public List<MensagemOutput> buscarMensagensEntreUsuarios(Integer remetenteId, Integer destinatarioId) {
        if (Objects.equals(destinatarioId, remetenteId)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Remetende e Destinatário não podem ser iguais");
        }
        if (!usuarioRepository.existsById(remetenteId) || !usuarioRepository.existsById(destinatarioId)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado");
        }

        List<Mensagem> mensagens = mensagemRepository.findByRemetenteAndDestinatario(remetenteId, destinatarioId);

        if (mensagens.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        return mapper.map(mensagens,
                new TypeToken<List<MensagemOutput>>() {}.getType());
    }
}
