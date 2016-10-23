package ponto.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.Configuracao;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.ConfiguracaoRepository;
import ponto.model.repository.consulta.ConsultaConfiguracao;
import ponto.model.service.validadores.ValidadorConfiguracao;
import ponto.util.NegocioException;

@Service
public class ConfiguracaoService extends AbstractService<Configuracao, ConsultaConfiguracao>  {

	@Autowired
	private ConfiguracaoRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	protected AbstractRepository<ConsultaConfiguracao, Configuracao> getRepository() {
		return repository;
	}
	
	@Transactional
	public void salvar(Configuracao configuracao) throws NegocioException {
		ValidadorConfiguracao validador = new ValidadorConfiguracao();
		validador.validarCamposNulosEVazios(configuracao);
		validador.lancarErros();
		configuracao.setUsuario(usuarioService.getRepository().get(
				configuracao.getUsuario().getId()));
		repository.saveOrUpdate(configuracao);
	}
	
	@Transactional
	public void deletar(Configuracao configuracao) {
		repository.delete(configuracao);
	}

}
