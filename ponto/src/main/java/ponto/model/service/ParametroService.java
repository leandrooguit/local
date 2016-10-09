package ponto.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.Parametro;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.ParametroRepository;
import ponto.model.repository.consulta.ConsultaParametro;

@Service
public class ParametroService extends
		AbstractService<Parametro, ConsultaParametro> {

	@Autowired
	private ParametroRepository parametroRepository;

	@Override
	protected AbstractRepository<ConsultaParametro, Parametro> getRepository() {
		return parametroRepository;
	}

	@Transactional(readOnly = true)
	public String getValorParametro(String nome) {
		ConsultaParametro consulta = new ConsultaParametro();
		consulta.setNome(nome);
		Parametro p = parametroRepository.buscar(consulta);
		return p == null ? null : p.getValor();
	}

	@Transactional
	public void criar(Parametro parametro) {
		parametroRepository.save(parametro);
	}

}
