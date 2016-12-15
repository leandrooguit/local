package ponto.model.repository.consulta;

import ponto.model.domain.TipoConjunto;

public class ConsultaTipoConjunto extends Consulta<TipoConjunto> {

	private Long id;
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void addCamposLista() {
		addCampo("id");
		addCampo("descricao");
	}
	
}
