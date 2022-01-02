package fr.reminder.ui.evenement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.Calendar;
import java.util.List;

import fr.commons.generique.controller.db.TableDAO;
import fr.commons.generique.model.db.AbstractObjetBddAvecId;
import fr.commons.generique.ui.AbstractEditDialogObjetBddFragment;
import fr.reminder.R;
import fr.reminder.controller.dao.EvenementDAO;
import fr.reminder.controller.dao.TypeEvenementDAO;
import fr.reminder.controller.utils.BindingConverter;
import fr.reminder.databinding.EditEvenementBinding;
import fr.reminder.model.Evenement;
import fr.reminder.model.TypeEvenement;
import fr.reminder.service.AlarmReceiver;
import fr.reminder.ui.type.TypeSpinnerAdapter;
import lombok.Setter;

public class EditEvenementDialogFragment extends AbstractEditDialogObjetBddFragment<Evenement> {

	@Setter
	private TypeEvenement preselectedType;

	private EditEvenementBinding binding;

	public EditEvenementDialogFragment(@NonNull Evenement item) {
		super(item);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		addValidationItemListener(this::check);
		addOnCreateListener(e -> AlarmReceiver.alarm(requireContext(), e));
		addOnUpdateListener(e -> {
			AlarmReceiver.alarm(requireContext(), e);
		});
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected ViewDataBinding createBinding() {
		this.binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
				R.layout.edit_evenement, null, false);

		this.binding.setEvenement(this.item);


		// you need to have a list of data that you want the spinner to display
		TypeSpinnerAdapter adapter = new TypeSpinnerAdapter(getActivity());
		if (this.preselectedType != null) {
			adapter.getList().add(this.preselectedType);
			this.binding.getEvenement().setIdType(this.preselectedType.getId());
		} else {
			List<TypeEvenement> lstType = TypeEvenementDAO.getInstance().selectAll();
			adapter.getList().addAll(lstType);
		}
		this.binding.type.setAdapter(adapter);
		this.binding.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				EditEvenementDialogFragment.this.binding.getEvenement().setIdType(adapter.getItemId(position));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Ne devrait pas arriver
				throw new NullPointerException("Rien a été sélectionné, ce choix ne devrait pas être possible");
			}
		});

		this.binding.dateEvenement.setShowSoftInputOnFocus(false);
		this.binding.dateEvenement.setOnFocusChangeListener((v, b) -> {
			if (b) openDatePicker();
		});
		this.binding.dateEvenement.setOnClickListener((v -> {
			openDatePicker();
		}));

		return this.binding;
	}

	private void openDatePicker() {
		final Calendar cal = Calendar.getInstance();
		if (this.binding.getEvenement().getDate() != null) {
			cal.setTime(this.binding.getEvenement().getDate());
		}
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		// date picker dialog
		DatePickerDialog picker = new DatePickerDialog(getContext(),
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						openTimePicker(cal, year, monthOfYear, dayOfMonth);
					}
				}, year, month, day);
		picker.show();
	}

	private void openTimePicker(Calendar cal, int year, int monthOfYear, int dayOfMonth) {
		int hh = cal.get(Calendar.HOUR_OF_DAY);
		int mm = cal.get(Calendar.MINUTE);
		// time picker dialog
		TimePickerDialog picker = new TimePickerDialog(getContext(),
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						Calendar c = Calendar.getInstance();
						c.set(Calendar.YEAR, year);
						c.set(Calendar.MONTH, monthOfYear);
						c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						c.set(Calendar.HOUR_OF_DAY, hourOfDay);
						c.set(Calendar.MINUTE, minute);
						EditEvenementDialogFragment.this.binding.dateEvenement.setText(BindingConverter.dateToString(c.getTime()));
					}
				}, hh, mm, true);
		picker.show();
	}

	private boolean check(Evenement evenement) {
		boolean isValid = true;
		if (evenement != null) {
			if (evenement.getDate() == null) {
				this.binding.dateEvenement.setError("Obligatoire");
				isValid = false;
			}
			if (evenement.getIdType() == AbstractObjetBddAvecId.NO_ID) {
				Adapter a = this.binding.type.getAdapter();
				if (a.getCount() > 0) {
					evenement.setIdType(this.binding.type.getAdapter().getItemId(0));
				} else {
					TextView errorText = (TextView) this.binding.type.getSelectedView();
					errorText.setError("");
					errorText.setTextColor(Color.RED);//just to highlight that this is an error
					errorText.setText("Obligatoire");//changes the selected item text to this
					isValid = false;
				}
			}
		} else {
			isValid = false;
		}
		return isValid;
	}

	@Override
	protected TableDAO<Evenement> getTableDAO() {
		return EvenementDAO.getInstance();
	}
}