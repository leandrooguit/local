package ponto.model.repository.consulta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ponto.model.domain.Entidade;

public abstract class Consulta<T extends Entidade> {

	private Long id;
	private List<String> campos;
	private boolean desabilitarFlush;
	private Map<String, String> mapAlias = new HashMap<String, String>();
	private String campoOrdenacaoAsc;

	public List<String> addCampo(String campo) {
		if (campos == null) {
			campos = new ArrayList<String>();
		}
		campos.add(campo);
		return campos;
	}

	public void addAlias(String alias) {
		if (!mapAlias.containsKey(alias)) {
			mapAlias.put(alias, alias);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getCampos() {
		return campos;
	}

	public void setCampos(List<String> campos) {
		this.campos = campos;
	}

	public boolean isDesabilitarFlush() {
		return desabilitarFlush;
	}

	public void setDesabilitarFlush(boolean desabilitarFlush) {
		this.desabilitarFlush = desabilitarFlush;
	}

	public Map<String, String> getMapAlias() {
		return mapAlias;
	}

	public void setMapAlias(Map<String, String> mapAlias) {
		this.mapAlias = mapAlias;
	}

	public String getCampoOrdenacaoAsc() {
		return campoOrdenacaoAsc;
	}

	public void setCampoOrdenacaoAsc(String campoOrdenacaoAsc) {
		this.campoOrdenacaoAsc = campoOrdenacaoAsc;
	}

}
