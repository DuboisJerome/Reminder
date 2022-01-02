package fr.reminder.ui.type;

import android.content.Context;

import androidx.annotation.NonNull;

import fr.commons.generique.ui.AbstractGeneriqueSpinnerAdapter;
import fr.reminder.model.TypeEvenement;

public class TypeSpinnerAdapter extends AbstractGeneriqueSpinnerAdapter<TypeEvenement> {

	public TypeSpinnerAdapter(@NonNull Context context) {
		super(context);
	}

	@Override
	protected String getLibelle(TypeEvenement t) {
		return t.getNom() + " - " + t.getCategorie().getNom();
	}
}