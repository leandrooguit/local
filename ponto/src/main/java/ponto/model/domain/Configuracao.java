package ponto.model.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@Column(name = "CON_TIPO_ELEMENTNO")
	private String tipoElemento;
	
	@ManyToOne
	@JoinColumn(name = "USU_ID")
	private Usuario usuario;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="CTC_CONFIGURACAO_TIPO_CONJUNTO", joinColumns={@JoinColumn(name="CON_ID")}, 
    	inverseJoinColumns={@JoinColumn(name="TCO_ID")})
    private List<TipoConjunto> tipoConjuntos;
	
	public EQtdElementoCartela getQuantElementoCartela() {
		return quantElementoCartela;
	}
	
	public void setQuantElementoCartela(EQtdElementoCartela quantElementoCartela) {
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

	public List<TipoConjunto> getTipoConjuntos() {
		return tipoConjuntos;
	}

	public void setTipoConjuntos(List<TipoConjunto> tipoConjuntos) {
		this.tipoConjuntos = tipoConjuntos;
	}
	
}
