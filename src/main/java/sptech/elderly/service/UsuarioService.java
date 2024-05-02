package sptech.elderly.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.*;
import sptech.elderly.repository.GeneroRepository;
import sptech.elderly.repository.TipoUsuarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.usuario.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UsuarioService {

//  Atributos Usuarios
    private final UsuarioRepository usuarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    private final ClienteMapper clienteMapper;

//  Atributo Genero
    private final GeneroRepository generoRepository;

//  TipoUsuario
    private final TipoUsuarioRepository tipoUsuarioRepository;

//  Endereço
    private final ResidenciaService residenciaService;

    private final EnderecoService enderecoService;

//  Especialidade
    private final EspecialidadeService especialidadeService;

    private final CurriculoService curriculoService;

    public UsuarioEntity salvarCliente(CriarCliente novoCliente) {
        if (usuarioRepository.existsByEmail(novoCliente.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoCliente.tipoUsuario())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(novoCliente.endereco());

        UsuarioEntity novoUsuario = clienteMapper.criarCliente(novoCliente);
        novoUsuario.setTipoUsuario(tipoUsuarioId);

        novoUsuario = usuarioRepository.save(novoUsuario);

        residenciaService.salvar(novoUsuario, endereco);

        return novoUsuario;
    }

    public UsuarioEntity salvarFuncionario(CriarFuncionario novoFuncionario) {
        if (usuarioRepository.existsByEmail(novoFuncionario.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoFuncionario.tipoUsuario())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Tipo usuário não encontrado.")
                );

        Genero generoId = generoRepository.findById(novoFuncionario.genero())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Gênero não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(novoFuncionario.endereco());

        UsuarioEntity novoUsuario = funcionarioMapper.criarFuncionario(novoFuncionario);
        novoUsuario.setTipoUsuario(tipoUsuarioId);
        novoUsuario.setGenero(generoId);


        novoUsuario = usuarioRepository.save(novoUsuario);

        List<Especialidade> especialidades = especialidadeService.salvar(novoFuncionario.especialidades());

        for (Especialidade especialidade : especialidades) {
            curriculoService.associarEspecialidadeUsuario(novoUsuario, especialidade);
        }

        residenciaService.salvar(novoUsuario, endereco);
        return novoUsuario;
    }

    @Transactional
    public UsuarioConsultaDto buscarPorId(Integer userId) {
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado.")
        );

        return UsuarioMapperClass.toDto(usuario);
    }

    public List<UsuarioEntity> buscarUsuarios() {
        // Buscar todos os usuários
        List<UsuarioEntity> todosUsuarios = usuarioRepository.findAll();

        // Usar um HashSet para remover duplicações
        Set<UsuarioEntity> usuariosSemDuplicacao = new HashSet<>(todosUsuarios);

        // Converter o Set de volta para uma lista e retornar
        return usuariosSemDuplicacao.stream().collect(Collectors.toList());
    }

    @Transactional
    public UsuarioEntity buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado")
        );
    }

    public String gerarStringCsv() {
        List<UsuarioEntity> colaboradores = usuarioRepository.findAll();
        Boolean deuRuim = false;

        try(StringWriter arq = new StringWriter()) {
            for (int i = 0; i < colaboradores.size(); i++) {
                UsuarioEntity colaborador = colaboradores.get(i);
                arq.append(String.format("%s;%s;%s;%s\n",
                        colaborador.getId(),
                        colaborador.getNome(),
                        colaborador.getEmail(),
                        colaborador.getDocumento()
                ));
            }

            return arq.toString();
        } catch (IOException erro) {
            System.out.println("Erro ao gravar o arquivo");
            return "Erro ao gravar o arquivo";
        }
    }
}
