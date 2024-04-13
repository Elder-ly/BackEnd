package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Genero;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.GeneroRepository;
import sptech.elderly.repository.TipoUsuarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.usuario.CriarUsuarioInput;
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

    public UsuarioEntity salvar(CriarUsuarioInput novoUser) {
        UsuarioEntity novoUsuario;

        if (usuarioRepository.existsByEmail(novoUser.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoUser.getTipoUsuarioId())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        if (novoUser.getGeneroId() != null) {
            Genero generoId = generoRepository.findById(novoUser.getGeneroId())
                    .orElseThrow(
                            () -> new RuntimeException("Gênero não encontrado.")
                    );

            novoUsuario = UsuarioMapper.of(novoUser, tipoUsuarioId, generoId);
        } else {
            novoUsuario = UsuarioMapper.of(novoUser, tipoUsuarioId);
        }

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
