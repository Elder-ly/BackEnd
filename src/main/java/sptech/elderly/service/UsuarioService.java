package sptech.elderly.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.*;
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

    private final ColaboradorMapper colaboradorMapper;

    private final ClienteMapper clienteMapper;

//  Atributo Genero
    private final GeneroRepository generoRepository;

//  TipoUsuario
    private final TipoUsuarioRepository tipoUsuarioRepository;

//  Endereço
    private final ResidenciaService residenciaService;

    private final EnderecoService enderecoService;

//  EspecialidadeController
    private final EspecialidadeService especialidadeService;

    private final CurriculoService curriculoService;

    public UsuarioEntity salvarCliente(CriarClienteInput input) {
        if (input.tipoUsuario() != 3){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Tipo de usuário inválido.");
        }

        if (usuarioRepository.existsByEmail(input.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(input.tipoUsuario())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Tipo usuário não encontrado")
                );

        Genero generoId = generoRepository.findById(input.genero())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Gênero não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(input.endereco());
        UsuarioEntity novoUsuario = clienteMapper.criarCliente(input);

        novoUsuario.setTipoUsuario(tipoUsuarioId);
        novoUsuario.setGenero(generoId);

        novoUsuario = usuarioRepository.save(novoUsuario);
        residenciaService.salvar(novoUsuario, endereco);

        return novoUsuario;
    }

    public UsuarioEntity salvarColaborador(CriarColaboradorInput input) {
        if (input.tipoUsuario() != 2){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Tipo de usuário inválido.");
        }

        if (usuarioRepository.existsByEmail(input.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(input.tipoUsuario())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Tipo usuário não encontrado.")
                );

        Genero generoId = generoRepository.findById(input.genero())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Gênero não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(input.endereco());

        UsuarioEntity novoUsuario = colaboradorMapper.criarFuncionario(input);
        novoUsuario.setTipoUsuario(tipoUsuarioId);
        novoUsuario.setGenero(generoId);

        novoUsuario = usuarioRepository.save(novoUsuario);
        residenciaService.salvar(novoUsuario, endereco);

        return novoUsuario;
    }

    @Transactional
    public UsuarioEntity buscarUsuarioId(Integer userId) {
        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado.")
        );

        return usuario;
    }

    public List<UsuarioEntity> buscarUsuarios() {
        List<UsuarioEntity> todosUsuarios = usuarioRepository.findAll();

        Set<UsuarioEntity> usuariosSemDuplicacao = new HashSet<>(todosUsuarios);

        return usuariosSemDuplicacao.stream().collect(Collectors.toList());
    }

    @Transactional
    public UsuarioEntity buscarPorEmail(String email) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Email não encontrado")
        );

        return buscarUsuarioId(usuario.getId());
    }

    public void excluirUsuario(@PathVariable Integer id){
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Cliente não encontrado"));

        if (usuario.getResidencias() != null){
            usuario.getResidencias()
                    .forEach(residencia -> {
                        usuarioRepository.delete(residencia.getUsuario());
                    });
        }

        usuarioRepository.delete(usuario);
    }

    public UsuarioEntity atualizarColaborador(Integer id, AtualizarColaboradorInput input) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado"));

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

        if(input.id() != null){
            usuario.setCurriculos(curriculoService.associarColaboradorEspecialidade(usuario, input.id()));
        }

        return usuarioRepository.save(usuario);
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