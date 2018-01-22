package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class DateUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	private Calendar calendar = Calendar.getInstance();

	private Date data;

	public DateUtil getHoje() {
		data = calendar.getTime();
		return this;
	}

	public DateUtil getDataFuturo(Integer dias) {
		if (dias < 0)
			dias = dias * (-1);
		data = getDate(dias);
		return this;
	}

	public DateUtil getDataPassado(Integer dias) {
		if (dias > 0)
			dias = dias * (-1);
		data = getDate(dias);
		return this;
	}

	public DateUtil adicionarData(String string) {
		data = stringToDate(string);
		return this;
	}

	private Date getDate(Integer dias) {
		calendar.setTime(data);
		calendar.add(Calendar.DATE, dias);
		return calendar.getTime();
	}

	private Date stringToDate(String string) {
		String formato;
		if (string.trim().length() == 10)
			formato = "dd/MM/yyyy";
		else if (string.trim().length() == 19)
			formato = "dd/MM/yyyy HH:mm:ss";
		else
			throw new IllegalArgumentException("não foi possivel converter a data informada!");

		Date date;
		try {
			date = new SimpleDateFormat(formato).parse(string);
		} catch (ParseException e) {
			System.out.println("não foi possivel converter a data informada!");
			throw new IllegalArgumentException(e);
		}
		return date;
	}

	public Double contarDias(String string) {
		Date data2 = stringToDate(string);
		Double dias = (double) ((data2.getTime() - data.getTime()) / (24 * 60 * 60 * 1000));
		if (data2.after(data))
			dias += 1;
		return ((dias < 0) ? (dias * (-1)) : dias);
	}

	public Date toDate() {
		return data;
	}

	public String toStringDataHora() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
	}

	public String toStringData() {
		return new SimpleDateFormat("dd/MM/yyyy").format(data);
	}

	public String toStringHora() {
		return new SimpleDateFormat("HH:mm:ss").format(data);
	}

}
