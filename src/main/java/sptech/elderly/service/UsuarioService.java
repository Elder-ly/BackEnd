package sptech.elderly.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.*;
import sptech.elderly.repository.GeneroRepository;
import sptech.elderly.repository.TipoUsuarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.usuario.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UsuarioService {

//  Atributos Usuarios
    private final UsuarioRepository usuarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    private final ClienteMapper clienteMapper;

//  Atributo Genero
    private final GeneroRepository generoRepository;

//  TipoUsuario
    private final TipoUsuarioRepository tipoUsuarioRepository;

//  Endereço
    private final ResidenciaService residenciaService;

    private final EnderecoService enderecoService;

//  Especialidade
    private final EspecialidadeService especialidadeService;

    private final CurriculoService curriculoService;

    public UsuarioEntity salvarCliente(CriarClienteInput input) {
        if (usuarioRepository.existsByEmail(input.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(input.tipoUsuario())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        Genero generoId = generoRepository.findById(input.genero())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Gênero não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(input.endereco());

        UsuarioEntity novoUsuario = clienteMapper.criarCliente(input);
        novoUsuario.setTipoUsuario(tipoUsuarioId);
        novoUsuario.setGenero(generoId);

        novoUsuario = usuarioRepository.save(novoUsuario);

        residenciaService.salvar(novoUsuario, endereco);

        return novoUsuario;
    }

    public UsuarioEntity salvarFuncionario(CriarFuncionarioInput input) {
        if (usuarioRepository.existsByEmail(input.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(input.tipoUsuario())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Tipo usuário não encontrado.")
                );

        Genero generoId = generoRepository.findById(input.genero())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Gênero não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(input.endereco());

        UsuarioEntity novoUsuario = funcionarioMapper.criarFuncionario(input);
        novoUsuario.setTipoUsuario(tipoUsuarioId);
        novoUsuario.setGenero(generoId);


//        novoUsuario = usuarioRepository.save(novoUsuario);
//
//        List<Especialidade> especialidades = especialidadeService.salvar();
//
//        for (Especialidade especialidade : especialidades) {
//            curriculoService.associarEspecialidadeUsuario(novoUsuario, especialidade);
//        }
//
//        residenciaService.salvar(novoUsuario, endereco);
        return novoUsuario;
    }

    @Transactional
    public UsuarioConsultaDto buscarUsuarioId(Integer userId) {
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado.")
        );

        return UsuarioMapper.toDto(usuario);
    }

    public List<UsuarioEntity> buscarUsuarios() {
        List<UsuarioEntity> todosUsuarios = usuarioRepository.findAll();

        Set<UsuarioEntity> usuariosSemDuplicacao = new HashSet<>(todosUsuarios);

        return usuariosSemDuplicacao.stream().collect(Collectors.toList());
    }

    @Transactional
    public UsuarioEntity buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Email não encontrado")
        );
    }

    public UsuarioEntity atualizarUsuario(Integer id, AtualizarClienteInput input){
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Cliente não encontrado"));

        return usuarioRepository.save(clienteMapper.partialUpdate(input, usuario));
    }

    public void excluirCliente(@PathVariable Integer id){
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Cliente não encontrado"));

        if (usuario.getResidencias() != null){
            usuario.getResidencias()
                    .forEach(residencia -> {
                        usuarioRepository.delete(residencia.getUsuario());
                    });
        }

        usuarioRepository.delete(usuario);
    }
}