package fr.reminder.model;

import android.os.SystemClock;

import java.util.Date;

import fr.commons.generique.model.db.AbstractObjetBddAvecId;
import fr.reminder.controller.dao.TypeEvenementDAO;
import fr.reminder.controller.utils.DureeManager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Evenement extends AbstractObjetBddAvecId {
	private static final long serialVersionUID = 4708368215014782442L;

	private Date date;
	private long idType;
	private String lieu;
	private String commentaire;
	private boolean isProgramme;

	public Evenement() {
		super();
	}

	public Evenement(long id) {
		super(id);
	}

	public TypeEvenement getType() {
		TypeEvenementDAO dao = TypeEvenementDAO.getInstance();
		return dao.getById(this.idType);
	}

	public long getRappelMs() {
		TypeEvenement t = getType();
		Duree rappel = t.getRappel();
		Date dateDebut = getDate();
		long rappelMs;
		if (rappel == null || rappel.isEmpty()) {
			rappelMs = -1;
		} else {
			// Récupère directement l'heure de rappel
			long now = SystemClock.currentThreadTimeMillis();
			rappelMs = dateDebut.getTime() - rappel.toFullSecondes() * 1000L;
		}
		return rappelMs;
	}

	public boolean isNeedRappel() {
		TypeEvenement typeEvenement = getType();
		Duree rappel = typeEvenement.getRappel();
		Date dateSiRappelNow = new DureeManager().add(rappel).getTime();
		// Si date courant + rappel > date evenement
		// c'est à dire : date courante > date evenement - rappel
		return dateSiRappelNow.after(getDate());
	}

}