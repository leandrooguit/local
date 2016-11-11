package ponto.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "JOG_JOGO")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "JOG_ID", nullable = false, insertable = true, updatable = false))})
public class Jogo extends Entidade {

	@Column(name = "JOG_NUMERO_JOGO")
	private Integer numeroJogo;
	
	@Column(name = "JOG_DATA_HORA")
	private DateTime dataHora;
	
	@Column(name = "JOG_TAMANHO_CARTELA")
	private Integer tamanhoCartela;
	
	public Integer getNumeroJogo() {
		return numeroJogo;
	}
	
	public void setNumeroJogo(Integer numeroJogo) {
		this.numeroJogo = numeroJogo;
	}
	
	public DateTime getDataHora() {
		return dataHora;
	}
	
	public void setDataHora(DateTime dataHora) {
		this.dataHora = dataHora;
	}
	
	public Integer getTamanhoCartela() {
		return tamanhoCartela;
	}
	
	public void setTamanhoCartela(Integer tamanhoCartela) {
		this.tamanhoCartela = tamanhoCartela;
	}
	
}
