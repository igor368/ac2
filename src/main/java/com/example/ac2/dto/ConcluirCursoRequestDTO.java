package com.example.ac2.dto;
import jakarta.validation.constraints.*;

public record ConcluirCursoRequestDTO(
	    @NotNull @Min(0) @Max(10) Double mediaFinal
	) {}