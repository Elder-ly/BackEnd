package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.service.MensagemService;
import sptech.elderly.service.PropostaService;
import sptech.elderly.web.dto.google.EventoConsultaDTO;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaInput;
import sptech.elderly.web.dto.mensagem.MensagemInput;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaOutput;
import sptech.elderly.web.dto.proposta.PropostaOutput;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/propostas")
@RequiredArgsConstructor
public class PropostaController {
    private final MensagemService mensagemService;
    private final PropostaService propostaService;

    // Identica a postNovaMensagem (MensagemController)
    @PostMapping
    public ResponseEntity<MensagemComPropostaOutput> postNovaProposta(@RequestBody @Valid MensagemComPropostaInput input) {
        return ResponseEntity.status(201).body(propostaService.enviarProposta(input));
    }

    @PatchMapping("/aceitar/{idProposta}")
    public ResponseEntity<PropostaOutput> aceitarProposta(@RequestHeader String accessToken,
                                                          @PathVariable Long idProposta) throws GeneralSecurityException, IOException {
        return ResponseEntity.status(200).body(propostaService.aceitarProposta(accessToken, idProposta));
    }
}
