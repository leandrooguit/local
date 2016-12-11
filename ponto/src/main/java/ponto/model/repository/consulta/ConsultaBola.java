package ponto.model.repository.consulta;

import ponto.model.domain.Bola;
import ponto.model.domain.TipoConjunto;

public class ConsultaBola extends Consulta<Bola> {

	private TipoConjunto tipoConjunto;

	public TipoConjunto getTipoConjunto() {
		return tipoConjunto;
	}

	public void setTipoConjunto(TipoConjunto tipoConjunto) {
		this.tipoConjunto = tipoConjunto;
	}
	
}
