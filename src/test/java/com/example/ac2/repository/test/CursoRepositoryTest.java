package com.example.ac2.repository.test;

import com.example.ac2.entity.Curso;
import com.example.ac2.repository.CursoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    @Test
    void testSaveAndFindCurso() {
        // Criar entidade
        Curso curso = new Curso();
        curso.setNomeDoCurso("Matemática");

        // Salvar
        Curso saved = cursoRepository.save(curso);
        assertNotNull(saved.getId());
        assertEquals("Matemática", saved.getNomeDoCurso());

        // Buscar
        Optional<Curso> found = cursoRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Matemática", found.get().getNomeDoCurso());
    }
}
