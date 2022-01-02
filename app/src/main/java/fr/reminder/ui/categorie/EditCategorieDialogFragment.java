package fr.reminder.ui.categorie;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import fr.commons.generique.controller.db.TableDAO;
import fr.commons.generique.ui.AbstractEditDialogObjetBddFragment;
import fr.reminder.R;
import fr.reminder.controller.dao.CategorieEvenementDAO;
import fr.reminder.databinding.EditCategorieBinding;
import fr.reminder.model.CategorieEvenement;

public class EditCategorieDialogFragment extends AbstractEditDialogObjetBddFragment<CategorieEvenement> {

	public EditCategorieDialogFragment(@NonNull CategorieEvenement item) {
		super(item);
	}

	@Override
	protected ViewDataBinding createBinding() {
		EditCategorieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
				R.layout.edit_categorie, null, false);
		this.item = this.item != null ? this.item : new CategorieEvenement();
		binding.setCategorie(this.item);
		return binding;
	}

	@Override
	protected TableDAO<CategorieEvenement> getTableDAO() {
		return CategorieEvenementDAO.getInstance();
	}
}