package com.example.ac2.controller.test;

import com.example.ac2.controller.AcademicoController;
import com.example.ac2.dto.*;
import com.example.ac2.service.AcademicoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AcademicoController.class)
public class AcademicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcademicoService academicoService;

    // ----------------------------------------------------------
    // 1) PATCH /matriculas/{id}/concluir
    // ----------------------------------------------------------
    @Test
    public void testConcluirMatricula() throws Exception {

        Mockito.doNothing()
                .when(academicoService)
                .concluirCurso(eq(10L), eq(8.5));

        String bodyJson = """
            {
              "mediaFinal": 8.5
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/academico/matriculas/10/concluir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isOk());
    }

    // ----------------------------------------------------------
    // 2) POST /matriculas
    // ----------------------------------------------------------
    @Test
    public void testNovaMatricula() throws Exception {

        Mockito.doNothing()
                .when(academicoService)
                .realizarNovaMatricula(any(NovaMatriculaRequestDTO.class));

        String bodyJson = """
            {
              "alunoId": 1,
              "cursoId": 5
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/academico/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isCreated());
    }

    // ----------------------------------------------------------
    // 3) POST /alunos
    // ----------------------------------------------------------
    @Test
    public void testCriarAluno() throws Exception {

        AlunoResponseDTO mockResponse =
                new AlunoResponseDTO(1L, "Maria", "RA123", 0);

        Mockito.when(academicoService.criarAluno(any(AlunoRequestDTO.class)))
                .thenReturn(mockResponse);

        String bodyJson = """
            {
              "nome": "Maria",
              "ra": "RA123",
              "email": "maria@example.com"
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/academico/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.ra").value("RA123"))
                .andExpect(jsonPath("$.saldoCursosExtras").value(0));
    }

    // ----------------------------------------------------------
    // 4) POST /cursos
    // ----------------------------------------------------------
    @Test
    public void testCriarCurso() throws Exception {

        CursoResponseDTO mockResponse =
                new CursoResponseDTO(10L, "Matemática");

        Mockito.when(academicoService.criarCurso(any(CursoRequestDTO.class)))
                .thenReturn(mockResponse);

        String bodyJson = """
            {
              "nomeDoCurso": "Matemática"
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/academico/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nomeDoCurso").value("Matemática"));
    }
}
