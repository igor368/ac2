package com.example.ac2.entity.test;

import com.example.ac2.entity.Curso;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CursoTest {

    @Test
    void testSetAndGetNomeDoCurso() {
        Curso curso = new Curso();
        curso.setNomeDoCurso("Matemática");

        assertEquals("Matemática", curso.getNomeDoCurso());
    }
}
