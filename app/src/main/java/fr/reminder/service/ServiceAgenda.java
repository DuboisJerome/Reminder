package fr.reminder.service;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import fr.reminder.controller.utils.DureeManager;
import fr.reminder.model.Evenement;

public class ServiceAgenda {

	// FIXME revoir
	public static void gestionEvenementFutur() {
//		Date now = new Date();
//		EvenementDAO evenementDAO = new EvenementDAO();
//		List<TypeEvenement> lstTypeEvenement = TypeEvenementDAO.getInstance().selectAll();
//		List<Evenement> lstEvenement = EvenementDAO.getInstance().selectAll();
//		List<Evenement> lstEvenementPasse = lstEvenement.stream().filter(rdv -> rdv.getDate().before(now)).collect(Collectors.toList());
//		List<Evenement> lstEvenementFutur = lstEvenement.stream().filter(rdv -> rdv.getDate().compareTo(now) > 0).collect(Collectors.toList());
//		for (TypeEvenement type : lstTypeEvenement) {
//			Evenement lastEvenement = getLastEvenement(type, lstEvenementPasse);
//			Evenement nextEvenement = getNextEvenement(type, lstEvenementFutur);
//			Date dateLastEvenement = lastEvenement == null ? null : lastEvenement.getDate();
//			Date dateNextEvenementTheorique = computeDateNextEvenementTheorique(type, dateLastEvenement, false);
//			// Si ce type d'evenement n'a pas de fréquence, la date théorique est null
//			// car il n'y a pas de prochaine activité à prévoir
//			if (dateNextEvenementTheorique == null) {
//				continue;
//			}
//			Date dateNextEvenementTheoriqueDelai = computeDateNextEvenementTheorique(type, dateLastEvenement, true);
//			if (nextEvenement == null) {
//				if (type.isCreationAutomatique()) {
//					Evenement a = new Evenement();
//					a.setDate(dateNextEvenementTheorique);
//					a.setIdType(type.getId());
//					evenementDAO.creer(a);
//				} else {
//					// TODO Alerte : Il n'y a pas d'activité de type {0} de prévu prochainement.
//					// La date optimale de la prochaine activité est le {1}
//					if (dateNextEvenementTheoriqueDelai.after(dateNextEvenementTheorique)) {
//						// ou le {2} si on tient compte du delai de {3} {4}
//					}
//					// TODO => priorité HAUT
//				}
//			} else if (nextEvenement.getDate().after(dateNextEvenementTheorique)) {
//				if (type.isCreationAutomatique()) {
//					nextEvenement.setDate(dateNextEvenementTheorique);
//					evenementDAO.update(nextEvenement);
//				} else {
//					// TODO Alerte : Le prochaine activité de type {0} est le {1}.
//					// La date optimale de la prochaine activité est le {2}.
//					// Le délai pour le démarrage de ce type d'activité est de {3} {4}
//					if (nextEvenement.getDate().after(dateNextEvenementTheoriqueDelai)) {
//						// Il est donc possible d'avancer cette activité pour la commencer le {5}
//						// TODO => priorité MOYENNE
//					} else {
//						// Il n'est sans doute pas possible d'avancer la date de la prochaine activité
//						// TODO => priorité BASSE
//					}
//				}
//			}
//		}
	}

	private static void ajouterEvenementAgendaTel(Context ctx, Evenement ev) {
		// TODO
		Calendar cal = Calendar.getInstance();
		cal.setTime(ev.getDate());
		Intent intent = new Intent(Intent.ACTION_EDIT);
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra("beginTime", cal.getTimeInMillis());
		intent.putExtra("allDay", true);
		intent.putExtra("rrule", "FREQ=YEARLY");
		long endTime = new DureeManager(ev.getDate()).add(ev.getType().getDuree()).getTimeInMillis();
		intent.putExtra("endTime", endTime);
		intent.putExtra("title", "A Test Event from android app");
		intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(intent);
	}

}