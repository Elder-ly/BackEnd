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
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;
import sptech.elderly.web.dto.usuario.CriarClienteInput;
import sptech.elderly.web.dto.usuario.CriarUsuarioEndereco;
import sptech.elderly.web.dto.usuario.UsuarioMapper;

import java.util.List;

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

    public UsuarioEntity salvarCliente(CriarUsuarioEndereco novoUser) {
        if (usuarioRepository.existsByEmail(novoUser.novoUsuario().email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoUser.novoUsuario().getTipoUsuarioId())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(novoUser.novoEndereco());

        UsuarioEntity novoUsuario = UsuarioMapper.ofCliente(novoUser.novoUsuario(), tipoUsuarioId);
        novoUsuario = usuarioRepository.save(novoUsuario);

        residenciaService.salvar(novoUsuario, endereco);

        return novoUsuario;
    }

    public UsuarioEntity salvarFuncionario(CriarClienteInput novoUser, CriarEnderecoInput novoEndereco) {
        Endereco endereco = enderecoService.salvar(novoEndereco);
        UsuarioEntity novoUsuario;

        if (usuarioRepository.existsByEmail(novoUser.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoUser.getTipoUsuarioId())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );


        Genero generoId = generoRepository.findById(novoUser.getGeneroId())
                .orElseThrow(
                        () -> new RuntimeException("Gênero não encontrado.")
                );

        novoUsuario = UsuarioMapper.ofFuncionario(novoUser, tipoUsuarioId, generoId);
        usuarioRepository.save(novoUsuario);

        return this.usuarioRepository.save(novoUsuario);
    }

    @Transactional(readOnly = true)
    public UsuarioEntity buscarPorId(Integer userId) {
        return usuarioRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado.")
        );
    }

    public List<UsuarioEntity> buscarUsuarios() {
        return usuarioRepository.findAll();
    }
}
