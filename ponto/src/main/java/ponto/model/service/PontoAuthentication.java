package ponto.model.service;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ponto.model.domain.Autorizacao;
import ponto.model.domain.Usuario;

public class PontoAuthentication implements Authentication {

	private static final long serialVersionUID = 8852747571567615255L;

	private final Usuario usuario;
	private boolean autenticado;
	private Set<Autorizacao> autorizacoes;

	public PontoAuthentication(Usuario usuario, Set<Autorizacao> autorizacoes) {
		this.usuario = usuario;
		this.autorizacoes = autorizacoes;
	}

	public String getName() {
		return usuario != null ? usuario.getLogin() : null;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return autorizacoes;
	}

	public Object getCredentials() {
		return usuario != null ? usuario.getSenha() : null;
	}

	public Object getDetails() {
		return usuario;
	}

	public Object getPrincipal() {
		return usuario != null ? usuario : null;
	}

	public boolean isAuthenticated() {
		return this.autenticado;
	}

	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {
		this.autenticado = isAuthenticated;

	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
