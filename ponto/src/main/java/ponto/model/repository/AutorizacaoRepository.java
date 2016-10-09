package ponto.model.repository;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ponto.model.domain.Autorizacao;

@Repository
public class AutorizacaoRepository {

	private @Autowired SessionFactory sessionFactory;

	public Autorizacao buscarAutorizacaoPorNome(String nome) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Autorizacao.class);
		criteria.add(Restrictions.eq("autorizacao", nome));
		return (Autorizacao) criteria.uniqueResult();
	}

}
