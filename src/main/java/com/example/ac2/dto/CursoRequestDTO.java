package com.example.ac2.dto;

import jakarta.validation.constraints.*;

// DTO para CRIAR um curso
public record CursoRequestDTO(
        @NotBlank String nomeDoCurso

) {}