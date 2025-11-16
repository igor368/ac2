package com.example.ac2.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record Aluno_Email(String endereco) {
    //  validação de formato de email aqui
}