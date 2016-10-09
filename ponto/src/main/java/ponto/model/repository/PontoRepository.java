package ponto.model.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import ponto.model.domain.Ponto;
import ponto.model.repository.consulta.ConsultaPonto;

@Repository
public class PontoRepository extends AbstractRepository<ConsultaPonto, Ponto> {

	@Override
	protected Class<Ponto> getClazz() {
		return Ponto.class;
	}

	@Override
	protected Criteria extrairCriterios(Criteria criteria,
			ConsultaPonto consulta) {
		if (consulta.getIdUsuario() != null) {
			criteria.createAlias("usuario", "usuario");
			criteria.add(Restrictions.eq("usuario.id", consulta.getIdUsuario()));
		}
		// ProjectionList projections = Projections.projectionList();
		// projections.add(Projections.property("entrada1"), "entrada1");
		// projections.add(Projections.property("entrada2"), "entrada2");
		// projections.add(Projections.property("saida1"), "saida1");
		// projections.add(Projections.property("saida2"), "saida2");
		// projections.add(Projections.property("total"), "total");
		// criteria.setProjection(projections);
		// criteria.setResultTransformer(Transformers.aliasToBean(Ponto.class));
		if (consulta.getDataCadastro() != null) {
			Type[] types = { IntegerType.INSTANCE, IntegerType.INSTANCE,
					IntegerType.INSTANCE };
			Integer[] values = { consulta.getDataCadastro().getYear(),
					consulta.getDataCadastro().getMonthOfYear(),
					consulta.getDataCadastro().getDayOfMonth() };
			criteria.add(Restrictions
					.sqlRestriction(
							"YEAR(pon_data_cadastro) = ? AND MONTH(pon_data_cadastro) = ? AND DAYOFMONTH(pon_data_cadastro) = ?",
							values, types));
		}
		if (consulta.getMesAno() != null) {
			Type[] types = { IntegerType.INSTANCE, IntegerType.INSTANCE };
			Integer[] values = { consulta.getMesAno().getYear(),
					consulta.getMesAno().getMonthOfYear() };
			criteria.add(Restrictions
					.sqlRestriction(
							"YEAR(pon_data_cadastro) = ? AND MONTH(pon_data_cadastro) = ?",
							values, types));
		}
		if (consulta.getMes() != null) {
			criteria.add(Restrictions.sqlRestriction(
					"MONTH(pon_data_cadastro) = ?", consulta.getMes(),
					IntegerType.INSTANCE));
		}
		if (consulta.getAno() != null) {
			criteria.add(Restrictions.sqlRestriction(
					"YEAR(pon_data_cadastro) = ?", consulta.getAno(),
					IntegerType.INSTANCE));
		}
		if (consulta.getDataCadastroInicial() != null) {
			criteria.add(Restrictions.ge("dataCadastro",
					consulta.getDataCadastroInicial()));
		}
		if (consulta.getDataCadastroFinal() != null) {
			criteria.add(Restrictions.le("dataCadastro",
					consulta.getDataCadastroFinal()));
		}
		criteria.addOrder(Property.forName("dataCadastro").asc());
		return criteria;
	}
}
