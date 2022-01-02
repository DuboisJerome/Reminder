package fr.reminder.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Date;

import fr.commons.generique.model.db.AbstractObjetBddAvecId;
import fr.reminder.controller.dao.EvenementDAO;
import fr.reminder.controller.utils.DureeManager;
import fr.reminder.model.Duree;
import fr.reminder.model.Evenement;
import fr.reminder.model.TypeEvenement;

public class AlarmReceiver extends BroadcastReceiver {
	private static final int CODE_CREATE_ALARM = 1;
	private static final String NAME_EXTRA_ID_EVENEMENT = "ID_EVENT";
	private static final String NAME_EXTRA_IS_RAPPEL = "IS_RAPPEL";
	private static final String START_NOTIF_ACTION = "evenements/notif/";

	@Override
	public void onReceive(Context ctx, Intent intent) {
		if (intent.getAction().startsWith(START_NOTIF_ACTION)) {
			long idEvenement = intent.getLongExtra(NAME_EXTRA_ID_EVENEMENT, AbstractObjetBddAvecId.NO_ID);
			Evenement e = EvenementDAO.getInstance().getById(idEvenement);
			if (e == null) return;

			// Alarm simple notif
			NotificationManager notifManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
			boolean isRappel = intent.getBooleanExtra(NAME_EXTRA_IS_RAPPEL, false);
			Notification notif;
			if (isRappel) {
				notif = ServiceNotification.creerNotifRappel(ctx, e);
			} else {
				notif = ServiceNotification.creerNotifDebut(ctx, e);
				TypeEvenement type = e.getType();
				Duree freq = type.getFrequence();
				if (type.isCreationAutomatique() && !freq.isEmpty()) {
					// Créer le prochain
					Date nextDate = new DureeManager(e.getDate()).add(freq).getTime();
					Evenement nextEvenement = new Evenement();
					nextEvenement.setIdType(e.getIdType());
					nextEvenement.setProgramme(false);
					nextEvenement.setDate(nextDate);
					nextEvenement.setCommentaire(e.getCommentaire());
					nextEvenement.setLieu(e.getLieu());
					EvenementDAO.getInstance().creer(nextEvenement);
				}
			}
			notifManager.notify(ServiceNotification.CODE_NOTIF_ALARM, notif);
		}
	}


	private static String actionNotifDebut(long idEvenement) {
		return START_NOTIF_ACTION + "debut/" + idEvenement;
	}

	private static String actionNotifRappel(long idEvenement) {
		return START_NOTIF_ACTION + "rappel/" + idEvenement;
	}

	public static void alarm(Context ctx, Evenement e) {
		long now = SystemClock.currentThreadTimeMillis();
		long rappelMs = e.getRappelMs();
		if (rappelMs > 0 && now < rappelMs) {
			alarmRappel(ctx, e.getId(), rappelMs);
		}
		alarmDebut(ctx, e.getId(), e.getDate().getTime());
	}

	public static void alarmDebut(Context ctx, long idEvenement, long dateMs) {
		// Désactive une alarme précédente si déjà set
		cancelAlarmDebut(ctx, idEvenement);
		// Création d'une nouvelle alarm
		AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		Intent intentNotif = new Intent(ctx, AlarmReceiver.class);
		intentNotif.setAction(actionNotifDebut(idEvenement));
		intentNotif.putExtra(NAME_EXTRA_ID_EVENEMENT, idEvenement);
		intentNotif.putExtra(NAME_EXTRA_IS_RAPPEL, false);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(ctx, CODE_CREATE_ALARM, intentNotif, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
		am.setExact(AlarmManager.RTC_WAKEUP, dateMs, alarmIntent);
	}

	public static void alarmRappel(Context ctx, long idEvenement, long rappelMs) {
		// Désactive une alarme précédente si déjà set
		cancelAlarmRappel(ctx, idEvenement);
		// Création d'une nouvelle alarm
		AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		Intent intentNotif = new Intent(ctx, AlarmReceiver.class);
		intentNotif.setAction(actionNotifRappel(idEvenement));
		intentNotif.putExtra(NAME_EXTRA_ID_EVENEMENT, idEvenement);
		intentNotif.putExtra(NAME_EXTRA_IS_RAPPEL, true);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(ctx, CODE_CREATE_ALARM, intentNotif, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
		am.setExact(AlarmManager.RTC_WAKEUP, rappelMs, alarmIntent);
	}

	public static void cancelAlarmAll(Context ctx, long idEvenement) {
		cancelAlarmDebut(ctx, idEvenement);
		cancelAlarmRappel(ctx, idEvenement);
	}

	public static void cancelAlarmDebut(Context ctx, long idEvenement) {
		AlarmManager mAlarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(ctx, AlarmReceiver.class);
		intent.setAction(actionNotifDebut(idEvenement));
		PendingIntent alarmIntent = PendingIntent.getBroadcast(ctx, CODE_CREATE_ALARM, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_NO_CREATE);
		if (alarmIntent != null) {
			mAlarmManager.cancel(alarmIntent);
		}
	}

	public static void cancelAlarmRappel(Context ctx, long idEvenement) {
		AlarmManager mAlarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(ctx, AlarmReceiver.class);
		intent.setAction(actionNotifRappel(idEvenement));
		PendingIntent alarmIntent = PendingIntent.getBroadcast(ctx, CODE_CREATE_ALARM, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_NO_CREATE);
		if (alarmIntent != null) {
			mAlarmManager.cancel(alarmIntent);
		}
	}
}