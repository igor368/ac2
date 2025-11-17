package com.example.ac2.repository.test;

import com.example.ac2.entity.Aluno;
import com.example.ac2.entity.Aluno_Email;
import com.example.ac2.entity.Aluno_RA;
import com.example.ac2.entity.Curso;
import com.example.ac2.entity.Matricula;
import com.example.ac2.repository.AlunoRepository;
import com.example.ac2.repository.CursoRepository;
import com.example.ac2.repository.MatriculaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class MatriculaRepositoryTest {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    void testSaveAndFindMatricula() {
        // Criar aluno
        Aluno aluno = new Aluno();
        aluno.setNome("João");
        aluno.setRa(new Aluno_RA("RA123"));
        aluno.setEmail(new Aluno_Email("joao@example.com"));
        aluno.setSaldoCursosExtras(0);
        aluno = alunoRepository.save(aluno);

        // Criar curso
        Curso curso = new Curso();
        curso.setNomeDoCurso("Física");
        curso = cursoRepository.save(curso);

        // Criar matricula
        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setCurso(curso);
        matricula.setMediaFinal(9.0);
        matricula.setStatus("CONCLUIDO");

        Matricula saved = matriculaRepository.save(matricula);

        assertNotNull(saved.getId());
        assertEquals(9.0, saved.getMediaFinal());
        assertEquals("CONCLUIDO", saved.getStatus());
        assertEquals(aluno.getId(), saved.getAluno().getId());
        assertEquals(curso.getId(), saved.getCurso().getId());

        // Buscar
        Optional<Matricula> found = matriculaRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("CONCLUIDO", found.get().getStatus());
    }
}
