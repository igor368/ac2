package com.example.ac2.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter @Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@EqualsAndHashCode(of = "id") 
@ToString 
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Aluno_RA ra;
    private String nome;

    @Embedded
    private Aluno_Email email;

    
    private Integer saldoCursosExtras = 0; 

    @OneToMany(mappedBy = "aluno")
    private List<Matricula> matriculas;
}
