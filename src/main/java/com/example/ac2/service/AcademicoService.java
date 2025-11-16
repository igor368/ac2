package com.example.ac2.service;

import com.example.ac2.dto.*;
import com.example.ac2.repository.*;
import com.example.ac2.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademicoService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final MatriculaRepository matriculaRepository;

    // Injeção de dependência via construtor
    public AcademicoService(AlunoRepository aluno, CursoRepository curso, MatriculaRepository matricula) {
        this.alunoRepository = aluno;
        this.cursoRepository = curso;
        this.matriculaRepository = matricula;
    }

    @Transactional
    public void concluirCurso(Long matriculaId, double mediaFinal) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));

        matricula.setMediaFinal(mediaFinal);
        matricula.setStatus("CONCLUIDO");

        // --- A REGRA DE NEGÓCIO ---
        if (mediaFinal > 7.0) {
            Aluno aluno = matricula.getAluno();
            // Usa o getter e setter do Lombok
            aluno.setSaldoCursosExtras(aluno.getSaldoCursosExtras() + 3);
            alunoRepository.save(aluno);
        }
        matriculaRepository.save(matricula);
    }

    @Transactional
    public void realizarNovaMatricula(NovaMatriculaRequestDTO request) {
        Aluno aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (aluno.getSaldoCursosExtras() > 0) {
            aluno.setSaldoCursosExtras(aluno.getSaldoCursosExtras() - 1);
            alunoRepository.save(aluno);

            Curso curso = cursoRepository.findById(request.cursoId())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

            Matricula nova = new Matricula(null, aluno, curso, null, "EM_ANDAMENTO");
            matriculaRepository.save(nova);
        } else {
            throw new RuntimeException("Aluno sem saldo de cursos extras.");
        }
    }

    @Transactional
    public AlunoResponseDTO criarAluno(AlunoRequestDTO dto) {
        // Converte o DTO para Entidade
        Aluno novoAluno = new Aluno();
        novoAluno.setNome(dto.nome());
        novoAluno.setRa(new Aluno_RA(dto.ra()));         // Cria o VO RA
        novoAluno.setEmail(new Aluno_Email(dto.email())); // Cria o VO Email
        novoAluno.setSaldoCursosExtras(0);          // Regra de negócio: começa com 0

        // Salva a nova entidade
        Aluno alunoSalvo = alunoRepository.save(novoAluno);

        // Converte a Entidade para um DTO de Resposta
        return new AlunoResponseDTO(
                alunoSalvo.getId(),
                alunoSalvo.getNome(),
                alunoSalvo.getRa().numero(),        // Extrai o valor do VO
                alunoSalvo.getSaldoCursosExtras()
        );
    }

    @Transactional
    public CursoResponseDTO criarCurso(CursoRequestDTO dto) {
        // Converte o DTO para Entidade
        Curso novoCurso = new Curso();
        novoCurso.setNomeDoCurso(dto.nomeDoCurso());

        // Salva a nova entidade
        Curso cursoSalvo = cursoRepository.save(novoCurso);

        // Converte a Entidade para um DTO de Resposta
        return new CursoResponseDTO(
                cursoSalvo.getId(),
                cursoSalvo.getNomeDoCurso()
        );
    }
}