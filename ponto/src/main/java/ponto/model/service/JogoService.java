package ponto.model.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ponto.controller.util.Caminhos;
import ponto.model.domain.Abono;
import ponto.model.domain.Autorizacao;
import ponto.model.domain.Jogo;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.JogoRepository;
import ponto.model.repository.consulta.ConsultaAbono;
import ponto.model.repository.consulta.ConsultaJogo;
import ponto.model.service.validadores.ValidadorAbono;
import ponto.util.Mensagens;
import ponto.util.NegocioException;

@Service
public class JogoService extends AbstractService<Jogo, ConsultaJogo> {

	@Autowired
	private JogoRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected AbstractRepository<ConsultaJogo, Jogo> getRepository() {
		return repository;
	}
	
	@Transactional
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
		///repository.saveOrUpdate(abono);
	}

	@Transactional
	public void aprovar(Long id) {
		Jogo abono = load(id);
		repository.update(abono);
	}

	@Transactional(readOnly = true)
	public List<Abono> abonosPorUsuario(Long idUsuario) {
		ConsultaAbono consulta = new ConsultaAbono();
		consulta.setIdUsuario(idUsuario).addCamposLista();
		return null;//consultar(consulta);
	}

	@Transactional
	public void deletar(Abono abono) {
	//	repository.delete(abono);
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
		List<Abono> abonos =null; //consultar(consultaAbono);
		return abonos;
	}

	@Transactional(readOnly = true)
	// @Cacheable("abonos")
	public List<Abono> buscarTodosOsAbonos() {
		return null;//consultar(new ConsultaAbono());
	}

}
