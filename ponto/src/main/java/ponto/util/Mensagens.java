package ponto.util;

public interface Mensagens {

	String TIPO_SUCCESS = "msgSuccess";
	String TIPO_INFO = "msgInfo";
	String TIPO_WARNING = "msgWarning";
	String TIPO_DANGER = "msgDanger";

	// Site
	String CAMPOS_OBRIGATORIOS = "Campos obrigatórios não preenchidos";
	String INFORMACOES_SALVAS_SUCESSO = "Informações salvas com sucesso";
	String ACESSO_NEGADO = "Acesso negado!";

	// Usuário
	String CADASTRO_EFETUADO_SUCESSO = "Cadastro efetuado com sucesso";
	String SENHAS_NAO_CONFEREM = "Senhas não conferem";
	String SENHA_ATUAL_INVALIDA = "Senha atual inválida";
	String SENHA_ALTERADA_COM_SUCESSO = "Senha alterada com sucesso";
	String EMAIL_SENHA_INVALIDO = "Email e/ou senha inválidos";

	String LOGIN_JA_CADASTRADO = "Login já cadastrado";

	// Ponto
	String PONTO_REGISTRADO_COM_SUCESSO = "Ponto registrado com sucesso!";
	String PONTO_REGISTRADO_MENOS_1_HORA = "Você registrou o ponto a menos de 5 minutos.";

	// Feriado
	String ANO_NAO_DEVE_SER_PREENCHIDO = "O Ano não deve ser preenchido";

}
