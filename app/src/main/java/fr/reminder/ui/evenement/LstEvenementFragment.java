package fr.reminder.ui.evenement;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;

import fr.commons.generique.ui.DeleteDialogObjetBddFragment;
import fr.commons.generique.ui.SwipeController;
import fr.reminder.R;
import fr.reminder.controller.dao.EvenementDAO;
import fr.reminder.controller.dao.TypeEvenementDAO;
import fr.reminder.databinding.FragmentLstEvenementBinding;
import fr.reminder.model.Evenement;
import fr.reminder.model.TypeEvenement;
import fr.reminder.service.AlarmReceiver;

public class LstEvenementFragment extends Fragment {

	protected FragmentLstEvenementBinding binding;
	protected LstEvenementAdapter adapter;

	@Override
	public View onCreateView(
			@NonNull LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState
	) {

		this.binding = FragmentLstEvenementBinding.inflate(inflater, container, false);
		View root = this.binding.getRoot();

		// UI
		//--------------------------------------------------
		RecyclerView recList = this.binding.lstCardViewEvenement.listCardView;
		LinearLayoutManager llm = new LinearLayoutManager(getContext());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recList.setLayoutManager(llm);

		// Get Data initial
		//--------------------------------------------------
		List<Evenement> lstEvenement = getLstEvenement();
		Comparator<Evenement> comparator = getComparatorLstEvenement();

		// Adapter
		//--------------------------------------------------
		this.adapter = new LstEvenementAdapter(lstEvenement, comparator);
		recList.setAdapter(this.adapter);

		// On Action Item Adapter
		//--------------------------------------------------
		final BiConsumer<View,Integer> binderCardViewType = (view, i) -> {
			Evenement e = this.adapter.getItemAt(i);
			view.setOnClickListener(v -> {
				boolean isSelected = this.adapter.isSelected(e);
				int visibilityInfoCompl;
				if (isSelected) {
					// unselect
					this.adapter.setSelected(null);
					visibilityInfoCompl = View.GONE;
				} else {
					// select
					this.adapter.setSelected(e);
					visibilityInfoCompl = View.VISIBLE;
				}
				View textView = view.findViewById(R.id.lieu_evenement);
				textView.setVisibility(visibilityInfoCompl);
				textView = view.findViewById(R.id.commentaire_evenement);
				textView.setVisibility(visibilityInfoCompl);
				v.setSelected(!isSelected);
			});

			int idDrawable = e.getType() != null && e.getType().isProgrammable() && !e.isProgramme() ? R.drawable.cardview_selectable_warn : R.drawable.cardview_selectable;
			view.setBackground(ResourcesCompat.getDrawable(getResources(), idDrawable, view.getContext().getTheme()));
		};
		this.adapter.setBinderView(binderCardViewType);

		SwipeController.SwipeControllerParams params = new SwipeController.SwipeControllerParams();
		params.setActions(new SwipeController.SwipeControllerActions() {

			@Override
			public void onEditClicked(int position) {
				Evenement e = LstEvenementFragment.this.adapter.getItemAt(position);
				EditEvenementDialogFragment d = new EditEvenementDialogFragment(e);
				d.addOnItemValideListener(LstEvenementFragment.this.adapter::addItem);
				d.show(getParentFragmentManager(), "DIALOG_TYPE");
			}

			@Override
			public void onDeleteClicked(int position) {
				Evenement e = LstEvenementFragment.this.adapter.getItemAt(position);
				DeleteDialogObjetBddFragment<Evenement> d = new DeleteDialogObjetBddFragment<>(e, EvenementDAO.getInstance());
				d.addOnItemValideListener(LstEvenementFragment.this.adapter::removeItem);
				d.addOnDeleteListener(ev -> AlarmReceiver.cancelAlarmAll(getContext(), ev.getId()));
				d.show(getParentFragmentManager(), "DIALOG_TYPE");
			}
		});
		params.setIdColorSwipeBg(R.color.main_dark);
		SwipeController swipeController = new SwipeController(params);
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
		itemTouchHelper.attachToRecyclerView(recList);
		recList.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				swipeController.onDraw(c);
			}
		});

		return root;
	}

	protected Comparator<Evenement> getComparatorLstEvenement() {
		return null;
	}

	protected List<Evenement> getLstEvenement() {
		long idType = LstEvenementFragmentArgs.fromBundle(getArguments()).getIdType();
		TypeEvenement preselectedType = TypeEvenementDAO.getInstance().getById(idType);
		if (preselectedType != null) {
			return EvenementDAO.getInstance().getByIdType(idType);
		}
		return EvenementDAO.getInstance().selectAll();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		this.binding = null;
	}
}