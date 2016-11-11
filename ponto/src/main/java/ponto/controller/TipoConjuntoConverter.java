package ponto.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import ponto.model.domain.TipoConjunto;

public class TipoConjuntoConverter implements Converter<String, TipoConjunto> {

	@Override
	public TipoConjunto convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			TipoConjunto tipo = new TipoConjunto();
			tipo.setId(Long.valueOf(id));
			return tipo;
		}
		
		return null;
	}

}
