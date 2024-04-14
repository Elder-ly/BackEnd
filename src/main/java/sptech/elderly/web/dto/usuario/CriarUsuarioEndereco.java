package sptech.elderly.web.dto.usuario;

import sptech.elderly.web.dto.endereco.CriarEnderecoInput;

public record CriarUsuarioEndereco(CriarClienteInput novoUsuario,
                                   CriarEnderecoInput novoEndereco) {
}
