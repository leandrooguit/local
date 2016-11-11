package ponto.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CON_CONFIGURACAO")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "CON_ID", nullable = false, insertable = true, updatable = false))})
public class Configuracao extends Entidade {

	@Enumerated(EnumType.STRING)
	@Column(name = "CON_QUANT_ELEMENTO_CARTELA")
	private EQtdElementoCartela quantElementoCartela;
	
	@ManyToOne
	@JoinColumn(name = "USU_ID")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "TCO_ID")
	private TipoConjunto tipoConjunto;
	
	public EQtdElementoCartela getQuantElementoCartela() {
		return quantElementoCartela;
	}
	
	public void setQuantElementoCartela(EQtdElementoCartela quantElementoCartela) {
		this.quantElementoCartela = quantElementoCartela;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoConjunto getTipoConjunto() {
		return tipoConjunto;
	}

	public void setTipoConjunto(TipoConjunto tipoConjunto) {
		this.tipoConjunto = tipoConjunto;
	}
	
}
