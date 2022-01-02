package fr.reminder.ui.type;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Predicate;

import fr.commons.generique.controller.utils.TagUtils;
import fr.commons.generique.ui.AbstractGeneriqueAdapter;
import fr.reminder.controller.dao.TypeEvenementDAO;
import fr.reminder.controller.utils.PredicateFilterBuilder;
import fr.reminder.databinding.CardViewTypeBinding;
import fr.reminder.model.TypeEvenement;

public class LstTypeAdapter extends AbstractGeneriqueAdapter<TypeEvenement,LstTypeViewHolder> {
	public LstTypeAdapter(List<TypeEvenement> l) {
		super(l);
	}

	@Override
	protected Predicate<TypeEvenement> getPredicateFilter(String json) {
		if (json == null) return null;
		Predicate<TypeEvenement> filter = null;
		try {
			PredicateFilterBuilder<TypeEvenement> predicateBuilder = new PredicateFilterBuilder<>(json);
			predicateBuilder.add(PredicateFilterBuilder.getPredicateFilterById(json));
			predicateBuilder.addLongFilter(TypeEvenementDAO.COL_ID_CATEGORIE, TypeEvenement::getIdCategorie);
			filter = predicateBuilder.getPredicate();
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(TagUtils.CRIT, e.getMessage());
		}
		return filter;
	}

	public void filterByCategorie(long idCategorie) {
		JSONObject j = new JSONObject();
		try {
			j.put(TypeEvenementDAO.COL_ID_CATEGORIE, idCategorie);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		filter(j.toString());
	}

	@NonNull
	@Override
	public LstTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		CardViewTypeBinding binding = CardViewTypeBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
		return new LstTypeViewHolder(binding);
	}
}