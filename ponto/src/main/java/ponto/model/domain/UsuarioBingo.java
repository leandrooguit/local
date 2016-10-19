package ponto.model.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//Projeto BINGO
@Entity
@Table(name = "USU_USUARIO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USU_PERFIL", discriminatorType = DiscriminatorType.STRING)
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "USU_ID", nullable = false, insertable = true, updatable = false))})
public abstract class UsuarioBingo extends EntidadeBingo {

	private String login;
	private String senha;
	private Integer matricula;
	private String nome;
	@ManyToMany
    @JoinTable(name="UCO_USUARIO_CONFIGURACAO", joinColumns= @JoinColumn(name="USU_ID") , 
    	inverseJoinColumns= @JoinColumn(name="CON_ID"))
	private List<Configuracao> configuracoes;
	@ManyToMany
    @JoinTable(name="UJO_USUARIO_JOGO", joinColumns= @JoinColumn(name="USU_ID") , 
    	inverseJoinColumns= @JoinColumn(name="JOG_ID"))
	private List<Jogo> jogos;
	
	@Column(name = "USU_LOGIN")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column(name = "USU_SENHA")
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Column(name = "USU_MATRICULA")
	public Integer getMatricula() {
		return matricula;
	}
	
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	
	@Column(name = "USU_NOME")
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Configuracao> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<Configuracao> configuracoes) {
		this.configuracoes = configuracoes;
	}

	public List<Jogo> getJogos() {
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}

}
