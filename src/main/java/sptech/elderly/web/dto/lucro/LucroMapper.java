package sptech.elderly.web.dto.lucro;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sptech.elderly.entity.Lucro;

import java.util.List;
import java.util.stream.Collectors;

@Component @RequiredArgsConstructor
public class LucroMapper {

    private final ModelMapper mapper;

    public static List<LucroOutput> ofLucros(List<Lucro> lucros) {
        return lucros.stream()
                .map(LucroMapper::toDto)
                .collect(Collectors.toList());
    }

    public Lucro mapearEntidade(CriarLucroInput input){
        return mapper.map(input, Lucro.class);
    }

    public static LucroOutput toDto(Lucro lucro){
        return new LucroOutput(lucro.getId(), lucro.getLucro());
    }
}
