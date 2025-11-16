package com.example.ac2.controller;

// em: com.example.ac2.controller.rest.AcademicoController
import com.example.ac2.dto.*;
import com.example.ac2.service.AcademicoService;
import jakarta.validation.Valid; // Importante para validar o DTO
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/academico")
public class AcademicoController {

    private final AcademicoService academicoService;

    public AcademicoController(AcademicoService academicoService) {
        this.academicoService = academicoService;
    }

    // Endpoint principal: Concluir um curso e aplicar a regra
    @PatchMapping("/matriculas/{id}/concluir")
    public ResponseEntity<Void> concluirMatricula(
            @PathVariable Long id,
            @Valid @RequestBody ConcluirCursoRequestDTO request) {

        academicoService.concluirCurso(id, request.mediaFinal());
        return ResponseEntity.ok().build(); // 200 OK
    }

    // Endpoint para usar a recompensa
    @PostMapping("/matriculas")
    public ResponseEntity<Void> novaMatricula(
            @Valid @RequestBody NovaMatriculaRequestDTO request) {

        academicoService.realizarNovaMatricula(request);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    // Endpoint para criar aluno
    @PostMapping("/alunos")
    public ResponseEntity<AlunoResponseDTO> criarAluno(
            @Valid @RequestBody AlunoRequestDTO dto) {

        AlunoResponseDTO alunoCriado = academicoService.criarAluno(dto);

        // Retorna 201 Created + o objeto criado no body
        return ResponseEntity.status(201).body(alunoCriado);
    }

    // endpoint para criar curso
    @PostMapping("/cursos")
    public ResponseEntity<CursoResponseDTO> criarCurso(
            @Valid @RequestBody CursoRequestDTO dto) {

        CursoResponseDTO cursoCriado = academicoService.criarCurso(dto);

        // Retorna 201 Created + o objeto criado no body
        return ResponseEntity.status(201).body(cursoCriado);
    }

}