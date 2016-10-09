package ponto.model.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "fer_feriado")
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "fer_id", nullable = false, insertable = true, updatable = false)),
		@AttributeOverride(name = "versao", column = @Column(name = "fer_versao", nullable = false)),
		@AttributeOverride(name = "dataCadastro", column = @Column(name = "fer_data_cadastro", nullable = false, insertable = true, updatable = false)) })
public class Feriado extends Entidade implements Serializable {

	private static final long serialVersionUID = 6033608272293976616L;

	@Column(name = "fer_nome", nullable = false)
	private String nome;

	@Column(name = "fer_data", nullable = false)
	private LocalDate data;

	@Column(name = "fer_persistente", nullable = false)
	private boolean persistente;

	@Enumerated(EnumType.STRING)
	@Column(name = "fer_local", nullable = false)
	private ELocal local;

	public Feriado() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public boolean isPersistente() {
		return persistente;
	}

	public void setPersistente(boolean persistente) {
		this.persistente = persistente;
	}

	public ELocal getLocal() {
		return local;
	}

	public void setLocal(ELocal local) {
		this.local = local;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (persistente ? 1231 : 1237);
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
		Feriado other = (Feriado) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (local != other.local)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (persistente != other.persistente)
			return false;
		return true;
	}

}
