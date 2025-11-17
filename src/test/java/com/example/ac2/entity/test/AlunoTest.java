package com.example.ac2.entity.test;

import com.example.ac2.entity.Aluno;
import com.example.ac2.entity.Aluno_Email;
import com.example.ac2.entity.Aluno_RA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoTest {

    @Test
    void testSetAndGetNome() {
        Aluno aluno = new Aluno();
        aluno.setNome("Maria");
        assertEquals("Maria", aluno.getNome());
    }

    @Test
    void testSetAndGetEmail() {
        Aluno aluno = new Aluno();
        Aluno_Email email = new Aluno_Email("test@example.com");
        aluno.setEmail(email);

        assertEquals(email, aluno.getEmail());
    }

    @Test
    void testSetAndGetRA() {
        Aluno aluno = new Aluno();
        Aluno_RA ra = new Aluno_RA("RA999");
        aluno.setRa(ra);

        assertEquals(ra, aluno.getRa());
    }

    @Test
    void testSaldoInicial() {
        Aluno aluno = new Aluno();
        assertEquals(0, aluno.getSaldoCursosExtras());
    }
}
