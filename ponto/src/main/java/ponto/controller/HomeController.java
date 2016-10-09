package ponto.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ponto.controller.util.Caminhos;
import ponto.model.domain.Autorizacao;

@Controller
public class HomeController {

	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping("/")
	public String inicio() {
		return Caminhos.INICIO;
	}

	@RequestMapping("/401")
	public String acessoNegado() {
		return Caminhos.ACESSO_NEGADO;
	}

}
