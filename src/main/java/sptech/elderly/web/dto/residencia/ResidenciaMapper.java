package sptech.elderly.web.dto.residencia;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sptech.elderly.entity.Endereco;
import sptech.elderly.entity.Residencia;
import sptech.elderly.entity.UsuarioEntity;

@Getter @Setter @NoArgsConstructor
public class ResidenciaMapper {

    public static Residencia of(CriarResidenciaInput novaResidencia, UsuarioEntity usuario, Endereco endereco){
        Residencia residencia = new Residencia();

        residencia.setUsuario(usuario);
        residencia.setEndereco(endereco);

        return residencia;
    }
}
