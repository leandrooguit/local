package ponto.model.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.ELocal;
import ponto.model.domain.Feriado;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.FeriadoRepository;
import ponto.model.repository.consulta.ConsultaFeriado;
import ponto.model.service.validadores.ValidadorFeriado;
import ponto.util.NegocioException;

@Service
public class FeriadoService extends AbstractService<Feriado, ConsultaFeriado> {

	@Autowired
	private FeriadoRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected AbstractRepository<ConsultaFeriado, Feriado> getRepository() {
		return repository;
	}

	@Transactional
	// @CacheEvict(value = { "feriados", "feriadosPorMesEUsuarioCorrente" },
	// allEntries = true, beforeInvocation = true)
	public void manterFeriado(Feriado feriado) throws NegocioException {
		ValidadorFeriado validador = new ValidadorFeriado();
		validador.validarCamposNulosEVazios(feriado);
		validador.lancarErros();
		repository.saveOrUpdate(feriado);
	}

	@Transactional
	public void deletar(Long id) {
		repository.delete(repository.load(id));
	}

	public List<Feriado> feriadosPorMesEUsuarioCorrente(LocalDate mes) {
		return feriadosPorMesEUsuarioCorrente(mes, false);
	}

	// @Cacheable(value = "feriadosPorMesEUsuarioCorrente", key =
	// "T(java.lang.String).valueOf(#mes)")
	@Transactional(readOnly = true)
	public List<Feriado> feriadosPorMesEUsuarioCorrente(LocalDate dataBase,
			boolean dataAPartir) {
		ConsultaFeriado consulta = new ConsultaFeriado();
		if (dataAPartir) {
			consulta.setDataInicialFixa(dataBase);
		} else {
			consulta.setMes(dataBase);
		}
		/*consulta.setLocais(new ELocal[] { ELocal.GERAL,
				usuarioService.getUsuarioCorrenteSpring().getLocal() });*/
		List<Feriado> feriados = consultar(consulta);
		return feriados;
	}

	@Transactional(readOnly = true)
	// @Cacheable("feriados")
	public List<Feriado> buscarTodosOsFeriados() {
		return consultar(new ConsultaFeriado());
	}

}
