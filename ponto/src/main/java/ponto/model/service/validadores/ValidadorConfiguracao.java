package ponto.model.service.validadores;

import ponto.model.domain.Configuracao;
import ponto.util.validador.Validador;

public class ValidadorConfiguracao extends Validador {

	public void validarCamposNulosEVazios(Configuracao configuracao) {
		validarObjetoNuloOuVazio(configuracao.getQuantElementoCartela(), "Qtd elemento na cartela");
		validarObjetoNuloOuVazio(configuracao.getTipoConjunto(), "Tipo conjunto");
		validarObjetoNuloOuVazio(configuracao.getUsuario(), "Usuário");
	}
	
}
