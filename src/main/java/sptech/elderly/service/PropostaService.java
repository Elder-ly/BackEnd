package sptech.elderly.service;

import com.google.api.client.util.DateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Mensagem;
import sptech.elderly.entity.Proposta;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.enums.TipoUsuarioEnum;
import sptech.elderly.exceptions.RecursoNaoEncontradoException;
import sptech.elderly.repository.MensagemRepository;
import sptech.elderly.repository.PropostaRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.google.EventoConsultaDTO;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaInput;
import sptech.elderly.web.dto.mensagem.MensagemInput;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaOutput;
import sptech.elderly.web.dto.proposta.PropostaInput;
import sptech.elderly.web.dto.proposta.PropostaOutput;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class PropostaService {
    private final PropostaRepository propostaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MensagemRepository mensagemRepository;
    private final GoogleCalendarService googleCalendarService;
    private final ModelMapper mapper;

    public MensagemComPropostaOutput enviarProposta(MensagemComPropostaInput input) {
        if (Objects.equals(input.destinatarioId(), input.remetenteId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Remetende e Destinatário não podem ser iguais");
        }

        UsuarioEntity remetente = usuarioRepository.findById(input.remetenteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", input.remetenteId()));
        UsuarioEntity destinatario = usuarioRepository.findById(input.destinatarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", input.destinatarioId()));

        if (remetente.getTipoUsuario().getId() == TipoUsuarioEnum.CLIENTE.getCodigo()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "O tipo de usuário não pode criar propostas.");
        }

        Mensagem novaMensagem = new Mensagem();
        novaMensagem.setDataHora(LocalDateTime.now());
        novaMensagem.setRemetente(remetente);
        novaMensagem.setDestinatario(destinatario);
        novaMensagem.setConteudo(input.conteudo());

        novaMensagem = mensagemRepository.save(novaMensagem);

        novaMensagem.setProposta(enviarProposta(input.proposta(), novaMensagem));

        return mapper.map(novaMensagem, MensagemComPropostaOutput.class);
    }

    public Proposta enviarProposta(PropostaInput input, Mensagem mensagem) {
        Proposta proposta = new Proposta();
        proposta.setDescricao(input.descricao());
        proposta.setDataHoraInicio(input.dataHoraInicio());
        proposta.setDataHoraFim(input.dataHoraFim());
        proposta.setPreco(input.preco());
        proposta.setAceita(false);
        proposta.setMensagem(mensagem);
        return propostaRepository.save(proposta);
    }

    public PropostaOutput aceitarProposta(String accessToken, Integer idProposta) throws GeneralSecurityException, IOException {
        Proposta proposta = propostaRepository.findById(idProposta)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Proposta", idProposta));

        proposta.setAceita(true);

        googleCalendarService.inserirEvento(
                accessToken,
                proposta.getMensagem().getConteudo(),
                proposta.getMensagem().getDestinatario().getEmail(),
                proposta.getMensagem().getRemetente().getEmail(),
                new DateTime(proposta.getDataHoraInicio().toInstant(ZoneOffset.of("-03:00")).toEpochMilli()),
                new DateTime(proposta.getDataHoraFim().toInstant(ZoneOffset.of("-03:00")).toEpochMilli()),
                null,
                proposta.getDescricao());

        return mapper.map(propostaRepository.save(proposta), PropostaOutput.class);
    }
}
