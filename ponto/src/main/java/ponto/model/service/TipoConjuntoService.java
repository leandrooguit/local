package ponto.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.TipoConjunto;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.TipoConjuntoRepository;
import ponto.model.repository.consulta.ConsultaTipoConjunto;
import ponto.model.service.validadores.ValidadorTipoConjunto;
import ponto.util.NegocioException;

@Service
public class TipoConjuntoService extends AbstractService<TipoConjunto, ConsultaTipoConjunto> {

	@Autowired
	private TipoConjuntoRepository repository;
	
	@Override
	protected AbstractRepository<ConsultaTipoConjunto, TipoConjunto> getRepository() {
		return repository;
	}
	
	@Transactional
	public void salvar(TipoConjunto tipoConjunto) throws NegocioException {
		ValidadorTipoConjunto validador = new ValidadorTipoConjunto();
		validador.validarCamposNulosEVazios(tipoConjunto);
		validador.lancarErros();
		repository.saveOrUpdate(tipoConjunto);
	}
	
	@Transactional
	public void deletar(TipoConjunto tipoConjunto) {
		repository.delete(tipoConjunto);
	}
	
	@Transactional(readOnly = true)
	public TipoConjunto load(Long id) {
		ConsultaTipoConjunto consulta = new ConsultaTipoConjunto();
		consulta.setId(id);
		return consultar(consulta).get(0);
	}
	
	@Transactional(readOnly = true)
	public List<TipoConjunto> buscarTodos(ConsultaTipoConjunto consulta) {
		return consultar(consulta);
	}
	
}
