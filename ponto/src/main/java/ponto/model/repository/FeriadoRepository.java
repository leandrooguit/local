package ponto.model.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import ponto.model.domain.Feriado;
import ponto.model.repository.consulta.ConsultaFeriado;

@Repository
public class FeriadoRepository extends
		AbstractRepository<ConsultaFeriado, Feriado> {

	@Override
	protected Class<Feriado> getClazz() {
		return Feriado.class;
	}

	@Override
	protected Criteria extrairCriterios(Criteria criteria,
			ConsultaFeriado consulta) {
		LocalDate diaMes = consulta.getMes();
		if (diaMes != null) {
			criteria.add(Restrictions.sqlRestriction("MONTH(fer_data) = ?",
					diaMes.getMonthOfYear(), IntegerType.INSTANCE));
		}
		if (consulta.getLocais() != null) {
			criteria.add(Restrictions.in("local", consulta.getLocais()));
		}

		LocalDate dataInicialFixa = consulta.getDataInicialFixa();
		if (dataInicialFixa != null) {
			criteria.add(Restrictions.ge("data", dataInicialFixa));
		}

		List<Criterion> disjunctions = new ArrayList<Criterion>();
		LocalDate dataInicial = consulta.getDataInicial();
		if (dataInicial != null) {
			disjunctions.add(Restrictions.and(
					Restrictions.ge("data", dataInicial),
					Restrictions.eq("persistente", false)));
		}

		LocalDate dataFinal = consulta.getDataFinal();
		if (dataFinal != null) {
			disjunctions.add(Restrictions.and(
					Restrictions.le("data", dataFinal),
					Restrictions.eq("persistente", false)));
		}
		if (CollectionUtils.isNotEmpty(disjunctions)) {
			criteria.add(Restrictions.or(Restrictions.and(disjunctions
					.toArray(new Criterion[disjunctions.size()])), Restrictions
					.eq("persistente", true)));
		}
		return criteria;
	}
}
