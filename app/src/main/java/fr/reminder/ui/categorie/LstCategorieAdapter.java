package fr.reminder.ui.categorie;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.function.Predicate;

import fr.commons.generique.ui.AbstractGeneriqueAdapter;
import fr.reminder.controller.utils.PredicateFilterBuilder;
import fr.reminder.databinding.CardViewCategorieBinding;
import fr.reminder.model.CategorieEvenement;

public class LstCategorieAdapter extends AbstractGeneriqueAdapter<CategorieEvenement,LstCategorieViewHolder> {
	public LstCategorieAdapter(List<CategorieEvenement> l) {
		super(l);
	}

	@Override
	protected Predicate<CategorieEvenement> getPredicateFilter(String json) {
		return PredicateFilterBuilder.getPredicateFilterById(json);
	}

	@NonNull
	@Override
	public LstCategorieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		CardViewCategorieBinding binding = CardViewCategorieBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
		return new LstCategorieViewHolder(binding);
	}
}