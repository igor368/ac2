package com.example.ac2.dto;
import jakarta.validation.constraints.*;

public record NovaMatriculaRequestDTO(
	    @NotNull Long alunoId, 
	    @NotNull Long cursoId
	) {}