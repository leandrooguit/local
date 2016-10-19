package ponto.model.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends UsuarioBingo {

	private String disciplina;

	@Column(name = "USU_DISCIPLINA")
	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
	
}
