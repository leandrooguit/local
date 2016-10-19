package ponto.model.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ALUNO")
public class Aluno extends UsuarioBingo {

}
