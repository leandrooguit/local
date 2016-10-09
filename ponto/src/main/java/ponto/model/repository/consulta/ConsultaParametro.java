package ponto.model.repository.consulta;

import ponto.model.domain.Parametro;

public class ConsultaParametro extends Consulta<Parametro> {

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
