package roiattia.com.salariestrack.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import java.util.List;

import roiattia.com.salariestrack.R;
import roiattia.com.salariestrack.model.SalaryEntry;
import roiattia.com.salariestrack.model.SalaryListItem;
import roiattia.com.salariestrack.ui.salarieslist.SalariesListActivity;

import static roiattia.com.salariestrack.utils.Constants.SALARIES_PENDING_INTENT_ID;
import static roiattia.com.salariestrack.utils.Constants.SALARIES_REMINDER_NOTIFICATION_CHANNEL_ID;
import static roiattia.com.salariestrack.utils.Constants.SALARIES_REMINDER_NOTIFICATION_ID;

public class NotificationUtils {

    public static void remindUserToPaySalaries(Context context, List<SalaryEntry> salaries) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    SALARIES_REMINDER_NOTIFICATION_CHANNEL_ID,
                    "channel name",
                    NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                context,SALARIES_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_add_white_24dp)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("It's pay day!")
                .setContentText("Salaries to pay: " + salaries.size() + ", of total: " + salaries.get(0).getSalary())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        // set the notification's priority to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
        if (notificationManager != null) {
            notificationManager.notify(SALARIES_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
        }
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, SalariesListActivity.class);
        return PendingIntent.getActivity(
                context,
                SALARIES_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
