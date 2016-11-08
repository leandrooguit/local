package ponto.model.repository;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ponto.model.domain.TipoConjunto;
import ponto.model.repository.consulta.ConsultaTipoConjunto;

@Repository
public class TipoConjuntoRepository extends AbstractRepository<ConsultaTipoConjunto, TipoConjunto> {

	@Override
	protected Class<TipoConjunto> getClazz() {
		return TipoConjunto.class;
	}

	@Override
	protected Criteria extrairCriterios(Criteria criteria, ConsultaTipoConjunto consulta) {
		if (StringUtils.isNotEmpty(consulta.getDescricao())) {
			criteria.add(Restrictions.eq("descricao", consulta.getDescricao()));
		}
		
		return criteria;
	}
	
}
