package ponto.model.repository.consulta;

import ponto.model.domain.TipoConjunto;

public class ConsultaTipoConjunto extends Consulta<TipoConjunto> {

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void addCamposLista() {
		addCampo("descricao");
	}
	
}
