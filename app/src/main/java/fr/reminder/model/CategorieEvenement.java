package fr.reminder.model;

import fr.commons.generique.model.db.AbstractObjetBddAvecId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CategorieEvenement extends AbstractObjetBddAvecId {
	private static final long serialVersionUID = 4052676170692092093L;

	private String nom;

	public CategorieEvenement() {
		super();
	}

	public CategorieEvenement(long id) {
		super(id);
	}

}