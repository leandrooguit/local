package ponto.model.repository;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import ponto.model.domain.Parametro;
import ponto.model.repository.consulta.ConsultaParametro;

@Repository
public class ParametroRepository extends
		AbstractRepository<ConsultaParametro, Parametro> {

	@Override
	protected Class<Parametro> getClazz() {
		return Parametro.class;
	}

	@Override
	protected Criteria extrairCriterios(Criteria criteria,
			ConsultaParametro consulta) {
		if (consulta.getNome() != null) {
			addEq(criteria, "nome", consulta.getNome());
		}
		return criteria;
	}
}
