package ponto.model.service.validadores;

import ponto.model.domain.Abono;
import ponto.util.validador.Validador;

public class ValidadorAbono extends Validador {

	public void validarCamposNulosEVazios(Abono abono) {
		validarObjetoNuloOuVazio(abono.getDescricao(), "Descrição");
		validarObjetoNuloOuVazio(abono.getDataInicial(), "Data");
		if (abono.getDataFinal() == null) {
			validarObjetoNuloOuVazio(abono.getQtdHoras(), "Quantidade de horas");
		} else {
			validarObjetoNuloOuVazio(abono.getDataFinal(), "Data final");
		}
		validarObjetoNuloOuVazio(abono.getUsuario(), "Usuário");
	}

	public void validarDatas(Abono abono) {
		if (abono.getDataFinal() != null && abono.getDataInicial() != null
				&& abono.getDataInicial().isAfter(abono.getDataFinal())) {
			adicionarErro("Data inicial deve ser antes da final");
		}
	}

}
