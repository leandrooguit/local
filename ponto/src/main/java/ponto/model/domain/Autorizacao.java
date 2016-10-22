package ponto.model.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "AUT_AUTORIZACAO")
public class Autorizacao implements GrantedAuthority {

	private static final long serialVersionUID = -1576287963725850699L;
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	@Id
	@Column(name = "AUT_AUTORIZACAO", nullable = false, insertable = true, updatable = false)
	private String autorizacao;

	public Autorizacao() {

	}

	public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getAuthority() {
		return autorizacao;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 83 * hash + Objects.hashCode(this.autorizacao);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Autorizacao other = (Autorizacao) obj;
		if (!Objects.equals(this.autorizacao, other.autorizacao)) {
			return false;
		}
		return true;
	}

}
