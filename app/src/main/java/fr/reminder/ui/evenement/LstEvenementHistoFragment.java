package fr.reminder.ui.evenement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import fr.reminder.controller.dao.EvenementDAO;
import fr.reminder.model.Evenement;

public class LstEvenementHistoFragment extends LstEvenementFragment {

	@Override
	public View onCreateView(
			@NonNull LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState
	) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		this.binding.btnAddEvenement.setVisibility(View.GONE);
		return v;
	}

	@Override
	protected List<Evenement> getLstEvenement() {
		return EvenementDAO.getInstance().getLstEvenementHisto();
	}
}