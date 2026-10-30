package br.com.fiap.esg.excecao;

// Excecao lancada quando um fornecedor ou relatorio de emissao nao e encontrado
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
