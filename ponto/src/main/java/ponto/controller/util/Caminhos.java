package ponto.controller.util;

public interface Caminhos {

	String INICIO = "public/inicio";
	String ACESSO_NEGADO = "public/acessoNegado";

	String LOGIN = "public/usuario/login";
	String CADASTRO_USUARIO = "public/usuario/cadastrar";
	String ALTERAR_SENHA = "secured/usuario/alterarSenha";

	String PONTOS_VISUALIZAR = "secured/ponto/pontos";
	String PONTOS_GERAR = "secured/ponto/gerar";
	String RESUMO = "secured/ponto/resumo";

	String FERIADOS_VISUALIZAR = "secured/feriado/feriados";

	String ABONOS_VISUALIZAR = "secured/abono/abonos";
	
	String JOGO = "secured/jogo/jogo";
	
	String CONFIGURACAO_VISUALIZAR = "secured/configuracao/configuracoes";
	
	String TIPO_CONJUNTO_VISUALIZAR = "secured/tipoconjunto/tipoConjuntos";

	
	//--------BINGO-------------
	String CRIAR_JOGOS = "secured/jogos/criar";
}
