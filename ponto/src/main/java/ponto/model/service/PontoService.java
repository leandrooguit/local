package ponto.model.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.Abono;
import ponto.model.domain.Feriado;
import ponto.model.domain.Parametro;
import ponto.model.domain.Ponto;
import ponto.model.domain.Usuario;
import ponto.model.repository.AbstractRepository;
import ponto.model.repository.PontoRepository;
import ponto.model.repository.consulta.ConsultaPonto;
import ponto.util.Mensagens;
import ponto.util.NegocioException;
import ponto.util.Util;

@Service
public class PontoService extends AbstractService<Ponto, ConsultaPonto> {

	@Autowired
	private PontoRepository repository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FeriadoService feriadoService;

	@Autowired
	private AbonoService abonoService;

	@Autowired
	private ParametroService parametroService;

	@Override
	protected AbstractRepository<ConsultaPonto, Ponto> getRepository() {
		return repository;
	}

	public void save(Ponto ponto) {
		repository.save(ponto);
	}

	public static final PeriodFormatter formatter = new PeriodFormatterBuilder()
			.printZeroAlways().minimumPrintedDigits(2).appendHours()
			.appendSeparator(":").appendMinutes().appendSeparator(":")
			.appendSeconds().toFormatter();

	@Transactional
	public void registrarPonto() throws NegocioException {
		ConsultaPonto consulta = new ConsultaPonto();
		Usuario usuarioCorrente = usuarioService.getUsuarioCorrente();
		consulta.setIdUsuario(usuarioCorrente.getId());
		DateTime now = DateTime.now();
		consulta.setDataCadastro(now);
		Ponto ponto = buscar(consulta);
		if (ponto == null) {
			ponto = new Ponto();
			ponto.setEntrada1(now);
			ponto.setUsuario(usuarioCorrente);
		} else {
			if (ponto.getSaida1() == null) {
				ponto.setSaida1(now);
			} else if (ponto.getEntrada2() == null) {
				if (Minutes.minutesBetween(ponto.getSaida1(), now).getMinutes() < 5) {
					throw new NegocioException(
							Mensagens.PONTO_REGISTRADO_MENOS_1_HORA);
				}
				ponto.setEntrada2(now);
			} else if (ponto.getSaida2() == null) {
				ponto.setSaida2(now);
			}
		}
		repository.saveOrUpdate(ponto);
	}

	public static String calcularTotal(Ponto ponto) {
		String total = "";
		if (ponto.getEntrada1() != null && ponto.getSaida1() != null) {

			Interval i1 = new Interval(ponto.getEntrada1(), ponto.getSaida1());
			total = formatter.print(i1.toPeriod().normalizedStandard());

			DateTime entrada2 = ponto.getEntrada2();

			if (entrada2 == null) {
				if (Hours.hoursBetween(ponto.getEntrada1(), ponto.getSaida1())
						.getHours() > 6) {

				}
			}

			else {

				entrada2 = ajustarHoraAlmoco(ponto, entrada2);

				if (entrada2 != null && ponto.getSaida2() != null) {
					Interval i2 = new Interval(entrada2, ponto.getSaida2());
					total = formatter.print(i1.toPeriod().plus(i2.toPeriod())
							.normalizedStandard());
				}
			}

		}
		return total;
	}

	private static DateTime ajustarHoraAlmoco(Ponto ponto,
			DateTime entrada2Auxiliar) {
		DateTime entrada2 = entrada2Auxiliar == null ? ponto.getEntrada2()
				: entrada2Auxiliar;
		if (entrada2 != null
				&& Hours.hoursBetween(ponto.getSaida1(), ponto.getEntrada2())
						.getHours() < 1) {
			entrada2 = ponto.getSaida1().plusHours(1);
		}
		return entrada2;
	}

	public String totalHorasEsperadas(LocalDate dataBase, boolean ateHoje,
			Long idUsuario, boolean ateHojeMenos1, Integer diasUteisPassado) {
		int diasUteis = diasUteisPassado == null ? qtdDiasUteisGeral(dataBase,
				ateHoje) : diasUteisPassado;

		if (ateHojeMenos1) {
			diasUteis--;
		}
		List<Feriado> feriados = feriadoService.feriadosPorMesEUsuarioCorrente(
				dataBase, diasUteisPassado != null);
		int horasAbonadas = 0;
		for (Feriado feriado : feriados) {
			if (feriado.isPersistente()
					|| feriado.getData().getYear() == dataBase.getYear()) {
				LocalDate diaMes = feriado.getData();
				diaMes = diaMes.withYear(dataBase.getYear());
				if (DateTimeConstants.SATURDAY != diaMes.getDayOfWeek()
						&& DateTimeConstants.SUNDAY != diaMes.getDayOfWeek()) {
					diasUteis--;
				}
			}
		}
		List<Abono> abonos = abonoService.abonosPorMesEUsuario(dataBase,
				idUsuario, diasUteisPassado != null);
		if (CollectionUtils.isNotEmpty(abonos)) {
			for (Abono abono : abonos) {
				LocalDate dtAux = abono.getDataInicial();
				// calculo para horas
				if (abono.getQtdHoras() != null) {
					horasAbonadas += abono.getQtdHoras();
				} else {
					// calculo para dias
					do {
						if (dataBase.getYear() == dtAux.getYear()
								&& dataBase.getMonthOfYear() == dtAux
										.getMonthOfYear()
								&& !(dtAux.getDayOfWeek() == DateTimeConstants.SATURDAY || dtAux
										.getDayOfWeek() == DateTimeConstants.SUNDAY)) {
							diasUteis--;
						}
						dtAux = dtAux.plusDays(1);
					} while (!dtAux.isAfter(abono.getDataFinal()));
				}

			}

		}
		return String.valueOf((diasUteis * 8) - horasAbonadas);
	}

