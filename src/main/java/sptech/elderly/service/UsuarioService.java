package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.*;
import sptech.elderly.repository.GeneroRepository;
import sptech.elderly.repository.TipoUsuarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.especialidade.CriarEspecialidadeInput;
import sptech.elderly.web.dto.usuario.CriarFuncionario;
import sptech.elderly.web.dto.usuario.CriarCliente;
import sptech.elderly.web.dto.usuario.UsuarioMapper;

import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private ResidenciaService residenciaService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EspecialidadeService especialidadeService;

    @Autowired
    private CurriculoService curriculoService;

    public UsuarioEntity salvarCliente(CriarCliente novoCliente) {
        if (usuarioRepository.existsByEmail(novoCliente.novoUsuario().email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoCliente.novoUsuario().tipoUsuario())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(novoCliente.novoEndereco());

        UsuarioEntity novoUsuario = UsuarioMapper.ofCliente(novoCliente.novoUsuario(), tipoUsuarioId);
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
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        Genero generoId = generoRepository.findById(novoFuncionario.novoUsuario().tipoGenero())
                .orElseThrow(
                        () -> new RuntimeException("Gênero não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(novoFuncionario.novoEndereco());

        UsuarioEntity novoUsuario = UsuarioMapper.ofFuncionario(novoFuncionario.novoUsuario(), tipoUsuarioId, generoId);
        novoUsuario = usuarioRepository.save(novoUsuario);

        for (CriarEspecialidadeInput especialidadeInput : novoFuncionario.especialidades()){
            Especialidade especialidade = especialidadeService.salvar(especialidadeInput);
            curriculoService.associarEspecialidadeUsuario(novoUsuario, especialidade);
        }

        residenciaService.salvar(novoUsuario, endereco);

        return novoUsuario;
    }

    @Transactional(readOnly = true)
    public UsuarioEntity buscarPorId(Integer userId) {
        return usuarioRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado.")
        );
    }

    public List<UsuarioEntity> buscarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UsuarioEntity buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado")
        );
    }
}
