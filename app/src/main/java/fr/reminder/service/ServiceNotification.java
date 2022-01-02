package fr.reminder.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import fr.reminder.R;
import fr.reminder.model.Evenement;

public class ServiceNotification {

	private static final String NOTIF_CHANNEL_ID = "NOTIF_CHANNEL_ID";

	public static final int CODE_NOTIF_ALARM = 1;

	public static void createNotificationChannel(@NonNull Context ctx) {
		CharSequence name = ctx.getString(R.string.channel_name);
		String description = ctx.getString(R.string.channel_description);
		int importance = NotificationManager.IMPORTANCE_DEFAULT;
		NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, name, importance);
		channel.setDescription(description);
		// Register the channel with the system; you can't change the importance
		// or other notification behaviors after this
		NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
		notificationManager.createNotificationChannel(channel);
	}

	public static Notification creerNotifRappel(@NonNull Context ctx, Evenement e) {
		Object[] args = {e.getType().getCategorie().getNom(),
				e.getType().getNom(), e.getLieu()};
		String contenu = ctx.getString(R.string.contenu_notif_rappel, args);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, NOTIF_CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_baseline_alarm_24)
				.setWhen(e.getRappelMs())
				.setContentTitle(ctx.getString(R.string.titre_notif_rappel))
				.setContentText(contenu)
				.setPriority(NotificationCompat.PRIORITY_HIGH);

		return builder.build();
	}

	public static Notification creerNotifDebut(@NonNull Context ctx, Evenement e) {
		Object[] args = {e.getType().getCategorie().getNom(),
				e.getType().getNom(), e.getLieu()};
		String contenu = ctx.getString(R.string.contenu_notif_debut_evenement, args);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, NOTIF_CHANNEL_ID)
				.setSmallIcon(R.drawable.ic_baseline_alarm_24)
				.setWhen(e.getDate().getTime())
				.setContentTitle(ctx.getString(R.string.titre_notif_debut_evenement))
				.setContentText(contenu)
				.setPriority(NotificationCompat.PRIORITY_HIGH);

		return builder.build();
	}
}