	private int qtdDiasUteisGeral(LocalDate mes, boolean ateHoje) {
		int diasUteis = ateHoje ? LocalDate.now().getDayOfMonth() : mes
				.dayOfMonth().withMaximumValue().getDayOfMonth();
		int diasUteisAux = diasUteis;
		LocalDate data = new LocalDate(mes.withDayOfMonth(1));
		for (int i = 1; i <= diasUteisAux; i++) {
			if (DateTimeConstants.SATURDAY == data.getDayOfWeek()
					|| DateTimeConstants.SUNDAY == data.getDayOfWeek()) {
				diasUteis--;
			}
			data = data.plusDays(1);
		}
		return diasUteis;
	}

	public String qtdHorasFeitas(List<Ponto> pontos) {
		DateTime a = DateTime.now();
		DateTime b = DateTime.now();
		for (Ponto ponto : pontos) {
			String total = ponto.getTotal();
			if (total != null && StringUtils.isNotBlank(total)) {
				DateTime aux = DateTime.parse(total,
						DateTimeFormat.forPattern("HH:mm:ss"));
				b = b.plusHours(aux.getHourOfDay());
				b = b.plusMinutes(aux.getMinuteOfHour());
				b = b.plusSeconds(aux.getSecondOfMinute());
			}
		}

		int hours = Hours.hoursBetween(a, b).getHours();
		int minutes = Minutes.minutesBetween(a, b).getMinutes() % 60;
		int segundos = Seconds.secondsBetween(a, b).getSeconds() % 60;

		return (hours < 9 ? ("0" + hours) : hours) + ":"
				+ (minutes < 9 ? ("0" + minutes) : minutes) + ":"
				+ (segundos < 9 ? ("0" + segundos) : segundos);
	}

	public String calcularSaldo(List<Ponto> pontos, Long idUsuario,
			boolean ateHoje, boolean ateHojeMenos1, int dia) {
		if (CollectionUtils.isNotEmpty(pontos)) {
			String qtdHorasFeitas = qtdHorasFeitas(pontos);
			LocalDate pontoLocalDate = pontos.get(0).getEntrada1()
					.toLocalDate();
			String totalHorasEsperadas = totalHorasEsperadas(pontoLocalDate,
					ateHoje, idUsuario, ateHojeMenos1, null);
			if (qtdHorasFeitas != null) {
				String[] splitHorasFeitas = qtdHorasFeitas.split(":");
				DateTime dtAux = DateTime
						.now()
						.withDate(pontoLocalDate.getYear(),
								pontoLocalDate.getMonthOfYear(), dia)
						.withTimeAtStartOfDay();
				DateTime horasFeitas = dtAux
						.plusHours(Integer.valueOf(splitHorasFeitas[0]))
						.plusMinutes(Integer.valueOf(splitHorasFeitas[1]))
						.plusSeconds(Integer.valueOf(splitHorasFeitas[2]));
				DateTime horasEsperadas = dtAux.plusHours(Integer
						.valueOf(totalHorasEsperadas));
				Hours hoursBetween = Hours.hoursBetween(horasEsperadas,
						horasFeitas);
				Minutes minutesBetween = Minutes.minutesBetween(horasEsperadas,
						horasFeitas);
				Seconds secondsBetween = Seconds.secondsBetween(horasEsperadas,
						horasFeitas);
				return hoursBetween.getHours()
						+ ":"
						+ String.valueOf(minutesBetween.getMinutes() % 60)
								.replace("-", "")
						+ ":"
						+ String.valueOf(secondsBetween.getSeconds() % 60)
								.replace("-", "");
			}
		}
		return "";
	}

