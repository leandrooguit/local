package ponto.model.service.validadores;

import ponto.model.domain.Bola;
import ponto.util.validador.Validador;

public class ValidadorBola extends Validador {

	public void validarCamposNulosEVazios(Bola bola) {
		validarObjetoNuloOuVazio(bola.getNome(), "Nome");
		validarObjetoNuloOuVazio(bola.getNumero(), "Número");
		validarObjetoNuloOuVazio(bola.getTipoConjunto(), "Tipo conjunto");
	}
	
}
