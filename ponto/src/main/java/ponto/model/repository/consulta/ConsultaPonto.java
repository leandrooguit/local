package ponto.model.repository.consulta;

import org.joda.time.DateTime;

import ponto.model.domain.Ponto;

public class ConsultaPonto extends Consulta<Ponto> {

	private Long idUsuario;
//	private DateTime dataCadastro;
//	private DateTime dataCadastroInicial;
//	private DateTime dataCadastroFinal;
	private DateTime mesAno;

	private Integer mes;
	private Integer ano;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public ConsultaPonto setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
		return this;
	}

//	public DateTime getDataCadastroInicial() {
//		return dataCadastroInicial;
//	}
//
//	public ConsultaPonto setDataCadastroInicial(DateTime dataCadastroInicial) {
//		this.dataCadastroInicial = dataCadastroInicial;
//		return this;
//	}
//
//	public DateTime getDataCadastroFinal() {
//		return dataCadastroFinal;
//	}
//
//	public ConsultaPonto setDataCadastroFinal(DateTime dataCadastroFinal) {
//		this.dataCadastroFinal = dataCadastroFinal;
//		return this;
//	}

	public DateTime getMesAno() {
		return mesAno;
	}

	public ConsultaPonto setMesAno(DateTime mesAno) {
		this.mesAno = mesAno;
		return this;
	}

	public Integer getMes() {
		return mes;
	}

	public ConsultaPonto setMes(Integer mes) {
		this.mes = mes;
		return this;
	}

	public Integer getAno() {
		return ano;
	}

	public ConsultaPonto setAno(Integer ano) {
		this.ano = ano;
		return this;
	}

}
