package fr.reminder.ui.type;

import fr.commons.generique.ui.AbstractGeneriqueViewHolder;
import fr.commons.generique.ui.SwipeController;
import fr.reminder.databinding.CardViewTypeBinding;
import fr.reminder.model.TypeEvenement;

public class LstTypeViewHolder extends AbstractGeneriqueViewHolder<TypeEvenement> {

	protected CardViewTypeBinding binding;

	public LstTypeViewHolder(CardViewTypeBinding binding) {
		super(binding.getRoot());
		this.binding = binding;
		this.binding.cardView.setMinimumHeight(SwipeController.btnMinWidth * 2);
	}

	@Override
	public void bind(TypeEvenement t) {
		this.binding.setTypeEvenement(t);
	}

}