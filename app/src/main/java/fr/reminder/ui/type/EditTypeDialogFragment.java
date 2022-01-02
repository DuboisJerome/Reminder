package fr.reminder.ui.type;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import fr.commons.generique.controller.db.TableDAO;
import fr.commons.generique.ui.AbstractEditDialogObjetBddFragment;
import fr.reminder.R;
import fr.reminder.controller.dao.CategorieEvenementDAO;
import fr.reminder.controller.dao.TypeEvenementDAO;
import fr.reminder.databinding.EditTypeBinding;
import fr.reminder.model.CategorieEvenement;
import fr.reminder.model.Duree;
import fr.reminder.model.TypeEvenement;
import fr.reminder.ui.categorie.CategorieSpinnerAdapter;
import lombok.Setter;

public class EditTypeDialogFragment extends AbstractEditDialogObjetBddFragment<TypeEvenement> {

	@Setter
	private CategorieEvenement preselectedCategorie;

	public EditTypeDialogFragment(@NonNull TypeEvenement item) {
		super(item);
	}


	@Override
	protected ViewDataBinding createBinding() {
		EditTypeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
				R.layout.edit_type, null, false);

		binding.setTypeEvenement(this.item);

		// you need to have a list of data that you want the spinner to display
		CategorieSpinnerAdapter adapter = new CategorieSpinnerAdapter(requireContext());
		if (this.preselectedCategorie != null) {
			adapter.getList().add(this.preselectedCategorie);
			binding.getTypeEvenement().setIdCategorie(this.preselectedCategorie.getId());
		} else {
			List<CategorieEvenement> lstCategorie = CategorieEvenementDAO.getInstance().selectAll();
			adapter.getList().addAll(lstCategorie);
		}
		binding.categorie.setAdapter(adapter);
		binding.categorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				binding.getTypeEvenement().setIdCategorie(adapter.getItemId(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Ne devrait pas arriver
				throw new NullPointerException("Rien a été sélectionné, ce choix ne devrait pas être possible");
			}
		});

		TypeEvenement t = binding.getTypeEvenement();
		bindClickDuree(binding.delai, t::getDelai, t::setDelai);
		bindClickDuree(binding.rappel, t::getRappel, t::setRappel);
		bindClickDuree(binding.duree, t::getDuree, t::setDuree);
		bindClickDuree(binding.freq, t::getFrequence, t::setFrequence);
		return binding;
	}

	private void bindClickDuree(AppCompatTextView textView, Supplier<Duree> getDuree, Consumer<Duree> setDuree) {
		textView.setOnClickListener((v) -> {
			EditDureeDialogFragment e = new EditDureeDialogFragment(getDuree.get());
			e.addOnItemValideListener(d -> {
				setDuree.accept(d);
				// On ne peut pas binder String -> Duree alors on met à jour l'interface explicitement
				textView.setText(d.toString());
			});
			e.show(getParentFragmentManager(), "DIALOG_FREQ");
		});
	}

	@Override
	protected TableDAO<TypeEvenement> getTableDAO() {
		return TypeEvenementDAO.getInstance();
	}
}