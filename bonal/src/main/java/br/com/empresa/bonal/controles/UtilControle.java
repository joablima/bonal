package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class UtilControle implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
