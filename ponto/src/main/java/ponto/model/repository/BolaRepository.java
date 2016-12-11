package ponto.model.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import ponto.model.domain.Bola;
import ponto.model.repository.consulta.ConsultaBola;

@Repository
public class BolaRepository extends AbstractRepository<ConsultaBola, Bola> {

	@Override
	protected Class<Bola> getClazz() {
		return Bola.class;
	}

	@Override
	protected Criteria extrairCriterios(Criteria criteria, ConsultaBola consulta) {
		if (consulta.getTipoConjunto() != null) {	
			consulta.addAlias("tipoConjunto");
			criteria.createAlias("tipoConjunto", "tipoConjunto", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("tipoConjunto.id", consulta.getTipoConjunto().getId()));
		}
		
		return criteria;
	}
	
}
