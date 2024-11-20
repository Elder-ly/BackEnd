package sptech.elderly.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Lucro;
import sptech.elderly.repository.LucroRepository;
import sptech.elderly.web.dto.lucro.AtualizarLucroInput;
import sptech.elderly.web.dto.lucro.CriarLucroInput;
import sptech.elderly.web.dto.lucro.LucroMapper;
import sptech.elderly.web.dto.lucro.LucroOutput;
import sptech.elderly.web.dto.usuario.UsuarioConsultaDto;

import java.util.List;

@Service @RequiredArgsConstructor
public class LucroService {

    private final LucroRepository lucroRepository;

    private final UsuarioService usuarioService;

    private final LucroMapper lucroMapper;

    public Lucro salvarLucro(CriarLucroInput input) {
        Lucro lucro = new Lucro();
        UsuarioConsultaDto usuario = usuarioService.buscarUsuarioId(input.usuario());

        if (usuario != null){
            lucro = lucroRepository.save(lucroMapper.mapearEntidade(input));
        }

        return lucro;
    }

    @Transactional
    public Lucro atualizarLucro(Long id, AtualizarLucroInput input){
        Lucro lucro = buscarLucro(id);

        lucro.setId(id);
        lucro.setLucro(input.lucro());
        return lucroRepository.save(lucro);
    }

    public Lucro buscarLucro(Long codigo) {
        return lucroRepository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Lucro não encontrado"));
    }

    public List<Lucro> findLucros() {
        return lucroRepository.findAll();
    }

    @Transactional
    public void deletarLucro(Long codigo) {
        lucroRepository.deleteById(buscarLucro(codigo).getId());
    }
}
