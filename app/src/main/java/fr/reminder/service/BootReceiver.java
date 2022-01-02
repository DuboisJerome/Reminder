package fr.reminder.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import fr.reminder.controller.dao.EvenementDAO;
import fr.reminder.model.Evenement;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctx, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			EvenementDAO dao = EvenementDAO.getInstance();
			List<Evenement> l = dao.getLstEvenementAVenir();
			for (Evenement e : l) {
				AlarmReceiver.alarm(ctx, e);
			}
		}
	}
}