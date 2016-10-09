package ponto.util.validador;

import org.apache.commons.lang3.StringUtils;

import ponto.util.NegocioException;

public class Validador {

	protected NegocioException negocioException = new NegocioException();

	public void validarObjetoNuloOuVazio(Object objeto, String nomeCampo) {
		if (objeto == null
				|| (objeto instanceof String && StringUtils
						.isBlank((String) objeto))) {
			adicionarErro("O campo " + nomeCampo
					+ " deve ser preenchido obrigatoriamente");
		}
	}

	public void adicionarErro(String mensagem) {
		negocioException.getMensagens().add(mensagem);
	}

	public void lancarErros() throws NegocioException {
		if (!negocioException.getMensagens().isEmpty()) {
			throw negocioException;
		}
	}

}
