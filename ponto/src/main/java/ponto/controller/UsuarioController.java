package ponto.controller;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ponto.controller.util.Caminhos;
import ponto.controller.util.CustomLocalDateEditor;
import ponto.model.domain.Autorizacao;
import ponto.model.domain.ELocal;
import ponto.model.domain.Usuario;
import ponto.model.service.UsuarioService;
import ponto.util.Mensagens;
import ponto.util.NegocioException;

@Controller
@RequestMapping("/usuarios")
@SessionAttributes(value = "usuario", types = Usuario.class)
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public UsuarioModel cadastrar() {
		return new UsuarioModel(Caminhos.CADASTRO_USUARIO);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView doCadastrar(@Valid @ModelAttribute Usuario usuario) {
		ModelAndView mv = new UsuarioModel(usuario, Caminhos.CADASTRO_USUARIO);
		try {
			usuarioService.salvar(usuario);
			mv.setViewName(Caminhos.LOGIN);
		} catch (NegocioException e) {
			mv.addObject(Mensagens.TIPO_DANGER, e.getMensagens());
		}
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return Caminhos.LOGIN;
	}

	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/alterar-senha", method = RequestMethod.GET)
	public ModelAndView alterarSenha() {
		return new UsuarioModel(usuarioService.getUsuarioCorrente(),
				Caminhos.ALTERAR_SENHA);
	}

	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/alteracao-senha", method = RequestMethod.PATCH)
	public ModelAndView alteracaoSenha(@ModelAttribute Usuario usuario) {
		ModelAndView mv = new UsuarioModel(usuario, Caminhos.ALTERAR_SENHA);
		try {
			usuarioService.alterarSenha(usuario);
			mv.setViewName(Caminhos.INICIO);
			mv.addObject(Mensagens.TIPO_SUCCESS,
					Mensagens.SENHA_ALTERADA_COM_SUCESSO);
		} catch (NegocioException e) {
			mv.addObject(Mensagens.TIPO_DANGER, e.getMensagens());
		}
		return mv;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class,
				new CustomLocalDateEditor());
	}

	class UsuarioModel extends ModelAndView {

		public UsuarioModel(String viewName) {
			this(new Usuario(), viewName);
		}

		public UsuarioModel(Usuario usuario, String viewName) {
			addObject("usuario", usuario);
			addObject("localidades", ELocal.values());
			setViewName(viewName);
		}
	}

}
