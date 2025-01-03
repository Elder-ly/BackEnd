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
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UsuarioService {
//  Atributos Usuarios
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final GoogleCalendarService googleCalendarService;

//  Atributo Genero
    private final GeneroRepository generoRepository;

//  TipoUsuarioEnum
    private final TipoUsuarioRepository tipoUsuarioRepository;

//  Endereço
    private final ResidenciaService residenciaService;

    private final EnderecoService enderecoService;

//  CurriculoService
    private final CurriculoService curriculoService;

//  EmailService
    private final EmailService emailService;

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

    public Genero validarGenero(Long generoId){
        return generoRepository.findById(generoId).orElseThrow(
                        () -> new RecursoNaoEncontradoException("Genero", generoId)
                );
    }

    public TipoUsuario validarTipoUsuario(Long tipoUsuarioId){
        return tipoUsuarioRepository.findById(tipoUsuarioId).orElseThrow(
                        () -> new RecursoNaoEncontradoException("TipoUsuario", tipoUsuarioId)
                );
    }

    public Usuario salvarCliente(CriarUsuarioInput input) {
        if (input.tipoUsuario() != TipoUsuarioEnum.CLIENTE.getCodigo()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Tipo de usuário inválido.");
        }

        validarEmail(input.email());
        validarDocumento(input.documento());

        Usuario novoUsuario = usuarioMapper.mapearEntidade(input);

        novoUsuario.setTipoUsuario(validarTipoUsuario(TipoUsuarioEnum.CLIENTE.getCodigo()));
        novoUsuario.setGenero(validarGenero(input.genero()));

        Endereco endereco = enderecoService.salvar(input.endereco());

        novoUsuario = usuarioRepository.save(novoUsuario);
        residenciaService.salvar(novoUsuario, endereco);

        return novoUsuario;
    }

    public Usuario salvarColaborador(CriarUsuarioInput input) {
        if (input.tipoUsuario() != TipoUsuarioEnum.COLABORADOR.getCodigo()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Tipo de usuário inválido.");
        }

        validarEmail(input.email());
        validarDocumento(input.documento());

        Usuario novoUsuario = usuarioMapper.mapearEntidade(input);

        novoUsuario.setTipoUsuario(validarTipoUsuario(TipoUsuarioEnum.COLABORADOR.getCodigo()));
        novoUsuario.setGenero(validarGenero(input.genero()));

        Endereco endereco = enderecoService.salvar(input.endereco());

        novoUsuario = usuarioRepository.save(novoUsuario);
        residenciaService.salvar(novoUsuario, endereco);

        novoUsuario.setCurriculos(curriculoService.associarColaboradorEspecialidade(novoUsuario, input.especialidades()));

        String htmlContent = """
                <html>
        <body style='font-family: Roboto, sans-serif; text-align: center;'>
            <div style='padding: 3px; text-align: center; width: 35%;'>
            </div>
            <h1 style='color: #0f456c'>Bem-vindo ao Elder.ly!</h1>
            <p style='color: #135686'>
                Seja muito bem-vindo à nossa comunidade dedicada ao cuidado de idosos. Você foi convidado pelo seu agente para fazer parte dessa inovação. <br>
                Para começar a utilizar a plataforma, por favor, complete seu cadastro clicando no botão abaixo:
            </p>
            <div>
                <a href='https://elderly.space/'
                   style='background-color: #229ef7; color: #ffffff; padding: 10px 20px; border-radius: 4px; text-decoration: none; display: inline-block;'>
                   Complete seu cadastro
                </a>
            </div>
            <p style='color: #135686'>
                Estamos animados para tê-lo conosco e ansiosos para ver como você contribuirá para tornar a vida dos idosos mais confortável e feliz.
            </p>
        </body>
    </html>
    """;


        Email email = new Email(novoUsuario.getEmail(), "Bem-Vindo ao Elder.ly!", htmlContent);
        emailService.sendEmail(email);

        return novoUsuario;
    }

    @Transactional
    public UsuarioConsultaDto buscarUsuarioId(Long userId) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(
                () -> new RecursoNaoEncontradoException("Usuário", userId)
        );

        return UsuarioMapper.toDto(usuario);
    }

    public Usuario buscarUsuario(Long userId) {
        return usuarioRepository.findById(userId).orElseThrow(
                () -> new RecursoNaoEncontradoException("Usuário", userId)
        );
    }

    public List<Usuario> buscarUsuarios() {
        List<Usuario> todosUsuarios = usuarioRepository.findAll();

        Set<Usuario> usuariosSemDuplicacao = new HashSet<>(todosUsuarios);

        return usuariosSemDuplicacao.stream().collect(Collectors.toList());
    }

    @Transactional
    public UsuarioConsultaDto buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new RecursoNaoEncontradoException("Email", email)
        );

        return buscarUsuarioId(usuario.getId());
    }

    @Transactional
    public void excluirUsuario(@PathVariable Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", id));

        if (usuario.getResidencias() != null){
            usuario.getResidencias()
                    .forEach(residencia -> {
                        usuarioRepository.delete(residencia.getUsuario());
                    });
        }

        if(usuario.getTipoUsuario().getId() == TipoUsuarioEnum.COLABORADOR.getCodigo()){
            curriculoService.excluirUsuario(usuario.getId());
        }

        googleCalendarService.excluirCalendario(usuario);
        enderecoService.excluirEndereco(idEndereco(usuario));
        usuarioRepository.deleteById(usuario.getId());
    }

    public Usuario atualizarUsuario(Long id, AtualizarUsuarioInput input) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", id));

        usuario.setId(id);
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

        if(input.fotoPerfil() != null){
            usuario.setFotoPerfil(input.fotoPerfil());
        }

        if(input.genero() != null){
            usuario.setGenero(validarGenero(input.genero()));
        }

        if(input.endereco() != null){
            enderecoService.atualizarEndereco(idEndereco(usuario), input.endereco());
        }

        if((usuario.getTipoUsuario().getId() == TipoUsuarioEnum.COLABORADOR.getCodigo() && input.especialidades() != null) || (usuario.getTipoUsuario().getId() == TipoUsuarioEnum.ADM.getCodigo() && input.especialidades() != null)){
            usuario.setCurriculos(curriculoService.associarColaboradorEspecialidade(usuario, input.especialidades()));
        }

        return usuarioRepository.save(usuario);
    }

    public Long idEndereco(Usuario usuario) {
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

    public List<UsuarioConsultaDto> buscarColaboradoresPorEspecialidadeEDispoibilidade(String accessToken, BuscarColaboradorInput input) throws GeneralSecurityException, IOException {
        return usuarioMapper.toDto(
                googleCalendarService.filtrarFuncionariosPorDisponibilidade(
                        accessToken,
                        input.dataHoraInicio(),
                        input.dataHoraFim(),
                        usuarioRepository.findByEspecialidades(input.especialidades())
                )
        );
    }
}