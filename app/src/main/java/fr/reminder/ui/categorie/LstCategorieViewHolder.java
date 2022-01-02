package fr.reminder.ui.categorie;

import fr.commons.generique.ui.AbstractGeneriqueViewHolder;
import fr.reminder.databinding.CardViewCategorieBinding;
import fr.reminder.model.CategorieEvenement;

public class LstCategorieViewHolder extends AbstractGeneriqueViewHolder<CategorieEvenement> {

	protected CardViewCategorieBinding binding;

	public LstCategorieViewHolder(CardViewCategorieBinding binding) {
		super(binding.getRoot());
		this.binding = binding;
	}

	@Override
	public void bind(CategorieEvenement v) {
		this.binding.setCategorie(v);
	}
}