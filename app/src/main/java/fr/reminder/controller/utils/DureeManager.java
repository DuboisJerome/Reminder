package fr.reminder.controller.utils;

import java.util.Calendar;
import java.util.Date;

import fr.reminder.model.Duree;

public class DureeManager {

	private Calendar cal = Calendar.getInstance();

	public DureeManager() {

	}

	public DureeManager(Date dateInit) {
		this.cal.setTime(dateInit);
	}


	public DureeManager add(Duree d) {
		this.cal.add(Calendar.YEAR, d.getAnnee());
		this.cal.add(Calendar.MONTH, d.getMois());
		this.cal.add(Calendar.DATE, d.getJour());
		this.cal.add(Calendar.HOUR, d.getHeure());
		this.cal.add(Calendar.MINUTE, d.getMinute());
		this.cal.add(Calendar.SECOND, d.getSeconde());
		return this;
	}

	public Date getTime() {
		return this.cal.getTime();
	}

	public long getTimeInMillis() {
		return this.cal.getTimeInMillis();
	}
}