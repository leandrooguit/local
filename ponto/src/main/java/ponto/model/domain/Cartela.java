package ponto.model.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CAR_CARTELA")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "CAR_ID", nullable = false, insertable = true, updatable = false))})
public class Cartela extends Entidade {

	@ManyToMany
    @JoinTable(name="CBO_CARTELA_BOLA", joinColumns={@JoinColumn(name="CAR_ID")}, 
    	inverseJoinColumns={@JoinColumn(name="BOL_ID")})
    private List<Bola> bolas;

	public List<Bola> getBolas() {
		return bolas;
	}

	public void setBolas(List<Bola> bolas) {
		this.bolas = bolas;
	}
	
}
