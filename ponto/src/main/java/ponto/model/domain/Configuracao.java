package ponto.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CON_CONFIGURACAO")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "CON_ID", nullable = false, insertable = true, updatable = false))})
public class Configuracao extends Entidade {

	@Column(name = "CON_QUANT_ELEMENTO_CARTELA")
	private Integer quantElementoCartela;
	
	@Column(name = "CON_TIPO_ELEMENTNO")
	private String tipoElemento;
	
	@ManyToOne
	@JoinColumn(name = "USU_ID")
	private Usuario usuario;
	
	public Integer getQuantElementoCartela() {
		return quantElementoCartela;
	}
	
	public void setQuantElementoCartela(Integer quantElementoCartela) {
		this.quantElementoCartela = quantElementoCartela;
	}
	
	public String getTipoElemento() {
		return tipoElemento;
	}
	
	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
