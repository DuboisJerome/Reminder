package fr.reminder.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.commons.generique.controller.db.DBHelper;
import fr.commons.generique.controller.utils.Log;
import fr.reminder.R;
import fr.reminder.databinding.ActivityMainBinding;
import fr.reminder.service.ServiceNotification;

public class MainActivity extends AppCompatActivity {
	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(this.binding.getRoot());

		BottomNavigationView navView = findViewById(R.id.nav_view);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_prog_a_venir, R.id.navigation_non_programmees, R.id.navigation_historique, R.id.navigation_lst_categorie)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(this.binding.navView, navController);

		ServiceNotification.createNotificationChannel(getApplicationContext());

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case DBHelper
					.IMPORT_DATABASE:
				if (resultCode == Activity.RESULT_OK) {
					if (data != null) {
						Uri fileUri = data.getData();
						Log.info("Uri: " + fileUri);

						try {
							if (!DBHelper.getInstance().importDatabase(getApplicationContext(), fileUri)) {
								Toast.makeText(getApplicationContext(), "Import BDD NOK", Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(getApplicationContext(), "Import BDD OK", Toast.LENGTH_SHORT).show();
								recreate();
							}
						} catch (Exception e) {
							Log.error("Error: ", e);
							Toast.makeText(this.getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
						}
					}
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.import_db:
				DBHelper.getInstance().importDatabase(this);
				return true;

			case R.id.export_db:
				DBHelper.getInstance().exportDatabase(getApplicationContext());
				return true;
			default:
				// If we got here, the user's action was not recognized.
				// Invoke the superclass to handle it.
				return super.onOptionsItemSelected(item);

		}
	}


}