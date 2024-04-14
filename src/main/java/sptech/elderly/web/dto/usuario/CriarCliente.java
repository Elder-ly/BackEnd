package sptech.elderly.web.dto.usuario;

import sptech.elderly.web.dto.endereco.CriarEnderecoInput;

public record CriarCliente(CriarUsuarioInput novoUsuario,
                           CriarEnderecoInput novoEndereco) {
}
