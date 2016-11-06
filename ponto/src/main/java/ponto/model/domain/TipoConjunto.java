package ponto.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TCO_TIPO_CONJUNTO")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "TCO_ID", nullable = false, insertable = true, updatable = false))})
public class TipoConjunto extends Entidade {

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
