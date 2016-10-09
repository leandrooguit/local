package ponto.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

@Entity
@Table(name = "usu_usuario")
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "usu_id", nullable = false, insertable = true, updatable = false)),
		@AttributeOverride(name = "versao", column = @Column(name = "usu_versao", nullable = false)),
		@AttributeOverride(name = "dataCadastro", column = @Column(name = "usu_data_cadastro", nullable = false, insertable = true, updatable = false)) })
public class Usuario extends Entidade implements Serializable {

	private static final long serialVersionUID = -8854383391546163244L;

	@Column(name = "usu_nome", nullable = false, length = 200)
	private String nome;

	@Column(name = "usu_login", nullable = false, unique = true, length = 250)
	private String login;

	@Column(name = "usu_senha", nullable = false, length = 10)
	private String senha;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "usa_usuario_autorizacao", joinColumns = @JoinColumn(name = "usu_id"), inverseJoinColumns = @JoinColumn(name = "aut_autorizacao"))
	private Set<Autorizacao> autorizacoes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
	private List<Ponto> dias;

	@Enumerated(EnumType.STRING)
	@Column(name = "usu_local", nullable = false)
	private ELocal local;

	@Transient
	private transient String senhaAtual;
	@Transient
	private transient String confirmacaoSenha;

	public Usuario() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(Set<Autorizacao> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public List<Ponto> getDias() {
		return dias;
	}

	public void setDias(List<Ponto> dias) {
		this.dias = dias;
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
		result = prime
				* result
				+ ((autorizacoes == null || !Hibernate
						.isInitialized(autorizacoes)) ? 0 : autorizacoes
						.hashCode());
		result = prime * result + ((dias == null) ? 0 : dias.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
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
		Usuario other = (Usuario) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

}
