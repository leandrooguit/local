package ponto.model.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.Autorizacao;
import ponto.model.domain.Usuario;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.UsuarioRepository;
import ponto.model.repository.consulta.ConsultaUsuario;
import ponto.model.service.validadores.ValidadorUsuario;
import ponto.util.Mensagens;
import ponto.util.NegocioException;

@Service
public class UsuarioService extends AbstractService<Usuario, ConsultaUsuario> {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private AutorizacaoService autorizacaoService;

	@Transactional(readOnly = true)
	public Usuario getUsuarioCorrente() {
		Usuario usuario = getUsuarioCorrenteSpring();
		if (usuario != null) {
			return usuarioPorId(usuario.getId());
		}
		return null;
	}

	@Transactional(readOnly = true)
	// @Cacheable(value = "usuarioPorId", key =
	// "T(java.lang.String).valueOf(#id)")
	public Usuario usuarioPorId(Long id) {
		ConsultaUsuario consulta = new ConsultaUsuario();
		consulta.setId(id);
		return buscar(consulta);
	}

	public Usuario getUsuarioCorrenteSpring() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return (Usuario) authentication.getPrincipal();
		}
		return null;
	}

	public void updateUsuarioCorrenteSpring(Usuario usuario) {
		PontoAuthentication authentication = new PontoAuthentication(usuario,
				usuario.getAutorizacoes());
		authentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Transactional(readOnly = true)
	public Usuario logar(String email, String senha) {
		if (StringUtils.isBlank(email) || StringUtils.isBlank(senha)) {
			return null;
		}
		ConsultaUsuario consulta = new ConsultaUsuario();
		consulta.setLogin(email).setSenha(senha);
		Usuario usuario = repository.buscar(consulta);
		if (usuario != null) {
			Hibernate.initialize(usuario.getAutorizacoes());
		}
		return usuario;
	}

	@Transactional
	@CacheEvict(value = "usuariosCombobox", allEntries = true, beforeInvocation = true)
	public void salvar(Usuario usuario) throws NegocioException {

		ValidadorUsuario validador = new ValidadorUsuario();
		validador.validarCamposNulosEVazios(usuario);
		if (usuario.getId() == null) {
			validador.validarCampoSenha(usuario);
		}
		String login = usuario.getLogin();
		if ((usuario.getId() == null || !login
				.equalsIgnoreCase(getUsuarioCorrenteSpring().getLogin()))
				&& loginJaCadastrado(login)) {
			validador.adicionarErro(Mensagens.LOGIN_JA_CADASTRADO);
		}
		validador.lancarErros();
		if (usuario.getId() == null) {
			autorizacaoService.concederAutorizacao(usuario,
					Autorizacao.ROLE_USER);
			repository.save(usuario);
		} else {
			repository.update(usuario);
			updateUsuarioCorrenteSpring(usuario);
		}
	}

	private boolean loginJaCadastrado(String login) {
		ConsultaUsuario consulta = new ConsultaUsuario();
		consulta.setLogin(login);
		consulta.setDesabilitarFlush(true);
		if (repository.total(consulta) > 0) {
			return true;
		}
		return false;
	}

	@Transactional
	public void alterarSenha(Usuario usuario) throws NegocioException {
		ValidadorUsuario validador = new ValidadorUsuario();
		validador.validarAlteracaoSenha(usuario, getUsuarioCorrenteSpring()
				.getSenha());
		validador.lancarErros();
		repository.update(usuario);
		updateUsuarioCorrenteSpring(usuario);
	}

	@Transactional(readOnly = true)
	@Cacheable("usuariosCombobox")
	public List<Usuario> consultarUsuariosParaCombobox() {
		ConsultaUsuario consulta = new ConsultaUsuario();
		consulta.addCamposCombobox();
		return consultar(consulta);
	}

	@Override
	protected AbstractRepository<ConsultaUsuario, Usuario> getRepository() {
		return repository;
	}

}
