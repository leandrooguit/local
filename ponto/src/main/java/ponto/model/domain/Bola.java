package ponto.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

// Projeto BINGO
@Entity
@Table(name = "BOL_BOLA")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "BOL_ID", nullable = false, insertable = true, updatable = false))})
public class Bola extends EntidadeBingo {

	private String desccricao;
	private byte[] imagem;
	
	@Column(name = "BOL_DESCRICAO", length = 50)
	public String getDesccricao() {
		return desccricao;
	}
	
	public void setDesccricao(String desccricao) {
		this.desccricao = desccricao;
	}
	
	@Lob
	@Column(name = "BOL_IMAGEM")
	public byte[] getImagem() {
		return imagem;
	}
	
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	
	
	
}
