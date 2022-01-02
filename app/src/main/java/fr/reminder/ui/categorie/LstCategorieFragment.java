package fr.reminder.ui.categorie;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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
import fr.reminder.controller.dao.CategorieEvenementDAO;
import fr.reminder.databinding.FragmentLstCategorieBinding;
import fr.reminder.model.CategorieEvenement;

public class LstCategorieFragment extends Fragment {

	private FragmentLstCategorieBinding binding;

	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState
	) {

		this.binding = FragmentLstCategorieBinding.inflate(inflater, container, false);
		View root = this.binding.getRoot();

		// UI
		//--------------------------------------------------
		RecyclerView recListCategorie = this.binding.lstCardViewCategorie.listCardView;
		LinearLayoutManager llm = new LinearLayoutManager(getContext());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recListCategorie.setLayoutManager(llm);

		AppCompatButton btnAddCategorie = this.binding.btnAddCategorie;


		// Get Data initial
		//--------------------------------------------------
		List<CategorieEvenement> lstCategorie = CategorieEvenementDAO.getInstance().selectAll();

		// Adapter
		//--------------------------------------------------
		LstCategorieAdapter adapterCategorie = new LstCategorieAdapter(lstCategorie);
		recListCategorie.setAdapter(adapterCategorie);

		// On Action Item Adapter
		//--------------------------------------------------
		final BiConsumer<View,Integer> binderCardViewCategorie = (view, i) -> {
			CategorieEvenement c = adapterCategorie.getItemAt(i);

			// Le clic dépends de la data actuel de la card view
			view.setOnClickListener(v -> {
				int bgColorId;
				if (adapterCategorie.isSelected(c)) {
					// unselect
					adapterCategorie.setSelected(null);
					bgColorId = R.color.cardview_dark_background;
				} else {
					// select
					adapterCategorie.setSelected(c);
					bgColorId = R.color.main_dark;
					toLstType(v, c.getId());
				}
				v.setBackgroundColor(getResources().getColor(bgColorId, v.getContext().getTheme()));
			});

			view.setOnLongClickListener(v -> {
				EditCategorieDialogFragment d = new EditCategorieDialogFragment(c);
				d.addOnItemValideListener(adapterCategorie::addItem);
				d.show(getParentFragmentManager(), LstCategorieFragment.class.getName());
				return false;
			});
		};
		adapterCategorie.setBinderView(binderCardViewCategorie);


		SwipeController.SwipeControllerParams params = new SwipeController.SwipeControllerParams();
		params.setActions(new SwipeController.SwipeControllerActions() {

			@Override
			public void onEditClicked(int position) {
				CategorieEvenement c = adapterCategorie.getItemAt(position);
				EditCategorieDialogFragment d = new EditCategorieDialogFragment(c);
				d.addOnItemValideListener(adapterCategorie::addItem);
				d.show(getParentFragmentManager(), "DIALOG_CATEG");
			}

			@Override
			public void onDeleteClicked(int position) {
				CategorieEvenement c = adapterCategorie.getItemAt(position);
				DeleteDialogObjetBddFragment<CategorieEvenement> d = new DeleteDialogObjetBddFragment<>(c, CategorieEvenementDAO.getInstance());
				d.addOnItemValideListener(adapterCategorie::removeItem);
				d.show(getParentFragmentManager(), "DIALOG_CATEG");
			}
		});
		params.setIdColorSwipeBg(R.color.main_dark);
		SwipeController swipeController = new SwipeController(params);
		ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
		itemTouchHelper.attachToRecyclerView(recListCategorie);
		recListCategorie.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
				swipeController.onDraw(c);
			}
		});


		btnAddCategorie.setOnClickListener(v -> {
			CategorieEvenement newCategorie = new CategorieEvenement();
			EditCategorieDialogFragment d = new EditCategorieDialogFragment(newCategorie);
			d.addOnItemValideListener(adapterCategorie::addItem);
			d.show(getParentFragmentManager(), LstCategorieFragment.class.getName());
		});

		return root;
	}

	private void toLstType(View v, long idCategorie) {
		LstCategorieFragmentDirections.ActionLstCategorieToLstType action = LstCategorieFragmentDirections.actionLstCategorieToLstType();
		action.setIdCategorie(idCategorie);
		Navigation.findNavController(v).navigate(action);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		this.binding = null;
	}

}