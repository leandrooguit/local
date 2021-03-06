package ponto.model.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends Usuario {

	@Column(name = "USU_DISCIPLINA")
	private String disciplina;

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
	
}
