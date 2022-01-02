package fr.reminder.ui.categorie;

import android.content.Context;

import androidx.annotation.NonNull;

import fr.commons.generique.ui.AbstractGeneriqueSpinnerAdapter;
import fr.reminder.model.CategorieEvenement;

public class CategorieSpinnerAdapter extends AbstractGeneriqueSpinnerAdapter<CategorieEvenement> {

	public CategorieSpinnerAdapter(@NonNull Context context) {
		super(context);
	}

	@Override
	protected String getLibelle(CategorieEvenement categorieEvenement) {
		return categorieEvenement.getNom();
	}
}