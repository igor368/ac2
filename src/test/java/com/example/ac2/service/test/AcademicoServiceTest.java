package com.example.ac2.service.test;

import com.example.ac2.dto.*;
import com.example.ac2.entity.*;
import com.example.ac2.repository.*;
import com.example.ac2.service.AcademicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AcademicoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private MatriculaRepository matriculaRepository;

    @InjectMocks
    private AcademicoService academicoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ----------------------------------------------------------
    // TEST 1: criarAluno()
    // ----------------------------------------------------------
    @Test
    void testCriarAluno() {

        AlunoRequestDTO request = new AlunoRequestDTO(
                "Maria",
                "RA123",
                "maria@example.com"
        );

        Aluno alunoSalvo = new Aluno();
        alunoSalvo.setId(1L);
        alunoSalvo.setNome("Maria");
        alunoSalvo.setRa(new Aluno_RA("RA123"));
        alunoSalvo.setEmail(new Aluno_Email("maria@example.com"));
        alunoSalvo.setSaldoCursosExtras(0);

        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoSalvo);

        AlunoResponseDTO resposta = academicoService.criarAluno(request);

        assertNotNull(resposta);
        assertEquals(1L, resposta.id());
        assertEquals("Maria", resposta.nome());
        assertEquals("RA123", resposta.ra());
        assertEquals(0, resposta.saldoCursosExtras());
    }

    // ----------------------------------------------------------
    // TEST 2: criarCurso()
    // ----------------------------------------------------------
    @Test
    void testCriarCurso() {

        CursoRequestDTO request = new CursoRequestDTO("Matemática");

        Curso cursoSalvo = new Curso();
        cursoSalvo.setId(10L);
        cursoSalvo.setNomeDoCurso("Matemática");

        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoSalvo);

        CursoResponseDTO resposta = academicoService.criarCurso(request);

        assertNotNull(resposta);
        assertEquals(10L, resposta.id());
        assertEquals("Matemática", resposta.nomeDoCurso());
    }

    // ----------------------------------------------------------
    // TEST 3: realizarNovaMatricula() - com saldo
    // ----------------------------------------------------------
    @Test
    void testRealizarNovaMatricula_ComSaldo() {

        NovaMatriculaRequestDTO request =
                new NovaMatriculaRequestDTO(1L, 5L);

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setSaldoCursosExtras(2);

        Curso curso = new Curso();
        curso.setId(5L);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(cursoRepository.findById(5L)).thenReturn(Optional.of(curso));

        academicoService.realizarNovaMatricula(request);

        assertEquals(1, aluno.getSaldoCursosExtras());

        verify(alunoRepository, times(1)).save(aluno);
        verify(matriculaRepository, times(1)).save(any(Matricula.class));
    }

    // ----------------------------------------------------------
    // TEST 4: realizarNovaMatricula() - sem saldo
    // ----------------------------------------------------------
    @Test
    void testRealizarNovaMatricula_SemSaldo() {

        NovaMatriculaRequestDTO request =
                new NovaMatriculaRequestDTO(1L, 5L);

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setSaldoCursosExtras(0);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        Exception e = assertThrows(RuntimeException.class,
                () -> academicoService.realizarNovaMatricula(request));

        assertEquals("Aluno sem saldo de cursos extras.", e.getMessage());
        verify(matriculaRepository, never()).save(any());
    }

    // ----------------------------------------------------------
    // TEST 5: concluirCurso() - media > 7 → ganha 3 créditos
    // ----------------------------------------------------------
    @Test
    void testConcluirCurso_GanhaCredito() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setSaldoCursosExtras(0);

        Matricula matricula = new Matricula();
        matricula.setId(10L);
        matricula.setAluno(aluno);

        when(matriculaRepository.findById(10L))
                .thenReturn(Optional.of(matricula));

        academicoService.concluirCurso(10L, 8.0);

        assertEquals("CONCLUIDO", matricula.getStatus());
        assertEquals(8.0, matricula.getMediaFinal());
        assertEquals(3, aluno.getSaldoCursosExtras());

        verify(alunoRepository).save(aluno);
        verify(matriculaRepository).save(matricula);
    }

    // ----------------------------------------------------------
    // TEST 6: concluirCurso() - media <= 7 → não ganha creditos
    // ----------------------------------------------------------
    @Test
    void testConcluirCurso_SemGanho() {

        Aluno aluno = new Aluno();
        aluno.setSaldoCursosExtras(5);

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);

        when(matriculaRepository.findById(10L))
                .thenReturn(Optional.of(matricula));

        academicoService.concluirCurso(10L, 6.5);

        assertEquals("CONCLUIDO", matricula.getStatus());
        assertEquals(5, aluno.getSaldoCursosExtras());

        verify(alunoRepository, never()).save(any());
        verify(matriculaRepository).save(matricula);
    }
}
