package ponto.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BOL_BOLA")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "BOL_ID", nullable = false, insertable = true, updatable = false))})
public class Bola extends Entidade {

	@Column(name = "BOL_NOME", length = 50)
	private String nome;
	
	@Lob
	@Column(name = "BOL_IMAGEM")
	private byte[] imagem;
	
	@Column(name = "BOL_NUMERO")
	private Integer numero;
	
	@ManyToOne
	@JoinColumn(name = "TCO_ID")
	private TipoConjunto tipoConjunto;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public byte[] getImagem() {
		return imagem;
	}
	
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public TipoConjunto getTipoConjunto() {
		return tipoConjunto;
	}

	public void setTipoConjunto(TipoConjunto tipoConjunto) {
		this.tipoConjunto = tipoConjunto;
	}
	
}
