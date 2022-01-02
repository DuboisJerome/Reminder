package fr.reminder.controller.utils;

import androidx.annotation.NonNull;

import java.io.IOException;

import fr.commons.generique.controller.utils.TagUtils;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

	public ExceptionHandler() {
		clearLogcat();
	}

	private void clearLogcat() {
		try {
			Runtime.getRuntime().exec(new String[]{"logcat", "-c"});
		} catch (IOException ioe) {
			logCrit("Unable to clear logcat", ioe);
		}
	}

	@Override
	public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
		logCrash(throwable);
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(10);
	}

	private void logCrash(final Throwable t) {
		logCrit("UncaughtException", t);
		logCause(t);
	}

	private void logCause(final Throwable t) {
		if (t != null) {
			Throwable cause = t.getCause();
			if (cause != null) {
				logCrit("Caused by", cause);
				logCause(cause);
			}
		}
	}

	private static void logCrit(String msg, Throwable t) {
		android.util.Log.e(TagUtils.CRIT, msg, t);
	}
}