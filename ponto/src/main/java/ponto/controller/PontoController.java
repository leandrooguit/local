package ponto.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ponto.controller.util.Caminhos;
import ponto.controller.util.CustomDateTimeEditor;
import ponto.controller.util.CustomLocalDateEditor;
import ponto.model.domain.Autorizacao;
import ponto.model.domain.Ponto;
import ponto.model.domain.ResumoDTO;
import ponto.model.repository.consulta.ConsultaPonto;
import ponto.model.repository.consulta.ConsultaUsuario;
import ponto.model.service.PontoService;
import ponto.model.service.UsuarioService;
import ponto.util.ConversorTxt;
import ponto.util.Mensagens;
import ponto.util.NegocioException;

@Controller
@RequestMapping("/pontos")
@SessionAttributes(value = "ponto", types = Ponto.class)
public class PontoController {

	@Autowired
	private PontoService pontoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ConversorTxt conversor;

	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	public ModelAndView doRegistrar() {
		ModelAndView mv = new ModelAndView(Caminhos.INICIO);
		try {
			pontoService.registrarPonto();
			mv.addObject(Mensagens.TIPO_SUCCESS,
					"Pontos registrado com sucesso!  "
							+ DateTime.now().toString("dd/MM/yyyy HH:mm:ss"));
		} catch (NegocioException e) {
			mv.addObject(Mensagens.TIPO_DANGER, e.getMensagens());
		}
		return mv;
	}

	@Secured(Autorizacao.ROLE_ADMIN)
	@RequestMapping(value = "/gerarPontos", method = RequestMethod.GET)
	public ModelAndView gerarPontos() {
		ModelAndView mv = new ModelAndView(Caminhos.PONTOS_GERAR);
		return mv;
	}

	@Secured(Autorizacao.ROLE_ADMIN)
	@RequestMapping(value = "/gerarPontos", method = RequestMethod.POST)
	public ModelAndView doGerarPontos() {
		ModelAndView mv = new ModelAndView(Caminhos.PONTOS_GERAR);
		conversor.gerar();
		mv.addObject(Mensagens.TIPO_SUCCESS, "Pontos gerados com sucesso!");
		return mv;
	}

	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView pontos() {
		ModelAndView mv = new ModelAndView(Caminhos.PONTOS_VISUALIZAR);
		ConsultaPonto consulta = new ConsultaPonto();
		consulta.setIdUsuario(usuarioService.getUsuarioCorrente().getId());
		consulta.setMesAno(DateTime.now());
		addDefaultObjects(mv, consulta);
		return mv;
	}

	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView pontos(@ModelAttribute ConsultaPonto consulta) {
		ModelAndView mv = new ModelAndView(Caminhos.PONTOS_VISUALIZAR);
		addDefaultObjects(mv, consulta);
		return mv;
	}

	private void addDefaultObjects(ModelAndView mv, ConsultaPonto consulta) {

		mv.addObject("consulta", consulta);
		mv.addObject("usuarios", usuarioService.consultarUsuariosParaCombobox());
		if (consulta.getMesAno() == null || consulta.getIdUsuario() == null) {
			mv.addObject(Mensagens.TIPO_DANGER, Mensagens.CAMPOS_OBRIGATORIOS);
			return;
		}

		List<Ponto> pontos = pontoService.consultar(consulta);
		mv.addObject("pontos", pontos);
		LocalDate mesAno = consulta.getMesAno().toLocalDate();
		mv.addObject(
				"total",
				pontoService.totalHorasEsperadas(mesAno, false,
						consulta.getIdUsuario(), false, null));
		LocalDate now = LocalDate.now();
		boolean mesAnoCorrente = now.getMonthOfYear() == mesAno
				.getMonthOfYear() && now.getYear() == mesAno.getYear();
		mv.addObject("totalAteHoje", pontoService.totalHorasEsperadas(mesAno,
				mesAnoCorrente, consulta.getIdUsuario(), false, null));
		mv.addObject("totalHorasFeitas", pontoService.qtdHorasFeitas(pontos));
		mv.addObject("saldo", pontoService.calcularSaldo(pontos,
				consulta.getIdUsuario(), false, false, 1));
		mv.addObject("saldoGeral", pontoService.saldoAteOMesNovo(consulta));
		mv.addObject("previsaoSaida", pontoService.obterPrevisaoSaida(pontos));
		ConsultaUsuario consultaUsuario = new ConsultaUsuario();
		consultaUsuario.addCamposCombobox();
	}

	@Secured(Autorizacao.ROLE_USER)
	@RequestMapping(value = "/resumo", method = RequestMethod.GET)
	public ModelAndView resumo() {
		ModelAndView mv = new ModelAndView(Caminhos.RESUMO);
		List<ResumoDTO> resumos = new ArrayList<ResumoDTO>();
		ConsultaPonto consulta = new ConsultaPonto();
		DateTime now = DateTime.now().minusMonths(12);
		consulta.setIdUsuario(usuarioService.getUsuarioCorrente().getId())
				.setAno(now.getYear()).setMes(now.getMonthOfYear());
		for (int i = 0; i < 24; i++) {
			List<Ponto> pontos = pontoService.consultar(consulta);
			if (CollectionUtils.isNotEmpty(pontos)) {
				ResumoDTO resumo = new ResumoDTO();
				resumo.setSaldo(pontoService.calcularSaldo(pontos,
						consulta.getIdUsuario(), false, false, 1));
				resumo.setTotal(pontoService.totalHorasEsperadas(
						now.toLocalDate(), false, consulta.getIdUsuario(),
						false, null));
				resumo.setHorasFeitas(pontoService.qtdHorasFeitas(pontos));
				resumo.setMesAno(now);
				resumos.add(resumo);
			}
			now = now.plusMonths(1);
			consulta.setAno(now.getYear()).setMes(now.getMonthOfYear());
		}
		mv.addObject("resumos", resumos);
		return mv;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class,
				new CustomLocalDateEditor());
		binder.registerCustomEditor(DateTime.class, new CustomDateTimeEditor());
	}

}
