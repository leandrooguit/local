package ponto.util;

import java.util.LinkedList;
import java.util.List;

public class NegocioException extends Exception {

	private static final long serialVersionUID = -6302382724888956434L;

	private List<String> mensagens = new LinkedList<String>();

	public List<String> getMensagens() {
		return this.mensagens;
	}

	public NegocioException(String mensagem) {
		this.mensagens.add(mensagem);
	}

	public NegocioException() {

	}

	public String getMensagensTratadas() {
		StringBuilder sb = new StringBuilder();

		for (String mensagem : mensagens) {
			sb.append(" - ");
			sb.append(mensagem);
			sb.append(" <br /> ");
		}

		return sb.toString();
	}

}
