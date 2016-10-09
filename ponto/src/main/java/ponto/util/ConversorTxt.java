package ponto.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.ELocal;
import ponto.model.domain.Parametro;
import ponto.model.domain.Ponto;
import ponto.model.domain.Usuario;
import ponto.model.service.ParametroService;
import ponto.model.service.PontoService;
import ponto.model.service.UsuarioService;

@Service
public class ConversorTxt {

	private static HashMap<String, String> mapperNomeLogin = new HashMap<String, String>();

	static {

		File time = new File("d:\\pontoNovo\\time.txt");
		FileReader arq;
		try {
			arq = new FileReader(time);
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			while (linha != null) {
				String[] split = linha.split("=");
				mapperNomeLogin.put(split[1], split[0]);
				linha = lerArq.readLine();
			}
			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PontoService pontoService;

	@Autowired
	private ParametroService parametroService;

	private boolean validarNomesExcluidos(String nomeArquivo) {
		return nomeArquivo.contains("Fox") || nomeArquivo.contains("Cintra")
				|| nomeArquivo.contains("Luiz")
				|| nomeArquivo.contains("Maxwell")
				|| nomeArquivo.contains("Rosberg")
				|| nomeArquivo.contains("Diego")
				|| nomeArquivo.contains("Debora")
				|| nomeArquivo.contains("Hiroshi")
				|| nomeArquivo.contains("Ingridd")
				|| nomeArquivo.contains("Bispo")
				|| nomeArquivo.contains("Luiz") || nomeArquivo.contains("Luiz");
	}

	@Transactional
	public void gerar() {
		Map<String, Ponto> pontos = null;
		HashMap<String, Usuario> usuariosGerados = new HashMap<String, Usuario>();
		// String nome = ler.nextLine();
		File raiz = new File("d:\\pontoNovo");
		// String nome = "d:/Claudio_fevereiro.txt";

		Integer cont = 0;
		try {

			for (File pasta : raiz.listFiles()) {

				if (!pasta.isDirectory()) {
					continue;
				}

				Map<String, Usuario> gerarUsuarios = gerarUsuarios(pasta,
						usuariosGerados);

				File[] listOfFiles = pasta.listFiles();

				for (File file : listOfFiles) {

					String name = file.getName();
					if (!name.contains("_") || validarNomesExcluidos(name)) {
						continue;
					}

					String nome = name;
					nome = nome.substring(0, nome.indexOf("_"));
					System.out.println(name);
					pontos = new HashMap<String, Ponto>();
					FileReader arq = new FileReader(file);
					BufferedReader lerArq = new BufferedReader(arq);

					String linha = StringUtils
							.normalizeSpace(lerArq.readLine());
					while (StringUtils.isNotBlank(StringUtils
							.normalizeSpace(linha))) {
						try {
							linha = linha.trim();
							LocalDate dia = LocalDate.parse(
									linha.split(" ")[0],
									DateTimeFormat.forPattern("dd/MM/yyyy"));
							DateTime dataHora = DateTime.parse(linha,
									DateTimeFormat
											.forPattern("dd/MM/yyyy HH:mm:ss"));
							Ponto ponto = new Ponto();
							if (pontos.containsKey(dia.toString())) {
								ponto = pontos.get(dia.toString());
							}

							if (ponto.getEntrada1() == null) {
								ponto.setEntrada1(dataHora);
							} else if (ponto.getSaida1() == null) {
								ponto.setSaida1(dataHora);
								ponto.setTotal(calcularTotal(ponto));
							} else if (ponto.getEntrada2() == null) {
								ponto.setEntrada2(dataHora);
							} else if (ponto.getSaida2() == null) {
								ponto.setSaida2(dataHora);
								ponto.setTotal(calcularTotal(ponto));
							}
							pontos.put(dia.toString(), ponto);
							linha = lerArq.readLine();
							if (StringUtils.isBlank(StringUtils
									.normalizeSpace(linha))) {
								linha = lerArq.readLine();
							}
						} catch (IllegalArgumentException e) {
							continue;
						}
					}

					arq.close();

					Usuario usuario = gerarUsuarios.get(mapperNomeLogin
							.get(nome));
					if (usuario == null) {
						System.out.println("Usuário nulo para: " + nome);
						continue;
					}
					cont = gerarPontos(pontos, cont, usuario);
					System.out.println(cont);
					pontoService.flush();
				}
			}

		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}
		System.out.println("Gerando saldos");

		gerarSaldos();

		System.out.println("Acabei");
	}

	private void gerarSaldos() {

		File saldos = new File("d:\\pontoNovo\\saldo.txt");

		try {
			FileReader arq = new FileReader(saldos);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();
			while (StringUtils.isNotBlank(linha)) {

				String[] split = linha.split(" ");
				Parametro p = new Parametro();
				p.setNome(split[1].trim());
				p.setValor(split[2].trim());

				parametroService.criar(p);

				linha = lerArq.readLine();
			}
			arq.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Integer gerarPontos(Map<String, Ponto> pontos, Integer cont,
			Usuario usuario) {
		for (Ponto ponto : pontos.values()) {
			cont++;
			ponto.setUsuario(usuario);
			ponto.setDataCadastro(ponto.getEntrada1());
			pontoService.save(ponto);
		}
		return cont;
	}

	@Transactional
	private Map<String, Usuario> gerarUsuarios(File pasta,
			HashMap<String, Usuario> usuariosGerados) {

		File[] listOfFiles = pasta.listFiles();

		DateTime dtAtual = DateTime.now();

		for (File file : listOfFiles) {

			if (file.isDirectory()) {
				continue;
			}

			String nome = file.getName();
			if (!nome.contains("_") || validarNomesExcluidos(nome)) {
				continue;
			}

			nome = nome.substring(0, nome.indexOf("_"));
			String login = mapperNomeLogin.get(nome);

			if (usuariosGerados.containsKey(login)
					|| StringUtils.isBlank(login)) {
				continue;
			}
			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setSenha("123123");
			usuario.setConfirmacaoSenha("123123");
			usuario.setLogin(login);
			usuario.setDataCadastro(dtAtual);
			usuario.setLocal(ELocal.PE);
			try {
				usuarioService.salvar(usuario);
				usuariosGerados.put(usuario.getLogin(), usuario);
			} catch (NegocioException e) {
				System.out.println(e.getMensagensTratadas());
			}
		}

		return usuariosGerados;
	}

	private String calcularTotal(Ponto ponto) {
		String total = "";
		if (ponto.getEntrada1() != null && ponto.getSaida1() != null) {

			Interval i1 = new Interval(ponto.getEntrada1(), ponto.getSaida1());
			total = PontoService.formatter.print(i1.toPeriod()
					.normalizedStandard());

			if (ponto.getEntrada2() != null && ponto.getSaida2() != null) {
				Interval i2 = new Interval(ponto.getEntrada2(),
						ponto.getSaida2());
				total = PontoService.formatter.print(i1.toPeriod()
						.plus(i2.toPeriod()).normalizedStandard());
			}

		}
		return total;
	}

}
