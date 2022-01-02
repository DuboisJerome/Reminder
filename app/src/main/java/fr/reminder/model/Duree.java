package fr.reminder.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Duree implements Serializable {
	private static final long serialVersionUID = -6982983558164060608L;

	public static final int SEC_PAR_HEURE = 60 * 60;
	public static final int SEC_PAR_JOUR = SEC_PAR_HEURE * 24;
	public static final int SEC_PAR_MOIS = SEC_PAR_JOUR * 30;
	public static final int SEC_PAR_AN = SEC_PAR_JOUR * 365;

	private int annee, mois, jour, heure, minute, seconde;

	public Duree() {

	}

	public Duree(int fullSecondes) {
		if (fullSecondes <= 0) return;
		int s = fullSecondes;
		this.annee = s / SEC_PAR_AN;
		s = s % SEC_PAR_AN;
		this.mois = s / SEC_PAR_MOIS;
		s = s % SEC_PAR_MOIS;
		this.jour = s / SEC_PAR_JOUR;
		s = s % SEC_PAR_JOUR;
		this.heure = s / SEC_PAR_HEURE;
		s = s % SEC_PAR_HEURE;
		this.minute = s / 60;
		s = s % 60;
		this.seconde = s;
	}

	private static int toFullSecondes(EchelleTemps e, int val) {
		int valEnSecondes;
		switch (e) {
			case ANNEE:
				valEnSecondes = val * SEC_PAR_AN;
				break;
			case MOIS:
				valEnSecondes = val * SEC_PAR_MOIS; // !!Pas précis
				break;
			case JOUR:
				valEnSecondes = val * SEC_PAR_JOUR;
				break;
			case HEURE:
				valEnSecondes = val * SEC_PAR_HEURE;
				break;
			case MINUTE:
				valEnSecondes = val * 60;
				break;
			case SECONDE:
			default:
				valEnSecondes = val;
				break;
		}
		return valEnSecondes;
	}


	public int toFullSecondes() {
		int valEnSecondes = 0;
		valEnSecondes += this.annee * SEC_PAR_AN;
		valEnSecondes += this.mois * SEC_PAR_MOIS;
		valEnSecondes += this.jour * SEC_PAR_JOUR;
		valEnSecondes += this.heure * SEC_PAR_HEURE;
		valEnSecondes += this.minute * 60;
		valEnSecondes += this.seconde;
		return valEnSecondes;
	}

	public boolean isEmpty() {
		return toFullSecondes() == 0;
	}

	@NonNull
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int s = this.toFullSecondes();
		if (s == 0) return "";

		int annee = s / SEC_PAR_AN;
		s = s % SEC_PAR_AN;
		if (annee > 0) {
			sb.append(annee).append(" ans ");
		}
		int mois = s / SEC_PAR_MOIS;
		s = s % SEC_PAR_MOIS;
		if (mois > 0) {
			sb.append(mois).append(" mois ");
		}
		int jour = s / SEC_PAR_JOUR;
		s = s % SEC_PAR_JOUR;
		if (jour > 0) {
			sb.append(jour).append(" jrs ");
		}
		int heure = s / SEC_PAR_HEURE;
		s = s % SEC_PAR_HEURE;
		if (heure > 0) {
			sb.append(heure).append(" h ");
		}
		int minutes = s / 60;
		s = s % 60;
		if (minutes > 0) {
			sb.append(minutes).append(" m ");
		}
		if (s > 0) {
			sb.append(s).append(" s ");
		}

		return sb.toString();
	}

}