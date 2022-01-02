package fr.reminder.controller.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;
import java.util.List;

import fr.commons.generique.controller.dao.AbstractObjetBddAvecIdDAO;
import fr.commons.generique.controller.utils.DatabaseUtils;
import fr.reminder.model.Duree;
import fr.reminder.model.TypeEvenement;


public class TypeEvenementDAO extends AbstractObjetBddAvecIdDAO<TypeEvenement> {

	public static final String NOM_TABLE = "TYPE_EVENEMENT";
	public static final String COL_NOM = "NOM";
	public static final String COL_ID_CATEGORIE = "ID_CATEGORIE";
	public static final String COL_IS_CREATION_AUTO = "IS_CREATION_AUTO";
	public static final String COL_IS_PROGRAMMABLE = "IS_PROGRAMMABLE";
	public static final String COL_DELAI = "DELAI";
	public static final String COL_FREQ = "FREQ";
	public static final String COL_RAPPEL = "RAPPEL";
	public static final String COL_DUREE = "DUREE";

	private static TypeEvenementDAO instance;

	public static TypeEvenementDAO getInstance() {
		if (instance == null) {
			instance = new TypeEvenementDAO();
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected TypeEvenement convert(Cursor c) {
		TypeEvenement t = new TypeEvenement(getId(c));
		t.setNom(DatabaseUtils.getStringCheckNullColumn(c, COL_NOM));
		t.setIdCategorie(DatabaseUtils.getLongCheckNullColumn(c, COL_ID_CATEGORIE));
		t.setCreationAutomatique(DatabaseUtils.getIntCheckNullColumn(c, COL_IS_CREATION_AUTO) == 1);
		t.setProgrammable(DatabaseUtils.getIntCheckNullColumn(c, COL_IS_PROGRAMMABLE) == 1);
		t.setDelai(new Duree(DatabaseUtils.getIntCheckNullColumn(c, COL_DELAI)));
		t.setFrequence(new Duree(DatabaseUtils.getIntCheckNullColumn(c, COL_FREQ)));
		t.setRappel(new Duree(DatabaseUtils.getIntCheckNullColumn(c, COL_RAPPEL)));
		t.setDuree(new Duree(DatabaseUtils.getIntCheckNullColumn(c, COL_DUREE)));
		return t;
	}

	@Override
	protected ContentValues getContentValues(TypeEvenement t) {
		ContentValues cv = super.getContentValues(t);
		cv.put(COL_NOM, t.getNom());
		cv.put(COL_ID_CATEGORIE, t.getIdCategorie());
		cv.put(COL_IS_CREATION_AUTO, t.isCreationAutomatique());
		cv.put(COL_IS_PROGRAMMABLE, t.isProgrammable());
		if (t.getDelai() != null) {
			cv.put(COL_DELAI, t.getDelai().toFullSecondes());
		}
		if (t.getFrequence() != null) {
			cv.put(COL_FREQ, t.getFrequence().toFullSecondes());
		}
		if (t.getRappel() != null) {
			cv.put(COL_RAPPEL, t.getRappel().toFullSecondes());
		}
		if (t.getDuree() != null) {
			cv.put(COL_DUREE, t.getDuree().toFullSecondes());
		}
		return cv;
	}

	public List<TypeEvenement> getByIdCategorie(long idCategorie) {
		return selectAll(getSelectAllQuery(COL_ID_CATEGORIE + "=" + idCategorie));
	}

	public List<TypeEvenement> getLstTypeNonProgramme() {
		// Tous les ID qui ne sont pas parmis les évèvements futurs entre maintenant ET maintenant + fréquence
		String now = String.valueOf(new Date().getTime());
		String sb = COL_ID + " NOT IN (" +
				"SELECT " + EvenementDAO.COL_ID_TYPE + " FROM " + EvenementDAO.NOM_TABLE +
				" WHERE " + EvenementDAO.COL_DATE + " BETWEEN " + now + " AND (" + now + " + " + COL_FREQ +
				"))";
		return selectAll(getSelectAllQuery(sb));
	}
}