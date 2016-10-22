package ponto.model.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "dia_dia")
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "dia_id", nullable = false, insertable = true, updatable = false)) })
public class Dia extends Entidade implements Serializable {

	private static final long serialVersionUID = -2662710674455652057L;

	@Column(name = "dia_entrada_1", nullable = false)
	private DateTime entrada1;

	@Column(name = "dia_entrada_2")
	private DateTime entrada2;

	@Column(name = "dia_saida_1")
	private DateTime saida1;

	@Column(name = "dia_saida_2")
	private DateTime saida2;

	public Dia() {

	}

	public DateTime getEntrada1() {
		return entrada1;
	}

	public void setEntrada1(DateTime entrada1) {
		this.entrada1 = entrada1;
	}

	public DateTime getEntrada2() {
		return entrada2;
	}

	public void setEntrada2(DateTime entrada2) {
		this.entrada2 = entrada2;
	}

	public DateTime getSaida1() {
		return saida1;
	}

	public void setSaida1(DateTime saida1) {
		this.saida1 = saida1;
	}

	public DateTime getSaida2() {
		return saida2;
	}

	public void setSaida2(DateTime saida2) {
		this.saida2 = saida2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((entrada1 == null) ? 0 : entrada1.hashCode());
		result = prime * result
				+ ((entrada2 == null) ? 0 : entrada2.hashCode());
		result = prime * result + ((saida1 == null) ? 0 : saida1.hashCode());
		result = prime * result + ((saida2 == null) ? 0 : saida2.hashCode());
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
		Dia other = (Dia) obj;
		if (entrada1 == null) {
			if (other.entrada1 != null)
				return false;
		} else if (!entrada1.equals(other.entrada1))
			return false;
		if (entrada2 == null) {
			if (other.entrada2 != null)
				return false;
		} else if (!entrada2.equals(other.entrada2))
			return false;
		if (saida1 == null) {
			if (other.saida1 != null)
				return false;
		} else if (!saida1.equals(other.saida1))
			return false;
		if (saida2 == null) {
			if (other.saida2 != null)
				return false;
		} else if (!saida2.equals(other.saida2))
			return false;
		return true;
	}

}
