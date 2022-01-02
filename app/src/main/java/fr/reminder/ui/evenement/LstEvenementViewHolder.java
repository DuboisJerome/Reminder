package fr.reminder.ui.evenement;

import fr.commons.generique.ui.AbstractGeneriqueViewHolder;
import fr.reminder.databinding.CardViewEvenementBinding;
import fr.reminder.model.Evenement;

public class LstEvenementViewHolder extends AbstractGeneriqueViewHolder<Evenement> {

	protected CardViewEvenementBinding binding;

	public LstEvenementViewHolder(CardViewEvenementBinding binding) {
		super(binding.getRoot());
		this.binding = binding;
	}

	@Override
	public void bind(Evenement a) {
		this.binding.setEvenement(a);
	}

}