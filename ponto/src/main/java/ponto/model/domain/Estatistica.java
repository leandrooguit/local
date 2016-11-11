package ponto.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//Projeto BINGO
@Entity
@Table(name = "EST_ESTATISTICA")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "EST_ID", nullable = false, insertable = true, updatable = false))})
public class Estatistica extends Entidade {

	@Column(name = "EST_NUMERO_BINGO")
	private Integer numeroBingo;
	
	@Column(name = "EST_TEMPO_RESPOSTA")
	private Integer tempoResposta;
	
	@Column(name = "EST_TIPO_ERRO")
	private String tipoErro;
	
	@Column(name = "EST_NUMERO_ERRO")
	private Integer numeroErro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USU_ID")
	private Usuario usuario;
	
	public Integer getNumeroBingo() {
		return numeroBingo;
	}
	
	public void setNumeroBingo(Integer numeroBingo) {
		this.numeroBingo = numeroBingo;
	}

	public Integer getTempoResposta() {
		return tempoResposta;
	}
	
	public void setTempoResposta(Integer tempoResposta) {
		this.tempoResposta = tempoResposta;
	}
	
	public String getTipoErro() {
		return tipoErro;
	}
	
	public void setTipoErro(String tipoErro) {
		this.tipoErro = tipoErro;
	}
	
	public Integer getNumeroErro() {
		return numeroErro;
	}
	
	public void setNumeroErro(Integer numeroErro) {
		this.numeroErro = numeroErro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
