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
import sptech.elderly.validacao.DocumentoCnpj;
import sptech.elderly.validacao.DocumentoCpf;
import sptech.elderly.web.dto.usuario.UsuarioCreateDto;

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

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoUser.getTipoUsuarioId())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        usuario.setNome(novoUser.nome());
        usuario.setEmail(novoUser.email());
        usuario.setSenha(novoUser.senha());
        usuario.setTipoUsuario(tipoUsuarioId);

        if (novoUser.documento().length() == 14){
            DocumentoCpf.cpf = novoUser.documento();
            usuario.setDocumento(DocumentoCpf.cpf);
        } else if (novoUser.documento().length() == 18){
            DocumentoCnpj.cnpj = novoUser.documento();
            usuario.setDocumento(DocumentoCnpj.cnpj);
        }

        if (novoUser.getGeneroId() != null) {
            Genero generoId = generoRepository.findById(novoUser.getGeneroId())
                    .orElseThrow(
                            () -> new RuntimeException("Genero não encontrado.")
                    );

            usuario.setGenero(generoId);
        }

        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioEntity buscarPorId(Integer userId) {
        return usuarioRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado.")
        );
    }
}
