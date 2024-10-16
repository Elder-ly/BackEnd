package sptech.elderly.service;

import com.google.api.client.util.DateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Mensagem;
import sptech.elderly.entity.Proposta;
import sptech.elderly.entity.Usuario;
import sptech.elderly.enums.TipoUsuarioEnum;
import sptech.elderly.exceptions.ListEmptyException;
import sptech.elderly.exceptions.RecursoNaoEncontradoException;
import sptech.elderly.repository.MensagemRepository;
import sptech.elderly.repository.PropostaRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaInput;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaOutput;
import sptech.elderly.web.dto.proposta.PropostaInput;
import sptech.elderly.web.dto.proposta.PropostaMapper;
import sptech.elderly.web.dto.proposta.PropostaOutput;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PropostaService {
    private final PropostaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final MensagemRepository mensagemRepository;
    private final GoogleCalendarService googleCalendarService;
    private final ModelMapper mapper;
    private final PropostaMapper propostaMapper;

    public MensagemComPropostaOutput enviarProposta(MensagemComPropostaInput input) {
        if (Objects.equals(input.destinatarioId(), input.remetenteId())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Remetende e Destinatário não podem ser iguais");
        }

        Usuario remetente = usuarioRepository.findById(input.remetenteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", input.remetenteId()));
        Usuario destinatario = usuarioRepository.findById(input.destinatarioId())
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
        return repository.save(proposta);
    }

    public PropostaOutput aceitarProposta(String accessToken, Long idProposta) throws GeneralSecurityException, IOException {
        Proposta proposta = repository.findById(idProposta)
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

        return mapper.map(repository.save(proposta), PropostaOutput.class);
    }

    public List<PropostaOutput> buscarPropostaMes(LocalDateTime dtInicio, LocalDateTime dtFim) {
        if (dtFim == null){
            dtFim = LocalDateTime.now();
        }

        List<Proposta> propostas = repository.findByDataHoraInicioBetween(dtInicio, dtFim);

        if (propostas.isEmpty()){
            throw new ListEmptyException();
        }

        return propostaMapper.toOutput(propostas);
    }

    public List<PropostaOutput> getPropostas() {
        List<Proposta> propostas = repository.findAll();

        if (propostas.isEmpty()){
            throw new ListEmptyException();
        }

        return propostaMapper.toOutput(propostas);
    }

    // Métodos utilizados na Dashboard
    public Map<String, BigDecimal> getFaturamentoAnual(int ano) {
        List<Object[]> resultados = repository.calcularFaturamentoPorMes(ano);

        Map<String, BigDecimal> faturamentoPorMes = new HashMap<>();
        String[] meses = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};

        for (Object[] resultado : resultados) {
            Integer mes = (Integer) resultado[0];
            BigDecimal total = (BigDecimal) resultado[1];
            faturamentoPorMes.put(meses[mes - 1], total);  // Meses em inglês
        }

        return faturamentoPorMes;
    }
}
