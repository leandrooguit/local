package ponto.model.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "abo_abono")
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "abo_id", nullable = false, insertable = true, updatable = false)),
		@AttributeOverride(name = "versao", column = @Column(name = "abo_versao", nullable = false)),
		@AttributeOverride(name = "dataCadastro", column = @Column(name = "abo_data_cadastro", nullable = false, insertable = true, updatable = false)) })
public class Abono extends Entidade implements Serializable {

	private static final long serialVersionUID = -8374699036142289097L;

	@Column(name = "abo_descricao", nullable = false)
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "abo_local", nullable = false)
	private EAbono abono;

	@Column(name = "abo_qtd_horas")
	private Integer qtdHoras;

	@Column(name = "abo_data_inicial", nullable = false)
	private LocalDate dataInicial;

	@Column(name = "abo_data_final")
	private LocalDate dataFinal;

	@Column(name = "abo_aprovado")
	private boolean aprovado;

	@ManyToOne
	@JoinColumn(name = "usu_id")
	private Usuario usuario;

	public Abono() {

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EAbono getAbono() {
		return abono;
	}

	public void setAbono(EAbono abono) {
		this.abono = abono;
	}

	public Integer getQtdHoras() {
		return qtdHoras;
	}

	public void setQtdHoras(Integer qtdHoras) {
		this.qtdHoras = qtdHoras;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((abono == null) ? 0 : abono.hashCode());
		result = prime * result + (aprovado ? 1231 : 1237);
		result = prime * result
				+ ((dataFinal == null) ? 0 : dataFinal.hashCode());
		result = prime * result
				+ ((dataInicial == null) ? 0 : dataInicial.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((qtdHoras == null) ? 0 : qtdHoras.hashCode());
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
		Abono other = (Abono) obj;
		if (abono != other.abono)
			return false;
		if (aprovado != other.aprovado)
			return false;
		if (dataFinal == null) {
			if (other.dataFinal != null)
				return false;
		} else if (!dataFinal.equals(other.dataFinal))
			return false;
		if (dataInicial == null) {
			if (other.dataInicial != null)
				return false;
		} else if (!dataInicial.equals(other.dataInicial))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (qtdHoras == null) {
			if (other.qtdHoras != null)
				return false;
		} else if (!qtdHoras.equals(other.qtdHoras))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
