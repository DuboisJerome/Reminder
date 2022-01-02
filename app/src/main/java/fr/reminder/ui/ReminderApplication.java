package fr.reminder.ui;

import android.app.Application;

import fr.commons.generique.controller.db.DBHelper;
import fr.reminder.R;

/**
 * @author Zapagon
 */
public class ReminderApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		//Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
		DBHelper db = new DBHelper(getApplicationContext(), getApplicationContext().getString(R.string.db_name), getApplicationContext().getResources().getInteger(R.integer.db_version));
		DBHelper.setInstance(db);
	}

}