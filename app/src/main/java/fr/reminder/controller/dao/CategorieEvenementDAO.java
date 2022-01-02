package fr.reminder.controller.dao;

import android.content.ContentValues;
import android.database.Cursor;

import fr.commons.generique.controller.dao.AbstractObjetBddAvecIdDAO;
import fr.commons.generique.controller.utils.DatabaseUtils;
import fr.reminder.model.CategorieEvenement;

public class CategorieEvenementDAO extends AbstractObjetBddAvecIdDAO<CategorieEvenement> {

	public static final String NOM_TABLE = "CATEGORIE_EVENEMENT";
	public static final String COL_NOM = "NOM";

	private static CategorieEvenementDAO instance;

	public static CategorieEvenementDAO getInstance() {
		if (instance == null) {
			instance = new CategorieEvenementDAO();
		}
		return instance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNomTable() {
		return NOM_TABLE;
	}

	@Override
	protected ContentValues getContentValues(CategorieEvenement bo) {
		ContentValues cv = super.getContentValues(bo);
		cv.put(COL_NOM, bo.getNom());
		return cv;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CategorieEvenement convert(Cursor c) {
		CategorieEvenement cat = new CategorieEvenement(getId(c));
		cat.setNom(DatabaseUtils.getStringCheckNullColumn(c, COL_NOM));
		return cat;
	}
}