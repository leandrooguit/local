package ponto.model.repository.consulta;

import org.joda.time.LocalDate;

import ponto.model.domain.ELocal;
import ponto.model.domain.Feriado;

public class ConsultaFeriado extends Consulta<Feriado> {

	private LocalDate mes;
	private LocalDate ano;
	private ELocal[] locais;

	private LocalDate dataInicial;
	private LocalDate dataInicialFixa;
	private LocalDate dataFinal;

	public LocalDate getMes() {
		return mes;
	}

	public ConsultaFeriado setMes(LocalDate mes) {
		this.mes = mes;
		return this;
	}

	public LocalDate getAno() {
		return ano;
	}

	public ConsultaFeriado setAno(LocalDate ano) {
		this.ano = ano;
		return this;
	}

	public ELocal[] getLocais() {
		return locais;
	}

	public ConsultaFeriado setLocais(ELocal[] locais) {
		this.locais = locais;
		return this;
	}

	public LocalDate getDataInicialFixa() {
		return dataInicialFixa;
	}

	public void setDataInicialFixa(LocalDate dataInicialFixa) {
		this.dataInicialFixa = dataInicialFixa;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public ConsultaFeriado setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
		return this;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public ConsultaFeriado setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
		return this;
	}

}
