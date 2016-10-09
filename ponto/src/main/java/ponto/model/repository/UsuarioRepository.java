package ponto.model.repository;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import ponto.model.domain.Usuario;
import ponto.model.repository.consulta.ConsultaUsuario;

@Repository
public class UsuarioRepository extends
		AbstractRepository<ConsultaUsuario, Usuario> {

	@Override
	protected Class<Usuario> getClazz() {
		return Usuario.class;
	}

	@Override
	protected Criteria extrairCriterios(Criteria criteria,
			ConsultaUsuario consulta) {
		if (StringUtils.isNotBlank(consulta.getLogin())) {
			addEq(criteria, "login", consulta.getLogin());
		}

		if (StringUtils.isNotBlank(consulta.getSenha())) {
			addEq(criteria, "senha", consulta.getSenha());
		}

		if (StringUtils.isNotBlank(consulta.getCampoOrdenacaoAsc())) {
			criteria.addOrder(Order.asc(consulta.getCampoOrdenacaoAsc()));
		}

		return criteria;
	}

}
