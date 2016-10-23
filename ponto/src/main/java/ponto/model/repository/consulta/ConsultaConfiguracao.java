package ponto.model.repository.consulta;

import ponto.model.domain.Configuracao;

public class ConsultaConfiguracao extends Consulta<Configuracao> {

	private Long idUsuario;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public ConsultaConfiguracao setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
		return this;
	}
	
	public void addCamposLista() {
		addCampo("usuario.id");
		addCampo("usuario.nome");
	}
	
}
