/*
package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.*;
import sptech.elderly.enums.TipoUsuarioEnum;
import sptech.elderly.exceptions.DadosDuplicadosException;
import sptech.elderly.exceptions.RecursoNaoEncontradoException;
import sptech.elderly.repository.GeneroRepository;
import sptech.elderly.repository.TipoUsuarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;
import sptech.elderly.web.dto.usuario.AtualizarUsuarioInput;
import sptech.elderly.web.dto.usuario.CriarUsuarioInput;
import sptech.elderly.web.dto.usuario.UsuarioConsultaDto;
import sptech.elderly.web.dto.usuario.UsuarioMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
class UsuarioServiceTest {
    //  Atributos Usuários
    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;
    private UsuarioMapper usuarioMapper;
    private GoogleCalendarService googleCalendarService;

    //  Atributo Gênero
    private GeneroRepository generoRepository;

    //  TipoUsuárioEnum
    private TipoUsuarioRepository tipoUsuarioRepository;

    //  Atributos Endereços
    private ResidenciaService residenciaService;

    private EnderecoService enderecoService;

    //  CurrículoService
    private CurriculoService curriculoService;

    //  EmailService
    private EmailService emailService;

    @BeforeEach
    void setupInicial() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioMapper = mock(UsuarioMapper.class);
        generoRepository = mock(GeneroRepository.class);
        tipoUsuarioRepository = mock(TipoUsuarioRepository.class);
        residenciaService = mock(ResidenciaService.class);
        enderecoService = mock(EnderecoService.class);
        curriculoService = mock(CurriculoService.class);
        emailService = mock(EmailService.class);

        usuarioService = new UsuarioService(
                usuarioRepository,
                usuarioMapper,
                null,
                generoRepository,
                tipoUsuarioRepository,
                residenciaService,
                enderecoService,
                curriculoService,
                emailService
        );
    }

    @DisplayName(
            "Se o código existir, deve retornar o usuário"
    )
    @Test
    void getUmEncontrado(){
        Integer codigo = 1;

        UsuarioEntity esperado = mock(UsuarioEntity.class);

//      Aqui deve retornar ture, caso exista um usuário com esse código
        when(usuarioRepository.existsById(codigo))
                .thenReturn(true);

//
        when(usuarioRepository.findById(codigo))
                .thenReturn(Optional.of(esperado));
    }

    @Test
    void validarDocumentoNaoDuplicado() {
        String documento = "12345678900";
        when(usuarioRepository.existsByDocumento(documento)).thenReturn(false);

        usuarioService.validarDocumento(documento);

        verify(usuarioRepository).existsByDocumento(documento);
    }

    @Test
    void validarDocumentoDuplicado() {
        String documento = "12345678900";
        when(usuarioRepository.existsByDocumento(documento)).thenReturn(true);

        assertThrows(DadosDuplicadosException.class, () -> {
            usuarioService.validarDocumento(documento);
        });

        verify(usuarioRepository).existsByDocumento(documento);
    }

    @Test
    void validarEmailNaoDuplicado() {
        String email = "email@teste.com";
        when(usuarioRepository.existsByEmail(email)).thenReturn(false);

        usuarioService.validarEmail(email);

        verify(usuarioRepository).existsByEmail(email);
    }

    @Test
    void validarEmailDuplicado() {
        String email = "email@teste.com";
        when(usuarioRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(DadosDuplicadosException.class, () -> {
            usuarioService.validarEmail(email);
        });

        verify(usuarioRepository).existsByEmail(email);
    }

    @Test
    void validarGeneroEncontrado() {
        Integer generoId = 1;
        Genero genero = new Genero();
        when(generoRepository.findById(generoId)).thenReturn(Optional.of(genero));

        Genero resultado = usuarioService.validarGenero(generoId);

        assertEquals(genero, resultado);
        verify(generoRepository).findById(generoId);
    }

    @Test
    void validarGeneroNaoEncontrado() {
        Integer generoId = 1;
        when(generoRepository.findById(generoId)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            usuarioService.validarGenero(generoId);
        });

        verify(generoRepository).findById(generoId);
    }

    @Test
    void validarTipoUsuarioEncontrado() {
        Integer tipoUsuarioId = 1;
        TipoUsuario tipoUsuario = new TipoUsuario();
        when(tipoUsuarioRepository.findById(tipoUsuarioId)).thenReturn(Optional.of(tipoUsuario));

        TipoUsuario resultado = usuarioService.validarTipoUsuario(tipoUsuarioId);

        assertEquals(tipoUsuario, resultado);
        verify(tipoUsuarioRepository).findById(tipoUsuarioId);
    }

    @Test
    void validarTipoUsuarioNaoEncontrado() {
        Integer tipoUsuarioId = 1;
        when(tipoUsuarioRepository.findById(tipoUsuarioId)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            usuarioService.validarTipoUsuario(tipoUsuarioId);
        });

        verify(tipoUsuarioRepository).findById(tipoUsuarioId);
    }

    @Test
    void salvarClienteSucesso() {
        CriarUsuarioInput input = mock(CriarUsuarioInput.class);
        when(input.tipoUsuario()).thenReturn(TipoUsuarioEnum.CLIENTE.getCodigo());
        when(input.email()).thenReturn("email@teste.com");
        when(input.documento()).thenReturn("12345678900");
        when(input.genero()).thenReturn(1);
        when(input.endereco()).thenReturn(new CriarEnderecoInput("02043-061", "Rua Almirante Noronha", "38", "Jardim São Paulo(Zona Norte)"
        , "870", "São Paulo", "SP"));

        UsuarioEntity usuario = new UsuarioEntity();
        when(usuarioMapper.mapearEntidade(input)).thenReturn(usuario);
        when(tipoUsuarioRepository.findById(TipoUsuarioEnum.CLIENTE.getCodigo())).thenReturn(Optional.of(new TipoUsuario()));
        when(generoRepository.findById(1)).thenReturn(Optional.of(new Genero()));
        when(enderecoService.salvar(any(CriarEnderecoInput.class))).thenReturn(new Endereco());
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioEntity resultado = usuarioService.salvarCliente(input);

        assertNotNull(resultado);
        verify(usuarioRepository).existsByEmail("email@teste.com");
        verify(usuarioRepository).existsByDocumento("12345678900");
        verify(usuarioMapper).mapearEntidade(input);
        verify(tipoUsuarioRepository).findById(TipoUsuarioEnum.CLIENTE.getCodigo());
        verify(generoRepository).findById(1);
        verify(enderecoService).salvar(any(CriarEnderecoInput.class));
        verify(usuarioRepository).save(usuario);
        verify(residenciaService).salvar(any(UsuarioEntity.class), any(Endereco.class));
    }

    @Test
    void salvarClienteTipoUsuarioInvalido() {
        CriarUsuarioInput input = mock(CriarUsuarioInput.class);
        when(input.tipoUsuario()).thenReturn(TipoUsuarioEnum.COLABORADOR.getCodigo());

        assertThrows(ResponseStatusException.class, () -> {
            usuarioService.salvarCliente(input);
        });

        verify(usuarioRepository, never()).existsByEmail(anyString());
        verify(usuarioRepository, never()).existsByDocumento(anyString());
    }

    @Test
    void salvarColaboradorTipoUsuarioInvalido() {
        CriarUsuarioInput input = mock(CriarUsuarioInput.class);
        when(input.tipoUsuario()).thenReturn(TipoUsuarioEnum.CLIENTE.getCodigo());

        assertThrows(ResponseStatusException.class, () -> {
            usuarioService.salvarColaborador(input);
        });

        verify(usuarioRepository, never()).existsByEmail(anyString());
        verify(usuarioRepository, never()).existsByDocumento(anyString());
    }

    @Test
    void buscarUsuarioIdNaoEncontrado() {
        Integer userId = 1;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            usuarioService.buscarUsuarioId(userId);
        });

        verify(usuarioRepository).findById(userId);
    }


    @Test
    void buscarUsuariosSucesso() {
        List<UsuarioEntity> usuarios = Arrays.asList(mock(UsuarioEntity.class), mock(UsuarioEntity.class));
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioEntity> resultado = usuarioService.buscarUsuarios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void buscarPorEmailNaoEncontrado() {
        String email = "email@teste.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            usuarioService.buscarPorEmail(email);
        });

        verify(usuarioRepository).findByEmail(email);
    }

    @Test
    void excluirUsuarioNaoEncontrado() {
        Integer id = 1;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            usuarioService.excluirUsuario(id);
        });

        verify(usuarioRepository).findById(id);
    }

    @Test
    void atualizarUsuarioNaoEncontrado() {
        Integer id = 1;
        AtualizarUsuarioInput input = mock(AtualizarUsuarioInput.class);
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            usuarioService.atualizarUsuario(id, input);
        });

        verify(usuarioRepository).findById(id);
    }
}*/
