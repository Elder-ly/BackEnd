package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.elderly.entity.Genero;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.GeneroRepository;
import sptech.elderly.repository.TipoUsuarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.UsuarioCreateDto;

@Service @RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public void salvar(UsuarioCreateDto novoUser) {
        UsuarioEntity usuario = new UsuarioEntity();

        Genero generoId = generoRepository.findById(novoUser.getGeneroId())
                .orElseThrow(
                        () -> new RuntimeException("Genero não encontrado.")
                );

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoUser.getTipoUsuarioId())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        usuario.setNome(novoUser.nome());
        usuario.setEmail(novoUser.email());
        usuario.setSenha(novoUser.senha());
        usuario.setDocumento(novoUser.documento());
        usuario.setGenero(generoId);
        usuario.setTipoUsuario(tipoUsuarioId);

        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioEntity buscarPorId(Integer userId) {
        return usuarioRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado.")
        );
    }
}
