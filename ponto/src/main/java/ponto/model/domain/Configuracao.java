package ponto.model.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//Projeto BINGO
@Entity
@Table(name = "CON_CONFIGURACAO")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "CON_ID", nullable = false, insertable = true, updatable = false))})
public class Configuracao extends EntidadeBingo {

	private Integer quantElementoCartela;
	private String tipoElemento;
	@ManyToMany(mappedBy="configuracoes")
	private List<UsuarioBingo> usuarios;
	
	@Column(name = "CON_QUANT_ELEMENTO_CARTELA")
	public Integer getQuantElementoCartela() {
		return quantElementoCartela;
	}
	
	public void setQuantElementoCartela(Integer quantElementoCartela) {
		this.quantElementoCartela = quantElementoCartela;
	}
	
	@Column(name = "CON_TIPO_ELEMENTNO")
	public String getTipoElemento() {
		return tipoElemento;
	}
	
	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public List<UsuarioBingo> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioBingo> usuarios) {
		this.usuarios = usuarios;
	}
	
}
