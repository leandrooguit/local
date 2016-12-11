package ponto.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.Bola;
import ponto.model.domain.TipoConjunto;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.BolaRepository;
import ponto.model.repository.consulta.ConsultaBola;
import ponto.model.service.validadores.ValidadorBola;
import ponto.util.NegocioException;

@Service
public class BolaService extends AbstractService<Bola, ConsultaBola> {

	@Autowired
	private BolaRepository repository;
	
	@Autowired
	private TipoConjuntoService tipoConjuntoService;
	
	@Override
	protected AbstractRepository<ConsultaBola, Bola> getRepository() {
		return repository;
	}
	
	@Transactional
	public void salvar(Bola bola) throws NegocioException {
		ValidadorBola validador = new ValidadorBola();
		validador.validarCamposNulosEVazios(bola);
		validador.lancarErros();
		
		Long id = bola.getTipoConjunto().getId();
		TipoConjunto tipoConjunto = tipoConjuntoService.load(id);
		bola.setTipoConjunto(tipoConjunto);
		
		repository.saveOrUpdate(bola);
	}
	
	@Transactional
	public void deletar(Bola bola) {
		repository.delete(bola);
	}

}
