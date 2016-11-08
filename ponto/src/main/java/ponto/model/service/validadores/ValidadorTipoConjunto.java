package ponto.model.service.validadores;

import ponto.model.domain.TipoConjunto;
import ponto.util.validador.Validador;

public class ValidadorTipoConjunto extends Validador {

	public void validarCamposNulosEVazios(TipoConjunto tipoConjunto) {
		validarObjetoNuloOuVazio(tipoConjunto.getDescricao(), "Descrição");
	}
	
}
