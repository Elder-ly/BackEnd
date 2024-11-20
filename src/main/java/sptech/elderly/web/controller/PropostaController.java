package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.service.MensagemService;
import sptech.elderly.service.PropostaService;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaInput;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaOutput;
import sptech.elderly.web.dto.proposta.PropostaOutput;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/propostas")
@RequiredArgsConstructor
public class PropostaController {
    private final MensagemService mensagemService;
    private final PropostaService service;

    // Identifica a postNovaMensagem (MensagemController)
    @PostMapping
    public ResponseEntity<MensagemComPropostaOutput> postNovaProposta(@RequestBody @Valid MensagemComPropostaInput input) {
        return status(201).body(service.enviarProposta(input));
    }

    @PatchMapping("/aceitar/{idProposta}")
    public ResponseEntity<PropostaOutput> aceitarProposta(@RequestHeader String accessToken,
                                                          @PathVariable Long idProposta) throws GeneralSecurityException, IOException {
        return status(200).body(service.aceitarProposta(accessToken, idProposta));
    }

    @GetMapping("/mes")
    public ResponseEntity<List<PropostaOutput>> getPropostasMes(LocalDateTime dtInicio,
                                                                @RequestParam(value = "dtFim", required = false) LocalDateTime dtFim){
        return status(200).body(service.buscarPropostaMes(dtInicio, dtFim));
    }

    @GetMapping()
    public ResponseEntity<List<PropostaOutput>> getPropostas(){
        return status(200).body(service.getPropostas()) ;
    }

    @GetMapping("/faturamento/{ano}")
    public ResponseEntity<Map<String, BigDecimal>> getFaturamentoPorAno(@PathVariable Integer ano) {
        Map<String, BigDecimal> faturamento = service.getFaturamentoAnual(ano);
        return ResponseEntity.ok(faturamento);
    }

    @GetMapping("/aceite/{ano}")
    public ResponseEntity<Map<String, List<Integer>>> getAceitesPropostas(@PathVariable Integer ano) {
        Map<String, List<Integer>> response = service.getPropostasAceitasERecusadas(ano);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/faturamento-absoluto/{mes}/{ano}")
    public ResponseEntity<?> getFaturamentoAbsolutoMensal(@PathVariable String mes, @PathVariable String ano) {
        BigDecimal faturamento = service.getFaturamentoAbsolutoMensal(mes, ano);
        return ResponseEntity.ok(Map.of("faturamento", faturamento.toString()));
    }

    /*
    @GetMapping("/lucro/{mes}/{ano}")
    public ResponseEntity<?> getLucroMensal(@PathVariable String mes, @PathVariable String ano){
        return null;
    }*/

    @GetMapping("/faturamento-diario/{mes}/{ano}")
    public ResponseEntity<Map<String, BigDecimal>> getFaturamentoDiarioMensal(@PathVariable String mes, @PathVariable String ano) {
        Map<String, BigDecimal> faturamento = service.getFaturamentoDiarioMensal(mes, ano);
        return ResponseEntity.ok(faturamento);
    }
}