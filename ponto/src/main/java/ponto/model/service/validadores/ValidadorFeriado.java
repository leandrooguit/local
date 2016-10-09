package ponto.model.service.validadores;

import ponto.model.domain.Feriado;
import ponto.util.validador.Validador;

public class ValidadorFeriado extends Validador {

	public void validarCamposNulosEVazios(Feriado feriado) {

		validarObjetoNuloOuVazio(feriado.getNome(), "Nome");
		validarObjetoNuloOuVazio(feriado.getData(), "Dia/Mês");
		validarObjetoNuloOuVazio(feriado.getLocal(), "Localidade");
	}

}
