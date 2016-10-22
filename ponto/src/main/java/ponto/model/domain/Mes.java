package ponto.model.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "mes_mes")
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "mes_id", nullable = false, insertable = true, updatable = false)) })
public class Mes extends Entidade implements Serializable {

	private static final long serialVersionUID = -8597254226930685115L;

	@Column(name = "mes_mes_referencia", nullable = false)
	private LocalDate mesReferencia;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "mes_id")
	private List<Dia> dias;

	public Mes() {

	}

	public LocalDate getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(LocalDate mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public List<Dia> getDias() {
		return dias;
	}

	public void setDias(List<Dia> dias) {
		this.dias = dias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dias == null) ? 0 : dias.hashCode());
		result = prime * result
				+ ((mesReferencia == null) ? 0 : mesReferencia.hashCode());
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
		Mes other = (Mes) obj;
		if (dias == null) {
			if (other.dias != null)
				return false;
		} else if (!dias.equals(other.dias))
			return false;
		if (mesReferencia == null) {
			if (other.mesReferencia != null)
				return false;
		} else if (!mesReferencia.equals(other.mesReferencia))
			return false;
		return true;
	}

}
