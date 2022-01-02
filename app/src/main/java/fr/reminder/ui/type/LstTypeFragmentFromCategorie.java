package fr.reminder.ui.type;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import fr.reminder.controller.dao.CategorieEvenementDAO;
import fr.reminder.controller.dao.TypeEvenementDAO;
import fr.reminder.model.CategorieEvenement;
import fr.reminder.model.TypeEvenement;

public class LstTypeFragmentFromCategorie extends LstTypeFragment {

	protected CategorieEvenement preselectedCategorie;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		long idCategorie = LstTypeFragmentFromCategorieArgs.fromBundle(getArguments()).getIdCategorie();
		this.preselectedCategorie = CategorieEvenementDAO.getInstance().getById(idCategorie);

		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	protected List<TypeEvenement> getLstTypeEvenement() {
		if (this.preselectedCategorie == null) {
			return super.getLstTypeEvenement();
		}
		return TypeEvenementDAO.getInstance().getByIdCategorie(this.preselectedCategorie.getId());
	}

	@Override
	protected EditTypeDialogFragment creerDialogEditType(LstTypeAdapter adapterType) {
		EditTypeDialogFragment d = super.creerDialogEditType(adapterType);
		d.setPreselectedCategorie(this.preselectedCategorie);
		return d;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		this.preselectedCategorie = null;
	}
}