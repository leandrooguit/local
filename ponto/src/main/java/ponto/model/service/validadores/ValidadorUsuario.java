package ponto.model.service.validadores;

import org.apache.commons.lang3.StringUtils;

import ponto.model.domain.Usuario;
import ponto.util.Mensagens;
import ponto.util.NegocioException;
import ponto.util.validador.Validador;

public class ValidadorUsuario extends Validador {

	public void validarCamposNulosEVazios(Usuario usuario) {

		validarObjetoNuloOuVazio(usuario.getNome(), "Nome");
		validarObjetoNuloOuVazio(usuario.getLogin(), "Login");
	}

	public void validarCampoSenha(Usuario usuario) throws NegocioException {
		validarObjetoNuloOuVazio(usuario.getSenha(), "Senha");
		validarObjetoNuloOuVazio(usuario.getConfirmacaoSenha(),
				"Confirmação de Senha");
		lancarErros();
		if (!StringUtils.equals(usuario.getSenha(),
				usuario.getConfirmacaoSenha())) {
			adicionarErro(Mensagens.SENHAS_NAO_CONFEREM);
		}
	}

	public void validarAlteracaoSenha(Usuario usuario,
			String senhaUsuarioCorrente) throws NegocioException {
		if (!StringUtils.equals(usuario.getSenhaAtual(), senhaUsuarioCorrente)) {
			adicionarErro(Mensagens.SENHA_ATUAL_INVALIDA);
		}
		lancarErros();
		validarCampoSenha(usuario);
	}

}
