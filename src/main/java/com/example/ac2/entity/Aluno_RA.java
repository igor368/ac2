package com.example.ac2.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record Aluno_RA(String numero) {
    public Aluno_RA {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("RA não pode ser nulo.");
        }
    }
}