package com.example.ac2.entity.test;

import com.example.ac2.entity.Aluno_RA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoRATest {

    @Test
    void testCreateValidRA() {
        Aluno_RA ra = new Aluno_RA("RA1234");
        assertEquals("RA1234", ra.numero());
    }

    @Test
    void testInvalidRAThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Aluno_RA(" ")
        );
    }

    @Test
    void testNullRAThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Aluno_RA(null)
        );
    }
}
