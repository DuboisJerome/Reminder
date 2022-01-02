package fr.reminder.controller.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;
import java.util.List;

import fr.commons.generique.controller.dao.AbstractObjetBddAvecIdDAO;
import fr.commons.generique.controller.utils.DatabaseUtils;
import fr.reminder.model.Evenement;

public class EvenementDAO extends AbstractObjetBddAvecIdDAO<Evenement> {

	public static final String NOM_TABLE = "EVENEMENT";
	public static final String COL_DATE = "DATE";
	public static final String COL_ID_TYPE = "ID_TYPE";
	public static final String COL_LIEU = "LIEU";
	public static final String COL_COMMENTAIRE = "COMMENTAIRE";
	public static final String COL_IS_PROGRAMME = "IS_PROGRAMME";

	private static EvenementDAO instance;

	public static EvenementDAO getInstance() {
		if (instance == null) {
			instance = new EvenementDAO();
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
	protected Evenement convert(Cursor c) {
		Evenement ev = new Evenement(getId(c));
		ev.setIdType(DatabaseUtils.getLongCheckNullColumn(c, COL_ID_TYPE));
		ev.setDate(new Date(DatabaseUtils.getLongCheckNullColumn(c, COL_DATE)));
		ev.setLieu(DatabaseUtils.getStringCheckNullColumn(c, COL_LIEU));
		ev.setCommentaire(DatabaseUtils.getStringCheckNullColumn(c, COL_COMMENTAIRE));
		ev.setProgramme(DatabaseUtils.getIntCheckNullColumn(c, COL_IS_PROGRAMME) == 1);
		return ev;
	}

	@Override
	protected ContentValues getContentValues(Evenement bo) {
		ContentValues cv = super.getContentValues(bo);
		if (bo.getDate() != null) {
			cv.put(COL_DATE, bo.getDate().getTime());
		}
		cv.put(COL_ID_TYPE, bo.getIdType());
		cv.put(COL_LIEU, bo.getLieu());
		cv.put(COL_COMMENTAIRE, bo.getCommentaire());
		cv.put(COL_IS_PROGRAMME, bo.isProgramme());
		return cv;
	}

	public List<Evenement> getLstEvenementAVenir() {
		return selectAll(getSelectAllQuery(COL_DATE + " >= " + new Date().getTime() + " ORDER BY " + COL_DATE));
	}

	public List<Evenement> getLstEvenementHisto() {
		return selectAll(getSelectAllQuery(COL_DATE + " < " + new Date().getTime() + " ORDER BY " + COL_DATE + " desc"));
	}

	public List<Evenement> getByIdType(long idType) {
		return selectAll(getSelectAllQuery(COL_ID_TYPE + " = " + idType + " ORDER BY " + COL_DATE));
	}
}