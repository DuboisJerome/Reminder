package fr.reminder.ui.type;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.BiConsumer;

import fr.commons.generique.ui.DeleteDialogObjetBddFragment;
import fr.commons.generique.ui.SwipeController;
import fr.reminder.R;
import fr.reminder.controller.dao.TypeEvenementDAO;
import fr.reminder.databinding.FragmentLstTypeBinding;
import fr.reminder.model.TypeEvenement;

public class LstTypeFragment extends Fragment {

	private FragmentLstTypeBinding binding;


	public LstTypeFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		this.binding = FragmentLstTypeBinding.inflate(inflater, container, false);
		View root = this.binding.getRoot();

		// UI
		//--------------------------------------------------
		RecyclerView recListType = this.binding.lstCardViewType.listCardView;
		LinearLayoutManager llm = new LinearLayoutManager(getContext());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recListType.setLayoutManager(llm);

		Button btnAddType = this.binding.btnAddType;

		// Get Data initial
		//--------------------------------------------------
		List<TypeEvenement> lstTypeEvenement = getLstTypeEvenement();

		// Adapter
		//--------------------------------------------------
		LstTypeAdapter adapterType = new LstTypeAdapter(lstTypeEvenement);
		recListType.setAdapter(adapterType);

		// On Action Item Adapter
		//--------------------------------------------------
		final BiConsumer<View,Integer> binderCardViewType = (view, i) -> {
			TypeEvenement t = adapterType.getItemAt(i);
			view.setOnClickListener(v -> {
				boolean isSelected = adapterType.isSelected(t);
				if (isSelected) {
					// unselect
					adapterType.setSelected(null);
				} else {
					// select
					adapterType.setSelected(t);
					toLstEvenement(v, t.getId());
				}
				v.setSelected(!isSelected);
			});

			view.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.cardview_selectable, view.getContext().getTheme()));

		};
		adapterType.setBinderView(binderCardViewType);

		SwipeController.SwipeControllerParams params = new SwipeController.SwipeControllerParams();
		params.setActions(new SwipeController.SwipeControllerActions() {

			@Override
			public void onEditClicked(int position) {
				TypeEvenement t = adapterType.getItemAt(position);
				EditTypeDialogFragment d = new EditTypeDialogFragment(t);
				d.addOnItemValideListener(adapterType::addItem);
				d.show(getParentFragmentManager(), "DIALOG_TYPE");
			}

			@Override
			public void onDeleteClicked(int position) {
				TypeEvenement t = adapterType.getItemAt(position);
				DeleteDialogObjetBddFragment<TypeEvenement> d = new DeleteDialogObjetBddFragment<>(t, TypeEvenementDAO.getInstance());
				d.addOnItemValideListener(adapterType::removeItem);
				d.show(getParentFragmentManager(), "DIALOG_TYPE");
			}
		});
		params.setIdColorSwipeBg(R.color.main_dark);
		params.setOrientation(SwipeController.VERTICAL);
		SwipeController swipeController = new SwipeController(params);
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
		itemTouchHelper.attachToRecyclerView(recListType);
		recListType.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				swipeController.onDraw(c);
			}
		});

		btnAddType.setOnClickListener(v -> {
			EditTypeDialogFragment d = creerDialogEditType(adapterType);
			d.show(getParentFragmentManager(), "DIALOG_TYPE");
		});

		return root;
	}

	protected EditTypeDialogFragment creerDialogEditType(LstTypeAdapter adapterType) {
		TypeEvenement newTypeEvenement = new TypeEvenement();
		EditTypeDialogFragment d = new EditTypeDialogFragment(newTypeEvenement);
		d.addOnItemValideListener(adapterType::addItem);
		return d;
	}

	protected List<TypeEvenement> getLstTypeEvenement() {
		return TypeEvenementDAO.getInstance().selectAll();
	}

	private void toLstEvenement(View v, long idType) {
		LstTypeFragmentFromCategorieDirections.ActionLstTypeToLstEvenement action = LstTypeFragmentFromCategorieDirections.actionLstTypeToLstEvenement();
		action.setIdType(idType);
		Navigation.findNavController(v).navigate(action);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		this.binding = null;
	}
}