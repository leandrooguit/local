package ponto.model.domain;

public enum EQtdElementoCartela {

	TRES_X_QUATRO("3 X 4"),
	TRES_X_CINCO("3 X 5"),
	TRES_X_SEIS("3 X 6"),
	TRES_X_SETE("3 X 7");
	
	private String descricao;
	
	EQtdElementoCartela(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
