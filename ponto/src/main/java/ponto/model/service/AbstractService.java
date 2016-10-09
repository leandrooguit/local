package ponto.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.Entidade;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.consulta.Consulta;
import ponto.util.NegocioException;

@Service
public abstract class AbstractService<T extends Entidade, C extends Consulta<T>> {

	protected abstract AbstractRepository<C, T> getRepository();

	@Transactional(readOnly = true)
	public T buscar(C consulta) {
		return (T) getRepository().buscar(consulta);
	}

	@Transactional(readOnly = true)
	public List<T> consultar(C consulta) {
		return getRepository().consultar(consulta);
	}

	@Transactional(readOnly = true)
	public T load(Long id) {
		return getRepository().load(id);
	}

	@Transactional(readOnly = true)
	public T get(Long id) {
		return getRepository().get(id);
	}

	@Transactional(readOnly = true)
	private void verificarVersao(T entity) throws NegocioException {
		// @SuppressWarnings("unchecked")
		// T entityId = getDAO().buscarPorId((ID) entity.getId());
		//
		// if (entity.getVersao() < entityId.getVersao()) {
		// throw new NegocioException("Ops!, por favor repita a operação.");
		// }
	}

}
