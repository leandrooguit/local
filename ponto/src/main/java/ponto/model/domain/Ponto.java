package ponto.model.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import ponto.model.service.PontoService;

@Entity
@Table(name = "pon_ponto")
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "pon_id", nullable = false, insertable = true, updatable = false)) })
public class Ponto extends Entidade implements Serializable {

	private static final long serialVersionUID = -2662710674455652057L;

	@ManyToOne
	@JoinColumn(name = "usu_id", nullable = false)
	private Usuario usuario;

	@Column(name = "pon_entrada_2")
	private DateTime entrada2;

	@Column(name = "pon_saida_1")
	private DateTime saida1;

	@Column(name = "pon_saida_2")
	private DateTime saida2;

	@Transient
	private transient String total;

	public Ponto() {

	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
				+ ((entrada2 == null) ? 0 : entrada2.hashCode());
		result = prime * result + ((saida1 == null) ? 0 : saida1.hashCode());
		result = prime * result + ((saida2 == null) ? 0 : saida2.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		return true;
	}

	@Override
	public String toString() {
		return saida1.toString();
	}

}
