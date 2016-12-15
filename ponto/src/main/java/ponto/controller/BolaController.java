package ponto.controller;

import java.util.ArrayList;

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
import ponto.model.domain.Bola;
import ponto.model.repository.consulta.ConsultaBola;
import ponto.model.repository.consulta.ConsultaConfiguracao;
import ponto.model.repository.consulta.ConsultaTipoConjunto;
import ponto.model.service.BolaService;
import ponto.model.service.TipoConjuntoService;
import ponto.util.Mensagens;
import ponto.util.NegocioException;

@Controller
@RequestMapping("/bolas")
@SessionAttributes(value = "bola", types = Bola.class)
public class BolaController {

	@Autowired
	private BolaService bolaService;
	
	@Autowired
	private TipoConjuntoService tipoConjuntoService;
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView bola() {
		ModelAndView mv = new ModelAndView(Caminhos.BOLA_VISUALIZAR);
		Bola bola = new Bola();
		mv.addObject("bola", bola);
		addObjects(mv, null);
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public ModelAndView bolas(@ModelAttribute ConsultaBola consulta) {
		ModelAndView mv = new ModelAndView(Caminhos.BOLA_VISUALIZAR);
		addObjects(mv, consulta);
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView salvar(@ModelAttribute Bola bola,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(Caminhos.BOLA_VISUALIZAR);
		try {
			bola.setRespostas(new ArrayList<Bola>());
			bola.getRespostas().add(bola);
			bolaService.salvar(bola);
			mv.setViewName("redirect:bolas");
		} catch (NegocioException e) {
			mv.addObject(Mensagens.TIPO_DANGER, e.getMensagens());
			mv.addObject("bola", bola);
			addObjects(mv, null);
		}
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/{id}/editar", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView(Caminhos.BOLA_VISUALIZAR);
		ConsultaBola consulta = new ConsultaBola();
		consulta.setId(Long.valueOf(id));
		mv.addObject("bola", bolaService.buscar(consulta));
		addObjects(mv, null);
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/{id}/deletar", method = RequestMethod.GET)
	public ModelAndView deletar(@PathVariable("id") String id,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(Caminhos.BOLA_VISUALIZAR);
		Bola bola = bolaService.get(Long.valueOf(id));
		bolaService.deletar(bola);
		addObjects(mv, null);
		return mv;
	}
	
	private void addObjects(ModelAndView mv, ConsultaBola consulta) {
		if (consulta == null) {
			consulta = new ConsultaBola();
		}
		mv.addObject("bolas", bolaService.consultar(consulta));
		mv.addObject("tipoConjuntos", tipoConjuntoService.buscarTodos(new ConsultaTipoConjunto()));
		mv.addObject("consulta", consulta == null ? new ConsultaConfiguracao()
				: consulta);
	}
	
}
