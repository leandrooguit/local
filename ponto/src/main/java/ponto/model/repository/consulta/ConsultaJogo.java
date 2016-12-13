package ponto.model.repository.consulta;

import org.joda.time.LocalDate;

import ponto.model.domain.Jogo;

public class ConsultaJogo extends Consulta<Jogo> {

	private Long idUsuario;
	private LocalDate mesAno;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private boolean aprovado;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public ConsultaJogo setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
		return this;
	}

	public LocalDate getMesAno() {
		return mesAno;
	}

	public ConsultaJogo setMesAno(LocalDate mesAno) {
		this.mesAno = mesAno;
		return this;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	public void addCamposLista() {
		addCampo("descricao");
		addCampo("abono");
		addCampo("qtdHoras");
		addCampo("dataInicial");
		addCampo("dataFinal");
		addCampo("usuario.id");
		addCampo("usuario.nome");
		addCampo("aprovado");
	}

}
