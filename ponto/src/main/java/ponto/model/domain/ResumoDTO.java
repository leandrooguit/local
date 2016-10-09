package ponto.model.domain;

import org.joda.time.DateTime;

public class ResumoDTO {

	private DateTime mesAno;
	private String saldo;
	private String total;
	private String horasFeitas;

	public DateTime getMesAno() {
		return mesAno;
	}

	public void setMesAno(DateTime mesAno) {
		this.mesAno = mesAno;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getHorasFeitas() {
		return horasFeitas;
	}

	public void setHorasFeitas(String horasFeitas) {
		this.horasFeitas = horasFeitas;
	}

}
