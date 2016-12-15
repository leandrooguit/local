package ponto.model.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import ponto.model.domain.Abono;
import ponto.model.domain.Jogo;
import ponto.model.repository.consulta.ConsultaAbono;
import ponto.model.repository.consulta.ConsultaJogo;

@Repository
public class JogoRepository extends AbstractRepository<ConsultaJogo, Jogo> {

	@Override
	protected Class<Jogo> getClazz() {
		return Jogo.class;
	}

	@Override
	protected Criteria extrairCriterios(Criteria criteria,
			ConsultaJogo consulta) {
		if (consulta.getIdUsuario() != null) {
			consulta.addAlias("usuario");
			criteria.createAlias("usuario", "usuario", JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.or(
					Restrictions.eq("usuario.id", consulta.getIdUsuario()),
					Restrictions.isNull("usuario")));
		}

		LocalDate mesAno = consulta.getMesAno();
		if (mesAno != null) {
			Type[] types = { IntegerType.INSTANCE, IntegerType.INSTANCE,
					IntegerType.INSTANCE, IntegerType.INSTANCE };
			Integer[] values = { mesAno.getYear(), mesAno.getMonthOfYear() };
			criteria.add(Restrictions
					.sqlRestriction(
							"YEAR(abo_data_inicial) = ? AND MONTH(abo_data_inicial) = ?",
							values, types));
		}
		List<Criterion> disjunctions = new ArrayList<Criterion>();
		LocalDate dataInicial = consulta.getDataInicial();
		if (dataInicial != null) {
			disjunctions.add(Restrictions.ge("dataInicial", dataInicial));
		}

		LocalDate dataFinal = consulta.getDataFinal();
		if (dataFinal != null) {
			disjunctions.add(Restrictions.le("dataFinal", dataFinal));
		}
		if (CollectionUtils.isNotEmpty(disjunctions)) {
			criteria.add(Restrictions.and(disjunctions
					.toArray(new Criterion[disjunctions.size()])));
		}
		if (consulta.isAprovado()) {
			criteria.add(Restrictions.eq("aprovado", true));
		}
		return criteria;
	}
}
