package ponto.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Util {

	public static DateTime stringDataToDateTime(String data) {
		return DateTime.parse(data, DateTimeFormat.forPattern("dd/MM/yyyy"));
	}
	
	public static int calcHoraEmSegundos(String horaCompleta) {
        int resultSegundos = 0;

        //pego a posição onde esta os (dois pontos ':') e uso (-2) para ver o inicio da hora
        int posicao = horaCompleta.indexOf(":");

        /*
         * acho as posições exatas da hora, minuto e segundo e jogo em variaveis não usei direto
         * int aqui, porque o int corta o zero à esquera (exemplo 01 - fica 1)
         */
        String h = horaCompleta.substring(0, posicao);
        String m = horaCompleta.substring(posicao + 1, posicao + 3);
        String s = horaCompleta.substring(posicao + 4, posicao + 6);

        //faço calculos da hora em minutos depois em segundos.
        int hora = Integer.parseInt(h) * 3600;
        int minutos = Integer.parseInt(m) * 60;
        int segundos = Integer.parseInt(s);

        //somo tudo os resultados em segundos
        resultSegundos = hora + minutos + segundos;

        //retorno do resultado
        return resultSegundos;
    }

	
	  public static String converterSegundosEmHHMMSS(long segundos) {

	        long segundo = segundos % 60;
	        long minutos = segundos / 60;
	        long minuto = minutos % 60;
	        long hora = minutos / 60;
	        return String.format("%02d:%02d:%02d", Math.abs(hora), Math.abs(minuto), Math.abs(segundo));

	    }
}
