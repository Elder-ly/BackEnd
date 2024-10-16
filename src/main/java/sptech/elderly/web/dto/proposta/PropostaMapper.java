package sptech.elderly.web.dto.proposta;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sptech.elderly.entity.Proposta;
import sptech.elderly.entity.Usuario;
import sptech.elderly.web.dto.usuario.CriarUsuarioInput;

import java.util.List;
import java.util.stream.Collectors;

@Component @RequiredArgsConstructor
public class PropostaMapper {

    private final ModelMapper mapper;

    public Proposta mapearEntidade(PropostaInput input){
        return mapper.map(input, Proposta.class);
    }

    public List<PropostaOutput> toOutput(List<Proposta> propostas){
        return propostas.stream()
                .map(entity -> mapper.map(entity, PropostaOutput.class))
                .collect(Collectors.toList());
    }
}
