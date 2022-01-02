package fr.reminder.controller.utils;

import androidx.databinding.InverseMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.commons.generique.controller.utils.Log;
import fr.reminder.model.Evenement;
import fr.reminder.model.TypeEvenement;

public class BindingConverter {

	@InverseMethod("stringToInt")
	public static String intToString(int i) {
		String str = Integer.toString(i);
		return str;
	}

	public static int stringToInt(String s) {
		if (s == null || s.isEmpty()) return 0;
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			Log.error("Erreur stringToInt " + s, nfe);
			return 0;
		}
	}

	@InverseMethod("stringToLong")
	public static String longToString(long l) {
		return Long.toString(l);
	}

	public static long stringToLong(String s) {
		if (s == null || s.isEmpty()) return 0;
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException nfe) {
			Log.error("Erreur stringToLong " + s, nfe);
			return 0;
		}
	}

	private static final String FORMAT_DATE_TIME = "dd/MM/yyyy\nHH:mm";

	@InverseMethod("stringToDate")
	public static String dateToString(Date date) {
		if (date == null) return "";
		final SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME, Locale.getDefault());
		return dateFormat.format(date);
	}

	public static Date stringToDate(String date) {
		if (date == null || date.isEmpty()) return null;
		try {
			final SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME, Locale.getDefault());
			return dateFormat.parse(date);
		} catch (ParseException e) {
			Log.error("String to date", e);
			return null;
		}
	}

	public static String evenementTypeToString(Evenement e) {
		TypeEvenement t = e.getType();
		return t.getNom() + "\n" + t.getCategorie().getNom();
	}

}