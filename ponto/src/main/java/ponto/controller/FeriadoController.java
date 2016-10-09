package ponto.controller;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ponto.controller.util.Caminhos;
import ponto.controller.util.CustomLocalDateEditor;
import ponto.model.domain.Autorizacao;
import ponto.model.domain.ELocal;
import ponto.model.domain.Feriado;
import ponto.model.repository.consulta.ConsultaFeriado;
import ponto.model.service.FeriadoService;
import ponto.util.Mensagens;
import ponto.util.NegocioException;

@Controller
@RequestMapping("/feriados")
@SessionAttributes(value = "feriado", types = Feriado.class)
public class FeriadoController {

	@Autowired
	private FeriadoService feriadoService;

	@Secured(Autorizacao.ROLE_ADMIN)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView doRegistrar(@ModelAttribute Feriado feriado) {
		ModelAndView mv = new ModelAndView(Caminhos.FERIADOS_VISUALIZAR);
		try {
			feriadoService.manterFeriado(feriado);
			mv.setViewName("redirect:feriados");
		} catch (NegocioException e) {
			mv.addObject(Mensagens.TIPO_DANGER, e.getMensagens());
			addObjects(mv, feriado, null);
		}
		return mv;
	}

	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public ModelAndView pontos(@ModelAttribute ConsultaFeriado consulta) {
		ModelAndView mv = new ModelAndView(Caminhos.FERIADOS_VISUALIZAR);
		addObjects(mv, null, consulta);
		return mv;
	}

	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView feriados() {
		ModelAndView mv = new ModelAndView(Caminhos.FERIADOS_VISUALIZAR);
		addObjects(mv, null, null);
		return mv;
	}

	private void addObjects(ModelAndView mv, Feriado feriado,
			ConsultaFeriado consulta) {
		mv.addObject("feriados",
				consulta == null ? feriadoService.buscarTodosOsFeriados()
						: feriadoService.consultar(consulta));
		mv.addObject("localidades", ELocal.values());
		mv.addObject("feriado", feriado == null ? new Feriado() : feriado);
		mv.addObject("consulta", consulta == null ? new ConsultaFeriado()
				: consulta);
	}

	@Secured(Autorizacao.ROLE_ADMIN)
	@RequestMapping(value = "/{id}/editar", method = RequestMethod.GET)
	public ModelAndView feriados(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView(Caminhos.FERIADOS_VISUALIZAR);
		ConsultaFeriado consulta = new ConsultaFeriado();
		consulta.setId(Long.valueOf(id));
		addObjects(mv, feriadoService.buscar(consulta), null);
		return mv;
	}

	@Secured(Autorizacao.ROLE_ADMIN)
	@RequestMapping(value = "/{id}/deletar", method = RequestMethod.GET)
	public ModelAndView deletar(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView(Caminhos.FERIADOS_VISUALIZAR);
		feriadoService.deletar(Long.valueOf(id));
		addObjects(mv, null, null);
		return mv;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class,
				new CustomLocalDateEditor());
	}

}
