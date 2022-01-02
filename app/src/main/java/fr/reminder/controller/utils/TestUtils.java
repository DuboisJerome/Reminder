package fr.reminder.controller.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.reminder.model.CategorieEvenement;
import fr.reminder.model.Duree;
import fr.reminder.model.Evenement;
import fr.reminder.model.TypeEvenement;

public class TestUtils {

	private static List<Evenement> createListEvenement(int size) {
		List<Evenement> result = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			Evenement t = new Evenement(i);
			t.setDate(new Date());
			t.setLieu("Paris");
			t.setCommentaire("Commentaire");
			result.add(t);
		}
		return result;
	}

	private static List<CategorieEvenement> createListCategorie(int size) {
		List<CategorieEvenement> result = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			CategorieEvenement ci = new CategorieEvenement(i);
			ci.setNom("Nom_" + i);
			result.add(ci);
		}
		return result;
	}

	private static List<TypeEvenement> createListType(int size) {
		List<TypeEvenement> result = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			TypeEvenement t = new TypeEvenement(i);
			t.setNom("Nom_" + i);
			t.setIdCategorie(1);
			Duree v = new Duree(Duree.SEC_PAR_JOUR);
			t.setDelai(v);
			t.setFrequence(v);
			t.setCreationAutomatique(true);
			result.add(t);
		}
		return result;
	}
}