package fr.reminder.ui.evenement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.Comparator;
import java.util.List;

import fr.reminder.controller.dao.EvenementDAO;
import fr.reminder.model.Evenement;

public class LstEvenementAVenirFragment extends LstEvenementFragment {

	@Override
	public View onCreateView(
			@NonNull LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState
	) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		this.binding.btnAddEvenement.setOnClickListener(btn -> {
			EditEvenementDialogFragment d = creerDialogEditEvenement();
			d.show(getParentFragmentManager(), "DIALOG_EVENEMENT");
		});
		return v;
	}

	@Override
	protected List<Evenement> getLstEvenement() {
		return EvenementDAO.getInstance().getLstEvenementAVenir();
	}

	@Override
	protected Comparator<Evenement> getComparatorLstEvenement() {
		return (e1, e2) -> {
			if (e1 == null && e2 == null) return 0;
			if (e1 == null) return -1;
			if (e2 == null) return 1;
			return e1.getDate().compareTo(e2.getDate());
		};
	}

	protected EditEvenementDialogFragment creerDialogEditEvenement() {
		Evenement newEvenement = new Evenement();
		EditEvenementDialogFragment d = new EditEvenementDialogFragment(newEvenement);
		d.addOnItemValideListener(this.adapter::addItem);
		return d;
	}
}