package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Mensagem;
import sptech.elderly.entity.Usuario;
import sptech.elderly.exceptions.RecursoNaoEncontradoException;
import sptech.elderly.repository.MensagemRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.mensagem.MensagemInput;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaOutput;
import sptech.elderly.web.dto.mensagem.MensagemOutput;
import sptech.elderly.web.dto.mensagem.UsuarioConversaOutput;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class MensagemService {
    private final MensagemRepository mensagemRepository;
    private final UsuarioRepository usuarioRepository;
    private final PropostaService propostaService;
    private final ModelMapper mapper;

    public MensagemOutput enviarMensagem(MensagemInput input) {
        if (Objects.equals(input.destinatarioId(), input.remetenteId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Remetende e Destinatário não podem ser iguais");
        }

        Usuario remetente = usuarioRepository.findById(input.remetenteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", input.remetenteId()));
        Usuario destinatario = usuarioRepository.findById(input.destinatarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", input.destinatarioId()));

        Mensagem novaMensagem = new Mensagem();
        novaMensagem.setDataHora(LocalDateTime.now());
        novaMensagem.setRemetente(remetente);
        novaMensagem.setDestinatario(destinatario);
        novaMensagem.setConteudo(input.conteudo());

        novaMensagem = mensagemRepository.save(novaMensagem);

        return mapper.map(novaMensagem, MensagemOutput.class);
    }

    public List<MensagemComPropostaOutput> buscarMensagensEntreUsuarios(Long remetenteId, Long destinatarioId) {
        if (Objects.equals(destinatarioId, remetenteId)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Remetende e Destinatário não podem ser iguais");
        }
        if (!usuarioRepository.existsById(remetenteId) || !usuarioRepository.existsById(destinatarioId)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado");
        }

        List<Mensagem> mensagens = mensagemRepository.findByRemetenteAndDestinatario(remetenteId, destinatarioId);

        if (mensagens.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        return mapper.map(mensagens,
                new TypeToken<List<MensagemComPropostaOutput>>() {}.getType());
    }

    public List<UsuarioConversaOutput> buscarConversas(Long userId) {
        if (!usuarioRepository.existsById(userId)) throw new RecursoNaoEncontradoException("Usuário", userId);

        return mapper.map(usuarioRepository.findConversas(userId),
                new TypeToken<List<UsuarioConversaOutput>>() {}.getType());
    }
}
