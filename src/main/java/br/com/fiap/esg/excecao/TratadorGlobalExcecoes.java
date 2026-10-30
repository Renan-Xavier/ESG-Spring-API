package br.com.fiap.esg.excecao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Tratamento global de erros da API usando ProblemDetail
@RestControllerAdvice
public class TratadorGlobalExcecoes {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail tratarErroValidacao(MethodArgumentNotValidException ex) {
        ProblemDetail detalheProblema = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Verifique os campos preenchidos incorretamente"
        );
        detalheProblema.setTitle("Erro de validação nos dados enviados");

        Map<String, String> errosCampos = new HashMap<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            errosCampos.put(erro.getField(), erro.getDefaultMessage());
        }

        detalheProblema.setProperty("errosCampos", errosCampos);
        return detalheProblema;
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        ProblemDetail detalheProblema = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        detalheProblema.setTitle("Registro não encontrado");
        return detalheProblema;
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ProblemDetail tratarRegraDeNegocio(RegraDeNegocioException ex) {
        ProblemDetail detalheProblema = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        detalheProblema.setTitle("Regra de negócio violada");
        return detalheProblema;
    }
}
