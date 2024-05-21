package sptech.elderly.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.*;
import sptech.elderly.enums.TipoUsuarioEnum;
import sptech.elderly.exceptions.DadosDuplicadosException;
import sptech.elderly.exceptions.RecursoNaoEncontradoException;
import sptech.elderly.repository.GeneroRepository;
import sptech.elderly.repository.TipoUsuarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.util.ListUtils;
import sptech.elderly.web.dto.usuario.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UsuarioService {
//  Atributos Usuarios
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

//  Atributo Genero
    private final GeneroRepository generoRepository;

//  TipoUsuarioEnum
    private final TipoUsuarioRepository tipoUsuarioRepository;

//  Endereço
    private final ResidenciaService residenciaService;

    private final EnderecoService enderecoService;

//  CurriculoService
    private final CurriculoService curriculoService;

    public void validarDocumento(String documento){
        if (usuarioRepository.existsByDocumento(documento)){
            throw new DadosDuplicadosException("Usuário", "Documento", documento);
        }
    }

    public void validarEmail(String email){
        if(usuarioRepository.existsByEmail(email)){
            throw new DadosDuplicadosException("Usuário", "Email", email);
        }
    }

    public Genero validarGenero(Integer generoId){
        return generoRepository.findById(generoId).orElseThrow(
                        () -> new RecursoNaoEncontradoException("Genero", generoId)
                );
    }

    public TipoUsuario validarTipoUsuario(Integer tipoUsuarioId){
        return tipoUsuarioRepository.findById(tipoUsuarioId).orElseThrow(
                        () -> new RecursoNaoEncontradoException("TipoUsuario", tipoUsuarioId)
                );
    }

    public UsuarioEntity salvarCliente(CriarUsuarioInput input) {
        if (input.tipoUsuario() != TipoUsuarioEnum.CLIENTE.getCodigo()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Tipo de usuário inválido.");
        }

        return getUsuarioEntity(input);
    }

    public UsuarioEntity getUsuarioEntity(CriarUsuarioInput input) {
        validarEmail(input.email());
        validarDocumento(input.documento());

        UsuarioEntity novoUsuario = usuarioMapper.mapearEntidade(input);

        novoUsuario.setTipoUsuario(validarTipoUsuario(input.tipoUsuario()));
        novoUsuario.setGenero(validarGenero(input.genero()));

        Endereco endereco = enderecoService.salvar(input.endereco());

        novoUsuario = usuarioRepository.save(novoUsuario);
        residenciaService.salvar(novoUsuario, endereco);
        return novoUsuario;
    }

    public UsuarioEntity salvarColaborador(CriarUsuarioInput input) {
        if (input.tipoUsuario() != TipoUsuarioEnum.COLABORADOR.getCodigo()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Tipo de usuário inválido.");
        }

        return getUsuarioEntity(input);
    }

    @Transactional
    public UsuarioEntity buscarUsuarioId(Integer userId) {
        return usuarioRepository.findById(userId).orElseThrow(
                () -> new RecursoNaoEncontradoException("Usuário", userId)
        );
    }

    public List<UsuarioEntity> buscarUsuarios() {
        List<UsuarioEntity> todosUsuarios = usuarioRepository.findAll();

        Set<UsuarioEntity> usuariosSemDuplicacao = new HashSet<>(todosUsuarios);

        return usuariosSemDuplicacao.stream().collect(Collectors.toList());
    }

    @Transactional
    public UsuarioEntity buscarPorEmail(String email) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new RecursoNaoEncontradoException("Email", email)
        );

        return buscarUsuarioId(usuario.getId());
    }

    public void excluirUsuario(@PathVariable Integer id){
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", id));

        if (usuario.getResidencias() != null){
            usuario.getResidencias()
                    .forEach(residencia -> {
                        usuarioRepository.delete(residencia.getUsuario());
                    });
        }

        usuarioRepository.delete(usuario);
    }

    public UsuarioEntity atualizarUsuario(Integer id, AtualizarUsuarioInput input) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", id));

        if (input.nome() != null){
            usuario.setNome(input.nome());
        }

        if(input.email() != null){
            usuario.setEmail(input.email());
        }

        if(input.documento() != null){
            usuario.setDocumento(input.documento());
        }

        if(input.dataNascimento() != null){
            usuario.setDataNascimento(input.dataNascimento());
        }

        if(input.biografia() != null){
            usuario.setBiografia(input.biografia());
        }

        if(input.endereco() != null){
            enderecoService.atualizarEndereco(idEndereco(usuario), input.endereco());
        }

        if(input.especialidades() != null){
            usuario.setCurriculos(curriculoService.associarColaboradorEspecialidade(usuario, input.especialidades()));
        }

        return usuarioRepository.save(usuario);
    }

    public Integer idEndereco(UsuarioEntity usuario) {
        if (usuario.getResidencias() != null && !usuario.getResidencias().isEmpty()) {
            Residencia residencia = usuario.getResidencias().get(0);
            Endereco endereco = residencia.getEndereco();

            if (endereco != null){
                return endereco.getId();
            }
        }

        return  null;
    }

    public String gerarStringCsv() {
        List<ColaboradorOutput> colaboradores = UsuarioMapper.ofColaborador(buscarUsuarios());

        try(StringWriter arq = new StringWriter()) {
            arq.append(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                    "ID", "Nome", "E-mail", "Documento", "CEP", "Logradouro", "Número", "Complemento", "Bairro", "Cidade", "UF", "Especialidades"));
            for (ColaboradorOutput colaborador : colaboradores) {
                arq.append(String.format("%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;[%s]\n",
                        colaborador.id(),
                        colaborador.nome(),
                        colaborador.email(),
                        colaborador.documento(),
                        colaborador.endereco().cep(),
                        colaborador.endereco().logradouro(),
                        colaborador.endereco().numero(),
                        colaborador.endereco().complemento(),
                        colaborador.endereco().bairro(),
                        colaborador.endereco().cidade(),
                        colaborador.endereco().uf(),
                        ListUtils.mapToString(colaborador.especialidades())
                ));
            }

            return arq.toString();
        } catch (IOException erro) {
            System.out.println("Erro ao gravar o arquivo");
            return "Erro ao gravar o arquivo";
        }
    }
}