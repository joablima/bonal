package br.com.empresa.bonal.controles;

import java.util.Calendar;
import java.util.Date;

public class UtilControle {

	private Date hoje = Calendar.getInstance().getTime();

	public Date getHoje() {
		return hoje;
	}

	public Date getMaxDate(Integer dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hoje);
		calendar.add(Calendar.DATE, dias);
		Date maxDate = calendar.getTime();
		return maxDate;
	}

	public Date getMinDate(Integer dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hoje);
		calendar.add(Calendar.DATE, -dias);
		Date minDate = calendar.getTime();
		return minDate;
	}
	
	
	public Date getMaxDateByMinDate(Date minDate, Integer dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(minDate);
		calendar.add(Calendar.DATE, dias);
		Date maxDate = calendar.getTime();
		return maxDate;
	}
	
}
