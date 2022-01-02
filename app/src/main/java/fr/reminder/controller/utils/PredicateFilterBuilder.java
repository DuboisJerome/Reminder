package fr.reminder.controller.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Function;
import java.util.function.Predicate;

import fr.commons.generique.controller.dao.AbstractObjetBddAvecIdDAO;
import fr.commons.generique.controller.utils.TagUtils;
import fr.commons.generique.model.db.AbstractObjetBddAvecId;

public class PredicateFilterBuilder<T> {

	private Predicate<T> predicate = null;
	private final JSONObject jsonObjectFilter;

	public PredicateFilterBuilder(String json) throws JSONException {
		this.jsonObjectFilter = new JSONObject(json);
	}

	public void add(Predicate<T> p) {
		if (this.predicate == null) {
			this.predicate = p;
		} else {
			this.predicate = p.and(p);
		}
	}

	public void addLongFilter(String nomCol, Function<T,Long> supplier) throws JSONException {
		Long l = this.jsonObjectFilter.has(nomCol) ? this.jsonObjectFilter.getLong(nomCol) : null;
		if (l != null) {
			Predicate<T> p = o -> l.equals(supplier.apply(o));
			add(p);
		}
	}

	public Predicate<T> getPredicate() {
		return this.predicate;
	}

	public static <T extends AbstractObjetBddAvecId> Predicate<T> getPredicateFilterById(String json) {
		if (json == null) return null;
		Predicate<T> filter = null;
		try {
			PredicateFilterBuilder<T> predicateBuilder = new PredicateFilterBuilder<>(json);
			predicateBuilder.addLongFilter(AbstractObjetBddAvecIdDAO.COL_ID, AbstractObjetBddAvecId::getId);
			filter = predicateBuilder.getPredicate();
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e(TagUtils.CRIT, e.getMessage());
		}
		return filter;
	}
}