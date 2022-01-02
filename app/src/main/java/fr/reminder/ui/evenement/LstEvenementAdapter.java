package fr.reminder.ui.evenement;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import fr.commons.generique.ui.AbstractGeneriqueAdapter;
import fr.reminder.controller.utils.PredicateFilterBuilder;
import fr.reminder.databinding.CardViewEvenementBinding;
import fr.reminder.model.Evenement;

public class LstEvenementAdapter extends AbstractGeneriqueAdapter<Evenement,LstEvenementViewHolder> {
	public LstEvenementAdapter(List<Evenement> l) {
		super(l);
	}

	public LstEvenementAdapter(List<Evenement> l, Comparator<Evenement> c) {
		super(l, c);
	}

	@Override
	protected Predicate<Evenement> getPredicateFilter(String json) {
		return PredicateFilterBuilder.getPredicateFilterById(json);
	}

	@Override
	public LstEvenementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		CardViewEvenementBinding binding = CardViewEvenementBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);

		return new LstEvenementViewHolder(binding);
	}
}