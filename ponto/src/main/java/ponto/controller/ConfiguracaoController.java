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
import ponto.model.domain.Configuracao;
import ponto.model.domain.Usuario;
import ponto.model.repository.consulta.ConsultaConfiguracao;
import ponto.model.service.ConfiguracaoService;
import ponto.model.service.UsuarioService;
import ponto.util.Mensagens;
import ponto.util.NegocioException;

@Controller
@RequestMapping("/configuracoes")
@SessionAttributes(value = "configuracao", types = Configuracao.class)
public class ConfiguracaoController {

	@Autowired
	private ConfiguracaoService configuracaoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView configuracao() {
		ModelAndView mv = new ModelAndView(Caminhos.CONFIGURACAO_VISUALIZAR);
		Configuracao configuracao = new Configuracao();
		Usuario usuarioCorrenteSpring = usuarioService
				.getUsuarioCorrenteSpring();
		configuracao.setUsuario(usuarioCorrenteSpring);
		mv.addObject("configuracao", configuracao);
		addObjects(mv, null);
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public ModelAndView configuracoes(@ModelAttribute ConsultaConfiguracao consulta) {
		ModelAndView mv = new ModelAndView(Caminhos.CONFIGURACAO_VISUALIZAR);
		addObjects(mv, consulta);
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView salvar(@ModelAttribute Configuracao configuracao,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(Caminhos.CONFIGURACAO_VISUALIZAR);
		try {
			configuracaoService.salvar(configuracao);
			mv.setViewName("redirect:configuracoes");
		} catch (NegocioException e) {
			mv.addObject(Mensagens.TIPO_DANGER, e.getMensagens());
			mv.addObject("configuracao", configuracao);
			addObjects(mv, null);
		}
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/{id}/editar", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView(Caminhos.CONFIGURACAO_VISUALIZAR);
		ConsultaConfiguracao consulta = new ConsultaConfiguracao();
		consulta.setId(Long.valueOf(id));
		mv.addObject("configuracao", configuracaoService.buscar(consulta));
		addObjects(mv, null);
		return mv;
	}
	
	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/{id}/deletar", method = RequestMethod.GET)
	public ModelAndView deletar(@PathVariable("id") String id,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(Caminhos.CONFIGURACAO_VISUALIZAR);
		Configuracao configuracao = configuracaoService.get(Long.valueOf(id));
		try {
			policy(configuracao, request);
			configuracaoService.deletar(configuracao);
			addObjects(mv, null);
		} catch (NegocioException e) {
			mv.addObject(Mensagens.TIPO_DANGER, e.getMensagens());
			mv.addObject("configuracao", configuracao);
			addObjects(mv, null);
		}
		return mv;
	}
	
	private void addObjects(ModelAndView mv, ConsultaConfiguracao consulta) {
		if (consulta == null) {
			consulta = new ConsultaConfiguracao();
			consulta.setIdUsuario(usuarioService.getUsuarioCorrenteSpring()
					.getId());
		}
		mv.addObject("configuracoes", configuracaoService.consultar(consulta));
		mv.addObject("usuarioLogado", usuarioService.getUsuarioCorrenteSpring().getLogin());
		mv.addObject("usuarios", usuarioService.consultarUsuariosParaCombobox());
		mv.addObject("consulta", consulta == null ? new ConsultaConfiguracao()
				: consulta);
	}
	
	private void policy(Configuracao configuracao, HttpServletRequest request)
			throws NegocioException {
		if (!request.isUserInRole(Autorizacao.ROLE_ADMIN)
				&& configuracao.getUsuario() != null
				&& !usuarioService.getUsuarioCorrenteSpring().getId()
						.equals(configuracao.getUsuario().getId())) {
			throw new NegocioException(Mensagens.ACESSO_NEGADO);
		}
	}
	
}
