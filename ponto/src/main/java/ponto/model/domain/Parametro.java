package ponto.model.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "par_parametro")
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "par_id", nullable = false, insertable = true, updatable = false)) })
public class Parametro extends Entidade {

	public static final String PAR_DATA_ULTIMA_EXECUCAO_BANCO_HORAS = "dataUltimaExecucaoBancoHoras";
	private static final long serialVersionUID = -1205885767914471708L;

	@Column(name = "par_nome", nullable = false)
	private String nome;

	@Column(name = "par_valor", nullable = false)
	private String valor;

	public Parametro() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parametro other = (Parametro) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
