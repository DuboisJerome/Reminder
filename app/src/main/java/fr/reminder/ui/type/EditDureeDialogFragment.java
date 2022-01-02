package fr.reminder.ui.type;

import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import fr.commons.generique.ui.AbstractItemDialogFragment;
import fr.reminder.R;
import fr.reminder.databinding.EditDureeBinding;
import fr.reminder.model.Duree;

public class EditDureeDialogFragment extends AbstractItemDialogFragment<Duree> {

	public EditDureeDialogFragment(Duree item) {
		super(item);
	}

	@Override
	protected ViewDataBinding createBinding() {
		EditDureeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
				R.layout.edit_duree, null, false);
		binding.setDuree(this.item);
		return binding;
	}
}