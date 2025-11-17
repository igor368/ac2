package com.example.ac2.entity.test;

import com.example.ac2.entity.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatriculaTest {

    @Test
    void testSetAndGetFields() {
        Matricula matricula = new Matricula();

        Aluno aluno = new Aluno();
        aluno.setNome("Carlos");

        Curso curso = new Curso();
        curso.setNomeDoCurso("DevOps");

        matricula.setAluno(aluno);
        matricula.setCurso(curso);
        matricula.setMediaFinal(9.0);
        matricula.setStatus("CONCLUIDO");

        assertEquals(aluno, matricula.getAluno());
        assertEquals(curso, matricula.getCurso());
        assertEquals(9.0, matricula.getMediaFinal());
        assertEquals("CONCLUIDO", matricula.getStatus());
    }
}
