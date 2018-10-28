package roiattia.com.salariestrack.sync;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;

import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;

import java.util.concurrent.TimeUnit;

import static com.firebase.jobdispatcher.Lifetime.FOREVER;

public class ReminderUtils {

    private static final int REMINDER_INTERVAL_MINUTES = 60*24;
    private static final int REMINDER_INTERVAL_SECONDS = (int) TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES);
    private static final int SYNC_FLEXTIME_SECONDS = (int) TimeUnit.MINUTES.toSeconds(60);

    private static final String REMINDER_JOB_TAG = "salaries_reminder_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleSalariesReminder(Context context){
        if(sInitialized) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job constraintsReminderJob = dispatcher.newJobBuilder()
                .setService(SalariesFirebaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setLifetime(FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS, SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(constraintsReminderJob);
        sInitialized = true;
    }

    private static int getReminderTime() {
        LocalDateTime toDate = new LocalDateTime();
        toDate = toDate.plusDays(0).withHourOfDay(17).withMinuteOfHour(48);
        Log.i("kinga", "to " + toDate.toString());
        LocalDateTime now = new LocalDateTime();
        Log.i("kinga", "now " + now.toString());
        Log.i("kinga", "difference " + Minutes.minutesBetween(now, toDate).getMinutes());
        return (int) TimeUnit.MINUTES.toSeconds(Minutes.minutesBetween(now, toDate).getMinutes());
    }

}
