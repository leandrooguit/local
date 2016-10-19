package ponto.model.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.joda.time.DateTime;

//Projeto BINGO
@Entity
@Table(name = "JOG_JOGO")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "JOG_ID", nullable = false, insertable = true, updatable = false))})
public class Jogo extends EntidadeBingo {

	private Integer numeroJogo;
	private DateTime dataHora;
	private Integer tamanhoCartela;
	@ManyToMany(mappedBy="jogos")
	private List<UsuarioBingo> usuarios;
	
	@Column(name = "JOG_NUMERO_JOGO")
	public Integer getNumeroJogo() {
		return numeroJogo;
	}
	
	public void setNumeroJogo(Integer numeroJogo) {
		this.numeroJogo = numeroJogo;
	}
	
	@Column(name = "JOG_NUMERO_JOGO")
	public DateTime getDataHora() {
		return dataHora;
	}
	
	public void setDataHora(DateTime dataHora) {
		this.dataHora = dataHora;
	}
	
	@Column(name = "JOG_TAMANHO_CARTELA")
	public Integer getTamanhoCartela() {
		return tamanhoCartela;
	}
	
	public void setTamanhoCartela(Integer tamanhoCartela) {
		this.tamanhoCartela = tamanhoCartela;
	}
	
	public List<UsuarioBingo> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioBingo> usuarios) {
		this.usuarios = usuarios;
	}
	
}
