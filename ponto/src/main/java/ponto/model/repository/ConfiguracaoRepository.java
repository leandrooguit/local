package ponto.model.repository;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import ponto.model.domain.Configuracao;
import ponto.model.repository.consulta.ConsultaConfiguracao;

@Repository
public class ConfiguracaoRepository extends AbstractRepository<ConsultaConfiguracao, Configuracao> {

	@Override
	protected Class<Configuracao> getClazz() {
		return Configuracao.class;
	}

	@Override
	protected Criteria extrairCriterios(Criteria criteria, ConsultaConfiguracao consulta) {
		if (consulta.getIdUsuario() != null) {
			consulta.addAlias("usuario");
			
			criteria.createAlias("usuario", "usuario", JoinType.INNER_JOIN);
			
			criteria.add(Restrictions.or(Restrictions.eq("usuario.id", consulta.getIdUsuario()),
					Restrictions.isNull("usuario")));
		}
		
		return criteria;
	}

}
