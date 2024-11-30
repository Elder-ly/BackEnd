package sptech.elderly.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Lucro;
import sptech.elderly.entity.Usuario;
import sptech.elderly.repository.LucroRepository;
import sptech.elderly.web.dto.lucro.AtualizarLucroInput;
import sptech.elderly.web.dto.lucro.CriarLucroInput;
import sptech.elderly.web.dto.lucro.LucroMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LucroService {

    private final LucroRepository lucroRepository;

    private final UsuarioService usuarioService;

    private final LucroMapper lucroMapper;

    public Lucro salvarLucro(CriarLucroInput input) {
        Usuario usuario = usuarioService.buscarUsuario(input.usuario());
        Lucro lucro = lucroMapper.mapearEntidade(input);

        if (buscarLucroPorUsuario(usuario) ) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Métrica do usuário ja cadastrado!");
        }

        lucro.setUsuario(usuario);
        return lucroRepository.save(lucro);
    }

    @Transactional
    public Lucro atualizarLucro(Long id, AtualizarLucroInput input) {
        Lucro lucro = buscarLucro(id);

        lucro.setId(id);
        lucro.setLucro(input.lucro());
        return lucroRepository.save(lucro);
    }

    public Lucro buscarLucro(Long codigo) {
        return lucroRepository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Lucro não encontrado"));
    }

    public Boolean buscarLucroPorUsuario(Usuario usuario) {
        return lucroRepository.existsByUsuario(usuario);
    }

    public List<Lucro> findLucros() {
        return lucroRepository.findAll();
    }

    @Transactional
    public void deletarLucro(Long codigo) {
        lucroRepository.deleteById(buscarLucro(codigo).getId());
    }
}
