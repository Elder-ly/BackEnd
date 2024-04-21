package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.*;
import sptech.elderly.repository.GeneroRepository;
import sptech.elderly.repository.TipoUsuarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.usuario.CriarFuncionario;
import sptech.elderly.web.dto.usuario.CriarCliente;
import sptech.elderly.web.dto.usuario.UsuarioConsultaDto;
import sptech.elderly.web.dto.usuario.UsuarioMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final GeneroRepository generoRepository;

    private final TipoUsuarioRepository tipoUsuarioRepository;

    private final ResidenciaService residenciaService;

    private final EnderecoService enderecoService;

    private final EspecialidadeService especialidadeService;

    private final CurriculoService curriculoService;

    public UsuarioEntity salvarCliente(CriarCliente novoCliente) {
        if (usuarioRepository.existsByEmail(novoCliente.novoUsuario().email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoCliente.novoUsuario().tipoUsuario())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(novoCliente.novoEndereco());

        UsuarioEntity novoUsuario = UsuarioMapper.ofClienteEntity(novoCliente.novoUsuario(), tipoUsuarioId);
        novoUsuario = usuarioRepository.save(novoUsuario);

        residenciaService.salvar(novoUsuario, endereco);

        return novoUsuario;
    }

    public UsuarioEntity salvarFuncionario(CriarFuncionario novoFuncionario) {
        if (usuarioRepository.existsByEmail(novoFuncionario.novoUsuario().email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoFuncionario.novoUsuario().tipoUsuario())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Tipo usuário não encontrado.")
                );

        Genero generoId = generoRepository.findById(novoFuncionario.novoUsuario().tipoGenero())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Gênero não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(novoFuncionario.novoEndereco());

        UsuarioEntity novoUsuario = UsuarioMapper.ofFuncionarioEntity(novoFuncionario.novoUsuario(), tipoUsuarioId, generoId);
        novoUsuario = usuarioRepository.save(novoUsuario);

        List<Especialidade> especialidades = especialidadeService.salvar(novoFuncionario.especialidades());
        for (Especialidade especialidade : especialidades) {
            curriculoService.associarEspecialidadeUsuario(novoUsuario, especialidade);
        }

        residenciaService.salvar(novoUsuario, endereco);
        return novoUsuario;
    }

    @Transactional(readOnly = true)
    public UsuarioConsultaDto buscarPorId(Integer userId) {
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado.")
        );

        return UsuarioMapper.toDto(usuario);
    }

    public List<UsuarioEntity> buscarUsuarios() {
        // Buscar todos os usuários
        List<UsuarioEntity> todosUsuarios = usuarioRepository.findAll();

        // Usar um HashSet para remover duplicações
        Set<UsuarioEntity> usuariosSemDuplicacao = new HashSet<>(todosUsuarios);

        // Converter o Set de volta para uma lista e retornar
        return usuariosSemDuplicacao.stream().collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioEntity buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado")
        );
    }
}
