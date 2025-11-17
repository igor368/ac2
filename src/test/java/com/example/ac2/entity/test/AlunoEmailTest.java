package com.example.ac2.entity.test;

import com.example.ac2.entity.Aluno_Email;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlunoEmailTest {

    @Test
    void testCreateValidEmail() {
        Aluno_Email email = new Aluno_Email("test@example.com");
        assertEquals("test@example.com", email.endereco());
    }
}
