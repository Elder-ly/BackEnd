package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.UsuarioRepository;

@Service @RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioEntity salvar(UsuarioEntity novoUser) {
        return usuarioRepository.save(novoUser);
    }

    @Transactional(readOnly = true)
    public UsuarioEntity buscarPorId(Long userId) {
        return usuarioRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado.")
        );
    }
}