	@Transactional(readOnly = true)
	public String saldoAteOMesNovo(ConsultaPonto consulta) {
		DateTime i = DateTime.now();
		ConsultaPonto consultaAux = new ConsultaPonto();
		consultaAux.setIdUsuario(consulta.getIdUsuario());
		DateTime dataParametro = Util
				.stringDataToDateTime(
						parametroService
								.getValorParametro(Parametro.PAR_DATA_ULTIMA_EXECUCAO_BANCO_HORAS))
				.plusDays(1).hourOfDay().withMinimumValue();
		consultaAux.setDataCadastroInicial(dataParametro);

		List<Ponto> pontosDb = consultar(consultaAux);
		DateTime total = i.withTimeAtStartOfDay();
		for (Ponto ponto : pontosDb) {
			total = calcularTotal(total, ponto.getTotal().split(":"));
		}

		String saldo = parametroService.getValorParametro(usuarioService
				.usuarioPorId(consultaAux.getIdUsuario()).getLogin());

		if (saldo == null) {

			saldo = "00:00:00";
		}
		total = calcularTotal(total, saldo.split(":"));

		int diasUteisAux = Days.daysBetween(dataParametro.toLocalDate(),
				i.toLocalDate()).getDays();
		int diasUteis = diasUteisAux;
		LocalDate data = dataParametro.toLocalDate();
		for (int j = 1; j <= diasUteisAux; j++) {
			if (DateTimeConstants.SATURDAY == data.getDayOfWeek()
					|| DateTimeConstants.SUNDAY == data.getDayOfWeek()) {
				diasUteis--;
			}
			data = data.plusDays(1);
		}

		String totalHorasEsperadas = totalHorasEsperadas(
				dataParametro.toLocalDate(), true, consulta.getIdUsuario(),
				false, diasUteis);

		String totalHorasFeitas = qtdHorasFeitas(pontosDb);

		// totalHorasExecutadasAteHj = totalHorasExecutadasAteHj.substring(0,
		// totalHorasExecutadasAteHj.indexOf(":"));
		long segundosEsperadoAteHj = Util
				.calcHoraEmSegundos(totalHorasEsperadas + ":00:00");
		long segundosExecutadosAteHj = Util
				.calcHoraEmSegundos(totalHorasFeitas);
		long resultado = 0;

		if (segundosEsperadoAteHj > segundosExecutadosAteHj) {
			resultado = segundosExecutadosAteHj - segundosEsperadoAteHj;
		} else {
			resultado = segundosExecutadosAteHj - segundosEsperadoAteHj;
		}

		String sinal = saldo.charAt(0) + "";
		String saldoSemSinal = saldo.substring(1);

		long saldoSS = Util.calcHoraEmSegundos(saldoSemSinal);
		if (sinal.equals("-")) {
			saldoSS *= -1;
		}

		saldoSS = resultado + saldoSS;
		if (saldoSS > 0) {
			sinal = "+";
		} else {
			sinal = "-";
		}
		// Para apresentação e calculo do intervalo - Ajustar
		return sinal + Util.converterSegundosEmHHMMSS(saldoSS);
	}

	private DateTime calcularTotal(DateTime total, String[] splitSaldo) {
		if (splitSaldo.length == 3) {
			if (splitSaldo[0].startsWith("-")) {
				total = total
						.minusHours(
								Integer.valueOf(splitSaldo[0].replace("-", "")))
						.minusMinutes(Integer.valueOf(splitSaldo[1]))
						.minusSeconds(Integer.valueOf(splitSaldo[2]));
			} else {
				total = total.plusHours(Integer.valueOf(splitSaldo[0]))
						.plusMinutes(Integer.valueOf(splitSaldo[1]))
						.plusSeconds(Integer.valueOf(splitSaldo[2]));
			}
		}
		return total;
	}

	public String obterPrevisaoSaida(List<Ponto> pontos) {
		final LocalDate hoje = LocalDate.now();
		for (Ponto ponto : pontos) {
			if (ponto.getDataCadastro().toLocalDate().equals(hoje)) {
				return obterPrevisaoSaida(ponto);
			}
		}
		return null;
	}

	private String obterPrevisaoSaida(Ponto ponto) {

		if (ponto != null && ponto.getEntrada2() != null
				&& ponto.getSaida2() == null) {

			DateTime pFinal = DateTime.now().withTimeAtStartOfDay()
					.plusHours(8);

			DateTime p1 = ponto.getEntrada1();
			DateTime p2 = ponto.getSaida1();
			DateTime p3 = ajustarHoraAlmoco(ponto, null);

			Interval i1 = new Interval(p1, p2);
			DateTime pp = pFinal.minus(i1.toPeriod());

			return pp.plusHours(p3.getHourOfDay())
					.plusMinutes(p3.getMinuteOfHour())
					.plusSeconds(p3.getSecondOfMinute()).toString("HH:mm:ss");
		}
		return null;
	}

	public void flush() {
		repository.flush();

	}

	public static void main(String[] args) {
		System.out.println(Minutes.minutesBetween(DateTime.now(),
				DateTime.now().plusHours(3).plusMinutes(35)).getMinutes());
	}

}
