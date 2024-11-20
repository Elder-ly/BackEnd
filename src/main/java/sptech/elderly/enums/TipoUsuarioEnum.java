package sptech.elderly.enums;

import lombok.Getter;

@Getter
public enum TipoUsuarioEnum {
    ADM(1, "Administrador"),
    COLABORADOR(2, "Colaborador"),
    CLIENTE(3, "Cliente");

    private final long codigo;
    private final String descricao;

    TipoUsuarioEnum(long codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
