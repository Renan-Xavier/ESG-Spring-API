package br.com.fiap.esg.excecao;

// Excecao lancada quando uma regra de negocio ESG e violada
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
