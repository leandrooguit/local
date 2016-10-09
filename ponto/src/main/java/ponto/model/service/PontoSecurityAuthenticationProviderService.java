package ponto.model.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import ponto.model.domain.Autorizacao;
import ponto.model.domain.Usuario;

@Component
public class PontoSecurityAuthenticationProviderService implements
		AuthenticationProvider {

	private @Autowired UsuarioService usuarioService;

	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
		Usuario usuario = usuarioService.logar(token.getName(), token
				.getCredentials().toString());

		if (usuario == null) {
			return null;
		}

		Set<Autorizacao> autorizacoes = usuario.getAutorizacoes();
		PontoAuthentication authentication = new PontoAuthentication(usuario,
				autorizacoes);
		authentication.setAuthenticated(usuario != null);
		return authentication;
	}

	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication));
	}
}
