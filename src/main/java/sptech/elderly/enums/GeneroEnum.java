package sptech.elderly.enums;

import lombok.Getter;

@Getter
public enum GeneroEnum {

    MASCULINO(1, "Masculino"),
    FEMININO(2, "Feminino"),
    PREFIRO_NAO_INFORMAR(3, "Prefiro não informar");

    private final int codigo;
    private final String descricao;

    GeneroEnum(int codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
