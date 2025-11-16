package com.example.ac2.dto;

import jakarta.validation.constraints.*;

public record AlunoRequestDTO(
	    @NotBlank String nome, 
	    @NotBlank String ra, 
	    @Email String email
	) {}