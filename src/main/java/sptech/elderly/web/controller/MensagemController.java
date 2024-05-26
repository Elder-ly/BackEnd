package sptech.elderly.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sptech.elderly.web.dto.mensagem.ConversaOutput;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {
    @GetMapping("/conversas")
    public List<ConversaOutput> getConversas(@RequestParam Integer userId) {
        return null;
    }
}
