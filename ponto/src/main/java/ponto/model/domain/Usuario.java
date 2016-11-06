package ponto.model.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

@Entity
@Table(name = "USU_USUARIO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USU_PERFIL", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USUARIO")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "USU_ID", nullable = false, insertable = true, updatable = false))})
public class Usuario extends Entidade implements Serializable {

	private static final long serialVersionUID = -8854383391546163244L;

	@Column(name = "USU_NOME", nullable = false, length = 200)
	private String nome;

	@Column(name = "USU_LOGIN", nullable = false, unique = true, length = 250)
	private String login;

	@Column(name = "USU_SENHA", nullable = false, length = 10)
	private String senha;
	
	@ManyToOne
	@JoinColumn(name = "JOG_ID", nullable = true)
	private Jogo jogo;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USU_USUARIO_AUTORIZACAO", joinColumns = @JoinColumn(name = "USU_ID"), inverseJoinColumns = @JoinColumn(name = "AUT_AUTORIZACAO"))
	private Set<Autorizacao> autorizacoes;
	
	@OneToOne
//	@Column(name = "CAR_ID")
	@PrimaryKeyJoinColumn
	private Cartela cartela;

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

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public Set<Autorizacao> getAutorizacoes() {
		if (autorizacoes != null && !Hibernate.isInitialized(autorizacoes)) {
			Hibernate.initialize(autorizacoes);
		}
		return autorizacoes;
	}

	public void setAutorizacoes(Set<Autorizacao> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}
	
	public Cartela getCartela() {
		return cartela;
	}

	public void setCartela(Cartela cartela) {
		this.cartela = cartela;
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
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
