package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import sptech.elderly.entity.UsuarioEntity;

public class criarUsuario {
    @NotBlank
    private String nome;
    @NotBlank
    private String documento;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;

    public criarUsuario(UsuarioEntity usuario) {
        this.nome = usuario.getNome();
        this.documento = usuario.getDocumento();
        this.email = email;
        this.senha = senha;
    }
}