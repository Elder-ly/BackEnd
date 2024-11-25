package sptech.elderly.service;

import com.google.api.client.util.DateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Lucro;
import sptech.elderly.entity.Mensagem;
import sptech.elderly.entity.Proposta;
import sptech.elderly.entity.Usuario;
import sptech.elderly.enums.TipoUsuarioEnum;
import sptech.elderly.exceptions.ListEmptyException;
import sptech.elderly.exceptions.RecursoNaoEncontradoException;
import sptech.elderly.repository.LucroRepository;
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
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PropostaService {
    private final PropostaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final MensagemRepository mensagemRepository;
    private final GoogleCalendarService googleCalendarService;
    private final ModelMapper mapper;
    private final PropostaMapper propostaMapper;
    private final LucroRepository lucroRepository;

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

        // Cria um mapa base com todos os meses inicializados com BigDecimal.ZERO
        Map<String, BigDecimal> faturamentoPorMes = new LinkedHashMap<>();
        for (Month mes : Month.values()) {
            faturamentoPorMes.put(mes.name().toLowerCase(), BigDecimal.ZERO);
        }

        // Preenche o mapa com os valores retornados do banco
        for (Object[] resultado : resultados) {
            Integer mesNumero = (Integer) resultado[0];
            BigDecimal total = (BigDecimal) resultado[1];
            String mesNome = Month.of(mesNumero).name().toLowerCase();
            faturamentoPorMes.put(mesNome, total);
        }

        return faturamentoPorMes;
    }

    public BigDecimal getFaturamentoAbsolutoMensal(String mes, String ano) {
        try {
            int mesNumerico = Integer.parseInt(mes);
            int anoNumerico = Integer.parseInt(ano);

            if (mesNumerico < 1 || mesNumerico > 12) {
                throw new IllegalArgumentException("Mês inválido. Deve estar entre 01 e 12.");
            }

            BigDecimal faturamento = repository.calcularFaturamentoMensal(mesNumerico, anoNumerico);
            return faturamento != null ? faturamento : BigDecimal.ZERO;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato do parâmetro 'mes' inválido. Use MM/AAAA.");
        }
    }

    public Map<String, List<Integer>> getPropostasAceitasERecusadas(Integer ano) {
        List<Object[]> resultados = repository.countPropostasPorMes(ano);

        // Cria um mapa base com todos os meses inicializados com [0, 0] (total, aceitas)
        Map<String, List<Integer>> propostasPorMes = new LinkedHashMap<>();
        for (Month mes : Month.values()) {
            propostasPorMes.put(mes.name().toLowerCase(), Arrays.asList(0, 0)); // [total, aceitas]
        }

        // Preenche o mapa com os valores retornados do banco
        for (Object[] resultado : resultados) {
            Integer mesNumero = (Integer) resultado[0]; // Mês (1 a 12)
            Integer total = ((Number) resultado[1]).intValue(); // Total de propostas
            Integer aceitas = ((Number) resultado[2]).intValue(); // Propostas aceitas

            // Mapeia o mês (1 a 12) para o nome do mês em minúsculas
            String mesNome = Month.of(mesNumero).name().toLowerCase();
            propostasPorMes.put(mesNome, Arrays.asList(total, aceitas)); // [total, aceitas]
        }

        return propostasPorMes;
    }

    public Map<String, BigDecimal> getFaturamentoDiarioMensal(String mes, String ano) {
        Integer mesNumerico = Integer.parseInt(mes);
        Integer anoNumerico = Integer.parseInt(ano);

        // Busca os resultados da repository
        List<Object[]> resultados = repository.calcularFaturamentoDiario(mesNumerico, anoNumerico);

        // Obtém o número total de dias no mês
        YearMonth yearMonth = YearMonth.of(anoNumerico, mesNumerico);
        int totalDias = yearMonth.lengthOfMonth();

        // Inicializa o mapa com todos os dias do mês como chaves
        Map<String, BigDecimal> faturamentoPorDia = new LinkedHashMap<>();
        BigDecimal acumulado = BigDecimal.ZERO;

        for (int i = 1; i <= totalDias; i++) {
            String diaFormatado = String.format("%02d/%02d/%d", i, mesNumerico, anoNumerico);

            // Busca o resultado correspondente ao dia atual
            int diaAtual = i; // Variável auxiliar final
            Object[] resultado = resultados.stream()
                    .filter(r -> ((Number) r[0]).intValue() == diaAtual)
                    .findFirst()
                    .orElse(null);

            // Atualiza o acumulado se houver resultado para o dia
            if (resultado != null) {
                BigDecimal total = (BigDecimal) resultado[1];
                acumulado = acumulado.add(total);
            }

            // Define o acumulado no mapa para o dia atual
            faturamentoPorDia.put(diaFormatado, acumulado);
        }

        return faturamentoPorDia;
    }

    public BigDecimal calcularLucroMensal(String mes, String ano) {
        String mesAno = mes + "/" + ano;
        BigDecimal somaPropostas = repository.getSomaPropostasAceitas(mesAno);
        BigDecimal lucroEsperado = lucroRepository.getLucroEsperado();
        return somaPropostas.subtract(lucroEsperado);
    }
}
