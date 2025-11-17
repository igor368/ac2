package com.example.ac2.repository.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.ac2.entity.Aluno;
import com.example.ac2.entity.Aluno_Email;
import com.example.ac2.entity.Aluno_RA;
import com.example.ac2.repository.AlunoRepository;

@ActiveProfiles("test")
@DataJpaTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    void testSaveAndFindAluno() {

        // Criar o aluno
        Aluno aluno = new Aluno();
        aluno.setNome("Joao Silva");
        aluno.setRa(new Aluno_RA("RA123"));
        aluno.setEmail(new Aluno_Email("joao@teste.com"));
        aluno.setSaldoCursosExtras(0);

        // Salvar
        Aluno savedAluno = alunoRepository.save(aluno);
        assertNotNull(savedAluno.getId()); // ID gerado

        // Buscar
        Optional<Aluno> retrieved = alunoRepository.findById(savedAluno.getId());

        assertTrue(retrieved.isPresent());
        assertEquals("Joao Silva", retrieved.get().getNome());
        assertEquals("RA123", retrieved.get().getRa().numero());
        assertEquals("joao@teste.com", retrieved.get().getEmail().endereco());
        assertEquals(0, retrieved.get().getSaldoCursosExtras());
    }
}
