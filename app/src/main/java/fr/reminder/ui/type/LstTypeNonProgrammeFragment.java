package fr.reminder.ui.type;

import java.util.List;

import fr.reminder.controller.dao.TypeEvenementDAO;
import fr.reminder.model.TypeEvenement;

public class LstTypeNonProgrammeFragment extends LstTypeFragment {

	@Override
	protected List<TypeEvenement> getLstTypeEvenement() {
		return TypeEvenementDAO.getInstance().getLstTypeNonProgramme();
	}
}