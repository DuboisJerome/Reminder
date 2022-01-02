package fr.reminder.model;

import fr.commons.generique.model.db.AbstractObjetBddAvecId;
import fr.reminder.controller.dao.CategorieEvenementDAO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TypeEvenement extends AbstractObjetBddAvecId {
	private static final long serialVersionUID = 8384829180345258172L;

	private String nom;
	// Medical, Hygiène, Autre
	private long idCategorie;
	// Liste des durées avant évenement pour rappel
	private Duree rappel = new Duree();

	private boolean isCreationAutomatique;
	private boolean isProgrammable;
	// Temps généralement nécessaire à la programmation de l'évenement.
	// !! Durée ne doit pas être null pour le data binding
	private Duree delai = new Duree();
	// Temps entre chaque événement s'il est a répétition
	// !! Durée ne doit pas être null pour le data binding
	private Duree frequence = new Duree();
	// Durée de chaque événement
	// !! Durée ne doit pas être null pour le data binding
	private Duree duree = new Duree();

	public TypeEvenement() {
		super();
	}

	public TypeEvenement(long id) {
		super(id);
	}

	public CategorieEvenement getCategorie() {
		CategorieEvenementDAO dao = CategorieEvenementDAO.getInstance();
		return dao.getById(this.idCategorie);
	}
}