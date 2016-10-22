package ponto.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//Projeto BINGO
@Entity
@Table(name = "EST_ESTATISTICA")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "EST_ID", nullable = false, insertable = true, updatable = false))})
public class Estatistica extends Entidade {

	private Integer numeroBingo;
	private Integer tempoResposta;
	private String tipoErro;
	private Integer numeroErro;
	private Usuario usuario;
	
	@Column(name = "EST_NUMERO_BINGO")
	public Integer getNumeroBingo() {
		return numeroBingo;
	}
	
	public void setNumeroBingo(Integer numeroBingo) {
		this.numeroBingo = numeroBingo;
	}

	@Column(name = "EST_TEMPO_RESPOSTA")
	public Integer getTempoResposta() {
		return tempoResposta;
	}
	
	public void setTempoResposta(Integer tempoResposta) {
		this.tempoResposta = tempoResposta;
	}
	
	@Column(name = "EST_TIPO_ERRO")
	public String getTipoErro() {
		return tipoErro;
	}
	
	public void setTipoErro(String tipoErro) {
		this.tipoErro = tipoErro;
	}
	
	@Column(name = "EST_NUMERO_ERRO")
	public Integer getNumeroErro() {
		return numeroErro;
	}
	
	public void setNumeroErro(Integer numeroErro) {
		this.numeroErro = numeroErro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "USU_ID")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
