//package sptech.elderly.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import sptech.elderly.entity.Especialidade;
//import sptech.elderly.repository.EspecialidadeRepository;
//import sptech.elderly.web.dto.especialidade.CriarEspecialidadeInput;
//import sptech.elderly.web.dto.especialidade.EspecialidadeMapper;
//
//@Service @RequiredArgsConstructor
//public class EspecialidadeService {
//
//    @Autowired
//    private EspecialidadeRepository especialidadeRepository;
//
//
//    public Especialidade salvar(CriarEspecialidadeInput especialidadeInput) {
//        Especialidade especialidade = EspecialidadeMapper.ofEspecialidade(especialidadeInput);
//        return this.especialidadeRepository.save(especialidade);
//    }
//}
