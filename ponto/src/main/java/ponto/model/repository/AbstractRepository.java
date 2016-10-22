package ponto.model.repository;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ponto.model.domain.Entidade;
import ponto.model.repository.consulta.Consulta;

import com.google.common.base.Preconditions;

@Repository
@SuppressWarnings("unchecked")
public abstract class AbstractRepository<C extends Consulta<? extends Entidade>, T extends Entidade> {

	private static final String OBJETO_NAO_PODE_SER_NULO = "Objeto não pode ser nulo!";

	private @Autowired SessionFactory sessionFactory;

	protected abstract Class<T> getClazz();

	public Long save(T entity) {
		Preconditions.checkNotNull(entity, OBJETO_NAO_PODE_SER_NULO);
//		preencherDataCadastro(entity);
		return (Long) sessionFactory.getCurrentSession().save(entity);
	}

	/*private void preencherDataCadastro(T entity) {
		if (entity.getDataCadastro() == null) {
			entity.setDataCadastro(DateTime.now());
		}
	}*/

	public void update(T entity) {
		Preconditions.checkNotNull(entity, OBJETO_NAO_PODE_SER_NULO);
		sessionFactory.getCurrentSession().update(entity);
	}

	public void saveOrUpdate(T entity) {
		Preconditions.checkNotNull(entity, OBJETO_NAO_PODE_SER_NULO);
//		preencherDataCadastro(entity);
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	public T merge(T entity) {
		Preconditions.checkNotNull(entity, OBJETO_NAO_PODE_SER_NULO);
		return (T) sessionFactory.getCurrentSession().merge(entity);
	}

	public void delete(T entity) {
		Preconditions.checkNotNull(entity, OBJETO_NAO_PODE_SER_NULO);
		sessionFactory.getCurrentSession().delete(entity);
	}

	public Criteria criarCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(getClazz());
	}

	public T load(Long id) {
		Preconditions.checkNotNull(id, "ID deve ser informado!");
		return (T) sessionFactory.getCurrentSession().load(getClazz(), id);
	}

	public T get(Long id) {
		Preconditions.checkNotNull(id, "ID deve ser informado!");
		return (T) sessionFactory.getCurrentSession().get(getClazz(), id);
	}

	public void evict(T entity) {
		Preconditions.checkNotNull(entity, OBJETO_NAO_PODE_SER_NULO);
		sessionFactory.getCurrentSession().evict(entity);
	}

	public void refresh(T entity) {
		Preconditions.checkNotNull(entity, OBJETO_NAO_PODE_SER_NULO);
		sessionFactory.getCurrentSession().refresh(entity);
	}

	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}

	public T buscar(C consulta) {
		Criteria criteria = passosConsulta(consulta);
		return (T) criteria.uniqueResult();
	}

	public List<T> consultar(C consulta) {
		Criteria criteria = passosConsulta(consulta);
		return criteria.list();
	}

	public Long total(C consulta) {
		Criteria criteria = passosConsulta(consulta);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private Criteria passosConsulta(C consulta) {
		Criteria criteria = criarCriteria();
		incluirCriterioPorId(consulta, criteria);
		extrairCriterios(criteria, consulta);
		if (consulta.isDesabilitarFlush()) {
			criteria.setFlushMode(FlushMode.MANUAL);
		}
		if (CollectionUtils.isNotEmpty(consulta.getCampos())) {
			ProjectionList projections = Projections.projectionList();
			for (String campo : consulta.getCampos()) {
				if (campo.contains(".")) {
					String alias = campo.substring(0, campo.indexOf("."));
					if (!consulta.getMapAlias().containsKey(alias)) {
						consulta.addAlias(alias);
						criteria.createAlias(alias, alias,
								JoinType.LEFT_OUTER_JOIN);
						projections.add(Projections.property(campo));
					}
				} else {
					projections.add(Projections.property(campo), campo);
				}
			}
			criteria.setProjection(projections);
			criteria.setResultTransformer(Transformers.aliasToBean(getClazz()));
		}
		return criteria;
	}

	private void incluirCriterioPorId(C consulta, Criteria criteria) {
		if (consulta.getId() != null) {
			addEq(criteria, "id", consulta.getId());
		}
	}

	protected abstract Criteria extrairCriterios(Criteria criteria, C consulta);

	// Helps do Criteria
	protected void addEq(Criteria criteria, String propriedade, Object valor) {
		criteria.add(Restrictions.eq(propriedade, valor));
	}

}
