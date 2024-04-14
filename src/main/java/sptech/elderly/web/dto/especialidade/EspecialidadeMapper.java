package sptech.elderly.web.dto.especialidade;

import sptech.elderly.entity.Especialidade;

import java.util.List;

public class EspecialidadeMapper {

    public static Especialidade ofEspecialidade(CriarEspecialidadeInput especialidadeInput){
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(especialidadeInput.nome());
        return especialidade;
    }
}
