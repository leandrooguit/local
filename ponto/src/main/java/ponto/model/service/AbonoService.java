package ponto.model.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.Abono;
import ponto.model.repository.AbonoRepository;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.consulta.ConsultaAbono;
import ponto.model.service.validadores.ValidadorAbono;
import ponto.util.NegocioException;

@Service
public class AbonoService extends AbstractService<Abono, ConsultaAbono> {

	@Autowired
	private AbonoRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected AbstractRepository<ConsultaAbono, Abono> getRepository() {
		return repository;
	}

	@Transactional
	// @CacheEvict(value = { "abonosPorMesEUsuarioCorrente", "abonos" },
	// allEntries = true, beforeInvocation = true)
	public void manterAbono(Abono abono) throws NegocioException {
		ValidadorAbono validador = new ValidadorAbono();
		validador.validarCamposNulosEVazios(abono);
		validador.validarDatas(abono);
		validador.lancarErros();
		abono.setUsuario(usuarioService.getRepository().get(
				abono.getUsuario().getId()));
		if (abono.getDataFinal() != null) {
			abono.setQtdHoras(null);
		}
		repository.saveOrUpdate(abono);
	}

	@Transactional
	public void aprovar(Long id) {
		Abono abono = load(id);
		abono.setAprovado(true);
		repository.update(abono);
	}

	@Transactional(readOnly = true)
	public List<Abono> abonosPorUsuario(Long idUsuario) {
		ConsultaAbono consulta = new ConsultaAbono();
		consulta.setIdUsuario(idUsuario).addCamposLista();
		return consultar(consulta);
	}

	@Transactional
	public void deletar(Abono abono) {
		repository.delete(abono);
	}

	@Transactional(readOnly = true)
	// @Cacheable(value = "abonosPorMesEUsuarioCorrente", key =
	// "T(java.lang.String).valueOf(#mes)")
	public List<Abono> abonosPorMesEUsuario(LocalDate dataBase, Long idUsuario,
			boolean datasAPartir) {
		ConsultaAbono consultaAbono = new ConsultaAbono();
		consultaAbono.setAprovado(true);
		consultaAbono.setIdUsuario(idUsuario);
		if (datasAPartir) {
			consultaAbono.setDataInicial(dataBase);
		} else {
			consultaAbono.setMesAno(dataBase);
		}
		List<Abono> abonos = consultar(consultaAbono);
		return abonos;
	}

	@Transactional(readOnly = true)
	// @Cacheable("abonos")
	public List<Abono> buscarTodosOsAbonos() {
		return consultar(new ConsultaAbono());
	}

}
