package ponto.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ponto.controller.util.Caminhos;
import ponto.model.domain.Autorizacao;
import ponto.model.domain.TipoConjunto;
import ponto.model.repository.consulta.ConsultaTipoConjunto;
import ponto.model.service.TipoConjuntoService;
import ponto.util.Mensagens;
import ponto.util.NegocioException;

@Controller
@RequestMapping("/tipoConjuntos")
@SessionAttributes(value = "tipoConjunto", types = TipoConjunto.class)
public class TipoConjuntoController {

	@Autowired
	private TipoConjuntoService tipoConjuntoService;
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView tipoConjunto() {
		ModelAndView mv = new ModelAndView(Caminhos.TIPO_CONJUNTO_VISUALIZAR);
		TipoConjunto tipoConjunto = new TipoConjunto();
		mv.addObject("tipoConjunto", tipoConjunto);
		addObjects(mv, null);
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public ModelAndView tipoConjunto(@ModelAttribute ConsultaTipoConjunto consulta) {
		ModelAndView mv = new ModelAndView(Caminhos.TIPO_CONJUNTO_VISUALIZAR);
		addObjects(mv, consulta);
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView salvar(@ModelAttribute TipoConjunto tipoConjunto,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(Caminhos.TIPO_CONJUNTO_VISUALIZAR);
		try {
			tipoConjuntoService.salvar(tipoConjunto);
			mv.setViewName("redirect:tipoConjuntos");
		} catch (NegocioException e) {
			mv.addObject(Mensagens.TIPO_DANGER, e.getMensagens());
			mv.addObject("tipoConjunto", tipoConjunto);
			addObjects(mv, null);
		}
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/{id}/editar", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView(Caminhos.TIPO_CONJUNTO_VISUALIZAR);
		ConsultaTipoConjunto consulta = new ConsultaTipoConjunto();
		consulta.setId(Long.valueOf(id));
		mv.addObject("tipoConjunto", tipoConjuntoService.buscar(consulta));
		addObjects(mv, null);
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/{id}/deletar", method = RequestMethod.GET)
	public ModelAndView deletar(@PathVariable("id") String id,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(Caminhos.TIPO_CONJUNTO_VISUALIZAR);
		TipoConjunto tipoConjunto = tipoConjuntoService.get(Long.valueOf(id));
		tipoConjuntoService.deletar(tipoConjunto);
		addObjects(mv, null);
		return mv;
	}
	
	private void addObjects(ModelAndView mv, ConsultaTipoConjunto consulta) {
		if (consulta == null) {
			consulta = new ConsultaTipoConjunto();
		}
		mv.addObject("tipoConjuntos", tipoConjuntoService.buscarTodos(consulta));
		mv.addObject("consulta", consulta == null ? new ConsultaTipoConjunto()
				: consulta);
	}
	
}
