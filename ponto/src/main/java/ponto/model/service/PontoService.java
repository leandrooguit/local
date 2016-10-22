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
		Ponto ponto = buscar(consulta);
		repository.saveOrUpdate(ponto);
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

	public void flush() {
		repository.flush();

	}

	public static void main(String[] args) {
		System.out.println(Minutes.minutesBetween(DateTime.now(),
				DateTime.now().plusHours(3).plusMinutes(35)).getMinutes());
	}

}
