package ponto.model.repository.consulta;

import ponto.model.domain.Usuario;

public class ConsultaUsuario extends Consulta<Usuario> {

	private String login;
	private String senha;

	public void addCamposCombobox() {
		addCampo("id");
		addCampo("nome");
		setCampoOrdenacaoAsc("nome");
	}

	public String getLogin() {
		return login;
	}

	public ConsultaUsuario setLogin(String login) {
		this.login = login;
		return this;
	}

	public String getSenha() {
		return senha;
	}

	public ConsultaUsuario setSenha(String senha) {
		this.senha = senha;
		return this;
	}

}
