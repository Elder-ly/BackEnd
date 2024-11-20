package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.service.MensagemService;
import sptech.elderly.web.dto.mensagem.MensagemInput;
import sptech.elderly.web.dto.mensagem.MensagemOutput;
import sptech.elderly.web.dto.mensagem.UsuarioConversaOutput;
import sptech.elderly.web.dto.mensagem.MensagemComPropostaOutput;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mensagens")
@RequiredArgsConstructor
public class MensagemController {
    private final MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<MensagemOutput> postNovaMensagem(@RequestBody @Valid MensagemInput input) {
        return ResponseEntity.status(201).body(mensagemService.enviarMensagem(input));
    }

    @GetMapping("/{remetenteId}/{destinatarioId}")
    private ResponseEntity<List<MensagemComPropostaOutput>> getMensagensComUsuario(@PathVariable Long remetenteId, @PathVariable Long destinatarioId) {
        return ResponseEntity.of(Optional.ofNullable(mensagemService.buscarMensagensEntreUsuarios(remetenteId, destinatarioId)));
    }

    @GetMapping("/conversas/{userId}")
    public ResponseEntity<List<UsuarioConversaOutput>> getConversas(@PathVariable Long userId) {
        return ResponseEntity.status(200).body(mensagemService.buscarConversas(userId));
    }
}
